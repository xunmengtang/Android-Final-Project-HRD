package com.reaksmeyarun.coffee.ui.main.fragment_menu.mvp;

import com.reaksmeyarun.coffee.model.Category;
import com.reaksmeyarun.coffee.model.Item;

import java.util.List;

public interface ItemMVP {
    interface View{

        void onShowLoading();
        void onHideLoading();

        void onCategorySuccess(List<Category> categoryList);
        void onItemSuccess(List<Item> itemList);

        void onCategoryError(String msg);
        void onItemError(String msg);
    }

    interface Presenter{
        void onGetItem();
        void onGetCategory();

    }

    interface Interactor{

        void onGetItem(InteractorItemsResponse interactorItemsResponse);
        void onGetCategory(InteractorCategoryResponse interactorCategoryResponse);

        interface InteractorCategoryResponse{
            void onCategorySuccess(List<Category> categoryList);
            void onError(String msg);
        }
        interface InteractorItemsResponse{
            void onItemSuccess(List<Item> itemList);
            void onError(String msg);
        }

    }
}
