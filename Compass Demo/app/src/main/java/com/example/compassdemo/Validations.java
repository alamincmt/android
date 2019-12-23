package com.example.compassdemo;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.IntentSender.SendIntentException;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.appcompat.app.AlertDialog.Builder;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class Validations {
    public static final int REQUEST_CODE = 1;

    public static boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        boolean z = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        if (!z) {
            Builder builder = new Builder(activity);
            builder.setMessage((CharSequence) activity.getString(R.string.internet_alert)).setCancelable(false).setPositiveButton((CharSequence) activity.getString(R.string.ok), (OnClickListener) $$Lambda$Validations$wptXNfcRvV7FWj6ghi1Ky5JlBU.INSTANCE);
            builder.create().show();
        }
        return z;
    }

    static /* synthetic */ void lambda$createLocationRequest$1(Activity activity, LocationSettingsResponse locationSettingsResponse) {
        if (activity instanceof CompassActivity) {
            ((CompassActivity) activity).showMapCompass();
        }
    }

    static /* synthetic */ void lambda$createLocationRequest$2(Activity activity, Exception exc) {
        if (exc instanceof ResolvableApiException) {
            try {
                ((ResolvableApiException) exc).startResolutionForResult(activity, Constants.REQUEST_CHECK_SETTINGS);
            } catch (SendIntentException unused) {
            }
        }
    }
}
