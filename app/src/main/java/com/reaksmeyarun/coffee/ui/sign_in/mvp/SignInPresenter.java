package com.reaksmeyarun.coffee.ui.sign_in.mvp;


import com.reaksmeyarun.coffee.model.User;

public class SignInPresenter implements SignInMVP.Presenter {

    SignInMVP.Interactor interactor;
    SignInMVP.View view;

    public SignInPresenter(SignInMVP.View view) {
        this.view = view;
        interactor = new SignInInteractor();
    }

    @Override
    public void onLogin(final User user) {
        if(view!=null){
            view.onShowLoading();
            interactor.authenticat(user, new SignInMVP.Interactor.InteractorResponse() {
                @Override
                public void onLoginSuccess(String id) {
                    view.onHideLoading();
                    view.onSuccess(id);
                }

                @Override
                public void onLoginFail(String msg) {
                    view.onHideLoading();
                    view.onError(msg);
                }
            });
        }
    }

    @Override
    public void onGetCurrentUser() {
        if(view!=null){
            view.onShowLoading();
            interactor.onGetCurrentUser( new SignInMVP.Interactor.InteractorGetUserResponse() {
                @Override
                public void onGetSuccess(User user) {
                    view.onHideLoading();
                    view.onGetSuccess(user);
                }

                @Override
                public void onGetFailure(String msg) {
                    view.onHideLoading();
                    view.onGetFailure(msg);
                }
            });
        }
    }

    @Override
    public void onSetStatus(String id) {
        if(view!=null){
            view.onShowLoading();
            interactor.onSetStatus(id, new SignInMVP.Interactor.InteractorSetStatusResponse() {
                @Override
                public void onSetSuccess(String msg) {
                    view.onHideLoading();
                    view.onSetStatusSuccess(msg);
                }

                @Override
                public void onSetFailure(String msg) {
                    view.onHideLoading();
                    view.onSetStatusFail(msg);
                }
            });
        }
    }
}
