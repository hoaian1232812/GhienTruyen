package com.app.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.app.model.Chapter;
import com.app.user.ChapterDetailFragment;

import java.util.List;

public class ChapterPaperAdapter extends FragmentStateAdapter {
    List<Chapter> chapters;

    public ChapterPaperAdapter(@NonNull FragmentActivity fragment, List<Chapter> chapters) {
        super(fragment);
        this.chapters = chapters;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new ChapterDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("chapter", chapters.get(position));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return chapters.size();
    }
}
