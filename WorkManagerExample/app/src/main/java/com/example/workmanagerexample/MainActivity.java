package com.example.workmanagerexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void oneTimeWork(View view) {
        WorkRequest locationUploadWorkRequest =
                new OneTimeWorkRequest.Builder(LocationUploadWorker.class)
                        .setConstraints(new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).setRequiresStorageNotLow(true).build())
                        .build();
        WorkManager.getInstance(MainActivity.this).enqueue(locationUploadWorkRequest);


        // to cancel worker class
//        WorkManager.getInstance(MainActivity.this).cancelWorkById(locationUploadWorkRequest.getId());
    }
}