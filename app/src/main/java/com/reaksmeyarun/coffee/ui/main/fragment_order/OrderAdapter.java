package com.reaksmeyarun.coffee.ui.main.fragment_order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.reaksmeyarun.coffee.R;
import com.reaksmeyarun.coffee.model.Global;
import com.reaksmeyarun.coffee.model.Table;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> implements Filterable {

    View view;
    Context context;
    List<Table> tableList;
    List<Table> tableListFiltered;

    public OrderAdapter(Context context, List<Table> tableList) {
            this.context = context;
            this.tableList = tableList;
            this.tableListFiltered = tableList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.custom_table_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(holder instanceof ViewHolder){
            Table table = tableListFiltered.get(position);
            ((ViewHolder) holder).binding(table);
        }
    }

    @Override
    public int getItemCount() {
        return tableListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String key = constraint.toString();
                if(key.isEmpty()){
                    tableListFiltered = tableList;
                }else {
                    List<Table> lstFilered = new ArrayList<>();
                    for(Table table : tableList){
                        if(table.getTableID().toLowerCase().contains(key.toLowerCase())||table.getStatus().toLowerCase().contains(key.toLowerCase())){
                            lstFilered.add(table);
                        }
                    }
                    tableListFiltered = lstFilered;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = tableListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                tableListFiltered = (List<Table>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTableID, tvStaus;
        ImageView btnView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnView = view.findViewById(R.id.btnView);
            tvStaus = view.findViewById(R.id.tvStatus);
            tvTableID = view.findViewById(R.id.tvTableID);
        }

        public void binding(Table table){
            if(table.getStatus().equals(Global.BOOKED)){
                tvStaus.setTextColor(ContextCompat.getColor(context, R.color.orange));
            }else {
                tvStaus.setTextColor(ContextCompat.getColor(context, R.color.green));
            }

            tvStaus.setText(table.getStatus()!=null ? table.getStatus() : "");
            tvTableID.setText(table.getTableID()!=null ? table.getTableID() : "");

            btnView.setOnClickListener(v -> {
                PopupMenu menu=new PopupMenu(context,v);
                menu.inflate(R.menu.table_menu);
                menu.setOnMenuItemClickListener(b->{
                    switch (b.getItemId()){
                        case R.id.btnView:
                            if(table.getStatus().equals(Global.BOOKED)){
                                callBack.onTableView(table.getTableID());
                            }else {
                                Toast.makeText(context, "No item order yet", Toast.LENGTH_SHORT).show();
                            }
                            return true;
                        case R.id.btnCreateOrder:
                            callBack.onOrder(table.getTableID());
                            return true;
                        default : return false;
                    }
                });
                menu.show();
            });
        }
    }

    public void addMoreReceipt(List<Table> tableList){
        this.tableList.addAll(tableList);
        notifyDataSetChanged();
    }

    interface CallBack{
        void onTableView(String ID);
        void onOrder(String id);
    }

    public CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public void remove() {
        this.tableList.clear();
        notifyDataSetChanged();
    }
}
