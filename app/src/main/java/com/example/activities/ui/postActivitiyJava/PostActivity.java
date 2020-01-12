package com.example.activities.ui.postActivitiyJava;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.activities.ui.main.MainActivity;
import com.example.activities.R;
import com.example.activities.data.entities.Activity;
import com.example.activities.data.rtdb.ActivityInfo;
import com.example.activities.data.entities.user.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


public class PostActivity extends AppCompatActivity {
    private static final String activities = "activities/";
    private static DatabaseReference database_activity = null;

    public static String getActivities() {
        return activities;
    }

    private Button closeAppFromPost;
    private Button buttonLogout;
    private Button clickToPost;
    private Button dateButton;
    private Button timeButton;
    private TextView showTheDate;
    private TextView showtheTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        final Activity newPost = getIntent().getParcelableExtra("newPost");

        //add date button
        showTheDate = findViewById(R.id.enterDatePlainText);
        dateButton = findViewById(R.id.pressToSetDate);
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
        timeButton = findViewById(R.id.addTimeButton);
        showtheTime = findViewById(R.id.showTheTime);
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
                        if (selectedMinute < 10) {
                            a = "0" + selectedMinute;
                        }
                        if (selectedHour < 10) {
                            b = "0" + selectedHour;
                        }
                        showtheTime.setText(b + ":" + a);
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
        //log out button
        buttonLogout = findViewById(R.id.logoutPostActivity);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(PostActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //send data to the database
        clickToPost = findViewById(R.id.btnClickToPost);
        clickToPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //date edit text
                EditText theDate = findViewById(R.id.enterDatePlainText);
                //time edit text
                TextView time = findViewById(R.id.showTheTime);

                //take the split from date string that entered if legal,continue
                //  else ask to enter date again
                String st = theDate.getText().toString();
                String[] st2 = st.split("/");
                if (st2.length == 3) {
                    try {

                        int a = Integer.parseInt(st2[0]);
                        a = Integer.parseInt(st2[1]);
                        a = Integer.parseInt(st2[2]);
                        Activity.Date date = new Activity.Date(st2[0], st2[1], st2[2]);
                        newPost.setDate(date);
                    } catch (Exception e) {
                        theDate.setText("");
                        theDate.setError("You must enter date, For example \n" + "1/1/2020");
                        theDate.requestFocus();
                    }
                } else {
                    theDate.setText("");
                    theDate.setError("You must enter date, For example \n" + "1/1/2020");
                    theDate.requestFocus();
                }

                //take the split from time string that entered if legal,continue
                //  else ask to enter time again
                String theTimeString = time.getText().toString();
                String[] theTimeSplit = theTimeString.split(":");
                if (theTimeSplit.length == 2) {
                    try {
                        int a = Integer.parseInt(theTimeSplit[0]);
                        a = Integer.parseInt(theTimeSplit[1]);
                        newPost.setTime(theTimeString);
                    } catch (Exception e) {
                        time.setText("");
                        time.setError("You must enter time, For example \n" + "10:30");
                        time.requestFocus();
                    }

                } else {
                    time.setText("");
                    time.setError("You must enter time, For example \n" + "10:30");
                    time.requestFocus();
                }

                //date,time  entered can create the post
                if (!(theDate.getText().toString().isEmpty()) && !(time.getText().toString().isEmpty())) {
                    // befor the post Write new id counter to the database, and update the post id
                    newPost.setId(Activity.getId_counter());//Update the real id of the user
                    newPost.setpostedUser(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    String[] tokens = NamePostActivity.id_counter_path.split("/");//[0] = resources, [1] = activity_id_counter
                    NamePostActivity.database_ref_id_counter.child(tokens[1]).setValue(newPost.getId() + 1);
                    //Write new activity to the database
                    database_activity = FirebaseDatabase.getInstance().getReference(activities + "Activity_" + newPost.getId());
                    database_activity.setValue(newPost);
                    database_activity=FirebaseDatabase.getInstance().getReference("users_history_posted"+"/"+FirebaseAuth.getInstance().getUid()+
                            "/"+"Activity_"+ newPost.getId());
                    database_activity.setValue(newPost);


                    database_activity=FirebaseDatabase.getInstance().getReference("users_history_joined"+"/"+FirebaseAuth.getInstance().getUid()+
                            "/"+"Activity_"+ newPost.getId());
                    database_activity.setValue(newPost);

                    database_activity=FirebaseDatabase.getInstance().getReference("users_in_activities/Activity_"+newPost.getId());
                    database_activity.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    Toast.makeText(PostActivity.this, "Your post has been uploaded successfully", Toast.LENGTH_LONG).show();
                    postNotification(newPost.getName(),Long.toString(newPost.getId()));
                    startActivity(User.getCurrentUser().loadMainMenu(PostActivity.this));
                    finish();
                }//if date and time are valid, finish
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

    private void postNotification(String name,String id) {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("12345",
                    "channel name",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("unique channel");
            mNotificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "12345")
                .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                .setContentTitle("Activity notification") //c title for notification
                .setContentText("You posted activity: " + name + ", "+"ID for invitation: " +id)// message for notification
                .setAutoCancel(true); // clear notification after click
        Intent intent = new Intent(getApplicationContext(), ActivityInfo.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);
        mNotificationManager.notify(0, mBuilder.build());
    }
}