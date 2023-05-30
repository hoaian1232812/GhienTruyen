package com.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.R;
import com.app.model.Comment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentVH>{
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
        holder.nameUser.setText("aaa");
        holder.content.setText(comment.getContent());
        holder.date.setText(comment.getDate());
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
        public CommentVH(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_user);
            nameUser = itemView.findViewById(R.id.nameUser);
            content = itemView.findViewById(R.id.content);
            date = itemView.findViewById(R.id.date);
        }
    }
}
