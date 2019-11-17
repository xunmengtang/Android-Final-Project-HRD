package com.reaksmeyarun.coffee.ui.main.fragment_order.feature.receipt_view.mvp;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.reaksmeyarun.coffee.model.Receipt;
import com.reaksmeyarun.coffee.model.User;

public class ReceiptInteractor implements ReceiptMVP.Interactor {

    private FirebaseDatabase firebaseDatabase =  FirebaseDatabase.getInstance();

    private final String RECEIPT_NODE = "receipt";
    private final String USER_NODE = "User";
    private DatabaseReference receiptReference = firebaseDatabase.getReference(RECEIPT_NODE);
    private DatabaseReference createByReference = firebaseDatabase.getReference(USER_NODE);

    @Override
    public void onGetReceipt(String ID, InteractorGetReceiptResponse interactorGetReceiptResponse) {
        receiptReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Receipt receipt = dataSnapshot.child(ID).getValue(Receipt.class);
                interactorGetReceiptResponse.onSuccess(receipt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                interactorGetReceiptResponse.onFailure("Something gone wrong!");
            }
        });
    }

    @Override
    public void onGetCreateBy(String ID, InteractorGetCreateByResponse interactorGetCreateByResponse) {
        createByReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.child(ID).getValue(User.class);
                interactorGetCreateByResponse.onSuccess(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                interactorGetCreateByResponse.onFailure("Something went wrong!");
            }
        });
    }
}
