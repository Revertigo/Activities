package com.example.activities.ui.registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.activities.R;
import com.example.activities.data.entities.user.User;


public class RegisterToApp_Name extends AppCompatActivity {

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private Button Next;
    private User newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_name);
        //Get from last activity the User object of new user:
        newUser = getIntent().getParcelableExtra("newUser");

        firstNameEditText = findViewById(R.id.firstNameEdit);
        lastNameEditText = findViewById(R.id.lastNameEdit);
        Next = findViewById(R.id.Next3);

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                // TO DO:
                // add constrains on the Name/to parse it
                if (firstName.isEmpty()) {
                    firstNameEditText.setError("Please enter your first name");
                    firstNameEditText.requestFocus();
                } else if (lastName.isEmpty()) {
                    lastNameEditText.setError("Please enter your last name");
                    lastNameEditText.requestFocus();
                } else {
                    newUser.setFirstName(firstName);
                    newUser.setLastName(lastName);
                    Intent i = new Intent(RegisterToApp_Name.this, RegisterToApp_Gender.class);
                    i.putExtra("newUser", newUser);//Submit the User object to the next activity
                    startActivity(i);
                    finish();
                }
            }
        });
    }


}