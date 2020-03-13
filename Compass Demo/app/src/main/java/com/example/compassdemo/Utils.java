package com.example.compassdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.hardware.SensorManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.StringTokenizer;

import static com.example.compassdemo.Validations.isNetworkAvailable;

public class Utils {
    private static String[] bngNumbers = {"০", "১", "২", "৩", "৪", "৫", "৬", "৭", "৮", "৯"};
    private static String[] engNumbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private static Toast mToast;

    public static void checkAdMobInternetAndShow(Activity activity) {
        if (isNetworkAvailable(activity)) {
        }
    }

    public static String getEnglishNumber(String str) {
        if (str == null) {
            return null;
        }
        int i = 0;
        while (true) {
            String[] strArr = engNumbers;
            if (i >= strArr.length) {
                return str;
            }
            str = str.replace(bngNumbers[i], strArr[i]);
            i++;
        }
    }

    public static String getBanglaNumber(String str) {
        if (str == null) {
            return null;
        }
        int i = 0;
        while (true) {
            String[] strArr = engNumbers;
            if (i >= strArr.length) {
                return str;
            }
            str = str.replace(strArr[i], bngNumbers[i]);
            i++;
        }
    }

    public static String getBanglaNumber(int i) {
        return getBanglaNumber(String.valueOf(i));
    }

    public static String getActualTime(String str, String str2, String str3) {
        int i;
        int i2;
        String str4;
        String str5 = " ";
        String str6 = "";
        String replace = str.replace(str5, str6).replace(str5, str6).replace("am", str6).replace("pm", str6).replace("AM", str6).replace("PM", str6).replace("Am", str6).replace("Pm", str6);
        String str7 = ":";
        StringTokenizer stringTokenizer = new StringTokenizer(replace, str7);
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (stringTokenizer.hasMoreTokens()) {
            i5 = Integer.parseInt(stringTokenizer.nextToken());
            i4 = Integer.parseInt(stringTokenizer.nextToken());
        }
        int parseInt = Integer.parseInt(str2);
        String str8 = "before";
        String str9 = "FFFFFFFF";
        if (str3.equals(str8)) {
            if (parseInt >= 60) {
                i3 = parseInt / 60;
            }
            int i6 = parseInt - (i3 * 60);
            i = i5 - i3;
            Log.d(str9, str8);
            if (i4 >= i6) {
                i2 = i4 - i6;
            } else {
                i2 = (i4 + 60) - i6;
                i--;
            }
        } else {
            Log.d(str9, "after");
            i2 = i4 + parseInt;
            if (i2 >= 60) {
                i = i5 + (i2 / 60);
                i2 %= 60;
            } else {
                i = i5 + 0;
            }
        }
        if (i == 0) {
            i = 12;
        }
        if (i2 < 10) {
            StringBuilder sb = new StringBuilder();
            sb.append(i);
            sb.append(":0");
            sb.append(i2);
            str4 = sb.toString();
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(i);
            sb2.append(str7);
            sb2.append(i2);
            str4 = sb2.toString();
        }
        Log.d("Actual alarm time", str4);
        return str4;
    }

    public static String getMyCurrentDate() {
        String str = "EEEE dd MMM, yyyy";
        String charSequence = new SimpleDateFormat(str, Locale.ENGLISH).format(Long.valueOf(new Date().getTime())).toString();
        System.out.println(charSequence);
        return charSequence;
    }

    public static String getNotificationDate(int i, int i2, int i3) {
        String str = "-";
        Calendar instance = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M-d-yyyy", Locale.ENGLISH);
        Date date = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(i2);
            sb.append(str);
            sb.append(i);
            sb.append(str);
            sb.append(i3);
            date = simpleDateFormat.parse(sb.toString());
            instance.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String charSequence = new SimpleDateFormat("MMM\ndd", Locale.ENGLISH).format(Long.valueOf(date.getTime())).toString();
        Log.d("IOMO", charSequence);
        return charSequence;
    }

