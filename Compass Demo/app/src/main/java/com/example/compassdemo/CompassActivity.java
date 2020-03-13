package com.example.compassdemo;

import android.app.Activity;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.internal.view.SupportMenu;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class CompassActivity extends AppCompatActivity implements SensorEventListener, OnMapReadyCallback {
    private long SensorSendTime;
    private Activity activity;
    private Bundle bundle;
    private float currentDegree = 0.0f;
    private GoogleMap googleMap;
    private AppCompatImageView ivCompassLevel;
    private AppCompatImageView ivIndicator;
    private AppCompatImageView ivPointerIndicatorInner;
    private AppCompatImageView ivRedCircle;
    private AppCompatImageView ivSmallCircleLevel;
    private double lastPitch;
    private double lastRoll;
    private double lastTime;
    private double latitude = 0.0d;
    private double longitude = 0.0d;
    private Sensor mAccelerometer;
    private GoogleApiClient mGoogleApiClient;
    private float[] mLastAccelerometer = new float[3];
    private boolean mLastAccelerometerSet = false;
    private float[] mLastMagnetometer = new float[3];
    private boolean mLastMagnetometerSet = false;
    private LocationRequest mLocationRequest;
    private Sensor mMagnetometer;
    private float[] mOrientation = new float[3];

    /* renamed from: mR */
    private float[] f80mR = new float[9];
    private SensorManager mSensorManager;
    private boolean mapReady = false;
    private float newX;
    private float newY;
    private float pointerFirstPositionX;
    private float pointerFirstPositionY;
    private boolean pointerPosition = true;
    private double previousAzimuthInDegrees = 0.0d;
    private RelativeLayout rlCompassContainer;
    private RelativeLayout rlCompassMapContainer;
    private RelativeLayout rlInnerPosition;
    private float smallCircleRadius;
    private boolean start = false;
    private boolean switchView = false;
    private TextView tvPlaceName;
    private TextView tvQuiblaDegree;

    static /* synthetic */ void lambda$startFusedLocation$1(ConnectionResult connectionResult) {
    }

    public double lowPassPointerLevel(double d, double d2, double d3) {
        double d4 = d3 / (0.25d + d3);
        return (d * d4) + ((1.0d - d4) * d2);
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle2) {
        super.onCreate(bundle2);
        this.activity = this;
        this.bundle = getIntent().getExtras();
        Bundle bundle3 = this.bundle;
        if (bundle3 != null) {
            this.latitude = bundle3.getDouble("lat");
            this.longitude = this.bundle.getDouble("lng");
        }
        setContentView(R.layout.activity_qibla_compass);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle((CharSequence) getString(R.string.quibla_compass));
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (!Utils.isSensorAvailable(this) || !Utils.isAccelerometerAvailable(this)) {
            setNoQiblaAlertDialog();
        }
        init();
    }

    private void setNoQiblaAlertDialog() {
        Drawable drawable;
        Drawable drawable2 = AppCompatResources.getDrawable(this, R.drawable.ic_compass);
        if (drawable2 != null) {
            drawable = DrawableCompat.wrap(drawable2);
            DrawableCompat.setTint(drawable, ContextCompat.getColor(this, R.color.primaryColor));
        } else {
            drawable = null;
        }
        Builder builder = new Builder(this, R.style.MyAlertDialogStyle);
        Builder message = builder.setTitle((CharSequence) getResources().getString(R.string.quibla_compass)).setMessage((CharSequence) getResources().getString(R.string.compass_not_supported));
        StringBuilder sb = new StringBuilder();
        sb.append("<big>");
        sb.append(getResources().getString(R.string.ok));
        sb.append("</big>");
        message.setPositiveButton((CharSequence) Html.fromHtml(sb.toString()), (OnClickListener) $$Lambda$CompassActivity$xF5Va_CmJEf41SyJWP67KpXIGrs.INSTANCE);
        if (drawable != null) {
            builder.setIcon(drawable);
        } else {
            builder.setIcon((int) R.drawable.ic_compass);
        }
        AlertDialog create = builder.create();
        create.show();
        create.getButton(-2).setAllCaps(false);
        create.getButton(-1).setAllCaps(false);
        create.getButton(-3).setAllCaps(false);
        create.show();
    }

    public void onResume() {
        super.onResume();
        this.mSensorManager.registerListener(this, this.mAccelerometer, 1);
        this.mSensorManager.registerListener(this, this.mMagnetometer, 1);
    }

    public void onPause() {
        super.onPause();
        this.mSensorManager.unregisterListener(this, this.mAccelerometer);
        this.mSensorManager.unregisterListener(this, this.mMagnetometer);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        /*int itemId = menuItem.getItemId();
        if (itemId == 16908332) {
            finish();
        } else if (itemId == R.id.view) {
            SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            if (supportMapFragment != null) {
                supportMapFragment.getMapAsync(this);
            }
            if (this.switchView) {
                this.ivRedCircle.setVisibility(View.VISIBLE);
                stopFusedLocation();
                this.rlCompassContainer.setVisibility(View.VISIBLE);
                this.rlCompassMapContainer.setVisibility(View.VISIBLE);
                this.rlInnerPosition.setVisibility(View.VISIBLE);
                this.switchView = false;
            } else if (Utils.checkPermissions(this.activity, Constants.location_permissions, 128)) {
                afterLocationPermissionGranted();
            }
        }*/
        return true;
    }

    public void showMapCompass() {
        if (Validations.isNetworkAvailable(this) && checkPlayServices()) {
            startFusedLocation();
            this.rlCompassContainer.setVisibility(View.VISIBLE);
            this.rlCompassMapContainer.setVisibility(View.VISIBLE);
            this.rlInnerPosition.setVisibility(View.VISIBLE);
            this.ivRedCircle.setVisibility(View.VISIBLE);
            this.switchView = true;
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1287 && i2 == -1) {
            showMapCompass();
        }
        setPermissionsRequest(i);
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        setPermissionsRequest(i);
    }

    private void setPermissionsRequest(int i) {
        if (i != 128) {
            return;
        }
        if (Utils.isAllPermissionsGranted(this.activity, Constants.location_permissions)) {
//            afterLocationPermissionGranted();
        } else {
//            Utils.setSnackbarPermission(this.activity, this.tvPlaceName, getResources().getString(R.string.location_permission), i);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_compass, menu);
        return true;
    }

    private void init() {
        int doCalculate = (int) QuiblaCalculator.doCalculate(this.latitude, this.longitude);
        this.tvPlaceName = (TextView) findViewById(R.id.tvPlaceName);
        String banglaNumber = Utils.getBanglaNumber(Utils.getDistance(this.latitude, this.longitude, 21.422487d, 39.826206d));
        TextView textView = this.tvPlaceName;
        StringBuilder sb = new StringBuilder();
        String str = "\n";
        String str2 = "";
//        sb.append(Utils.getString(this, "city_name").replace(str, " ").replace("; ", " | ").replace("বিভাগঃ ", str2).replace("উপজেলাঃ ", str2).replace("জেলাঃ ", str2).replace("থানাঃ ", str2));
        sb.append(str);
        sb.append(getResources().getString(R.string.qaba_distance));
        sb.append(banglaNumber);
        sb.append(" কিঃমিঃ");
        textView.setText(sb.toString());
        this.tvQuiblaDegree = (TextView) findViewById(R.id.tvQuiblaDegree);
        TextView textView2 = this.tvQuiblaDegree;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(getString(R.string.qibla_direction));
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str2);
        sb3.append(doCalculate);
        sb2.append(Utils.getBanglaNumber(sb3.toString()));
        sb2.append("°");
        textView2.setText(sb2.toString());
        this.ivIndicator = (AppCompatImageView) findViewById(R.id.ivIndicator);
        this.rlCompassContainer = (RelativeLayout) findViewById(R.id.rlCompassContainer);
        this.rlCompassMapContainer = (RelativeLayout) findViewById(R.id.rlCompassMapContainer);
        this.ivSmallCircleLevel = (AppCompatImageView) findViewById(R.id.ivSmallCircleLevel);
        this.rlInnerPosition = (RelativeLayout) findViewById(R.id.rlInnerPosition);
        this.ivPointerIndicatorInner = (AppCompatImageView) findViewById(R.id.ivPointerIndicatorInner);
        this.ivRedCircle = (AppCompatImageView) findViewById(R.id.ivRedCircle);
        this.mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        SensorManager sensorManager = this.mSensorManager;
        if (sensorManager != null) {
            this.mAccelerometer = sensorManager.getDefaultSensor(1);
            this.mMagnetometer = this.mSensorManager.getDefaultSensor(2);
        }
        this.ivCompassLevel = (AppCompatImageView) findViewById(R.id.ivCompassLevel);
        Utils.setImageTint(this.ivCompassLevel, R.drawable.ic_level_pointer, ContextCompat.getColor(getApplicationContext(), R.color.primaryColor));
        RotateAnimation rotateAnimation = new RotateAnimation(this.currentDegree, (float) doCalculate, 1, 0.5f, 1, 0.5f);
        rotateAnimation.setDuration(400);
        rotateAnimation.setFillAfter(true);
        this.ivIndicator.startAnimation(rotateAnimation);
        this.ivPointerIndicatorInner.startAnimation(rotateAnimation);
    }

    public double lowPass(double d, double d2, double d3) {
        double d4 = (double) this.SensorSendTime;
        Double.isNaN(d4);
        double d5 = d3 - d4;
        StringBuilder sb = new StringBuilder();
        sb.append(d5);
        sb.append("");
        Log.d("TIMESEND", sb.toString());
        this.SensorSendTime = (long) d3;
        double d6 = d5 / 1000.0d;
        double d7 = d6 / (d6 + 1.0d);
        return (d * d7) + ((1.0d - d7) * d2);
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        SensorEvent sensorEvent2 = sensorEvent;
        double currentTimeMillis = (double) System.currentTimeMillis();
        if (sensorEvent2.sensor == this.mAccelerometer) {
            this.mLastAccelerometer = sensorEvent2.values;
            this.mLastAccelerometerSet = true;
        } else if (sensorEvent2.sensor == this.mMagnetometer) {
            this.mLastMagnetometer = sensorEvent2.values;
            this.mLastMagnetometerSet = true;
        }
        if (this.mLastAccelerometerSet && this.mLastMagnetometerSet) {
            boolean rotationMatrix = SensorManager.getRotationMatrix(this.f80mR, null, this.mLastAccelerometer, this.mLastMagnetometer);
            SensorManager.getOrientation(this.f80mR, this.mOrientation);
            double d = (double) ((-((float) (Math.toDegrees((double) this.mOrientation[0]) + 360.0d))) % 360.0f);
            double d2 = this.previousAzimuthInDegrees;
            Double.isNaN(d);
            if (Math.abs(d - d2) > 300.0d) {
                this.previousAzimuthInDegrees = d;
            }
            double lowPass = lowPass(d, this.previousAzimuthInDegrees, currentTimeMillis);
            if (this.mapReady) {
                updateCamera((float) lowPass);
            }
            RotateAnimation rotateAnimation = new RotateAnimation((float) this.previousAzimuthInDegrees, (float) lowPass, 1, 0.5f, 1, 0.5f);
            rotateAnimation.setDuration(500);
            rotateAnimation.setFillAfter(true);
            this.rlCompassContainer.startAnimation(rotateAnimation);
            this.rlInnerPosition.startAnimation(rotateAnimation);
            this.previousAzimuthInDegrees = lowPass;
            if (this.pointerPosition) {
                this.pointerFirstPositionX = this.ivCompassLevel.getX();
                this.pointerFirstPositionY = this.ivCompassLevel.getY();
                this.smallCircleRadius = this.ivSmallCircleLevel.getX();
                this.pointerPosition = false;
            }
            if (rotationMatrix) {
                float[] fArr = new float[3];
                SensorManager.getOrientation(this.f80mR, fArr);
                float f = fArr[0];
                double d3 = (double) (fArr[1] * 57.29578f);
                double d4 = (double) (fArr[2] * 57.29578f);
                if (d3 > 90.0d) {
                    Double.isNaN(d3);
                    d3 -= 180.0d;
                }
                if (d3 < -90.0d) {
                    d3 += 180.0d;
                }
                double d5 = d3;
                if (d4 > 90.0d) {
                    Double.isNaN(d4);
                    d4 -= 180.0d;
                }
                if (d4 < -90.0d) {
                    d4 += 180.0d;
                }
                double d6 = d4;
                double currentTimeMillis2 = (double) System.currentTimeMillis();
                if (!this.start) {
                    this.lastTime = currentTimeMillis2;
                    this.lastRoll = d6;
                    this.lastPitch = d5;
                }
                this.start = true;
                double d7 = this.lastTime;
                Double.isNaN(currentTimeMillis2);
                double d8 = (currentTimeMillis2 - d7) / 1000.0d;
                double lowPassPointerLevel = lowPassPointerLevel(d6, this.lastRoll, d8);
                double d9 = d5;
                double d10 = lowPassPointerLevel;
                double lowPassPointerLevel2 = lowPassPointerLevel(d9, this.lastPitch, d8);
                this.lastTime = currentTimeMillis2;
                this.lastRoll = d10;
                this.lastPitch = lowPassPointerLevel2;
                float f2 = this.pointerFirstPositionX;
                double d11 = (double) f2;
                double d12 = (double) f2;
                Double.isNaN(d12);
                double d13 = (d12 * d10) / 90.0d;
                Double.isNaN(d11);
                this.newX = (float) (d11 + d13);
                float f3 = this.pointerFirstPositionY;
                double d14 = (double) f3;
                double d15 = (double) f3;
                Double.isNaN(d15);
                double d16 = (d15 * lowPassPointerLevel2) / 90.0d;
                Double.isNaN(d14);
                this.newY = (float) (d14 + d16);
                this.ivCompassLevel.setX(this.newX);
                this.ivCompassLevel.setY(this.newY);
                if (((double) (this.smallCircleRadius / 3.0f)) < Math.sqrt((d10 * d10) + (lowPassPointerLevel2 * lowPassPointerLevel2))) {
                    Utils.setImageTint(this.ivCompassLevel, R.drawable.ic_error_pointer, SupportMenu.CATEGORY_MASK);
                } else {
                    Utils.setImageTint(this.ivCompassLevel, R.drawable.ic_level_pointer, ContextCompat.getColor(getApplicationContext(), R.color.primaryColor));
                }
            }
        }
    }

    public void onMapReady(GoogleMap googleMap2) {
        this.googleMap = googleMap2;
        this.mapReady = true;
    }

    private void updateCamera(float f) {
        this.googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.builder(this.googleMap.getCameraPosition()).target(new LatLng(this.latitude, this.longitude)).zoom(17.0f).bearing(360.0f - f).build()));
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability instance = GoogleApiAvailability.getInstance();
        int isGooglePlayServicesAvailable = instance.isGooglePlayServicesAvailable(this);
        if (isGooglePlayServicesAvailable == 0) {
            return true;
        }
        if (instance.isUserResolvableError(isGooglePlayServicesAvailable)) {
            instance.getErrorDialog(this, isGooglePlayServicesAvailable, 1).show();
        }
        return false;
    }

    public void startFusedLocation() {
        GoogleApiClient googleApiClient = this.mGoogleApiClient;
        if (googleApiClient == null) {
            this.mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(new ConnectionCallbacks() {
                public void onConnected(Bundle bundle) {
                }

                public void onConnectionSuspended(int i) {
                }
            }).addOnConnectionFailedListener($$Lambda$CompassActivity$KNDqQ4tnENSrCvPH_QZcn8_DLA.INSTANCE).build();
            this.mGoogleApiClient.connect();
            return;
        }
        googleApiClient.connect();
    }

    public void stopFusedLocation() {
        GoogleApiClient googleApiClient = this.mGoogleApiClient;
        if (googleApiClient != null) {
            googleApiClient.disconnect();
        }
    }

    public void registerRequestUpdate(LocationListener locationListener) {
        this.mLocationRequest = LocationRequest.create();
        this.mLocationRequest.setPriority(100);
        this.mLocationRequest.setInterval(1000);
        /*new Handler().postDelayed(new Runnable(locationListener) {
            private final *//* synthetic *//* LocationListener f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                CompassActivity.this.lambda$registerRequestUpdate$2$CompassActivity(this.f$1);
            }
        }, 1000);*/
    }

    public /* synthetic */ void lambda$registerRequestUpdate$2$CompassActivity(LocationListener locationListener) {
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(this.mGoogleApiClient, this.mLocationRequest, locationListener);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
            if (!isGoogleApiClientConnected()) {
                this.mGoogleApiClient.connect();
            }
            registerRequestUpdate(locationListener);
        }
    }

    public boolean isGoogleApiClientConnected() {
        GoogleApiClient googleApiClient = this.mGoogleApiClient;
        return googleApiClient != null && googleApiClient.isConnected();
    }
}
