package com.reaksmeyarun.coffee.ui.main.account_drawer.feature.change_password.mvp;

public class ChangePassPresenter implements ChangePassMVP.Presenter {

    ChangePassMVP.View view;
    ChangePassMVP.Interactor interactor;

    public ChangePassPresenter(ChangePassMVP.View view) {
        this.view = view;
        interactor = new ChangePassInteractor();
    }

    @Override
    public void onChangePassword(String currentPassword, String newPassword) {
        if(view!=null){
            view.onShowLoading();
            interactor.onChangePassword(currentPassword, newPassword, new ChangePassMVP.Interactor.OnChangePasswordResponse() {
                @Override
                public void onSuccess(String msg) {
                    view.onHideLoading();
                    view.onChangeSuccess(msg);
                }

                @Override
                public void onFailure(String msg) {
                    view.onHideLoading();
                    view.onChangeFailure(msg);
                }
            });
        }
    }
}
