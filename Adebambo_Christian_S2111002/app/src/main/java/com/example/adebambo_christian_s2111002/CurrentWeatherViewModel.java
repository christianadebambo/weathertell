package com.example.adebambo_christian_s2111002;

/* Christian Adebambo - S2111002 */

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.util.Log;

import java.util.HashMap;

public class CurrentWeatherViewModel extends ViewModel {
    private static final String TAG = "CurrentWeatherViewModel";
    private MutableLiveData<HashMap<String, CurrentWeather>> currentWeatherData = new MutableLiveData<>();
    private WeatherDataManager dataManager = new WeatherDataManager();
    private String[] locationIds = new String[] {"2648579", "2643743", "5128581", "287286", "934154", "1185241"};

    public LiveData<HashMap<String, CurrentWeather>> getCurrentWeatherData() {
        return currentWeatherData;
    }

    public String[] getLocationIds() {
        return locationIds;
    }

    public void fetchCurrentWeatherForAllLocations() {
        Log.d(TAG, "Starting to fetch current weather for all locations.");
        dataManager.fetchCurrentWeatherDataForLocations(locationIds, new WeatherRepository.Callback<HashMap<String, CurrentWeather>>() {
            @Override
            public void onSuccess(HashMap<String, CurrentWeather> result) {
                if (result == null || result.isEmpty()) {
                    Log.e(TAG, "Received empty or null weather data.");
                    currentWeatherData.postValue(null);
                } else {
                    Log.i(TAG, "Successfully fetched current weather data.");
                    currentWeatherData.postValue(result);
                }
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "Error fetching current weather data: " + e.getMessage(), e);
                currentWeatherData.postValue(null);
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "ViewModel being cleared. Shutting down data manager.");
        dataManager.shutdown();
    }
}