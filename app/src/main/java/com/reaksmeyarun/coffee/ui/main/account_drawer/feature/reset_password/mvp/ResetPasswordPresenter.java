package com.reaksmeyarun.coffee.ui.main.account_drawer.feature.reset_password.mvp;

public class ResetPasswordPresenter implements ResetPasswordMVP.Presenter {

    ResetPasswordMVP.View view;
    ResetPasswordMVP.Interactor interactor;

    public ResetPasswordPresenter(ResetPasswordMVP.View view) {
        this.view = view;
        interactor = new ResetPasswordInteractor();
    }

    @Override
    public void onSendPasswordResetEmail(String email) {
        if(view!=null){
            view.onShowLoading();
            interactor.onSendPasswordResetEmail(email, new ResetPasswordMVP.Interactor.OnSendPasswordResetEmailResponse() {
                @Override
                public void onSuccess(String msg) {
                    view.onHideLoading();
                    view.onSendSuccess(msg);
                }

                @Override
                public void onFailure(String msg) {
                    view.onHideLoading();
                    view.onSendFailure(msg);
                }
            });
        }
    }
}
