package com.example.blog;

public class Post {
    private int id;
    private String title, description, author, created_at, updated_at, status, category;

    Post(String title, String description, String author, String created_at, String updated_at){
        this.title = title;
        this.description = description;
        this.author = author;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
    Post(String title, String description, String author){
        this.title = title;
        this.description = description;
        this.author = author;

    }
    Post(int id, String title, String description, String author, String created_at, String updated_at){
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
