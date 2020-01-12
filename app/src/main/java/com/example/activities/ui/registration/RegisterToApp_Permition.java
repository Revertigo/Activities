package com.example.activities.ui.registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.activities.R;
import com.example.activities.ui.searchActivity.SearchActivity;
import com.example.activities.data.entities.user.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterToApp_Permition extends AppCompatActivity {

    private Button search;
    private Button post;
    private String permition = "search";//default for Parcalble

    private User newUser;

    private FirebaseAuth mAuth;
    private DatabaseReference users_ref;
    private String search_users = "users/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_permition);
        //Get from last activity the User object of new user:
        newUser = getIntent().getParcelableExtra("newUser");
        users_ref = FirebaseDatabase.getInstance().getReference(search_users);

        search = findViewById(R.id.registerToSearchButton);
        post = findViewById(R.id.registerToPostButton);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permition = "search";
                newUser.setPermission(permition);
                newUser.setpictureUri("");

                //Upload to database:
                mAuth = FirebaseAuth.getInstance();// Initialize Firebase Auth
                mAuth.createUserWithEmailAndPassword(newUser.getUsername(), newUser.getPassword())
                        .addOnCompleteListener(RegisterToApp_Permition.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    users_ref.child(mAuth.getUid()).setValue(newUser);
                                    startActivity(new Intent(RegisterToApp_Permition.this, SearchActivity.class));
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    startActivity(new Intent(RegisterToApp_Permition.this, RegisterToApp_Email.class));
                                    finish();
                                    Toast.makeText(getApplicationContext(),
                                            "This email username already exist, Choose another email.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permition = "post";
                newUser.setPermission(permition);
                Intent i = new Intent(RegisterToApp_Permition.this, RegisterToApp_PostIntro.class);
                i.putExtra("newUser", newUser);
                startActivity(i);
                finish();
            }
        });

    }


}
