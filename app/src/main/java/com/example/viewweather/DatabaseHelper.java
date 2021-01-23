package com.example.viewweather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "weather_database.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "weather_data";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_TEMPERATURE = "temperature";
    public static final String COLUMN_PRESSURE = "pressure";
    public static final String COLUMN_WIND = "wind";
    public static final String COLUMN_HUMIDITY = "humidity";
    public static final String COLUMN_DESCRIPTION = "description";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table if not exists weather_data " +
                        "(id integer primary key, city text, temperature text, pressure text, wind text, " +
                        "humidity text, description text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS weather_data");
        onCreate(db);
    }

    public boolean insertCityData (String city, String temperature, String pressure, String wind, String humidity, String description) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("city", city);
            contentValues.put("temperature", temperature);
            contentValues.put("pressure", pressure);
            contentValues.put("wind", wind);
            contentValues.put("humidity", humidity);
            contentValues.put("description", description);
            db.insert("weather_data", null, contentValues);
        }
        catch(Exception ex){
            return false;
        }

        return true;
    }

    public Cursor getWeatherData(String city) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from weather_data where city='" + city + "'", null);
        return res;
    }

    public boolean updateCityData (Integer id, String city, String temperature, String pressure, String wind, String humidity,String description) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("city", city);
            contentValues.put("temperature", temperature);
            contentValues.put("pressure", pressure);
            contentValues.put("wind", wind);
            contentValues.put("humidity", humidity);
            contentValues.put("description", description);
            db.update("weather_data", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        }
        catch (Exception ex) {
            return false;
        }

        return true;
    }
}
