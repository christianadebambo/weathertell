package com.example.adebambo_christian_s2111002;

/* Christian Adebambo - S2111002 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

    public static String fetchData(String urlString) throws IOException {
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                if (inputStream != null) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                }
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return result.toString();
    }
}