package com.example.activities.ui.login.Registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import com.example.activities.R;
import com.example.activities.ui.login.user.User;


public class RegisterToApp_Gender extends AppCompatActivity {

    private Button Next;
    private String gender="gender";
    private User newUser ;

    private RadioButton lastRadioBtn;
    private RadioGroup group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_gender);
        //Get from last activity the User object of new user:
        newUser = getIntent().getParcelableExtra("newUser");

        lastRadioBtn = findViewById(R.id.radioButtonWoman);
        group = findViewById(R.id.RadioGroupGender);
        Next = findViewById(R.id.Next4);

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If nothing is selected:
                if(group.getCheckedRadioButtonId()== -1){
                    lastRadioBtn.setError("Select Item");//Set error to last Radio button
                }
                else{
                    newUser.setGender(gender);
                    Intent i = new Intent(RegisterToApp_Gender.this, RegisterToApp_DateOfBirth.class);
                    i.putExtra("newUser",newUser); //Submit the object to the next activity
                    startActivity(i);
                }
            }
        });
    }

    //A method connected to the radio buttons group
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();// Is the button now checked?

        // Check which radio button was clicked:
        switch(view.getId()) {
            case R.id.radioButtonMan:
                if (checked){
                    this.gender = "Man";
                    break;}
            case R.id.radioButtonWoman:
                if (checked){
                    this.gender = "Woman";
                    break;}
        }
    }


}
