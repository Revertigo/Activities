package com.example.activities.data.interfaces;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

/** Ipermission interface represent objects with certain permission
 * Allows certain buttons to send to a different content depending on user permission*/
public interface Ipermission {
    public Intent loadMainMenu(AppCompatActivity la);//Main Activity
    public Intent loadProfile(AppCompatActivity ma);
    public Intent loadHistory(AppCompatActivity la);
    public Intent loadFuture(AppCompatActivity la);
}
