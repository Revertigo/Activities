package com.example.activities.data.rtdb.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.activities.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {
    private TextView name,email,occupation,education,gender,permission,birthday;
    private ImageView emailImage,occupationImage,educationImage,genderImage,permissionImage,birthdayImage,profileImage;
    private FirebaseDatabase database;
    private DatabaseReference userRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Intent intent=getIntent();
        final String emailStr=intent.getStringExtra("email");



        name=findViewById(R.id.nameOfTheUser);
        email=findViewById(R.id.emailOfTheUser);
        occupation=findViewById(R.id.occupationOfTheUser);
        education=findViewById(R.id.educationOfTheUser);
        gender=findViewById(R.id.genderOfTheUser);
        permission=findViewById(R.id.permissionOfTheUser);
        birthday=findViewById(R.id.birthdayOfTheUser);

        emailImage=findViewById(R.id.emailImage);
        occupationImage=findViewById(R.id.occupationImage);
        educationImage=findViewById(R.id.educationImage);
        genderImage=findViewById(R.id.genderImage);
        permissionImage=findViewById(R.id.permissionImage);
        birthdayImage=findViewById(R.id.birthdayImage);
        profileImage=findViewById(R.id.profileImage);

        database=FirebaseDatabase.getInstance();
        userRef=database.getReference("Users");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    if(ds.child("username").getValue().equals(emailStr)){
                        name.setText("Full name: "+ds.child("firstName").getValue(String.class)+" "+ds.child("lastName").getValue(String.class));

                        email.setText("Email: "+emailStr);

                        if(ds.child("occupation").exists()){
                        occupation.setText("Occupation: "+ds.child("permission").getValue(String.class));}
                        else{occupation.setText("Occupation: ");}

                        if(ds.child("education").exists()){
                            education.setText("Education"+ds.child("education").getValue(String.class));}
                        else{education.setText("Education:");}

                        permission.setText("Permission: "+ds.child("permission").getValue(String.class));

                        birthday.setText("Birthday: "+ds.child("dateOfBirth").getValue(String.class));
                        gender.setText("Gender: "+ds.child("gender").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}