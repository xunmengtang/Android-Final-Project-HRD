package com.reaksmeyarun.coffee.ui.sign_in;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.reaksmeyarun.coffee.R;
import com.reaksmeyarun.coffee.model.Global;
import com.reaksmeyarun.coffee.model.User;
import com.reaksmeyarun.coffee.ui.sign_in.mvp.SignInMVP;
import com.reaksmeyarun.coffee.ui.sign_in.mvp.SignInPresenter;
import com.reaksmeyarun.coffee.ui.splash_screen.SplashScreenActivity;

public class SignInActivity extends AppCompatActivity implements SignInMVP.View {

    private Button btnSignIn;
    private TextView mEmail, mPassword;
    private ProgressBar progressBar;
    private User user;

    private SignInMVP.Presenter presenter;
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        btnSignIn = findViewById(R.id.btnReset);
        mEmail = findViewById(R.id.tvEmail);
        mPassword = findViewById(R.id.etNewPassword);
        progressBar=findViewById(R.id.progressBar);

        presenter = new SignInPresenter(this);
//        //onCheck condition if user already login
//        SharedPreferences sharedPreferences = getSharedPreferences("save", MODE_PRIVATE);
//        String onCheck = sharedPreferences.getString("remember","");
//
//        if (onCheck.equals("true")){
//            startActivity(new Intent(this, MainActivity.class));
//            finish();
//        }

//        TODO: working on softkey Enter
//        mPassword.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if(keyCode == KeyEvent.KEYCODE_ENTER)
//                {
//                    if(!validateForm()){
//                        return false;
//                    }
//                    presenter.onLogin(new User(
//                            mEmail.getText().toString(),
//                            mPassword.getText().toString()));
//                    return true;
//                }else
//                    return false;
//            }
//        });

        btnSignIn.setOnClickListener(v->{
            if(!validateForm()){
                return;
            }
            presenter.onLogin(new User(
                    mEmail.getText().toString(),
                    mPassword.getText().toString()));
        });
    }

    @Override
    public void onSuccess(String msg) {
        presenter.onGetCurrentUser();
        //onCheck
        //Start new Activity
    }

    @Override
    public void onError(String msg) {
        mEmail.setError(msg);
        mPassword.setError(msg);
        progressBar.setVisibility(View.GONE);
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
    public void onGetSuccess(User user) {
        FirebaseUser userAuth = auth.getCurrentUser();
        if(user.getStatus().equals(Global.ONLINE)){
            Toast.makeText(this, "Account already login", Toast.LENGTH_SHORT).show();
        }else {
            if(userAuth!=null){
                if(userAuth.isEmailVerified()) {
                    Global.setUser(user);
                    this.user = user;
                    presenter.onSetStatus(auth.getUid());
                }else {
                    Toast.makeText(this, "Please Verification your Email", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onGetFailure(String msg) {
        Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSetStatusSuccess(String msg) {
        //remember  password
        SharedPreferences sharedPreferences = getSharedPreferences("save", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString("remember","true");
        editor.putString("currentUser", json);
        editor.apply();
        //Start activity
        startActivity(new Intent(this, SplashScreenActivity.class));
        overridePendingTransition(R.anim.open, R.anim.close);
        finish();
    }

    @Override
    public void onSetStatusFail(String msg) {
        Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
    }

    private boolean validateForm(){
        boolean valid = true;

        if(TextUtils.isEmpty(mEmail.getText().toString())){
            mEmail.setError("!");
            valid = false;
        }else if(TextUtils.isEmpty(mPassword.getText().toString())){
            mPassword.setError("!");
            valid = false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(mEmail.getText().toString()).matches()){
            mEmail.setError("@gmail.com");
            valid = false;
        }else if(mPassword.getText().toString().length()<6){
            mPassword.setError("At least 6 characters");
            valid = false;
        }else{
            mPassword.setError(null);
            mEmail.setError(null);
        }
        return valid;
    }

    @Override
    protected void onStart() {
        overridePendingTransition(R.anim.open, R.anim.close);
        super.onStart();
    }
}
