package com.reaksmeyarun.coffee.ui.main.fragment_order.feature.receipt_view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.reaksmeyarun.coffee.R;
import com.reaksmeyarun.coffee.model.Item;
import com.reaksmeyarun.coffee.model.Receipt;
import com.reaksmeyarun.coffee.model.User;
import com.reaksmeyarun.coffee.ui.main.MainActivity;
import com.reaksmeyarun.coffee.ui.main.fragment_order.feature.receipt_view.mvp.ReceiptMVP;
import com.reaksmeyarun.coffee.ui.main.fragment_order.feature.receipt_view.mvp.ReceiptPresenter;

import java.util.List;

public class ReceiptActivity extends AppCompatActivity implements ReceiptMVP.View {

    ReceiptMVP.Presenter presenter;
    private String id;
    private ReceiptAdapter receiptAdapter;
    private Receipt receipt;

    private ProgressBar progressBar;
    private RecyclerView rvItem;
    private TextView tvID, tvCreateDate, tvSubTotal, tvCreateBy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        initUI();
        onGetIntent();

        presenter = new ReceiptPresenter(this);
        presenter.onGetReceipt(id);

    }

    private void initUI() {
        progressBar = findViewById(R.id.progressBar);
        rvItem = findViewById(R.id.rvItem);
        tvID = findViewById(R.id.tvTableID);
        tvCreateDate = findViewById(R.id.tvCreateDate);
        tvSubTotal = findViewById(R.id.tvTable);
        tvCreateBy = findViewById(R.id.tvCreateBy);
    }

    private void setUpUI(Receipt receipt, User user) {
        tvID.setText(receipt.getId()!=null ? receipt.getId() : "");
        tvCreateDate.setText(receipt.getCreateDate()!=null ? receipt.getCreateDate() : "");
        tvSubTotal.setText(receipt.getTotal()!=null ? receipt.getTotal() : "");
        tvCreateBy.setText(user.getUser()!=null ? user.getUser() : "");
    }

    @Override
    public void onShowLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onGetReceitSuccessful(Receipt receipt) {
        if(receipt!=null){
            this.receipt = receipt;
            setUpRV(receipt.getItemID());
            presenter.onGetCreateBy(receipt.getCreateBy());
        }else {
            this.receipt = null;
        }
    }

    @Override
    public void onGetReceiptFailure(String msg) {
        Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetCreateBySuccess(User user) {
        setUpUI(receipt, user);
    }

    @Override
    public void onGetCreateByFailure(String msg) {
        Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
    }

    private void onGetIntent(){
        if(getIntent()!=null){
            id = getIntent().getStringExtra("ID");
        }
    }

    private void setUpRV(List<Item> itemList){
        receiptAdapter = new ReceiptAdapter(itemList, this);
        rvItem.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvItem.setAdapter(receiptAdapter);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
        overridePendingTransition(R.anim.open,R.anim.close);
    }
}
