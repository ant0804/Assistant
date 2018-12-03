package com.venus.assistant;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.venus.assistant.Utilise.Datetime;
import com.venus.assistant.Weather.Entities.City;
import com.venus.assistant.Weather.Entities.County;
import com.venus.assistant.Weather.Entities.Province;


import org.litepal.LitePal;
import org.litepal.LitePalApplication;
import org.litepal.LitePalDB;
import org.litepal.crud.LitePalSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.security.auth.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

import static com.venus.assistant.Utilise.HttpUtil.sendOkHttpRequest;
import static com.venus.assistant.Utilise.Utility.handleCityResponse;
import static com.venus.assistant.Utilise.Utility.handleCountyResponse;
import static com.venus.assistant.Utilise.Utility.handleProvinceResponse;

public class ChooseAreaFragment extends Fragment {

    final private String getProvinceUrl="http://cdn.weather.hao.360.cn/sed_api_area_query.php?grade=province&_jsonp=getData&_=%x";
    final private String getCityUrl="http://cdn.weather.hao.360.cn/sed_api_area_query.php?grade=city&_jsonp=getData&code=%s&_=%x";
    final private String getTownUrl="http://cdn.weather.hao.360.cn/sed_api_area_query.php?grade=town&_jsonp=getData&code=%s%s&_=%x";

    public static final int LEVEL_PROVINCE =0;
    public static final int LEVEL_CITY=1;
    public static final int LEVEL_COUNTY=2;

    private ProgressDialog progressDialog;
    private TextView titleText;
    private Button backButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList=new ArrayList<>();

    private List<Province> provinceList;
    private List<City> cityList;
    private List<County> countyList;

    private Province selectedProvince;
    private City selectedCity;

    private int currentLevel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.choose_area,container,false);
        titleText=(TextView)view.findViewById(R.id.title_text);
        backButton=(Button)view.findViewById(R.id.back_button);
        listView=(ListView)view.findViewById(R.id.list_view);
        adapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(currentLevel==LEVEL_PROVINCE){
                    selectedProvince=provinceList.get(position);
                    queryCities();
                }else if(currentLevel==LEVEL_CITY){
                    selectedCity=cityList.get(position);
                    queryCounties();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        backButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(currentLevel==LEVEL_COUNTY){
                    queryCities();
                }else if(currentLevel==LEVEL_CITY){
                    queryProvinces();
                }
            }
        });
        queryProvinces();
    }

    private void queryProvinces() {
        titleText.setText("中国");
        backButton.setVisibility(View.GONE);
        //provinceList=LitePal.findAll(Province.class);
//        if(provinceList.size()>0){
//            dataList.clear();
//            for(Province province:provinceList){
//                dataList.add(province.getProvinceName());
//            }
//            adapter.notifyDataSetChanged();
//            listView.setSelection(0);
//            currentLevel=LEVEL_PROVINCE;
//        }else{
//            String address=String.format(getProvinceUrl, Datetime.getTimeStamp());
//            queryFromServer(address,"province");
//        }
        String address=String.format(getProvinceUrl, Datetime.getTimeStamp());
        queryFromServer(address,"province");
    }

    private void queryCities() {
        titleText.setText(selectedProvince.getProvinceName());
        backButton.setVisibility(View.VISIBLE);
        cityList=LitePal.where("provinceid=?",String.valueOf(selectedProvince.getId())).find(City.class);
        if(cityList.size()>0){
            dataList.clear();
            for(City city:cityList){
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel=LEVEL_CITY;
        }else{
            int provinceCode=selectedProvince.getProvinceCode();
            String address=String.format(getCityUrl,String.valueOf(provinceCode), Datetime.getTimeStamp());
            queryFromServer(address,"city");
        }
    }

    private void queryCounties() {
        titleText.setText(selectedCity.getCityName());
        backButton.setVisibility(View.VISIBLE);
        countyList=LitePal.where("cityid=?",String.valueOf(selectedCity.getId())).find(County.class);
        if(countyList.size()>0){
            dataList.clear();
            for(County county:countyList){
                dataList.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel=LEVEL_COUNTY;
        }else{
            int provinceCode=selectedProvince.getProvinceCode();
            int cityCode=selectedCity.getCityCode();
            String address=String.format(getTownUrl,String.valueOf(provinceCode),String.valueOf(cityCode), Datetime.getTimeStamp());
            queryFromServer(address,"city");
        }
    }

    private void queryFromServer(String address,final String type) {
        showProcessDialog();
        sendOkHttpRequest(address,new okhttp3.Callback(){
            @Override
            public void onResponse(Call cll, Response response)throws IOException{
                String resopnseText=response.body().string().replace("getData","").trim();
                resopnseText=resopnseText.substring(1,resopnseText.length()-2);
                //resopnseText=resopnseText.substring(0,resopnseText.length()-2);
                boolean result=false;

                if("province".equals(type)){
                    result=handleProvinceResponse(resopnseText);
                }else if("city".equals(type)){
                    result=handleCityResponse(resopnseText,selectedProvince.getId());
                }else if("county".equals(type)){
                    result=handleCountyResponse(resopnseText,selectedCity.getId());
                }

                if(result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if("province".equals(type)){
                                queryProvinces();
                            }else if("city".equals(type)){
                                queryCities();
                            }else if("county".equals(type)){
                                queryCounties();
                            }
                        }


                    });
                }
            }
            @Override
            public void onFailure(Call call,IOException e){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(),"Load Data Failed",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void showProcessDialog() {
        if(progressDialog==null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading Data...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void closeProgressDialog() {
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }
}
