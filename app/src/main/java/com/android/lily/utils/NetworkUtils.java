package com.android.lily.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @Author rape flower
 * @Date 2018-06-20 14:53
 * @Describe 网络相关的基本信息
 */
public class NetworkUtils {

    private static int debugIpCount;

    /**
     * gsm和其他网络类型不同，在TelephonyManager中被定义为@hide
     */
    private static final int NETWORK_TYPE_GSM = 16;
    private static final String NETWORK_2_G = "2G";
    private static final String NETWORK_3_G = "3G";
    private static final String NETWORK_4_G = "4G";
    private static final String NETWORK_UNKNOWN = "unknown";
    private static final String WIFI = "WIFI";
    private static final String NO_NETWORK = "No network";

    private NetworkUtils() {
          /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
        //throw new AssertionError("No instances");
    }


    /**
     * 获得当前连接的网络类型
     *
     * @param context
     * @return
     */
    public static String getConnectType(Context context) {
        if (isWifi(context)) {
            return WIFI;
        } else if (isMobile(context)) {
            return getNetworkType(context);
        } else {
            return NO_NETWORK;
        }
    }

    /**
     * 判断当前网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (null != connectivity) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (null != info && info.isConnected()) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断当前网络是否是wifi连接
     */
    public static boolean isWifi(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity == null || connectivity.getActiveNetworkInfo() == null) {
                return false;
            }
            return connectivity.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 判断当前网络是否是手机网络
     *
     * @param context
     * @return boolean
     */
    public static boolean isMobile(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetInfo = connectivity.getActiveNetworkInfo();
            if (activeNetInfo != null
                    && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 在判断是手机网络(isMobile())后，通过
     * 该方法可以获得当前手机网络的具体类型(2G,3G,4G)
     *
     * @param context
     * @return
     */
    public static String getNetworkType(Context context) {
        TelephonyManager telephony = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (telephony == null) {
            return NETWORK_UNKNOWN;
        }
        switch (telephony.getNetworkType()) {
            case NETWORK_TYPE_GSM:
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return NETWORK_2_G;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return NETWORK_3_G;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return NETWORK_4_G;
            default:
                return NETWORK_UNKNOWN;
        }
    }

    /**
     * 当前网络使用wifi时的ip地址
     *
     * @return
     */
    public static String getLocalIpOnWifi(Context context) {
        String ip = "";
        //获取wifi服务
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        if (wifiManager.isWifiEnabled()) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            ip = intToIp(ipAddress);
        }
        return ip;
    }

    private static String intToIp(int i) {
        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }

    /**
     * 当前网络使用手机数据时的ip地址
     *
     * @return
     */
    public static String getLocalIpOnMobile() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface networkInterface = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddress = networkInterface.getInetAddresses(); enumIpAddress.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddress.nextElement();
                    //这里是ipv4,如果是ipv6的话用下面注释掉的判断
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        //if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet6Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 打开网络设置界面
     */
    public static void openNetworkSetting(Context context) {
        Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 判断网络是否可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isAvailable();
    }
}
