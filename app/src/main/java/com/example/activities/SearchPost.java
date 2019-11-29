package com.example.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;

public class SearchPost extends AppCompatActivity {
  private Button buttonLogout;
  private Button closeAppFromSearchPost;
  private Button changeActivityToPostActivity;
  private Button changeActivityToSearchActivity;

  private  FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_post);


        //Button to change to post activity
        changeActivityToPostActivity=findViewById(R.id.button8);
        changeActivityToPostActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SearchPost.this,PostActivity.class);
                startActivity(intent);
            }
        });

        //Button to change to search activity
        changeActivityToSearchActivity=findViewById(R.id.button9);
        changeActivityToSearchActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SearchPost.this,SearchActivity.class);
                startActivity(intent);
            }
        });

        //close app button
        closeAppFromSearchPost=findViewById(R.id.button13);
        closeAppFromSearchPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                moveTaskToBack(true);

            }
        });




        buttonLogout=findViewById(R.id.button7);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.getInstance().signOut();
                Intent intent =new Intent(SearchPost.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
