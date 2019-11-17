package com.reaksmeyarun.coffee.ui.main.fragment_receipt;

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
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.reaksmeyarun.coffee.R;
import com.reaksmeyarun.coffee.model.Receipt;

import java.util.ArrayList;
import java.util.List;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.ViewHolder> implements Filterable {

    View view;
    Context context;
    List<Receipt> receiptList;
    List<Receipt> receiptListFiltered;

    public ReceiptAdapter(Context context, List<Receipt> receiptList) {
            this.context = context;
            this.receiptList = receiptList;
            this.receiptListFiltered = receiptList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.custom_receipt_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(holder instanceof ViewHolder){
            Receipt receipt = receiptListFiltered.get(position);
            ((ViewHolder) holder).binding(receipt);
        }
    }

    @Override
    public int getItemCount() {
        return receiptListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String key = constraint.toString();
                if(key.isEmpty()){
                    receiptListFiltered = receiptList;
                }else {
                    List<Receipt> lstFilered = new ArrayList<>();
                    for(Receipt receipt : receiptList){
                        if(receipt.getId().toLowerCase().contains(key.toLowerCase()) || receipt.getCreateDate().toLowerCase().contains(key.toLowerCase())||String.valueOf(receipt.getSortDate()).toLowerCase().contains(key.toLowerCase())){
                            lstFilered.add(receipt);
                        }
                    }
                    receiptListFiltered = lstFilered;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = receiptListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                receiptListFiltered = (List<Receipt>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvReceiptID, tvCreateDate, tvTotal, tvTotalCon;
        ImageView btnView;
        CardView cardView;
        ConstraintLayout constraint;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCreateDate = view.findViewById(R.id.tvReceiptCreateDate);
            tvReceiptID = view.findViewById(R.id.tvTableID);
            tvTotal = view.findViewById(R.id.tvTable);
            btnView = view.findViewById(R.id.btnView);
            tvTotalCon = view.findViewById(R.id.tv);
            cardView = view.findViewById(R.id.cardView);
            constraint = view.findViewById(R.id.constraint);
        }

        public void binding(Receipt receipt){
            tvReceiptID.setText(receipt.getId()!=null ? receipt.getId() : "");
            tvCreateDate.setText(receipt.getCreateDate()!=null ? receipt.getCreateDate() : "");
            tvTotal.setText(receipt.getTotal()!=null ? receipt.getTotal() : "");

            btnView.setOnClickListener(v -> {
                PopupMenu menu=new PopupMenu(context,v);
                menu.inflate(R.menu.receipt_menu);
                menu.setOnMenuItemClickListener(b->{
                    switch (b.getItemId()){
                        case R.id.btnView:
                            callBack.onReceiptView(receipt.getId());
                            return true;
                        default : return false;
                    }
                });
                menu.show();
            });
        }
    }

    public void addMoreReceipt(List<Receipt> receiptList){
        this.receiptList.addAll(receiptList);
        notifyDataSetChanged();
    }

    interface CallBack{
        void onReceiptView(String ID);
    }

    public CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public void remove() {
        this.receiptList.clear();
        notifyDataSetChanged();
    }
}
