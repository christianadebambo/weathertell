package com.example.adebambo_christian_s2111002;

/* Christian Adebambo - S2111002 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private WeatherViewModel viewModel;
    private ViewPager2 viewPagerForecast;
    private ForecastPagerAdapter forecastPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_forecast);

        viewPagerForecast = findViewById(R.id.viewPagerForecast);

        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        viewModel.getLocationWeatherData().observe(this, locationWeatherData -> {
            if (locationWeatherData != null && !locationWeatherData.isEmpty()) {
                if (viewPagerForecast.getAdapter() == null) {
                    forecastPagerAdapter = new ForecastPagerAdapter(locationWeatherData, viewModel.getLocationIds());
                    viewPagerForecast.setAdapter(forecastPagerAdapter);
                    // Add fake padding items (First and Last)
                    viewPagerForecast.setCurrentItem(1, false);
                } else {
                    forecastPagerAdapter.notifyDataSetChanged();
                }
            } else {
                Toast.makeText(this, "Failed to fetch weather data or data is empty.", Toast.LENGTH_LONG).show();
            }
        });

        viewPagerForecast.registerOnPageChangeCallback(new OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // Check if the current position is at fake padding item
                int realPosition = forecastPagerAdapter.getRealPosition(position);
                if (position == 0) {
                    viewPagerForecast.setCurrentItem(forecastPagerAdapter.getRealCount(), false);
                } else if (position == forecastPagerAdapter.getItemCount() - 1) {
                    viewPagerForecast.setCurrentItem(1, false);
                }
            }
        });

        // Setup bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.navigation_home) {
                // Already on home
                return true;
            } else if (id == R.id.navigation_current) {
                startActivity(new Intent(this, CurrentWeatherActivity.class));
                return true;
            }
            return false;
        });
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);


        if (NetworkUtil.isNetworkAvailable(this)) {
            viewModel.fetchWeatherForAllLocations();
        } else {
            Toast.makeText(this, "No network connection available.", Toast.LENGTH_LONG).show();
        }
    }
}