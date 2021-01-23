package com.example.viewweather;

import android.content.Context;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetWeather {
    private static final String OPEN_WEATHER_MAP_API ="http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";
    private static final String OPEN_WEATHER_MAPS_APP_ID = "b3da896d24186adf266e4966212ce88c";

    public static JSONObject getJSON(String city){
        try {
            URL url = new URL(String.format(OPEN_WEATHER_MAP_API, city));
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("x-api-key", OPEN_WEATHER_MAPS_APP_ID);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder json = new StringBuilder(1024);
            String currentLine="";

            while((currentLine=reader.readLine()) != null)
                json.append(currentLine).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());


            if(data.getInt("cod") != 200){
                return null;
            }
            return data;
        }
        catch(Exception e){
            return null;
        }
    }
}
