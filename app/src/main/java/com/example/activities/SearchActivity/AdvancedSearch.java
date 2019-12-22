package com.example.activities.SearchActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class AdvancedSearch extends AppCompatActivity {
private Button clickToSearch;
    private Button backToSearchActivity;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final String path="activities";
    final DatabaseReference ref = database.getReference(path);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

   clickToSearch.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
          final TextView name=findViewById(R.id.nameSearch);
           final  TextView type=findViewById(R.id.typeSearch);
           final TextView difficulty=findViewById(R.id.difficultySearch);
           final TextView gender=findViewById(R.id.genderSearch);
           final  TextView group=findViewById(R.id.groupSearch);
           final  TextView describe=findViewById(R.id.describeSearch);
           final TextView address=findViewById(R.id.addressSearch);
           final  TextView date=findViewById(R.id.dateSearch);
           final  TextView time=findViewById(R.id.timeSearch);

           ref.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   boolean allMatch=true;
                   ArrayList<Activity> activitiesArray = new ArrayList<Activity>();
                   Intent intent = new Intent(AdvancedSearch.this, ShowActivities.class);

                   String name1 = name.getText().toString();
                   if (!name1.isEmpty()) {name1 = name1.toLowerCase();}

                   String type1 = type.getText().toString();
                   if (!type1.isEmpty()) {type1 = type1.toLowerCase();}

                   String difficulty1 = difficulty.getText().toString();
                   if (!difficulty1.isEmpty()){ difficulty1 = difficulty1.toLowerCase();}

                   String gender1 = gender.getText().toString();
                   if (!gender1.isEmpty()) {gender1 = gender1.toLowerCase();}

                   String group1 = group.getText().toString();
                   if (!group1.isEmpty()) {group1 = group1.toLowerCase();}

                   String describe1 = describe.getText().toString();
                   if (!describe1.isEmpty()) {describe1 = describe1.toLowerCase();}

                   String address1 = address.getText().toString();
                   if (!address1.isEmpty()){ address1 = address1.toLowerCase();}

                   String date1 = date.getText().toString();
                   if (!date1.isEmpty()){ date1 = date1.toLowerCase();}

                   String time1 = time.getText().toString();
                   if (!time1.isEmpty()){ time1 = time1.toLowerCase();}

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
                            Log.wtf("group1 is",group1);
                            if(theCurrentActivity.isGroup()){
                                activity[4]="group";
                            }
                            else{
                                activity[4]="single;";
                            }
                               Log.wtf("arcivity[4] is ",activity[4]);
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
                               activity[7]=theCurrentActivity.getDate().toString().toLowerCase();
                           }
                           if(!time1.isEmpty()){
                               activity[8]= theCurrentActivity.getTime().toLowerCase();
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
