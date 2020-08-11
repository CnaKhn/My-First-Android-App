package com.cnakhn.faradarscompletion;

import android.content.Context;
import android.content.SharedPreferences;

public class IntroSliderPrefManager {
    private Context context;
    private SharedPreferences preferences;
    private static final String PREF_NAME = "sliderPref";
    private static final String KEY_START = "sliderStart";

    public IntroSliderPrefManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public boolean startSlider() {
        return preferences.getBoolean(KEY_START, true);
    }

    public void setStartSlider(boolean start) {
        preferences.edit().putBoolean(KEY_START, start).apply();
    }
}
