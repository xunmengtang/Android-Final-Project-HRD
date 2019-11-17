package com.reaksmeyarun.coffee.ui.main.fragment_order.feature.create_order.mvp;


import com.reaksmeyarun.coffee.model.Item;

import java.util.List;

public interface CreateOrderMVP {

    interface View{
        void onShowLoading();
        void onHideLoading();

        void onGetItemSuccess(List<Item> itemList);
        void onGetItemFail(String msg);
    }

    interface Presenter{
        void onGetItem();
    }

    interface Interactor{

        void onGetGetItem(InteractorGetItemResponse interactorGetItemResponse);

        interface InteractorGetItemResponse{
            void onGetSuccess(List<Item> itemList);
            void onGetFail(String msg);
        }
    }
}
