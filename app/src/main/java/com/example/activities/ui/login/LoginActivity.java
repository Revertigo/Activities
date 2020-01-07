package com.example.activities.ui.login;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.activities.R;
import com.example.activities.ui.login.Registration.RegisterToApp_Email;
import com.example.activities.SearchActivity.SearchOrPost;

import com.example.activities.ui.login.user.PostUser;
import com.example.activities.ui.login.user.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button buttonSignIn;
    private Button tvSignUp;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private Button closeAppFromLoginActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //close app button
        closeAppFromLoginActivity = findViewById(R.id.button12);
        closeAppFromLoginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                moveTaskToBack(true);
            }
        });
        mAuth = FirebaseAuth.getInstance();
        usernameEditText = findViewById(R.id.name);
        passwordEditText = findViewById(R.id.password);
        buttonSignIn = findViewById(R.id.button6);
        tvSignUp = findViewById(R.id.button);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    Toast.makeText(LoginActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, SearchOrPost.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Please login", Toast.LENGTH_SHORT).show();
                }

            }
        };
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = usernameEditText.getText().toString();
                String pass = passwordEditText.getText().toString();
                if (email.isEmpty()) {
                    usernameEditText.setError("Please enter username id");
                    usernameEditText.requestFocus();
                } else if (pass.isEmpty()) {
                    passwordEditText.setError("Please enter password");
                    passwordEditText.requestFocus();
                } else if (email.isEmpty() && pass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Fields are empty!", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && pass.isEmpty())) {
                    mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Invalid name or password", Toast.LENGTH_SHORT).show();
//                            // Username or password false, display and an error
                            } else {
                                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users");
                                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                            if (ds.child("username").getValue().equals(mAuth.getCurrentUser().getEmail())) {
                                                String user_permission = (String) ds.child("permission").getValue();

                                                if (user_permission.equals("search")) {
                                                    User.setCurrentUser(new User(mAuth.getCurrentUser().getEmail(), "no_pass",
                                                            (String) ds.child("firstName").getValue(),
                                                            (String) ds.child("lastName").getValue(),
                                                            user_permission, (String) ds.child("gender").getValue(),
                                                            (String) ds.child("dateOfBirth").getValue(),
                                                            "no_phone", "no_location"));
                                                } else {
                                                    User.setCurrentUser(new PostUser(mAuth.getCurrentUser().getEmail(), "",
                                                            (String) ds.child("firstName").getValue(),
                                                            (String) ds.child("lasttName").getValue(),
                                                            user_permission, (String) ds.child("gender").getValue(),
                                                            (String) ds.child("dateOfBirth").getValue(),
                                                            "no_phone", "no_location",
                                                            (String) ds.child("occupation").getValue(),
                                                            (String) ds.child("education").getValue()));
                                                }

                                                Intent in = User.getCurrentUser().loadMainMenu(LoginActivity.this);
                                                startActivity(in);
                                                finish();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "Error Occured", Toast.LENGTH_SHORT).show();

                }
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterToApp_Email.class);
                startActivity(intent);
                finish();
            }
        });
    }//end onCreate


    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }
}


