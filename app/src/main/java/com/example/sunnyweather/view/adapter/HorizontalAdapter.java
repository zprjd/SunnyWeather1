package com.example.sunnyweather.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.sunnyweather.R;
import com.example.sunnyweather.gson.Weather;
import com.example.sunnyweather.gson.everyData;
import com.example.sunnyweather.gson.everyHour;
import com.example.sunnyweather.gson.everyIndex;
import com.example.sunnyweather.view.activity.MYNEWWeatherActivity;

import java.util.List;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.HorizontalAdapterViewHolder> {

    private List<Weather> weatherList;
    private View view;
    private MYNEWWeatherActivity mynewWeatherActivity;
    public HorizontalAdapter(MYNEWWeatherActivity context,List<Weather> viewList) {
        this.weatherList = viewList;
        mynewWeatherActivity = context;
    }
    @NonNull
    @Override
    public HorizontalAdapterViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mynewWeatherActivity).inflate(R.layout.weather_fragment,parent,false);
        Log.d("onCreateViewHolder","");
        return new HorizontalAdapterViewHolder(view);
    }
    int flag = 0;
    @Override
    public void onBindViewHolder(@NonNull HorizontalAdapterViewHolder holder, int position) {
        Weather weather = weatherList.get(position);
        //Log.d("HoonBindViewHolder", String.valueOf(position));
        holder.degreeText.setText(weather.getData().get(0).getTem());
        holder.weather_info_text.setText(weather.getData().get(0).getWea());
        String tempair = "空气质量:" + weather.getData().get(0).getAir_level();
        //Log.d("WeatherInfoWeather.Data0.air", weather.getData().get(0).getAir_level());
        holder.air_text.setText(tempair);
        String tempTips = weather.getData().get(0).getAir_tips();
        holder.wea_text.setText(tempTips);
        holder.dataList = weather.getData();
        DataAdapter dataAdapter = new DataAdapter(holder.dataList);
        holder.forecast_recyclerView.setAdapter(dataAdapter);
        LinearLayoutManager flayoutManager = new LinearLayoutManager(mynewWeatherActivity);
        holder.forecast_recyclerView.setLayoutManager(flayoutManager);
        everyData todayData = weather.getData().get(0);
        //Log.d("WeatherInfoWeather.Data0.air", weather.getData().get(0).getAir_level());
        String degree = todayData.getTem();
        holder.degreeText.setText(degree);
        holder.weather_info_text.setText(todayData.getWea());
        holder.hoursList = todayData.getHours();    //解析给data后hoursList已经生成了
        //Log.d("showWeatherInfo", holder.hoursList.size() + "," + holder.hoursList.get(0).getTem() + ","
        //        + holder.hoursList.get(0).getHours() + "," + holder.hoursList.get(0).getWea() + "," + holder.hoursList.get(0).getWin() + "," + holder.hoursList.get(0).getWin_speed() + "," + holder.hoursList.get(0).getWea_img());
        HourAdapter hourAdapter = new HourAdapter(holder.hoursList);
        //Log.d("showWeatherInfo", "22222");
        holder.hours_recyclerView.setAdapter(hourAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mynewWeatherActivity);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.hours_recyclerView.setLayoutManager(layoutManager);
        holder.indexList = todayData.getIndex();
        IndexAdapter indexAdapter = new IndexAdapter(holder.indexList);
        LinearLayoutManager layoutManagerI = new LinearLayoutManager(mynewWeatherActivity);
        layoutManagerI.setOrientation(LinearLayoutManager.VERTICAL);
        holder.index_recyclerView.setLayoutManager(layoutManagerI);
        holder.index_recyclerView.setAdapter(indexAdapter);
        holder.weatherScroll.setVisibility(View.VISIBLE);
        holder.winText.setText(weather.getData().get(0).getWin()[0]);
        holder.winspeedText.setText(weather.getData().get(0).getWin_speed());
        holder.visibilityText.setText(weather.getData().get(0).getVisibility1());
        holder.pressureText.setText(weather.getData().get(0).getPressure() + "hPa");
        holder.humidityText.setText(weather.getData().get(0).getHumidity());
        holder.sundownText.setText("日落 " + weather.getData().get(0).getSunset());
        holder.sunriseText.setText("日出 " + weather.getData().get(0).getSunrise());
        holder.weatherScroll.setNestedScrollingEnabled(false);
        holder.swipeRefreshLayout.setColorSchemeResources(R.color.design_default_color_primary);
        holder.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                for(int i = 0;i<mynewWeatherActivity.weatherList.size();i++) {
//                    Weather weather1 = mynewWeatherActivity.weatherList.get(i);
//                    if(mynewWeatherActivity.titleCity.getText().toString().equals(weather1.getCity())){
//                        mynewWeatherActivity.mweather = weather1;
//                    }
//                }
                mynewWeatherActivity.requestWeather(mynewWeatherActivity.mweather.getCity(),"1");
            }
        });
