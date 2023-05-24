package com.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.R;
import com.app.model.Story;
import com.app.user.StoryDetail;
import com.bumptech.glide.Glide;

import java.util.List;

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
        Glide.with(holder.imageView.getContext()).load("http://139.180.129.238:8080/Untitled1.jpg").into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
