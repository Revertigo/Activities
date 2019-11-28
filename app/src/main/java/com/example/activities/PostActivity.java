package com.example.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class PostActivity extends AppCompatActivity {

    private Button closeAppFromPost;
    private Button buttonLogout;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        //close app button
        closeAppFromPost=findViewById(R.id.button14);
        closeAppFromPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                moveTaskToBack(true);

            }
        });

        buttonLogout=findViewById(R.id.button16);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.getInstance().signOut();
                Intent intent =new Intent(PostActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
