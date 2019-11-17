package com.reaksmeyarun.coffee.ui.main.fragment_menu.feature.category.category.mvp;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.reaksmeyarun.coffee.model.Category;
import com.reaksmeyarun.coffee.model.Item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public  class CategoryInteractor implements CategoryMVP.Interactor {

    private final String CATEGORY_NODE = "category";
    private final String ITEM_NODE = "item";

    private FirebaseDatabase firebaseDatabase =  FirebaseDatabase.getInstance();
    private DatabaseReference categoryReference = firebaseDatabase.getReference(CATEGORY_NODE);
    private DatabaseReference itemReference = firebaseDatabase.getReference(ITEM_NODE);

    List<Category> categoryList = new ArrayList<>();
    List<Item> itemList = new ArrayList<>();

    @Override
    public void onGetCategory(InteractorResponse interactorResponse) {
        categoryReference.orderByChild("categoryName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                Iterator<DataSnapshot> dataSnapshotIterator = dataSnapshot.getChildren().iterator();
                while (dataSnapshotIterator.hasNext()){
                    Category category = dataSnapshotIterator.next().getValue(Category.class);
                    categoryList.add(category);
                }
                interactorResponse.onGetSuccess(categoryList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                interactorResponse.onGetFaild("Something wrong!");
            }
        });
    }

    @Override
    public void onGetItem(InteractorItemResponse interactorItemResponse) {
        itemReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> dataSnapshotIterator = dataSnapshot.getChildren().iterator();
                while (dataSnapshotIterator.hasNext()){
                    Item item = dataSnapshotIterator.next().getValue(Item.class);
                    itemList.add(item);
                }
                interactorItemResponse.onGetSuccess(itemList);
                itemList.clear();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                interactorItemResponse.onGetFaild("Something wrong!");
            }
        });
    }

    @Override
    public void onDeleteCategory(Category category, List<Item> itemList, InteractorDeleteResponse interactorDeleteResponse) {
        if(category.getId()!=null && itemList!=null){
            for(int i = 0; i < itemList.size(); i++) {
                if (itemList.get(i).getCategoryID().equals(category.getId())) {
                    String itemID = itemList.get(i).getId();
                    DatabaseReference databaseReferenceItem = firebaseDatabase.getReference().child(ITEM_NODE).child(itemID);
                    databaseReferenceItem.removeValue();
                }
            }
            DatabaseReference databaseReferenceCategory = firebaseDatabase.getReference().child(CATEGORY_NODE).child(category.getId());
            databaseReferenceCategory.removeValue();
            interactorDeleteResponse.onDeleteSuccess("Successful!");
        }else {
            interactorDeleteResponse.onDeleteFail("Something gone wrong!");
        }
    }

}
