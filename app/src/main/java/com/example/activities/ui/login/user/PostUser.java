package com.example.activities.ui.login.user;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.activities.MainActivity;
import com.example.activities.SearchActivity.SearchOrPost;
import com.example.activities.data.rtdb.activity.Ipermission;
import com.example.activities.data.rtdb.activity.ShowFuturePost;
import com.example.activities.data.rtdb.activity.ShowHistoryPost;

import androidx.appcompat.app.AppCompatActivity;

/** PostUser class represents the user with advertising permission **/
public class PostUser extends User implements Ipermission {
    // Parcelable parameter:
    private static final int NUM_USER_PROPS = 11;

    // Extra fields besides that are in User class
    private String occupation;
    private String education;


    /**
     * Constructor
     * @param user
     */
    public PostUser(User user) {
        super(user);
        this.occupation = "default_occupation";
        this.education = "default_education";
    }

    public PostUser(String username, String password, String firstName, String lastName, String permission, String gender,
                    String dateOfBirth, String phone, String pictureUri, String occupation, String education) {
        super(username, password, firstName, lastName, permission, gender, dateOfBirth, phone, pictureUri);
        this.occupation = occupation;
        this.education = education;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getOccupation() {
        return this.occupation;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getEducation() {
        return this.education;
    }


    //Parcel implementation part:
    //User constructor from Parcel (for the getIntent().getParcelableExtra method)
    public PostUser(Parcel in) {
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
        this.pictureUri = data[8];
        this.occupation = data[9];
        this.education = data[10];
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
                        this.permission, this.gender, this.dateOfBirth, this.phone, this.pictureUri, this.occupation, this.education});
    }

    public static final Parcelable.Creator<PostUser> CREATOR = new Parcelable.Creator<PostUser>() {

        @Override
        public PostUser createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            return new PostUser(source);  //using parcelable constructor
        }

        @Override
        public PostUser[] newArray(int size) {
            // TODO Auto-generated method stub
            return new PostUser[size];
        }
    };

    @Override
    public Intent loadMainMenu(AppCompatActivity la) {
        return new Intent(la, SearchOrPost.class);
    }

    @Override
    public Intent loadProfile(AppCompatActivity ma) {
        //TODO:Imeplement
        return null;
    }

    @Override
    public Intent loadHistory(AppCompatActivity la) {
        return new Intent(la, ShowHistoryPost.class);
    }

    @Override
    public Intent loadFuture(AppCompatActivity la) {
        return new Intent(la, ShowFuturePost.class);
    }

}
