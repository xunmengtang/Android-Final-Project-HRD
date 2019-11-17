package com.reaksmeyarun.coffee.ui.main.fragment_menu.feature.category.edit_category.mvp;


import com.reaksmeyarun.coffee.model.Category;

public class EditCategoryPresenter implements EditCategoryMVP.Presenter {

    private EditCategoryMVP.Interactor interactor;
    private EditCategoryMVP.View view;

    public EditCategoryPresenter(EditCategoryMVP.View view) {
        this.view = view;
        interactor = new EditCategoryInteractor();
    }

    @Override
    public void onEditCategry(Category category) {
            if(view!=null){
                view.onShowLoading();
                interactor.onEditCategory(category, new EditCategoryMVP.Interactor.InteractorCategoryEditResponse() {
                    @Override
                    public void onEditSuccess(Category category) {
                        view.onHideLoading();
                        view.onEditCategorySuccess("Edit Successful!");
                    }

                    @Override
                    public void onEditFail(String msg) {
                        view.onHideLoading();
                        view.onEditError("Something gone wrong!");
                    }
                });
            }
    }

    @Override
    public void onCreateCategory(Category category) {
        if(view!=null){
            view.onShowLoading();
            interactor.createCate(category, new EditCategoryMVP.Interactor.InteractorResponse() {
                @Override
                public void onSuccess(String msg) {
                    view.onHideLoading();
                    view.onSuccess(msg);
                }

                @Override
                public void onError(String msg) {
                    view.onHideLoading();
                    view.onError(msg);
                }
            });
        }
    }
}
