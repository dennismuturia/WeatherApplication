package com.webweaver.dennis.weatherapplication.services;

import android.util.Log;

import com.webweaver.dennis.weatherapplication.Constants;
import com.webweaver.dennis.weatherapplication.model.CurrentWeatherMOdel;
import com.webweaver.dennis.weatherapplication.ui.MainActivity;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class CurrentWeatherService {
    public static void getCurrentWeather(String location,Callback callback){
        //Building the okhttp client to buld the url
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        //Building the link itself
        HttpUrl.Builder urlbuilder = HttpUrl.parse(Constants.currentWeatherURL).newBuilder();
        urlbuilder.addQueryParameter(Constants.searchQuery,location);
        urlbuilder.addQueryParameter(Constants.appID, Constants.apiKey);
        String url = urlbuilder.build().toString();//String of the url

        Log.d("url", url);

        //Create the request section
        Request request = new Request.Builder().url(url).build();

        //Create a call asynchronously
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }
    //Now the method to fetch the data from the api

    public ArrayList<CurrentWeatherMOdel> theWeather(Response response){
        ArrayList<CurrentWeatherMOdel>weather = new ArrayList<>();

    }

}
