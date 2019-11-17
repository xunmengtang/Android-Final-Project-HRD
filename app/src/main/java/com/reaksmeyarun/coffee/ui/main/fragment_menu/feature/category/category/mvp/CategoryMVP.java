package com.reaksmeyarun.coffee.ui.main.fragment_menu.feature.category.category.mvp;

import com.reaksmeyarun.coffee.model.Category;
import com.reaksmeyarun.coffee.model.Item;

import java.util.List;

public interface CategoryMVP {

    interface View{
        void onShowLoading();
        void onHideLoading();

        void onCategorySuccess(List<Category> categoryList);
        void onCategoryFail(String msg);

        void onItemSuccess(List<Item> items);
        void onItemFail(String msg);

        void onDeleteSuccess(String msg);
        void onDeleteFail(String msg);
    }

    interface Presenter{

        void onGetCategory();
        void onGetItem();
        void onDeleteCategory(Category category, List<Item> itemList);
    }

    interface Interactor{

        void onGetCategory(InteractorResponse interactorResponse);
        void onGetItem(InteractorItemResponse interactorItemResponse);
        void onDeleteCategory(Category category, List<Item> itemList, InteractorDeleteResponse interactorDeleteResponse);

        interface InteractorResponse{
            void onGetSuccess(List<Category> categoryList);
            void onGetFaild(String msg);
        }
        interface InteractorItemResponse{
            void onGetSuccess(List<Item> itemList);
            void onGetFaild(String msg);
        }

        interface InteractorDeleteResponse{
            void onDeleteSuccess(String msg);
            void onDeleteFail(String msg);
        }
    }
}
