package com.cnakhn.faradarscompletion.Widget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RemoteViews;

import com.cnakhn.faradarscompletion.Activities.MainActivity;
import com.cnakhn.faradarscompletion.R;

import static com.cnakhn.faradarscompletion.Widget.ExampleAppWidgetProvider.ACTION_REFRESH;

public class ExampleAppWidgetConfigActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "widget_prefs";
    public static final String WIDGET_BUTTON_TEXT = "widget_button_text";

    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private EditText inputExampleWidgetName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_app_widget_config);
        inputExampleWidgetName = findViewById(R.id.input_example_widget_name);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(RESULT_CANCELED, resultValue);

        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }


    }

    public void confirmWidgetConfig(View view) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        Intent buttonIntent = new Intent(this, MainActivity.class);
        PendingIntent buttonPendingIntent = PendingIntent.getActivity(this, 0, buttonIntent, 0);
        String buttonText = inputExampleWidgetName.getText().toString();

        Intent serviceIntent = new Intent(this, ExampleWidgetService.class);
        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

        Intent clickIntent = new Intent(this, ExampleAppWidgetProvider.class);
        clickIntent.setAction(ACTION_REFRESH);
        PendingIntent clickPendingIntent = PendingIntent.getBroadcast(this, 0, clickIntent, 0);

        RemoteViews views = new RemoteViews(this.getPackageName(), R.layout.example_widget);
        views.setOnClickPendingIntent(R.id.example_widget_button, buttonPendingIntent);
        views.setCharSequence(R.id.example_widget_button, "setText", buttonText);
        views.setInt(R.id.example_widget_button, "setTextColor", ContextCompat.getColor(this, R.color.colorGrey));
        views.setRemoteAdapter(R.id.example_widget_stack_view, serviceIntent);
        views.setEmptyView(R.id.example_widget_stack_view, R.id.example_widget_empty_view);
        views.setPendingIntentTemplate(R.id.example_widget_stack_view, clickPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(WIDGET_BUTTON_TEXT + appWidgetId, buttonText);
        editor.apply();

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }
}