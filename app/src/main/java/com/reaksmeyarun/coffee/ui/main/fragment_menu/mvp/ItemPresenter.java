package com.reaksmeyarun.coffee.ui.main.fragment_menu.mvp;

import com.reaksmeyarun.coffee.model.Category;
import com.reaksmeyarun.coffee.model.Item;

import java.util.List;

public class ItemPresenter implements ItemMVP.Presenter {

    private static final String TAG = "ItemPresenter";

    private ItemMVP.View view;
    private ItemMVP.Interactor interactor;

    public ItemPresenter(ItemMVP.View view) {
        this.view = view;
        interactor = new ItemInteractor();
    }

    @Override
    public void onGetCategory() {
        if(view!=null){
            view.onShowLoading();
            interactor.onGetCategory(new ItemMVP.Interactor.InteractorCategoryResponse() {

                @Override
                public void onCategorySuccess(List<Category> categoryList) {
                    if (view!=null){
                        view.onHideLoading();
                        view.onCategorySuccess(categoryList);
                    }
                }

                @Override
                public void onError(String msg) {
                    if (view!=null){
                        view.onHideLoading();
                        view.onCategoryError(msg);
                    }
                }
            });
        }
    }

    @Override
    public void onGetItem() {
        if(view!=null){
            view.onShowLoading();
            interactor.onGetItem(new ItemMVP.Interactor.InteractorItemsResponse() {
                @Override
                public void onItemSuccess(List<Item> itemList) {
                    view.onHideLoading();
                    view.onItemSuccess(itemList);
                }

                @Override
                public void onError(String msg) {
                    view.onHideLoading();
                    view.onItemError(msg);
                }
            });
        }
    }
}
