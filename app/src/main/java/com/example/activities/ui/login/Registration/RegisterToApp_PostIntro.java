package com.example.activities.ui.login.Registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.activities.R;
import com.example.activities.ui.login.user.PostUser;
import com.example.activities.ui.login.user.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterToApp_PostIntro extends AppCompatActivity {

    private Button finishRegistrationPostUser;
    private User newUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_post_intro);
        newUser = getIntent().getParcelableExtra("newUser");
        finishRegistrationPostUser = findViewById(R.id.nextIntro);

        finishRegistrationPostUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostUser postNewUser = new PostUser(newUser);
                Intent i = new Intent(RegisterToApp_PostIntro.this, RegisterToApp_Education.class);
                i.putExtra("postNewUser", postNewUser);
                startActivity(i);
                finish();
            }
        });
    }
}
