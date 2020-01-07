package com.example.activities.data.rtdb.activity;

import android.content.Intent;

import com.example.activities.MainActivity;
import com.example.activities.ui.login.LoginActivity;

public interface Ipermission {
    public Intent loadMainMenu(LoginActivity la);//Main Activity
    public Intent loadProfile(MainActivity ma);
}
