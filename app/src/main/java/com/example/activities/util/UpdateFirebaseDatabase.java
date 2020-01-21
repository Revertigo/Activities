package com.example.activities.util;


import androidx.annotation.NonNull;

import com.example.activities.data.entities.Activity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateFirebaseDatabase {
    public static void fixDatabaseHistoryPosted() {
        String futurePosted = "users_future_posted";
        String historyPosted = "users_history_posted";
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(futurePosted);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot currentPerson : dataSnapshot.getChildren()) {
                    String UID = currentPerson.getKey();
                    for (DataSnapshot currentActivity : currentPerson.getChildren()) {

                        String[] date = {currentActivity.getValue(Activity.class).getDate().getDay(), currentActivity.getValue(Activity.class).getDate().getMonth(), currentActivity.getValue(Activity.class).getDate().getYear()};
                        String[] time = currentActivity.getValue(Activity.class).getTime().split(":");
                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        Date dateobj = new Date();
                        String[] dateAndTime = df.format(dateobj).split(" ");
                        String[] dateStr = dateAndTime[0].split("/");
                        String[] timeStr = dateAndTime[1].split(":");
                        //check if year passed
                        if (Integer.parseInt(date[2]) < Integer.parseInt(dateStr[2])) {
                            DatabaseReference refToHistoryPosted = FirebaseDatabase.getInstance().
                                    getReference(historyPosted).child(UID).child(currentActivity.getValue(Activity.class).getId());
                            refToHistoryPosted.setValue(currentActivity.getValue(Activity.class));
                            FirebaseDatabase.getInstance().getReference().child(futurePosted).child(UID)
                                    .child(currentActivity.getValue(Activity.class).getId()).removeValue();
                        } else {//year not passed so check month
                            if (Integer.parseInt(date[2]) == Integer.parseInt(dateStr[2])) {//if year are equals check month
                                if ((Integer.parseInt(date[1]) < Integer.parseInt(dateStr[1]))) {//if month passed remove activity
                                    DatabaseReference refToHistoryPosted = FirebaseDatabase.getInstance()
                                            .getReference(historyPosted).child(UID).child(currentActivity.getValue(Activity.class).getId());
                                    refToHistoryPosted.setValue(currentActivity.getValue(Activity.class));
                                    FirebaseDatabase.getInstance().getReference().child(futurePosted).child(UID)
                                            .child(currentActivity.getValue(Activity.class).getId()).removeValue();
                                } else {//month not passed so check days
                                    if (Integer.parseInt(date[1]) == Integer.parseInt(dateStr[1])) {//if same month check days
                                        if (Integer.parseInt(date[0]) < Integer.parseInt(dateStr[0])) {//if day passed so remove
                                            DatabaseReference refToHistoryPosted = FirebaseDatabase.getInstance().getReference(historyPosted).child(UID).child(currentActivity.getValue(Activity.class).getId());
                                            refToHistoryPosted.setValue(currentActivity.getValue(Activity.class));
                                            FirebaseDatabase.getInstance().getReference().child(futurePosted).child(UID)
                                                    .child(currentActivity.getValue(Activity.class).getId()).removeValue();
                                        } else {//day is fine check the time.
                                            if (Integer.parseInt(date[0]) == Integer.parseInt(dateStr[0])) {//if we are in same day check time else all is fine
                                                if (Integer.parseInt(time[0]) < Integer.parseInt(timeStr[0])) {//if hour is passed so remove else check min
                                                    DatabaseReference refToHistoryPosted = FirebaseDatabase.getInstance().getReference(historyPosted).child(UID).child(currentActivity.getValue(Activity.class).getId());
                                                    refToHistoryPosted.setValue(currentActivity.getValue(Activity.class));
                                                    FirebaseDatabase.getInstance().getReference().child(futurePosted).child(UID)
                                                            .child(currentActivity.getValue(Activity.class).getId()).removeValue();
                                                } else {
                                                    if (Integer.parseInt(time[0]) == Integer.parseInt(timeStr[0])) {//if we are in same hour check min
                                                        if (Integer.parseInt(time[1]) < Integer.parseInt(timeStr[1])) {//if mid passed remove
                                                            DatabaseReference refToHistoryPosted = FirebaseDatabase.getInstance().getReference(historyPosted).child(UID).child(currentActivity.getValue(Activity.class).getId());
                                                            refToHistoryPosted.setValue(currentActivity.getValue(Activity.class));
                                                            FirebaseDatabase.getInstance().getReference().child(futurePosted).child(UID)
                                                                    .child(currentActivity.getValue(Activity.class).getId()).removeValue();
                                                        }
                                                    }
                                                }
                                            }

                                        }
                                    }
                                }
                            }
                        }

                    }
                }

                ref.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    public static void fixDatabaseHistoryJoined() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users_future_joined");
        String futureJoined = "users_future_joined";
        String historyJoined = "users_history_joined";
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot currentPerson : dataSnapshot.getChildren()) {
                    String UID = currentPerson.getKey();
                    for (DataSnapshot currentActivity : currentPerson.getChildren()) {

                        String[] date = {currentActivity.getValue(Activity.class).getDate().getDay(), currentActivity.getValue(Activity.class).getDate().getMonth(), currentActivity.getValue(Activity.class).getDate().getYear()};
                        String[] time = currentActivity.getValue(Activity.class).getTime().split(":");
                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        Date dateobj = new Date();
                        String[] dateAndTime = df.format(dateobj).split(" ");
                        String[] dateStr = dateAndTime[0].split("/");
                        String[] timeStr = dateAndTime[1].split(":");
                        //check if year passed
                        if (Integer.parseInt(date[2]) < Integer.parseInt(dateStr[2])) {
                            DatabaseReference refToHistoryPosted = FirebaseDatabase.getInstance().
                                    getReference(historyJoined).child(UID).child(currentActivity.getValue(Activity.class).getId());
                            refToHistoryPosted.setValue(currentActivity.getValue(Activity.class));
                            FirebaseDatabase.getInstance().getReference().child(futureJoined).child(UID)
                                    .child( currentActivity.getValue(Activity.class).getId()).removeValue();
                        } else {//year not passed so check month
                            if (Integer.parseInt(date[2]) == Integer.parseInt(dateStr[2])) {//if year are equals check month
                                if ((Integer.parseInt(date[1]) < Integer.parseInt(dateStr[1]))) {//if month passed remove activity
                                    DatabaseReference refToHistoryPosted = FirebaseDatabase.getInstance().getReference(historyJoined).child(UID).child(currentActivity.getValue(Activity.class).getId());
                                    refToHistoryPosted.setValue(currentActivity.getValue(Activity.class));
                                    FirebaseDatabase.getInstance().getReference().child(futureJoined).child(UID)
                                            .child(currentActivity.getValue(Activity.class).getId()).removeValue();
                                } else {//month not passed so check days
                                    if (Integer.parseInt(date[1]) == Integer.parseInt(dateStr[1])) {//if same month check days
                                        if (Integer.parseInt(date[0]) < Integer.parseInt(dateStr[0])) {//if day passed so remove
                                            DatabaseReference refToHistoryPosted = FirebaseDatabase.getInstance().getReference(historyJoined).child(UID).child(currentActivity.getValue(Activity.class).getId());
                                            refToHistoryPosted.setValue(currentActivity.getValue(Activity.class));
                                            FirebaseDatabase.getInstance().getReference().child(futureJoined).child(UID)
                                                    .child( currentActivity.getValue(Activity.class).getId()).removeValue();
                                        } else {//day is fine check the time.
                                            if (Integer.parseInt(date[0]) == Integer.parseInt(dateStr[0])) {//if we are in same day check time else all is fine
                                                if (Integer.parseInt(time[0]) < Integer.parseInt(timeStr[0])) {//if hour is passed so remove else check min
                                                    DatabaseReference refToHistoryPosted = FirebaseDatabase.getInstance().getReference(historyJoined).child(UID).child(currentActivity.getValue(Activity.class).getId());
                                                    refToHistoryPosted.setValue(currentActivity.getValue(Activity.class));
                                                    FirebaseDatabase.getInstance().getReference().child(futureJoined).child(UID)
                                                            .child(currentActivity.getValue(Activity.class).getId()).removeValue();
                                                } else {
                                                    if (Integer.parseInt(time[0]) == Integer.parseInt(timeStr[0])) {//if we are in same hour check min
                                                        if (Integer.parseInt(time[1]) < Integer.parseInt(timeStr[1])) {//if mid passed remove
                                                            DatabaseReference refToHistoryPosted = FirebaseDatabase.getInstance().getReference(historyJoined).child(UID).child(currentActivity.getValue(Activity.class).getId());
                                                            refToHistoryPosted.setValue(currentActivity.getValue(Activity.class));
                                                            FirebaseDatabase.getInstance().getReference().child(futureJoined).child(UID)
                                                                    .child(currentActivity.getValue(Activity.class).getId()).removeValue();
                                                        }
                                                    }
                                                }
                                            }

                                        }
                                    }
                                }
                            }
                        }

                    }

                }
                ref.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
