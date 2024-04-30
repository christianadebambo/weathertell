package com.example.adebambo_christian_s2111002;

/* Christian Adebambo - S2111002 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DetailedForecastActivity extends AppCompatActivity {

    public static final String EXTRA_WEATHER_DETAILS = "com.example.adebambo_christian_s2111002.EXTRA_WEATHER_DETAILS";
    public static final String EXTRA_LOCATION_NAME = "com.example.adebambo_christian_s2111002.EXTRA_LOCATION_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_forecast);

        // Set up back button
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> finish());

        // Get the WeatherForecast object passed from the Main Forecast Screen
        WeatherForecast forecast = (WeatherForecast) getIntent().getSerializableExtra(EXTRA_WEATHER_DETAILS);
        String locationName = getIntent().getStringExtra(EXTRA_LOCATION_NAME);

        TextView textViewLocationName = findViewById(R.id.locationNameTextView);
        TextView textViewDate = findViewById(R.id.textViewDate);
        TextView textViewCondition = findViewById(R.id.textViewCondition);
        TextView textViewTemperature = findViewById(R.id.textViewTemperature);
        TextView textViewWind = findViewById(R.id.textViewWind);
        TextView textViewPressure = findViewById(R.id.textViewPressure);
        TextView textViewHumidity = findViewById(R.id.textViewHumidity);
        TextView textViewUVRisk = findViewById(R.id.textViewUVRisk);
        TextView textViewPollution = findViewById(R.id.textViewPollution);
        TextView textViewSunTimes = findViewById(R.id.textViewSunTimes);

        textViewLocationName.setText(locationName);
        textViewDate.setText(String.format("%s, %s", forecast.getDate(), forecast.getFormattedDate()));

        // Set each TextView's text based on the WeatherForecast details
        textViewCondition.setText("Weather Condition: " + (forecast.getCondition() != null ? forecast.getCondition() : "N/A"));
        textViewTemperature.setText("Temperature: " + formatTemperature(forecast.getMinTemp(), forecast.getMaxTemp()));
        textViewWind.setText("Wind: " + (forecast.getWindDirection() != null ? forecast.getWindDirection() : "N/A") + " at " +
                (forecast.getWindSpeed() != null ? forecast.getWindSpeed() : "N/A"));
        textViewPressure.setText("Pressure: " + (forecast.getPressure() != null ? forecast.getPressure() : "N/A"));
        textViewHumidity.setText("Humidity: " + (forecast.getHumidity() != null ? forecast.getHumidity() + "%" : "N/A"));
        textViewUVRisk.setText("UV Risk: " + (forecast.getUvRisk() != null ? forecast.getUvRisk() : "N/A"));
        textViewPollution.setText("Pollution: " + (forecast.getPollution() != null ? forecast.getPollution() : "N/A"));
        textViewSunTimes.setText("Sunrise: " + (forecast.getSunrise() != null ? forecast.getSunrise() : "N/A") +
                ", Sunset: " + (forecast.getSunset() != null ? forecast.getSunset() : "N/A"));

        // Setup bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.navigation_home) {
                startActivity(new Intent(this, MainActivity.class));
                return true;
            } else if (id == R.id.navigation_current) {
                startActivity(new Intent(this, CurrentWeatherActivity.class));
                return true;
            }
            return false;
        });
    }

    private String formatTemperature(String minTemp, String maxTemp) {
        if (minTemp != null && maxTemp != null) {
            return String.format("%s - %s", minTemp, maxTemp);
        } else if (minTemp != null) {
            return minTemp;
        } else if (maxTemp != null) {
            return maxTemp;
        } else {
            return "Data not available";
        }
    }
}
