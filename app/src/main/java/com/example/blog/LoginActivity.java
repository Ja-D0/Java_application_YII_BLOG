package com.example.blog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements LoginInterface {

    private TextView login;
    private Button enter;
    private String loginOrEmail, password;
    private EditText loginField, passwordField;
    private final String testLogin = "local";
    private final String testPassword = "local";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        HttpRequestController.init();
        login = findViewById(R.id.loginTitle);
        login.setText("Вход");
        enter = findViewById(R.id.enter);
        loginField = findViewById(R.id.loginOrEmail);
        passwordField = findViewById(R.id.password);

        login();
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginOrEmail = loginField.getText().toString();
                password = passwordField.getText().toString();
                if (checkLoginFields(v))
                    HttpRequestController.check_user(loginOrEmail, password, LoginActivity.this, v);
            }
        });
    }

    private boolean checkLoginFields(View view){
        if (Objects.equals(loginOrEmail, "") || Objects.equals(password, "")) {
            Snackbar.make(view, "Введите логин и пароль", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void Login(String body) throws JSONException {
        try {
            JSONObject jsonUser = new JSONObject(body);
            User.setEmail(jsonUser.getString("email"));
            User.setUsername(jsonUser.getString("username"));
            User.setNickname(jsonUser.getString("nickname"));
            User.setCreated_at(jsonUser.getString("created_at"));
            User.setUpdated_at(jsonUser.getString("updated_at"));
            login();

        } catch (JSONException e) {
            Log.e("JSON_LOGIN", "BODY", e);
        }
    }
    @Override
    public void Login(String login, String password, View v){
        if (Objects.equals(login, testLogin) && Objects.equals(password, testPassword)){
            User.setEmail("email");
            User.setNickname(login);
            User.setUsername(login);
            User.setCreated_at("00.00.0000");
            User.setUpdated_at("00.00.0000");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        Snackbar.make(v, "Неправильный логин или пароль", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        login();
    }

    private void login(){
        if (User.getUsername() != null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
