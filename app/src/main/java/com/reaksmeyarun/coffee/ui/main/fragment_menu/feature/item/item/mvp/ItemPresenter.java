package com.reaksmeyarun.coffee.ui.main.fragment_menu.feature.item.item.mvp;

import com.reaksmeyarun.coffee.model.Item;

import java.util.List;

public class ItemPresenter implements ItemMVP.Presenter {

    ItemMVP.Interactor interactor;
    ItemMVP.View view;

    public ItemPresenter(ItemMVP.View view) {
        this.view = view;
        interactor = new ItemInteractor();
    }

    @Override
    public void onGetItem() {
        if(view!=null){
            view.onShowLoading();
            interactor.onGetItem(new ItemMVP.Interactor.InteractorItemResponse() {
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

    @Override
    public void onItemDelete(Item item) {
        if(view!=null){
            view.onShowLoading();
            interactor.onDeleteItem(item, new ItemMVP.Interactor.InteractorDeleteItemResponse() {
                @Override
                public void onDeleteItemSuccess(String msg) {
                    view.onHideLoading();
                    view.onDeleteItemSuccess(msg);
                }

                @Override
                public void onDeleteItemError(String msg) {
                    view.onHideLoading();
                    view.onDeleteItemFail(msg);
                }
            });
        }
    }
}
