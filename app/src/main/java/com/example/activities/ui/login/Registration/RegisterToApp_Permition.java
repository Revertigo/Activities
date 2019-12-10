package com.example.activities.ui.login.Registration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.activities.R;
import com.example.activities.SearchActivity;
import com.example.activities.data.model.LoggedInUser;
import com.example.activities.ui.login.Users.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterToApp_Permition extends AppCompatActivity {

    private Button search;
    private Button post;
    private String permition;

    private User newUser ;

    private FirebaseAuth mAuth;
    private DatabaseReference users_ref;
    private String search_users="Search users/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_permition);
        //Get from last activity the User object of new user:
        newUser = getIntent().getParcelableExtra("newUser");

        search = findViewById(R.id.registerToSearchButton);
        post = findViewById(R.id.registerToPostButton);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permition = "search";
                newUser.setPermition(permition);
                //Upload to database:
                mAuth = FirebaseAuth.getInstance();// Initialize Firebase Auth
                mAuth.createUserWithEmailAndPassword(newUser.getEmail(), newUser.getPassword())
                        .addOnCompleteListener(RegisterToApp_Permition.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(RegisterToApp_Permition.this, SearchActivity.class));
                                }
                                else {
                                    // If sign in fails, display a message to the user.
                                    startActivity(new Intent(RegisterToApp_Permition.this, RegisterToApp_Email.class));
                                }
                            }
                        });
                users_ref = FirebaseDatabase.getInstance().getReference(search_users);
                users_ref.child(search_users).setValue(newUser);
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permition = "post";
                newUser.setPermition(permition);
                Intent i = new Intent(RegisterToApp_Permition.this, RegisterToApp_PostIntro.class);
                i.putExtra("newUser",newUser); //Submit the object to the next activity
                startActivity(i);
            }
        });

    }


}
