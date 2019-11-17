package com.reaksmeyarun.coffee.ui.main.fragment_order.mvp;

import com.reaksmeyarun.coffee.model.Table;

import java.util.List;

public class OrderPresenter implements OrderMVP.Presenter {

    OrderMVP.View  view;
    OrderMVP.Interactor interactor;

    public OrderPresenter(OrderMVP.View view) {
        this.view = view;
        interactor = new OrderInteractor();
    }

    @Override
    public void onGetTable() {
        if(view!=null){
            interactor.onGetTable(new OrderMVP.Interactor.OnGetTableResponse() {
                @Override
                public void onSuccess(List<Table> tableList) {
                    view.onTableSuccess(tableList);
                }

                @Override
                public void onFail(String msg) {
                    view.onTableFail(msg);
                }
            });
        }
    }
}
