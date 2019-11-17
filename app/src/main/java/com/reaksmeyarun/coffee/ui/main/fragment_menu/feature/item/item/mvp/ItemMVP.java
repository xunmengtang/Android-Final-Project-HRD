package com.reaksmeyarun.coffee.ui.main.fragment_menu.feature.item.item.mvp;

import com.reaksmeyarun.coffee.model.Item;

import java.util.List;

public interface ItemMVP {

    interface View{
        void onShowLoading();
        void onHideLoading();

        void onGetItemSuccess(List<Item> itemList);
        void onGetItemFail(String msg);

        void onDeleteItemSuccess(String msg);
        void onDeleteItemFail(String msg);
    }

    interface Presenter{

        void onItemDelete(Item item);
        void onGetItem();
    }

    interface Interactor{
        void onGetItem(InteractorItemResponse interactorItemResponse);
        void onDeleteItem(Item item, InteractorDeleteItemResponse interactorDeleteItemResponse);

        interface InteractorItemResponse{
            void onGetSuccess(List<Item> itemList);
            void onGetFail(String msg);
        }

        interface InteractorDeleteItemResponse{
            void onDeleteItemSuccess(String msg);
            void onDeleteItemError(String msg);
        }
    }
}
