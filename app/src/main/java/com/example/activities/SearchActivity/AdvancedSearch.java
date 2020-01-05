package com.example.activities.SearchActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.activities.R;
import com.example.activities.data.rtdb.activity.Activity;
import com.example.activities.data.rtdb.activity.ShowActivities;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class AdvancedSearch extends AppCompatActivity {
    private Button clickToSearch;
    private Button dateButton;
    private Button timeButton;
    private Button backToSearchActivity;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final String path="activities";
    final DatabaseReference ref = database.getReference(path);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dateButton=findViewById(R.id.enterDateAdvanced);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search);
        clickToSearch=findViewById(R.id.clickToUseAdvancedSearch);
        backToSearchActivity=findViewById(R.id.clickToBackToMainSearchActivity);

        backToSearchActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdvancedSearch.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        //spinner info slots
        String[] types = new String[]{
                "Type", "Sport", "Food", "Online gaming", "Art", "Learning", "Other"
        };
        //type spinner
        final Spinner typeSpinnerAdvanced = findViewById(R.id.typeSpinnerAdvanced);
        ArrayAdapter<String> adapterForTypeSpinnerAdvanced = new ArrayAdapter<String>(AdvancedSearch.this,android.R.layout.simple_spinner_item, types);
        adapterForTypeSpinnerAdvanced.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinnerAdvanced.setAdapter(adapterForTypeSpinnerAdvanced);


        //difficulty
        String[] difficulty = new String[]{
                "Difficulty", "Beginner", "Advanced", "Professional"
        };
        //difficulty spinner
        final Spinner difficultySpinnerAdvanced = findViewById(R.id.difficultySpinnerAdvanced);
        ArrayAdapter<String> adapterFordifficultySpinnerAdvanced= new ArrayAdapter<String>(AdvancedSearch.this,
                android.R.layout.simple_spinner_item, difficulty);
        adapterFordifficultySpinnerAdvanced.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinnerAdvanced.setAdapter(adapterFordifficultySpinnerAdvanced);


        //time
        timeButton=findViewById(R.id.addTimeButtonAdvanced);
        final  TextView time=findViewById(R.id.timeSearchAdvanced);
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AdvancedSearch.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String a = "" + selectedMinute;
                        String b = "" + selectedHour;
                        if(selectedMinute<10){
                            a = "0"+selectedMinute;
                        }
                        if(selectedHour<10){
                            b = "0"+selectedHour;
                        }
                        time.setText( b + ":" + a);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        //date
        final  TextView  date=findViewById(R.id.dateSearchAdvanced) ;
        dateButton=findViewById(R.id.enterDateAdvanced);
        dateButton.setOnClickListener(new View.OnClickListener() {
            DatePickerDialog datePickerDialog;
            int year;
            int month;
            int dayOfMonth;
            Calendar calendar;
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(AdvancedSearch.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                date.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
        //name
        final TextView name=findViewById(R.id.nameSearch);


        //gender
        final RadioGroup rgGender=findViewById(R.id.rgGenderAdvanced);
        //rgGender.getCheckedRadioButtonId()==-1)

        //group
        final RadioGroup rgActivityFor=findViewById(R.id.rgGroupSingle);


        final  TextView describe=findViewById(R.id.describeAdvanced);
        final TextView address=findViewById(R.id.addressSearch);



        clickToSearch.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {

           ref.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   boolean allMatch=true;
                   ArrayList<Activity> activitiesArray = new ArrayList<Activity>();
                   ShowActivities.activityFilter=true;
                   Intent intent = new Intent(AdvancedSearch.this, ShowActivities.class);

                   String name1 = name.getText().toString();
                   if (!name1.isEmpty()) {name1 = name1.toLowerCase();}

                   String type1 = typeSpinnerAdvanced.getSelectedItem().toString();
                   if (type1.toLowerCase().equals("type")) {type1 = "";}



                   String difficulty1 = difficultySpinnerAdvanced.getSelectedItem().toString();
                   if (difficulty1.toLowerCase().equals("difficulty")){ difficulty1 = "";}



                   String gender1="";
                   if(!(rgGender.getCheckedRadioButtonId()==-1)){gender1= ((RadioButton)findViewById(rgGender.getCheckedRadioButtonId())).getText().toString();}

                   if (!gender1.isEmpty()) {gender1 = gender1.toLowerCase();}

                   String group1 ="";
                   if(!(rgActivityFor.getCheckedRadioButtonId()==-1)){group1=((RadioButton) findViewById(rgActivityFor.getCheckedRadioButtonId())).getText().toString();}
                   if (!group1.isEmpty()) {group1 = group1.toLowerCase();}

                   String describe1 = describe.getText().toString();
                   if (!describe1.isEmpty()) {describe1 = describe1.toLowerCase();}

                   String address1 = address.getText().toString();
                   if (!address1.isEmpty()){ address1 = address1.toLowerCase();}

                   String date1 = date.getText().toString();
                   if (!date1.isEmpty()){
                       String[] dateSplit=date1.split("/");
                       try{
                           if(dateSplit.length!=3){date1="wrong enter date(dd/MM/yyyy)";}
                           else{
                           int a=Integer.parseInt(dateSplit[0]);
                           a=Integer.parseInt(dateSplit[1]);
                           a=Integer.parseInt(dateSplit[2]);
                           }
                       }
                       catch (Exception e){date1="wrong enter date(dd/MM/yyyy)";}
                   }



                   String time1 = time.getText().toString();
                   if (!time1.isEmpty()){
                       String[] timeSplit=time1.split(":");
                       try{
                           if(timeSplit.length!=2){time1="wrong enter time(hh:mm)";}
                           else{
                               int a=Integer.parseInt(timeSplit[0]);
                               a=Integer.parseInt(timeSplit[1]);
                           }
                       }
                       catch (Exception e){time1="wrong enter time(hh:mm)";}
                   }

                    String[] array={name1,type1,difficulty1,gender1,group1,describe1,address1,date1,time1};

                       for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Activity theCurrentActivity=ds.getValue(Activity.class);
                           String[] activity=new String[9];
                           //activity name
                           if(!name1.isEmpty()){
                           activity[0]=theCurrentActivity.getName().toLowerCase();}
                           //activity type
                                   if(!type1.isEmpty()){
                                   activity[1]=theCurrentActivity.getType().toLowerCase();
                                   }
                           //activity difficulty
                           if(!difficulty1.isEmpty()){
                               activity[2]= theCurrentActivity.getDifficulty().toLowerCase();
                           }
                           //activity Gender
                           if(!gender1.isEmpty()){
                               activity[3]=theCurrentActivity.getGender().toLowerCase();
                           }
                           //activity group(yes or not)
                           if(!group1.isEmpty()){
                            if(theCurrentActivity.isGroup()){
                                activity[4]="group";
                            }
                            else{
                                activity[4]="single;";
                            }
                             // activity[4]= String.valueOf(theCurrentActivity.isGroup()).toLowerCase();
                           }
                           //describe activity
                           if(!describe1.isEmpty()){
                               activity[5]=  theCurrentActivity.getDescription().toLowerCase();}

                                   //activity adress
                                   if(!address1.isEmpty()){
                                   activity[6]=theCurrentActivity.getAddr().getCity_set().toLowerCase()+
                                           theCurrentActivity.getAddr().getStreet().toLowerCase();
                                   }

                           //date of the activity
                           if(!date1.isEmpty()) {
                               activity[7]=theCurrentActivity.getDate().getDay()+
                                       "/"+theCurrentActivity.getDate().getMonth()+"/"+theCurrentActivity.getDate().getYear();
                           }
                           if(!time1.isEmpty()){
                               activity[8]= theCurrentActivity.getTime();
                           }
                               for(int i=0;i<array.length;i++){
                                   if(!array[i].isEmpty()){
                                       if(activity[i].isEmpty()){allMatch=false; break;}
                                       else{
                                           if(i==3){if (!(activity[3].equals(array[3]))){allMatch=false; break;
                                                }
                                           }
                                           else {
                                               if (!(activity[i].contains(array[i]))) {
                                                   allMatch = false;
                                               }
                                           }
                                       }

                                   }
                               }
                               if(allMatch==true){
                                   activitiesArray.add(ds.getValue(Activity.class));
                               }
                               allMatch=true;
                    // check if all the not null are all contains   activitiesArray.add(ds.getValue(Activity.class));

                       }
                       if (activitiesArray.size() == 0) {
                           Toast.makeText(getApplicationContext(), "Sorry, there wasn't match to your search. ", Toast.LENGTH_SHORT).show();
                       }
                       else {
                           intent.putExtra("activitiesArray", activitiesArray);
                           startActivity(intent);
                       }
               }//end on data change

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });//end on cancelled
       }
   });//end advanced click listener

    }//end on create
}//end class
