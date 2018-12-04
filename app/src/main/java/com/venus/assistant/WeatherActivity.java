package com.venus.assistant;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.venus.assistant.Utilise.Datetime;
import com.venus.assistant.Weather.Entities.Weather;
import com.venus.assistant.Weather.Entities.WeatherInfo;
import com.venus.assistant.Utilise.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.venus.assistant.Utilise.Utility.handleWeatherResponse;

public class WeatherActivity extends AppCompatActivity {

    private final String weatherUrl="http://tq.360.cn/api/weatherquery/querys?app=tq360&code=%s&t=%s&c=%s&_jsonp=getData&_=%s";
    private ScrollView weatherLayout;
    private TextView titleCity;
    private TextView titleUpdateTime;
    private TextView temperatureText;
    private TextView weatherInfoText;
    private LinearLayout forecastLayout;
    private TextView airQuality;
    private TextView aqiText;
    private TextView pm25Text;
    private TextView comfortText;
    private TextView sportText;
    private TextView clothText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        weatherLayout=(ScrollView)findViewById(R.id.weather_layout);
        titleCity=(TextView)findViewById(R.id.title_city);
        titleUpdateTime=(TextView)findViewById(R.id.title_update_time);
        temperatureText=(TextView)findViewById(R.id.weather_temperature_text);
        weatherInfoText=(TextView)findViewById(R.id.weather_info_text);
        forecastLayout=(LinearLayout)findViewById(R.id.weather_forecast_layout);
        airQuality=(TextView)findViewById(R.id.air_quality_text);
        aqiText=(TextView)findViewById(R.id.aqi_text);
        pm25Text=(TextView)findViewById(R.id.pm25_text);
        comfortText=(TextView)findViewById(R.id.comfort_text);
        sportText=(TextView)findViewById(R.id.spont_text);
        clothText=(TextView)findViewById(R.id.cloth_text);

        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);

        String weatherString=prefs.getString("weather",null);

//        if(weatherString!=null){
//            WeatherInfo weatherInfo=handleWeatherResponse(weatherString);
//            showWeatherInfo(weatherInfo);
//        }else{
//            String weatherId=getIntent().getStringExtra("weather_id");
//            weatherLayout.setVisibility(View.INVISIBLE);
//            requestWeather(weatherId);
//        }
        String weatherId=getIntent().getStringExtra("weather_id");
        weatherLayout.setVisibility(View.INVISIBLE);
        requestWeather(weatherId);

    }

    private void requestWeather(String weatherId) {
        long startTimestamp=Datetime.getTimeStamp();
        long endTimestamp=startTimestamp+1;
        HttpUtil.sendOkHttpRequest(String.format(weatherUrl, weatherId, String.valueOf(startTimestamp), String.valueOf(endTimestamp), String.valueOf(startTimestamp)), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this,"Get Weather Data Failed!",Toast.LENGTH_SHORT).show();
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
                    }
                });
            }
        });

    }

    private void showWeatherInfo(WeatherInfo weatherInfo) {
        String cityName= String.valueOf(weatherInfo.getArea().get(weatherInfo.getArea().size()-1));
        String updateTime=weatherInfo.getRealtime().getTime();
        String temperature=weatherInfo.getRealtime().getWeather().getTemperature();
        String weatherName=weatherInfo.getRealtime().getWeather().getInfo();
        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
        temperatureText.setText(temperature);
        weatherInfoText.setText(weatherName);

        forecastLayout.removeAllViews();

        for(Weather weather:weatherInfo.getWeather()){
            View view=LayoutInflater.from(this).inflate(R.layout.weather_forecast_item,forecastLayout,false);
            TextView dateText=(TextView) view.findViewById(R.id.date_text);
            TextView infoText=(TextView) view.findViewById(R.id.info_text);
            TextView maxText=(TextView) view.findViewById(R.id.max_text);
            TextView minText=(TextView) view.findViewById(R.id.min_text);

            dateText.setText(weather.getDate());
            infoText.setText(weather.getInfo().getNight().get(1));
            maxText.setText(weather.getInfo().getNight().get(2));
            minText.setText(weather.getInfo().getNight().get(0));
            forecastLayout.addView(view);

        }
        airQuality.setText(weatherInfo.getPm25().getQuality());
        aqiText.setText(weatherInfo.getPm25().getAqi()+"");
        pm25Text.setText(weatherInfo.getPm25().getPm25()+"");

        comfortText.setText(weatherInfo.getLife().getInfo().getKongtiao().get(1));
        sportText.setText(weatherInfo.getLife().getInfo().getYundong().get(1));
        clothText.setText(weatherInfo.getLife().getInfo().getChuanyi().get(1));

        weatherLayout.setVisibility(View.VISIBLE);

    }
}
