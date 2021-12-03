package com.example.sunnyweather.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunnyweather.R;
import com.example.sunnyweather.gson.everyIndex;

import java.util.List;

public class IndexAdapter extends RecyclerView.Adapter<IndexAdapter.ViewHolder> {
    private List<everyIndex> indexList;
    @NonNull
    @Override
    public IndexAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.index_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public IndexAdapter(List<everyIndex> indexList) {
        this.indexList = indexList;
    }

    @Override
    public void onBindViewHolder(@NonNull IndexAdapter.ViewHolder holder, int position) {
        everyIndex everyindex = indexList.get(position);
        holder.descText.setText(everyindex.getDesc());
        holder.titleText.setText(everyindex.getTitle());
        holder.levelText.setText(everyindex.getLevel());
    }

    @Override
    public int getItemCount() {
        return indexList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView levelText;
        TextView titleText;
        TextView descText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            levelText=itemView.findViewById(R.id.level_text);
            titleText=itemView.findViewById(R.id.title_text);
            descText=itemView.findViewById(R.id.desc_text);
        }
    }
}
