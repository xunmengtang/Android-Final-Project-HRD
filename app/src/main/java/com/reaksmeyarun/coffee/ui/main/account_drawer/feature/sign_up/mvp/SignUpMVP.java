package com.reaksmeyarun.coffee.ui.main.account_drawer.feature.sign_up.mvp;

import com.reaksmeyarun.coffee.model.User;

public interface SignUpMVP {

    interface View{
        void onShowLoading();
        void onHideLoading();
        void onSignUpSuccess(String msg);
        void onSignUpFailure(String msg);
    }

    interface Presenter{
        void onSignUp(User user);
    }

    interface Interactor{

        void onSignUp(User user, InteractorSignUpResponse interactorSignUpResponse);

        interface InteractorSignUpResponse{
            void onSuccess(String msg);
            void onFailure(String msg);
        }

    }
}
