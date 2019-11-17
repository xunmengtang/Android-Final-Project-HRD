package com.reaksmeyarun.coffee.ui.main.fragment_order.feature.receipt_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.reaksmeyarun.coffee.R;
import com.reaksmeyarun.coffee.model.Item;

import java.util.List;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.ViewHolder> {

    List<Item> itemList;
    Context context;
    View view;

    public ReceiptAdapter(List<Item> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.custom_on_view_receipt, parent, false);
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

        TextView tvItem, tvPrice, tvQuality, tvTotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = view.findViewById(R.id.tvItem);
            tvPrice = view.findViewById(R.id.tvPrice);
            tvQuality = view.findViewById(R.id.tvQuality);
            tvTotal = view.findViewById(R.id.tvTable);
        }
        public void binding(Item item){
            tvItem.setText(item.getItemName() != null ? item.getItemName() : "");
            tvPrice.setText(String.valueOf(item.getPrice())!= null ? String.valueOf(item.getPrice()) : "");
            tvQuality.setText(item.getQuaility() != null ? item.getQuaility() : "");
            tvTotal.setText(calulate(item));
        }
    }
    private String calulate(Item item){
        Double total;
        total = Double.parseDouble(item.getQuaility() )*item.getPrice();
        return String.format(total.toString());
    }
}
