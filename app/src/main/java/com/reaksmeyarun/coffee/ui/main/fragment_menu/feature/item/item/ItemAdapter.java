package com.reaksmeyarun.coffee.ui.main.fragment_menu.feature.item.item;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.reaksmeyarun.coffee.R;
import com.reaksmeyarun.coffee.model.Global;
import com.reaksmeyarun.coffee.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> implements Filterable {

    List<Item> itemList;
    List<Item> itemListFiltered;
    Context context;
    View view;

    public ItemAdapter(List<Item> itemList, Context context) {
        this.itemList = itemList;
        this.itemListFiltered = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.custom_edit_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(holder instanceof ViewHolder){
            Item item = itemListFiltered.get(position);
            ((ViewHolder) holder).binding(item);
        }
    }

    @Override
    public int getItemCount() {
        return itemListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String key = constraint.toString();
                if (key.isEmpty()){
                    itemListFiltered = itemList;
                }else {
                    List<Item> lstItemList = new ArrayList<>();
                    for (Item item : itemList){
                        if(item.getItemCode().toLowerCase().contains(key.toLowerCase())){
                            lstItemList.add(item);
                        }
                    }
                    itemListFiltered = lstItemList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = itemListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                itemListFiltered = (List<Item>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvItemName, tvItemPrice, tvCodeID;
        ImageView btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemPrice = itemView.findViewById(R.id.tvItemPrice);
            tvCodeID = itemView.findViewById(R.id.tvCodeID);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

        public void binding(Item item){
            tvItemName.setText(item.getItemName() != null ? item.getItemName() : "");
            tvItemPrice.setText(String.valueOf(item.getPrice()) !=null ? String.valueOf(item.getPrice()) : "" );
            tvCodeID.setText(item.getItemCode() != null ? item.getItemCode() : "");

            btnDelete.setOnClickListener(v->{
                if(itemCallBack!=null){
                    PopupMenu menu=new PopupMenu(context,v);
                    menu.inflate(R.menu.edit_and_remove_menu);
                    menu.setOnMenuItemClickListener(b->{
                        switch (b.getItemId()){
                            case R.id.btnEdit:
                                itemCallBack.onEditItem(item,getAdapterPosition(), Global.ONEDIT_ITEM, Global.ONEDITITEM_TITLE);
                                return true;
                            case R.id.btnRemove:
                                new AlertDialog.Builder(context)
                                        .setMessage(R.string.alert_delete)
                                        .setTitle("Alert")
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        })
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                remove(getAdapterPosition());
                                                itemCallBack.onDeleteItem(item);
                                            }
                                        })
                                        .show();
                                return true;
                            default : return false;
                        }
                    });
                    menu.show();
                }
            });
        }
    }

    public void remove(int i) {
        this.itemList.remove(i);
        notifyDataSetChanged();
    }

    public interface ItemCallBack{
        void onEditItem(Item item, int position, String KEY, String TITLE);
        void onDeleteItem(Item item);
    }

    private ItemCallBack itemCallBack;

    public void setItemCallBack(ItemCallBack itemCallBack) {
        this.itemCallBack = itemCallBack;
    }
}
