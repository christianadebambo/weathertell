package com.example.adebambo_christian_s2111002;

/* Christian Adebambo - S2111002 */


import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CurrentWeatherActivity extends AppCompatActivity {
    private CurrentWeatherViewModel viewModel;
    private ViewPager2 viewPagerCurrentWeather;
    private CurrentWeatherPagerAdapter currentWeatherPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_weather);

        viewModel = new ViewModelProvider(this).get(CurrentWeatherViewModel.class);
        viewPagerCurrentWeather = findViewById(R.id.viewPagerCurrentWeather);

        viewModel.getCurrentWeatherData().observe(this, currentWeatherData -> {
            if (currentWeatherData != null && !currentWeatherData.isEmpty()) {
                currentWeatherPagerAdapter = new CurrentWeatherPagerAdapter(currentWeatherData, viewModel.getLocationIds());
                viewPagerCurrentWeather.setAdapter(currentWeatherPagerAdapter);
                viewPagerCurrentWeather.setCurrentItem(1, false);

                viewPagerCurrentWeather.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        if (position == 0) {
                            viewPagerCurrentWeather.setCurrentItem(currentWeatherPagerAdapter.getRealCount(), false);
                        } else if (position == currentWeatherPagerAdapter.getItemCount() - 1) {
                            viewPagerCurrentWeather.setCurrentItem(1, false);
                        }
                    }
                });
            } else {
                Toast.makeText(CurrentWeatherActivity.this, "Failed to fetch current weather data.", Toast.LENGTH_LONG).show();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.navigation_home) {
                startActivity(new Intent(this, MainActivity.class));
                return true;
            } else if (id == R.id.navigation_current) {
                // Already here
                return true;
            }
            return false;
        });
        bottomNavigationView.setSelectedItemId(R.id.navigation_current);

        if (NetworkUtil.isNetworkAvailable(this)) {
            viewModel.fetchCurrentWeatherForAllLocations();
        } else {
            Toast.makeText(this, "No network connection available.", Toast.LENGTH_LONG).show();
        }
    }
}
