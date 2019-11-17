package com.reaksmeyarun.coffee.ui.main.fragment_order.mvp;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.reaksmeyarun.coffee.model.Table;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OrderInteractor implements OrderMVP.Interactor {

    private final String TABLENODE = "table";
    private List<Table> tableList = new ArrayList<>();

    private FirebaseDatabase firebaseDatabase =  FirebaseDatabase.getInstance();
    private DatabaseReference tableReference = firebaseDatabase.getReference(TABLENODE);

    @Override
    public void onGetTable(OnGetTableResponse onGetTableResponse) {
        tableReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> dataSnapshotIterator = dataSnapshot.getChildren().iterator();
                while (dataSnapshotIterator.hasNext()) {
                    Table table = dataSnapshotIterator.next().getValue(Table.class);
                    tableList.add(table);
                }
                onGetTableResponse.onSuccess(tableList);
                tableList.clear();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                onGetTableResponse.onFail("No data");
            }
        });
    }
}
