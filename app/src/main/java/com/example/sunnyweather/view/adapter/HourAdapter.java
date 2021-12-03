package com.example.sunnyweather.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunnyweather.R;
import com.example.sunnyweather.gson.everyHour;

import java.util.List;

public class HourAdapter extends RecyclerView.Adapter<HourAdapter.ViewHolder> {
    private List<everyHour> mhourList;

    public HourAdapter(List<everyHour> mhourList) {
        this.mhourList = mhourList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hour_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HourAdapter.ViewHolder holder, int position) {
        everyHour hour = mhourList.get(position);
        holder.hourTimeText.setText(hour.getHours());
        holder.hourTemperatureText.setText(hour.getTem() + "℃");
        holder.hourWinText.setText(hour.getWin());
        holder.hourWinspeedText.setText(hour.getWin_speed());
        holder.hourWeaText.setText(hour.getWea());
        //Log.d("onBindViewHolder",hour.getHours()+"startImg," + hour.getWea_img());
        if (hour.getWea_img().equals("yun")) {
            holder.hourWeaImg.setImageResource(R.drawable.duoyun);
            //Log.d("onBindViewHolder",hour.getHours()+"setYunImg");
        }else if(hour.getWea_img().equals("qing")){
            holder.hourWeaImg.setImageResource(R.drawable.qing);
            //Log.d("onBindViewHolder",hour.getHours()+"setQingImg");
        }else if(hour.getWea_img().equals("yu")){
            holder.hourWeaImg.setImageResource(R.drawable.yu);
            //Log.d("onBindViewHolder",hour.getHours()+"setYuImg");
        }else if(hour.getWea_img().equals("yin")){
            holder.hourWeaImg.setImageResource(R.drawable.yin);
           // Log.d("onBindViewHolder",hour.getHours()+"setYinImg");
        }else if(hour.getWea_img().equals("xue")){
            holder.hourWeaImg.setImageResource(R.drawable.xue);
            //Log.d("onBindViewHolder",hour.getHours()+"setXueImg");
        }
    }

    @Override
    public int getItemCount() {
        return mhourList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView hourTimeText;
        TextView hourTemperatureText;
        TextView hourWinText;
        TextView hourWinspeedText;
        TextView hourWeaText;
        ImageView hourWeaImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hourTimeText = (TextView)itemView.findViewById(R.id.hour_time);
            hourTemperatureText = itemView.findViewById(R.id.hour_temperature);
            hourWinText = itemView.findViewById(R.id.hour_win);
            hourWinspeedText = itemView.findViewById(R.id.hour_winspeed);
            hourWeaText = itemView.findViewById(R.id.hour_weather);
            hourWeaImg = itemView.findViewById(R.id.hour_weatherimg);
        }
    }
}
