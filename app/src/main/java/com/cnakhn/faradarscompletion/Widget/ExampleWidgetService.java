package com.cnakhn.faradarscompletion.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.cnakhn.faradarscompletion.R;

import java.text.DateFormat;
import java.util.Date;


public class ExampleWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ExampleWidgetItemFactory(getApplicationContext(), intent);
    }

    static class ExampleWidgetItemFactory implements RemoteViewsFactory {
        private Context context;
        private int appWidgetId;
        private String[] exampleData = {"Item one", "Item two", "Item three", "Item four", "Item five", "Item six", "Item seven", "Item eight", "Item nine", "Item ten"};

        ExampleWidgetItemFactory(Context context, Intent intent) {
            this.context = context;
            this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {
            //Connect to data source

        }

        @Override
        public void onDataSetChanged() {
            //Refresh data
            Date date = new Date();
            String timeFormatted = DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
            exampleData = new String[]{"Time\n" + timeFormatted, "Time\n" + timeFormatted, "Time\n" + timeFormatted};
            SystemClock.sleep(1500);
        }

        @Override
        public void onDestroy() {
            //Close data source
        }

        @Override
        public int getCount() {
            return exampleData.length;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.example_widget_item);
            views.setTextViewText(R.id.example_widget_item_text, exampleData[position]);
            Intent fillIntent = new Intent();
            fillIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            views.setOnClickFillInIntent(R.id.example_widget_item_text, fillIntent);
            SystemClock.sleep(500);
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
