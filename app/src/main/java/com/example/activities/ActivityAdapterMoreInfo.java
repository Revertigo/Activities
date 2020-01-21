package com.example.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.activities.data.entities.Activity;

import java.util.ArrayList;

public class ActivityAdapterMoreInfo extends BaseAdapter {
    private ArrayList<Activity> activities = new ArrayList<Activity>();
    private Context context;

    public ActivityAdapterMoreInfo(ArrayList<Activity> activities, Context context) {
        this.activities = activities;
        this.context = context;

    }

    @Override
    public int getCount() {
        return activities.size();
    }

    @Override
    public Object getItem(int position) {
        return activities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.activity_adapter_more_info, null);
        }
        ((TextView) convertView.findViewById(R.id.activityIdAdapterMoreInfo)).setText(String.valueOf("Activity id: " + activities.get(position).getId()));
        ((TextView) convertView.findViewById(R.id.activityNameAdapterMoreInfo)).setText(String.valueOf("Activity name: " + activities.get(position).getName()));
        ((TextView) convertView.findViewById(R.id.activityCreatorAdapterMoreInfo)).setText(String.valueOf("Activity Creator: " + activities.get(position).getpostedUser()));
        ((TextView) convertView.findViewById(R.id.activityGenderAdapterMoreInfo)).setText(String.valueOf("Activity gender: " + activities.get(position).getGender()));
        ((TextView) convertView.findViewById(R.id.activityDifficultyAdapterMoreInfo)).setText(String.valueOf("Activity difficulty: " + activities.get(position).getDifficulty()));
        ((TextView) convertView.findViewById(R.id.activitySingleGroupAdapterMoreInfo)).setText(String.valueOf("Activity group: " + activities.get(position).isGroup()));
        ((TextView) convertView.findViewById(R.id.activityTimeAdapterMoreInfo)).setText(String.valueOf("Activity time: " + activities.get(position).getTime()));
        ((TextView) convertView.findViewById(R.id.activityDateAdapterMoreInfo)).setText(String.valueOf("Activity date: " + activities.get(position).getDate().getDay() + "/" + activities.get(position).getDate().getMonth() + "/" + activities.get(position).getDate().getYear()));
        ((TextView) convertView.findViewById(R.id.activityAdressAdapterMoreInfo)).setText(String.valueOf("Activity address: " + activities.get(position).getAddr().getCity_set() + ", " + activities.get(position).getAddr().getStreet() + ", " + activities.get(position).getAddr().getApartment_number()));
        ((TextView) convertView.findViewById(R.id.activityDescriptionAdapterMoreInfo)).setText(String.valueOf("Activity description: " + activities.get(position).getDescription()));
        ((TextView) convertView.findViewById(R.id.activityTypeAdapterMoreInfo)).setText(String.valueOf("Activity type: " + activities.get(position).getType()));
        return convertView;
    }
}
