package com.reaksmeyarun.coffee.ui.main.fragment_order.feature.create_order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
        view = LayoutInflater.from(context).inflate(R.layout.custom_order_item_view, parent, false);
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
                if(key.isEmpty()){
                    itemListFiltered = itemList;
                }else {
                    List<Item> lstItemList = new ArrayList<>();
                    for(Item item : itemList){
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

        TextView tvItemName, tvItemCode;
        ImageView btnAddTo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvItemCode = itemView.findViewById(R.id.tvItemCode);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            btnAddTo = itemView.findViewById(R.id.btnAddTo);
        }

        public void binding(Item item){
            tvItemName.setText(item.getItemName() != null ? item.getItemName() : "");
            tvItemCode.setText(item.getItemCode() != null ? item.getItemCode() : "");


            btnAddTo.setOnClickListener(v->{
                callBack.onCallBackAdd(item, Global.ONADDQUALITY_CARTORDER);
            });
        }
    }

    interface CallBack{
        void onCallBackAdd(Item item, String KEY);
    }

    private CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

}
