package com.reaksmeyarun.coffee.ui.main.fragment_order.feature.cart_view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.reaksmeyarun.coffee.R;
import com.reaksmeyarun.coffee.model.Global;
import com.reaksmeyarun.coffee.model.Item;
import com.reaksmeyarun.coffee.model.Receipt;
import com.reaksmeyarun.coffee.model.Table;
import com.reaksmeyarun.coffee.ui.main.MainActivity;
import com.reaksmeyarun.coffee.ui.main.fragment_order.feature.cart_view.mvp.ViewOrderMVP;
import com.reaksmeyarun.coffee.ui.main.fragment_order.feature.cart_view.mvp.ViewOrderPresenter;
import com.reaksmeyarun.coffee.ui.main.fragment_order.feature.create_order.CreateOrderActivity;
import com.reaksmeyarun.coffee.ui.main.fragment_order.feature.quality.QualityActivity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ViewOrderActivity extends AppCompatActivity implements ViewOrderMVP.View, CartViewOrderAdapter.CartOrderCallBack  {

    private final String RECEIPT_NODE = "receipt";

    private FirebaseDatabase firebaseDatabase =  FirebaseDatabase.getInstance();
    private DatabaseReference receiptReference = firebaseDatabase.getReference(RECEIPT_NODE);
    String key = receiptReference.child(RECEIPT_NODE).push().getKey();
    Date sortdate = new Date();
    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    private RecyclerView rvCartOrder;
    private FloatingActionButton btnSend;
    private TextView tvTotal;
    private ConstraintLayout constraintLayout;
    private CartViewOrderAdapter cartViewOrderAdapter;
    private ViewOrderMVP.Presenter presenter;
    private String id;
    List<Item> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);
        initUI();
        setUpRV(itemList);
        presenter = new ViewOrderPresenter(this);
        presenter.onGetTable(Global.currentTable);
    }

    private void initUI() {
        rvCartOrder = findViewById(R.id.rvOrderItem);
        btnSend = findViewById(R.id.btnSend);
        tvTotal = findViewById(R.id.tvTotal);
        constraintLayout = findViewById(R.id.layoutBottom);

        if (Global.view.equals("VIEW")) {
            btnSend.setImageDrawable(getApplicationContext().getDrawable(R.drawable.ic_receipt_black_24dp));
            if (Global.user.getRule().equals(Global.WAITER_NODE)) {
                btnSend.hide();
            } else {
                btnSend.show();
            }
        }

        btnSend.setOnClickListener(v->{
            if(Global.itemList!=null){
                if(Global.view.equals("VIEW")){
                    onCheckUp("\nDo you want to pay now, are you sure?\n", itemList);
                }else {
                    if(itemList!=null){
                        itemList.addAll(Global.itemList);
                    }else {
                        itemList=Global.itemList;
                    }
                    onCheckUp("\nDo you want to save this, are you sure?\n", itemList);
                }
            }
        });
    }

    private void onCheckUp(String msg, List<Item> items){
        new AlertDialog.Builder(ViewOrderActivity.this)
                .setTitle("Alert")
                .setMessage(msg)
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(Global.view.equals("VIEW")){
                            if(items.size()==0){
                                return;
                            }
                            presenter.onPushReceipt(Global.currentTable, new Receipt(
                                            key,
                                            date,
                                            sortdate.getTime(),
                                            FirebaseAuth.getInstance().getCurrentUser().getUid(),
                                            items,
                                            sum(items))
                            );
                        }else {
                            presenter.onPushTable(new Table(Global.currentTable,
                                    Global.BOOKED,
                                    items
                            ));
                        }
                    }
                }).show();
    }

    private void setUpRV(List<Item> itemList){
        if(Global.itemList!=null){
            cartViewOrderAdapter = new CartViewOrderAdapter(this, itemList);
            rvCartOrder.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            rvCartOrder.setAdapter(cartViewOrderAdapter);
            cartViewOrderAdapter.setCartOrderCallBack(this);
        }else {
            Toast.makeText(this, "No item order", Toast.LENGTH_SHORT).show();
        }
    }

    private String sum(List<Item> itemList){
        double total = 0;
        double eachSum ;
        if(itemList!=null){
            for(Item item : itemList){
                eachSum = item.getPrice()* Double.parseDouble( item.getQuaility());
                total = total+eachSum;
            }
        }
        return new DecimalFormat("#.0#").format(total);
    }

    @Override
    public void onBackPressed() {
        if(Global.view.equals("VIEW")){
            startActivity(new Intent(this, MainActivity.class));
            finish();
            overridePendingTransition(R.anim.open, R.anim.close);
        }else {
            startActivity(new Intent(this, CreateOrderActivity.class));
            finish();
            overridePendingTransition(R.anim.open, R.anim.close);
        }
    }

    @Override
    public void onShowLoading() {
        btnSend.setEnabled(false);
    }

    @Override
    public void onHideLoading() {
        btnSend.setEnabled(true);
    }

    @Override
    public void onReceiptSuccess(String msg) {
        Global.itemList.clear();
        Global.currentTable="";
        Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();
        overridePendingTransition(R.anim.open, R.anim.close);
    }

    @Override
    public void onReceiptFail(String msg) {
        Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPushTableSuccess(String msg) {
        Global.itemList.clear();
        Global.currentTable="";
        Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();
        overridePendingTransition(R.anim.open, R.anim.close);
    }

    @Override
    public void onPushTableFail(String msg) {
        Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetTableSuccess(Table table) {
        if(Global.view.equals("VIEW")){
            if(table.getItemList()!=null){
                this.itemList=table.getItemList();
                tvTotal.setText(sum(itemList));
                constraintLayout.setVisibility(View.VISIBLE);
//                this.itemList.addAll(table.getItemList());
                cartViewOrderAdapter.itemOrder.clear();
                cartViewOrderAdapter.addMoreReceipt(table.getItemList());
            }else {
                this.itemList=null;
            }
        }else {
            constraintLayout.setVisibility(View.GONE);
            if(table.getItemList()!=null){
                this.itemList = table.getItemList();
            }else {
                this.itemList=null;
            }
            cartViewOrderAdapter.itemOrder.clear();
            cartViewOrderAdapter.addMoreReceipt(Global.itemList);
        }
    }

    @Override
    public void onGetTableFail(String msg) {

    }

    @Override
    public void onEdit(Item item, String Key,int position) {
        if(item!=null){
            Intent intent = new Intent(this, QualityActivity.class);
            intent.putExtra("ID", id);
            intent.putExtra("item",item);
            intent.putExtra("position",position);
            intent.putExtra("quality",item.getQuaility());
            intent.putExtra("KEY",Key);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.open, R.anim.close);
        }
    }

    @Override
    public void onDelete(Item item, String Key) {

    }
}
