package com.reaksmeyarun.coffee.ui.sign_in.mvp;

import com.reaksmeyarun.coffee.model.User;

public interface SignInMVP {
    interface View{
        void onSuccess(String msg);
        void onError(String msg);
        void onGetSuccess(User user);
        void onGetFailure(String msg);
        void onSetStatusSuccess(String msg);
        void onSetStatusFail(String msg);
        void onShowLoading();
        void onHideLoading();
    }

    interface Presenter{
        void onLogin(User user);
        void onGetCurrentUser();
        void onSetStatus(String id);
    }

    interface Interactor{

        void onGetCurrentUser(InteractorGetUserResponse interactorGetUserResponse);
        void onSetStatus(String id, InteractorSetStatusResponse interactorSetStatusResponse);
        void authenticat(User user, InteractorResponse response);

        interface InteractorResponse{
            void onLoginSuccess(String msg);
            void onLoginFail(String msg);
        }
        interface InteractorGetUserResponse{
            void onGetSuccess(User user);
            void onGetFailure(String msg);
        }
        interface InteractorSetStatusResponse{
            void onSetSuccess(String msg);
            void onSetFailure(String msg);
        }
    }
}
