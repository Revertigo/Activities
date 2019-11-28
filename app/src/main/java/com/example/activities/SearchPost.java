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

   FirebaseAuth auth;
  private FirebaseAuth.AuthStateListener authListener;
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

        String[] arraySpinner1 = new String[] {
                "Enter Location","North", "Middle", "South","Another"
        };
        String[] arraySpinner2 = new String[] {
                "Enter Activity","Sport", "Food", "Fun","Another"
        };
        String[] arraySpinner3 = new String[] {
                "Enter Level","Beginner", "Advanced", "Expert"
        };

        Spinner spin1 = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapterForSpinner2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner2);
        adapterForSpinner2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(adapterForSpinner2);

        Spinner spin2 = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapterForSpinner1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner1);
        adapterForSpinner1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(adapterForSpinner1);


        Spinner spin3 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> adapterForSpinner3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner3);
        adapterForSpinner3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin3.setAdapter(adapterForSpinner3);


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
