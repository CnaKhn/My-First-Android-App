package com.cnakhn.faradarscompletion.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.cnakhn.faradarscompletion.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ChartsActivity extends AppCompatActivity {
    RelativeLayout layoutCharts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);
        layoutCharts = findViewById(R.id.layout_charts);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Line Chart").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                layoutCharts.removeAllViews();
                lineChart();
                return false;
            }
        });

        menu.add("Pie Chart").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                layoutCharts.removeAllViews();
                pieChart();
                return false;
            }
        });

        menu.add("Clear").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                layoutCharts.removeAllViews();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void lineChart() {
        Random random = new Random();
        int randNum = random.nextInt((100 * 67));
        LineChart chart = new LineChart(this);
        chart.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        chart.setBorderColor(Color.WHITE);
        layoutCharts.addView(chart);

        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < randNum; i++) {
            entries.add(new Entry(Math.abs(i), i + randNum));
        }

        LineDataSet dataSet = new LineDataSet(entries, "sin");
        dataSet.setColor(Color.RED);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setDrawCircles(false);
        dataSet.setDrawCircleHole(false);
        chart.animateX(2000);
        chart.setData(new LineData(dataSet));
        chart.invalidate();
    }

    private void pieChart() {
        PieChart chart = new PieChart(this);
        chart.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        layoutCharts.addView(chart);

        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(4f, "C"));
        entries.add(new PieEntry(9f, "C#"));
        entries.add(new PieEntry(13f, "PHP"));
        entries.add(new PieEntry(20f, "Swift"));
        entries.add(new PieEntry(46f, "JavaScript"));
        entries.add(new PieEntry(49f, "C++"));
        entries.add(new PieEntry(61f, "Kotlin"));
        entries.add(new PieEntry(70f, "Java"));

        PieDataSet dataSet = new PieDataSet(entries, "Programming Language Usage");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        chart.animateXY(1500, 1000);
        chart.setData(new PieData(dataSet));

    }
}