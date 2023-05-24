package com.app.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.app.R;
import com.app.adapter.StoryAdapter;
import com.app.model.Story;
import com.app.service.ApiClient;
import com.app.service.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends Fragment {
    EditText editText;
    StoryAdapter adapter;
    RecyclerView recyclerView;
    List<Story> stories;
    GridLayoutManager layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        setUpEditSearch(root);
        setUpList(root);
        return root;
    }

    public void setUpList(View root) {
        layout = new GridLayoutManager(root.getContext(), 3);
        recyclerView = root.findViewById(R.id.search_recyc);
        recyclerView.setLayoutManager(layout);
        Call<List<Story>> call = ApiClient.getApiService().getAllStory();
        call.enqueue(new Callback<List<Story>>() {
            @Override
            public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                if (response.isSuccessful()) {
                    stories = response.body();
                    adapter = new StoryAdapter(stories);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Story>> call, Throwable t) {
            }
        });

    }

    public void setUpEditSearch(View root) {
        editText = root.findViewById(R.id.search);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String keywork = charSequence.toString();
                if (!keywork.isEmpty()) {
                    List<Story> search = search(keywork);
                    adapter.setTruyenList(search);
                } else {
                    adapter.setTruyenList(stories);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public List<Story> search(String keywork) {
        List<Story> searchs = new ArrayList<>();
        for (Story s : stories) {
            String title = s.getTitle().toLowerCase();
            if (title.contains(keywork))
                searchs.add(s);
        }
        return searchs;
    }
}