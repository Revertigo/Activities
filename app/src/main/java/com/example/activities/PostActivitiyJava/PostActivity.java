package com.example.activities.PostActivitiyJava;


import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.activities.MainActivity;
import com.example.activities.R;
import com.example.activities.SearchActivity.SearchActivity;
import com.example.activities.Util.CsvReader;
import com.example.activities.data.rtdb.activity.Activity;
import com.example.activities.ui.login.Registration.RegisterToApp_DateOfBirth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PostActivity extends AppCompatActivity {
    private static final String activities = "activities/";
    private static DatabaseReference database_activity = null;

    private Button closeAppFromPost;
    private Button buttonLogout;
    private Button clickToPost;
    private Button dateButton;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private String dateStr;
    private TextView showTheDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        final Activity newPost=getIntent().getParcelableExtra("newPost");
            showTheDate=findViewById(R.id.enterDatePlainText) ;
        dateButton=findViewById(R.id.setDatePostActivity);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        PostActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                dateStr = month + "/" + day + "/" + year;
                showTheDate.setText(dateStr);
            }
        };

        //close app button
        closeAppFromPost = findViewById(R.id.closeAppPostActivity);
        closeAppFromPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                moveTaskToBack(true);

            }
        });

        //Setup back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Sort the data
        CsvReader.reorganize_data();



        //log out button
        buttonLogout = findViewById(R.id.logoutPostActivity);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(PostActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //send data to the database
        clickToPost = findViewById(R.id.btnClickToPost);
        clickToPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //set Date
                EditText theDate=findViewById(R.id.enterDatePlainText);
                DateFormat format = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);
                try {
                    newPost.setDate(format.parse( theDate.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //set Format
                String format2 = "dd/mm/yyy";
                TextView time=findViewById(R.id.timeEditText);
                newPost.setTime(time.getText().toString());
                //Write new id counter to the database
                while(!NamePostActivity.is_id_read);
                newPost.setId(Activity.getId_counter());//Update the real id of the user
                String [] tokens = NamePostActivity.id_counter_path.split("/");//[0] = resources, [1] = activity_id_counter
                NamePostActivity.database_ref_id_counter.child(tokens[1]).setValue(newPost.getId() + 1);
                NamePostActivity.is_id_read = false;

                //Write new activity to the database
                database_activity = FirebaseDatabase.getInstance().getReference(activities + "Activity_" + newPost.getId());
                database_activity.setValue(newPost);

                startActivity(new Intent(PostActivity.this, SearchActivity.class));
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}