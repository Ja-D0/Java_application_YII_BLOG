package com.example.blog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.zip.Inflater;

public class PostAdapter extends ArrayAdapter<Post> {

    private LayoutInflater inflater;
    private int layout;
    private List<Post> posts;

    public PostAdapter(@NonNull Context context, int resource, @NonNull List<Post> posts) {
        super(context, resource, posts);
        this.posts = posts;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewPostHolder postHolder;
        if (convertView == null){
            convertView = inflater.inflate(this.layout, parent, false);
            postHolder = new ViewPostHolder(convertView);
            convertView.setTag(postHolder);
        }
        else {
            postHolder = (ViewPostHolder) convertView.getTag();
        }
        Post post = posts.get(position);
        postHolder.title.setText(post.getTitle());
        postHolder.description.setText(post.getDescription());
        postHolder.author.setText(post.getAuthor());
        postHolder.created_at.setText(post.getCreated_at());
        postHolder.updated_at.setText(post.getUpdated_at());
        return convertView;
    }

    private class ViewPostHolder{
        private TextView title, description, author, created_at, updated_at;

        ViewPostHolder(View view){
            title = view.findViewById(R.id.postTitle);
            description = view.findViewById(R.id.postDescription);
            author = view.findViewById(R.id.postAuthor);
            created_at = view.findViewById(R.id.post_created_at);
            updated_at = view.findViewById(R.id.post_updated_at);
        }
    }
}
