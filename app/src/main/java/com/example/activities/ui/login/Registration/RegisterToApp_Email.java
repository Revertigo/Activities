package com.example.activities.ui.login.Registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.activities.R;
import com.example.activities.data.rtdb.user.User;

public class RegisterToApp_Email extends AppCompatActivity {

    private EditText EmailEditText;
    private Button Next;

    //  private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration_email);

//        mAuth=FirebaseAuth.getInstance();

        EmailEditText = findViewById(R.id.editTextEmail);

        Next = findViewById(R.id.Next1);

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= EmailEditText.getText().toString();
                //String pass=passwordEditText.getText().toString();
                if(email.isEmpty()){
                    EmailEditText.setError("Please enter email id");
                    EmailEditText.requestFocus();
                }
                else{
                    User newUser = new User(email);
                    Intent i = new Intent(RegisterToApp_Email.this, RegisterToApp_Password.class);
                    i.putExtra("newUser",newUser);
                    startActivity(i);
                }
            }
        });
    }

}
