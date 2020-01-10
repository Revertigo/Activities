package com.example.activities.data.rtdb.activity;

import android.content.Intent;

import com.example.activities.MainActivity;
import com.example.activities.ui.login.LoginActivity;

import androidx.appcompat.app.AppCompatActivity;

public interface Ipermission {
    public Intent loadMainMenu(AppCompatActivity la);//Main Activity
    public Intent loadProfile(MainActivity ma);
    public Intent loadHistory(AppCompatActivity la);
    public Intent loadFuture(AppCompatActivity la);
}
