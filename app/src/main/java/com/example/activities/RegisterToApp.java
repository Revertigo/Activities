package com.example.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.activities.ui.login.LoginActivity;

public class RegisterToApp extends AppCompatActivity {
    private Button backToLoginScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_to_app);
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
