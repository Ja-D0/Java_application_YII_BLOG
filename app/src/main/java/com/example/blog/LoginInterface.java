package com.example.blog;

import android.view.View;

import org.json.JSONException;

interface LoginInterface {
    public void Login(String body) throws JSONException;
    public void Login(String login, String password, View v);
}
