package com.example.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.activities.SearchActivity.SearchActivity;

import org.w3c.dom.Text;

public class AdvancedSearch extends AppCompatActivity {
private Button clickToSearch;
    private Button backToSearchActivity;
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
       TextView name=findViewById(R.id.nameSearch);
       TextView type=findViewById(R.id.typeSearch);
       TextView difficulty=findViewById(R.id.difficultySearch);
       TextView gender=findViewById(R.id.genderSearch);
       TextView group=findViewById(R.id.groupSearch);
       TextView describe=findViewById(R.id.describeSearch);
       TextView address=findViewById(R.id.addressSearch);
       TextView date=findViewById(R.id.dateSearch);
       TextView time=findViewById(R.id.timeSearch);
       @Override
       public void onClick(View v) {

       }
   });

    }
}
