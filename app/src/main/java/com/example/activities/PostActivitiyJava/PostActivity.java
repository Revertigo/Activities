package com.example.activities.PostActivitiyJava;


import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.activities.MainActivity;
import com.example.activities.R;
import com.example.activities.SearchActivity.SearchActivity;
import com.example.activities.Util.CsvReader;
import com.example.activities.data.rtdb.activity.Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private Button timeButton;
    private TextView showTheDate;
    private TextView showtheTime;
    final Calendar myCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        final Activity newPost=getIntent().getParcelableExtra("newPost");

        //add date button
            showTheDate=findViewById(R.id.enterDatePlainText) ;
        dateButton=findViewById(R.id.pressToSetDate);final Calendar myCalendar = Calendar.getInstance();
        dateButton.setOnClickListener(new View.OnClickListener() {
            DatePickerDialog datePickerDialog;
            int year;
            int month;
            int dayOfMonth;
            Calendar calendar;
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(PostActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                showTheDate.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });


        //time add button
        timeButton=findViewById(R.id.addTimeButton);
        showtheTime=findViewById(R.id.showTheTime);
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(PostActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String a = "" + selectedMinute;
                        String b = "" + selectedHour;
                        if(selectedMinute<10){
                            a = "0"+selectedMinute;
                        }
                        if(selectedHour<10){
                            b = "0"+selectedHour;
                        }
                        showtheTime.setText( b + ":" + a);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });



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
                TextView time=findViewById(R.id.showTheTime);
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