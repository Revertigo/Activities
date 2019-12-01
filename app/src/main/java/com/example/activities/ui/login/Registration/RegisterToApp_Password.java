package com.example.activities.ui.login.Registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.activities.R;
import com.example.activities.ui.login.Users.User;

public class RegisterToApp_Password extends AppCompatActivity {
    private EditText passwordEditText;
    private Button Next;
    private User newUser = getIntent().getParcelableExtra("newUser");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_password);

//        mAuth=FirebaseAuth.getInstance();

        passwordEditText = findViewById(R.id.editTextPassword);

        Next = findViewById(R.id.Next2);

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
                    Intent i = new Intent(RegisterToApp_Password.this, RegisterToApp_Password.class);
                    i.putExtra("newUser",newUser);
                    startActivity(i);
                }
            }
        });
    }


}
