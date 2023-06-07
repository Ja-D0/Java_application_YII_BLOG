package com.example.blog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PostInformationActivity extends AppCompatActivity {
    private TextView title, author, description;
    private Intent intent;
    private int post_id;
    private Button update, delete;
    private DBController dbController;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_information_activity);

        title = findViewById(R.id.postInformationTitle);
        description = findViewById(R.id.postInformationDescription);
        author = findViewById(R.id.postInformationAuthor);

        dbController = new DBController(this);

        intent = getIntent();
        post_id = intent.getIntExtra("post_id", 0);

        fillPostInformation(post_id);

        update = findViewById(R.id.postInformationUpdateButton);
        delete = findViewById(R.id.postInformationDeleteButton);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent update = new Intent(PostInformationActivity.this, MyPostsActivity.class);
                update.putExtra("post_id", post_id);
                startActivity(update);
                finish();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post post = dbController.select(post_id);
                dbController.delete(post_id);
                HttpRequestController.delete_post(post);
                finish();
            }
        });

    }
    private void fillPostInformation(int post_id){
        Post post = dbController.select(post_id);
        title.setText(post.getTitle());
        description.setText(post.getDescription());
        author.setText(post.getAuthor());
    }
}
