package com.reaksmeyarun.coffee.ui.main.account_drawer.feature.change_password.mvp;

public interface ChangePassMVP {

    interface View{
        void onShowLoading();
        void onHideLoading();
        void onChangeSuccess(String msg);
        void onChangeFailure(String msg);
    }

    interface Presenter{
        void onChangePassword(String currentPassword, String newPassword);
    }

    interface Interactor{
        void onChangePassword(String currentPassword, String newPassword, OnChangePasswordResponse onChangePasswordResponse);

        interface OnChangePasswordResponse{
            void onSuccess(String msg);
            void onFailure(String msg);
        }
    }
}
