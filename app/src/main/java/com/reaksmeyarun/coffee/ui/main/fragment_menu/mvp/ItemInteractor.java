package com.reaksmeyarun.coffee.ui.main.fragment_menu.mvp;

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

public class ItemInteractor implements ItemMVP.Interactor {

    private final String CATEGORY_NODE = "category";
    private final String ITEM_NODE = "item";
    private final String SORTITEM_NODE = "itemName";
    private final String SORTCATEGORY_NODE = "categoryName";

    private FirebaseDatabase firebaseDatabase =  FirebaseDatabase.getInstance();
    private DatabaseReference categoryReference = firebaseDatabase.getReference(CATEGORY_NODE);
    private DatabaseReference itemReference = firebaseDatabase.getReference(ITEM_NODE);

    List<Category> categoryList = new ArrayList<>();
    List<Item> itemList = new ArrayList<>();


    @Override
    public void onGetCategory(InteractorCategoryResponse interactorCategoryResponse) {
        categoryReference.orderByChild(SORTCATEGORY_NODE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                Iterator<DataSnapshot> dataSnapshotIterator = dataSnapshot.getChildren().iterator();
                while (dataSnapshotIterator.hasNext()){
                    Category category = dataSnapshotIterator.next().getValue(Category.class);
                    categoryList.add(category);
                }
                interactorCategoryResponse.onCategorySuccess(categoryList);
                categoryList.clear();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                interactorCategoryResponse.onError("Something wrong!");
            }
        });
    }

    @Override
    public void onGetItem(InteractorItemsResponse interactorItemsResponse) {
        itemReference.orderByChild(SORTITEM_NODE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> dataSnapshotIterator = dataSnapshot.getChildren().iterator();
                while (dataSnapshotIterator.hasNext()){
                    Item item = dataSnapshotIterator.next().getValue(Item.class);
                    itemList.add(item);
                }
                interactorItemsResponse.onItemSuccess(itemList);
                itemList.clear();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                interactorItemsResponse.onError("Something wrong!");
            }
        });
    }

}
