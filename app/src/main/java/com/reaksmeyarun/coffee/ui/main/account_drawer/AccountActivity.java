package com.reaksmeyarun.coffee.ui.main.account_drawer;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.reaksmeyarun.coffee.R;
import com.reaksmeyarun.coffee.model.User;
import com.reaksmeyarun.coffee.ui.main.MainActivity;
import com.reaksmeyarun.coffee.ui.main.account_drawer.feature.edit_info.EditInfoActivity;
import com.reaksmeyarun.coffee.ui.main.account_drawer.feature.sign_up.SignUpActivity;
import com.reaksmeyarun.coffee.ui.main.account_drawer.feature.view_account_dialog.AccountViewDialog;
import com.reaksmeyarun.coffee.ui.main.account_drawer.mvp.AccountMVP;
import com.reaksmeyarun.coffee.ui.main.account_drawer.mvp.AccountPresenter;

import java.util.ArrayList;
import java.util.List;


public class AccountActivity extends AppCompatActivity implements AccountMVP.View, AccountAdapter.AccCallBack {

    AccountMVP.Presenter presenter;

    private AccountAdapter accountAdapter;
    private List<User> userList = new ArrayList<>();
    private RecyclerView rvAccount;
    private EditText etSearch;
    private ProgressBar progressBar;
    private FloatingActionButton btnCreateNewAcc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        iniUI();
        setUpRV(userList);

        presenter = new AccountPresenter(this);
        presenter.onGetUser();
    }

    private void iniUI() {
        rvAccount = findViewById(R.id.rvAccount);
        btnCreateNewAcc = findViewById(R.id.btnCreateNewAcc);
        progressBar = findViewById(R.id.progressBar);
        etSearch = findViewById(R.id.etSearchAcc);

        btnCreateNewAcc.setOnClickListener(v->{
            startActivity(new Intent(this, SignUpActivity.class));
            finish();
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                accountAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setUpRV(List<User> userList){
        accountAdapter = new AccountAdapter(userList,this);
        rvAccount.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvAccount.setAdapter(accountAdapter);
        accountAdapter.setAccCallBack(this);
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
    public void onGetSuccess(List<User> userList) {
        if(userList!=null){
            accountAdapter.addMoreUser(userList);
        }
    }

    @Override
    public void onGetFail(String msg) {
        Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
        overridePendingTransition(R.anim.open, R.anim.close);
    }

    @Override
    public void onEdit(User user) {
        Intent intent = new Intent(this, EditInfoActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    @Override
    public void onView(User user) {
        AccountViewDialog accountViewDialog = new AccountViewDialog(this,user);
        accountViewDialog.customDialog();
    }
}
