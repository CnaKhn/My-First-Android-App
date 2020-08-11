package com.cnakhn.faradarscompletion.Weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.cnakhn.faradarscompletion.ApiService;
import com.cnakhn.faradarscompletion.R;

public class WeatherFragment extends Fragment {
    private static final String TAG = "WeatherFragment";
    public static TextView txtCity;
    public static TextView txtTemp;
    public static TextView txtTempSign;
    public static TextView txtDetail;
    public static TextViewWeather txtIcon;
    public ImageView btnMenu;
    private String city = "";


    @Override
    public void onResume() {
        super.onResume();
        if (getArguments() != null && city != null) {
            final ApiService apiService = new ApiService(getContext());
            apiService.requestWeatherData(new ApiService.onWeatherInfoReceived() {
                @Override
                public void onReceived(WeatherInfo weatherInfo) {
                }
            }, getArguments().getString("city"));
        }
    }

    public static WeatherFragment newInstance(String city) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putString("city", city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_weather_card, container, false);
        txtCity = view.findViewById(R.id.weather_city);
        txtTemp = view.findViewById(R.id.weather_temp);
        txtTempSign = view.findViewById(R.id.weather_temp_sign);
        txtDetail = view.findViewById(R.id.weather_detail);
        txtIcon = view.findViewById(R.id.weather_icon);
        return view;
    }

}
