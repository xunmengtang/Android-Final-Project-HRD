package com.reaksmeyarun.coffee.ui.main.fragment_receipt.mvp;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.reaksmeyarun.coffee.model.Receipt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReceiptInteractor implements ReceiptMVP.Interactor {

    private final String RECEIPT_NODE = "receipt";
    private final String SORTBYDATE_NODE = "sortDate";
    private List<Receipt> receiptList = new ArrayList<>();

    private FirebaseDatabase firebaseDatabase =  FirebaseDatabase.getInstance();
    private DatabaseReference receiptReference = firebaseDatabase.getReference(RECEIPT_NODE);

    @Override
    public void onGetReceipt(InteractorGetReceipt interactorGetReceipt) {
        receiptReference.orderByChild(SORTBYDATE_NODE).limitToLast(50).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> dataSnapshotIterator = dataSnapshot.getChildren().iterator();
                while (dataSnapshotIterator.hasNext()){
                    Receipt receipt = dataSnapshotIterator.next().getValue(Receipt.class);
                    receiptList.add(receipt);
                }
                interactorGetReceipt.onSuccess(receiptList);
                receiptList.clear();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                interactorGetReceipt.onFailure("No data");
            }
        });
    }
}
