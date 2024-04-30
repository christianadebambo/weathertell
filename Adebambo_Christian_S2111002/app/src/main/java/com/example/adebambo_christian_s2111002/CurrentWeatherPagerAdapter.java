package com.example.adebambo_christian_s2111002;

/* Christian Adebambo - S2111002 */

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;

public class CurrentWeatherPagerAdapter extends RecyclerView.Adapter<CurrentWeatherPagerAdapter.CurrentWeatherViewHolder> {
    private HashMap<String, CurrentWeather> currentWeatherData;
    private String[] locationOrder;

    public CurrentWeatherPagerAdapter(HashMap<String, CurrentWeather> currentWeatherData, String[] locationOrder) {
        this.currentWeatherData = currentWeatherData;
        this.locationOrder = locationOrder;
    }

    @NonNull
    @Override
    public CurrentWeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_weather_list_item, parent, false);
        return new CurrentWeatherViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentWeatherViewHolder holder, int position) {
        int realPosition = getRealPosition(position);
        String locationId = locationOrder[realPosition];
        CurrentWeather currentWeather = currentWeatherData.get(locationId);

        holder.textViewLocation.setText(LocationUtil.getLocationNameById(locationId));
        holder.textViewDay.setText(currentWeather.getDay());
        holder.textViewWeatherCondition.setText("Weather Condition: " + currentWeather.getWeatherCondition());
        holder.textViewTemperature.setText("Temperature: " + currentWeather.getTemperature());
        holder.textViewWind.setText("Wind: " + currentWeather.getWindDirection() + " at " + currentWeather.getWindSpeed());
        holder.textViewHumidity.setText("Humidity: " + currentWeather.getHumidity());
    }

    @Override
    public int getItemCount() {
        return locationOrder.length + 2;
    }

    public int getRealPosition(int position) {
        return (position - 1 + locationOrder.length) % locationOrder.length;
    }

    public int getRealCount() {
        return locationOrder.length;
    }

    static class CurrentWeatherViewHolder extends RecyclerView.ViewHolder {
        TextView textViewLocation, textViewDay, textViewWeatherCondition, textViewTemperature, textViewWind, textViewHumidity;

        CurrentWeatherViewHolder(View itemView) {
            super(itemView);
            textViewLocation = itemView.findViewById(R.id.textViewLocation);
            textViewDay = itemView.findViewById(R.id.textViewDay);
            textViewWeatherCondition = itemView.findViewById(R.id.textViewWeatherCondition);
            textViewTemperature = itemView.findViewById(R.id.textViewTemperature);
            textViewWind = itemView.findViewById(R.id.textViewWind);
            textViewHumidity = itemView.findViewById(R.id.textViewHumidity);
        }
    }
}
