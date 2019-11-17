package com.reaksmeyarun.coffee.ui.main.fragment_order.feature.create_order;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.reaksmeyarun.coffee.R;
import com.reaksmeyarun.coffee.model.Global;
import com.reaksmeyarun.coffee.model.Item;
import com.reaksmeyarun.coffee.ui.main.MainActivity;
import com.reaksmeyarun.coffee.ui.main.fragment_order.feature.cart_view.ViewOrderActivity;
import com.reaksmeyarun.coffee.ui.main.fragment_order.feature.create_order.mvp.CreateOrderMVP;
import com.reaksmeyarun.coffee.ui.main.fragment_order.feature.create_order.mvp.CreateOrderPresenter;
import com.reaksmeyarun.coffee.ui.main.fragment_order.feature.quality.QualityActivity;

import java.util.List;

public class CreateOrderActivity extends AppCompatActivity implements CreateOrderMVP.View, ItemAdapter.CallBack {

    CreateOrderMVP.Presenter presenter;
    ItemAdapter itemAdapter;

    private RecyclerView rvItem;
    private FloatingActionButton btnCart;
    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_order);

        presenter = new CreateOrderPresenter(this);
        presenter.onGetItem();


        initUI();
    }

    private void initUI(){
        rvItem = findViewById(R.id.rvItem);
        btnCart = findViewById(R.id.btnCreateOrder);
        etSearch = findViewById(R.id.etSearch);

        btnCart.setOnClickListener(v->{
            if(Global.itemList.size()!=0){
                Intent intent = new Intent(CreateOrderActivity.this, ViewOrderActivity.class);
//                intent.putExtra("VIEW", "ORDER");
//                intent.putExtra("ID", Global.getCurrentTable());
                Global.view = "ORDER";
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.open, R.anim.close);
            }else {
                Toast.makeText(this, "Please Take Order First", Toast.LENGTH_SHORT).show();
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                itemAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onShowLoading() {

    }

    @Override
    public void onHideLoading() {

    }

    @Override
    public void onGetItemSuccess(List<Item> itemList) {
        if(itemList!=null){
            setUpRV(itemList);
        }
    }

    @Override
    public void onGetItemFail(String msg) {
        Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
    }

    private void setUpRV(List<Item> itemList){
        itemAdapter = new ItemAdapter(itemList, this);
        rvItem.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvItem.setAdapter(itemAdapter);
        itemAdapter.setCallBack(this);
    }

    @Override
    public void onBackPressed() {
        if(Global.itemList.size()!=0) {
            new AlertDialog.Builder(CreateOrderActivity.this)
                    .setTitle("Alert")
                    .setMessage(R.string.alert_close)
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (Global.itemList != null) {
                                Global.itemList.clear();
                            }
                            newActivity();
                        }
                    }).show();
        }else {
            newActivity();
        }
    }

    private void newActivity(){
        startActivity(new Intent(CreateOrderActivity.this, MainActivity.class));
        finish();
        overridePendingTransition(R.anim.open, R.anim.close);
    }
    @Override
    public void onCallBackAdd(Item item, String KEY) {
        Intent intent = new Intent(this, QualityActivity.class);
        intent.putExtra("item",item);
        intent.putExtra("quality",item.getQuaility());
        intent.putExtra("KEY",KEY);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.open, R.anim.close);
    }
}
