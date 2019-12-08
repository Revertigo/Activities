package com.example.activities.data.rtdb.activity;

import java.io.Serializable;
import java.util.Date;

public class Activity implements Serializable {
    private static long id_counter = 1;//For autoincrement of id's

    private long id;//Very long id number
    private String name;//Activity name
    private String Type;
    private Address addr;//Activity address
    private String difficulty;
    private boolean group;//True for Group, False for single
    private String gender;
    private String description;
    private Date date;
    private String time;//Format: hh:mm

    public Activity(String name, String Type, Address addr, String difficulty, boolean group,
                    String gender, String description, Date date, String time) {
        this.id = id_counter++;
        this.name = name;
        this.Type = Type;
        this.addr = addr;
        this.difficulty = difficulty;
        this.group = group;
        this.gender = gender;
        this.description = description;
        this.date = date;
        this.time = time;
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

    public enum Gender {
        FEMALE, MALE, BOTH
    }

    //id
    public long getID() {
        return this.id;
    }

    public void setID(long id) {
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
        return this.Type;
    }

    public void setType(String type) {
        this.Type = type;
    }

    //addr
    public Address getAddr() {
        return this.addr;
    }

    //difficulty


    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDifficulty() {
        return this.difficulty;
    }

    //activity for single or group getter setter
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

    //date
    public Date getDate() {
        return this.date;
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

    public static void setId_counter(long id_counter) {Activity.id_counter = id_counter; }
}
