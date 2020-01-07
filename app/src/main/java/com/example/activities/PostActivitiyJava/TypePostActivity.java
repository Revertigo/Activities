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

public class TypePostActivity extends AppCompatActivity {
    private Button nextToDifficulty;
    private Activity newPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_post);
        newPost = getIntent().getParcelableExtra("newPost");
        nextToDifficulty = findViewById(R.id.nextToDifficultyPostActivity);

        //spinner info slots
        String[] types = new String[]{
                "Type", "Sport", "Food", "Online gaming", "Art", "Learning", "Other"
        };
        Spinner typeSpinner = findViewById(R.id.typeSpinner);
        ArrayAdapter<String> adapterForTypeSpinner = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, types);
        adapterForTypeSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapterForTypeSpinner);


        nextToDifficulty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Spinner typeThatSelected = findViewById(R.id.typeSpinner);
                String theTypedThatSelected = typeThatSelected.getSelectedItem().toString();
                newPost.setType(theTypedThatSelected);
                Intent intent = new Intent(TypePostActivity.this, DifficultyPostActivity.class);
                intent.putExtra("newPost", newPost);
                startActivity(intent);
                finish();
            }
        });
    }
}
