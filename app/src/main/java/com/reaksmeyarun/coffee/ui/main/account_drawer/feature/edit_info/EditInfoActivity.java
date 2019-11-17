package com.reaksmeyarun.coffee.ui.main.account_drawer.feature.edit_info;

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

import com.reaksmeyarun.coffee.R;
import com.reaksmeyarun.coffee.model.Global;
import com.reaksmeyarun.coffee.model.User;
import com.reaksmeyarun.coffee.ui.main.account_drawer.AccountActivity;
import com.reaksmeyarun.coffee.ui.main.account_drawer.feature.edit_info.mvp.EditInfoMVP;
import com.reaksmeyarun.coffee.ui.main.account_drawer.feature.edit_info.mvp.EditInfoPresenter;

import java.util.ArrayList;
import java.util.List;

public class EditInfoActivity extends AppCompatActivity implements EditInfoMVP.View {

    private EditInfoMVP.Presenter presenter;
    private EditText etUsername;
    private TextView tvEmail;
    private Button btnSave;
    private ProgressBar progressBar;
    private Spinner spRule;
    private String newRule;
    private List<String> rule = new ArrayList<>();
    private User user;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        rule.add(Global.ADMIN_NODE);
        rule.add(Global.CASHIER_NODE);
        rule.add(Global.WAITER_NODE);

        onGetIntent();
        initUI();
        presenter = new EditInfoPresenter(this);
        setUpRuleSpinner(rule);
        setUpUI();
    }

    private void initUI() {
        progressBar = findViewById(R.id.progressBar);
        etUsername = findViewById(R.id.etUsername);
        tvEmail = findViewById(R.id.tvEmail);
        spRule = findViewById(R.id.spRule);
        btnSave = findViewById(R.id.btnSave);

    }

    private void setUpUI(){
        etUsername.setText(user.getUser());
        tvEmail.setText(user.getEmail());
        for (int i = 0; i < rule.size(); i++) {
            if (rule.get(i).equals(user.getRule())) {
                spRule.setSelection(i);
            }
        }

        spRule.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newRule = rule.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                newRule = rule.get(1);
            }
        });
        btnSave.setOnClickListener(v->{
            if(!validateForm()){
                return;
            }
            new AlertDialog.Builder(EditInfoActivity.this)
                    .setTitle("Edit Information")
                    .setIcon(R.drawable.ic_icons_dark_user)
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
                            presenter.onUpdateUser(new User(user.getAuthID(),
                                    etUsername.getText().toString(),
                                    user.getEmail(),
                                    newRule,
                                    Global.OFFLINE));
                        }
                    }).show();
        });
    }

    private void setUpRuleSpinner(List<String> rule){
        //setup Rule
        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,rule);
        spRule.setAdapter(arrayAdapter);

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
    public void onUpdateSuccess(String msg) {
        Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, AccountActivity.class));
        finish();
        overridePendingTransition(R.anim.open, R.anim.close);
    }

    @Override
    public void onUpdateFail(String msg) {
        Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, AccountActivity.class));
        finish();
        overridePendingTransition(R.anim.open, R.anim.close);
    }

    private void onGetIntent(){
        if(getIntent()!=null){
            user = getIntent().getParcelableExtra("user");
        }
    }
    private boolean validateForm(){
        boolean valid = true;

        if(TextUtils.isEmpty(etUsername.getText().toString())){
            etUsername.setError("!");
            valid = false;
        }else{
            etUsername.setError(null);
        }
        return valid;
    }
}
