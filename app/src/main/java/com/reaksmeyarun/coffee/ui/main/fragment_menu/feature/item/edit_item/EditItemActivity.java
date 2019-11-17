package com.reaksmeyarun.coffee.ui.main.fragment_menu.feature.item.edit_item;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.reaksmeyarun.coffee.R;
import com.reaksmeyarun.coffee.model.Category;
import com.reaksmeyarun.coffee.model.Global;
import com.reaksmeyarun.coffee.model.Item;
import com.reaksmeyarun.coffee.ui.main.fragment_menu.feature.item.edit_item.mvp.EditItemMVP;
import com.reaksmeyarun.coffee.ui.main.fragment_menu.feature.item.edit_item.mvp.EditItemPresenter;
import com.reaksmeyarun.coffee.ui.main.fragment_menu.feature.item.item.ItemActivity;

import java.util.ArrayList;
import java.util.List;

public class EditItemActivity extends AppCompatActivity implements EditItemMVP.View {

    private ProgressBar progressBar;
    private Spinner spCategory;
    private Button btnSave;
    private TextView tvTitle;
    private EditText mProductName,
            mCodeID,
            mCost,
            mPrice;

    private EditItemMVP.Presenter presenter;
    private Item item;
    private String positionCate;
    private String KEY;
    private String title;

    List<String> categoryName = new ArrayList<>();
    List<Category> categoryList = new ArrayList<>();
    ArrayAdapter<String> categoryAdapter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    static final String ITEM_NODE = "item";
    DatabaseReference categoryRef = database.getReference(ITEM_NODE);
    String key = categoryRef.child(ITEM_NODE).push().getKey();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_item);

        initUI();

        onGetIntent();

        presenter = new EditItemPresenter(this);
        presenter.onGetCategory();

    }

    @Override
    public void onBackPressed() {
       startActivity(new Intent(this,ItemActivity.class));
       finish();
       overridePendingTransition(R.anim.open, R.anim.close);
    }

    @Override
    public void onShowLoading() {
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void onHideLoading() {
        progressBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void onCategorySuccess(List<Category> categoryList) {
            if(categoryList!=null){
                this.categoryList = categoryList;
                if(KEY.equals(Global.ONEDIT_ITEM)){
                    for(Category category : categoryList){
                        categoryName.add(category.getCategoryName());
                    }
                    setUpSpinner(categoryName);
                    setUpUI(categoryList);
                }else if(KEY.equals(Global.ONADD_ITEM)){
                    for(Category category : categoryList){
                        categoryName.add(category.getCategoryName());
                    }
                    tvTitle.setText(title);
                    setUpSpinner(categoryName);
                }
            }else {
                Toast.makeText(this, "Something wrong", Toast.LENGTH_SHORT).show();
            }
    }

    @Override
    public void onError(String msg) {
        Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateItemSuccess() {
        Toast.makeText(this, "Update successful!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, ItemActivity.class));
        finish();
        overridePendingTransition(R.anim.open, R.anim.close);
    }

    @Override
    public void onPushItemSuccess(String msg) {
        Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, ItemActivity.class));
        finish();
        overridePendingTransition(R.anim.open, R.anim.close);
    }

    @Override
    public void onPushItemFail(String msg) {
        Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
    }

    private void initUI(){
        mProductName = findViewById(R.id.etItemName);
        mCodeID = findViewById(R.id.etCodeID);
        mCost = findViewById(R.id.etCost);
        mPrice = findViewById(R.id.etPrice);
        btnSave = findViewById(R.id.btnSave);
        spCategory = findViewById(R.id.spCategory);
        progressBar = findViewById(R.id.progressBar);
        tvTitle = findViewById(R.id.btnMenu);

        btnSave.setOnClickListener(v->{
            new AlertDialog.Builder(EditItemActivity.this)
                    .setTitle("Alert")
                    .setMessage(R.string.alert_save)
                    .setNegativeButton("No",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(KEY.equals(Global.ONADD_ITEM)){
                                if(!validateForm()){
                                    return;
                                }
                                progressBar.setVisibility(View.VISIBLE);
                                presenter.onPushItem(
                                        new Item(key,
                                                FirebaseAuth.getInstance().getCurrentUser().getUid(),
                                                mProductName.getText().toString(),
                                                mCodeID.getText().toString(),
                                                positionCate,
                                                Double.parseDouble(mPrice.getText().toString()),
                                                Double.parseDouble(mCost.getText().toString())
                                        ));
                            }else if(KEY.equals(Global.ONEDIT_ITEM)){
                                presenter.onUpdateItem(
                                        new Item(
                                                item.getId(),
                                                item.getCreateBy(),
                                                mProductName.getText().toString(),
                                                mCodeID.getText().toString(),
                                                positionCate,
                                                Double.parseDouble(mPrice.getText().toString()),
                                                Double.parseDouble(mCost.getText().toString())
                                        ));
                            }
                        }
                    }).show();
        });

        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                positionCate = categoryList.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                positionCate = categoryList.get(0).getId();
            }
        });
    }

    private void setUpSpinner(List<String> categoryName){
        categoryAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,categoryName);
        spCategory.setAdapter(categoryAdapter);
    }

    private void onGetIntent(){
        if(getIntent()!=null){
            item = getIntent().getParcelableExtra("item");
            KEY = getIntent().getStringExtra("KEY");
            title = getIntent().getStringExtra("TITLE");
        }
    }

    private void setUpUI(List<Category> categories){
        if(item!=null){
            tvTitle.setText(title);
            mProductName.setText(item.getItemName());
            mCodeID.setText(item.getItemCode());
            mCost.setText(String.valueOf(item.getCost()));
            mPrice.setText(String.valueOf(item.getPrice()));
            for (int i = 0; i < categories.size(); i++) {
                if (categories.get(i).getId().equals(item.getCategoryID())) {
                    spCategory.setSelection(i);
                }
            }
        }
    }
    private boolean validateForm(){
        boolean valid = true;

        if(TextUtils.isEmpty(mProductName.getText().toString())){
            mProductName.setError("!");
            valid = false;
        }else if(TextUtils.isEmpty(mCodeID.getText().toString())){
            mCodeID.setError("!");
            valid = false;
        }else if(TextUtils.isEmpty(mCost.getText().toString())){
            mCost.setError("!");
            valid = false;
        }else if(TextUtils.isEmpty(mPrice.getText().toString())){
            mPrice.setError("!");
            valid = false;
        }else if(mCodeID.getText().toString().length()!=6){
            mCodeID.setError("(Six characters)");
            valid = false;
        }else if(Double.parseDouble(String.valueOf(mPrice.getText())) <= Double.parseDouble(String.valueOf(mCost.getText()))){
            mPrice.setError("Price must be higher!");
            mCost.setError("!");
            valid = false;
        }else{
            mProductName.setError(null);
            mCodeID.setError(null);
            mCost.setError(null);
            mPrice.setError(null);
        }
        return valid;
    }
}
