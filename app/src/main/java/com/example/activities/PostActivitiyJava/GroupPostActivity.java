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

public class GroupPostActivity extends AppCompatActivity {
private Button nextToDescribePostActivity;
private Activity newPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_post);
        newPost=getIntent().getParcelableExtra("newPost");
        nextToDescribePostActivity=findViewById(R.id.nextToDescribe);
        nextToDescribePostActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RadioGroup rgActivityFor=findViewById(R.id.rgGroupSingle);
                if(rgActivityFor.getCheckedRadioButtonId()==-1) {
                    Toast.makeText(getApplicationContext(), "Select activity capacity suitable", Toast.LENGTH_SHORT).show();
                }
                else {
                    String activityFor = ((RadioButton) findViewById(rgActivityFor.getCheckedRadioButtonId())).getText().toString();
                    boolean singleOrGroup = activityFor.equals("Group") ? true : false;
                    newPost.setGroup(singleOrGroup);
                    Intent intent = new Intent(GroupPostActivity.this, DescribePostActivity.class);
                    intent.putExtra("newPost", newPost);
                    startActivity(intent);
                }
            }
        });


    }
}
