package com.example.activities.PostActivitiyJava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.activities.R;
import com.example.activities.data.rtdb.activity.Activity;

public class GenderPostActivity extends AppCompatActivity {
    private Button nextToGroupPostActivity;
    private Activity newPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender_post);
        newPost = getIntent().getParcelableExtra("newPost");
        nextToGroupPostActivity = findViewById(R.id.nextToGroup);
        nextToGroupPostActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RadioGroup rgGender = findViewById(R.id.rgGenderAdvanced);

                if (rgGender.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Please select gender suitable", Toast.LENGTH_SHORT).show();
                } else {
                    String gender = ((RadioButton) findViewById(rgGender.getCheckedRadioButtonId())).getText().toString();
                    newPost.setGender(gender);
                    Intent intent = new Intent(GenderPostActivity.this, GroupPostActivity.class);
                    intent.putExtra("newPost", newPost);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
