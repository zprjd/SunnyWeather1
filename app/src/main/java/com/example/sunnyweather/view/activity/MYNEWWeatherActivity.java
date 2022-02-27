package com.example.sunnyweather.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMapException;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.bumptech.glide.Glide;
import com.example.sunnyweather.R;
import com.example.sunnyweather.VpSwipeRefreshLayout;
import com.example.sunnyweather.db.SunnyWeatherDB;
import com.example.sunnyweather.gson.Weather;
import com.example.sunnyweather.util.HttpCallbackListener;
import com.example.sunnyweather.util.HttpUtil;
import com.example.sunnyweather.util.Utility;
import com.example.sunnyweather.view.adapter.HorizontalAdapter;
import com.example.sunnyweather.view.handler.ViewHandler;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;

@SuppressLint("LongLogTag")
public class MYNEWWeatherActivity extends BaseAcvity implements AMapLocationListener,Serializable {
    private ImageView bingPicImg;
    private ProgressDialog progressDialog;
    public VpSwipeRefreshLayout swipeRefreshLayout;
//    public DrawerLayout drawerLayout;   //public
    public ViewPager2 viewPager2;
    public List<Weather> weatherList;
    public volatile Weather mweather;
    public HorizontalAdapter horizontalAdapter;
    public SunnyWeatherDB sunnyWeatherDB;
    public ViewHandler viewHandler;
    public TabLayout tabLayout;
    public TextView titleCity;
    private TextView titleUpdateTime;
    private Button settingsButton;
    private Button freshButton;
    private Button navButton;
    private String location_id;
    private String location_name;
    private String nowCounty;
    private AMapLocationListener aMapLocationListener;
    AMapLocationClient aMapLocationClient1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mynewweatheractivity);


        //初始化定位
        AMapLocationClient.updatePrivacyShow(this, true, true);
        AMapLocationClient.updatePrivacyAgree(this, true);
        //ActivityCollector.finishAll();
        viewHandler = new ViewHandler(MYNEWWeatherActivity.this);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        drawerLayout = findViewById(R.id.drawer_layout);
        viewPager2 = findViewById(R.id.mviewpager2);
        bingPicImg = findViewById(R.id.bing_pic_img);
        sunnyWeatherDB = SunnyWeatherDB.getInstance(this);
        tabLayout = findViewById(R.id.myTabLayout);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        titleCity = findViewById(R.id.title_city);
        titleUpdateTime = findViewById(R.id.title_updatetime);
        settingsButton = findViewById(R.id.settings_button);
//        freshButton = findViewById(R.id.fresh_button);
        navButton = findViewById(R.id.nav_button);
//            String weatherString = PreferenceManager.getDefaultSharedPreferences(this).getString("weatherString", null);
//            if (weatherString != null) {
//                try {
//                    mweather = Utility.handleWeatherResponse(weatherString);
//                    Log.d("WeatherActivityonCreate", weatherString);
//                    try {
//                        weatherList = sunnyWeatherDB.findAllWeathers();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    horizontalAdapter = new HorizontalAdapter(this, weatherList);
//                    showWeatherInfo(mweather);
//                    sunnyWeatherDB.saveWeather(weatherString);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            } else {
//        initWeatherView();
////        try {
////            weatherList  = sunnyWeatherDB.findAllWeathers("");
////        } catch (JSONException e) {
////            e.printStackTrace();
////        }
//        Intent intent = getIntent();    //一定是getIntent()
//        String countyName = intent.getStringExtra("countyName");
//        Log.d("WeatherActivityonCreate", "weatherString = " + countyName);
//        if(countyName == null){
//            requestWeather("北京","");
//        }else {
//            requestWeather(countyName,"");
//        }
//        Message message = Message.obtain();
//        message.what = 1;
//        viewHandler.sendMessage(message);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MYNEWWeatherActivity.this,SettingActivity.class);
                Log.d("settingsButton", String.valueOf(weatherList.size()));
                intent.putExtra("weatherList",(Serializable)weatherList);
                intent.putExtra("location_name",location_name);
                startActivity(intent);
            }
        });
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mynewWeatherActivity,".........",Toast.LENGTH_SHORT).show();
//                Log.d("settingsButton","delete");
//                for(Weather weather1:mynewWeatherActivity.weatherList){
//                    if(weather1.getCity()==holder.titleCity.getText()){
//                        mynewWeatherActivity.weatherList.remove(weather1);
//                        mynewWeatherActivity.sunnyWeatherDB.deleteWeather(weather1.getCity(),mynewWeatherActivity.weatherList);
//                        Log.d("settingsButton","");
//                        mynewWeatherActivity.horizontalAdapter.notifyDataSetChanged();
//                    }
//                }
                Intent intent = new Intent(MYNEWWeatherActivity.this, SearchActivity.class);
                startActivity(intent);
