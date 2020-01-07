package com.example.activities.PostActivitiyJava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.activities.R;
import com.example.activities.data.rtdb.activity.Activity;

public class DescribePostActivity extends AppCompatActivity {
    private Activity newPost;
    private Button nextToPostActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_describe_post);
        newPost = getIntent().getParcelableExtra("newPost");
        nextToPostActivity = findViewById(R.id.nextToPostActivity);
        nextToPostActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText description = findViewById(R.id.editTextDescribeApp);
                String desc = description.getText().toString();
                if (desc.isEmpty()) {
                    desc = "";
                }
                newPost.setDescription(desc);
                Intent intent = new Intent(DescribePostActivity.this, AdressPostActivity.class);
                intent.putExtra("newPost", newPost);
                startActivity(intent);
                finish();
            }
        });
    }
}
