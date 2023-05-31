package com.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.R;
import com.app.model.Story;
import com.app.model.User;
import com.app.service.ApiClient;
import com.app.user.StoryDetail;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.TruyenVH> {

    List<Story> storyList;

    Context context;

    public StoryAdapter(List<Story> storyList) {
        this.storyList = storyList;
    }

    public void setTruyenList(List<Story> newList) {
        storyList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TruyenVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_truyen, parent, false);
        return new TruyenVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TruyenVH holder, int position) {
        Story story = storyList.get(position);
        holder.textView.setText(story.getTitle());
        Glide.with(holder.imageView.getContext())
                .load(story.getImage())
                .into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = User.getUserFromSharedPreferences(view.getContext());
                if (user != null) {
                    Call<JsonObject> call = ApiClient.getApiService().updateView(user.getId(), story.getId());
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {

                        }
                    });
                } else {
                    String deviceId = Settings.Secure.getString(view.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                    String uniqueName = "user_preferences_" + deviceId;
                    SharedPreferences userPreferences = view.getContext().getSharedPreferences(uniqueName, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = userPreferences.edit();
                    Gson gson = new Gson();
                    editor.putString("story_" + story.getId() + "_read", gson.toJson(story));
                    editor.apply();
                }
                Intent intent = new Intent(view.getContext(), StoryDetail.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("story", story);
                intent.putExtra("data", bundle);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return storyList.size();
    }

    public void addNewData(List<Story> body) {
        this.storyList.addAll(body);
        notifyDataSetChanged();
    }

    class TruyenVH extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public TruyenVH(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_truyen);
            textView = itemView.findViewById(R.id.title);
        }
    }
}