//        holder.navButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mynewWeatherActivity.drawerLayout.openDrawer(GravityCompat.START);
//            }
//        });
//        holder.titleCity.setText(weather.getCity());
//        //Log.d("HoonBindViewHolder",weather.getCity());
//        holder.titleUpdateTime.setText(weather.getUpdate_time());
//        if (holder.weatherScroll != null){
//            holder.weatherScroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//                @Override
//                public void onScrollChanged() {
//                    if (holder.weatherScroll != null) {
//                        holder.weatherScroll.setEnabled(holder.weatherScroll.getScrollY() == 0);
//                        Log.d("init scrollView","");
//                    }
//                }
//            });
//        }
//
//        holder.settingsButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
////                Toast.makeText(mynewWeatherActivity,".........",Toast.LENGTH_SHORT).show();
////                Log.d("settingsButton","delete");
////                for(Weather weather1:mynewWeatherActivity.weatherList){
////                    if(weather1.getCity()==holder.titleCity.getText()){
////                        mynewWeatherActivity.weatherList.remove(weather1);
////                        mynewWeatherActivity.sunnyWeatherDB.deleteWeather(weather1.getCity(),mynewWeatherActivity.weatherList);
////                        Log.d("settingsButton","");
////                        mynewWeatherActivity.horizontalAdapter.notifyDataSetChanged();
////                    }
////                }
//                Intent intent = new Intent(mynewWeatherActivity, MainActivity.class);
//                mynewWeatherActivity.startActivity(intent);
////            mynewWeatherActivity.horizontalAdapter.notifyDataSetChanged();
//             }
//        });
//        holder.freshButton.setOnClickListener(new View.OnClickListener(){ @Override public void onClick(View v) {  mynewWeatherActivity.requestWeather("",holder.titleCity.getText().toString()); }});

    }

    @Override
    public int getItemCount() {
        if(weatherList!=null) {
            return weatherList.size();
        }else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public class HorizontalAdapterViewHolder extends RecyclerView.ViewHolder {
        private NestedScrollView weatherScroll;     //普通Scroll无法下拉刷新
        private TextView degreeText;
        private TextView weather_info_text;
        private TextView air_text;
        private TextView wea_text;
        private TextView sunriseText;
        private TextView sundownText;
        private TextView winspeedText;
        private TextView winText;
        private TextView visibilityText;
        private TextView pressureText;
        private RecyclerView hours_recyclerView;
        private List<everyHour> hoursList;
        private RecyclerView index_recyclerView;
        private List<everyIndex> indexList;
        private List<everyData> dataList;
        private RecyclerView forecast_recyclerView;
        public SwipeRefreshLayout swipeRefreshLayout;
        private TextView humidityText;
        private Button navButton;
//        public DrawerLayout drawerLayout;   //public
        public TextView titleCity;
//        private TextView titleUpdateTime;
//        private Button settingsButton;
//        private Button freshButton;
        public HorizontalAdapterViewHolder(@NonNull  View itemView) {
            super(itemView);
            sunriseText = itemView.findViewById(R.id.sunriseText);
            sundownText = itemView.findViewById(R.id.sundownText);
            winspeedText = itemView.findViewById(R.id.win_speedText);
            winText = itemView.findViewById(R.id.winNameText);
            visibilityText = itemView.findViewById(R.id.visibilityText);
            pressureText = itemView.findViewById(R.id.pressureText);
            weatherScroll = itemView.findViewById(R.id.weather_layout);
            degreeText = itemView.findViewById(R.id.degree_text);
            weather_info_text = itemView.findViewById(R.id.weather_info_text);
            air_text = itemView.findViewById(R.id.air_text);
            wea_text = itemView.findViewById(R.id.wea_text);
            hours_recyclerView = itemView.findViewById(R.id.hours_recyclerView);
            index_recyclerView = itemView.findViewById(R.id.index_recyclerView);
            forecast_recyclerView = itemView.findViewById(R.id.forecast_recyclerview);
            humidityText = itemView.findViewById(R.id.humidityText);
            swipeRefreshLayout = itemView.findViewById(R.id.swipe_refresh);
            navButton = itemView.findViewById(R.id.nav_button);
//            drawerLayout = itemView.findViewById(R.id.drawer_layout);
            titleCity = itemView.findViewById(R.id.title_city);
//            titleUpdateTime = itemView.findViewById(R.id.title_updatetime);
//            settingsButton = itemView.findViewById(R.id.settings_button);
//            freshButton = itemView.findViewById(R.id.fresh_button);
        }
    }

}