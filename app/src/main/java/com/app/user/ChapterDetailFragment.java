package com.app.user;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.R;
import com.app.model.Chapter;


public class ChapterDetailFragment extends Fragment {
TextView title, content;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View root = inflater.inflate(R.layout.fragment_chapter_detail, container, false);
       Chapter chapter = (Chapter) getArguments().getSerializable("chapter");
        title = root.findViewById(R.id.title_detail_chapter);
        title.setText("Chương " + chapter.getIndex() + ": " + chapter.getName());
        content = root.findViewById(R.id.content_detail_chapter);
        content.setText(chapter.getContent());
        return root;
    }
}