package com.example.blog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView profileTitle, nickname, email, updated_at, created_at;
    private Button myPosts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        profileTitle = findViewById(R.id.profileTitle);
        nickname = findViewById(R.id.nickname);
        email = findViewById(R.id.email);
        updated_at = findViewById(R.id.profileUpdated_at);
        created_at = findViewById(R.id.profileCreated_at);
        myPosts = findViewById(R.id.myPosts);

        myPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, MyPostsActivity.class);
                startActivity(intent);
            }
        });

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
