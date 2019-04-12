package com.example.grantalarm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class LocalWordService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("Checking...", intent.getStringExtra("data"));
        return null;
    }
}
