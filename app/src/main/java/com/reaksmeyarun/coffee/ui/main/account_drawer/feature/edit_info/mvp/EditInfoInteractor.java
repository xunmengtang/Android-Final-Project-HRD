package com.reaksmeyarun.coffee.ui.main.account_drawer.feature.edit_info.mvp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.reaksmeyarun.coffee.model.User;

import java.util.HashMap;
import java.util.Map;

public class EditInfoInteractor implements EditInfoMVP.Interactor {


    private final String USER_NODE = "User";

    private FirebaseDatabase firebaseDatabase =  FirebaseDatabase.getInstance();
    private DatabaseReference userReference = firebaseDatabase.getReference(USER_NODE);

    @Override
    public void onUpdateUser(User user, OnUpdateUserResponse onUpdateUserResponse) {
        if(user!=null){
            updateItem(userReference,user);
            onUpdateUserResponse.onSuccess("Update Successful");
        }else {
            onUpdateUserResponse.onFail("Something gone wrong!");
        }
    }
    private void updateItem(DatabaseReference databaseReference, User user){
        databaseReference.child(user.getAuthID());
        Map<String, Object> updateValue = new HashMap<>();
        updateValue.put(user.getAuthID(),user.toMap());
        databaseReference.updateChildren(updateValue);
    }
}
