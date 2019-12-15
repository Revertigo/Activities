package com.example.activities.PostActivitiyJava;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.activities.R;
import com.example.activities.data.rtdb.activity.Activity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class NamePostActivity extends AppCompatActivity {

    private Button nextToType;
    static public final String id_counter_path = "resources/activity_id_counter";
    static public DatabaseReference database_ref_id_counter = null;
    static public boolean is_id_read = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_post);
        nextToType=findViewById(R.id.nextToType);
        nextToType.setOnClickListener(new View.OnClickListener() {
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
                Activity newPost = new Activity(activityNameChoosen,"", new Activity.Address(), "",
                        true,"","", new Date(), "");
                Intent intent=new Intent(NamePostActivity.this, TypePostActivity.class);
                intent.putExtra("newPost",newPost);
                startActivity(intent);

            }
        });

    }
}