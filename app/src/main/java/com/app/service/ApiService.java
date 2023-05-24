package com.app.service;

import com.app.model.Chapter;
import com.app.model.Story;
import com.app.model.TimeStory;
import com.app.model.Topic;
import com.app.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/api/story/search")
    Call<List<Story>> getSearchStory(@Query("keyword") String keyword);

    @GET("/api/story/all")
    Call<List<Story>> getAllStory();

    @GET("/api/story/allTopic")
    Call<List<Story>> getAllStoryByTopic(@Query("idTopic") int idTopic);

    @GET("/api/story/newTopic")
    Call<List<Story>> getNewStoriesByTopicOnPage(@Query("idTopic") int idTopic, @Query("limit") int limit, @Query("page") int page);

    @GET("/api/topic/all")
    Call<List<Topic>> getAllTopic();

    @GET("/api/story/allNewUpdate")
    Call<List<Story>> getAllStoryNewUpdate();

    @GET("/api/story/allChapter")
    Call<List<Chapter>> getAllChapterByStory(@Query("idStory") int idStory);

    @GET("/api/users/nameUser")
    Call<User> getNameById(@Query("idUser") int idUser);

    @GET("/api/story/allCompletedByTopic")
    Call<List<Story>> getStoriesCompletedByTopicOnPage(@Query("idTopic") int idTopic, @Query("limit") int limit, @Query("page") int page);

    @GET("/api/story/allStoryMostViewed")
    Call<List<Story>> getStoriesMostViewedByTopicOnPage(@Query("idTopic") int idTopic, @Query("limit") int limit, @Query("page") int page);

    @GET("/api/story/allStoryMostLiked")
    Call<List<Story>> getStoriesLikedByTopicOnPage(@Query("idTopic") int idTopic, @Query("limit") int limit, @Query("page") int page);

    @GET("/api/story/newUpdateById")
    Call<TimeStory> getTimeUpdateById(@Query("idStory") int idStory);

    @GET("/api/story/storyById")
    Call<Story> getStoryById(@Query("idStory") int idStory);

}
