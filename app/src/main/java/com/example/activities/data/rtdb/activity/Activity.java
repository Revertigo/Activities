package com.example.activities.data.rtdb.activity;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;

public class Activity implements Parcelable {
    private static long id_counter;//For autoincrement of id's
    private final int NUM_USER_PROPS = 15;
    private String postedUser;
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


    public Activity() {
    }

    public Activity(String postedUser,String name, String Type, Address addr, String difficulty, boolean group,
                    String gender, String description, Date date, String time) {
        this.postedUser=postedUser;
        this.name = name;
        this.type = Type;
        this.addr = addr;
        this.difficulty = difficulty;
        this.group = group;
        this.gender = gender;
        this.description = description;
        this.date = date;
        this.time = time;
    }

    public static class Date {
        private String day;
        private String month;
        private String year;


        public Date() {
            this.year = "";
            this.month = "";
            this.day = "";
        }

        public Date(String day, String month, String year) {
            this.day = day;
            this.month = month;
            this.year = year;
        }

        public String getYear() {
            return this.year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getMonth() {
            return this.month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getDay() {
            return this.day;
        }

        public void setDay(String day) {
            this.day = day;
        }
    }

    //date
    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public static class Address {
        private String city_set;//City or settlement
        private String street;
        private int apartment_number;//zero for none

        public Address() {
            city_set = "";
            street = "";

        }

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

    //addr
    public Address getAddr() {
        return this.addr;
    }

    public void setAddr(Address addr) {
        this.addr = addr;

    }


    public Activity(Parcel in) {
        String[] data = new String[NUM_USER_PROPS];
        in.readStringArray(data);

        //This shouldn't be in here, but due to null exception according we will keep it here.
        this.addr = new Address();
        this.date = new Date();
        this.id = Long.parseLong(data[0]);
        this.postedUser = data[1];
        this.name = data[2];
        this.type = data[3];
        this.addr.city_set = data[4];
        this.addr.street = data[5];
        this.addr.apartment_number = Integer.parseInt(data[6]);
        this.difficulty = data[7];
        this.gender = data[8];
        this.description = data[9];
        this.date.day = data[10];//Since we will update later this.date
        this.date.month = data[11];
        this.date.year = data[12];
        this.time = data[13];
        this.group = Boolean.parseBoolean(data[14]);

    }

    public enum Gender {
        FEMALE, MALE, BOTH
    }

    //id
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }


    //name
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //type
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }


    //difficulty
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDifficulty() {
        return this.difficulty;
    }

    public void setGroup(boolean ans) {
        this.group = ans;
    }

    public boolean isGroup() {
        return group;
    }

    //gender of the activity
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return this.gender;
    }

    //description of the activity
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }


    //time format
    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public static long getId_counter() {
        return id_counter;
    }

    public static void setId_counter(long id_counter) {
        Activity.id_counter = id_counter;
    }

    public void setpostedUser(String postedUser) {
        this.postedUser = postedUser;
    }

    public String getpostedUser() {
        return this.postedUser;
    }

    //implementation

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(
                new String[]{Long.toString(this.id), this.postedUser, this.name, this.type, this.addr.city_set, this.addr.street,
                        Integer.toString(this.addr.apartment_number), this.difficulty, this.gender, this.description,
                        this.date.day, this.date.month, this.date.year, this.time, String.valueOf(this.group)});
    }

    public static final Parcelable.Creator<Activity> CREATOR = new Parcelable.Creator<Activity>() {

        @Override
        public Activity createFromParcel(Parcel source) {
            return new Activity(source);  //using parcelable constructor
        }

        @Override
        public Activity[] newArray(int size) {
            return new Activity[size];
        }
    };

    public String toString() {
        String activityFor;
        if (isGroup()) {
            activityFor = "Group";
        } else {
            activityFor = "Single";
        }
        return (
                        "Posted user: " + getpostedUser() + "\n" +
                        "Activity's id: " + getId() + "\n" +
                        "Activity's name: " + getName() + "\n" +
                        "Activity's type: " + getType() + "\n" +
                        "Occurrence City: " + getAddr().getCity_set() + "\n" +
                        "Street name: " + getAddr().getStreet() + "\n" +
                        "Apartment number:" + getAddr().getApartment_number() + "\n" +
                        "Difficulty classification: " + getDifficulty() + "\n" +
                        "Activity for: " + activityFor + "\n" +
                        "Gender suitable: " + getGender() + "\n" +
                        "Activity Description: " + getDescription() + "\n" +
                        "Date: " + getDate().getDay() + "/" + getDate().getMonth() + "/" + getDate().getYear() + "\n" +
                        "Time:" + getTime());
    }
}