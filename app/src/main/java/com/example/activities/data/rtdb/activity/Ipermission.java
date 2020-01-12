package com.example.activities.data.rtdb.activity;

import android.content.Intent;

import com.example.activities.MainActivity;
import com.example.activities.ui.login.LoginActivity;

import androidx.appcompat.app.AppCompatActivity;

/** Ipermission interface represent objects with certain permission
 * Allows certain buttons to send to a different content depending on user permission*/
public interface Ipermission {
    public Intent loadMainMenu(AppCompatActivity la);//Main Activity
    public Intent loadProfile(AppCompatActivity ma);
    public Intent loadHistory(AppCompatActivity la);
    public Intent loadFuture(AppCompatActivity la);
}
