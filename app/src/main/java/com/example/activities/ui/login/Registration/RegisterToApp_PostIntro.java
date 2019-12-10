package com.example.activities.ui.login.Registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.activities.R;
import com.example.activities.ui.login.Users.PostUser;
import com.example.activities.ui.login.Users.User;

public class RegisterToApp_PostIntro extends AppCompatActivity {

    private Button Next;
    private User newUser ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_post_intro);
        //Get from last activity the User object of new user:
        newUser = getIntent().getParcelableExtra("newUser");

        Next = findViewById(R.id.Next6);

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create new postUser:
                PostUser postNewUser = new PostUser(newUser);
                Intent i = new Intent(RegisterToApp_PostIntro.this, RegisterToApp_Education.class);
                i.putExtra("PostNewUser",postNewUser);//Submit the PostUser object to the next activity
                startActivity(i);
            }
        });
    }
}
