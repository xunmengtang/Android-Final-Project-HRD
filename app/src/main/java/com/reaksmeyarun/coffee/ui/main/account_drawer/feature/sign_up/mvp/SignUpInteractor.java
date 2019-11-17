package com.reaksmeyarun.coffee.ui.main.account_drawer.feature.sign_up.mvp;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.reaksmeyarun.coffee.model.User;

import java.util.HashMap;
import java.util.Map;

public class SignUpInteractor implements SignUpMVP.Interactor {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    static final String USER_NODE = "User";
    DatabaseReference databaseReference = database.getReference(USER_NODE);

    @Override
    public void onSignUp(User user, InteractorSignUpResponse interactorSignUpResponse) {
        if(user!=null){
            auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser userAuth = auth.getCurrentUser();
                                userAuth.sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    auth.getUid();
                                                    saveUser(databaseReference,new User(auth.getUid(),
                                                            user.getUser(),
                                                            user.getEmail(),
                                                            user.getRule(),
                                                            user.getStatus()));
                                                    interactorSignUpResponse.onSuccess("Successful!");
                                                }else {
                                                    interactorSignUpResponse.onFailure("Email already use");
                                                }
                                            }
                                        });
                            }
                        }
                    });
        }else {

        }
    }

    public void saveUser(DatabaseReference reference, User user){
        Map<String ,Object> updateValues=new HashMap<>();
        updateValues.put(auth.getUid(),user.toMap());
        reference.updateChildren(updateValues);
    }
}
