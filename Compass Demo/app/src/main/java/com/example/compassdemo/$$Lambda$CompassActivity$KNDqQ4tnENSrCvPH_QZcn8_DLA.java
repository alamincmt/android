package com.example.compassdemo;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

/* renamed from: com.muslim.compass.-$$Lambda$CompassActivity$KNDqQ4tnENSrCvPH_-QZcn8_DLA reason: invalid class name */
/* compiled from: lambda */
public final /* synthetic */ class $$Lambda$CompassActivity$KNDqQ4tnENSrCvPH_QZcn8_DLA implements OnConnectionFailedListener {
    public static final /* synthetic */ $$Lambda$CompassActivity$KNDqQ4tnENSrCvPH_QZcn8_DLA INSTANCE = new $$Lambda$CompassActivity$KNDqQ4tnENSrCvPH_QZcn8_DLA();

    private /* synthetic */ $$Lambda$CompassActivity$KNDqQ4tnENSrCvPH_QZcn8_DLA() {
    }

    public final void onConnectionFailed(ConnectionResult connectionResult) {
        CompassActivity.lambda$startFusedLocation$1(connectionResult);
    }
}
