package com.reaksmeyarun.coffee.ui.main.fragment_receipt.mvp;


import com.reaksmeyarun.coffee.model.Receipt;

import java.util.List;

public class ReceiptPresenter implements ReceiptMVP.Presenter {

    ReceiptMVP.View view;
    ReceiptMVP.Interactor interactor;

    public ReceiptPresenter(ReceiptMVP.View view) {
        this.view = view;
        interactor = new ReceiptInteractor();
    }

    @Override
    public void onGetReceipt() {
        if(view!=null){
            view.onShowLoading();
            interactor.onGetReceipt(new ReceiptMVP.Interactor.InteractorGetReceipt() {
                @Override
                public void onSuccess(List<Receipt> receipt) {
                    view.onHideLoading();
                    view.onGetReceiptSuccess(receipt);
                }

                @Override
                public void onFailure(String msg) {
                    view.onHideLoading();
                    view.onGetReceptFailure(msg);
                }
            });
        }
    }
}
