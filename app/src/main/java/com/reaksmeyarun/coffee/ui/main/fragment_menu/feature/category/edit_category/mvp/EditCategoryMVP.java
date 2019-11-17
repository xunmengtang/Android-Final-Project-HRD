package com.reaksmeyarun.coffee.ui.main.fragment_menu.feature.category.edit_category.mvp;

import com.reaksmeyarun.coffee.model.Category;

public interface EditCategoryMVP {

    interface View{
        void onShowLoading();
        void onHideLoading();

        void onEditCategorySuccess(String msg);
        void onEditError(String msg);

        void onSuccess(String msg);
        void onError(String msg);
    }

    interface Presenter{
        void onEditCategry(Category category);
        void onCreateCategory(Category category);
    }

    interface Interactor{
        void onEditCategory(Category category, InteractorCategoryEditResponse interactorCategoryEditResponse);
        void createCate(Category category, InteractorResponse interactorResponse);

        interface InteractorResponse{
            void onSuccess(String msg);
            void onError(String msg);
        }
        interface InteractorCategoryEditResponse{
            void onEditSuccess(Category category);
            void onEditFail(String msg);
        }
    }
}
