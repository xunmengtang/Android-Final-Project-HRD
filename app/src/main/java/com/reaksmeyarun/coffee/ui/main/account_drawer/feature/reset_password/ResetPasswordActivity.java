package com.reaksmeyarun.coffee.ui.main.account_drawer.feature.reset_password;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.reaksmeyarun.coffee.R;
import com.reaksmeyarun.coffee.ui.main.MainActivity;
import com.reaksmeyarun.coffee.ui.main.account_drawer.feature.reset_password.mvp.ResetPasswordMVP;
import com.reaksmeyarun.coffee.ui.main.account_drawer.feature.reset_password.mvp.ResetPasswordPresenter;

public class ResetPasswordActivity extends AppCompatActivity implements ResetPasswordMVP.View {

    private EditText etEmail;
    private Button btnSend;
    private ProgressBar progressBar;

    private ResetPasswordMVP.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        presenter = new ResetPasswordPresenter(this);
        initUI();
    }

    private void initUI() {
        etEmail = findViewById(R.id.tvEmail);
        btnSend = findViewById(R.id.btnReset);
        progressBar = findViewById(R.id.progressBar);

        btnSend.setOnClickListener(v->{
            if(!validateForm()){
                return;
            }
            new AlertDialog.Builder(ResetPasswordActivity.this)
                    .setIcon(R.drawable.ic_forgot)
                    .setTitle("Reset Password")
                    .setMessage(R.string.alert_send)
                    .setNegativeButton("No",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            presenter.onSendPasswordResetEmail(etEmail.getText().toString());
                        }
                    }).show();
        });
    }

    private boolean validateForm(){
        boolean valid = true;

        if(TextUtils.isEmpty(etEmail.getText().toString())){
            etEmail.setError("!");
            valid = false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()){
            etEmail.setError("@gmail.com");
            valid = false;
        }else{
            etEmail.setError(null);
        }
        return valid;
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(ResetPasswordActivity.this, MainActivity.class));
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
    public void onSendSuccess(String msg) {
        Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(ResetPasswordActivity.this, MainActivity.class));
        finish();
        overridePendingTransition(R.anim.open, R.anim.close);
    }

    @Override
    public void onSendFailure(String msg) {
        Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
    }
}
