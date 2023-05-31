package com.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.R;
import com.app.model.Comment;
import com.app.model.User;
import com.app.service.ApiClient;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentVH> {
    List<Comment> commentList;
    Context context;

    public CommentAdapter(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public CommentVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new CommentVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentVH holder, int position) {
        Comment comment = commentList.get(position);
        Glide.with(holder.img.getContext())
                .load("http://139.180.129.238:8080/images/OIP.jpg")
                .transform(new CircleCrop())
                .into(holder.img);
        Call<User> call = ApiClient.getApiService().getNameById(comment.getIdUser());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    holder.nameUser.setText(response.body().getName());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        holder.content.setText(comment.getContent());
        holder.date.setText(comment.getDate());
        for (int i = 0; i < 5; i++) {
            ImageView img = new ImageView(holder.itemView.getContext());
            if (i < comment.getStar()) {
                img.setImageDrawable(context.getDrawable(R.drawable.baseline_star_24));
            } else {
                img.setImageDrawable(context.getDrawable(R.drawable.outline_star_rate_24_blue));
            }
            holder.layoutStar.addView(img);
        }
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public void addNewData(List<Comment> body) {
        this.commentList.addAll(body);
        notifyDataSetChanged();
    }

    class CommentVH extends RecyclerView.ViewHolder {
        ImageView img;
        TextView nameUser, content, date;
        LinearLayout layoutStar;

        public CommentVH(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_user);
            nameUser = itemView.findViewById(R.id.nameUser);
            content = itemView.findViewById(R.id.content);
            date = itemView.findViewById(R.id.date);
            layoutStar = itemView.findViewById(R.id.star_comment);
        }
    }
}
