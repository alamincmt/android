package com.shikkhabandhu.chartexample;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class PieChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        getSupportActionBar().setTitle("Pie Chart");

        PieChart pieChart = findViewById(R.id.pieChart);

        ArrayList<PieEntry> dataList = new ArrayList<>();
        dataList.add(new PieEntry(50, "02"));
        dataList.add(new PieEntry(50, "08"));
        dataList.add(new PieEntry(50, "90"));

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(242, 197, 64)); // color yellow
        colors.add(Color.rgb(237, 28, 36)); // color red
        colors.add(Color.rgb(44, 179, 116)); // green color


        PieDataSet pieDataSet = new PieDataSet(dataList, "Marks Data");
//        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(android.R.color.white);

        pieDataSet.setColors(colors);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("88");
        pieChart.setCenterTextSize(25f);
        pieChart.setCenterTextColor(getResources().getColor(android.R.color.white));
        pieChart.setEntryLabelTextSize(20f);
        pieChart.setEntryLabelColor(getResources().getColor(android.R.color.white));

        ///// disabling chart legend
        pieChart.getLegend().setEnabled(false);

        pieChart.setHoleColor(getResources().getColor(R.color.pie_chart_center));
        pieChart.setHoleRadius(50f);
        pieChart.animate();



    }
}
