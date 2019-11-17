package com.reaksmeyarun.coffee.ui.main.fragment_order.mvp;

import com.reaksmeyarun.coffee.model.Table;

import java.util.List;

public interface OrderMVP {

    interface View{
        void onTableSuccess(List<Table> tableList);
        void onTableFail(String msg);
    }

    interface Presenter{
        void onGetTable();
    }

    interface Interactor{
        void onGetTable(OnGetTableResponse onGetTableResponse);

        interface OnGetTableResponse{
            void onSuccess(List<Table> tableList);
            void onFail(String msg);
        }
    }
}
