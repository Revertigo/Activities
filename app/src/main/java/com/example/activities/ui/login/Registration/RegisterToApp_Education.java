package com.example.activities.ui.login.Registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import com.example.activities.R;
import com.example.activities.ui.login.user.PostUser;


public class RegisterToApp_Education extends AppCompatActivity {

    private Button Next;
    private PostUser postNewUser ;
    private EditText occupationEditText;
    private EditText educationEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_education);
        //Get from last activity the User object of new user:
        postNewUser = getIntent().getParcelableExtra("PostNewUser");

        occupationEditText = findViewById(R.id.occupationEditText);
        educationEditText = findViewById(R.id.educationEditText);
        Next = findViewById(R.id.Next7);


        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String occupation = occupationEditText.getText().toString();
                String education = educationEditText.getText().toString();
                if(occupation.isEmpty()){
                    occupationEditText.setError("Please enter your current occupation");
                    occupationEditText.requestFocus();
                }
                if(education.isEmpty()){
                    educationEditText.setError("Please enter your education");
                    educationEditText.requestFocus();
                }
                else{
                    postNewUser.setOccupation(occupation);
                    postNewUser.setEducation(education);
                    Intent i = new Intent(RegisterToApp_Education.this, RegisterToApp_Education.class);
                    i.putExtra("newUser",postNewUser);//Submit the PostUser object to the next activity
                    startActivity(i);
                }
            }
        });
    }

}
