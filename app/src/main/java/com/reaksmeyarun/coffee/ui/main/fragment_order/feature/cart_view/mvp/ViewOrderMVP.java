package com.reaksmeyarun.coffee.ui.main.fragment_order.feature.cart_view.mvp;

import com.reaksmeyarun.coffee.model.Receipt;
import com.reaksmeyarun.coffee.model.Table;

public interface ViewOrderMVP {

    interface View{
        void onShowLoading();
        void onHideLoading();
        void onReceiptSuccess(String msg);
        void onReceiptFail(String msg);
        void onPushTableSuccess(String msg);
        void onPushTableFail(String msg);
        void onGetTableSuccess(Table table);
        void onGetTableFail(String msg);
    }

    interface Presenter{
        void onPushTable(Table table);
        void onGetTable(String id);
        void onPushReceipt(String tableId, Receipt receipt);
    }

    interface Interactor{
        void onPushTable(Table table, OnPushTableResponse onUpdateTableResponse);
        void onGetTable(String id, OnGetTableResponse onGetTableResponse);
        void onPushReceipt(String tableId, Receipt receipt, InteractorReceiptResponse interactorReceiptResponse);

        interface InteractorReceiptResponse{
            void onSuccess(String msg);
            void onFailure(String msg);
        }
        interface OnPushTableResponse{
            void onPushSuccess(String msg);
            void onPushFailure(String msg);
        }
        interface OnGetTableResponse{
            void onSuccess(Table table);
            void onFailure(String msg);
        }
    }
}
