package com.app.service;
import com.app.model.Story;
import com.app.model.Topic;
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
    Call<List<Story>> getNewStoriesByTopic(@Query("idTopic") int idTopic);

    @GET("/api/topic/all")
    Call<List<Topic>> getAllTopic();

    @GET("/api/story/allNewUpdate")
    Call<List<Story>> getAllStoryNewUpdate();
}
