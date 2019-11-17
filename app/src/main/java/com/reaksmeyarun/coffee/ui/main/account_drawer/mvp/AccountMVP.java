package com.reaksmeyarun.coffee.ui.main.account_drawer.mvp;

import com.reaksmeyarun.coffee.model.User;

import java.util.List;

public interface AccountMVP {
    interface View{
        void onShowLoading();
        void onHideLoading();
        void onGetSuccess(List<User> userList);
        void onGetFail(String msg);
    }

    interface Presenter{
        void onGetUser();
    }

    interface Interactor{

        void onGetUser(OnGetUserResponse onGetUserResponse);

        interface OnGetUserResponse{
            void onGetSuccess(List<User> users);
            void onGetFail(String msg);
        }
    }
}
