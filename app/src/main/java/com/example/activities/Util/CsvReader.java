package com.example.activities.Util;

import android.content.Context;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class CsvReader {
    public static final String CITIES_AND_SETTLEMENTS = "server/cities_settlements";
    static final String streets = "server/streets";

    public static ArrayList<String> cities = new ArrayList<String>();

    private static DatabaseReference mDatabase = null;

    //Static block to initialize out ArrayList
    static {
        cities.add("City/Settlement");
    }

    public static String[] ReadCSVFile(String path, Context c) {
        String[] tokens = null;

        try (InputStream is = c.getAssets().open(path);) {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            String output = new String(buffer);
            tokens = output.split("\\r?\\n");//Split by line feed and carriage return

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tokens;
    }

    static void writeRecordsToDB(String[] records) {
        mDatabase = FirebaseDatabase.getInstance().getReference(CITIES_AND_SETTLEMENTS);//Get Database instance

        //Write data into FireBase realtime database
        for (int i = 0; i < records.length; i++) {
            mDatabase.child("cities_settelments" + i).setValue(records[i]);
        }
    }

    public static void readRecordsfromDB(String path_to_collection, String record) {
        mDatabase = FirebaseDatabase.getInstance().getReference(path_to_collection);//Get Database instance

        mDatabase.orderByChild(record).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                //Add one city/settlement each time function is called
                cities.add(dataSnapshot.getValue(String.class));
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
    }

    public static void reorganize_data() {
        Collections.sort(CsvReader.cities);
        CsvReader.cities.remove("City/Settlement");//Remove from the list the first slot
        CsvReader.cities.add(0, "City/Settlement");//Re add it after sorting
    }
}