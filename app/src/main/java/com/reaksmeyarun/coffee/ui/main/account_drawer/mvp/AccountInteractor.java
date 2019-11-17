package com.reaksmeyarun.coffee.ui.main.account_drawer.mvp;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.reaksmeyarun.coffee.model.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AccountInteractor implements AccountMVP.Interactor {


    private final String USER_NODE = "User";

    private FirebaseDatabase firebaseDatabase =  FirebaseDatabase.getInstance();
    private DatabaseReference userReference = firebaseDatabase.getReference(USER_NODE);
    private List<User> userList = new ArrayList<>();

    @Override
    public void onGetUser(OnGetUserResponse onGetUserResponse) {
        userReference.orderByChild("rule").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> dataSnapshotIterator = dataSnapshot.getChildren().iterator();
                while (dataSnapshotIterator.hasNext()){
                    User user = dataSnapshotIterator.next().getValue(User.class);
                    userList.add(user);
                }
                onGetUserResponse.onGetSuccess(userList);
                userList.clear();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                onGetUserResponse.onGetFail("Something gone wrong!");
            }
        });
    }

}
