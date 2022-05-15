package com.podorozhnik.managers;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.podorozhnik.entities.Request;
import com.podorozhnik.enums.OperationResults;
import com.podorozhnik.final_values.DatabaseValues;
import com.podorozhnik.fragments.CreateFragment;

public class RequestsManager implements ValueEventListener {
    private Request request;
    private CreateFragment fragmentReference;

    public RequestsManager(Request request, CreateFragment fragmentReference){
        this.request = request;
        this.fragmentReference = fragmentReference;
    }
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        FirebaseDatabase.getInstance()
                .getReference()
                .child(DatabaseValues.REQUESTS_TABLE)
                .push()
                .setValue(request);

        fragmentReference.onResultReceived(OperationResults.SUCCESS);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        fragmentReference.onResultReceived(OperationResults.DATABASE_ERROR);
    }

    public void publishRequest() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference requests = database.child(DatabaseValues.REQUESTS_TABLE);
        requests.addListenerForSingleValueEvent(this);
    }
}
