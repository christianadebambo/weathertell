package com.example.adebambo_christian_s2111002;

/* Christian Adebambo - S2111002 */

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class DailyForecastAdapter extends RecyclerView.Adapter<DailyForecastAdapter.DailyForecastViewHolder> {
    private List<WeatherForecast> forecastList;
    private String locationId;

    public DailyForecastAdapter(List<WeatherForecast> forecastList, String locationId) {
        this.forecastList = forecastList;
        this.locationId = locationId;
    }

    @NonNull
    @Override
    public DailyForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_forecast, parent, false);
        return new DailyForecastViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyForecastViewHolder holder, int position) {
        WeatherForecast forecast = forecastList.get(position);
        holder.textViewDate.setText(String.format("%s (%s)", forecast.getDate(), forecast.getFormattedDate()));
        holder.textViewCondition.setText(String.format("Weather Condition: %s", forecast.getCondition()));

        // Check for null temperature values and display accordingly
        String temperatureText;
        if (forecast.getMinTemp() != null && forecast.getMaxTemp() != null) {
            temperatureText = String.format("Temperature: %s - %s", forecast.getMinTemp(), forecast.getMaxTemp());
        } else if (forecast.getMinTemp() != null) {
            temperatureText = String.format("Temperature: %s", forecast.getMinTemp());
        } else if (forecast.getMaxTemp() != null) {
            temperatureText = String.format("Temperature: %s", forecast.getMaxTemp());
        } else {
            temperatureText = "Temperature: Data not available";
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailedForecastActivity.class);
            intent.putExtra(DetailedForecastActivity.EXTRA_WEATHER_DETAILS, forecast);
            intent.putExtra(DetailedForecastActivity.EXTRA_LOCATION_NAME, LocationUtil.getLocationNameById(this.locationId));
            v.getContext().startActivity(intent);
        });


        holder.textViewTemperature.setText(temperatureText);
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }

    static class DailyForecastViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDate, textViewCondition, textViewTemperature;

        DailyForecastViewHolder(View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewCondition = itemView.findViewById(R.id.textViewCondition);
            textViewTemperature = itemView.findViewById(R.id.textViewTemperature);
        }
    }
}