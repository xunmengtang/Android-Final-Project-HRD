package com.reaksmeyarun.coffee.ui.main.account_drawer.feature.edit_info.mvp;

import com.reaksmeyarun.coffee.model.User;

public class EditInfoPresenter implements EditInfoMVP.Presenter {

    EditInfoMVP.View view;
    EditInfoMVP.Interactor interactor;

    public EditInfoPresenter(EditInfoMVP.View view) {
        this.view = view;
        interactor = new EditInfoInteractor();
    }

    @Override
    public void onUpdateUser(User user) {
        if(view!=null){
            view.onShowLoading();
            interactor.onUpdateUser(user, new EditInfoMVP.Interactor.OnUpdateUserResponse() {
                @Override
                public void onSuccess(String msg) {
                    view.onHideLoading();
                    view.onUpdateSuccess("Update information successful");
                }

                @Override
                public void onFail(String msg) {
                    view.onHideLoading();
                    view.onUpdateFail("Can't not update information");
                }
            });
        }
    }
}
