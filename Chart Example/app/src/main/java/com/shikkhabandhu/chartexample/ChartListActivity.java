package com.shikkhabandhu.chartexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ChartListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_list);

    }

    public void pieChart(View view){
        startActivity(new Intent(ChartListActivity.this, PieChartActivity.class));
    }
}
