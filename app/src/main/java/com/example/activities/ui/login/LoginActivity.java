package com.example.activities.ui.login;

import androidx.annotation.NonNull;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.activities.R;
import com.example.activities.RegisterToApp;
import com.example.activities.SearchPost;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.app.AlertDialog;
import android.content.DialogInterface;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button buttonSignIn;
    private Button tvSignUp;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        buttonSignIn = findViewById(R.id.button6);
        tvSignUp=findViewById(R.id.button);

        mAuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser=mAuth.getCurrentUser();
                if(mFirebaseUser !=null){
                    Toast.makeText(LoginActivity.this,"You are logged in",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(LoginActivity.this,SearchPost.class);
                    startActivity(intent);
                }
                else{Toast.makeText(LoginActivity.this,"Please login",Toast.LENGTH_SHORT).show();}

            }
        };
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=usernameEditText.getText().toString();
                String pass=passwordEditText.getText().toString();
                if(email.isEmpty()){
                    usernameEditText.setError("Please enter email id");
                    usernameEditText.requestFocus();
                }
                else if(pass.isEmpty()){
                    passwordEditText.setError("Please enter password");
                    passwordEditText.requestFocus();
                }
                else if(email.isEmpty()&&pass.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Fields are empty!",Toast.LENGTH_SHORT).show();
                }
                else if(!(email.isEmpty()&&pass.isEmpty())){
                    mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(LoginActivity.this,"Login error, Please login again",Toast.LENGTH_SHORT).show();

                        }
                        else{
                            Intent intent=new Intent(LoginActivity.this,SearchPost.class);
                            startActivity(intent);
                        }
                        }
                    });
                }
                else{
                    Toast.makeText(LoginActivity.this,"Error Occured",Toast.LENGTH_SHORT).show();

                }
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterToApp.class);
            startActivity(intent);
            }
        });

        }//end onCreate
    protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }
    }


