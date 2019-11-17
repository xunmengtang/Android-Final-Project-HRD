package com.reaksmeyarun.coffee.ui.main.account_drawer.feature.reset_password.mvp;

public interface ResetPasswordMVP {

    interface View{
        void onShowLoading();
        void onHideLoading();
        void onSendSuccess(String msg);
        void onSendFailure(String msg);
    }

    interface Presenter{
        void onSendPasswordResetEmail(String email);
    }

    interface Interactor{
        void onSendPasswordResetEmail(String email, OnSendPasswordResetEmailResponse onSendPasswordResetEmailResponse);

        interface OnSendPasswordResetEmailResponse{
            void onSuccess(String msg);
            void onFailure(String msg);
        }
    }
}
