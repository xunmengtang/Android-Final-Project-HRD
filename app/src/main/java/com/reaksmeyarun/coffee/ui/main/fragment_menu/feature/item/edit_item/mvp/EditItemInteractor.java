package com.reaksmeyarun.coffee.ui.main.fragment_menu.feature.item.edit_item.mvp;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.reaksmeyarun.coffee.model.Category;
import com.reaksmeyarun.coffee.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EditItemInteractor implements EditItemMVP.Interactor{

    private final String CATEGORY_NODE = "category";
    private final String ITEM_NODE = "item";

    private FirebaseDatabase firebaseDatabase =  FirebaseDatabase.getInstance();
    private DatabaseReference categoryReference = firebaseDatabase.getReference(CATEGORY_NODE);
    private DatabaseReference itemReference = firebaseDatabase.getReference(ITEM_NODE);

    List<Category> categoryList = new ArrayList<>();

    @Override
    public void onGetCategorySuccess(InteractorCategoryRespone interactorCategoryRespone) {
        categoryReference.orderByChild("categoryName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                Iterator<DataSnapshot> dataSnapshotIterator = dataSnapshot.getChildren().iterator();
                while (dataSnapshotIterator.hasNext()){
                    Category category = dataSnapshotIterator.next().getValue(Category.class);
                    categoryList.add(category);
                }
                interactorCategoryRespone.onSuccess(categoryList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                interactorCategoryRespone.onFail("Something went wrong!");
            }
        });
    }

    @Override
    public void onUpdateItem(Item item, InteractorItemUpdateResponse interactorItemUpdateResponse) {
        if(item!=null){
            updateItem(itemReference,item);
            interactorItemUpdateResponse.onSuccess("Success");
        }else {
            interactorItemUpdateResponse.onFail("Something gone wrong!");
        }
    }

    @Override
    public void onPushItem(Item item, InteractorPushItemResponse interactorPushItemResponse) {
        if(item!=null){
            createItem(itemReference,item);
            interactorPushItemResponse.onPushSuccess("Add Successful");
        }else {
            interactorPushItemResponse.onPushError("Something gone wrong!");
        }
    }

    private void updateItem(DatabaseReference databaseReference, Item item){
        databaseReference.child(item.getId());
        Map<String, Object> updateValue = new HashMap<>();
        updateValue.put(item.getId(),item.toMap());
        databaseReference.updateChildren(updateValue);
    }

    private void createItem(DatabaseReference databaseReference, Item item){
        Map<String, Object> updateValue = new HashMap<>();
        updateValue.put(item.getId(),item.toMap());
        databaseReference.updateChildren(updateValue);

    }
}
