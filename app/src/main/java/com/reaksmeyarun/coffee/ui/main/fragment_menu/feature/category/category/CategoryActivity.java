package com.reaksmeyarun.coffee.ui.main.fragment_menu.feature.category.category;

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
import com.reaksmeyarun.coffee.model.Category;
import com.reaksmeyarun.coffee.model.Global;
import com.reaksmeyarun.coffee.model.Item;
import com.reaksmeyarun.coffee.ui.main.MainActivity;
import com.reaksmeyarun.coffee.ui.main.fragment_menu.feature.category.category.mvp.CategoryMVP;
import com.reaksmeyarun.coffee.ui.main.fragment_menu.feature.category.category.mvp.CategoryPresenter;
import com.reaksmeyarun.coffee.ui.main.fragment_menu.feature.category.edit_category.EditCategoryActivity;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity implements CategoryMVP.View , CategoryAdapter.CategoryCallBack {

    CategoryMVP.Presenter presenter;
    CategoryAdapter categoryAdapter;

    private RecyclerView rvCategory;
    private EditText etSearch;
    private FloatingActionButton btnAddmore;
    List<Item> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        presenter = new CategoryPresenter(this);
        presenter.onGetItem();
        presenter.onGetCategory();

        initUI();
    }

    private void initUI(){

        rvCategory = findViewById(R.id.rvItem);
        etSearch = findViewById(R.id.etSearchCategory);
        btnAddmore = findViewById(R.id.btnAddMoreItem);

        btnAddmore.setOnClickListener(v->{
            Intent intent = new Intent(this, EditCategoryActivity.class);
            intent.putExtra("TITLE", Global.ONADDCATEGORY_TITLE);
            intent.putExtra("KEY", Global.ONADD_CATEGORY);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.open, R.anim.close);
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                categoryAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
    public void onCategorySuccess(List<Category> categoryList) {
        if(categoryList!=null){
            setUpRV(categoryList);
        }
    }

    @Override
    public void onCategoryFail(String msg) {
        Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSuccess(List<Item> itemList) {
        if(itemList!=null){
            this.itemList.addAll(itemList);
        }
    }

    @Override
    public void onItemFail(String msg) {
        Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteSuccess(String msg) {
        Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteFail(String msg) {
        Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
    }

    private void setUpRV(List<Category> categoryList){
        categoryAdapter = new CategoryAdapter(categoryList,this);
        categoryAdapter.setCategoryCallBack(this);
        rvCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvCategory.setAdapter(categoryAdapter);
    }

    @Override
    public void onEditCategory(Category category, int position) {
        Intent intent = new Intent(this, EditCategoryActivity.class);
        intent.putExtra("category", category);
        intent.putExtra("TITLE", Global.ONEDITCATEGORY_TITLE);
        intent.putExtra("KEY", Global.ONEDIT_CATEGORY);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.open, R.anim.close);
    }

    @Override
    public void onDeleteCategory(Category category) {
        if(category!=null && itemList!=null){
            presenter.onDeleteCategory(category, this.itemList);
        }else {
            Toast.makeText(this, "Something gone wrong", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
        overridePendingTransition(R.anim.open, R.anim.close);
    }
}
