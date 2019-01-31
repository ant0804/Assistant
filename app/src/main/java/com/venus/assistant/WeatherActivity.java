package com.venus.assistant;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.venus.assistant.Utilise.Datetime;
import com.venus.assistant.Weather.Entities.HourlyForecast;
import com.venus.assistant.Weather.Entities.Weather;
import com.venus.assistant.Weather.Entities.WeatherInfo;
import com.venus.assistant.Utilise.HttpUtil;
import com.venus.assistant.databinding.ActivityWeatherBinding;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.venus.assistant.Utilise.Utility.handleWeatherResponse;

public class WeatherActivity extends AppCompatActivity {

    private ActivityWeatherBinding binding;
    private IntentFilter intentFilter;

    private NetworkChangeReceiver networkChangeReceiver;

    private final String weatherUrl="http://tq.360.cn/api/weatherquery/querys?app=tq360&code=%s&t=%s&c=%s&_jsonp=getData&_=%s";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intentFilter=new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver=new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver,intentFilter);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        // setContentView(R.layout.activity_weather);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather);
        binding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString=prefs.getString("weather",null);

        final String weatherId;

//        if(weatherString!=null){
//            WeatherInfo weatherInfo=handleWeatherResponse(weatherString);
//            weatherId=weatherInfo.getArea().get(weatherInfo.getArea().size()-1).get(1);
//            showWeatherInfo(weatherInfo);
//        }else{
//            weatherId=getIntent().getStringExtra("weather_id");
//            weatherLayout.setVisibility(View.INVISIBLE);
//            requestWeather(weatherId);
//        }
        weatherId = getIntent().getStringExtra("weather_id");
        binding.weatherLayout.setVisibility(View.INVISIBLE);
        requestWeather(weatherId);

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(weatherId);
            }
        });

        String weatherBackground = prefs.getString("weatherBackground", null);
        if (weatherBackground != null) {
            Glide.with(this).load(weatherBackground).into(binding.weatherBackground);
        } else {
            loadWeatherBackground();
        }


    }

    private void loadWeatherBackground() {
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "Get Weather Data Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPicUrl = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                editor.putString("weatherBackground", bingPicUrl);
                editor.apply();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(bingPicUrl).into(binding.weatherBackground);

                    }
                });
            }
        });
    }

    private void requestWeather(String weatherId) {
        long startTimestamp=Datetime.getTimeStamp();
        long endTimestamp = startTimestamp + Integer.valueOf(weatherId);
        HttpUtil.sendOkHttpRequest(String.format(weatherUrl, weatherId, String.valueOf(startTimestamp), String.valueOf(endTimestamp), String.valueOf(endTimestamp)), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this,"Get Weather Data Failed!",Toast.LENGTH_SHORT).show();
                        binding.swipeRefresh.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resopnseText=response.body().string().replace("getData","").trim();
                final String finalResopnseText=resopnseText.substring(1,resopnseText.length()-1);
                WeatherInfo weatherInfo= handleWeatherResponse(finalResopnseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(weatherInfo!=null){
                            SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather",finalResopnseText);
                            editor.apply();
                            showWeatherInfo(weatherInfo);

                        }else{
                            Toast.makeText(WeatherActivity.this,"Get Weather Data Failed!",Toast.LENGTH_SHORT).show();
                        }
                        binding.swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });
        loadWeatherBackground();

    }

    private void showWeatherInfo(WeatherInfo weatherInfo) {
        String cityName = weatherInfo.getArea().get(weatherInfo.getArea().size() - 1).get(0);
        String updateTime=weatherInfo.getRealtime().getTime();
        String temperature = weatherInfo.getRealtime().getWeather().getTemperature() + "℃";
        String humidity = weatherInfo.getRealtime().getWeather().getHumidity() + "%";
        String weatherName=weatherInfo.getRealtime().getWeather().getInfo();
        binding.weatherTitleLayout.titleCity.setText(cityName);
        binding.weatherTitleLayout.titleUpdateTime.setText(updateTime);
        binding.weatherRealtimeLayout.weatherTemperatureText.setText(temperature);
        binding.weatherRealtimeLayout.weatherHumidityText.setText(humidity);
        binding.weatherRealtimeLayout.weatherInfoText.setText(weatherName);

        //dailyForecastLayout.removeAllViews();
        binding.weatherForecastLayout.weatherDailyForecastLinearlayout.removeAllViews();

        for(Weather weather:weatherInfo.getWeather()){
            View view=LayoutInflater.from(this).inflate(R.layout.weather_forecast_item, binding.weatherForecastLayout.weatherDailyForecastLinearlayout,false);
            TextView dateText=(TextView) view.findViewById(R.id.date_text);
            TextView infoText=(TextView) view.findViewById(R.id.info_text);
            TextView maxText=(TextView) view.findViewById(R.id.max_text);
            TextView minText=(TextView) view.findViewById(R.id.min_text);


            dateText.setText(weather.getDate());
            infoText.setText(weather.getInfo().getNight().get(1));
            maxText.setText(weather.getInfo().getNight().get(2) + "℃");
            minText.setText(weather.getInfo().getNight().get(0) + "℃");
            //dailyForecastLayout.addView(view);
            binding.weatherForecastLayout.weatherDailyForecastLinearlayout.addView(view);
        }
        //weatherHourForecastLayout.removeAllViews();
        binding.weatherHourForecastLayout.weatherHoursForecastLinearlayout.removeAllViews();
        for (HourlyForecast hourlyForecast : weatherInfo.getHourlyForecast()) {
            //View view = LayoutInflater.from(this).inflate(R.layout.weather_hour_forecast_item, weatherHourForecastLayout, false);
            View view = LayoutInflater.from(this).inflate(R.layout.weather_hour_forecast_item, binding.weatherHourForecastLayout.weatherHoursForecastLinearlayout, false);
            TextView hourText = (TextView) view.findViewById(R.id.hour_text);
            TextView hourInfoText = (TextView) view.findViewById(R.id.hour_info_text);
            TextView hourTemperatureText = (TextView) view.findViewById(R.id.hour_temperature_text);
            TextView windDirectText = (TextView) view.findViewById(R.id.wind_direct_text);
            TextView windSpeedText = (TextView) view.findViewById(R.id.wind_speed_text);

            hourText.setText(hourlyForecast.getHour());
            hourInfoText.setText(hourlyForecast.getInfo());
            hourTemperatureText.setText(hourlyForecast.getTemperature() + "℃");
            windDirectText.setText(hourlyForecast.getWindDirect());
            windSpeedText.setText(hourlyForecast.getWindSpeed() + "km/h");
            //weatherHourForecastLayout.addView(view);
            binding.weatherHourForecastLayout.weatherHoursForecastLinearlayout.addView(view);

        }

        binding.weatherAqiLayout.airQualityText.setText(weatherInfo.getPm25().getQuality());
        binding.weatherAqiLayout.aqiText.setText(weatherInfo.getPm25().getAqi()+"");
        binding.weatherAqiLayout.pm25Text.setText(weatherInfo.getPm25().getPm25()+"");

        binding.weatherSuggestionLayout.comfortText.setText(weatherInfo.getLife().getInfo().getKongtiao().get(1));
        binding.weatherSuggestionLayout.spontText.setText(weatherInfo.getLife().getInfo().getYundong().get(1));
        binding.weatherSuggestionLayout.clothText.setText(weatherInfo.getLife().getInfo().getChuanyi().get(1));

        binding.weatherLayout.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }

    class NetworkChangeReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

            Toast.makeText(context,"network changes",Toast.LENGTH_SHORT).show();
        }
    }
}
