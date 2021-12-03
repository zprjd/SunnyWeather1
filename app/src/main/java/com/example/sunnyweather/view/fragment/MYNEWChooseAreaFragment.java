package com.example.sunnyweather.view.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sunnyweather.R;
import com.example.sunnyweather.db.SunnyWeatherDB;
import com.example.sunnyweather.loadfile.loadFile;
import com.example.sunnyweather.model.City;
import com.example.sunnyweather.model.County;
import com.example.sunnyweather.model.Province;
import com.example.sunnyweather.view.activity.ActivityCollector;
import com.example.sunnyweather.view.activity.MYNEWWeatherActivity;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MYNEWChooseAreaFragment extends Fragment {
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;
    private ProgressDialog progressDialog;
    private TextView titleText;
    private Button backButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<>();
    private List<Province> provinceList;
    private List<City> cityList;
    private List<County> countyList;
    private Province selectedProvince;//选中的省份
    private City selectedCity;
    private int currentLevel;
    private SunnyWeatherDB sunnyWeatherDB;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_areafrag,container,false);//碎片要加载的布局，父容器，不与父容器连接
        titleText = view.findViewById(R.id.title_textinchoose);
        backButton = view.findViewById(R.id.back_button);
        listView = view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);
        sunnyWeatherDB = SunnyWeatherDB.getInstance(getContext());
        searchView = view.findViewById(R.id.search_My);
        Log.d("onCreateView","1");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        try {
            countyList = sunnyWeatherDB.findAllCountiesInSelectedCity("漳州");
            if(countyList.isEmpty()) {
                queryFromFile("all");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        listView.setOnItemClickListener((adapterView, view, position, id) -> {
//            if(currentLevel == LEVEL_PROVINCE){
//                selectedProvince = provinceList.get(position);
//                Log.d("onActivityCreated","selectedProvince"+selectedProvince.getProvinceName());
//                MYNEWqueryAndLoadCities();
//            }else if(currentLevel == LEVEL_CITY){
//                selectedCity = cityList.get(position);
//                MYNEWqueryAndLoadCounties();
//            }else if(currentLevel == LEVEL_COUNTY){
//                String countyName = countyList.get(position).getCountyName();
//                if(getActivity() instanceof MainActivity) {//判断碎片对象属于某个类的实例
//                    Intent intent = new Intent(getActivity(), MYNEWWeatherActivity.class);
//                    intent.putExtra("countyName", countyName);
//                    Log.d("MYNEWChooseAreaFragment", "即将显示" + countyName + "天气");
//                    startActivity(intent);
//                    getActivity().finish();
//                }
//                else if(getActivity() instanceof MYNEWWeatherActivity){
//                    Log.d("MYNEWChooseAreaFragment", "111");
//                    MYNEWWeatherActivity  mynewWeatherActivity= (MYNEWWeatherActivity) getActivity();
////                    mynewWeatherActivity.drawerLayout.closeDrawers();
//                    //mynewWeatherActivity.swipeRefreshLayout.setRefreshing(true);
//                    mynewWeatherActivity.requestWeather(countyName,"");
//                }
//            }
            String countyName = countyList.get(position).getCountyName();
            Intent intent = new Intent(getActivity(), MYNEWWeatherActivity.class);
            intent.putExtra("countyName", countyName);
            Toast.makeText(getActivity(), "你的选择是：" + countyName,
                    Toast.LENGTH_SHORT).show();
            Log.d("MYNEWChooseAreaFragment", "即将显示" + countyName + "天气");
            startActivity(intent);
            getActivity().finish();
            ActivityCollector.finishAll();
        });
        backButton.setOnClickListener(view -> {
            getActivity().finish();
        });
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(false);//搜索框展开时显示提交按钮
        searchView.setQueryHint("查找");
        searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);//键盘回车设置成搜索
        searchView.setQueryHint("搜索全国天气");//请输入关键字
        searchView.setIconifiedByDefault(false);//搜索图标显示在搜索框内
        searchView.onActionViewExpanded();//默认展开搜索框
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {    //设置搜索文本监听
            //单机搜索按钮时激发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {        //当点击搜索按钮触发
                //实际应用中应该在该方法内执行实际查询，此处仅使用Toast显示用户输入的查询内容
                Toast.makeText(getActivity(), "你的选择是：" + query,
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MYNEWWeatherActivity.class);
                intent.putExtra("countyName", query);
                Log.d("MYNEWChooseAreaFragment", "即将显示" + query + "天气");
                startActivity(intent);
                getActivity().finish();
                searchView.clearFocus();
                return false;
            }

            //用户输入字符时激发该方法
            @Override
            public boolean onQueryTextChange(String newText) {       //搜索内容改变时触发
                try {
                    if(!TextUtils.isEmpty(newText)) {
                        countyList = sunnyWeatherDB.searchCounty(newText);
                        dataList.clear();
                        for (County county : countyList) {
                            if (dataList.contains(county.getCountyName()) == false) {
                                dataList.add(county.getProvinceName()+","+county.getCityName()+","+county.getCountyName());
                                //Log.d("MYNEWqueryAndLoadCounties",county.getCountyName());
                            }
                        }
                        adapter.notifyDataSetChanged();
                        listView.setSelection(0);//?
                    }
                    else {
                        dataList.clear();
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
        Log.d("onActivityCreated","1");
        MYNEWqueryAndLoadProvinces();
        Log.d("onActivityCreated","2");
    }

    private void MYNEWqueryAndLoadProvinces(){
        titleText.setText("中国");
        backButton.setVisibility(View.GONE);
        provinceList = sunnyWeatherDB.findAllProvinces();
//        if(provinceList.size()==0){
//            try {
//                //queryFromFile("province");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            provinceList = sunnyWeatherDB.findAllProvinces();
//        }
        dataList.clear();
        for(Province province : provinceList){
            if(dataList.contains(province.getProvinceName()) == false)      dataList.add(province.getProvinceName());
        }
        adapter.notifyDataSetChanged();
        listView.setSelection(0);//?
        currentLevel = LEVEL_PROVINCE;
    }

    private void MYNEWqueryAndLoadCities() {
        titleText.setText(selectedProvince.getProvinceName());
        Log.d("MYNEWqueryAndLoadCities", "selectedProvince"+selectedProvince.getProvinceName());
        backButton.setVisibility(View.VISIBLE);
        cityList = sunnyWeatherDB.findAllCitiesInSlecetedProvince(selectedProvince.getProvinceName());
//        if (cityList.isEmpty()) {
//            new Thread() {
//                @Override
//                public void run() {
//                    try {
//                        //queryFromFile("city");//找不到加载所有市到数据库
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    getActivity().runOnUiThread(() -> {
//                        Log.d("MYNEWqueryAndLoadCities","00");
//                        adapter.notifyDataSetChanged();
//                        listView.setSelection(0);
//                        currentLevel = LEVEL_COUNTY;
//                    });
//                }
//            }.start();
//            cityList = sunnyWeatherDB.findAllCitiesInSlecetedProvince(selectedProvince.getProvinceName());
//        }
        dataList.clear();

        for (City city : cityList) {
            dataList.add(city.getCityName());
            //Log.d("MYNEWqueryAndLoadCities","dataList加入了City"+city.getCityName());
        }

        adapter.notifyDataSetChanged();
        listView.setSelection(0);
        currentLevel = LEVEL_CITY;

//        if (dataList.isEmpty() == false) {
//            Log.d("MYNEWqueryAndLoadCities", "finish"+dataList.get(0));
//        }else {
//            Log.d("MYNEWqueryAndLoadCities","failed");
//        }
    }

    private void MYNEWqueryAndLoadCounties() {
        titleText.setText(selectedCity.getCityName());
        backButton.setVisibility(View.VISIBLE);
        Log.d("MYNEWqueryAndLoadCounties","1");
        countyList = sunnyWeatherDB.findAllCountiesInSelectedCity(selectedCity.getCityName());
        Log.d("MYNEWqueryAndLoadCounties","2");
//        if(countyList.isEmpty()) {
//            new Thread() {
//                @Override
//                public void run() {
//                    try {
//                        Log.d("MYNEWqueryAndLoadCounties","0");
//                        //queryFromFile("county");//找不到加载所有市到数据库
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Log.d("MYNEWqueryAndLoadCounties","00");
//                                adapter.notifyDataSetChanged();
//                                listView.setSelection(0);
//                                currentLevel = LEVEL_COUNTY;
//                            }
//                        });
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }.start();
//            Log.d("MYNEWqueryAndLoadCounties","3");
//            countyList = sunnyWeatherDB.findAllCountiesInSelectedCity(selectedCity.getCityName());
//        }
        dataList.clear();
        Log.d("MYNEWqueryAndLoadCounties","4");
        for (County county : countyList) {
            if (dataList.contains(county.getCountyName()) == false) {
                dataList.add(county.getCountyName());
                //Log.d("MYNEWqueryAndLoadCounties",county.getCountyName());
            }
        }
        Log.d("MYNEWqueryAndLoadCounties","5");
        adapter.notifyDataSetChanged();
        listView.setSelection(0);
        currentLevel = LEVEL_COUNTY;
//        if (dataList.isEmpty() == false) {
//            Log.d("dataList加入了County", countyList.get(0).getCountyName() +","+ dataList.get(0));
//        }else {
//            Log.d("MYNEWqueryAndLoadCounties","failed");
//        }
    }

    private void queryFromFile(final String type) throws IOException {
        final boolean[] result = {false};
//        if("province".equals(type)){
//            Log.d("queryFromFile","province");
//            result = loadFile.loadProvinceInFile(sunnyWeatherDB,getActivity());
//        }else if("city".equals(type)){
//            Log.d("queryFromFile","city");
//            result = loadFile.loadAllCitiesInFile(sunnyWeatherDB,getActivity());
//        }else if("county".equals(type)){
//            Log.d("queryFromFile","county");
//            result = loadFile.loadCountyInFile(sunnyWeatherDB,getActivity());
//        }else if("all".equals(type)){
//            Log.d("queryFromFile","all");
//            result = loadFile.loadAllInFile(sunnyWeatherDB,getActivity());
//        }
        showProgressDialog();
        if ("all".equals(type)) {
            Log.d("queryFromFile", "all");
            Handler handler = new Handler();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                showProgressDialog();
                            }
                        });
                        result[0] = loadFile.loadAllInFile(sunnyWeatherDB, getActivity());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                closeProgressDialog();
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
        Log.d("queryFromFile", "finish");
        if(result[0] == true)      closeProgressDialog();
    }

    private void showProgressDialog(){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);//点击外部不能取消
        }
        progressDialog.show();
    }
    private void closeProgressDialog(){
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }
}
