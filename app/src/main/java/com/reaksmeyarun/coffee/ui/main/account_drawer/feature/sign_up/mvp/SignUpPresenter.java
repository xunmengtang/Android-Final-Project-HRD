package com.reaksmeyarun.coffee.ui.main.account_drawer.feature.sign_up.mvp;

import com.reaksmeyarun.coffee.model.User;

public class SignUpPresenter implements SignUpMVP.Presenter {

    SignUpMVP.Interactor interactor;
    SignUpMVP.View view;

    public SignUpPresenter(SignUpMVP.View view) {
        this.view = view;
        interactor = new SignUpInteractor();
    }

    @Override
    public void onSignUp( User user) {
        if(view!=null){
            view.onShowLoading();
            interactor.onSignUp(user, new SignUpMVP.Interactor.InteractorSignUpResponse() {
                @Override
                public void onSuccess(String msg) {
                    view.onHideLoading();
                    view.onSignUpSuccess(msg);
                }

                @Override
                public void onFailure(String msg) {
                    view.onHideLoading();
                    view.onSignUpFailure(msg);
                }
            });
        }
    }
}
