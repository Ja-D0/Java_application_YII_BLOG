package com.example.blog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MyPostsActivity extends AppCompatActivity {

    private ListView listView;
    private ImageButton button;
    private DBController dbController;
    private int post_item_layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_posts_layout);

        dbController = new DBController(this);
        HttpRequestController.init();
        post_item_layout = R.layout.layout_post;
        listView = findViewById(R.id.myPosts);
        button = findViewById(R.id.createPost);

        fillPostsList();

    }
    private void fillPostsList() {
        ArrayList<Post> listPosts = dbController.selectMyPosts();
        PostAdapter adapter = new PostAdapter(this, post_item_layout, listPosts);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int post_id = ((Post) listPosts.get(position)).getId();
                Log.i("ID", String.valueOf(post_id), new Throwable());
                Intent intent = new Intent(MyPostsActivity.this, PostInformationActivity.class);
                intent.putExtra("post_id", post_id);
                startActivity(intent);
            }
        });
    }
    public void createPost(View view) {
        Intent intent = new Intent(this, PostsDoActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fillPostsList();
    }
}
