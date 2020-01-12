package com.example.activities.ui.registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.activities.R;
import com.example.activities.data.entities.user.PostUser;
import com.example.activities.data.entities.user.User;


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
