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
import com.app.user.ChapterDetailActivity;
import com.google.gson.Gson;

import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterVH> {
    List<Chapter> chaps;
    Context context;

    public ChapterAdapter(List<Chapter> chaps) {
        this.chaps = chaps;
    }

    @NonNull
    @Override
    public ChapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_chapter, parent, false);
        return new ChapterVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterVH holder, int position) {
        Chapter chapter = chaps.get(position);
        holder.textView.setText("Chương " + chapter.getIndex() + ": " + chapter.getName());
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), ChapterDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("chapters", new Gson().toJson(chaps));
            bundle.putInt("position", position);
            intent.putExtra("data", bundle);
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return chaps.size();
    }

    class ChapterVH extends RecyclerView.ViewHolder {
        TextView textView;

        public ChapterVH(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.chapter_story);
        }
    }
}
