package com.example.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.activities.data.rtdb.activity.Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class firstStepPostActivity extends AppCompatActivity {
    private static final String id_counter_path = "resources/activity_id_counter/";

    private Button btnCloseAppFromFirstStepPostActivity;
    private Button btnLogoutFromFirstStepPostActivity;
    private Button btnChangeToPostActivity;
    FirebaseAuth auth;
    private static DatabaseReference database_ref_id_counter = null;
    static boolean is_id_read = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_step_post);
        btnLogoutFromFirstStepPostActivity=findViewById(R.id.closeAppFirstStepPost);
        btnCloseAppFromFirstStepPostActivity=findViewById(R.id.closeAppFirstStepPost);
        btnChangeToPostActivity=findViewById(R.id.changeToPostActivity);

        String[] difficulty = new String[]{
                "Difficulty", "Beginner", "Advanced", "Professional"
        };
        //difficulty spinner
        Spinner difficultySpinner = findViewById(R.id.difficultySpinner);
        ArrayAdapter<String> adapterForSpinner4 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, difficulty);
        adapterForSpinner4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(adapterForSpinner4);

        //spinner info slots
        String[] types = new String[]{
                "Type", "Sport", "Food", "Online gaming", "Art", "Learning", "Other"
        };
        Spinner typeSpinner = findViewById(R.id.typeSpinner);
        ArrayAdapter<String> adapterForTypeSpinner = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, types);
        adapterForTypeSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapterForTypeSpinner);

        //logout listener
        btnLogoutFromFirstStepPostActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.getInstance().signOut();
                Intent intent = new Intent(firstStepPostActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        //close app listener
        btnCloseAppFromFirstStepPostActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                moveTaskToBack(true);
            }
        });
        //change to post activity listener
        btnChangeToPostActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //First read from the database
                String [] tokens = id_counter_path.split("/");//[0] = resources, [1] = activity_id_counter

                database_ref_id_counter = FirebaseDatabase.getInstance().getReference(tokens[0]);//Get Database instance
                database_ref_id_counter.orderByChild(tokens[1]).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                        Activity.setId_counter((dataSnapshot.getValue(Long.class)));
                        is_id_read = true;
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                final EditText name =findViewById(R.id.activityNameEditText);
                String activityNameChoosen=name.getText().toString();

                final Spinner typeThatSelected=findViewById(R.id.typeSpinner);
                String theTypedThatSelected=typeThatSelected.getSelectedItem().toString();

                final Spinner difficultSelected=findViewById(R.id.difficultySpinner);
                String difficultThatSelected=difficultSelected.getSelectedItem().toString();

                final   RadioGroup rgGender=findViewById(R.id.rgGender);
                String gender = ((RadioButton)findViewById(rgGender.getCheckedRadioButtonId())).getText().toString();

                final EditText description=findViewById(R.id.editTextDescribeApp);
                String desc=description.getText().toString();

                final RadioGroup rgActivityFor=findViewById(R.id.rgActivityFor);
                String activityFor = ((RadioButton)findViewById(rgActivityFor.getCheckedRadioButtonId())).getText().toString();
                boolean singleOrGroup = activityFor.equals("Group") ? true : false;

                Activity newPost = new Activity(activityNameChoosen,theTypedThatSelected, new Activity.Address(), difficultThatSelected,
                        singleOrGroup,gender,desc, new Date(), "");
                Intent intent=new Intent(firstStepPostActivity.this, PostActivity.class);
                intent.putExtra("newPost",newPost);
                startActivity(intent);
            }
        });
    }//end onCreate




}