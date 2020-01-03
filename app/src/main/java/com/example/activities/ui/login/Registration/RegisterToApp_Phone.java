package com.example.activities.ui.login.Registration;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.activities.R;
import com.example.activities.ui.login.user.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterToApp_Phone extends AppCompatActivity {

    private Button Next;
    private EditText phoneEditText;
    private User newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_phone);
        //Get from last activity the User object of new user:
        newUser = getIntent().getParcelableExtra("newUser");


        Next = findViewById(R.id.nextPhone);
        phoneEditText = findViewById(R.id.editTextPhone);


        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = phoneEditText.getText().toString();
                newUser.setPhone(phone);
                Intent i = new Intent(RegisterToApp_Phone.this, RegisterToApp_Permition.class);
                i.putExtra("newUser", newUser); //Submit the User object to the next activity
                startActivity(i);
            }
        });

    }


}
