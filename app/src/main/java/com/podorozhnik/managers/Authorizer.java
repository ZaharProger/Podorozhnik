package com.podorozhnik.managers;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.podorozhnik.entities.User;
import com.podorozhnik.enums.OperationResults;
import com.podorozhnik.fragments.LoginFragment;

import java.util.ArrayList;

public class Authorizer implements ValueEventListener {
    private User userData;
    private LoginFragment fragmentReference;

    public Authorizer(User userData, LoginFragment fragmentReference){
        this.userData = userData;
        this.fragmentReference = fragmentReference;
    }

    public void doAuthorization(){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference users = database.child("users");
        users.addListenerForSingleValueEvent(this);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        OperationResults result;

        ArrayList<User> users = new ArrayList<>();

        for (DataSnapshot databaseChildren : dataSnapshot.getChildren()){
            users.add(databaseChildren.getValue(User.class));
        }

        boolean isFound = false;

        int i;
        for (i = 0; i < users.size() && !isFound; ++i){
            isFound = users.get(i).getLogin().equals(userData.getLogin());
        }

        if (isFound){
            if (users.get(i - 1).getPassword().equals(userData.getPassword()))
                result = OperationResults.SUCCESS;
            else
                result = OperationResults.WRONG_PASSWORD;
        }
        else
            result = OperationResults.WRONG_LOGIN;

        fragmentReference.onResultReceived(result);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
    }
}
