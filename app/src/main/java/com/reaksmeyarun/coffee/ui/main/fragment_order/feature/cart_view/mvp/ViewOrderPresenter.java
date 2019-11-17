package com.reaksmeyarun.coffee.ui.main.fragment_order.feature.cart_view.mvp;

import com.reaksmeyarun.coffee.model.Receipt;
import com.reaksmeyarun.coffee.model.Table;

public class ViewOrderPresenter implements ViewOrderMVP.Presenter {

    ViewOrderMVP.View view;
    ViewOrderMVP.Interactor interactor;

    public ViewOrderPresenter(ViewOrderMVP.View view) {
        this.view = view;
        interactor = new ViewOrderInteractor();
    }

    @Override
    public void onPushTable(Table table) {
        if(view!=null){
            view.onShowLoading();
            interactor.onPushTable(table, new ViewOrderMVP.Interactor.OnPushTableResponse() {
                @Override
                public void onPushSuccess(String msg) {
                    view.onHideLoading();
                    view.onPushTableSuccess(msg);
                }

                @Override
                public void onPushFailure(String msg) {
                    view.onHideLoading();
                    view.onPushTableFail(msg);
                }
            });
        }
    }

    @Override
    public void onGetTable(String id) {
        if(view!=null){
            view.onShowLoading();
            interactor.onGetTable(id, new ViewOrderMVP.Interactor.OnGetTableResponse() {
                @Override
                public void onSuccess(Table table) {
                    view.onHideLoading();
                    view.onGetTableSuccess(table);
                }

                @Override
                public void onFailure(String msg) {
                    view.onHideLoading();
                    view.onGetTableFail(msg);
                }
            });
        }
    }

    @Override
    public void onPushReceipt(String tableId, Receipt receipt) {
        if(view!=null){
            view.onShowLoading();
            interactor.onPushReceipt(tableId, receipt, new ViewOrderMVP.Interactor.InteractorReceiptResponse() {
                @Override
                public void onSuccess(String msg) {
                    view.onHideLoading();
                    view.onReceiptSuccess(msg);
                }

                @Override
                public void onFailure(String msg) {
                    view.onHideLoading();
                    view.onReceiptFail(msg);
                }
            });
        }
    }
}
