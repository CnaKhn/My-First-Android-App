package com.cnakhn.faradarscompletion.Weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;

import com.cnakhn.faradarscompletion.R;
import com.cnakhn.faradarscompletion.Utils;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Utils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorBlack));
        getSupportFragmentManager().beginTransaction().add(R.id.weather_fragment, WeatherFragment.newInstance("Tehran")).commit();
    }
}