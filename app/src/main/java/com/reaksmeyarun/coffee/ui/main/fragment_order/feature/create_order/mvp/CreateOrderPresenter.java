package com.reaksmeyarun.coffee.ui.main.fragment_order.feature.create_order.mvp;


import com.reaksmeyarun.coffee.model.Item;

import java.util.List;

public class CreateOrderPresenter implements CreateOrderMVP.Presenter {

    CreateOrderMVP.View view;
    CreateOrderMVP.Interactor interactor;

    public CreateOrderPresenter(CreateOrderMVP.View view) {
        this.view = view;
        interactor = new CreateOrderInteractor();
    }

    @Override
    public void onGetItem() {
        if(view!=null){
            view.onShowLoading();
            interactor.onGetGetItem(new CreateOrderMVP.Interactor.InteractorGetItemResponse() {
                @Override
                public void onGetSuccess(List<Item> itemList) {
                    view.onHideLoading();
                    view.onGetItemSuccess(itemList);
                }

                @Override
                public void onGetFail(String msg) {
                    view.onHideLoading();
                    view.onGetItemFail(msg);
                }
            });
        }
    }
}
