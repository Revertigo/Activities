package com.example.activities.data.rtdb.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.activities.MainActivity;
import com.example.activities.PostActivitiyJava.PostActivity;
import com.example.activities.R;
import com.example.activities.ui.login.user.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Constants;

import java.util.ArrayList;

import static com.example.activities.R.mipmap.ic_launcher_round;

public class ActivityInfo extends AppCompatActivity {
    private Button backToShowActivities;
    private Button joinThisActitivty;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Button leaveThisActivity;
    private Button showRegisteredUsers;
    private static final String users_in_activities = "users_in_activities";


    public static String getUsers_in_activities() {
        return users_in_activities;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(users_in_activities);
        final ArrayList<Activity> showActivitiesAgain = getIntent().getParcelableArrayListExtra("showActivitiesAgain");

        final ArrayList<Activity> currentActivity = getIntent().getParcelableArrayListExtra("joinActivity");


        backToShowActivities = findViewById(R.id.backToShowActivities);
        joinThisActitivty = findViewById(R.id.joinThisActivity);

        ListView lv = findViewById(R.id.joinActivityListView);
        ArrayAdapter<Activity> adapter = new ArrayAdapter<Activity>(ActivityInfo.this, android.R.layout.simple_list_item_1, currentActivity);
        lv.setAdapter(adapter);

        backToShowActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityInfo.this, ShowActivities.class);
                intent.putExtra("activitiesArray", showActivitiesAgain);
                startActivity(intent);
                finish();
            }
        });


        joinThisActitivty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityInfo.this, UserProfile.class);
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        myRef.child("Activity_" + currentActivity.get(0).getId())
                                .child(FirebaseAuth.getInstance().getUid()).setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        DatabaseReference history_ref = FirebaseDatabase.getInstance().getReference("users_history_joined");
                        history_ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("Activity_" + currentActivity.get(0).getId()).setValue(currentActivity.get(0));
                        Toast.makeText(ActivityInfo.this, "You are joined to this activity", Toast.LENGTH_LONG).show();
                        myRef.removeEventListener(this);
                        joinNotification(currentActivity.get(0).getName());
                        startActivity(intent);
                        finish();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        showRegisteredUsers = findViewById(R.id.showRegisteredUsers);
        showRegisteredUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference showRegisteredUsersRef = FirebaseDatabase.getInstance().
                        getReference("users_in_activities/Activity_" + currentActivity.get(0).getId());
                showRegisteredUsersRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<String> usersInfo = new ArrayList<String>();
                        ArrayList<String> registeredUsers = new ArrayList<String>();

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            registeredUsers.add(ds.getValue(String.class));
                        }
                        DatabaseReference usersRef = FirebaseDatabase.getInstance().
                                getReference("users");
                        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (int i = 0; i < registeredUsers.size(); i++) {
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        Log.wtf("", ds.child("username").getValue(String.class));
                                        Log.wtf("", registeredUsers.get(i));
                                        if (ds.child("username").getValue(String.class).equals(registeredUsers.get(i))) {
                                            Log.wtf("", "inside the if");
                                            String currentUser = "";
                                            String st1 = ds.getValue(User.class).getFirstName() + " " + ds.getValue(User.class).getLastName() + "\n";
                                            if (st1.isEmpty()) {
                                                st1 = "No name";
                                            }
                                            String st2 = ds.getValue(User.class).getGender() + "\n";
                                            String st3 = ds.getValue(User.class).getDateOfBirth();
                                            currentUser = st1 + st2 + st3;
                                            usersInfo.add(currentUser);
                                        }
                                    }
                                }
                                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(ActivityInfo.this, android.R.layout.simple_list_item_1, usersInfo);
                                lv.setAdapter(adapter2);
                                lv.clearAnimation();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
//Todo check if has a bug
        leaveThisActivity = findViewById(R.id.leaveThisActivity);
        leaveThisActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              open(currentActivity.get(0));

            }
        });//leaveThisActivity click listener

    }//onCreate


    private void joinNotification(String name) {
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
                .setContentTitle("Activity notification") // title for notification
                .setContentText("You joined to activity: " + name)// message for notification
                .setAutoCancel(true); // clear notification after click
        Intent intent = new Intent(getApplicationContext(), ActivityInfo.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);
        mNotificationManager.notify(0, mBuilder.build());
    }

    private void leftNotification(String name) {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("123456",
                    "channel name",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("unique channel");
            mNotificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "123456")
                .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                .setContentTitle("Activity notification") // title for notification
                .setContentText("You left the activity: " + name)// message for notification
                .setAutoCancel(true); // clear notification after click
        Intent intent = new Intent(getApplicationContext(), ActivityInfo.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);
        mNotificationManager.notify(0, mBuilder.build());
    }

    public void open(Activity currentActivity){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure,You want to leave this activity?");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(ActivityInfo.this, UserProfile.class);
                        DatabaseReference owner = FirebaseDatabase.getInstance().getReference("users_history_posted");
                        DatabaseReference showRegisteredUsersRef = FirebaseDatabase.getInstance()
                                .getReference("users_in_activities/Activity_" + currentActivity.getId());
                        showRegisteredUsersRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                try {
                                    owner.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Activity_" + currentActivity.getId()).exists()) {
                                                Toast.makeText(ActivityInfo.this, "You are the activity owner, you cant leave.", Toast.LENGTH_LONG).show();
                                                owner.removeEventListener(this);
                                                showRegisteredUsersRef.removeEventListener(this);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                showRegisteredUsersRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                                                leftNotification(currentActivity.getName());
                                                Toast.makeText(ActivityInfo.this, "You left this activity", Toast.LENGTH_LONG).show();
                                                owner.removeEventListener(this);
                                                showRegisteredUsersRef.removeEventListener(this);
                                                startActivity(intent);
                                                finish();

                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    });
                                } catch (Exception e) {
                                    Toast.makeText(ActivityInfo.this, "You are not in this activity", Toast.LENGTH_LONG).show();
                                    owner.removeEventListener(this);
                                    showRegisteredUsersRef.removeEventListener(this);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ActivityInfo.this,"Thanks for stay with us.",Toast.LENGTH_LONG).show();

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}//class