    public static String getMySelectedDate(int i, int i2, int i3) {
        String str = "-";
        Calendar instance = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M-d-yyyy", Locale.ENGLISH);
        Date date = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(i2);
            sb.append(str);
            sb.append(i);
            sb.append(str);
            sb.append(i3);
            date = simpleDateFormat.parse(sb.toString());
            instance.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String charSequence = new SimpleDateFormat("EEEE dd MMM, yyyy", Locale.ENGLISH).format(Long.valueOf(date.getTime())).toString();
        Log.d("IOMO", charSequence);
        return charSequence;
    }

    public static String getBanglaMonthWeek(String str) {
        return str.replace("Jan", "জানুয়ারি").replace("Feb", "ফেব্রুয়ারি").replace("Mar", "মার্চ").replace("Apr", "এপ্রিল").replace("May", "মে").replace("Jun", "জুন").replace("Jul", "জুলাই").replace("Aug", "আগষ্ট").replace("Sep", "সেপ্টেম্বর").replace("Oct", "অক্টোবর").replace("Nov", "নভেম্বর").replace("Dec", "ডিসেম্বর").replace("Saturday", "শনিবার").replace("Sunday", "রবিবার").replace("Monday", "সোমবার").replace("Tuesday", "মঙ্গলবার").replace("Wednesday", "বুধবার").replace("Thursday", "বৃহস্পতিবার").replace("Friday", "শুক্রবার");
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0059 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int countryCode(Activity r7) {
        throw new UnsupportedOperationException("Method not decompiled: com.muslim.prayerTimes.utils.Utils.countryCode(android.app.Activity):int");
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int[] countryCalc(String r8) {
        throw new UnsupportedOperationException("Method not decompiled: com.muslim.prayerTimes.utils.Utils.countryCalc(java.lang.String):int[]");
    }

    /* access modifiers changed from: private */
    public static void goToSettings(Activity activity, int i) {
        StringBuilder sb = new StringBuilder();
        sb.append("package:");
        sb.append(activity.getPackageName());
        activity.startActivityForResult(new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.parse(sb.toString())), i);
    }

    public static boolean checkPermissions(Activity activity, String[] strArr, int i) {
        ArrayList arrayList = new ArrayList();
        for (String str : strArr) {
            if (ContextCompat.checkSelfPermission(activity, str) != 0) {
                arrayList.add(str);
            }
        }
        if (arrayList.isEmpty()) {
            return true;
        }
        ActivityCompat.requestPermissions(activity, (String[]) arrayList.toArray(new String[arrayList.size()]), i);
        return false;
    }

    public static boolean isAllPermissionsGranted(Activity activity, String[] strArr) {
        for (String checkSelfPermission : strArr) {
            if (ContextCompat.checkSelfPermission(activity, checkSelfPermission) != 0) {
                return false;
            }
        }
        return true;
    }

    public static String getCountryCode(String str) {
        String[] iSOCountries = Locale.getISOCountries();
        HashMap hashMap = new HashMap();
        for (String str2 : iSOCountries) {
            hashMap.put(new Locale("", str2).getDisplayCountry(), str2);
        }
        return (String) hashMap.get(str);
    }

    public static int getStatusBarHeight(Activity activity) {
        int identifier = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            return activity.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public static void setImageTint(AppCompatImageView appCompatImageView, int i, int i2) {
        appCompatImageView.setImageResource(i);
        ImageViewCompat.setImageTintList(appCompatImageView, ColorStateList.valueOf(i2));
    }

    public static boolean isSensorAvailable(Activity activity) {
        SensorManager sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        return (sensorManager == null || sensorManager.getDefaultSensor(2) == null) ? false : true;
    }

    public static boolean isAccelerometerAvailable(Context context) {
        return context.getPackageManager().hasSystemFeature("android.hardware.sensor.accelerometer");
    }

    public static int getDistance(double d, double d2, double d3, double d4) {
        Location location = new Location("point A");
        location.setLatitude(d);
        location.setLongitude(d2);
        Location location2 = new Location("point B");
        location2.setLatitude(d3);
        location2.setLongitude(d4);
        return Math.round(location.distanceTo(location2)) / 1000;
    }
}
