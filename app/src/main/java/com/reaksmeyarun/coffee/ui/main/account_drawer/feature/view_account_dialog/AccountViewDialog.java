package com.reaksmeyarun.coffee.ui.main.account_drawer.feature.view_account_dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.reaksmeyarun.coffee.R;
import com.reaksmeyarun.coffee.model.User;

public class AccountViewDialog {

    Context context;
    User user;
    View view;

    private TextView tvEmail, tvUsername, tvRule;

    public AccountViewDialog(Context context, User user) {
        this.context = context;
        this.user = user;
    }

    public void customDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("User Information");
        view = LayoutInflater.from(context).inflate(R.layout.account_view_dialog, null);

        initUI();

        builder.setView(view);
        builder.setCancelable(false);
        builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    private void initUI() {
        tvEmail = view.findViewById(R.id.tvEmail);
        tvUsername = view.findViewById(R.id.tvUsername);
        tvRule = view.findViewById(R.id.tvRule);

        tvEmail.setText(user.getEmail());
        tvUsername.setText(user.getUser());
        tvRule.setText(user.getRule());
    }
}
