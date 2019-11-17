package com.reaksmeyarun.coffee.ui.main.fragment_menu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.reaksmeyarun.coffee.R;
import com.reaksmeyarun.coffee.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private Context context;
    private List<Item> itemList = new ArrayList<>();
    private View view;

    public ItemAdapter(List<Item> itemList, Context context) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.custom_edit_item_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(holder instanceof ViewHolder){
            Item item = itemList.get(position);
            ((ViewHolder) holder).binding(item);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView mItemName, mItemPrice, mItemCodeID;
        ImageView btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mItemName = itemView.findViewById(R.id.tvItemName);
            mItemPrice = itemView.findViewById(R.id.tvItemPrice);
            mItemCodeID = itemView.findViewById(R.id.tvCodeID);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

        public void binding(Item item) {
            mItemName.setText(item.getItemName() != null ? item.getItemName() : "");
            mItemPrice.setText(String.valueOf(item.getPrice()) !=null ? String.valueOf(item.getPrice()) : "" );
            mItemCodeID.setText(item.getItemCode() != null ? item.getItemCode() : "");
            btnDelete.setVisibility(View.GONE);
        }
    }

    public void addMoreItem(List<Item> itemList){
        this.itemList.clear();
        this.itemList.addAll(itemList);
        notifyDataSetChanged();
    }
}
