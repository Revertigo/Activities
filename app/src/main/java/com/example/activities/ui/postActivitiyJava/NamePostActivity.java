package com.example.activities.ui.postActivitiyJava;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.activities.R;
import com.example.activities.data.entities.Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class NamePostActivity extends AppCompatActivity {
    private Button nextToType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_post);
        nextToType = findViewById(R.id.nextToType);
        nextToType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final EditText name = findViewById(R.id.activityNameEditText);

                String activityNameChoosen = name.getText().toString();
                if (activityNameChoosen.isEmpty()) {
                    name.setError("Please enter your Activity's name");
                    name.requestFocus();
                } else {
                  String id=UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();

                    Activity newPost = new Activity(id,"",activityNameChoosen,"", new Activity.Address(), "",
                            true, "", "", new Activity.Date(), "");

                    Intent intent = new Intent(NamePostActivity.this, TypePostActivity.class);
                    intent.putExtra("newPost", newPost);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}
