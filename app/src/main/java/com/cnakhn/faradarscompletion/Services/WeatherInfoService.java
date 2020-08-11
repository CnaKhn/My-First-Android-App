package com.cnakhn.faradarscompletion.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.cnakhn.faradarscompletion.ApiService;
import com.cnakhn.faradarscompletion.Weather.WeatherInfo;
import com.cnakhn.faradarscompletion.Weather.WeatherWidget;

public class WeatherInfoService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        ApiService apiService = new ApiService(this);
        apiService.requestWeatherData(new ApiService.onWeatherInfoReceived() {
            @Override
            public void onReceived(WeatherInfo weatherInfo) {
                if (weatherInfo != null) {
                    Intent sendDataIntent = new Intent(WeatherWidget.INTENT_ACTION_UPDATE_DATA);
                    sendDataIntent.putExtra("data", weatherInfo);
                    sendBroadcast(sendDataIntent);
                }
                stopSelf();
            }
        }, "Tehran");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
