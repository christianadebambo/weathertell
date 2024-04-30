package com.example.adebambo_christian_s2111002;

/* Christian Adebambo - S2111002 */

import android.util.Log;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WeatherForecast implements Serializable {
    private static final String TAG = "WeatherForecast";
    private String date;
    private String formattedDate;
    private String condition;
    private String description;
    private String minTemp;
    private String maxTemp;
    private String windDirection;
    private String windSpeed;
    private String humidity;
    private String pressure;
    private String uvRisk;
    private String pollution;
    private String sunrise;
    private String sunset;

    public WeatherForecast() {
    }

    public WeatherForecast(String date, String formattedDate, String condition, String description,
                           String minTemp, String maxTemp, String windDirection, String windSpeed,
                           String humidity, String pressure, String uvRisk, String pollution,
                           String sunrise, String sunset) {
        setDate(date);
        setFormattedDate(formattedDate);
        setCondition(condition);
        setDescription(description);
        setMinTemp(minTemp);
        setMaxTemp(maxTemp);
        setWindDirection(windDirection);
        setWindSpeed(windSpeed);
        setHumidity(humidity);
        setPressure(pressure);
        setUvRisk(uvRisk);
        setPollution(pollution);
        setSunrise(sunrise);
        setSunset(sunset);
    }

    private void parseDetailsFromDescription(String description) {
        String[] parts = description.split(", ");
        for (String part : parts) {
            try {
                if (part.startsWith("Maximum Temperature")) {
                    this.maxTemp = extractTemperature(part);
                } else if (part.startsWith("Minimum Temperature")) {
                    this.minTemp = extractTemperature(part);
                } else if (part.startsWith("Wind Direction")) {
                    this.windDirection = part.substring("Wind Direction: ".length()).trim();
                } else if (part.startsWith("Wind Speed")) {
                    this.windSpeed = part.substring("Wind Speed: ".length()).trim();
                } else if (part.startsWith("Pressure")) {
                    this.pressure = part.substring("Pressure: ".length()).trim();
                } else if (part.startsWith("Humidity")) {
                    this.humidity = part.substring("Humidity: ".length(), part.indexOf('%')).trim();
                } else if (part.startsWith("UV Risk")) {
                    this.uvRisk = part.substring("UV Risk: ".length()).trim();
                } else if (part.startsWith("Pollution")) {
                    this.pollution = part.substring("Pollution: ".length()).trim();
                } else if (part.startsWith("Sunrise")) {
                    this.sunrise = part.substring("Sunrise: ".length()).trim();
                } else if (part.startsWith("Sunset")) {
                    this.sunset = part.substring("Sunset: ".length()).trim();
                }
            } catch (Exception e) {
                Log.e(TAG, "Error parsing part: " + part, e);
            }
        }
    }

    private String extractTemperature(String temperaturePart) {
        try {
            if (!temperaturePart.contains("null")) {
                return temperaturePart.substring(temperaturePart.indexOf(":") + 2);
            }
        } catch (IndexOutOfBoundsException e) {
            Log.e(TAG, "Failed to extract temperature from: " + temperaturePart, e);
        }
        return null;
    }

    public void setDateFromPubDate(String pubDate, int dayOffset) {
        try {
            SimpleDateFormat sourceFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.ENGLISH);
            Date date = sourceFormat.parse(pubDate);
            SimpleDateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
            date.setTime(date.getTime() + (long) dayOffset * 24 * 3600 * 1000);
            this.formattedDate = targetFormat.format(date);
        } catch (ParseException e) {
            Log.e(TAG, "Error parsing publication date: " + pubDate, e);
            this.formattedDate = "";
        }
    }

    // Getter and setter methods
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        parseDetailsFromDescription(description);
    }

    public String getMinTemp() {
        return minTemp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    private void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    private void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getUvRisk() {
        return uvRisk;
    }

    public void setUvRisk(String uvRisk) {
        this.uvRisk = uvRisk;
    }

    public String getPollution() {
        return pollution;
    }

    public void setPollution(String pollution) {
        this.pollution = pollution;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }
}