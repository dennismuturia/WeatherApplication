package com.webweaver.dennis.weatherapplication.model;

/**
 * Created by dennis on 5/27/18.
 */

public class CurrentWeatherModel {
    private String mainWeather;
    private String description;
    private String avgTemp;
    private String humidity;


    //This is the constructor for this class
public CurrentWeatherModel(String mainWeather, String description, String avgTemp, String humidity){
    this.mainWeather = mainWeather;
    this.description = description;
    this.avgTemp = avgTemp;
    this.humidity = humidity;
}

    public String getMainWeather() {
        return mainWeather;
    }

    public String getDescription() {
        return description;
    }
    public String getAvgTemp() {
        return avgTemp;
    }

    public String getHumidity() {
        return humidity;
    }

}
