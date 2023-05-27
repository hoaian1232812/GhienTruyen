package com.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.R;
import com.app.model.Topic;
import com.app.user.TopicDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class StatisticalAdapter extends RecyclerView.Adapter<StatisticalAdapter.StatisticalVH> {

    List<String> statistics;
    Context context;

    public StatisticalAdapter() {
        this.statistics = new ArrayList<>();
        statistics.add("Lượt thích");
        statistics.add("Lượt đọc");
        statistics.add("Lượt đánh giá");
    }

    @NonNull
    @Override
    public StatisticalVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_topic, parent, false);
        return new StatisticalVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticalVH holder, int position) {
        String statisticalName = statistics.get(position);
        holder.text.setText(statisticalName);
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), MenuStatisticalAdapter.class);
            Bundle bundle = new Bundle();
            bundle.putString("statisticalName", statisticalName);
            bundle.putInt("statisticalId", position);
            intent.putExtra("data", bundle);
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return statistics.size();
    }


    class StatisticalVH extends RecyclerView.ViewHolder {
        TextView text;

        public StatisticalVH(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.topic_text);
        }
    }
}
