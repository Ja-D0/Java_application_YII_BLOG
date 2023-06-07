package com.example.blog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private TextView textView;
    private ImageButton imageButtonMenu;
    private Button button;
    private  DBController dbController;
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

    private void addPosts_toBD(ArrayList<Post> postsList){
        dbController.insert(postsList);
    }
    private void fillPostsList(){
       ArrayList<Post> listPosts = dbController.selectAll();
       PostAdapter adapter = new PostAdapter(this, R.layout.layout_post, listPosts);
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
    public void createPost(View view){
        Intent intent = new Intent(this, MyPostsActivity.class);
        startActivity(intent);
    }

    private void openProfile(){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);

    }
    private void openMyPosts(){
        Intent intent = new Intent(this, MyPostsActivity.class);
        startActivity(intent);
    }
    private void createMenu(){
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
                switch (item.getItemId()){
                    case R.id.action_profile:{
                        openProfile();
                        return true;
                    }
                    case R.id.action_myPosts:{
                        openMyPosts();
                        return true;
                    }
                    case R.id.action_refresh:{
                        dbController.deleteAll();
                        addPosts_toBD(HttpRequestController.get_posts());
                        fillPostsList();
                        return true;
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
        Toast.makeText(this, "Удалено", Toast.LENGTH_SHORT).show();
    }
}