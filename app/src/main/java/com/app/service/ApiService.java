package com.app.service;

import com.app.model.Chapter;
import com.app.model.Story;
import com.app.model.TimeStory;
import com.app.model.Topic;
import com.app.model.User;
import com.app.model.support.MonthStatistical;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
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

    @GET("/api/story/allStoryMostViewedById")
    Call<List<Story>> getStoriesMostViewedByTopicOnPage(@Query("idTopic") int idTopic, @Query("limit") int limit, @Query("page") int page);

    @GET("/api/story/allStoryMostLikedById")
    Call<List<Story>> getStoriesLikedByTopicOnPage(@Query("idTopic") int idTopic, @Query("limit") int limit, @Query("page") int page);

    @GET("/api/story/newUpdateById")
    Call<TimeStory> getTimeUpdateById(@Query("idStory") int idStory);

    @GET("/api/story/storyById")
    Call<Story> getStoryById(@Query("idStory") int idStory);

    @GET("/api/statistical/likeInMonthOfYear")
    Call<List<MonthStatistical>> getMonthStatisticalLikeInYear(@Query("year") int year, @Query("author") int idAuthor);

    @GET("/api/statistical/likeOfYear")
    Call<JsonArray> getStatisticalLikeOfYear(@Query("author") int idAuthor);

    @GET("/api/story/avgRatingOfStory")
    Call<JsonObject> getRatingById(@Query("idStory") int idStory);

    @GET("/api/story/allStoryAppreciation")
    Call<List<Story>> getAllStoryAppreciation(@Query("limit") int limit, @Query("page") int page);

    @GET("/api/story/allStoryMostLiked")
    Call<List<Story>> getAllStoryLiked(@Query("limit") int limit, @Query("page") int page);
    @GET("/api/story/allStoryMostViewed")
    Call<List<Story>> getAllStoryViewed(@Query("limit") int limit, @Query("page") int page);
}
