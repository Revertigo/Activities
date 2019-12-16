package com.example.activities.ui.login.Registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.activities.R;
import com.example.activities.ui.login.user.User;

public class RegisterToApp_Password extends AppCompatActivity {

    private EditText passwordEditText;
    private Button Next;
    private User newUser ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_password);
        //Get from last activity the User object of new user:
        newUser = getIntent().getParcelableExtra("newUser");

        passwordEditText = findViewById(R.id.editTextPassword);
        Next = findViewById(R.id.nextPassword);

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password= passwordEditText.getText().toString();
                // TO DO:
                // add constrains on the password
                if(password.isEmpty()){
                    passwordEditText.setError("Please enter correct password");
                    passwordEditText.requestFocus();
                }
                else{
                    newUser.setPassword(password);
                    Intent i = new Intent(RegisterToApp_Password.this, RegisterToApp_Name.class);
                    i.putExtra("newUser",newUser);//Submit the User object to the next activity
                    startActivity(i);

                }
            }
        });
    }


}
