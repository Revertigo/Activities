package com.example.activities.data.rtdb.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.activities.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowHistory extends AppCompatActivity {
    private Button backToProfile;
    private Button showJoinHistory;
    private Button showPostsHistory;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_history);

        backToProfile = findViewById(R.id.backToProfile);
        showJoinHistory = findViewById(R.id.showJoinedHistory);
        showPostsHistory = findViewById(R.id.showPostedHistory);
        ArrayList<Activity> historyArray = new ArrayList<Activity>();


        backToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ShowHistory.this, UserProfile.class);
                startActivity(i);
            }
        });

        showPostsHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef = FirebaseDatabase.getInstance().getReference("users_history_posted");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(FirebaseAuth.getInstance().getUid())) {
                            myRef = FirebaseDatabase.getInstance().getReference("users_history_posted/" + FirebaseAuth.getInstance().getUid());
                            myRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    historyArray.clear();
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        historyArray.add(ds.getValue(Activity.class));
                                    }
                                    if(historyArray.size()>0){
                                        Intent intent=new Intent(ShowHistory.this,ShowActivities.class);
                                        intent.putExtra("activitiesArray",historyArray);
                                        ShowActivities.activityFilter=false;
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
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
                        if (dataSnapshot.hasChild(FirebaseAuth.getInstance().getUid())) {
                            myRef = FirebaseDatabase.getInstance().getReference("users_history_joined/" + FirebaseAuth.getInstance().getUid());
                            myRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    historyArray.clear();
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        historyArray.add(ds.getValue(Activity.class));
                                    }
                                    if(historyArray.size()>0){
                                        Intent intent=new Intent(ShowHistory.this,ShowActivities.class);
                                        intent.putExtra("activitiesArray",historyArray);
                                        ShowActivities.activityFilter=false;
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

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
