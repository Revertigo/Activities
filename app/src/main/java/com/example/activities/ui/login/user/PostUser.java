package com.example.activities.ui.login.user;

import android.os.Parcel;

public class PostUser extends User {

    private int NUM_USER_PROPS = 9;
    String occupation;
    String education;

    public PostUser(User user){
        super(user);
        this.occupation = "";
        this.education ="";
    }

    public void setOccupation(String occupation){
        this.occupation = occupation;
    }

    public void setEducation(String education){
        this.education = education;
    }


    //Parcel implementation part:
    //User constructor from Parcel (for the getIntent().getParcelableExtra method)
    public PostUser(Parcel in){
        String[] data= new String[NUM_USER_PROPS];

        in.readStringArray(data);
        this.email = data[0];
        this.password = data[1];
        this.username = data[2];
        this.permition = data[3];
        this.gender = data[4];
        this.dateOfBirth = data[5];
        this.location = data[6];
        this.occupation = data[7];
        this.education = data[8];
    }

}
