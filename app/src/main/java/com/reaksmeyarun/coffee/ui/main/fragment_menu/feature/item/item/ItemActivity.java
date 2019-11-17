package com.reaksmeyarun.coffee.ui.main.fragment_menu.feature.item.item;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.reaksmeyarun.coffee.R;
import com.reaksmeyarun.coffee.model.Global;
import com.reaksmeyarun.coffee.model.Item;
import com.reaksmeyarun.coffee.ui.main.MainActivity;
import com.reaksmeyarun.coffee.ui.main.fragment_menu.feature.item.edit_item.EditItemActivity;
import com.reaksmeyarun.coffee.ui.main.fragment_menu.feature.item.item.mvp.ItemMVP;
import com.reaksmeyarun.coffee.ui.main.fragment_menu.feature.item.item.mvp.ItemPresenter;

import java.util.List;

public class ItemActivity extends AppCompatActivity implements ItemMVP.View, ItemAdapter.ItemCallBack{

    ItemMVP.Presenter presenter;
    ItemAdapter itemAdapter;

    private RecyclerView rvItem;
    private FloatingActionButton btnAddMore;
    private EditText etItemSearch;
    private List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_item);

        presenter = new ItemPresenter(this);
        if(itemList==null){
            presenter.onGetItem();
        }

        initUI();
    }

    private void initUI() {
        rvItem = findViewById(R.id.rvItem);
        etItemSearch = findViewById(R.id.etSearchCategory);
        btnAddMore = findViewById(R.id.btnAddMoreItem);

        btnAddMore.setOnClickListener(v->{
            Intent intent = new Intent(this, EditItemActivity.class);
            intent.putExtra("KEY", Global.ONADD_ITEM);
            intent.putExtra("TITLE", Global.ONADDITEM_TITLE);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.open, R.anim.close);
        });
        etItemSearch.addTextChangedListener(new TextWatcher() {
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

    private void setUpRv(List<Item> itemList){
        itemAdapter = new ItemAdapter(itemList, this);
        itemAdapter.setItemCallBack(this);
        rvItem.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvItem.setAdapter(itemAdapter);
    }

    @Override
    public void onShowLoading() {
        //TODO
    }

    @Override
    public void onHideLoading() {
        //TODO
    }

    @Override
    public void onGetItemSuccess(List<Item> itemList) {
        if(itemList!=null){
            this.itemList = itemList;
            setUpRv(itemList);
        }
    }

    @Override
    public void onGetItemFail(String msg) {
        Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteItemSuccess(String msg) {
        Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteItemFail(String msg) {
        Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEditItem(Item item, int position, String KEY, String TITLE) {
        Intent intent = new Intent(this, EditItemActivity.class);
        intent.putExtra("item", item);
        intent.putExtra("KEY", KEY);
        intent.putExtra("TITLE", TITLE);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.open, R.anim.close);
    }

    @Override
    public void onDeleteItem(Item item) {
        //TODO: delete
        if(item!=null){
            presenter.onItemDelete(item);
        }else {
            Toast.makeText(this, "Something gone wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
        overridePendingTransition(R.anim.open, R.anim.close);
    }
}
