package com.reaksmeyarun.coffee.ui.main.mvp;

public class MainPresenter implements MainMVP.Presenter {

    MainMVP.View view;
    MainMVP.Interactor interactor;

    public MainPresenter(MainMVP.View view) {
        this.view = view;
        interactor = new MainInteractor();
    }

    @Override
    public void onSetStatus(String id) {
        if(view!=null){
            view.onShowLoading();
            interactor.onSetStatus(id, new MainMVP.Interactor.InteractorSetStatusResponse() {
                @Override
                public void onSetSuccess(String msg) {
                    view.onHideLoading();
                    view.onSetStatusSuccess("Successful!");
                }

                @Override
                public void onSetFailure(String msg) {
                    view.onHideLoading();
                    view.onSetStatusFail("Fail!");
                }
            });
        }
    }
}
