package com.example.compassdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void gotoCompass(View view) {
        startActivity(new Intent(MainActivity.this, CompassActivity.class).putExtra("lat", 23.745002).putExtra("lng", 90.355151));
    }
}
