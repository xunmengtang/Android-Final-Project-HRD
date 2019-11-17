package com.reaksmeyarun.coffee.ui.main.fragment_menu.feature.category.category.mvp;

import com.reaksmeyarun.coffee.model.Category;
import com.reaksmeyarun.coffee.model.Item;

import java.util.List;

public class CategoryPresenter implements CategoryMVP.Presenter {

    CategoryMVP.Interactor interactor;
    CategoryMVP.View view;

    public CategoryPresenter(CategoryMVP.View view) {
        this.view = view;
        interactor = new CategoryInteractor();
    }

    @Override
    public void onGetCategory() {
        if(view!=null){
            view.onShowLoading();
            interactor.onGetCategory(new CategoryMVP.Interactor.InteractorResponse() {
                @Override
                public void onGetSuccess(List<Category> categoryList) {
                    view.onHideLoading();
                    view.onCategorySuccess(categoryList);
                }

                @Override
                public void onGetFaild(String msg) {
                    view.onHideLoading();
                    view.onCategoryFail(msg);
                }
            });
        }
    }

    @Override
    public void onGetItem() {
        if(view!=null){
            view.onShowLoading();
            interactor.onGetItem(new CategoryMVP.Interactor.InteractorItemResponse() {
                @Override
                public void onGetSuccess(List<Item> items) {
                    view.onHideLoading();
                    view.onItemSuccess(items);
                }

                @Override
                public void onGetFaild(String msg) {
                    view.onHideLoading();
                    view.onItemFail(msg);
                }
            });
        }
    }

    @Override
    public void onDeleteCategory(Category category, List<Item> itemList) {
        if(view!=null){
            view.onShowLoading();
            interactor.onDeleteCategory(category, itemList, new CategoryMVP.Interactor.InteractorDeleteResponse() {
                @Override
                public void onDeleteSuccess(String msg) {
                    view.onHideLoading();
                    view.onDeleteSuccess(msg);
                }

                @Override
                public void onDeleteFail(String msg) {
                    view.onHideLoading();
                    view.onDeleteFail(msg);
                }
            });
        }
    }
}
