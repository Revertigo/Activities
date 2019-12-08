package com.example.activities;

import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.activities.data.rtdb.activity.NewActivity;
import com.google.firebase.auth.FirebaseAuth;

public class firstStepPostActivity extends AppCompatActivity {
    private Button btnCloseAppFromFirstStepPostActivity;
    private Button btnLogoutFromFirstStepPostActivity;
    private Button btnChangeToPostActivity;
    FirebaseAuth auth;

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

                NewActivity newPost = new NewActivity(activityNameChoosen,theTypedThatSelected,difficultThatSelected,gender,desc,activityFor);

                Intent intent=new Intent(firstStepPostActivity.this,PostActivity.class);
                intent.putExtra("newPost",newPost);
                startActivity(intent);
            }
        });
    }//end onCreate




}
