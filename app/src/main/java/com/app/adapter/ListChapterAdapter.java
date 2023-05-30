package com.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.R;
import com.app.model.Chapter;
import com.app.model.Story;
import com.app.user.ChapterActivity;
import com.app.user.ChapterDetailActivity;
import com.app.userdashboard.detail.ListChapterActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import java.util.List;

public class ListChapterAdapter extends RecyclerView.Adapter<ListChapterAdapter.ChapterViewHolder> {
    List<Chapter> chapters;
    Context context;

    public ListChapterAdapter(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    @NonNull
    @Override
    public ListChapterAdapter.ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.chapter_item, parent, false);
        return new ListChapterAdapter.ChapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListChapterAdapter.ChapterViewHolder holder, int position) {
        Chapter chapter = chapters.get(position);
        holder.index.setText("Chapter " + (position + 1) + ": ");
        holder.title.setText(chapter.getName());
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), ChapterDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("chapter", chapter);
            intent.putExtra("data", bundle);
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return chapters.size();
    }

    class ChapterViewHolder extends RecyclerView.ViewHolder {
        TextView index, title;

        public ChapterViewHolder(@NonNull View itemView) {
            super(itemView);
            index = itemView.findViewById(R.id.chapter_index);
            title = itemView.findViewById(R.id.chapter_title);
        }
    }
}