//            mynewWeatherActivity.horizontalAdapter.notifyDataSetChanged();
            }
        });
//        freshButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                requestWeather(titleCity.getText().toString(), "1");
//            }
//        });
        aMapLocationListener = new AMapLocationListener() {

            @SuppressLint("LongLogTag")
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation.getErrorCode() == 0) {
                    location_id = aMapLocation.getLongitude() + "," + aMapLocation.getLatitude();
                    location_name = aMapLocation.getDistrict();
                    Log.d("onLocationChanged,getlocation", location_id);
                    Log.d("onLocationChanged,getlocation", location_name);
                    location_name = location_name.replace("区","");
                    location_name = location_name.replace("市","");
                    Log.d("onLocationChanged,getlocation1", location_id);
                    Log.d("onLocationChanged,getlocation2", location_name);
                    requestWeather(location_name,"");
                    //requestWeather(location_name,"");
                    //nowCounty.setText(location_name);
//                            url_24H = "https://devapi.qweather.com/v7/weather/24h?location=" + location_id + "&key=a5cf6ab782a14013b08fb92a57dd2f72";
//                            loadMessage(TYPE_DAY_24H);
//                            url_day_3 = "https://devapi.qweather.com/v7/weather/3d?location=" + location_id + "&key=a5cf6ab782a14013b08fb92a57dd2f72";
//                            loadMessage(TYPE_DAY_3);
//                            url_day_now = "https://devapi.qweather.com/v7/weather/now?location=" + location_id + "&key=a5cf6ab782a14013b08fb92a57dd2f72";
//                            loadMessage(TYPE_DAY_NOW);
                } else {
                    location_id = aMapLocation.getLongitude() + "," + aMapLocation.getLatitude();
                    location_name = aMapLocation.getDistrict();
                    String aa = location_name.replace("区","");
                    String aa2 = aa.replace("市","");
                    boolean b = isLocationEnabled(MYNEWWeatherActivity.this);
                    Toast.makeText(MYNEWWeatherActivity.this, "获取地理位置错误", Toast.LENGTH_SHORT).show();
                    Log.d("onLocationChanged,getlocatione", "error" + b + aMapLocation.getErrorCode());
                }
            }
        };
        String  s = sHA1(MYNEWWeatherActivity.this);
        if (ContextCompat.checkSelfPermission(MYNEWWeatherActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(MYNEWWeatherActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(MYNEWWeatherActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(MYNEWWeatherActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(MYNEWWeatherActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(MYNEWWeatherActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
            }, 1);//32:53:18:E0:A3:D3:E5:97:68:D8:F4:34:9C:0B:4D:7D:81:4E:3B:1F
            Log.d("getlocation",s+"11111"); //32:53:18:E0:A3:D3:E5:97:68:D8:F4:34:9C:0B:4D:7D:81:4E:3B:1F
        }else {
            Log.d("getlocation",s+"22222");//15:36:E5:2F:E6:EF:9D:30:50:BC:BA:CB:A9:9F:C1:18:B2:29:6F:96:22:22:2
            getLocation();//15:36:E5:2F:E6:EF:9D:30:50:BC:BA:CB:A9:9F:C1:18:B2:29:6F:96:22:22:2
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            case 1:
                if(grantResults.length > 0) {
                    for (int grantResult : grantResults) {
                        Log.d("getlocation3333", String.valueOf(grantResult));
                        if(grantResult != PackageManager.PERMISSION_GRANTED) {
                            Log.d("getlocation", String.valueOf(grantResults.length));
                            Toast.makeText(MYNEWWeatherActivity.this, "已经拒绝访问", Toast.LENGTH_SHORT).show();
                            //finish();
                            return ;
                        }
                    }
                    getLocation();
                }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bar,menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        horizontalAdapter = new HorizontalAdapter(this, weatherList);
        Intent intent = getIntent();    //一定是getIntent()
        String countyName = intent.getStringExtra("countyName");        //
        Log.d("WeatherActivityonCreate", "weatherString = " + countyName);
        if(countyName != null){
            requestWeather(countyName,"");
            horizontalAdapter.notifyDataSetChanged();
            intent.removeExtra("countyName");
        }else {
//            String weatherstr = PreferenceManager.getDefaultSharedPreferences(this).getString("weatherString",null);
//            try {
//                if(weatherstr!=null) {
//                    mweather = Utility.handleWeatherResponse(weatherstr);
//                    requestWeather(mweather.getCity(),"");
//                }
//                //else
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
            try {
                weatherList = sunnyWeatherDB.findAllWeathers("");
                if(location_name!=null) {
                    requestWeather(location_name, "");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        initWeatherInfo();
        //ACCESS_FINE_LOCATION
    }

    public void initWeatherInfo() {         //打印weatherlist信息
        try {
            weatherList = sunnyWeatherDB.findAllWeathers("");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(!weatherList.isEmpty()){
            Log.d("initWeatherViewweatherlist", String.valueOf(weatherList.size()));
            for (Weather weather:weatherList) {
                Log.d("initWeatherViewweatherListre",weather.getCity());
            }
        }else {
            Log.d("initWeatherViewweatherlist", " is null");
        }
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mweather = weatherList.get(position);
                Log.d("onPageSelected",mweather.getCity());
                titleCity.setText(mweather.getCity());          //标题不在碎片中
                titleUpdateTime.setText(mweather.getUpdate_time());
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
        //horizontalAdapter.notifyDataSetChanged();
        Log.d("horizontalAdapter4","4");
        Log.d("initWeatherView","finished");
    }

    public void requestWeather(String countyName,String para1) {
        String address = "https://tianqiapi.com/api?unescape=1&version=v1&appid=76623742&appsecret=t371QAyF&city=" + countyName;
        Log.d("requestWeatheraddress", address);
        showProgressDialog();
        HttpUtil.sendRequestWithHttpURLConnection(address, new HttpCallbackListener() {
            @Override
            public void onFinsh(String response) throws JSONException {
                mweather = Utility.handleWeatherResponse(response);
                Log.d("requestweather",mweather.getCity());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = Message.obtain();
                        if(para1=="") {
                            message.what = 1;       //新加入城市
                        }else {
                            message.what = 2;
                        }
                        message.obj = response;
                        viewHandler.sendMessage(message);
                    }
                }).start();
            }
            @Override
            public void onError(Exception e) {
                Log.e("requestWeather", "start");
                e.printStackTrace();
                Log.e("requestWeather", "thinking");
                closeProgressDialog();
                //Android是不能直接在子线程中弹出Toast的
                if (!mweather.getCity().equals("")) {
                    runOnUiThread(() -> Toast.makeText(MYNEWWeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show());
                } else {
                    runOnUiThread(() -> {
                                Toast.makeText(MYNEWWeatherActivity.this, "第一次使用天气预报加载城市请先打开网络！！", Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder dialog = new AlertDialog.Builder(MYNEWWeatherActivity.this);
                                dialog.setTitle("无天气信息");
                                dialog.setMessage("第一次使用天气预报加载城市请先打开网络！！点击我知道了按钮APP会退出，点击我想看背景按钮可以继续对着背景发呆。");
                                dialog.setCancelable(false);
                                dialog.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                });
                                dialog.setNegativeButton("我想看背景", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                                dialog.show();
                            }
                    );
                }
//                swipeRefreshLayout.setRefreshing(false);
            }
        });
        loadBingPic();
    }

    private void loadBingPic() {
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendRequestWithHttpURLConnection(requestBingPic, new HttpCallbackListener() {
            @Override
            public void onFinsh(String response) throws JSONException {
                final String[] bingPic = {response};
                Log.d("loadBingPic", response);
                runOnUiThread(() -> {
//                    if (bingPic[0].startsWith("http://")){
//                        bingPic[0] = bingPic[0].replace("http://", "https://");
//                    }
//                    final String bingPic_a = bingPic[0];
                    Glide.with(MYNEWWeatherActivity.this)
                            .load(response)
                            .into(bingPicImg);
                });
                Log.d("loadBingPic", "finished");
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                Log.d("loadBingPic", "failed");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //bingPicImg.setImageResource(R.drawable.background);
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载天气...");
            progressDialog.setCanceledOnTouchOutside(false);//点击外部不能取消
        }
        progressDialog.show();
    }

    public void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

    }

    private void getLatlon(String cityName) throws AMapException {
        GeocodeSearch geocodeSearch= null;
        try {
            geocodeSearch = new GeocodeSearch(this);
        } catch (com.amap.api.services.core.AMapException e) {
            e.printStackTrace();
        }
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
            }
            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                if (i==1000){
                    if (geocodeResult!=null && geocodeResult.getGeocodeAddressList()!=null &&
                            geocodeResult.getGeocodeAddressList().size()>0){
                        GeocodeAddress geocodeAddress = geocodeResult.getGeocodeAddressList().get(0);
                        double latitude = geocodeAddress.getLatLonPoint().getLatitude();//纬度
                        double longititude = geocodeAddress.getLatLonPoint().getLongitude();//经度
                        String adcode= geocodeAddress.getAdcode();//区域编码
                        Log.e("lgq地理编码", geocodeAddress.getAdcode()+"");
                        Log.e("lgq纬度latitude",latitude+"");
                        Log.e("lgq经度longititude",longititude+"");
                        Log.i("lgq","dddwww===="+longititude);
                    }else {
                        Toast.makeText(MYNEWWeatherActivity.this,"地名出错",Toast.LENGTH_SHORT).show();
//                        ToastUtils.show(context,"地址名出错");
                    }
                }
            }
        });
        GeocodeQuery geocodeQuery=new GeocodeQuery(cityName.trim(),"29");
        geocodeSearch.getFromLocationNameAsyn(geocodeQuery);
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    public void getLocation() {
        try {
            aMapLocationClient1 = new AMapLocationClient(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {



                AMapLocationClientOption option = new AMapLocationClientOption();

                option.setHttpTimeOut(20000);
                option.setInterval(10000);
                option.setOnceLocation(true);
                option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

                aMapLocationClient1.setLocationListener(aMapLocationListener);
                aMapLocationClient1.setLocationOption(option);
                aMapLocationClient1.stopLocation();
                aMapLocationClient1.startLocation();
                Log.d("getlocation", "run: ");
//            }
//        }).start();
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("SHA1");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(appendString);
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < hexString.length(); i += 2) {
                stringBuilder.append(hexString.subSequence(i, i + 2))
                        .append(":");
            }
            return stringBuilder.substring(0, stringBuilder.length() - 1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}