package com.example.blog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class PostsDoActivity extends AppCompatActivity {

    private EditText title, description, create, update;
    private Button createOrUpdate;
    private Intent intent;
    private int post_id;
    private DBController dbController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_post);

        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        createOrUpdate = findViewById(R.id.create);
        dbController = new DBController(this);

        intent = getIntent();
        post_id = intent.getIntExtra("post_id", -1);

        setValuesToForm(post_id);

        createOrUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                sdf.setTimeZone(TimeZone.getTimeZone("GMT+3"));
                String currentTime = sdf.format(new Date());
                if (post_id < 0) {
                    Post post = new Post(title.getText().toString(), description.getText().toString(), User.getNickname(),currentTime, currentTime);
                    dbController.insert(post);
                    HttpRequestController.create_post(post);
                    finish();
                } else {
                    Post post = dbController.select(post_id);
                    post.setAuthor(User.getNickname());
                    post.setTitle(title.getText().toString());
                    post.setDescription(description.getText().toString());
                    post.setUpdated_at(currentTime);
                    dbController.update(post);
                    HttpRequestController.update_post(post);
                    finish();
                }
            }
        });
    }

    private void setValuesToForm(int post_id) {
        if (post_id >= 0) {
            Post post = dbController.select(post_id);
            title.setText(post.getTitle());
            description.setText(post.getDescription());
        }
    }
}
