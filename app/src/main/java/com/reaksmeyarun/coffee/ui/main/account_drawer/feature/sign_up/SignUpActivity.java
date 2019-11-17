package com.reaksmeyarun.coffee.ui.main.account_drawer.feature.sign_up;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.reaksmeyarun.coffee.R;
import com.reaksmeyarun.coffee.model.User;
import com.reaksmeyarun.coffee.ui.main.account_drawer.AccountActivity;
import com.reaksmeyarun.coffee.ui.main.account_drawer.feature.sign_up.mvp.SignUpMVP;
import com.reaksmeyarun.coffee.ui.main.account_drawer.feature.sign_up.mvp.SignUpPresenter;
import com.reaksmeyarun.coffee.ui.sign_in.SignInActivity;

import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity implements SignUpMVP.View {

    SignUpMVP.Presenter presenter;

    private Button btnSignUp;
    private EditText etEmail, etPassword, etConPassword, etUsername;
    private Spinner spRule;
    private ProgressBar progressBar;
    private String currentRule;
    List<String> rule = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);

        rule.add("Cashier");
        rule.add("Waiter");
        rule.add("Admin");

        presenter = new SignUpPresenter(this);

        initUI();
    }

    private void initUI() {
        btnSignUp = findViewById(R.id.btnSave);
        etEmail = findViewById(R.id.tvEmail);
        etPassword = findViewById(R.id.etNewPassword);
        etConPassword = findViewById(R.id.etConNewPassword);
        etUsername = findViewById(R.id.etUsername);
        spRule = findViewById(R.id.spRule);
        progressBar = findViewById(R.id.progressBar);

        setUpSpinner(rule);

        spRule.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentRule = rule.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                currentRule = rule.get(3);
            }
        });

        btnSignUp.setOnClickListener(v -> {
            if(!validateForm()){
                return;
            }
            if(etPassword.getText().toString().equals(etConPassword.getText().toString())) {
                new AlertDialog.Builder(SignUpActivity.this)
                        .setTitle("Sign Up")
                        .setMessage("\nAre you sure?")
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                presenter.onSignUp(new User(
                                        "",
                                        etUsername.getText().toString(),
                                        etEmail.getText().toString(),
                                        etPassword.getText().toString(),
                                        currentRule
                                ));
                            }
                        }).show();
            }else {
                etConPassword.setError("Miss match!");
                etPassword.setError("Miss match!");
            }
        });

    }

    private boolean validateForm(){
        boolean valid = true;

        if(TextUtils.isEmpty(etEmail.getText().toString())){
            etEmail.setError("!");
            valid = false;
        }
        else if(TextUtils.isEmpty(etUsername.getText().toString())){
            etUsername.setError("!");
            valid = false;
        }
        else if(etPassword.getText().toString().length()<6){
            etPassword.setError("At least 6 characters");
            valid = false;
        }
        else if(etConPassword.getText().toString().length()<6){
            etConPassword.setError("At least 6 characters");
            valid = false;
        }else{
            etPassword.setError(null);
            etConPassword.setError(null);
            etEmail.setError(null);
        }
        return valid;
    }
    private void setUpSpinner(List<String> rule){
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
    public void onSignUpSuccess(String msg) {
        SharedPreferences sharedPreferences = getSharedPreferences("save", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("remember","false");
        editor.apply();
        //Log out firebaseAuth
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, SignInActivity.class));
        finish();
        overridePendingTransition(R.anim.open, R.anim.close);
    }

    @Override
    public void onSignUpFailure(String msg) {
        Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
            startActivity(new Intent(SignUpActivity.this, AccountActivity.class));
            finish();
            overridePendingTransition(R.anim.open, R.anim.close);
    }
}
