package com.example.adebambo_christian_s2111002;

/* Christian Adebambo - S2111002 */

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WeatherRepository {

    private static final String TAG = "WeatherRepository";
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public void fetchWeatherData(String url, Callback<List<WeatherForecast>> callback) {
        executor.execute(() -> {
            try {
                String data = HttpUtil.fetchData(url);
                if (data.isEmpty()) {
                    Log.e(TAG, "No data received from URL: " + url);
                    callback.onError(new Exception("No data received"));
                    return;
                }
                List<WeatherForecast> forecasts = parseWeatherData(data);
                Log.i(TAG, "Weather data parsed successfully for URL: " + url);
                callback.onSuccess(forecasts);
            } catch (Exception e) {
                Log.e(TAG, "Error fetching or parsing weather data: " + e.getMessage(), e);
                callback.onError(e);
            }
        });
    }

    public void fetchCurrentWeatherData(String url, Callback<CurrentWeather> callback) {
        executor.execute(() -> {
            try {
                String data = HttpUtil.fetchData(url);
                if (data.isEmpty()) {
                    Log.e(TAG, "No data received from URL: " + url);
                    callback.onError(new Exception("No data received"));
                    return;
                }
                CurrentWeather currentWeather = parseCurrentWeatherData(data);
                Log.i(TAG, "Current weather data parsed successfully for URL: " + url);
                callback.onSuccess(currentWeather);
            } catch (Exception e) {
                Log.e(TAG, "Error fetching or parsing current weather data: " + e.getMessage(), e);
                callback.onError(e);
            }
        });
    }

    private List<WeatherForecast> parseWeatherData(String xmlData) throws Exception {
        XmlPullParser parser = Xml.newPullParser();
        try (StringReader reader = new StringReader(xmlData)) {
            parser.setInput(reader);
            List<WeatherForecast> forecasts = new ArrayList<>();
            int eventType = parser.getEventType();
            WeatherForecast currentForecast = null;
            Date pubDate = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagName.equalsIgnoreCase("pubDate") && pubDate == null) {
                            parser.next();
                            pubDate = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.UK).parse(parser.getText());
                        } else if (tagName.equalsIgnoreCase("item")) {
                            currentForecast = new WeatherForecast();
                        } else if (currentForecast != null) {
                            fillForecastDetails(parser, tagName, currentForecast);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (tagName.equalsIgnoreCase("item") && currentForecast != null) {
                            forecasts.add(currentForecast);
                            currentForecast = null;
                        }
                        break;
                }
                eventType = parser.next();
            }
            setForecastDates(forecasts, pubDate);
            return forecasts;
        }
    }

    private void fillForecastDetails(XmlPullParser parser, String tagName, WeatherForecast currentForecast) throws Exception {
        if (tagName.equalsIgnoreCase("title")) {
            parser.next();
            String fullTitle = parser.getText();

            String[] titleParts = fullTitle.split(":", 2);
            if (titleParts.length > 1) {
                currentForecast.setDate(titleParts[0].trim());

                String[] conditionParts = titleParts[1].split(",", 2);
                if (conditionParts.length > 0) {
                    currentForecast.setCondition(conditionParts[0].trim());
                }
            }
        } else if (tagName.equalsIgnoreCase("description")) {
            parser.next();
            currentForecast.setDescription(parser.getText());
        }
    }

    private void setForecastDates(List<WeatherForecast> forecasts, Date pubDate) {
        if (pubDate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(pubDate);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.UK);
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            for (int i = 0; i < forecasts.size(); i++) {
                forecasts.get(i).setFormattedDate(dateFormat.format(calendar.getTime()));
                calendar.add(Calendar.DATE, 1);
            }
        }
    }

    private CurrentWeather parseCurrentWeatherData(String xmlData) throws Exception {
        XmlPullParser parser = Xml.newPullParser();
        try (StringReader reader = new StringReader(xmlData)) {
            parser.setInput(reader);
            CurrentWeather currentWeather = null;
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagName.equalsIgnoreCase("item")) {
                            currentWeather = new CurrentWeather();
                        } else if (currentWeather != null) {
                            if (tagName.equalsIgnoreCase("title")) {
                                parser.next();
                                currentWeather.parseTitle(parser.getText());
                            } else if (tagName.equalsIgnoreCase("description")) {
                                parser.next();
                                currentWeather.parseDescription(parser.getText());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (tagName.equalsIgnoreCase("item") && currentWeather != null) {
                            return currentWeather;
                        }
                        break;
                }
                eventType = parser.next();
            }
            return currentWeather;
        }
    }

    public interface Callback<T> {
        void onSuccess(T result);
        void onError(Exception e);
    }
}
