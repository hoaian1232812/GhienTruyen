package com.app.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.app.service.ApiClient;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Story implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("author")
    @Expose
    private int author_id;
    @SerializedName("introduce")
    @Expose
    private String introduce;
    @SerializedName("views")
    @Expose
    private int views;
    @SerializedName("likes")
    @Expose
    private int likes;
    @SerializedName("createDate")
    @Expose
    private String date;
    @SerializedName("topic")
    @Expose
    private List<Topic> topics;
    @SerializedName("status")
    @Expose
    private int status;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public String getStatus() {
        switch (this.status) {
            case 0:
                return "Đang ra";
            case 1:
                return "Đã hoàn thành";
            default:
                return "Lỗi rồi mày";
        }
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public CompletableFuture<List<Chapter>> getAllChapter() {
        CompletableFuture<List<Chapter>> future = new CompletableFuture<>();
        Call<List<Chapter>> call = ApiClient.getApiService().getAllChapterByStory(this.id);
        call.enqueue(new Callback<List<Chapter>>() {
            @Override
            public void onResponse(Call<List<Chapter>> call, Response<List<Chapter>> response) {
                if (response.isSuccessful()) {
                    List<Chapter> chapters = response.body();
                    future.complete(chapters);
                } else {
                    future.completeExceptionally(new Exception("Request failed")); // Xử lý khi có lỗi xảy ra
                }
            }

            @Override
            public void onFailure(Call<List<Chapter>> call, Throwable t) {
                future.completeExceptionally(t);
            }
        });

        return future;
    }

    public CompletableFuture<User> getNameAuthor() {
        CompletableFuture<User> future = new CompletableFuture<>();
        Call<User> call = ApiClient.getApiService().getNameById(this.author_id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    future.complete(response.body());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
        return future;
    }

    public CompletableFuture<TimeStory> getTime() {
        CompletableFuture<TimeStory> future = new CompletableFuture<>();
        Call<TimeStory> call = ApiClient.getApiService().getTimeUpdateById(this.id);
        call.enqueue(new Callback<TimeStory>() {
            @Override
            public void onResponse(Call<TimeStory> call, Response<TimeStory> response) {
                if (response.isSuccessful()) {
                    future.complete(response.body());
                }
            }

            @Override
            public void onFailure(Call<TimeStory> call, Throwable t) {

            }
        });
        return future;
    }

    public CompletableFuture<Double> getRating() {
        CompletableFuture<Double> future = new CompletableFuture<>();
        Call<JsonObject> call = ApiClient.getApiService().getRatingById(this.id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    double rating = jsonObject.get("rating").getAsDouble();
                    future.complete(rating);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("Lỗi", t.getMessage());
            }
        });
        return future;
    }


    public String getListNameTopic() {
        String result = "";
        for (Topic t : this.topics) {
            result += " • " + t.getName();
        }
        return result.replaceFirst(" • ", "");
    }

    @Override
    public String toString() {
        return "Story{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", author_id=" + author_id +
                ", introduce='" + introduce + '\'' +
                ", views=" + views +
                ", likes=" + likes +
                ", date='" + date + '\'' +
                ", topics=" + topics +
                ", status=" + status +
                '}';
    }
}
