package com.example.activities.PostActivitiyJava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.activities.R;
import com.example.activities.data.rtdb.activity.Activity;

public class DifficultyPostActivity extends AppCompatActivity {
private Activity newPost;
private Button nextToGenderPostActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_post);
        newPost=getIntent().getParcelableExtra("newPost");
        nextToGenderPostActivity=findViewById(R.id.nextToGroup);
        String[] difficulty = new String[]{
                "Difficulty", "Beginner", "Advanced", "Professional"
        };
        //difficulty spinner
        Spinner difficultySpinner = findViewById(R.id.difficultySpinner);
        ArrayAdapter<String> adapterForSpinner4 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, difficulty);
        adapterForSpinner4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(adapterForSpinner4);

        nextToGenderPostActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Spinner difficultSelected=findViewById(R.id.difficultySpinner);
                String difficultThatSelected=difficultSelected.getSelectedItem().toString();
                newPost.setDifficulty(difficultThatSelected);
                Intent intent=new Intent(DifficultyPostActivity.this,GenderPostActivity.class);
                intent.putExtra("newPost",newPost);
                startActivity(intent);
            }
        });



    }
}
