package com.venus.assistant.Utilise;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.venus.assistant.Weather.Entities.City;
import com.venus.assistant.Weather.Entities.County;
import com.venus.assistant.Weather.Entities.Province;
import com.venus.assistant.Weather.Entities.WeatherInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {

    public static WeatherInfo handleWeatherResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            Gson gson=new Gson();
            WeatherInfo weatherInfo= gson.fromJson(response,WeatherInfo.class);
            return weatherInfo;

        }
        return new WeatherInfo();
    }


    public static boolean handleProvinceResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allProvince = new JSONArray(response);
                for (int i = 0; i < allProvince.length(); i++) {
                    JSONArray provinceObject = allProvince.getJSONArray(i);
                    Province province = new Province();
                    province.setProvinceCode(provinceObject.getString(1));
                    province.setProvinceName(provinceObject.getString(0));
                    province.save();
                }
                return true;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handleCityResponse(String response, String provinceId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCity = new JSONArray(response);
                for (int i = 0; i < allCity.length(); i++) {
                    JSONArray cityObject = allCity.getJSONArray(i);
                    City city = new City();
                    city.setCityCode(cityObject.getString(1));
                    city.setCityName(cityObject.getString(0));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handleCountyResponse(String response, String cityId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCounty = new JSONArray(response);
                for (int i = 0; i < allCounty.length(); i++) {
                    JSONArray countyObject = allCounty.getJSONArray(i);
                    County county = new County();
                    county.setWeatherId(countyObject.getString(1));
                    county.setCountyName(countyObject.getString(0));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
