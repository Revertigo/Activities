package com.example.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.activities.ui.login.LoginActivity;
import com.example.activities.ui.login.LoginViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterToApp extends AppCompatActivity {
    private Button backToLoginScreen;
    private FirebaseAuth mAuth;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button buttonSignUp;
    private TextView tvSignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_to_app);

        mAuth=FirebaseAuth.getInstance();
        usernameEditText = findViewById(R.id.editText);
        passwordEditText = findViewById(R.id.editText7);
        buttonSignUp = findViewById(R.id.button3);
        tvSignIn=findViewById(R.id.textView6);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(RegisterToApp.this,"Fields are empty!",Toast.LENGTH_SHORT).show();
                }
                else if(!(email.isEmpty()&&pass.isEmpty())){
                    mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(RegisterToApp.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(RegisterToApp.this,"SignUp Unsuccessfull,Please try again!",Toast.LENGTH_SHORT).show();

                            }
                            else{startActivity(new Intent(RegisterToApp.this,LoginActivity.class));}
                        }
                    });
                }
                else{
                    Toast.makeText(RegisterToApp.this,"Error Occured",Toast.LENGTH_SHORT).show();

                }
            }
        });
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterToApp.this,LoginActivity.class);
            }
        });


        backToLoginScreen=findViewById(R.id.button5);
        backToLoginScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToLoginScreen();
            }
        });



    }
    public void backToLoginScreen(){
        Intent intent=new Intent(RegisterToApp.this, LoginActivity.class);
        startActivity(intent);
    }
}
