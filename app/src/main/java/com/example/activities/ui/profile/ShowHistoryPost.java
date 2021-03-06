package com.example.activities.ui.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.activities.R;
import com.example.activities.data.entities.Activity;
import com.example.activities.data.entities.user.User;
import com.example.activities.data.rtdb.ShowActivities;
import com.example.activities.util.UpdateFirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowHistoryPost extends AppCompatActivity {
    private Button backToProfile;
    private Button showJoinHistory;
    private Button showPostsHistory;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_post);
        UpdateFirebaseDatabase.fixDatabaseHistoryPosted();
        backToProfile = findViewById(R.id.backToProfileHistory);
        showJoinHistory = findViewById(R.id.showJoinedHistory);
        showPostsHistory = findViewById(R.id.showPostedHistory);
        ArrayList<Activity> historyArray = new ArrayList<Activity>();

        backToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = User.getCurrentUser().loadProfile(ShowHistoryPost.this);
                startActivity(i);
                finish();
            }
        });

        showPostsHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef = FirebaseDatabase.getInstance().getReference("users_history_posted");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).exists()) {
                            myRef = FirebaseDatabase.getInstance().getReference
                                    ("users_history_posted/" + FirebaseAuth.getInstance().getCurrentUser().getUid());
                            myRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    historyArray.clear();
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        historyArray.add(ds.getValue(Activity.class));
                                }
                                    if (historyArray.size() > 0) {
                                        Intent intent = new Intent(ShowHistoryPost.this, ShowActivities.class);
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
                            Toast.makeText(ShowHistoryPost.this, "You never posted an activity.", Toast.LENGTH_LONG).show();
                        }
                        myRef.removeEventListener(this);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
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
                                        Intent intent = new Intent(ShowHistoryPost.this, ShowActivities.class);
                                        intent.putExtra("activitiesArray", historyArray);
                                        ShowActivities.activityFilter = false;
                                        startActivity(intent);
                                        finish();
                                    }
                                    myRef.removeEventListener(this);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        } else {
                            Toast.makeText(ShowHistoryPost.this, "You didnt joined any activity right now.", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}