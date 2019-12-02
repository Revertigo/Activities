package com.example.activities;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class CsvReader {
    static final String cities_and_settlements = "server/cities_settlements";
    static final String streets = "server/streets";

    public static ArrayList<String> cities = new ArrayList<String>();

    private static DatabaseReference mDatabase = null;

    public static String [] ReadCSVFile(String path, Context c) {
        String [] tokens = null;

        try(InputStream is = c.getAssets().open(path);){
//            File file = new File(c.getAssets().open);
//            copyInputStreamToFile(is, file);
//            CSVReader reader = new CSVReader(new FileReader(file));
//            String[] nextLine;
//            while ((nextLine = reader.readNext()) != null) {
//                // nextLine[] is an array of values from the line
//                Log.wtf("Data", nextLine[0] + " " + nextLine[1] + " " + nextLine[2] + " " + nextLine[3]);
//            }
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            String output = new String(buffer);
            tokens = output.split("\\r?\\n");//Split by

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tokens;
    }

    static void writeRecordsToDB(String [] records)
    {
        mDatabase = FirebaseDatabase.getInstance().getReference(cities_and_settlements);//Get Database instance

        //Write data into FireBase realtime database
        for (int i = 0; i < records.length; i++) {
            mDatabase.child("cities_settelments" + i).setValue(records[i]);
        }
    }

    static void readRecordsfromDB(String path_to_collection, String record)
    {
        mDatabase = FirebaseDatabase.getInstance().getReference(path_to_collection);//Get Database instance

        mDatabase.orderByChild(record).addChildEventListener(new ChildEventListener(){
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                String city = dataSnapshot.getValue(String.class);
                cities.add(city);
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


}