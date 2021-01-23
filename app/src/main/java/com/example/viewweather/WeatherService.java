package com.example.viewweather;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.widget.Toast;

public class WeatherService extends Service {

    DatabaseHelper dbHelper;


    public WeatherService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            dbHelper = new DatabaseHelper(this);
        }
        catch(Exception ex){
           dbHelper.close();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String[] weatherData;
        if(intent.getStringArrayExtra("weatherData") != null) {
               weatherData = intent.getStringArrayExtra("weatherData");
               Cursor findIdCursor = dbHelper.getWeatherData(weatherData[0]);
               if(findIdCursor.getCount() > 0) {
                   findIdCursor.moveToFirst();
                   int id = findIdCursor.getInt(0);
                   dbHelper.updateCityData(id,weatherData[0],weatherData[1],weatherData[2],weatherData[3],
                           weatherData[4],weatherData[5]);
               }
               else {
                   dbHelper.insertCityData(weatherData[0],weatherData[1],weatherData[2],weatherData[3],
                           weatherData[4],weatherData[5]);
               }
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
}