package com.cnakhn.faradarscompletion.Weather;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.util.AttributeSet;

import com.cnakhn.faradarscompletion.R;

import java.util.Date;

public class TextViewWeather extends androidx.appcompat.widget.AppCompatTextView {
    public TextViewWeather(Context context) {
        super(context);
        init(context);
    }

    public TextViewWeather(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TextViewWeather(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        Typeface weatherFont = Typeface.createFromAsset(context.getAssets(), "fonts/ic_weathers.ttf");
        setTypeface(weatherFont);
    }

    public void setWeatherIcon(int id, long sunrise, long sunset) {
        String icWeatherCode = "";
        if (id == 800) {
            long currentTime = new Date().getTime();
            if (currentTime >= sunrise && currentTime < sunset) {
                icWeatherCode = getResources().getString(R.string.wi_day_sunny);
            } else {
                icWeatherCode = getResources().getString(R.string.wi_night_clear);
            }
        } else {
            id /= 100;
            switch (id) {
                case 2:
                    icWeatherCode = getResources().getString(R.string.wi_thunderstorm);
                    break;

                case 3:
                    icWeatherCode = getResources().getString(R.string.wi_raindrops);
                    break;

                case 5:
                    icWeatherCode = getResources().getString(R.string.wi_rain);
                    break;

                case 6:
                    icWeatherCode = getResources().getString(R.string.wi_snow);
                    break;

                case 7:
                    icWeatherCode = getResources().getString(R.string.wi_fog);
                    break;

                case 8:
                    icWeatherCode = getResources().getString(R.string.wi_cloudy);
                    break;
                default: break;
            }
        }
        setText(Html.fromHtml(icWeatherCode));
    }
}
