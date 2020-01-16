package com.example.activities.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.activities.R;
import com.example.activities.data.entities.Activity;
import com.example.activities.data.rtdb.ShowActivities;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ShowHistorySearch extends AppCompatActivity {
    private Button backToProfile;
    private Button showJoinHistory;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_search);

        backToProfile = findViewById(R.id.backToProfile);
        showJoinHistory = findViewById(R.id.showJoinedHistory);
        ArrayList<Activity> historyArray = new ArrayList<Activity>();

        backToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ShowHistorySearch.this, UserProfile.class);
                i.putExtra("email",FirebaseAuth.getInstance().getCurrentUser().getEmail());
                startActivity(i);
                finish();
            }
        });

        showJoinHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef = FirebaseDatabase.getInstance().getReference("users_history_joined");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).exists()) {
                            myRef = FirebaseDatabase.getInstance().getReference("users_history_joined/" + FirebaseAuth.getInstance().getCurrentUser().getUid());
                            myRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    historyArray.clear();
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        historyArray.add(ds.getValue(Activity.class));
                                    }
                                    if (historyArray.size() > 0) {
                                        Intent intent = new Intent(ShowHistorySearch.this, ShowActivities.class);
                                        intent.putExtra("activitiesArray", historyArray);
                                        ShowActivities.activityFilter = false;
                                        startActivity(intent);
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        } else {
                            Toast.makeText(ShowHistorySearch.this, "You didnt joined any activity right now.", Toast.LENGTH_LONG).show();
                        }
                         myRef.removeEventListener(this);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}