package com.example.viewweather;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class WeatherDetailsActivity extends AppCompatActivity {

    TextView cityTextView, tempTextView, pressureTextView, windTextView, humidityTextView, descriptionTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        cityTextView = findViewById(R.id.txt_city_name);
        tempTextView = findViewById(R.id.txt_temperature);
        pressureTextView = findViewById(R.id.txt_pressure);
        windTextView = findViewById(R.id.txt_wind);
        humidityTextView = findViewById(R.id.txt_humidity);
        descriptionTextView = findViewById(R.id.txt_description);

        Intent intent = getIntent();
        if(intent.getStringExtra(MainActivity.SELECTED_CITY).isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    getApplicationContext().getString(R.string.inputCity),
                    Toast.LENGTH_LONG).show();
        }
        else {
            String selectedCity = intent.getStringExtra(MainActivity.SELECTED_CITY);

            WeatherAsyncTask asyncTask = new WeatherAsyncTask();
            asyncTask.execute(selectedCity);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private class WeatherAsyncTask extends AsyncTask<String, String, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(WeatherDetailsActivity.this,
                    "ProgressDialog",
                    getString(R.string.waitingMessage));
        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";
            String selectedCity = params[0];
            try {
                final JSONObject json = GetWeather.getJSON(selectedCity);

                if(json != null)
                    return json.toString();

            }
            catch (Exception e) {
                result = e.getMessage();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            try {
                if(result.isEmpty()){
                    Intent resultIntent = new Intent();
                    setResult(-1,resultIntent);
                    finish();
                }
                else {
                    JSONObject json = new JSONObject(result);
                    JSONObject details = json.getJSONArray("weather").getJSONObject(0);
                    JSONObject main = json.getJSONObject("main");
                    JSONObject wind = json.getJSONObject("wind");

                    String cityName = json.getString("name");
                    String temperature = main.getString("temp") + " ℃";
                    String pressure = main.getString("pressure") + " hPa";
                    String windSpeed = wind.getString("speed") + " meter/sec";
                    String humidity = main.getString("humidity") + "%";
                    String description = details.getString("description");

                    cityTextView.setText(cityName);
                    tempTextView.setText(temperature);
                    pressureTextView.setText(pressure);
                    windTextView.setText(windSpeed);
                    humidityTextView.setText(humidity);
                    descriptionTextView.setText(description);

                    Intent dbServiceIntent = new Intent(WeatherDetailsActivity.this,WeatherService.class);

                    String[] weatherData = new String[] {
                            cityName, temperature, pressure, windSpeed, humidity, description
                    };

                    dbServiceIntent.putExtra("weatherData", weatherData);
                    startService(dbServiceIntent);
                }
            }
            catch(Exception e){

            }
        }
    }

}

