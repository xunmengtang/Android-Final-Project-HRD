package com.reaksmeyarun.coffee.ui.main.fragment_menu.feature.item.item.mvp;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.reaksmeyarun.coffee.model.Item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ItemInteractor implements ItemMVP.Interactor {

    private final String ITEM_NODE = "item";

    private FirebaseDatabase firebaseDatabase =  FirebaseDatabase.getInstance();
    private DatabaseReference itemReference = firebaseDatabase.getReference(ITEM_NODE);

    List<Item> itemList = new ArrayList<>();
    @Override
    public void onGetItem(InteractorItemResponse interactorItemResponse) {
        itemReference.orderByChild("itemName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> dataSnapshotIterator = dataSnapshot.getChildren().iterator();
                while (dataSnapshotIterator.hasNext()){
                    Item item = dataSnapshotIterator.next().getValue(Item.class);
                    itemList.add(item);
                }
                interactorItemResponse.onGetSuccess(itemList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                interactorItemResponse.onGetFail("Something wrong!");
            }
        });
    }

    @Override
    public void onDeleteItem(Item item, InteractorDeleteItemResponse interactorDeleteItemResponse) {
        if(item.getId()!=null){
            DatabaseReference databaseReferenceItem = firebaseDatabase.getReference().child(ITEM_NODE).child(item.getId());
            databaseReferenceItem.removeValue();
            interactorDeleteItemResponse.onDeleteItemSuccess("Successful!");
        }else {
            interactorDeleteItemResponse.onDeleteItemError("Something gone wrong!");
        }
    }
}
