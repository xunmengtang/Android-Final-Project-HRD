package com.reaksmeyarun.coffee.ui.main.fragment_menu.feature.category.edit_category.mvp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.reaksmeyarun.coffee.model.Category;

import java.util.HashMap;
import java.util.Map;

public class EditCategoryInteractor implements EditCategoryMVP.Interactor {


    private final String CATEGORY_NODE = "category";

    private FirebaseDatabase firebaseDatabase =  FirebaseDatabase.getInstance();
    private DatabaseReference categoryReference = firebaseDatabase.getReference(CATEGORY_NODE);
    private DatabaseReference categoryRef = firebaseDatabase.getReference(CATEGORY_NODE);

    @Override
    public void onEditCategory(Category category, InteractorCategoryEditResponse interactorCategoryEditResponse) {
        if(category!=null){
            updateItem(categoryReference, category);
            interactorCategoryEditResponse.onEditSuccess(category);
        }else {
            interactorCategoryEditResponse.onEditFail("Something gone wrong!");
        }
    }

    @Override
    public void createCate(Category category, InteractorResponse interactorResponse) {
        if(category!=null ){
            createCategory(categoryRef, category);
            interactorResponse.onSuccess("Create Successful");
        }else {
            interactorResponse.onError("Something gone wrong!");
        }
    }

    private void updateItem(DatabaseReference databaseReference, Category category) {
        databaseReference.child(category.getId());
        Map<String, Object> updateValue = new HashMap<>();
        updateValue.put(category.getId(), category.toMap());
        databaseReference.updateChildren(updateValue);
    }

    private void createCategory(DatabaseReference databaseReference, Category category){
        Map<String, Object> updateValue = new HashMap<>();
        updateValue.put(category.getId(),category.toMap());
        databaseReference.updateChildren(updateValue);
    }
}
