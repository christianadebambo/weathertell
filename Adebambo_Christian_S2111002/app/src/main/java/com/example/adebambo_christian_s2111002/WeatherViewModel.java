package com.example.adebambo_christian_s2111002;

/* Christian Adebambo - S2111002 */

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

public class WeatherViewModel extends ViewModel {
    private static final String TAG = "WeatherViewModel";
    private MutableLiveData<HashMap<String, List<WeatherForecast>>> locationWeatherData = new MutableLiveData<>();
    private WeatherDataManager dataManager = new WeatherDataManager();
    private String[] locationIds = new String[] {"2648579", "2643743", "5128581", "287286", "934154", "1185241"};

    public LiveData<HashMap<String, List<WeatherForecast>>> getLocationWeatherData() {
        return locationWeatherData;
    }

    public String[] getLocationIds() {
        return locationIds;
    }

    public void fetchWeatherForAllLocations() {
        Log.d(TAG, "Fetching weather for all locations.");
        dataManager.fetchWeatherDataForLocations(locationIds, new WeatherRepository.Callback<HashMap<String, List<WeatherForecast>>>() {
            @Override
            public void onSuccess(HashMap<String, List<WeatherForecast>> result) {
                if (result == null || result.isEmpty()) {
                    Log.e(TAG, "No weather data received.");
                    locationWeatherData.postValue(null);
                } else {
                    Log.i(TAG, "Weather data fetched successfully.");
                    locationWeatherData.postValue(result);
                }
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "Failed to fetch weather data: " + e.getMessage(), e);
                locationWeatherData.postValue(null);
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "ViewModel being cleared. Cancelling all ongoing operations.");
        dataManager.shutdown();
    }
}
