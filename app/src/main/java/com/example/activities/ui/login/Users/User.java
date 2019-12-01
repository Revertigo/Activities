package com.example.activities.ui.login.Users;

import android.os.Parcelable;
import android.os.Parcel;

public class User implements Parcelable{


    public String email;
    private String password;
    public String username;
    private String permition;

    public String gender;
    public String dateOfBirth;
    public String location;

    //to do: for later
    //ArrayList<String> hobbies;

    public User(){}

    public User(String email){
        this.email = email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPermition(String permition) {
        this.permition = permition;
    }

    //parcel part
    public User(Parcel in){
        String[] data= new String[3];

        in.readStringArray(data);
        this.username = data[0];
        this.email = data[1];
        this.password = data[2];
        this.permition = data[3];
        this.gender = data[4];
        this.dateOfBirth = data[5];
        this.location = data[6];
    }

    @Override
    public int describeContents() {
    // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    // TODO Auto-generated method stub
        dest.writeStringArray(
                new String[]{this.username, this.email, this.password,
                         this.permition, this.gender, this.dateOfBirth, this.location});
    }

    public static final Parcelable.Creator<User> CREATOR= new Parcelable.Creator<User>() {

        @Override
        public User createFromParcel(Parcel source) {
        // TODO Auto-generated method stub
            return new User(source);  //using parcelable constructor
        }

        @Override
        public User[] newArray(int size) {
        // TODO Auto-generated method stub
            return new User[size];
        }
    };


}
