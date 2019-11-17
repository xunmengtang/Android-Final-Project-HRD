package com.reaksmeyarun.coffee.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.reaksmeyarun.coffee.R;
import com.reaksmeyarun.coffee.model.Global;
import com.reaksmeyarun.coffee.model.User;
import com.reaksmeyarun.coffee.ui.main.fragment_order.OrderFragment;
import com.reaksmeyarun.coffee.ui.main.fragment_receipt.ReceiptFragment;
import com.reaksmeyarun.coffee.ui.sign_in.SignInActivity;
import com.reaksmeyarun.coffee.ui.main.account_drawer.AccountActivity;
import com.reaksmeyarun.coffee.ui.main.account_drawer.feature.about.AboutDialog;
import com.reaksmeyarun.coffee.ui.main.account_drawer.feature.change_password.ChangePasswordActivity;
import com.reaksmeyarun.coffee.ui.main.account_drawer.feature.reset_password.ResetPasswordActivity;
import com.reaksmeyarun.coffee.ui.main.fragment_menu.MenuFragment;
import com.reaksmeyarun.coffee.ui.main.mvp.MainMVP;
import com.reaksmeyarun.coffee.ui.main.mvp.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainMVP.View {

    private BottomNavigationView bottomNavigationView;
    private MainMVP.Presenter presenter;
    private ProgressBar progressBar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private View headerView;
    private TextView tvEmail, tvRule;
    private boolean mSlideState = true;
    private Menu menu;
    private MenuItem btnEditAccount, btnResetPass;
    private FirebaseAuth mAuth;
    private String currentID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initUI();

        presenter = new MainPresenter(this);

        SharedPreferences sharedPreferences = getSharedPreferences("save", MODE_PRIVATE);
        Gson gson = new Gson();
        String json  = sharedPreferences.getString("currentUser","");
        User user = gson.fromJson(json, User.class);
        Global.setUser(user);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.containers, OrderFragment.getInstance());
        fragmentTransaction.commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.btnReceipt:
                        replaceFragment(ReceiptFragment.getInstance(),R.id.containers);
                        return true;
                    case R.id.btnMenu:
                        replaceFragment(MenuFragment.getInstance(),R.id.containers);
                        return true;
                        case R.id.btnCreateOrder:
                        replaceFragment(OrderFragment.getInstance(),R.id.containers);
                        return true;
                }
                return false;
            }
        });
        setUpUI();
    }

    private void initUI(){

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        progressBar = findViewById(R.id.progressBar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.design_navigation_view);
        headerView = navigationView.getHeaderView(0);
        tvEmail = headerView.findViewById(R.id.tvEmail);
        tvRule = headerView.findViewById(R.id.tvRule);
        menu = navigationView.getMenu();
        btnEditAccount = menu.findItem(R.id.btnEditAccount);
        btnResetPass = menu.findItem(R.id.btnResetPass);

        drawerLayout.addDrawerListener(setupDrawerToggle());
    }

    private void setUpUI(){
        setupNavigationViewListener(navigationView);
        if(Global.user!=null){
            tvEmail.setText(Global.user.getUser());
            tvRule.setText(Global.user.getRule());
            if(!Global.onCheck()){
                btnResetPass.setVisible(false);
                btnEditAccount.setVisible(false);
            }
        }
    }
    private void setupNavigationViewListener(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case  R.id.btnEditAccount:
                        startActivity(new Intent(MainActivity.this, AccountActivity.class));
                        finish();
                        return true;
                    case R.id.btnFeature:
                        Toast.makeText(MainActivity.this, "TODO : Feature Activity", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.btnChangePass:
                        startActivity(new Intent(MainActivity.this, ChangePasswordActivity.class));
                        finish();
                        return true;
                    case R.id.btnResetPass:
                        startActivity(new Intent(MainActivity.this, ResetPasswordActivity.class));
                        finish();
                        return true;
                    case R.id.btnAboutUs:
                        AboutDialog aboutDialog = new AboutDialog(MainActivity.this);
                        aboutDialog.customDialog();
                        return true;
                    case R.id.btnSignOut:
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Alert")
                                .setMessage(R.string.alert_sign_out)
                                .setIcon(R.drawable.ic_sign_out_option)
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                })
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        presenter.onSetStatus(Global.user.getAuthID());
                                    }
                                })
                                .show();

                        return true;
                }
                return false;
            }
        });
    }

    private void replaceFragment(Fragment fragment, @IdRes int container){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment f = fragmentManager.findFragmentById(container);
        if(f==null || !f.getClass().getName().equals(fragment.getClass().getName())){
            fragmentTransaction.replace(container,fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
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
    public void onSetStatusSuccess(String msg) {
        //cancel remember password
        SharedPreferences sharedPreferences = getSharedPreferences("save", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("remember","false");
        editor.apply();
        //Log out firebaseAuth
        FirebaseAuth.getInstance().signOut();
        //Start new Activity
        startActivity(new Intent(MainActivity.this, SignInActivity.class));
        finish();
        overridePendingTransition(R.anim.open, R.anim.close);
    }

    @Override
    public void onSetStatusFail(String msg) {
        Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
    }

    private ActionBarDrawerToggle setupDrawerToggle(){
        return new ActionBarDrawerToggle(
                this,
                drawerLayout,
                null,
                R.string.drawer_open,
                R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mSlideState=false;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                mSlideState=true;
            }
        };
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(MainActivity.this)
                .setIcon(R.drawable.ic_icon)
                .setTitle("Alert")
                .setMessage(R.string.alert_close)
                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
        //super.onBackPressed();
    }

    @Override
    protected void onStart() {
        overridePendingTransition(R.anim.open, R.anim.close);
        super.onStart();
    }
}
