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
import com.app.model.Truyen;
import com.bumptech.glide.Glide;

import java.util.List;

public class TruyenAdapter extends RecyclerView.Adapter<TruyenAdapter.TruyenVH> {

    List<Truyen> truyenList;

    Context context;

    public TruyenAdapter(List<Truyen> truyenList) {
        this.truyenList = truyenList;
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
        Truyen truyen = truyenList.get(position);
        holder.textView.setText(truyen.getTitle());
        Glide.with(holder.imageView.getContext())
                .load("http://139.180.129.238:8080/Untitled.png")
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return truyenList.size();
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
