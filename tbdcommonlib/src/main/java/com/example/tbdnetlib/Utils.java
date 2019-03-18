package com.example.tbdnetlib;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Base64;
//import cz.msebera.android.httpclient.conn.util.InetAddressUtils;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Utils {
    private Context mContext;

    public Utils(Context context) {
        this.mContext = context;
    }

    public String getCertificateSHA1Fingerprint() {
        PackageManager pm = this.mContext.getPackageManager();
        String packageName = this.mContext.getPackageName();
        int flags = 64;
        PackageInfo packageInfo = null;

        try {
            packageInfo = pm.getPackageInfo(packageName, flags);
        } catch (NameNotFoundException var16) {
            var16.printStackTrace();
        }

        Signature[] signatures = packageInfo.signatures;
        byte[] cert = signatures[0].toByteArray();
        InputStream input = new ByteArrayInputStream(cert);
        CertificateFactory cf = null;

        try {
            cf = CertificateFactory.getInstance("X509");
        } catch (CertificateException var15) {
            var15.printStackTrace();
        }

        X509Certificate c = null;

        try {
            c = (X509Certificate)cf.generateCertificate(input);
        } catch (CertificateException var14) {
            var14.printStackTrace();
        }

        String hexString = null;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(c.getEncoded());
            hexString = byte2HexFormatted(publicKey);
        } catch (CertificateEncodingException | NoSuchAlgorithmException var13) {
            var13.printStackTrace();
        }

        return hexString;
    }

    public static String byte2HexFormatted(byte[] arr) {
        StringBuilder str = new StringBuilder(arr.length * 2);

        for(int i = 0; i < arr.length; ++i) {
            String h = Integer.toHexString(arr[i]);
            int l = h.length();
            if (l == 1) {
                h = "0" + h;
            }

            if (l > 2) {
                h = h.substring(l - 2, l);
            }

            str.append(h.toUpperCase());
            if (i < arr.length - 1) {
                str.append(':');
            }
        }

        return str.toString();
    }

    public String getAppCertHashKey() {
        PackageManager pm = this.mContext.getPackageManager();
        PackageInfo info = null;

        try {
            info = pm.getPackageInfo(this.mContext.getPackageName(), 64);
            Signature[] var3 = info.signatures;
            int var4 = var3.length;
            byte var5 = 0;
            if (var5 < var4) {
                Signature signature = var3[var5];
                MessageDigest md = null;

                try {
                    md = MessageDigest.getInstance("SHA");
                } catch (NoSuchAlgorithmException var9) {
                    var9.printStackTrace();
                }

                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                return hashKey;
            }
        } catch (NameNotFoundException var10) {
            var10.printStackTrace();
        }

        return "";
    }

    // InetAddressUtils class imported from another library
    /*public String getDeviceIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> networkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            Iterator var3 = networkInterfaces.iterator();

            while(var3.hasNext()) {
                NetworkInterface networkInterface = (NetworkInterface)var3.next();
                List<InetAddress> inetAddresses = Collections.list(networkInterface.getInetAddresses());
                Iterator var6 = inetAddresses.iterator();

                while(var6.hasNext()) {
                    InetAddress inetAddress = (InetAddress)var6.next();
                    String sAddr;
                    if (VERSION.SDK_INT > 21) {
                        sAddr = inetAddress.getHostAddress();
                        if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                            return sAddr;
                        }

                        int delim = sAddr.indexOf(37);
                        return delim < 0 ? sAddr : sAddr.substring(0, delim);
                    }

                    if (!inetAddress.isLoopbackAddress()) {
                        sAddr = inetAddress.getHostAddress();
                        boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        if (useIPv4) {
                            if (isIPv4) {
                                return sAddr;
                            }
                        } else if (!isIPv4) {
                            int delim = sAddr.indexOf(37);
                            return delim < 0 ? sAddr : sAddr.substring(0, delim);
                        }
                    }
                }
            }
        } catch (Exception var11) {
            var11.printStackTrace();
        }

        return "";
    }*/

    // InetAddressUtils class imported from another library
    /*public String _getDeviceIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> networkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            Iterator var3 = networkInterfaces.iterator();

            while(var3.hasNext()) {
                NetworkInterface networkInterface = (NetworkInterface)var3.next();
                List<InetAddress> inetAddresses = Collections.list(networkInterface.getInetAddresses());
                Iterator var6 = inetAddresses.iterator();

                while(var6.hasNext()) {
                    InetAddress inetAddress = (InetAddress)var6.next();
                    if (!inetAddress.isLoopbackAddress()) {
                        String sAddr = inetAddress.getHostAddress().toUpperCase();
                        boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        if (useIPv4) {
                            if (isIPv4) {
                                return sAddr;
                            }
                        } else if (!isIPv4) {
                            int delim = sAddr.indexOf(37);
                            return delim < 0 ? sAddr : sAddr.substring(0, delim);
                        }
                    }
                }
            }
        } catch (Exception var11) {
            var11.printStackTrace();
        }

        return "";
    }*/

    public String getApplicationName() {
        PackageManager pm = this.mContext.getPackageManager();

        ApplicationInfo ai;
        try {
            ai = pm.getApplicationInfo(this.mContext.getPackageName(), 0);
        } catch (NameNotFoundException var4) {
            ai = null;
        }

        return (String)((String)(ai != null ? pm.getApplicationLabel(ai) : "(unknown)"));
    }

    // Requiring READ_PHONE_STATE permission
    /*public String getDeviceUUID() {
        TelephonyManager telephonyManager = (TelephonyManager)this.mContext.getSystemService("phone");
        return telephonyManager.getDeviceId();
    }*/

    public String getAdvertisingId(){
//        return 	AdvertisingIdClient.Info.getId();
        return "";
    }

    public String getAndroidId() {
        return Secure.getString(this.mContext.getContentResolver(), "android_id");
    }

    public String getApplicationPackageName() {
        return this.mContext.getApplicationContext().getPackageName();
    }

    public String getAppVersionCode() {
        try {
            PackageInfo pInfo = this.mContext.getPackageManager().getPackageInfo(this.mContext.getPackageName(), 0);
            return String.valueOf(pInfo.versionCode);
        } catch (NameNotFoundException var2) {
            var2.printStackTrace();
            return "unknown";
        }
    }

    public String getAppVersionName() {
        try {
            PackageInfo pInfo = this.mContext.getPackageManager().getPackageInfo(this.mContext.getPackageName(), 0);
            return String.valueOf(pInfo.versionName);
        } catch (NameNotFoundException var2) {
            var2.printStackTrace();
            return "unknown";
        }
    }

    public String getTelephoneCountryISO() {
        TelephonyManager manager = (TelephonyManager)this.mContext.getSystemService(Context.TELEPHONY_SERVICE);
        String countryISO = manager.getSimCountryIso().toUpperCase();
        return countryISO.length() > 0 ? countryISO : manager.getNetworkCountryIso().toUpperCase();
    }

    public String getDeviceModel() {
        return Build.MODEL;
    }

    public String getDeviceManufacturer() {
        return Build.MANUFACTURER;
    }

    public String getDeviceBrand() {
        return Build.BRAND;
    }

    public String getAndroidCurrentAPIVersion() {
        return Integer.toString(VERSION.SDK_INT);
    }
}

