package com.reaksmeyarun.coffee.ui.main.fragment_order;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.reaksmeyarun.coffee.R;
import com.reaksmeyarun.coffee.model.Global;
import com.reaksmeyarun.coffee.model.Table;
import com.reaksmeyarun.coffee.ui.main.fragment_order.feature.cart_view.ViewOrderActivity;
import com.reaksmeyarun.coffee.ui.main.fragment_order.feature.create_order.CreateOrderActivity;
import com.reaksmeyarun.coffee.ui.main.fragment_order.mvp.OrderMVP;
import com.reaksmeyarun.coffee.ui.main.fragment_order.mvp.OrderPresenter;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment implements OrderMVP.View, OrderAdapter.CallBack {

    private View view;
    private EditText etSearch;
    private OrderAdapter orderAdapter;
    private List<Table> tableList = new ArrayList<>();
    private RecyclerView rvTable;
    private ImageView btnShortBy;
    private String postion ;
    private OrderMVP.Presenter presenter;

    private static OrderFragment orderFragment;

    public static OrderFragment getInstance(){
        if(orderFragment ==null)
            return new OrderFragment();
        return orderFragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.order_fragment,container,false);

        initUI();
        presenter = new OrderPresenter(this);
        presenter.onGetTable();
        setUpRV(tableList);
        return view;
    }

    private void initUI() {
        rvTable = view.findViewById(R.id.rvTable);
        etSearch = view.findViewById(R.id.etSearch);
        btnShortBy = view.findViewById(R.id.btnSortBy);

        btnShortBy.setOnClickListener(v->{
            PopupMenu menu=new PopupMenu(getContext(),v);
            menu.inflate(R.menu.sort_by_table_menu);
            menu.setOnMenuItemClickListener(b->{
                switch (b.getItemId()){
                    case R.id.btnAll:
                        orderAdapter.getFilter().filter("");
                        return true;
                    case R.id.btnAvailable:
                        orderAdapter.getFilter().filter(Global.AVAILABLE);
                        return true;
                    case R.id.btnBooked:
                        orderAdapter.getFilter().filter(Global.BOOKED);
                        return true;
                    default : return false;
                }
            });
            menu.show();
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                orderAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setUpRV(List<Table> tableList){
        orderAdapter = new OrderAdapter(getContext(),tableList);
        orderAdapter.setCallBack(this);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext());
        //set recyclerView with reverse data of list
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayout.setReverseLayout(false);
        linearLayout.setStackFromEnd(false);
        rvTable.setLayoutManager(linearLayout);
        rvTable.setAdapter(orderAdapter);
    }

    @Override
    public void onTableSuccess(List<Table> tableList) {
        orderAdapter.tableList.clear();
        orderAdapter.addMoreReceipt(tableList);
    }

    @Override
    public void onTableFail(String msg) {

    }

    @Override
    public void onTableView(String ID) {
        Global.view = "VIEW";
        Global.currentTable=ID;
        Intent intent = new Intent(getContext(), ViewOrderActivity.class);
//        intent.putExtra("ID", ID);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onOrder(String id) {
        Global.currentTable=id;
        Global.view = "ORDER";
        startActivity(new Intent(getContext(), CreateOrderActivity.class));
        getActivity().finish();
    }

}
