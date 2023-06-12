package com.example.blog;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView profileTitle, nickname, email, updated_at, created_at;
    private Button update, delete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        profileTitle = findViewById(R.id.profileTitle);
        nickname = findViewById(R.id.nickname);
        email = findViewById(R.id.email);
        updated_at = findViewById(R.id.profileUpdated_at);
        created_at = findViewById(R.id.profileCreated_at);
        update = findViewById(R.id.profileInformationUpdateButton);
        delete = findViewById(R.id.profileInformationDeleteButton);


        fillProfileInformation();
    }
    private void fillProfileInformation(){
        profileTitle.setText(User.getUsername());
        nickname.setText(User.getNickname());
        email.setText(User.getEmail());
        updated_at.setText(User.getUpdated_at());
        created_at.setText(User.getCreated_at());
    }
}
