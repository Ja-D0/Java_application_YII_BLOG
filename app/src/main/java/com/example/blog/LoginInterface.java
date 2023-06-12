package com.example.blog;

import android.view.View;

import org.json.JSONException;

interface LoginInterface {
    public void Login(String body, View v) throws JSONException;
}
