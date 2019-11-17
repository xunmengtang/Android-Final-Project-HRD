package com.reaksmeyarun.coffee.ui.main.mvp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.reaksmeyarun.coffee.model.Global;

public class MainInteractor implements MainMVP.Interactor {


    private final String USER_NODE = "User";
    private final String STATUS = "status";

    private FirebaseDatabase firebaseDatabase =  FirebaseDatabase.getInstance();
    private DatabaseReference userReference = firebaseDatabase.getReference(USER_NODE);

    @Override
    public void onSetStatus(String id, InteractorSetStatusResponse interactorSetStatusResponse) {
        if(id!=null){
            userReference.child(id).child(STATUS).setValue(Global.OFFLINE);
            interactorSetStatusResponse.onSetSuccess("Sign out successful");
        }else {
            interactorSetStatusResponse.onSetFailure("Fail!");
        }
    }
}
