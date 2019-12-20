package com.example.activities.ui.login.Registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.activities.R;
import com.example.activities.ui.login.user.User;

public class RegisterToApp_Email extends AppCompatActivity {

    private EditText EmailEditText;
    private Button Next ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration_email);

        EmailEditText = findViewById(R.id.editTextEmail);
        Next = findViewById(R.id.Next1);

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= EmailEditText.getText().toString();
                //TO DO:
                //add constrains on the username
                if(email.isEmpty()){
                    EmailEditText.setError("Please enter username id");
                    EmailEditText.requestFocus();
                }
                else{
                    User newUser = new User(email);
                    Intent i = new Intent(RegisterToApp_Email.this, RegisterToApp_Password.class);
                    i.putExtra("newUser",newUser); //Submit the User object to the next activity
                    startActivity(i);
                }
            }
        });
    }

}
