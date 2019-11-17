package com.reaksmeyarun.coffee.ui.splash_screen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.reaksmeyarun.coffee.R;
import com.reaksmeyarun.coffee.model.Category;
import com.reaksmeyarun.coffee.model.Item;
import com.reaksmeyarun.coffee.model.Receipt;
import com.reaksmeyarun.coffee.model.User;
import com.reaksmeyarun.coffee.ui.sign_in.SignInActivity;
import com.reaksmeyarun.coffee.ui.main.MainActivity;
import com.reaksmeyarun.coffee.ui.main.account_drawer.mvp.AccountMVP;
import com.reaksmeyarun.coffee.ui.main.account_drawer.mvp.AccountPresenter;
import com.reaksmeyarun.coffee.ui.main.fragment_receipt.mvp.ReceiptMVP;
import com.reaksmeyarun.coffee.ui.main.fragment_receipt.mvp.ReceiptPresenter;
import com.reaksmeyarun.coffee.ui.main.fragment_menu.mvp.ItemMVP;
import com.reaksmeyarun.coffee.ui.main.fragment_menu.mvp.ItemPresenter;

import java.util.List;

public class SplashScreenActivity extends AppCompatActivity implements ReceiptMVP.View,ItemMVP.View, AccountMVP.View {

    private ImageView icon;
    private TextView tvDesc;
    private ProgressBar progressBar;
    ReceiptMVP.Presenter receiptPresenter;
    ItemMVP.Presenter itemPresenter;
    AccountMVP.Presenter accPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        icon = findViewById(R.id.icon);
        tvDesc = findViewById(R.id.tvDesc);
        progressBar = findViewById(R.id.progressBar);

        SharedPreferences sharedPreferences = getSharedPreferences("save", MODE_PRIVATE);
        String onCheck = sharedPreferences.getString("remember","");

        if(onCheck.equals("true")){
            receiptPresenter = new ReceiptPresenter(this);
            itemPresenter = new ItemPresenter(this);
            accPresenter = new AccountPresenter(this);
            receiptPresenter.onGetReceipt();
            itemPresenter.onGetCategory();
            itemPresenter.onGetItem();
            accPresenter.onGetUser();
        }else {
            accPresenter = new AccountPresenter(this);
            accPresenter.onGetUser();
        }

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash_screen_transaction);
        icon.startAnimation(animation);
        tvDesc.startAnimation(animation);
        progressBar.startAnimation(animation);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(5000);
                }catch (Exception ex){
                    Log.e(TAG, "run: "+ex.toString() );
                }finally {
                    if (onCheck.equals("true")){
                        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                        finish();
                    }else {
                        startActivity(new Intent(SplashScreenActivity.this, SignInActivity.class));
                        finish();
                    }
                }
            }
        };
        thread.start();
    }

    private static final String TAG = "SplashScreenActivity";

    @Override
    public void onShowLoading() {
    }

    @Override
    public void onHideLoading() {
    }

    @Override
    public void onGetSuccess(List<User> userList) {

    }

    @Override
    public void onGetFail(String msg) {

    }

    @Override
    public void onCategorySuccess(List<Category> categoryList) {

    }

    @Override
    public void onItemSuccess(List<Item> items) {

    }

    @Override
    public void onCategoryError(String msg) {

    }

    @Override
    public void onItemError(String msg) {

    }

    @Override
    public void onGetReceiptSuccess(List<Receipt> receipt) {

    }

    @Override
    public void onGetReceptFailure(String msg) {

    }

    @Override
    public void onBackPressed() {
    }
}
