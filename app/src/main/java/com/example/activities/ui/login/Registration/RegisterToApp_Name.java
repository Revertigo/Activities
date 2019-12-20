package com.example.activities.ui.login.Registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.activities.R;
import com.example.activities.ui.login.user.User;


public class RegisterToApp_Name extends AppCompatActivity {

    private EditText nameEditText;
    private Button Next;
    private User newUser ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_name);
        //Get from last activity the User object of new user:
        newUser = getIntent().getParcelableExtra("newUser");

        nameEditText = findViewById(R.id.yourName1);
        Next = findViewById(R.id.Next3);

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name= nameEditText.getText().toString();
                // TO DO:
                // add constrains on the Name/to parse it
                if(name.isEmpty()){
                    nameEditText.setError("Please enter your name");
                    nameEditText.requestFocus();
                }
                else{
                    newUser.setName(name);
                    Intent i = new Intent(RegisterToApp_Name.this, RegisterToApp_Gender.class);
                    i.putExtra("newUser",newUser);//Submit the User object to the next activity
                    startActivity(i);
                }
            }
        });
    }


}
