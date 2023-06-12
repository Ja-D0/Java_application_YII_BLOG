package com.example.blog;

import android.provider.ContactsContract;

public class User {

    private static String username, nickname, email, created_at, updated_at;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        User.username = username;
    }

    public static String getNickname() {
        return nickname;
    }

    public static void setNickname(String nickname) {
        User.nickname = nickname;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        User.email = email;
    }

    public static String getCreated_at() {
        return created_at;
    }

    public static void setCreated_at(String created_at) {
        User.created_at = created_at;
    }

    public static String getUpdated_at() {
        return updated_at;
    }

    public static void setUpdated_at(String updated_at) {
        User.updated_at = updated_at;
    }
}
