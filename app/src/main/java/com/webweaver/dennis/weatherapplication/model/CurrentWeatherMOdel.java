package com.webweaver.dennis.weatherapplication.model;

/**
 * Created by dennis on 5/27/18.
 */

public class CurrentWeatherMOdel {
    private String mainWeather;
    private String description;

    //This is the constructor for this class
public CurrentWeatherMOdel(String mainWeather, String description){
    this.mainWeather = mainWeather;
    this.description = description;
}

    public String getMainWeather() {
        return mainWeather;
    }

    public String getDescription() {
        return description;
    }
}
