package com.reaksmeyarun.coffee.ui.main.fragment_order.feature.cart_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.reaksmeyarun.coffee.R;
import com.reaksmeyarun.coffee.model.Global;
import com.reaksmeyarun.coffee.model.Item;

import java.util.List;

public class CartViewOrderAdapter extends RecyclerView.Adapter<CartViewOrderAdapter.ViewHolder>{

    Context context;
    List<Item> itemOrder;
    View view;


    public CartViewOrderAdapter(Context context, List<Item> itemOrder) {
        this.context = context;
        this.itemOrder = itemOrder;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.custom_order_cart_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(holder instanceof ViewHolder){
            Item item = itemOrder.get(position);
            ((ViewHolder) holder).binding(item);
        }
    }

    @Override
    public int getItemCount() {
        return itemOrder.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvItemName, tvItemQuality, tvItemID;
        ImageView btnEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvItemName = view.findViewById(R.id.tvItemName);
            tvItemQuality = view.findViewById(R.id.tvItemQuality);
            tvItemID = view.findViewById(R.id.tvItemCodeID);
            btnEdit = view.findViewById(R.id.btnEdit);
        }

        public void binding(Item item){

            if(Global.view.equals("VIEW")){
                btnEdit.setVisibility(View.GONE);
            }else {
                btnEdit.setVisibility(View.VISIBLE);
            }

            tvItemName.setText(item.getItemName()!= null ? item.getItemName(): "");
            tvItemID.setText(item.getItemCode()!=null ? item.getItemCode() : "");
            tvItemQuality.setText(item.getQuaility()!= null ? item.getQuaility(): "");

            btnEdit.setOnClickListener(v->{
                PopupMenu menu=new PopupMenu(context,v);
                menu.inflate(R.menu.edit_and_remove_menu);
                menu.setOnMenuItemClickListener(b->{
                    switch (b.getItemId()){
                        case R.id.btnEdit:
                            cartOrderCallBack.onEdit(item, Global.ONEDIT_CARTORDER,getAdapterPosition());
                            return true;
                        case R.id.btnRemove:
                            remove(item,getAdapterPosition());
                            cartOrderCallBack.onDelete(item, Global.ONDELETE_CARTORDER);
                            return true;
                        default : return false;
                    }
                });
                menu.show();
            });
        }
    }

    interface CartOrderCallBack{
        void onEdit(Item item, String KEY, int position);
        void onDelete(Item item, String KEY);
    }

    private CartOrderCallBack cartOrderCallBack;

    public void setCartOrderCallBack(CartOrderCallBack cartOrderCallBack) {
        this.cartOrderCallBack = cartOrderCallBack;
    }

    public void remove(Item item, int pos) {
        this.itemOrder.remove(item);
        notifyItemRemoved(pos);
    }

    public void addMoreReceipt(List<Item> itemList){
        if(itemList==null){
            return;
        }else {
            this.itemOrder.addAll(itemList);
            notifyDataSetChanged();
        }
    }
}
