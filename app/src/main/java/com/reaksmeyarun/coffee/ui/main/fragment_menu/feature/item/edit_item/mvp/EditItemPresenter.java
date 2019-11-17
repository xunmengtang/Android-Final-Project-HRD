package com.reaksmeyarun.coffee.ui.main.fragment_menu.feature.item.edit_item.mvp;


import com.reaksmeyarun.coffee.model.Category;
import com.reaksmeyarun.coffee.model.Item;

import java.util.List;

public class EditItemPresenter implements EditItemMVP.Presenter {

    private EditItemMVP.Interactor interactor;
    private EditItemMVP.View view;

    public EditItemPresenter(EditItemMVP.View view) {
        this.view = view;
        interactor = new EditItemInteractor();
    }

    @Override
    public void onGetCategory() {
        if(view!=null){
            view.onShowLoading();
            interactor.onGetCategorySuccess(new EditItemMVP.Interactor.InteractorCategoryRespone() {
                @Override
                public void onSuccess(List<Category> categoryList) {
                    view.onHideLoading();
                    view.onCategorySuccess(categoryList);
                }

                @Override
                public void onFail(String msg) {
                    view.onHideLoading();
                    onFail("Something gone wrong!");
                }
            });
        }
    }

    @Override
    public void onUpdateItem(Item item) {
        if(view!=null){
            view.onShowLoading();
            interactor.onUpdateItem(
                    item,
                    new EditItemMVP.Interactor.InteractorItemUpdateResponse() {
                        @Override
                        public void onSuccess(String msg) {
                            view.onHideLoading();
                            view.onUpdateItemSuccess();
                        }

                        @Override
                        public void onFail(String msg) {
                            view.onHideLoading();
                            view.onError(msg);
                        }
                    });
        }
    }

    @Override
    public void onPushItem(Item item) {
        if(view!=null)
            view.onShowLoading();
        interactor.onPushItem(
                item,
                new EditItemMVP.Interactor.InteractorPushItemResponse() {
                    @Override
                    public void onPushSuccess(String msg) {
                        view.onHideLoading();
                        view.onPushItemSuccess(msg);
                    }

                    @Override
                    public void onPushError(String msg) {
                        view.onHideLoading();
                        view.onPushItemFail(msg);
                    }
                }
        );
    }
}
