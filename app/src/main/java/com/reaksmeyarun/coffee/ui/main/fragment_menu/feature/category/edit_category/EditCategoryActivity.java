package com.reaksmeyarun.coffee.ui.main.fragment_menu.feature.category.edit_category;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.reaksmeyarun.coffee.ui.main.fragment_menu.feature.category.category.CategoryActivity;
import com.reaksmeyarun.coffee.ui.main.fragment_menu.feature.category.edit_category.mvp.EditCategoryMVP;
import com.reaksmeyarun.coffee.ui.main.fragment_menu.feature.category.edit_category.mvp.EditCategoryPresenter;

public class EditCategoryActivity extends AppCompatActivity implements EditCategoryMVP.View {

    private Button btnSave;
    private EditText mCategoryName;
    private TextView tvTitle;
    private ProgressBar progressBar;
    private Category category;
    private String KEY;
    private String TITLE;

    EditCategoryMVP.Presenter presenter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    static final String CATEGORY_NODE = "category";
    DatabaseReference categoryRef = database.getReference(CATEGORY_NODE);
    String key = categoryRef.child(CATEGORY_NODE).push().getKey();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_category);

        initUI();

        presenter = new EditCategoryPresenter(this);


    }
    private void initUI(){

        onGetIntent();

        mCategoryName = findViewById(R.id.etItemName);
        tvTitle = findViewById(R.id.btnMenu);
        btnSave = findViewById(R.id.btnSave);
        progressBar = findViewById(R.id.progressBarCategory);

        if(KEY.equals(Global.ONEDIT_CATEGORY)){
            tvTitle.setText(TITLE);
            mCategoryName.setText(category.getCategoryName());
        }else if(KEY.equals(Global.ONADD_CATEGORY)){
            tvTitle.setText(TITLE);
            mCategoryName.setText("");
        }

        btnSave.setOnClickListener(v->{
            new AlertDialog.Builder(EditCategoryActivity.this)
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
                            if(!validateForm()){
                                return;
                            }

                            if(KEY.equals(Global.ONEDIT_CATEGORY)){
                                presenter.onEditCategry(new Category(category.getCreateBy(),
                                        category.getId(),
                                        mCategoryName.getText().toString()
                                ));
                            }else if(KEY.equals(Global.ONADD_CATEGORY)){
                                presenter.onCreateCategory(
                                        new Category(
                                                FirebaseAuth.getInstance().getCurrentUser().getUid(),
                                                key,
                                                mCategoryName.getText().toString()));
                            }
                        }
                    }).show();
        });
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
    public void onEditCategorySuccess(String msg) {
        startActivity(new Intent(this, CategoryActivity.class));
        finish();
        overridePendingTransition(R.anim.open, R.anim.close);
        Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEditError(String msg) {
        Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(String msg) {
        startActivity(new Intent(this, CategoryActivity.class));
        finish();
        overridePendingTransition(R.anim.open, R.anim.close);
    }

    @Override
    public void onError(String msg) {
        Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
    }

    private void onGetIntent(){
        if(getIntent()!=null){
            category = getIntent().getParcelableExtra("category");
            KEY = getIntent().getStringExtra("KEY");
            TITLE = getIntent().getStringExtra("TITLE");
        }
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, CategoryActivity.class));
        finish();
        overridePendingTransition(R.anim.open, R.anim.close);
    }
    private boolean validateForm(){
        boolean valid = true;
        if(TextUtils.isEmpty(mCategoryName.getText().toString())){
            mCategoryName.setError("Fill the box");
            return false;
        }else {
            mCategoryName.setError(null);
        }
        return valid;
    }
}
