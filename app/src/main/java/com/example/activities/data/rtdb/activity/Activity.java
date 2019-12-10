package com.example.activities.data.rtdb.activity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Activity implements Parcelable {
    //Todo  ,implemenst Serializable


    private static long id_counter = 1;//For autoincrement of id's

    private final int NUM_USER_PROPS = 12;

    private long id=id_counter;//Very long id number
    private String name="def_name";//Activity name
    private String type="def_type";
    private Activity.Address addr=new Address("def_city","def_street",1);//Activity address
    private String difficulty="diffic";
    private String gender="gender";
    private String description="desc";
    private Date date=new Date();
    private String timeFormat="def_format";//Format: hh:mm
    private boolean group=true;//True for Group, False for single

    DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);

    public Activity() {}

        public Activity(String name, String type, String difficulty,
                        String gender, String description ,String group) {
            this.id=getId_counter();
            setId_counter(getId_counter()+1);
            this.name = name;
            this.type = type;
            this.difficulty = difficulty;
            this.group = Boolean.valueOf(group);
            this.gender = gender;
            this.description = description;
        }

        public void completeDataInit (Activity.Address addr, String currentTime, String timeFormat){
        this.addr=addr;
            try {
                this.date =format.parse(currentTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            this.timeFormat = timeFormat;
        }

    public Activity(Parcel in) {
        String[] data = new String[NUM_USER_PROPS];
        in.readStringArray(data);

        this.id = Long.parseLong(data[0]);
        this.name = data[1];
        this.type = data[2];
        this.addr.city_set = data[3];
        this.addr.street = data[4];
        this.addr.apartment_number = Integer.parseInt(data[5]);
        this.difficulty = data[6];
        this.gender = data[7];
        this.description = data[8];
        //Todo check if date its ok
        try {
            this.date =format.parse(data[9]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.timeFormat = data[10];
        if(data[11].equals("False")){
            this.group =false;}
        else {this.group=true;}

    }

        public static class Address {
            public String city_set;//City or settlement
            public String street;
            public int apartment_number;//zero for none

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
        }
        public Activity.Address getAddr(){return  this.addr;}


        public enum Gender {
            FEMALE, MALE, BOTH
        }


        //id
        public long getID () {
            return this.id;
        }

        public void setID ( long id){
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
        public boolean isGroup () {
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

        //time format
        public String getTime () {
            return this.timeFormat;
        }

        public void setTime (String time){
            this.timeFormat = time;
        }
        public static long getId_counter () {
            return id_counter;
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
                            Integer.toString(this.addr.apartment_number), this.difficulty, this.gender, this.description, new SimpleDateFormat("MM/dd/yyyy").format(this.date), this.timeFormat, String.valueOf(this.group)});
        }
        public static final Parcelable.Creator<Activity> CREATOR = new Parcelable.Creator<Activity>() {

            @Override
            public Activity createFromParcel(Parcel source) {
                // TODO Auto-generated method stub
                return new Activity(source);  //using parcelable constructor
            }

            @Override
            public Activity[] newArray(int size) {
                // TODO Auto-generated method stub
                return new Activity[size];
            }
        };

        public static void setId_counter ( long id_counter){
            Activity.id_counter = id_counter;
        }

}