package com.example.sunnyweather.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunnyweather.R;
import com.example.sunnyweather.gson.Weather;
import com.example.sunnyweather.view.activity.SettingActivity;

import java.util.ArrayList;
import java.util.List;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.ViewHolder> {
    List<Weather> weatherList;
    static SettingActivity settingActivity;
    List<Boolean> checkBoxes;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weathercity_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public SettingAdapter(List<Weather> weatherList, SettingActivity settingActivity) {
        this.weatherList = weatherList;
        this.settingActivity = settingActivity;
        checkBoxes = new ArrayList<>(weatherList.size());
    }

    @Override
    public void onBindViewHolder(@NonNull SettingAdapter.ViewHolder holder, int position) {
        Weather weather = weatherList.get(position);
        Log.d("SettingActivity",weather.getCity());
        holder.cityName.setText(weather.getCity());
        holder.cityAir.setText("空气质量"+weather.getData().get(0).getAir_level());
        holder.cityTem.setText(weather.getData().get(0).getTem());
        holder.deleteButton.setVisibility(View.GONE);
        holder.constraintLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(!weather.getCity().equals(settingActivity.loaction_name)) {
                    Log.d("ddddddddddddddsettingOnCreate",weather.getCity()+","+settingActivity.loaction_name);
                    holder.deleteButton.setVisibility(View.VISIBLE);
                    holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Weather weather = settingActivity.weatherList.get(position);
                            settingActivity.sunnyWeatherDB.deleteWeather(weather.getCity(), settingActivity.weatherList);
                            settingActivity.weatherList.remove(position);
                            Log.d("onLongClick", "deleted"+weather.getCity());
                            settingActivity.settingAdapter.notifyDataSetChanged();
                        }
                    });
                    settingActivity.backButton.setVisibility(View.VISIBLE);
                    settingActivity.backButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            holder.deleteButton.setVisibility(View.GONE);       //最后一次点击的holder
//                            settingActivity.backButton.setVisibility(View.GONE);
//                            weatherList=settingActivity.weatherList;
                            settingActivity.backButton.setVisibility(View.GONE);
                            settingActivity.settingAdapter.notifyDataSetChanged();
                        }
                    });
                    return true;
                }
                else {holder.deleteButton.setVisibility(View.GONE);Log.d("settingOnCreatedddddddddddddd","不能删除loacationname！！");}
                return false;//setOnLongClickListener中return的值决定是否在长按后再加一个短按动作，true为不加短按,false为加入短按
            }
        });

    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cityName;
        TextView cityAir;
        TextView cityTem;
        Button deleteButton;
        ConstraintLayout constraintLayout;
        //Button backButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout=itemView.findViewById(R.id.weahter_item);
            cityName=itemView.findViewById(R.id.settingscityName);
            cityAir=itemView.findViewById(R.id.cityAir);
            cityTem=itemView.findViewById(R.id.cityTem);
            deleteButton=itemView.findViewById(R.id.cityDeleteButton);
            //backButton = itemView.findViewById(R.id.back_button_insetting);
        }
    }


//    public OnLongClickListener onLongClickListener = null;
//    public interface OnLongClickListener{
//         default void onLongClick(SettingAdapter.ViewHolder holder, int position){
//            holder.deleteButton.setVisibility(View.VISIBLE);
//            Weather weather = settingActivity.weatherList.get(position);
////                   settingActivity.sunnyWeatherDB.deleteWeather(holder.cityName.getText().toString(),settingActivity.weatherList);
//            settingActivity.sunnyWeatherDB.deleteWeather(weather.getCity(),settingActivity.weatherList);
//            settingActivity.weatherList.remove(position);
//            Log.d("onLongClick","deleted");
//            settingActivity.settingAdapter.notifyDataSetChanged();
//        };
//    }
//    public void setOnLongClickListener(OnLongClickListener listener){
//        onLongClickListener = listener;
//    }
}
