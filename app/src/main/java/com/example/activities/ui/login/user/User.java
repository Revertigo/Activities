package com.example.activities.ui.login.user;

import android.content.Intent;
import android.os.Parcelable;
import android.os.Parcel;

import com.example.activities.MainActivity;
import com.example.activities.SearchActivity.SearchOrPost;
import com.example.activities.data.rtdb.activity.Ipermission;
import com.example.activities.ui.login.LoginActivity;

public class User implements Parcelable, Ipermission {

    private static User currentUser = null;//We save instance of the logged user
    private static final int NUM_USER_PROPS = 9;

    protected String username;
    protected String password;
    protected String firstName;
    protected String lastName;
    protected String permission;
    protected String gender;
    protected String dateOfBirth;
    protected String phone;
    protected String pictureUri;

    //TODO: for later
    //ArrayList<String> hobbies;

    public User() {
    }

    public User(User other) {
        this.username = other.getUsername();
        this.password = other.getPassword();
        this.firstName = other.getFirstName();
        this.lastName = other.getLastName();
        this.permission = other.getPermission();
        this.gender = other.getGender();
        this.dateOfBirth = other.getDateOfBirth();
        this.phone = other.getPhone();
        this.pictureUri = other.getpictureUri();
    }

    public User(String username, String password, String firstName, String lastName,
                String permission, String gender, String dateOfBirth, String phone,String pictureUri) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.permission = permission;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.pictureUri=pictureUri;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        User.currentUser = currentUser;
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


    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setpictureUri(String pictureUri) {
        this.pictureUri = pictureUri;
    }

    public String getpictureUri() {
        return this.pictureUri;
    }


    public String getPermission() {
        return this.permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    //Parcel implementation part:
    //User constructor from Parcel (for the getIntent().getParcelableExtra method)
    public User(Parcel in) {
        String[] data = new String[NUM_USER_PROPS];

        in.readStringArray(data);
        this.username = data[0];
        this.password = data[1];
        this.firstName = data[2];
        this.lastName = data[3];
        this.permission = data[4];
        this.gender = data[5];
        this.dateOfBirth = data[6];
        this.phone = data[7];
        this.pictureUri=data[8];
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
                new String[]{this.username, this.password, this.firstName, this.lastName,
                        this.permission, this.gender, this.dateOfBirth, this.phone,this.pictureUri});
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

    @Override
    public Intent loadMainMenu(LoginActivity la) {
        return new Intent(la, SearchOrPost.class);
    }

    @Override
    public Intent loadProfile(MainActivity ma) {
        //TODO:Imeplement
        return null;
    }
}