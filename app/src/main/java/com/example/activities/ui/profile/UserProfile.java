package com.example.activities.ui.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.activities.R;
import com.example.activities.data.entities.user.User;
import com.squareup.picasso.Picasso;

public class UserProfile extends AppCompatActivity {
    private TextView name, email, gender, permission, birthday, phone;
    private Button backToMainMenu;
    private Button showMyActivities;
    private Button activitiesHistory;


    private ImageView editProfile;
    private ImageView profileImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        name = findViewById(R.id.nameOfTheUserProfile);
        email = findViewById(R.id.emailOfTheUserProfile);
        gender = findViewById(R.id.genderOfTheUserProfile);
        permission = findViewById(R.id.permissionOfTheUserProfile);
        birthday = findViewById(R.id.birthdayOfTheUserProfile);
        phone = findViewById(R.id.phoneOfTheUserProfile);
        profileImage = findViewById(R.id.profileImageUser);

        editProfile = findViewById(R.id.editImageViewUserProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserProfile.this, UserProfileEditable.class);
                startActivity(i);
                finish();
            }

        });

        backToMainMenu = findViewById(R.id.backToSearchOrPostUser);
        backToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = User.getCurrentUser().loadMainMenu(UserProfile.this);
                startActivity(intent);
                finish();
            }
        });

        activitiesHistory = findViewById(R.id.activitiesHistoryUser);
        activitiesHistory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = User.getCurrentUser().loadHistory(UserProfile.this);
                startActivity(i);
                finish();
            }
        });


        loadUserPicture();
        name.setText(User.getCurrentUser().getFirstName() + " " + User.getCurrentUser().getLastName());
        phone.setText(User.getCurrentUser().getPhone());
        email.setText(User.getCurrentUser().getUsername());
        permission.setText(User.getCurrentUser().getPermission());
        birthday.setText(User.getCurrentUser().getDateOfBirth());
        gender.setText(User.getCurrentUser().getGender());


        showMyActivities = findViewById(R.id.myPostedActivitiesUser);
        showMyActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = User.getCurrentUser().loadFuture(UserProfile.this);
                startActivity(in);
                finish();
            }
        });
    }

    private void loadUserPicture() {
        String pathToPicture = User.getCurrentUser().getpictureUri();
        if (pathToPicture != null) {
            //check if the user has already an profile image
            // if so, Load it
            if (!(pathToPicture.equals(""))) {
                Picasso.get()
                        .load(pathToPicture)
                        .transform(new CircleTransform())
                        .fit()
                        .into(profileImage);
            } else {
                profileImage.setImageResource(R.drawable.ic_person_black_24dp);

            }
            //uses default image
        }  else {
            profileImage.setImageResource(R.drawable.ic_person_black_24dp);

        }
    }

}
