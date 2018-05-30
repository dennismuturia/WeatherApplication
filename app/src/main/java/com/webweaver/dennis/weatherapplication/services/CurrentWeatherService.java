package com.webweaver.dennis.weatherapplication.services;

import android.util.Log;

import com.webweaver.dennis.weatherapplication.Constants;
import com.webweaver.dennis.weatherapplication.model.CurrentWeatherModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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

    public ArrayList<CurrentWeatherModel> theWeather(Response response){
        ArrayList<CurrentWeatherModel>weather = new ArrayList<>();
        try{
            String jsonData = response.body().string();
            if (response.isSuccessful()){
                JSONObject weatherObject = new JSONObject(jsonData);
                JSONObject temps = weatherObject.getJSONObject("main");
                for (int j = 0; j < temps.length(); j++) {
                    String mainTemp = temps.getString("temp");
                    String humidity = temps.getString("humidity");
                    JSONObject winds = weatherObject.getJSONObject("wind");
                    for (int k = 0; k < winds.length(); k++) {
                        String speed = winds.getString("speed");
                        JSONObject theSys = weatherObject.getJSONObject("sys");
                        for (int g = 0; g < theSys.length(); g++) {
                            String country = theSys.getString("country");
                            JSONArray weatherArray = weatherObject.getJSONArray("weather");
                            for (int i = 0; i < weatherArray.length(); i++) {
                                JSONObject theOtherObject = weatherArray.getJSONObject(i);
                                String mainWeather = theOtherObject.getString("main");
                                String weatherDesc = theOtherObject.getString("description");
                                //Parsing the data to the model that was fetched from the API
                                CurrentWeatherModel currentWeatherModel = new CurrentWeatherModel(mainWeather, weatherDesc, mainTemp, humidity, speed, country);
                                weather.add(currentWeatherModel);
                            }
                        }

                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }
        return weather;
    }

}
