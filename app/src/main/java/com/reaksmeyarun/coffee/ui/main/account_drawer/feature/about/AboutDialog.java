package com.reaksmeyarun.coffee.ui.main.account_drawer.feature.about;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.reaksmeyarun.coffee.R;

public class AboutDialog {

    Context context;
    View view;

    private TextView tvAboutUs;

    public AboutDialog(Context context) {
        this.context = context;
    }

    public void customDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("About");
        view = LayoutInflater.from(context).inflate(R.layout.about_dialog, null);

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
        tvAboutUs = view.findViewById(R.id.tvEmail);
    }
}
