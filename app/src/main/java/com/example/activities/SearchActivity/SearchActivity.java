package com.example.activities.SearchActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.SearchView;
import android.widget.Toast;

import com.example.activities.MainActivity;
import com.example.activities.R;
import com.example.activities.data.rtdb.activity.Activity;
import com.example.activities.data.rtdb.activity.ShowActivities;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class SearchActivity extends AppCompatActivity {

    private Button buttonLogout;
    private Button closeAppFromSearch;
    private Button showAllActivities;
    private SearchView searchByIDSearchView;
    private SearchView searchByStringSearchView;
    private Button searchById;
    private Button searchByString;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        showAllActivities=findViewById(R.id.showAllTheActivitiesButton);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final String path="activities";
        final DatabaseReference ref = database.getReference(path);

        searchByString =findViewById(R.id.advancedSearchButton);
        //Search by activity name/description
        searchByString.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchByString(ref);
            }
        });


        showAllActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Intent intent=new Intent(SearchActivity.this, ShowActivities.class);

                        //get current activity from database
                        ArrayList<Activity> activitiesArray=new ArrayList<Activity>();
                        for(DataSnapshot ds: dataSnapshot.getChildren()){
                           activitiesArray.add(ds.getValue(Activity.class));
                        }
                        intent.putExtra("activitiesArray",activitiesArray);

                        startActivity(intent);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }

        });

        //search by activity's id
        searchById=findViewById(R.id.searchByIdButton);
        searchById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchByID(ref);
            }
        });

        searchByIDSearchView = findViewById(R.id.searchByIdSearchView);
        //Search via soft keyboard key
        searchByIDSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchByID(ref);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //searchByID(ref);
                return false;
            }
        });

        searchByStringSearchView = findViewById(R.id.stringSearchView);
        //Search via soft keyboard key
        searchByStringSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchByString(ref);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        //close app button
        closeAppFromSearch = findViewById(R.id.closeAppSearchActivity);
        closeAppFromSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                moveTaskToBack(true);
            }
        });

        //log out button
        buttonLogout = findViewById(R.id.logoutSearchActivity);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInstance().signOut();
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void searchByID(DatabaseReference ref)
    {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Intent intent=new Intent(SearchActivity.this, ShowActivities.class);
                String id;
                SearchView enterId=findViewById(R.id.searchByIdSearchView);
                id=enterId.getQuery().toString();
                ArrayList<Activity> activitiesArray=new ArrayList<Activity>();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    if(Long.toString(ds.getValue(Activity.class).getId()).equals(id)){
                        activitiesArray.add(ds.getValue(Activity.class));
                        break;
                    }

                }
                if(activitiesArray.size()== 1){
                    //send the string array to the next activity
                    intent.putExtra("activitiesArray", activitiesArray);
                    startActivity(intent);}
                else{
                    Toast.makeText(getApplicationContext(),"The id isn't exist, try again",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void searchByString(DatabaseReference ref)
    {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Activity> activitiesArray = new ArrayList<Activity>();
                Intent intent = new Intent(SearchActivity.this, ShowActivities.class);
                SearchView searchView = findViewById(R.id.stringSearchView);
                String query = searchView.getQuery().toString();
                if (!query.isEmpty()) {
                    query=query.toLowerCase();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        //name
                        if (ds.getValue(Activity.class).getName().toLowerCase().contains(query)) {
                            activitiesArray.add(ds.getValue(Activity.class));
                        }
                        //description
                        else if (ds.getValue(Activity.class).getDescription().toLowerCase().contains(query)) {
                            activitiesArray.add(ds.getValue(Activity.class));
                        }
                    }
                    if (activitiesArray.size() == 0) {
                        Toast.makeText(getApplicationContext(), "Sorry, there wasn't match to your search. ", Toast.LENGTH_SHORT).show();
                    } else {
                        intent.putExtra("activitiesArray", activitiesArray);
                        startActivity(intent);
                    }
                }
                else { Toast.makeText(getApplicationContext(), "What do you want to search?", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}