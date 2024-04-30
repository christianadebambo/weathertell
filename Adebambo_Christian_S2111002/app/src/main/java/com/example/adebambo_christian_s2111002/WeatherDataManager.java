package com.example.adebambo_christian_s2111002;

/* Christian Adebambo - S2111002 */

import android.util.Log;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class WeatherDataManager {
    private static final String TAG = "WeatherDataManager";
    private WeatherRepository weatherRepository;
    private ExecutorService executor = Executors.newFixedThreadPool(6); // For parallel fetching

    public WeatherDataManager() {
        this.weatherRepository = new WeatherRepository();
    }

    public void fetchWeatherDataForLocations(String[] locationIds, WeatherRepository.Callback<HashMap<String, List<WeatherForecast>>> callback) {
        HashMap<String, List<WeatherForecast>> allForecasts = new HashMap<>();
        AtomicInteger completedTasks = new AtomicInteger(0);

        for (String locationId : locationIds) {
            executor.execute(() -> {
                String url = String.format("https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/%s", locationId);
                weatherRepository.fetchWeatherData(url, new WeatherRepository.Callback<List<WeatherForecast>>() {
                    @Override
                    public void onSuccess(List<WeatherForecast> forecasts) {
                        synchronized (allForecasts) {
                            allForecasts.put(locationId, forecasts);
                            if (completedTasks.incrementAndGet() == locationIds.length) {
                                Log.i(TAG, "All weather data successfully fetched");
                                callback.onSuccess(allForecasts);
                            }
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e(TAG, "Failed to fetch weather data for location: " + locationId, e);
                        if (completedTasks.incrementAndGet() == locationIds.length && allForecasts.isEmpty()) {
                            callback.onError(new Exception("Failed to fetch any weather data"));
                        }
                    }
                });
            });
        }
    }

    public void fetchCurrentWeatherDataForLocations(String[] locationIds, WeatherRepository.Callback<HashMap<String, CurrentWeather>> callback) {
        HashMap<String, CurrentWeather> allCurrentWeather = new HashMap<>();
        AtomicInteger completedTasks = new AtomicInteger(0);

        for (String locationId : locationIds) {
            executor.execute(() -> {
                String url = String.format("https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/%s", locationId);
                weatherRepository.fetchCurrentWeatherData(url, new WeatherRepository.Callback<CurrentWeather>() {
                    @Override
                    public void onSuccess(CurrentWeather currentWeather) {
                        synchronized (allCurrentWeather) {
                            allCurrentWeather.put(locationId, currentWeather);
                            if (completedTasks.incrementAndGet() == locationIds.length) {
                                Log.i(TAG, "All current weather data successfully fetched");
                                callback.onSuccess(allCurrentWeather);
                            }
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e(TAG, "Failed to fetch current weather data for location: " + locationId, e);
                        if (completedTasks.incrementAndGet() == locationIds.length && allCurrentWeather.isEmpty()) {
                            callback.onError(new Exception("Failed to fetch any current weather data"));
                        }
                    }
                });
            });
        }
    }

    // Shutdown method to stop all active or pending tasks gracefully
    public void shutdown() {
        Log.d(TAG, "Shutting down ExecutorService in WeatherDataManager");
        executor.shutdownNow();
        try {
            if (!executor.awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS)) {
                Log.e(TAG, "Executor did not terminate in the specified time.");
                executor.shutdownNow();
            }
        } catch (InterruptedException ie) {
            Log.e(TAG, "Interrupted during shutdown.");
            executor.shutdownNow();
            Thread.currentThread().interrupt(); // Preserve interrupt status
        }
    }
}