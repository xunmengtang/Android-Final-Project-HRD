package com.reaksmeyarun.coffee.ui.main.account_drawer.mvp;

import com.reaksmeyarun.coffee.model.User;

import java.util.List;

public class AccountPresenter implements AccountMVP.Presenter {

    AccountMVP.View view;
    AccountMVP.Interactor interactor;

    public AccountPresenter(AccountMVP.View view) {
        this.view = view;
        interactor = new AccountInteractor();
    }

    @Override
    public void onGetUser() {
        if(view!=null){
            view.onShowLoading();
            interactor.onGetUser(new AccountMVP.Interactor.OnGetUserResponse() {

                @Override
                public void onGetSuccess(List<User> users) {

                    view.onHideLoading();
                    view.onGetSuccess(users);
                }

                @Override
                public void onGetFail(String msg) {
                    view.onHideLoading();
                    view.onGetFail(msg);
                }
            });
        }
    }
}
