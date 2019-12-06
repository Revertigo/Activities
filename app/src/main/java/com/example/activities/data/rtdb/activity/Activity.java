package com.example.activities.data.rtdb.activity;

import java.util.Date;

public class Activity {
    private static int id_counter = 0;//For autoincrement of id's

    private long id;//Very long id number
    private String name;//Activity name
    private String Type;
    private Address addr;//Activity address
    private String difficulty;
    private boolean single_group;//True for Group, False for single
    private String gender;
    private String description;
    private Date date;
    private String time;//Format: hh:mm
    private String activityFor;

    static String[] names = {"ID", "Name", "Type", "Address-City", "Address-Street", "Apart Num",
            "Difficulty", "Single-Group", "Gender", "Description", "Date", "Time","Activity for"};

    public static String getNames(int i) {
        return names[i];
    }


    public Activity(String name, String Type, Address addr, String difficulty, boolean single_group,
                    String gender, String description, Date date, String time,String activityFor) {
        this.id = id_counter++;
        this.name = name;
        this.Type = Type;
        this.addr = addr;
        this.difficulty = difficulty;
        this.single_group = single_group;
        this.gender = gender;
        this.description = description;
        this.date = date;
        this.time = time;
        this.activityFor=activityFor;
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

        public String getCity() {
            return this.city_set;
        }

        public String getStreet() {
            return this.street;
        }

        public String getApartNumString() {
            return Integer.toString(this.apartment_number);
        }

        public int getApartNumInt() {
            return this.apartment_number;
        }
    }

    public String[] getData() {
        String[] data_array = new String[13];//array with the strings of the data(easy insert to database)

        data_array[0] = Long.toString(this.id); // the id of the activity
        data_array[1] = this.name;//the name of the activity
        data_array[2] = this.Type;//type of activity
        data_array[3] = this.addr.getCity();//city of the activity
        data_array[4] = this.addr.getStreet();//street of the activity
        data_array[5] = this.addr.getApartNumString();//apartment number of the activity
        data_array[6] = this.difficulty;//the difficult of the activity

        if (single_group == true) {
            data_array[7] = "true";
        }//group
        else {
            data_array[7] = "false";
        } //singe
        if (gender.equals("Female")) {//gender of the activity
            data_array[8] = "female";
        } else if (gender.equals("Male")) {
            data_array[8] = "male";
        }//gender of the activity
        else {
            data_array[8] = "both";
        } //gender of the activity
        data_array[9] = this.description; //the describe of the activity
        data_array[10] = "date"; //the date of the activity post
        data_array[11] = this.time;//format
        data_array[12]=this.activityFor;
        return data_array;
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
    public void setSingle_Group(boolean ans) {
        this.single_group = ans;
    }

    public boolean getSingle_Group() {
        return this.single_group;
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
    public void setActivityFor(String activityFor){this.activityFor=activityFor;}
    public String getActivityFor(){return this.activityFor;}

}
