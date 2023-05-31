package com.app.userdashboard;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.R;
import com.app.model.User;
import com.app.service.ApiClient;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HeaderDashBoardFragment extends Fragment {
    TextView myName, myView, myNumOfStory, myRate;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_header_dash_board, container, false);

        myName = root.findViewById(R.id.my_name);
        user = User.getUserFromSharedPreferences(getActivity());
        myName.setText(user.getName());

        myView = root.findViewById(R.id.my_view);
        setView(user.getId());

        myNumOfStory = root.findViewById(R.id.my_num_of_story);
        setNumOfStory(user.getId());

        myRate = root.findViewById(R.id.my_rate);
        setRate(user.getId());
        return root;
    }

    private void setView(int id) {
        Call<JsonObject> call = ApiClient.getApiService().getViewOfUser(id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    myView.setText(Integer.toString(jsonObject.get("countView").getAsInt()));
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    private void setNumOfStory(int id) {
        Call<JsonObject> call = ApiClient.getApiService().getNumOfStoryUserWritten(id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject num = response.body();
                    myNumOfStory.setText(Integer.toString(num.get("countStory").getAsInt()));
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    private void setRate(int id) {
        Call<JsonObject> call = ApiClient.getApiService().getRateOfUser(id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject rate = response.body();
//                    Log.e("zz", Integer.toString(rate.get("countRating").getAsInt()));
                    myRate.setText(Integer.toString(rate.get("countRating").getAsInt()));
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
}