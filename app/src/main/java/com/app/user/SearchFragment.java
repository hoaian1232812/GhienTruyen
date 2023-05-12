package com.app.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.app.R;
import com.app.adapter.TruyenAdapter;
import com.app.model.Truyen;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {
    EditText editText;
    TruyenAdapter adapter;
    RecyclerView recyclerView;
    List<Truyen> truyens;
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
        truyens = new ArrayList<>();
        truyens.add(new Truyen("", "title truyen1"));
        truyens.add(new Truyen("", "title truyen2"));
        truyens.add(new Truyen("", "title truyen3"));
        truyens.add(new Truyen("", "title truyen4"));
        truyens.add(new Truyen("", "title truyen5"));
        truyens.add(new Truyen("", "title truyen6"));
        truyens.add(new Truyen("", "a"));
        truyens.add(new Truyen("", "b"));
        adapter = new TruyenAdapter(truyens);
        recyclerView.setAdapter(adapter);
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
                    List<Truyen> search = search(keywork);
                    adapter.setTruyenList(search);
                } else {
                    adapter.setTruyenList(truyens);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public List<Truyen> search(String keywork) {
        List<Truyen> searchs = new ArrayList<>();
        for (Truyen t : truyens)
            if (t.getTitle().contains(keywork))
                searchs.add(t);
        return searchs;
    }
}