package com.reaksmeyarun.coffee.ui.main.fragment_receipt.mvp;

import com.reaksmeyarun.coffee.model.Receipt;

import java.util.List;

public interface ReceiptMVP {

    interface View{
        void onShowLoading();
        void onHideLoading();

        void onGetReceiptSuccess(List<Receipt> receipt);
        void onGetReceptFailure(String msg);
    }

    interface Presenter{
        void onGetReceipt();
    }

    interface Interactor{
        void onGetReceipt(InteractorGetReceipt interactorGetReceipt);

        interface InteractorGetReceipt{
            void onSuccess(List<Receipt> receipt);
            void onFailure(String msg);
        }
    }
}
