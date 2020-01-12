package com.example.activities.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.activities.R;
import com.example.activities.data.entities.user.PostUser;
import com.example.activities.data.entities.user.User;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class PostUserProfile extends AppCompatActivity {
    private TextView name, email, occupation, education, gender, permission, birthday, phone;
    private Button backToMainMenu;
    private Button showMyActivities;
    private Button activitiesHistory;


    private ImageView editProfile;
    private ImageView profileImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_user_profile);

        final String emailStr = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        name = findViewById(R.id.nameOfThePostUserProfile);
        email = findViewById(R.id.emailOfThePostUserProfile);
        occupation = findViewById(R.id.occupationOfThePostUserProfile);
        education = findViewById(R.id.educationOfThePostUserProfile);
        gender = findViewById(R.id.genderOfThePostUserProfile);
        permission = findViewById(R.id.permissionOfThePostUserProfile);
        birthday = findViewById(R.id.birthdayOfThePostUserProfile);
        phone = findViewById(R.id.PhoneOfThePostUserProfile);


        editProfile = findViewById(R.id.editImageViewPostUserProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PostUserProfile.this, PostUserProfileEditable.class);
                startActivity(i);
                finish();
            }

        });
        profileImage = findViewById(R.id.profileImagePostUser);
        profileImage.setImageResource(R.drawable.ic_person_black_24dp);

        backToMainMenu = findViewById(R.id.backToSearchOrPost);
        backToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = User.getCurrentUser().loadMainMenu(PostUserProfile.this);
                startActivity(intent);
                finish();
            }
        });

        activitiesHistory = findViewById(R.id.activitiesHistoryPostUser);
        activitiesHistory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = User.getCurrentUser().loadHistory(PostUserProfile.this);
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
        occupation.setText(((PostUser) User.getCurrentUser()).getOccupation());
        education.setText(((PostUser) User.getCurrentUser()).getEducation());
        loadUserPicture();

        showMyActivities = findViewById(R.id.myPostedActivitiesPostUser);
        showMyActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = User.getCurrentUser().loadFuture(PostUserProfile.this);
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
