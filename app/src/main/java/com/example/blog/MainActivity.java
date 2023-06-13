package com.example.blog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private TextView textView;
    private ImageButton imageButtonMenu;
    private ImageButton button;
    private DBController dbController;
    private int post_item_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbController = new DBController(this);
        HttpRequestController.init();
        post_item_layout = R.layout.layout_post;
        listView = findViewById(R.id.allPosts);
        textView = findViewById(R.id.title);
        imageButtonMenu = findViewById(R.id.imageButtonMenu);
        button = findViewById(R.id.createPost);

        createMenu();
        addPosts_toBD(HttpRequestController.get_posts());
        fillPostsList();
    }

    private void addPosts_toBD(ArrayList<Post> postsList) {
        dbController.insert(postsList);
    }

    private void fillPostsList() {
        ArrayList<Post> listPosts = dbController.selectAll();
        PostAdapter adapter = new PostAdapter(this, post_item_layout, listPosts);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int post_id = ((Post) listPosts.get(position)).getId();
                Log.i("ID", String.valueOf(post_id), new Throwable());
                Intent intent = new Intent(MainActivity.this, PostInformationActivity.class);
                intent.putExtra("post_id", post_id);
                startActivity(intent);
            }
        });
    }

    public void createPost(View view) {
        Intent intent = new Intent(this, PostsDoActivity.class);
        startActivity(intent);
    }

    /*
        private void openMyPosts(){
            Intent intent = new Intent(this, MyPostsActivity.class);
            startActivity(intent);
        }
     */
    private void openProfile() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private void createMenu() {
        PopupMenu menu = MainMenu.createMainMenu(this, imageButtonMenu, textView);
        imageButtonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.show();
            }
        });
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_profile: {
                        openProfile();
                        return true;
                    }
                    //case R.id.action_myPosts:{
                    //    openMyPosts();
                    //    return true;
                    //}
                    case R.id.action_refresh: {
                        dbController.deleteAll();
                        addPosts_toBD(HttpRequestController.get_posts());
                        fillPostsList();
                        return true;
                    }
                    case R.id.action_exit: {
                        deleteUser();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fillPostsList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbController.deleteAll();
    }
    private void deleteUser(){
        User.setNickname(null);
        User.setUsername(null);
        User.setEmail(null);
    }
}