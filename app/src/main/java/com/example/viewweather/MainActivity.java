package com.example.viewweather;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final String SELECTED_CITY = "Selected city";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText cityText = findViewById(R.id.editTextCityName);

        Button searchCityButton = findViewById(R.id.btnSearchCity);
        searchCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cityText.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            getApplicationContext().getString(R.string.inputCity),
                            Toast.LENGTH_LONG).show();
                }
                else {
                    String selectedCity = cityText.getText().toString().trim();
                    Intent intent = new Intent(v.getContext(),
                            WeatherDetailsActivity.class);
                    intent.putExtra(SELECTED_CITY, selectedCity);
                    startActivityForResult(intent, 1);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode,data);
        if(requestCode == 1 && resultCode == -1){
            Toast.makeText(getApplicationContext(),
                    getApplicationContext().getString(R.string.city_not_found),
                    Toast.LENGTH_LONG).show();
        }
    }
}