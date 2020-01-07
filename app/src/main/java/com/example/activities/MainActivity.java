package com.example.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.activities.Util.CsvReader;
import com.example.activities.ui.login.LoginActivity;
import com.example.activities.ui.login.Registration.RegisterToApp_Email;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button LoginToActivities;
    private Button RegisterToActivities;
    private Button closeAppBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //button of the login
        LoginToActivities = findViewById(R.id.button2);
        LoginToActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });

        //button of the Logout
        RegisterToActivities = findViewById(R.id.button10);
        RegisterToActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterActivity();
            }
        });

        //button to close the app
        closeAppBtn = findViewById(R.id.button11);
        closeAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                moveTaskToBack(true);
            }
        });

        //Read all cities/settlements from Realtime DB
        CsvReader.readCitiesAndStreetsFromDB(CsvReader.STREETS, CsvReader.STREETS);
        //Read all relevant data from Realtime DB(cities and settlements, streets)
        CsvReader.readCitiesFromDB(CsvReader.CITIES_AND_SETTLEMENTS_HEBREW, CsvReader.CITIES_AND_SETTLEMENTS_HEBREW);
    }

    public void openLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void openRegisterActivity() {
        Intent intent = new Intent(MainActivity.this, RegisterToApp_Email.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
