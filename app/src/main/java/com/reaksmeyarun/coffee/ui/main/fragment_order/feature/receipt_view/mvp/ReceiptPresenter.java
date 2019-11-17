package com.reaksmeyarun.coffee.ui.main.fragment_order.feature.receipt_view.mvp;

import com.reaksmeyarun.coffee.model.Receipt;
import com.reaksmeyarun.coffee.model.User;

public class ReceiptPresenter implements ReceiptMVP.Presenter {
    ReceiptMVP.View view;
    ReceiptMVP.Interactor interactor;

    public ReceiptPresenter(ReceiptMVP.View view) {
        this.view = view;
        interactor = new ReceiptInteractor();
    }

    @Override
    public void onGetReceipt(String ID) {
        if(view!=null){
            view.onShowLoading();
            interactor.onGetReceipt(ID, new ReceiptMVP.Interactor.InteractorGetReceiptResponse() {
                @Override
                public void onSuccess(Receipt receipt) {
                    view.onHideLoading();
                    view.onGetReceitSuccessful(receipt);
                }

                @Override
                public void onFailure(String msg) {
                    view.onHideLoading();
                    view.onGetReceiptFailure(msg);
                }
            });
        }
    }

    @Override
    public void onGetCreateBy(String ID) {
        if(view!=null){
            view.onShowLoading();
            interactor.onGetCreateBy(ID, new ReceiptMVP.Interactor.InteractorGetCreateByResponse() {
                @Override
                public void onSuccess(User user) {
                    view.onHideLoading();
                    view.onGetCreateBySuccess(user);
                }

                @Override
                public void onFailure(String msg) {
                    view.onHideLoading();
                    view.onGetCreateByFailure(msg);
                }
            });
        }
    }
}
