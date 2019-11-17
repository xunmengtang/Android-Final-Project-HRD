package com.reaksmeyarun.coffee.ui.main.fragment_menu.feature.item.edit_item.mvp;

import com.reaksmeyarun.coffee.model.Category;
import com.reaksmeyarun.coffee.model.Item;

import java.util.List;

public interface EditItemMVP {

    interface View{
        void onShowLoading();
        void onHideLoading();
        void onCategorySuccess(List<Category> categoryList);
        void onError(String msg);
        void onUpdateItemSuccess();
        void onPushItemSuccess(String msg);
        void onPushItemFail(String msg);
    }

    interface Presenter{
        void onGetCategory();
        void onUpdateItem(Item item);
        void onPushItem(Item item);
    }

    interface Interactor{
        void onGetCategorySuccess(InteractorCategoryRespone interactorCategoryRespone);
        void onUpdateItem(Item item, InteractorItemUpdateResponse interactorItemUpdateResponse);
        void onPushItem(Item item, InteractorPushItemResponse interactorPushItemResponse);

        interface InteractorCategoryRespone{
            void onSuccess(List<Category> categoryList);
            void onFail(String msg);
        }

        interface InteractorItemUpdateResponse{
            void onSuccess(String msg);
            void onFail(String msg);
        }

        interface InteractorPushItemResponse {
            void onPushSuccess(String msg);
            void onPushError(String msg);
        }
    }
}
