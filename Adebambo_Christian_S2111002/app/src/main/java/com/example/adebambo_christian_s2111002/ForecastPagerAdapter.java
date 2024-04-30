package com.example.adebambo_christian_s2111002;

/* Christian Adebambo - S2111002 */

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class ForecastPagerAdapter extends RecyclerView.Adapter<ForecastPagerAdapter.ForecastViewHolder> {
    private HashMap<String, List<WeatherForecast>> locationForecasts;
    private String[] locationOrder;

    public ForecastPagerAdapter(HashMap<String, List<WeatherForecast>> locationForecasts, String[] locationOrder) {
        this.locationForecasts = locationForecasts;
        this.locationOrder = locationOrder;
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_list, parent, false);
        return new ForecastViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        int realPosition = getRealPosition(position);
        String locationId = locationOrder[realPosition];
        List<WeatherForecast> forecasts = locationForecasts.get(locationId);
        holder.locationName.setText(LocationUtil.getLocationNameById(locationId));
        holder.dailyForecastRecyclerView.setAdapter(new DailyForecastAdapter(forecasts, locationId));
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

    static class ForecastViewHolder extends RecyclerView.ViewHolder {
        TextView locationName;
        RecyclerView dailyForecastRecyclerView;

        ForecastViewHolder(View itemView) {
            super(itemView);
            locationName = itemView.findViewById(R.id.locationNameTextView);
            dailyForecastRecyclerView = itemView.findViewById(R.id.daily_forecast_recyclerview);
            dailyForecastRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext(), RecyclerView.VERTICAL, false));
        }
    }
}
