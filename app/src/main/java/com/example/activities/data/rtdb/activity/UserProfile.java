package com.example.activities.data.rtdb.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.activities.PostActivitiyJava.PostActivity;
import com.example.activities.R;
import com.example.activities.SearchActivity.SearchOrPost;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserProfile extends AppCompatActivity {
    private TextView name, email, occupation, education, gender, permission, birthday;
    private Button backToSearchOrPost;
    private Button showJoinedActivities;
    private Button showMyActivities;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private DatabaseReference joinedRef;
    private DatabaseReference myActivitiesRef;
    private Button activitiesHistory;
    private ImageView EditProfile;
    private ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        final String emailStr = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        name = findViewById(R.id.nameOfTheUserProfile);
        email = findViewById(R.id.emailOfTheUserProfile);
        occupation = findViewById(R.id.occupationOfTheUserProfile);
        education = findViewById(R.id.educationOfTheUserProfile);
        gender = findViewById(R.id.genderOfTheUserProfile);
        permission = findViewById(R.id.permissionOfTheUserProfile);
        birthday = findViewById(R.id.birthdayOfTheUserProfile);
        database = FirebaseDatabase.getInstance();


        EditProfile=findViewById(R.id.editImageViewProfile);

        EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserProfile.this, UserProfileEditable.class);
                startActivity(i);
                finish();
            }

        });
        profileImage=findViewById(R.id.profileImageProfile);

        backToSearchOrPost = findViewById(R.id.backToSearchOrPost);
        backToSearchOrPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfile.this, SearchOrPost.class);
                startActivity(intent);
                finish();
            }
        });

        activitiesHistory = findViewById(R.id.activitiesHistory);
        activitiesHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserProfile.this, ShowHistory.class);
                startActivity(i);
                finish();
            }
        });

        userRef = database.getReference("users");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.child("username").getValue(String.class).equals(emailStr)) {
                        name.setText(ds.child("firstName").getValue(String.class) + " " + ds.child("lastName").getValue(String.class));

                        email.setText(emailStr);

                        if (ds.child("occupation").exists()) {
                            occupation.setText(ds.child("occupation").getValue(String.class));
                        } else {
                            occupation.setText("Occupation: ");
                        }

                        if (ds.child("education").exists()) {
                            education.setText(ds.child("education").getValue(String.class));
                        } else {
                            education.setText("Education:");
                        }

                        permission.setText(ds.child("permission").getValue(String.class));

                        birthday.setText(ds.child("dateOfBirth").getValue(String.class));
                        gender.setText(ds.child("gender").getValue(String.class));
                        // Todo  : GET URI OF PROFILE IMAGE
                        // profileImage.setImageURI();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        showJoinedActivities = findViewById(R.id.joinedActivities);
        showJoinedActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowActivities.activityFilter = true;
                joinedRef = database.getReference(ActivityInfo.getUsers_in_activities());
                joinedRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final ArrayList<String> joinedActivitiesArray = new ArrayList<String>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            //if my uid is exist here
                            if (ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).exists()) {
                                joinedActivitiesArray.add(ds.getKey());
                            }
                        }
                        if (joinedActivitiesArray.size() > 0) {
                            final ArrayList<Activity> activitiesArray = new ArrayList<Activity>();
                            final Intent i = new Intent(UserProfile.this, ShowActivities.class);
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
                            Toast.makeText(UserProfile.this, "Your not joined any Activity.", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
        showMyActivities = findViewById(R.id.myPostedActivities);
        showMyActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowActivities.activityFilter = true;
                myActivitiesRef = database.getReference(PostActivity.getActivities());
                final ArrayList<Activity> myPostedActivities = new ArrayList<Activity>();
                myActivitiesRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Activity ac = ds.getValue(Activity.class);
                            if (ac.getpostedUser().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                                myPostedActivities.add(ds.getValue(Activity.class));
                            }
                        }
                        if (myPostedActivities.size() > 0) {
                            Intent i = new Intent(UserProfile.this, ShowActivities.class);
                            i.putExtra("activitiesArray", myPostedActivities);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(UserProfile.this, "You not posted any activity.", Toast.LENGTH_LONG).show();
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
