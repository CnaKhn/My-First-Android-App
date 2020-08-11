package com.cnakhn.faradarscompletion.Weather;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.widget.RemoteViews;

import com.cnakhn.faradarscompletion.R;
import com.cnakhn.faradarscompletion.Services.WeatherInfoService;

import java.util.Locale;

public class WeatherWidget extends AppWidgetProvider {
    public static final String INTENT_ACTION_UPDATE_DATA = "com.cnakhn.faradarscompletion.UPDATE_DATA";
    private static final String TAG = "WeatherWidget";
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, WeatherInfo weatherInfo) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.weather_widget);
        remoteViews.setTextViewText(R.id.widget_weather_temp, String.valueOf((int) weatherInfo.getCityTemp()));
        remoteViews.setTextViewText(R.id.widget_weather_temp_sign, String.format(Locale.US, "%s", Html.fromHtml("&#8451;")));
        /*switch (weatherInfo.getWeatherID() / 100) {
            case 2:
                remoteViews.setTextViewText(R.id.weather_widget_icon, String.valueOf(R.string.wi_thunderstorm));
                break;

            case 3:
                remoteViews.setTextViewText(R.id.weather_widget_icon, String.valueOf(R.string.wi_raindrops));
                break;

            case 5:
                remoteViews.setTextViewText(R.id.weather_widget_icon, String.valueOf(R.string.wi_rain));
                break;

            case 6:
                remoteViews.setTextViewText(R.id.weather_widget_icon, String.valueOf(R.string.wi_snow));
                break;

            case 7:
                remoteViews.setTextViewText(R.id.weather_widget_icon, String.valueOf(R.string.wi_fog));
                break;

            case 8:
                remoteViews.setTextViewText(R.id.weather_widget_icon, String.valueOf(R.string.wi_cloudy));
                break;
            default: break;
        }*/

        Log.i(TAG, "updateAppWidget: " + weatherInfo.getCityTemp());
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        context.startService(new Intent(context, WeatherInfoService.class));
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(INTENT_ACTION_UPDATE_DATA)) {
            WeatherInfo weatherInfo = intent.getParcelableExtra("data");
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, WeatherWidget.class));
            for (int i = 0; i < appWidgetIds.length; i++) {
                updateAppWidget(context, appWidgetManager, appWidgetIds[i], weatherInfo);
            }
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}

