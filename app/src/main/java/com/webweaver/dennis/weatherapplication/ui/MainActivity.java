package com.webweaver.dennis.weatherapplication.ui;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.webweaver.dennis.weatherapplication.R;
import com.webweaver.dennis.weatherapplication.model.CurrentWeatherMOdel;
import com.webweaver.dennis.weatherapplication.services.CurrentWeatherService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    LocationManager locationManager;
    TextView locationText;
    TextView apiData;
    public ArrayList<CurrentWeatherMOdel>mWeather = new ArrayList<>();
    private static final int MY_PERMISSION_REQUEST_LOCATION = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationText = (TextView)findViewById(R.id.locationText);
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)){
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_REQUEST_LOCATION);
            }else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_REQUEST_LOCATION);
            }
        }
        else {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            try {
                locationText.setText(getLocation(location.getLatitude(), location.getLongitude()));
                String mLocation = getLocation(location.getLatitude(), location.getLongitude());//Setting the string variable to mLocation
                Toast.makeText(this, "Location Found", Toast.LENGTH_SHORT).show();
                getWeather(mLocation);
            }catch (Exception e){
                e.printStackTrace();
                //Toast.makeText(this, "Location not found!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions, int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSION_REQUEST_LOCATION:{
                if (grantResults.length > 0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        try {
                            locationText.setText(getLocation(location.getLatitude(), location.getLongitude()));
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(this, "Location not found!", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(this, "No permission has been granted", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    //In this method we will be getting the city name
    public String getLocation(double latitude, double longitude){
        String currentCity = "";

        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
        List<Address> addressList;
        try {
            addressList = geocoder.getFromLocation(latitude, longitude, 1);
            if (addressList.size() > 0){
                currentCity = addressList.get(0).getLocality();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return currentCity;
    }
    //This will be the method to get the wetaher data
    public void getWeather(String location){
        final CurrentWeatherService weatherService = new CurrentWeatherService();
        weatherService.getCurrentWeather(location, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String jsonData = response.body().string();
                    apiData = (TextView)findViewById(R.id.apiData);
                    apiData.setText(jsonData.toString());
                    mWeather = weatherService.theWeather(response);
                    Log.v(TAG, jsonData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
