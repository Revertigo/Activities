package com.example.activities.data.interfaces;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

/** IpermissionLP interface represent objects with certain permission
 * Allows certain buttons to send to a different content depending on user permission*/
public interface IpermissionLP {
    public Intent loadProfile(AppCompatActivity prevIntent);
}
