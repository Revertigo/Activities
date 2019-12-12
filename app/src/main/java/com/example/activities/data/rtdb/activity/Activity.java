package com.example.activities.data.rtdb.activity;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Activity implements Parcelable{

    private static long id_counter;//For autoincrement of id's
    private final int NUM_USER_PROPS = 12;

    private long id;//Very long id number
    private String name;//Activity name
    private String type;
    private Address addr;//Activity address
    private String difficulty;
    private boolean group;//True for Group, False for single
    private String gender;
    private String description;
    private Date date;
    private String time;//Format: hh:mm



    public Activity(String name, String type, Address addr, String difficulty, boolean group,
                    String gender, String description, Date date, String time) {
        this.id = id_counter++;
        this.name = name;
        this.type = type;
        this.addr = addr;
        this.difficulty = difficulty;
        this.group = group;
        this.gender = gender;
        this.description = description;
        this.date = date;
        this.time = time;
    }

    public void Activity(){}
    public void Activity(Activity activity){
        this.id=activity.getId();
        this.name=activity.getName();
        this.type=activity.getType();
        this.addr=activity.getAddr();
        this.difficulty=activity.getDifficulty();
        this.group=activity.getGroup();
        this.gender=activity.getGender();
        this.description=activity.getDescription();
        this.date=activity.getDate();
        this.time=activity.getTime();
    }

    public void completeDataInit (Activity.Address addr, String currentTime, String timeFormat){
//DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        DateFormat format = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
        this.addr=addr;
        try {
            this.date =format.parse(currentTime);
            Log.wtf("This date send: ", date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.time = timeFormat;
    }

    public static class Address {
        private String city_set;//City or settlement
        private String street;
        private int apartment_number;//zero for none

        public Address() { }

        public Address(String city_set, String street, int apartment_number) {
            this.city_set = city_set;
            this.street = street;
            this.apartment_number = apartment_number;
        }

        public String getCity_set() {
            return city_set;
        }
        public String getStreet() {
            return street;
        }
        public int getApartment_number() {
            return apartment_number;
        }

        public void setCity_set(String city_set) {
            this.city_set = city_set;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public void setApartment_number(int apartment_number) {
            this.apartment_number = apartment_number;
        }
    }

    public Activity(Parcel in) {
        String[] data = new String[NUM_USER_PROPS];
        in.readStringArray(data);

//This shouldn't be in here, but due to null exception according we will keep it here.
        this.addr = new Address();

        this.id = Long.parseLong(data[0]);
        this.name = data[1];
        this.type = data[2];
        this.addr.city_set = data[3];
        this.addr.street = data[4];
        this.addr.apartment_number = Integer.parseInt(data[5]);
        this.difficulty = data[6];
        this.gender = data[7];
        this.description = data[8];
        DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
//Todo check if date its ok
        try {
            this.date =format.parse(data[9]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.time = data[10];
        if(data[11].equals("False")){
            this.group =false;}
        else {this.group=true;}

    }

    public enum Gender {
        FEMALE, MALE, BOTH
    }

    //id
    public long getId () {
        return this.id;
    }
    public void setId ( long id){
        this.id = id;
    }


    //name
    public String getName () {
        return this.name;
    }
    public void setName (String name){
        this.name = name;
    }

    //type
    public String getType () {
        return this.type;
    }
    public void setType (String type){
        this.type = type;
    }

    //addr
    public Address getAddr() {
        return this.addr;
    }
    public void setAddr(Address addr) {
        this.addr = addr;
    }

    //difficulty
    public void setDifficulty (String difficulty){
        this.difficulty = difficulty;
    }
    public String getDifficulty () {
        return this.difficulty;
    }

    public void setGroup ( boolean ans){
        this.group = ans;
    }
    public boolean getGroup () {
        return group;
    }

    //gender of the activity
    public void setGender (String gender){
        this.gender = gender;
    }
    public String getGender () {
        return this.gender;
    }

    //description of the activity
    public void setDescription (String description){
        this.description = description;
    }
    public String getDescription () {
        return this.description;
    }

    //date
    public Date getDate () {
        return this.date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    //time format
    public String getTime () {
        return this.time;
    }
    public void setTime (String time){
        this.time = time;
    }

    public static long getId_counter () {
        return id_counter;
    }
    public static void setId_counter ( long id_counter){
        Activity.id_counter = id_counter;
    }

//implementation

    @Override
    public int describeContents () {
        return 0;
    }

    @Override
    public void writeToParcel (Parcel dest,int flags){
        dest.writeStringArray(
                new String[]{Long.toString(this.id), this.name, this.type, this.addr.city_set,
                        this.addr.street,
                        Integer.toString(this.addr.apartment_number), this.difficulty, this.gender, this.description, new SimpleDateFormat("MM/dd/yyyy").format(this.date), this.time, String.valueOf(this.group)});
    }
    public static final Parcelable.Creator<Activity> CREATOR = new Parcelable.Creator<Activity>() {

        @Override
        public Activity createFromParcel(Parcel source) {
// TODO Auto-generated method stub
            return new Activity(source); //using parcelable constructor
        }

        @Override
        public Activity[] newArray(int size) {
// TODO Auto-generated method stub
            return new Activity[size];
        }
    };
}