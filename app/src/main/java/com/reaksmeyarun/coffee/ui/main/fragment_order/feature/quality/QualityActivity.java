package com.reaksmeyarun.coffee.ui.main.fragment_order.feature.quality;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.reaksmeyarun.coffee.R;
import com.reaksmeyarun.coffee.model.Global;
import com.reaksmeyarun.coffee.model.Item;
import com.reaksmeyarun.coffee.ui.main.fragment_order.feature.cart_view.ViewOrderActivity;
import com.reaksmeyarun.coffee.ui.main.fragment_order.feature.create_order.CreateOrderActivity;

import java.util.ArrayList;
import java.util.List;

public class QualityActivity extends AppCompatActivity {

    private Item item;
    private Item currentItem;
    private List<Item> currentItemListForAdd = new ArrayList<>();
    private int position;
    private String quality;
    private String KEY;
    private String id;

    private TextView tvItemName, tvItemPrice, tvItemCode;
    private TextView etQuality;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quality);

        onGetIntent();

        initUI();
    }

    private void initUI() {
        tvItemCode = findViewById(R.id.tvItemCode);
        tvItemName = findViewById(R.id.tvItemName);
        tvItemPrice = findViewById(R.id.tvPrice);
        etQuality = findViewById(R.id.etQualities);
        btnSave = findViewById(R.id.btnSaveEdit);

        tvItemCode.setText(item.getItemCode());
        tvItemName.setText(item.getItemName());
        tvItemPrice.setText(String.format(item.getPrice().toString()));
        if(this.KEY.equals(Global.ONEDIT_CARTORDER)){
            etQuality.setText(quality);
        }else if(this.KEY.equals(Global.ONADDQUALITY_CARTORDER)){
            etQuality.setText("1");
        }
        btnSave.setOnClickListener(v->{
            if(this.KEY.equals(Global.ONEDIT_CARTORDER)){
                currentItem = new Item(
                        item.getId(),
                        item.getCreateBy(),
                        item.getItemName(),
                        item.getItemCode(),
                        item.getCategoryID(),
                        item.getPrice(),
                        item.getCost(),
                        etQuality.getText().toString());
                Global.itemList.set(position,currentItem);
                Toast.makeText(this, "Edit Successful", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(this, ViewOrderActivity.class));
                Intent intent = new Intent(this, ViewOrderActivity.class);
                intent.putExtra("ID",id);
                intent.putExtra("VIEW","EDIT");
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.open, R.anim.close);
            }else if (this.KEY.equals(Global.ONADDQUALITY_CARTORDER)) {
//                currentItemListForAdd.add(new Item(
//                        item.getId(),
//                        item.getCreateBy(),
//                        item.getItemName(),
//                        item.getItemCode(),
//                        item.getCategoryID(),
//                        item.getPrice(),
//                        item.getCost(),
//                        etQuality.getText().toString()
//                ));
                Global.itemList.add(new Item(
                        item.getId(),
                        item.getCreateBy(),
                        item.getItemName(),
                        item.getItemCode(),
                        item.getCategoryID(),
                        item.getPrice(),
                        item.getCost(),
                        etQuality.getText().toString()
                ));
                Toast.makeText(this, ""+item.getItemName(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, CreateOrderActivity.class));
                finish();
                overridePendingTransition(R.anim.open, R.anim.close);
            }
        });

    }

    private void onGetIntent(){
        Intent intent = getIntent();
        item = intent.getParcelableExtra("item");
        position = intent.getIntExtra("position",0);
        quality = intent.getStringExtra("quality");
        KEY = intent.getStringExtra("KEY");
        id = intent.getStringExtra("ID");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(this.KEY.equals(Global.ONEDIT_CARTORDER)){
            startActivity(new Intent(this, ViewOrderActivity.class));
            finish();
            overridePendingTransition(R.anim.open, R.anim.close);
        }else {
            startActivity(new Intent(this, CreateOrderActivity.class));
            finish();
            overridePendingTransition(R.anim.open, R.anim.close);
        }
    }
}
