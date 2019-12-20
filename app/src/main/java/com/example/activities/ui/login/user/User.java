package com.example.activities.ui.login.user;

import android.os.Parcelable;
import android.os.Parcel;

public class User implements Parcelable {

    private int NUM_USER_PROPS = 7;
    protected String username;
    protected String password;
    protected String name;
    protected String permition;
    protected String gender;
    protected String dateOfBirth;
    protected String location;

    //TODO: for later
    //ArrayList<String> hobbies;

    public User() {
    }

    public User(User other){
        this.username = other.getUsername();
        this.password = other.getPassword();
        this.name = other.getName();
        this.permition = other.getPermition();
        this.gender = other.getGender();
        this.dateOfBirth = other.getDateOfBirth();
        this.location = other.getLocation();
    }

    public User(String email) {
        this.username = email;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String mail) {
        this.username = mail;
    }


    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getPermition() {
        return this.permition;
    }

    public void setPermition(String permition) {
        this.permition = permition;
    }





    //Parcel implementation part:
    //User constructor from Parcel (for the getIntent().getParcelableExtra method)
    public User(Parcel in) {
        String[] data = new String[NUM_USER_PROPS];

        in.readStringArray(data);
        this.username = data[0];
        this.password = data[1];
        this.name = data[2];
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

    //String array from User fields
    //for i.putExtra("newUser",newUser) method
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // TODO Auto-generated method stub
        dest.writeStringArray(
                new String[]{this.username, this.password, this.name,
                        this.permition, this.gender, this.dateOfBirth, this.location});
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {

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
