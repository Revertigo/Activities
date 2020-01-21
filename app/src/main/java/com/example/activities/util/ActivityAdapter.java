package com.example.activities.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.activities.R;
import com.example.activities.data.entities.Activity;

import java.util.ArrayList;

public class ActivityAdapter extends BaseAdapter {
    private ArrayList<Activity> activities = new ArrayList<Activity>();
    private Context context;

    public ActivityAdapter(ArrayList<Activity> activities, Context context) {
        this.activities=activities;
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
        if (convertView==null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.activity_activity_adapter, null);
        }
        ((TextView)convertView.findViewById(R.id.activityNameAdapter)).setText(String.valueOf("Activity name: "+activities.get(position).getName()));
        ((TextView)convertView.findViewById(R.id.activityTimeAdapter)).setText(String.valueOf("Activity time: "+activities.get(position).getTime()));
        ((TextView)convertView.findViewById(R.id.activityDateAdapter)).setText(String.valueOf("Activity date: "+activities.get(position).getDate().getDay()+"/"+activities.get(position).getDate().getMonth()+"/"+activities.get(position).getDate().getYear()));
        ((TextView)convertView.findViewById(R.id.activityTypeAdapter)).setText(String.valueOf("Activity type: "+activities.get(position).getType()));



        return convertView;
    }
}
