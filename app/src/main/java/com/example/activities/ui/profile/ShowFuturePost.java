package com.example.activities.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.activities.data.entities.Activity;
import com.example.activities.data.rtdb.ActivityInfo;
import com.example.activities.data.rtdb.ShowActivities;
import com.example.activities.ui.postActivitiyJava.PostActivity;
import com.example.activities.R;
import com.example.activities.data.entities.user.User;
import com.example.activities.util.UpdateFirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ShowFuturePost extends AppCompatActivity {
    private Button backToProfile;
    private Button showFutureJoin;
    private Button showFuturePosts;
    private FirebaseDatabase database;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_post);
        UpdateFirebaseDatabase.fixDatabaseHistoryPosted();
        UpdateFirebaseDatabase.fixDatabaseHistoryJoined();//update future and history Trees on database
        backToProfile = findViewById(R.id.backToProfile);
        showFutureJoin = findViewById(R.id.showJoinedFuture);
        showFuturePosts = findViewById(R.id.showPostedFuture);
        database = FirebaseDatabase.getInstance();

        backToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = User.getCurrentUser().loadProfile(ShowFuturePost.this);
                startActivity(i);
                finish();
            }
        });

        showFuturePosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowActivities.activityFilter = true;
                DatabaseReference myActivitiesRef = database.getReference("users_future_posted");
                final ArrayList<Activity> myPostedActivities = new ArrayList<Activity>();
                myActivitiesRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).exists()) {
                            for (DataSnapshot currentUid : dataSnapshot.getChildren()) {
                                for(DataSnapshot currendActivity:currentUid.getChildren()){
                                if (currendActivity.getValue(Activity.class).getpostedUser().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                                    myPostedActivities.add(currendActivity.getValue(Activity.class));
                                }
                                }
                            }
                        }
                        if (myPostedActivities.size() > 0) {
                            Intent i = new Intent(ShowFuturePost.this, ShowActivities.class);
                            i.putExtra("activitiesArray", myPostedActivities);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(ShowFuturePost.this, "You not posted any activity.", Toast.LENGTH_LONG).show();
                        }

                        myActivitiesRef.removeEventListener(this);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        showFutureJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowActivities.activityFilter = true;
                DatabaseReference joinedRef = database.getReference("users_future_joined");
                joinedRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final ArrayList<String> joinedActivitiesArray = new ArrayList<String>();
                        for (DataSnapshot currentUid : dataSnapshot.getChildren()) {
                          for(DataSnapshot currentActivity:currentUid.getChildren()) {
                              //if my uid is exist here
                              if ((currentUid.getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))&&
                                      (currentUid.child(currentActivity.getKey()).exists())){
                                  joinedActivitiesArray.add(currentActivity.getKey());

                              }
                          }
                        }
                        if (joinedActivitiesArray.size() > 0) {
                            final ArrayList<Activity> activitiesArray = new ArrayList<Activity>();
                            final Intent i = new Intent(ShowFuturePost.this, ShowActivities.class);
                            DatabaseReference activitiesRef = database.getReference(PostActivity.getActivities());
                            activitiesRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (int i = 0; i < joinedActivitiesArray.size(); i++) {
                                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                            if (ds.getKey().equals(joinedActivitiesArray.get(i))) {
                                                activitiesArray.add(ds.getValue(Activity.class));
                                                break;
                                            }
                                        }
                                    }
                                    i.putExtra("activitiesArray", activitiesArray);
                                    startActivity(i);
                                    finish();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });
                        } else {
                            Toast.makeText(ShowFuturePost.this, "Your not joined any Activity.", Toast.LENGTH_LONG).show();
                        }
                        joinedRef.removeEventListener(this);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });

    }
}