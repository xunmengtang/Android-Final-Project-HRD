package com.reaksmeyarun.coffee.ui.main.mvp;

public interface MainMVP {

    interface View{
        void onShowLoading();
        void onHideLoading();
        void onSetStatusSuccess(String msg);
        void onSetStatusFail(String msg);
    }

    interface Presenter{
        void onSetStatus(String id);
    }

    interface Interactor{
        void onSetStatus(String id, InteractorSetStatusResponse interactorSetStatusResponse);

        interface InteractorSetStatusResponse{
            void onSetSuccess(String msg);
            void onSetFailure(String msg);
        }
    }

}
