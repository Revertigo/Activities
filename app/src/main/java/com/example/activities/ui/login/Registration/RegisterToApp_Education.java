package com.example.activities.ui.login.Registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.activities.MainActivity;
import com.example.activities.R;
import com.example.activities.ui.login.user.PostUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterToApp_Education extends AppCompatActivity {

    private Button Next;
    private PostUser postNewUser ;
    private EditText occupationEditText;
    private EditText educationEditText;
    private FirebaseAuth mAuth;
    private DatabaseReference users_ref;
    private String post_users="Users/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_education);
        //Get from last activity the User object of new user:
        postNewUser = getIntent().getParcelableExtra("postNewUser");

        occupationEditText = findViewById(R.id.occupationEditText);
        educationEditText = findViewById(R.id.educationEditText);
        Next = findViewById(R.id.finishRegistratiorButton);


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
                    //Create new postUser:
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(postNewUser.getUsername(), postNewUser.getPassword())
                            .addOnCompleteListener(RegisterToApp_Education.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        //Todo create text views and take info and insert into post user
                                        //send post user to database
                                        postNewUser.setEducation(educationEditText.getText().toString());
                                        postNewUser.setOccupation(occupationEditText.getText().toString());
                                        users_ref = FirebaseDatabase.getInstance().getReference(post_users);
                                        String[] emailSplit=postNewUser.getUsername().split("@");
                                        users_ref.child(emailSplit[0]).setValue(postNewUser);
                                        Intent i=new Intent(RegisterToApp_Education.this, MainActivity.class);
                                        startActivity(i);
                                    }
                                    else {
                                       Toast.makeText(getApplicationContext(),
                                                "enter education,occapution faild try again",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

}
