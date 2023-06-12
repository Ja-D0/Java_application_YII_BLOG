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

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private TextView login;
    private Button enter;
    private String loginOrEmail, password;
    private EditText loginField, passwordField;
    private final String testLogin = "admin";
    private final String testPassword = "admin";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        login = findViewById(R.id.loginTitle);
        login.setText("Вход");
        enter = findViewById(R.id.enter);
        loginField = findViewById(R.id.loginOrEmail);
        passwordField = findViewById(R.id.password);

        if (User.getUsername() != null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginOrEmail = loginField.getText().toString();
                password = passwordField.getText().toString();
                if (login(v, loginOrEmail, password)){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private boolean login(View view, String loginOrEmail, String password){
        if (Objects.equals(loginOrEmail, "") || Objects.equals(password, "")){
            Snackbar.make(view, "Введите логин и пароль", Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (!Objects.equals(loginOrEmail, testLogin) || !Objects.equals(password, testPassword)) {
            Snackbar.make(view, "Неправильный логин или пароль", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        User.setUsername(loginOrEmail);
        return true;
    }
}
