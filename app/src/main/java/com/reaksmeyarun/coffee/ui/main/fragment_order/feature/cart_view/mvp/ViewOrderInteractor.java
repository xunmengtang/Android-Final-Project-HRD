package com.reaksmeyarun.coffee.ui.main.fragment_order.feature.cart_view.mvp;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.reaksmeyarun.coffee.model.Global;
import com.reaksmeyarun.coffee.model.Receipt;
import com.reaksmeyarun.coffee.model.Table;

public class ViewOrderInteractor implements ViewOrderMVP.Interactor {

    private final String RECEIPT_NODE = "receipt";
    private final String TABLENODE = "table";

    private FirebaseDatabase firebaseDatabase =  FirebaseDatabase.getInstance();
    private DatabaseReference receiptReference = firebaseDatabase.getReference(RECEIPT_NODE);
    private DatabaseReference tableReference = firebaseDatabase.getReference(TABLENODE);

    @Override
    public void onPushTable(Table table, OnPushTableResponse onPushTableResponse) {
        if(table.getTableID()!=null){
            tableReference.child(table.getTableID()).setValue(table);
            onPushTableResponse.onPushSuccess("Successful!");
        }else {
            onPushTableResponse.onPushFailure("Can't save");
        }
    }

    @Override
    public void onGetTable(String id, OnGetTableResponse onGetTableResponse) {
        tableReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Table table = dataSnapshot.child(id).getValue(Table.class);
                onGetTableResponse.onSuccess(table);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                onGetTableResponse.onFailure("Fail!");
            }
        });
    }

    @Override
    public void onPushReceipt(String tableId, Receipt receipt, InteractorReceiptResponse interactorReceiptResponse) {
        if(receipt!=null){
            receiptReference.child(receipt.getId()).setValue(receipt);
            tableReference.child(tableId).child("status").setValue(Global.AVAILABLE);
            tableReference.child(tableId).child("itemList").removeValue();
            interactorReceiptResponse.onSuccess("Successful!");
        }else {
            interactorReceiptResponse.onFailure("Something went wrong!");
        }
    }
}
