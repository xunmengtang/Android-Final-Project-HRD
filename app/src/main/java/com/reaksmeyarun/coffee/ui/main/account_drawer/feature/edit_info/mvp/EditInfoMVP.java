package com.reaksmeyarun.coffee.ui.main.account_drawer.feature.edit_info.mvp;

import com.reaksmeyarun.coffee.model.User;

public interface EditInfoMVP {
    interface View{
        void onShowLoading();
        void onHideLoading();
        void onUpdateSuccess(String msg);
        void onUpdateFail(String msg);
    }

    interface Presenter{
        void onUpdateUser(User user);
    }

    interface Interactor{

        void onUpdateUser(User user, OnUpdateUserResponse onUpdateUserResponse);

        interface OnUpdateUserResponse{
            void onSuccess(String msg);
            void onFail(String msg);
        }
    }
}
