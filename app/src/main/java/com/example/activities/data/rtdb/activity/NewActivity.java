package com.example.activities.data.rtdb.activity;


import android.os.Parcelable;
import android.os.Parcel;

public class NewActivity implements Parcelable {
    private static int id_counter = 0;//For autoincrement of id's
    private final int NUM_USER_PROPS = 12;

    private long id;//Very long id number
    private String name;//Activity name
    private String type;
    String city;
    String street;
    String apartNum;
    private String difficulty;
    private String gender;
    private String description;
    private String date;
    private String timeFormat;//Format: hh:mm
    private String activityFor;

    static String[] names = {"ID", "Name", "Type", "Address-City", "Address-Street", "Apart Num",
            "Difficulty",  "Gender", "Description", "Date", "Time Format","Activity for"};

    public static String getNames(int i) {
        return names[i];
    }

    public NewActivity(){}

    public NewActivity(String name, String Type, String difficulty, String gender, String description,String activityFor) {
        this.id = Long.parseLong(Integer.toString(id_counter++));
        this.name = name;
        this.type = Type;
        this.difficulty = difficulty;
        this.gender = gender;
        this.description = description;
        this.activityFor=activityFor;
    }
    public void completeDataInit(String city,String street,String apartNum,String date,String timeFormat){
        this.city=city;
        this.street=street;
        this.apartNum=apartNum;
        this.date=date;
        this.timeFormat=timeFormat;
    }




    public String[] getData() {
        String[] data_array = new String[NUM_USER_PROPS];//array with the strings of the data(easy insert to database)

        data_array[0] = Long.toString(this.id); // the id of the activity
        data_array[1] = this.name;//the name of the activity
        data_array[2] = this.type;//type of activity
        data_array[3] = this.city;//city of the activity
        data_array[4] = this.street;//street of the activity
        data_array[5] = this.apartNum;//apartment number of the activity
        data_array[6] = this.difficulty;//the difficult of the activity
        data_array[7]=this.gender;
        data_array[8] = this.description; //the describe of the activity
        data_array[9] =this.date; //the date of the activity post
        data_array[10] = this.timeFormat;//format
        data_array[11]=this.activityFor;
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
    public String getDate() {
        return this.date;
    }

    //time format
    public String getTime() {
        return this.timeFormat;
    }

    public void setTime(String time) {
        this.timeFormat = time;
    }
    public void setActivityFor(String activityFor){this.activityFor=activityFor;}
    public String getActivityFor(){return this.activityFor;}

    //implementation
    //implementation
    public NewActivity(Parcel in) {

        String[] data = new String[NUM_USER_PROPS];
        in.readStringArray(data);
        this.id = Long.parseLong(data[0]);
        this.name = data[1];
        this.type = data[2];
        this.city = data[3];
        this.street = data[4];
        this.apartNum = data[5];
        this.difficulty = data[6];
        this.gender=data[7];
        this.description=data[8];
        this.date=data[9];
        this.timeFormat=data[10];
        this.activityFor=data[11];
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(
                new String[]{Long.toString(this.id),this.name, this.type,this.city,
                        this.street,
                        this.apartNum ,this.difficulty,this.gender,this.description,this.date,this.timeFormat, this.activityFor});
    }
    public static final Parcelable.Creator<NewActivity> CREATOR = new Parcelable.Creator<NewActivity>() {

        @Override
        public NewActivity createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            return new NewActivity(source);  //using parcelable constructor
        }

        @Override
        public NewActivity[] newArray(int size) {
            // TODO Auto-generated method stub
            return new NewActivity[size];
        }
    };

}
