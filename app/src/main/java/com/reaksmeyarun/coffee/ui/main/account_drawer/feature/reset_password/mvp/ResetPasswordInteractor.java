package com.reaksmeyarun.coffee.ui.main.account_drawer.feature.reset_password.mvp;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordInteractor implements ResetPasswordMVP.Interactor {

    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    public void onSendPasswordResetEmail(String email, OnSendPasswordResetEmailResponse onSendPasswordResetEmailResponse) {

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // do something when mail was sent successfully.
                            onSendPasswordResetEmailResponse.onSuccess("Email send successful, please check your email");
                        } else {
                            // ...
                            onSendPasswordResetEmailResponse.onFailure("Send failure!");
                        }
                    }
                });
    }
}
