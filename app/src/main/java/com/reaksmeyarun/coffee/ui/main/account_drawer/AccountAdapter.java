package com.reaksmeyarun.coffee.ui.main.account_drawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.reaksmeyarun.coffee.R;
import com.reaksmeyarun.coffee.model.User;

import java.util.ArrayList;
import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> implements Filterable {

    List<User> userList;
    List<User> userListFiltered;
    Context context;
    View view;

    public AccountAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.userListFiltered = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.custom_user_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(holder instanceof ViewHolder){
            User user = userListFiltered.get(position);
            ((ViewHolder) holder).binding(user);
        }
    }

    @Override
    public int getItemCount() {
        return userListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String key = constraint.toString();
                if(key.isEmpty()){
                    userListFiltered = userList;
                }else {
                    List<User> lstUser = new ArrayList<>();
                    for (User user : userList){
                        if ((user.getEmail().toLowerCase().contains(key.toLowerCase()))){
                            lstUser.add(user);
                        }
                    }
                    userListFiltered = lstUser;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = userListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                userListFiltered = (List<User>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvEmail, tvUsername, tvRule;
        ImageView btnMenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvRule = itemView.findViewById(R.id.tvRule);
            btnMenu = itemView.findViewById(R.id.btnDelete);
        }

        public void binding(User user){
            tvEmail.setText(user.getEmail());
            tvUsername.setText(user.getUser());
            tvRule.setText(user.getRule());

            btnMenu.setOnClickListener(v->{
                PopupMenu menu=new PopupMenu(context,v);
                menu.inflate(R.menu.account_menu);
                menu.setOnMenuItemClickListener(b->{
                    switch (b.getItemId()){
                        case R.id.btnView:
                            accCallBack.onView(user);
                            return true;
                        case R.id.btnEdit:
                            accCallBack.onEdit(user);
                            return true;
                        default : return false;
                    }
                });
                menu.show();
            });
        }
    }

    interface AccCallBack{
        void onEdit(User user);
        void onView(User user);
    }

    private AccCallBack accCallBack;

    public void setAccCallBack(AccCallBack accCallBack) {
        this.accCallBack = accCallBack;
    }

    public void addMoreUser(List<User> userList){
        this.userList.clear();
        this.userList.addAll(userList);
        notifyDataSetChanged();
    }
}
