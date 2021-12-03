package com.example.sunnyweather.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.sunnyweather.R;
import com.example.sunnyweather.gson.everyData;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    List<everyData> dataList;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public DataAdapter(List<everyData> dataList) {
        this.dataList = dataList;
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.ViewHolder holder, int position) {
        everyData eData = dataList.get(position);
        holder.temperatureText.setText(eData.getTem1()+"/"+eData.getTem2());
        holder.dateText.setText(eData.getDate());
        if(eData.getWea_img().equals("yun")){
            holder.foreWeatherImage.setImageResource(R.drawable.duoyun);
        }else if(eData.getWea_img().equals("yin")){
            holder.foreWeatherImage.setImageResource(R.drawable.yin);
        }else if(eData.getWea_img().equals("xue")){
            holder.foreWeatherImage.setImageResource(R.drawable.xue);
        }else if(eData.getWea_img().equals("qing")){
            holder.foreWeatherImage.setImageResource(R.drawable.qing);
        }else if(eData.getWea_img().equals("yu")){
            holder.foreWeatherImage.setImageResource(R.drawable.yu);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView foreWeatherImage;
        TextView dateText;
        TextView temperatureText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foreWeatherImage = itemView.findViewById(R.id.foreweaimg);
            dateText = itemView.findViewById(R.id.date_text);
            temperatureText = itemView.findViewById(R.id.temperature_text);
        }
    }
}
