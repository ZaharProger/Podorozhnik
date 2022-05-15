package com.podorozhnik.managers;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.podorozhnik.entities.User;
import com.podorozhnik.enums.OperationResults;
import com.podorozhnik.final_values.DatabaseValues;
import com.podorozhnik.fragments.RegisterFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AccountCreator implements ValueEventListener {
    private User userData;
    private RegisterFragment fragmentReference;

    public AccountCreator(User userData, RegisterFragment fragmentReference){
        this.userData = userData;
        this.fragmentReference = fragmentReference;
    }

    public void createAccount(){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference users = database.child(DatabaseValues.USERS_TABLE);
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

        if (!isFound){
            List<Integer> usersId = users.stream()
                                        .map(user -> user.getId())
                                        .collect(Collectors.toList());

            int newUserId;
            do{
                newUserId = Generator.generateId(10);
            } while (usersId.contains(newUserId));

            userData.setId(newUserId);
            FirebaseDatabase.getInstance()
                            .getReference()
                            .child(DatabaseValues.USERS_TABLE)
                            .push()
                            .setValue(userData);

            result = OperationResults.SUCCESS;
        }
        else
            result = OperationResults.EXISTING_LOGIN;

        fragmentReference.onResultReceived(result);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        fragmentReference.onResultReceived(OperationResults.DATABASE_ERROR);
    }
}
