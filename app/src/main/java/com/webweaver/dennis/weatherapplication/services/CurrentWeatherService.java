package com.webweaver.dennis.weatherapplication.services;

import android.util.Log;

import com.webweaver.dennis.weatherapplication.Constants;
import com.webweaver.dennis.weatherapplication.ui.MainActivity;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by dennis on 5/27/18.
 */

public class CurrentWeatherService {
    public static void getCurrentWeather(okhttp3.Callback callback){
        MainActivity mainActivity =  new MainActivity();
        //Building the okhttp client to buld the url
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        //Building the link itself
        HttpUrl.Builder urlbuilder = HttpUrl.parse(Constants.currentWeatherURL).newBuilder();
        urlbuilder.addQueryParameter(Constants.searchQuery, mainActivity.mLocation);
        String url = urlbuilder.build().toString();//String of the url

        Log.d("url", url);

        //Create the request section
        Request request = new Request.Builder().url(url).build();

        //Create a call asynchronously
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);


    }

}
