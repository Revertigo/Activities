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
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class CsvReader {
    public static final String CITIES_AND_SETTLEMENTS = "server/cities_settlements";
    public static final String CITIES_AND_SETTLEMENTS_HEBREW = "server/cities_settlements_hebrew";
    public static final String STREETS = "server/streets";

    public static HashMap<String, Object> all_streets = new HashMap<String, Object>();
    public static ArrayList<String> cities = new ArrayList<String>();

    private static DatabaseReference mDatabase = null;

    /*
      Sequence of method should be:
        ReadCSVFile(fileNameInsideAssets, this)
        prepeareCsvForDB(recordsFromLastCall)
        writeCitiesAndStreetsToDB(MatFromLastCall)
     */


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

    public static String [][] prepeareCsvForDB(String[] records)
    {
        String [][]outputMat = new String[records.length][2];

        for(int i = 0; i < records.length; i++)
        {
            String [] parsed = records[i].split(",");
            outputMat[i][0] = parsed[0];//settlment
            outputMat[i][1] = parsed[1];//street
        }

        return outputMat;
    }

    public static void writeCitiesToDB(String[] records, String path) {
        mDatabase = FirebaseDatabase.getInstance().getReference(path);//Get Database instance

        //Write data into FireBase realtime database
        for (int i = 0; i < records.length; i++) {
            mDatabase.child("cities_settelments" + i).setValue(records[i]);
        }
    }

    /**
     * This function used to write settelment + streets records to the database
     * parsed from the CSV file.
     * @param records
     * @param path
     */
    public static void writeCitiesAndStreetsToDB(String[][] records, String path) {

        //Write data into FireBase realtime database
        for (int i = 0; i < records.length; i++) {
            mDatabase = FirebaseDatabase.getInstance().getReference(path + "/" + records[i][0]);//Get Database instance
            mDatabase.child("" + i).setValue(records[i][1]);
        }
    }

    public static void readCitiesFromDB(String path_to_collection, String record) {
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

    public static void readCitiesAndStreetsFromDB(String path_to_collection, String record) {
        mDatabase = FirebaseDatabase.getInstance().getReference(path_to_collection);//Get Database instance

        mDatabase.addChildEventListener(new ChildEventListener() {
        //mDatabase.orderByChild(record).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                try{
                    all_streets.put(dataSnapshot.getKey().trim(), (HashMap<Integer, String>)dataSnapshot.getValue());
                }catch (Exception e)
                {
                    HashMap<Integer, String> dst_hm = new HashMap<Integer, String>();
                    ArrayList<String> src_al = new ArrayList<String>((ArrayList<String>)dataSnapshot.getValue());
                    for(int i = 0; i < src_al.size(); i++)
                    {
                        String item = src_al.get(i);
                        if(item != null)
                        {
                            dst_hm.put(i, item);
                        }
                    }
                    //After converting into hashmap, put it inside
                    all_streets.put(dataSnapshot.getKey().trim(),dst_hm);
                }
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

    public static void reorganize_data(List<String> col, String Record_to_front) {
        Collections.sort(col);
        col.remove(Record_to_front);//Remove from the list the first slot
        col.add(0, Record_to_front);//Re add it after sorting
    }
}