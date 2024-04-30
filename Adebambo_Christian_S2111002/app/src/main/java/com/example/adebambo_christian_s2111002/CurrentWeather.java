package com.example.adebambo_christian_s2111002;

/* Christian Adebambo - S2111002 */

import android.util.Log;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurrentWeather implements Serializable {
    private static final String TAG = "CurrentWeather";
    private String day;
    private String weatherCondition;
    private String temperature;
    private String windDirection;
    private String windSpeed;
    private String humidity;

    public void parseTitle(String title) {
        try {
            // Regex to capture the day-time part and condition separately
            Pattern pattern = Pattern.compile("^(.+? \\d{2}:\\d{2} [A-Z]{2,4}): (.+)$");
            Matcher matcher = pattern.matcher(title);
            if (matcher.find()) {
                this.day = matcher.group(1);
                this.weatherCondition = matcher.group(2).split(",")[0];
            } else {
                Log.e(TAG, "Title does not match expected format: " + title);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error parsing title: " + title, e);
        }
    }

    public void parseDescription(String description) {
        try {
            String[] parts = description.split(",");
            for (String part : parts) {
                part = part.trim();
                if (part.startsWith("Temperature")) {
                    this.temperature = extractValue(part);
                } else if (part.startsWith("Wind Direction")) {
                    this.windDirection = extractValue(part);
                } else if (part.startsWith("Wind Speed")) {
                    this.windSpeed = extractValue(part);
                } else if (part.startsWith("Humidity")) {
                    this.humidity = extractValue(part);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error parsing description: " + description, e);
        }
    }

    private String extractValue(String data) {
        String result = null;
        try {
            result = data.substring(data.indexOf(':') + 1).trim();
        } catch (Exception e) {
            Log.e(TAG, "Error extracting value from: " + data, e);
        }
        return result;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
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

}