package com.example.activities.data.rtdb.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.activities.R;
import com.example.activities.ui.login.user.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {
    private TextView name, email, occupation, education, gender, permission, birthday, phone;
    private Button backToMainMenu;
    private Button showMyActivities;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
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
        phone = findViewById(R.id.PhoneOfTheUserProfile);
        database = FirebaseDatabase.getInstance();


        EditProfile = findViewById(R.id.editImageViewProfile);

        EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserProfile.this, UserProfileEditable.class);
                startActivity(i);
                finish();
            }

        });
        profileImage = findViewById(R.id.profileImageProfile);

        backToMainMenu = findViewById(R.id.backToSearchOrPost);
        backToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = User.getCurrentUser().loadMainMenu(UserProfile.this);
                startActivity(intent);
                finish();
            }
        });

        activitiesHistory = findViewById(R.id.activitiesHistory);
        activitiesHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = User.getCurrentUser().loadHistory(UserProfile.this);
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
                        phone.setText(ds.child("phone").getValue(String.class));
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

        showMyActivities = findViewById(R.id.myPostedActivities);
        showMyActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = User.getCurrentUser().loadFuture(UserProfile.this);
                startActivity(in);
                finish();
            }
        });


    }
}
