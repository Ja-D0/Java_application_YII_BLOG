package com.example.blog;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class HttpRequestController {
    private static OkHttpClient httpClient;
    private static final String URL_GET_POSTS = "http://192.168.1.9/api/get_posts";
    private static final String URL_CREATE_POST = "http://192.168.1.9/api/create_post";
    private static final String URL_GET_POST = "http://192.168.1.9/api/get_post?id=";
    private static final String URL_UPDATE_POST = "http://192.168.1.9/api/update_post";
    private static final String URL_DELETE_POST = "http://192.168.1.9/api/delete_post";
    private static final String URL_CHECK_USER = "http://192.168.1.9/api/check_user";


    protected static void init() {
        httpClient = new OkHttpClient.Builder().connectTimeout(1, TimeUnit.SECONDS).build();
    }

    protected static ArrayList<Post> get_posts() {
        ArrayList<Post> postsList = new ArrayList<Post>();
        final CountDownLatch latch = new CountDownLatch(1);
        Request request = new Request.Builder().url(URL_GET_POSTS).build();
        httpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                latch.countDown();
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String result = response.body().string().replace("[", "").replace("]", "");
                String[] jsonRes = result.split(Pattern.quote("},"));
                jsonRes[jsonRes.length - 1] = jsonRes[jsonRes.length - 1].replace("}", "");

                Gson gson = new Gson();
                for (String json : jsonRes) {
                    Post post = gson.fromJson(json + "}", Post.class);
                    if (post.getCreated_at() == null) {
                        continue;
                    } else {
                        postsList.add(post);
                    }
                }
                latch.countDown();
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return postsList;
    }

    protected static void create_post(Post post) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                String jsonPost = gson.toJson(post);
                Log.i("JSON", jsonPost, new Throwable());

                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonPost);

                Request request = new Request.Builder().url(URL_CREATE_POST).post(body).build();
                try (Response response = httpClient.newCall(request).execute()) {
                    if (!response.isSuccessful()) {
                        Log.i("RESPONCE", response.body().string());
                        throw new IOException("Запрос к серверу не был успешен: " +
                                response.code() + " " + response.message());
                    }
                    Log.i("RESPONCE", response.body().string());
                } catch (IOException e) {
                    System.out.println("Ошибка подключения: " + e);
                }
            }
        });
        thread.start();
    }

    protected static void update_post(Post post) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                String jsonPost = gson.toJson(post);
                Log.i("JSON", jsonPost, new Throwable());

                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonPost);

                Request request = new Request.Builder().url(URL_UPDATE_POST).post(body).build();
                try (Response response = httpClient.newCall(request).execute()) {
                    if (!response.isSuccessful()) {
                        Log.i("RESPONCE", response.body().string());
                        throw new IOException("Запрос к серверу не был успешен: " +
                                response.code() + " " + response.message());
                    }
                    Log.i("RESPONCE", response.body().string());
                } catch (IOException e) {
                    System.out.println("Ошибка подключения: " + e);
                }
            }
        });
        thread.start();
    }

    protected static void delete_post(Post post) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                String jsonPost = gson.toJson(post);
                Log.i("JSON", jsonPost, new Throwable());

                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonPost);

                Request request = new Request.Builder().url(URL_DELETE_POST).post(body).build();
                try (Response response = httpClient.newCall(request).execute()) {
                    if (!response.isSuccessful()) {
                        Log.i("RESPONCE", response.body().string());
                        throw new IOException("Запрос к серверу не был успешен: " +
                                response.code() + " " + response.message());
                    }
                    Log.i("RESPONCE", response.body().string());
                } catch (IOException e) {
                    System.out.println("Ошибка подключения: " + e);
                }
            }
        });
        thread.start();
    }

    protected static void check_user(String login, String password, LoginInterface loginInterface, View v) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String jsonString = "{\"password\":\"" + password +"\", \"username\":\"" + login +"\"}";
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);

                Request request = new Request.Builder().url(URL_CHECK_USER).post(requestBody).build();
                try (Response response = httpClient.newCall(request).execute()) {
                    if (!response.isSuccessful()) {
                        Snackbar.make(v, "Неправильный логин или пароль", Snackbar.LENGTH_SHORT).show();
                        throw new IOException("Запрос к серверу не был успешен: " +
                                response.code() + " " + response.message());
                    }
                    loginInterface.Login(response.body().string(), v);
                } catch (IOException e) {
                    System.out.println("Ошибка подключения: " + e);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(OkHttpClient httpClient) {
        HttpRequestController.httpClient = httpClient;
    }
}
