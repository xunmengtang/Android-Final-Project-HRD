package com.reaksmeyarun.coffee.ui.main.account_drawer.feature.change_password;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.reaksmeyarun.coffee.R;
import com.reaksmeyarun.coffee.ui.sign_in.SignInActivity;
import com.reaksmeyarun.coffee.ui.main.MainActivity;
import com.reaksmeyarun.coffee.ui.main.account_drawer.feature.change_password.mvp.ChangePassMVP;
import com.reaksmeyarun.coffee.ui.main.account_drawer.feature.change_password.mvp.ChangePassPresenter;

public class ChangePasswordActivity extends AppCompatActivity implements ChangePassMVP.View {

    ChangePassMVP.Presenter presenter;
    private EditText etCurrentPass, etNewPass, etConNewPass;
    private Button btnSave;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        presenter = new ChangePassPresenter(this);

        initUI();
    }

    private void initUI() {
        etCurrentPass = findViewById(R.id.etCurrentPassword);
        etNewPass = findViewById(R.id.etNewPassword);
        etConNewPass = findViewById(R.id.etConNewPassword);
        btnSave = findViewById(R.id.btnSave);
        progressBar = findViewById(R.id.progressBar);

        btnSave.setOnClickListener(v->{

            //TODO
            if(!validate()){
                return;
            }
            if(etNewPass.getText().toString().equals(etConNewPass.getText().toString())){
                new AlertDialog.Builder(ChangePasswordActivity.this)
                        .setIcon(R.drawable.ic_password)
                        .setTitle("Change Password")
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
                                presenter.onChangePassword(etCurrentPass.getText().toString(), etNewPass.getText().toString());
                            }
                        }).show();
            }else {
                etNewPass.setError("!");
                etConNewPass.setError("!");
                Toast.makeText(this, "Password doesn't match!", Toast.LENGTH_SHORT).show();
            }

        });
    }
    //onCheck
    private boolean validate(){
        boolean valid = true;

        if(TextUtils.isEmpty(etCurrentPass.getText().toString())){
            etCurrentPass.setError("!");
            valid = false;
        }else if(TextUtils.isEmpty(etNewPass.getText().toString())){
            etNewPass.setError("!");
            valid = false;
        }else if(TextUtils.isEmpty(etConNewPass.getText().toString())){
            etConNewPass.setError("!");
            valid = false;
        }else if(etCurrentPass.getText().toString().length()<6){
            etCurrentPass.setError("At least 6 characters");
            valid = false;
        }else if(etNewPass.getText().toString().length()<6){
            etNewPass.setError("At least 6 characters");
            valid = false;
        }else if(etConNewPass.getText().toString().length()<6){
            etConNewPass.setError("At least 6 characters");
            valid = false;
        }else{
            etCurrentPass.setError(null);
            etNewPass.setError(null);
            etConNewPass.setError(null);
        }
        return valid;
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
    public void onChangeSuccess(String msg) {
        //Clear sharePreferences
        SharedPreferences sharedPreferences = getSharedPreferences("save", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("remember","false");
        editor.apply();
        //Log out firebaseAuth
        FirebaseAuth.getInstance().signOut();
        //start new Activity
        startActivity(new Intent(this, SignInActivity.class));
        finish();
        overridePendingTransition(R.anim.open, R.anim.close);

        Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onChangeFailure(String msg) {
        Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ChangePasswordActivity.this, MainActivity.class));
        finish();
        overridePendingTransition(R.anim.open, R.anim.close);
    }
}
