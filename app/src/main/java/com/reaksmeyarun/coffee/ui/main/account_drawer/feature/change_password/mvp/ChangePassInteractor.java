package com.reaksmeyarun.coffee.ui.main.account_drawer.feature.change_password.mvp;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassInteractor implements ChangePassMVP.Interactor {

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser userAuth = auth.getCurrentUser();

    @Override
    public void onChangePassword(String currentPassword, String newPassword, OnChangePasswordResponse onChangePasswordResponse) {
        if(userAuth!=null){
            AuthCredential authCredential = EmailAuthProvider
                    .getCredential(userAuth.getEmail(), currentPassword);
            userAuth.reauthenticate(authCredential)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            userAuth.updatePassword(newPassword)
                                    .addOnCompleteListener(task1 -> {
                                       onChangePasswordResponse.onSuccess("Change Password Successfully!");
                                    });
                        }else {
                            onChangePasswordResponse.onFailure("Change Password Fail!");
                        }
                    });
        }
    }
}
