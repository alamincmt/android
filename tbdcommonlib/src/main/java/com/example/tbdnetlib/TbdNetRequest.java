package com.example.tbdnetlib;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TbdNetRequest {

    private Context context;
    private Utils utils;

    public TbdNetRequest(Context context){
        this.context = context;
        this.utils = new Utils(context);
        String sha1Cert  = utils.getCertificateSHA1Fingerprint();
        Log.d("Sha1Cert", sha1Cert);
    }

    private String response = "";
    private URL url;
    private TbdNetLibListener listener;

    public TbdNetRequest(){
        this.listener = null;
    }

    public void POST(String url, HashMap<String, String> headerParams){
        // Checking internet connectivity
        if(!TbdNetLibUtils.isNetworkAvailable(context)){
            this.listener.onNetworkFailed("Network connection failed!");
            return;
        }

        MakeAsyncRequest makeAsyncRequest = new MakeAsyncRequest("POST", url, null, headerParams);
        makeAsyncRequest.execute();
    }

    public void POST(String url, HashMap<String, String> hashMap, HashMap<String, String> headerParams){
        // Checking internet connectivity
        if(!TbdNetLibUtils.isNetworkAvailable(context)){
            this.listener.onNetworkFailed("Network connection failed!");
            return;
        }

        MakeAsyncRequest makeAsyncRequest = new MakeAsyncRequest("POST", url, hashMap, headerParams);
        makeAsyncRequest.execute();
    }

    public void SaveDeviceInformation(String url, HashMap<String, String> headerParams){
        // Checking internet connectivity
        if(!TbdNetLibUtils.isNetworkAvailable(context)){
            this.listener.onNetworkFailed("Network connection failed!");
            return;
        }

        MakeAsyncRequest makeAsyncRequest = new MakeAsyncRequest("POST", url, getDeviceInformation(context), headerParams);
        makeAsyncRequest.execute();
    }

    public void GET(String url, HashMap<String, String> headerParams){
        // Checking internet connectivity
        if(!TbdNetLibUtils.isNetworkAvailable(context)){
            this.listener.onNetworkFailed("Network connection failed!");
            return;
        }

        MakeAsyncRequest makeAsyncRequest = new MakeAsyncRequest("GET", url, null, headerParams);
        makeAsyncRequest.execute();
    }

    public void GET(String url, HashMap<String, String> hashMap, HashMap<String, String> headerParams){
        // Checking internet connectivity
        if(!TbdNetLibUtils.isNetworkAvailable(context)){
            this.listener.onNetworkFailed("Network connection failed!");
            return;
        }

        MakeAsyncRequest makeAsyncRequest = new MakeAsyncRequest("GET", url, hashMap, headerParams);
        makeAsyncRequest.execute();
    }

    public HashMap<String, String> getDeviceInformation(Context context) {
        HashMap<String, String> params = new HashMap<>();

        String wifi_status = "no";
        WifiManager wifi = (WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifi.isWifiEnabled()){
            wifi_status = "yes";
        }

        String uuid = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        String manufacturer = Build.MANUFACTURER;
        String brand = Build.BRAND;
        String product = Build.PRODUCT;
        String model = Build.MODEL;
        String device_os = Build.VERSION.RELEASE;
        String device_sdk = String.valueOf(Build.VERSION.SDK_INT);
        String sha1CertificateFingerPrint = utils.getCertificateSHA1Fingerprint();
        String telephoneCountryISO = utils.getTelephoneCountryISO();

        String dpi = "";

        if (displayMetrics.densityDpi <= DisplayMetrics.DENSITY_MEDIUM) {
            dpi = "mdpi";
        } else if (displayMetrics.densityDpi <= DisplayMetrics.DENSITY_HIGH) {
            dpi = "hdpi";
        } else if (displayMetrics.densityDpi <= DisplayMetrics.DENSITY_XHIGH) {
            dpi = "xhdpi";
        } else if (displayMetrics.densityDpi <= DisplayMetrics.DENSITY_XXHIGH) {
            dpi = "xxhdpi";
        } else if (displayMetrics.densityDpi <= DisplayMetrics.DENSITY_XXXHIGH) {
            dpi = "xxxhdpi";
        }

        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;

        String versionName = "", versionCode = "";
        try {
            info = manager.getPackageInfo(
                    context.getPackageName(), 0);

            versionName = info.versionName;
            versionCode = String.valueOf(info.versionCode);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        params.put("api_key", "^)@$!");
        params.put("device_type", "2");
        params.put("device_manufacturer", manufacturer);
        params.put("device_brand", brand);
        params.put("device_product", product);
        params.put("device_model", model);
        params.put("device_os_version", device_os);
        params.put("device_sdk_version", device_sdk);
//        params.put("device_imei", imei);

        params.put("device_uuid", uuid);
//        params.put("advertising_id", advertId);

        params.put("device_uuid", uuid);
        params.put("device_width", String.valueOf(width));
        params.put("device_height", String.valueOf(height));
        params.put("device_density", dpi);
        params.put("app_version_name", versionName);
        params.put("app_version_code", versionCode);
        params.put("status", "1");
        params.put("wifi_status", wifi_status);
        params.put("device_token", "ofoskfjososfsojodjso44654sdsj");

        Log.d("information", params.toString());


        return params;
    }

    /*private String getHardwareAndSoftwareInfo() {

        return getString(R.string.serial) + " " + Build.SERIAL + "\n" +
                getString(R.string.model) + " " + Build.MODEL + "\n" +
                getString(R.string.id) + " " + Build.ID + "\n" +
                getString(R.string.manufacturer) + " " + Build.MANUFACTURER + "\n" +
                getString(R.string.brand) + " " + Build.BRAND + "\n" +
                getString(R.string.type) + " " + Build.TYPE + "\n" +
                getString(R.string.user) + " " + Build.USER + "\n" +
                getString(R.string.base) + " " + Build.VERSION_CODES.BASE + "\n" +
                getString(R.string.incremental) + " " + Build.VERSION.INCREMENTAL + "\n" +
                getString(R.string.sdk) + " " + Build.VERSION.SDK + "\n" +
                getString(R.string.board) + " " + Build.BOARD + "\n" +
                getString(R.string.host) + " " + Build.HOST + "\n" +
                getString(R.string.fingerprint) + " " + Build.FINGERPRINT + "\n" +
                getString(R.string.versioncode) + " " + Build.VERSION.RELEASE;
    }*/

    private JSONObject readStream(InputStream in) {
        BufferedReader reader = null;
        JSONObject jsonObject = null;
        StringBuffer displayMessage = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                displayMessage.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            if(isJSONValid(displayMessage.toString())){
                jsonObject = new JSONObject(displayMessage.toString());
//                this.listener.onSuccess(jsonObject);
            }else{
//                this.listener.onError("Invalid JSON response!");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private String getPOSTDataString(HashMap<String, String> hashMap) throws UnsupportedEncodingException {
        StringBuilder stringBuilder = new StringBuilder();
        boolean b = true;
        Iterator iterator = hashMap.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry)iterator.next();
            if(b)b=false;
            else stringBuilder.append("&");
            stringBuilder.append(URLEncoder.encode((String)((String)entry.getKey()),(String)"UTF-8"));
            stringBuilder.append("=");
            stringBuilder.append(URLEncoder.encode((String)((String)entry.getValue()),(String)"UTF-8"));
        }
        return  stringBuilder.toString();
    }

    public void setTBDAdsLibListener(TbdNetLibListener listener) {
        this.listener = listener;
    }

    public interface TbdNetLibListener {

        void onError(String message);

        void onSuccess(JSONObject jsonObject);

        void onFailure(String message, int errorCode);

        void onNetworkFailed(String message);

    }

    public boolean isJSONValid(String jsonString) {
        try {
            new JSONObject(jsonString);
        } catch (JSONException ex) {
            return false;
        }
        return true;
    }

    public class MakeAsyncRequest extends AsyncTask<String, Integer, JSONObject>{

        private String methodType, stringUrl;
        private HashMap<String, String> params;
        private HashMap<String, String> headerParams;
        private int requestResultCode = 0;

        public MakeAsyncRequest(String methodType, String url, HashMap<String, String> params, HashMap<String, String> headerParams){
            this.methodType = methodType;
            this.stringUrl = url;
            this.params = params;
            this.headerParams = headerParams;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            JSONObject jsonObject = null;
            try{
                url = new URL(stringUrl);
                response = "";
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setReadTimeout(90000);
                connection.setConnectTimeout(90000);

                if(headerParams != null){
                    Iterator it = headerParams.entrySet().iterator();
                    Map.Entry firstEntry = (Map.Entry)it.next();
                    connection.setRequestProperty(firstEntry.getKey().toString(), "Bearer " + firstEntry.getValue().toString());
                }

                if(methodType.equals("POST")){
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//                    connection.setFixedLengthStreamingMode(getPOSTDataString(params).getBytes().length);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    OutputStream outputStream = connection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter((Writer)new OutputStreamWriter(outputStream,"UTF-8"));
                    if(params != null){
                        bufferedWriter.write(getPOSTDataString(params));
                    }else{
                        bufferedWriter.write("");
                    }
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    requestResultCode = connection.getResponseCode();

                    if((connection.getResponseCode() == HttpURLConnection.HTTP_OK) || (connection.getResponseCode() == HttpURLConnection.HTTP_CREATED)){
                        jsonObject = readStream(new BufferedInputStream(connection.getInputStream()));
                        return jsonObject;
                    }
                    response="";
                    return jsonObject;
                }else if(methodType.equals("GET")){
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(90000);
                    connection.setConnectTimeout(90000);
                    if(params != null){
                        connection.setFixedLengthStreamingMode(getPOSTDataString(params).getBytes().length);
                        connection.setDoInput(true);
                        connection.setDoOutput(true);
                        OutputStream outputStream = connection.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter((Writer)new OutputStreamWriter(outputStream,"UTF-8"));
                        bufferedWriter.write(getPOSTDataString(params));
                        bufferedWriter.flush();
                        bufferedWriter.close();
                        outputStream.close();
                    }

                    requestResultCode = connection.getResponseCode();

                    if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                        jsonObject = readStream(connection.getInputStream());
                        return jsonObject;
                    }
//                    listener.onFailure("Request Failed! ", connection.getResponseCode());
                    response="";
                    return jsonObject;
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);

            if(jsonObject != null && isJSONValid(jsonObject.toString())){
                listener.onSuccess(jsonObject);
            }else{
                listener.onFailure("Error occurred!", requestResultCode);
            }
        }
    }
}
