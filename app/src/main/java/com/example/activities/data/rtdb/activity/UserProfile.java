package com.example.activities.data.rtdb.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.activities.R;
import com.example.activities.ui.login.user.User;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class UserProfile extends AppCompatActivity {
    private TextView name, email, gender, permission, birthday, phone;
    private Button backToMainMenu;
    private Button showMyActivities;
    private Button activitiesHistory;


    private ImageView EditProfile;
    private ImageView profileImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        final String emailStr = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        name = findViewById(R.id.nameOfTheUserProfile);
        email = findViewById(R.id.emailOfTheUserProfile);
        gender = findViewById(R.id.genderOfTheUserProfile);
        permission = findViewById(R.id.permissionOfTheUserProfile);
        birthday = findViewById(R.id.birthdayOfTheUserProfile);
        phone = findViewById(R.id.PhoneOfTheUserProfile);


        EditProfile = findViewById(R.id.editImageViewUserProfile);
        EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserProfile.this, UserProfileEditable.class);
                startActivity(i);
                finish();
            }

        });
        profileImage = findViewById(R.id.profileImageUser);
        profileImage.setImageResource(R.drawable.ic_person_black_24dp);

        backToMainMenu = findViewById(R.id.backToSearchOrPost);
        backToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = User.getCurrentUser().loadMainMenu(UserProfile.this);
                startActivity(intent);
                finish();
            }
        });

        activitiesHistory = findViewById(R.id.activitiesHistoryPostUser);
        activitiesHistory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = User.getCurrentUser().loadHistory(UserProfile.this);
                startActivity(i);
                finish();
            }
        });

        name.setText(User.getCurrentUser().getFirstName() + " " + User.getCurrentUser().getLastName());
        phone.setText(User.getCurrentUser().getPhone());
        email.setText(emailStr);
        permission.setText(User.getCurrentUser().getPermission());
        birthday.setText(User.getCurrentUser().getDateOfBirth());
        gender.setText(User.getCurrentUser().getGender());
        loadUserPicture();

        showMyActivities = findViewById(R.id.myPostedActivitiesPostUser);
        showMyActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = User.getCurrentUser().loadFuture(UserProfile.this);
                startActivity(in);
                finish();
            }
        });
    }

    private void loadUserPicture()
    {
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
            }
            //uses default image
        }
    }

}
