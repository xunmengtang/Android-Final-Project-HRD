package com.reaksmeyarun.coffee.ui.sign_in.mvp;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.reaksmeyarun.coffee.model.Global;
import com.reaksmeyarun.coffee.model.User;

public class SignInInteractor implements SignInMVP.Interactor {

    private FirebaseAuth mAuth;
    private final String USER_NODE = "User";
    private final String STATUS = "status";
    private FirebaseDatabase firebaseDatabase =  FirebaseDatabase.getInstance();
    private DatabaseReference userReference = firebaseDatabase.getReference(USER_NODE);

    @Override
    public void onGetCurrentUser(InteractorGetUserResponse interactorGetUserResponse) {
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mAuth = FirebaseAuth.getInstance();
                User user = dataSnapshot.child(mAuth.getUid()).getValue(User.class);
                interactorGetUserResponse.onGetSuccess(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                interactorGetUserResponse.onGetFailure("Something went wrong!");
            }
        });
    }

    @Override
    public void onSetStatus(String id, InteractorSetStatusResponse interactorSetStatusResponse) {
        if(id!=null){
            userReference.child(id).child(STATUS).setValue(Global.ONLINE);
            interactorSetStatusResponse.onSetSuccess("Successful");
        }else {
            interactorSetStatusResponse.onSetFailure("Fail!");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void authenticat(User user, InteractorResponse response) {
            mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(user.getEmail(),user.getPassword())
                    .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                String id = mAuth.getUid();
                                response.onLoginSuccess(id);
                            }else {
                                response.onLoginFail("Email or password is incorrect");
                            }
                        }
                    });
    }

}
