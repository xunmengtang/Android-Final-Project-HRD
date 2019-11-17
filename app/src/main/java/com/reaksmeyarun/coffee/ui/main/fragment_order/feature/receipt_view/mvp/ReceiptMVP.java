package com.reaksmeyarun.coffee.ui.main.fragment_order.feature.receipt_view.mvp;

import com.reaksmeyarun.coffee.model.Receipt;
import com.reaksmeyarun.coffee.model.User;

public interface ReceiptMVP {

    interface View{
        void onShowLoading();
        void onHideLoading();
        void onGetReceitSuccessful(Receipt receipt);
        void onGetReceiptFailure(String msg);
        void onGetCreateBySuccess(User user);
        void onGetCreateByFailure(String msg);
    }

    interface Presenter{
        void onGetReceipt(String ID);
        void onGetCreateBy(String ID);
    }

    interface Interactor{
        void onGetReceipt(String ID, InteractorGetReceiptResponse interactorGetReceiptResponse);
        void onGetCreateBy(String ID, InteractorGetCreateByResponse interactorGetCreateByResponse);
        interface InteractorGetReceiptResponse{
            void onSuccess(Receipt receipt);
            void onFailure(String msg);
        }
        interface InteractorGetCreateByResponse{
            void onSuccess(User user);
            void onFailure(String msg);
        }
    }
}
