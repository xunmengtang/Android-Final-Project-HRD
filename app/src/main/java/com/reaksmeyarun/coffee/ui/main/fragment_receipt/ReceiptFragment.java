package com.reaksmeyarun.coffee.ui.main.fragment_receipt;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.reaksmeyarun.coffee.R;
import com.reaksmeyarun.coffee.model.Receipt;
import com.reaksmeyarun.coffee.ui.main.fragment_order.feature.receipt_view.ReceiptActivity;
import com.reaksmeyarun.coffee.ui.main.fragment_receipt.mvp.ReceiptMVP;
import com.reaksmeyarun.coffee.ui.main.fragment_receipt.mvp.ReceiptPresenter;

import java.util.ArrayList;
import java.util.List;

public class ReceiptFragment extends Fragment implements ReceiptMVP.View, ReceiptAdapter.CallBack {

    private View view;
    private EditText etSearch;
    private ReceiptMVP.Presenter presenter;
    private ReceiptAdapter receiptAdapter;
    private List<Receipt> receiptList = new ArrayList<>();
    private RecyclerView rvReceipt;
    private String postion ;

    private static ReceiptFragment receiptFragment;

    public static ReceiptFragment getInstance(){
        if(receiptFragment ==null)
            return new ReceiptFragment();
        return receiptFragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.receipt_fragment,container,false);

        presenter = new ReceiptPresenter(this);
        presenter.onGetReceipt();

        initUI();
        setUpRV(receiptList);
        return view;
    }

    private void initUI() {
        rvReceipt = view.findViewById(R.id.rvTable);
        etSearch = view.findViewById(R.id.etSearch);


        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                receiptAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setUpRV(List<Receipt> receiptList){
        receiptAdapter = new ReceiptAdapter(getContext(),receiptList);
        receiptAdapter.setCallBack(this);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext());
        //set recyclerView with reverse data of list
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayout.setReverseLayout(true);
        linearLayout.setStackFromEnd(true);
        rvReceipt.setLayoutManager(linearLayout);
        rvReceipt.setAdapter(receiptAdapter);
    }
    @Override
    public void onShowLoading() {
    }

    @Override
    public void onHideLoading() {
    }

    @Override
    public void onGetReceiptSuccess(List<Receipt> receipt) {
        //this.receiptList = receipt;
        receiptAdapter.receiptList.clear();
        receiptAdapter.addMoreReceipt(receipt);
    }

    @Override
    public void onGetReceptFailure(String msg) {
        Toast.makeText(getContext(), ""+msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReceiptView(String ID) {
        Intent intent = new Intent(getContext(), ReceiptActivity.class);
        intent.putExtra("ID", ID);
        startActivity(intent);
        getActivity().finish();
    }
}
