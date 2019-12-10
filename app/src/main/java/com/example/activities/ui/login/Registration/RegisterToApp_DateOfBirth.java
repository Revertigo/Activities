package com.example.activities.ui.login.Registration;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.activities.R;
import com.example.activities.ui.login.user.User;


import java.util.Calendar;

public class RegisterToApp_DateOfBirth extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TextView dateView;
    private Button setDateButton;
    private Button Next;
    private String date;
    private User newUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_dateofbirth);
        //Get from last activity the User object of new user:
        newUser = getIntent().getParcelableExtra("newUser");

        dateView = findViewById(R.id.dateSelectedTextView);
        setDateButton = findViewById((R.id.buttonSetDate));
        Next = findViewById(R.id.Next5);

        setDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        RegisterToApp_DateOfBirth.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                date = month + "/" + day + "/" + year;
                dateView.setText(date);
            }
        };

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    newUser.setDateOfBirth(date);
                    Intent i = new Intent(RegisterToApp_DateOfBirth.this, RegisterToApp_Permition.class);
                    i.putExtra("newUser",newUser); //Submit the User object to the next activity
                    startActivity(i);
            }
        });
    }

}

