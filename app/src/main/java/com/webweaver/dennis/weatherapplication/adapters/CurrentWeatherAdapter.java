package com.webweaver.dennis.weatherapplication.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.webweaver.dennis.weatherapplication.model.CurrentWeatherModel;

import java.util.ArrayList;

public class CurrentWeatherAdapter extends RecyclerView.Adapter<CurrentWeatherAdapter.CurrentWeatherViewHolder>{
    private ArrayList<CurrentWeatherModel>weatherModels = new ArrayList<>();
    private Context mContext;


    public CurrentWeatherAdapter(Context context, ArrayList<CurrentWeatherModel>currentWeatherModels){
        mContext = context;
        weatherModels = currentWeatherModels;
    }

}
