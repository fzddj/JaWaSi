package com.makedream.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.io.File;
import java.util.List;
import java.util.Locale;

/**
 * 获取手机相关参数的工具类
 */
public class TelephoneUtil {
	private static final String TAG = "TelephoneUtil";

	/**
	 * 统计SDK新增接口，机型，增加前缀
	 */
	public static final String CONST_STRING_PREFIX_MAC = "HIM_";

	/**
	 * 统计SDK新增接口开关，true 打开，false关闭
	 */
	public static boolean switchPrefix = false;

	/**
	 * 取得IMEI号
	 * @param ctx
	 * @return String
	 */
	public static String getIMEI(Context ctx) {
		TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
		if (tm == null)
			return "";
		
		String result = tm.getDeviceId();
		if (result == null)
			return "";
		
		return result;
	}

	/**
	 * 取得IMSI号
	 * @param ctx
	 * @return String
	 */
	public static String getIMSI(Context ctx) {
		TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
		if (tm == null)
			return "";
		
		String result = tm.getSubscriberId();
		if (result == null)
			return "";

		return result;
	}


	/**
	 * 获取手机型号，若开启前缀开关，会有"HiLauncherM_"前缀 如 HiLauncherM_milestone
	 *                                         否则为milestone<br>
	 *                                         判断机型时候，建议使用 contains 方法比对
	 * @return String
	 */
	public static String getMachineName() {
		/**
		 * 开关打开，添加后缀
		 */
		if (switchPrefix) {
			return CONST_STRING_PREFIX_MAC + Build.MODEL;
		} else {
			return Build.MODEL;
		}
	}

	/**
	 * 获取字符串型的固件版本，如1.5、1.6、2.1
	 * @return String
	 */
	public static String getFirmWareVersion() {
		final String version_3 = "1.5";
		final String version_4 = "1.6";
		final String version_5 = "2.0";
		final String version_6 = "2.0.1";
		final String version_7 = "2.1";
		final String version_8 = "2.2";
		final String version_9 = "2.3";
		final String version_10 = "2.3.3";
		final String version_11 = "3.0";
		final String version_12 = "3.1";
		final String version_13 = "3.2";
		final String version_14 = "4.0";
		final String version_15 = "4.0.3";
		final String version_16 = "4.1.1";
		final String version_17 = "4.2";
		final String version_18 = "4.3";
		final String version_19 = "4.4";
		final String version_20 = "4.4W";
		final String version_21 = "5.0";
		final String version_22 = "5.1";
		final String version_23 = "6.0";
		String versionName = "";
		try {
			int version = Integer.parseInt(Build.VERSION.SDK);
			switch (version) {
			case 3:
				versionName = version_3;
				break;
			case 4:
				versionName = version_4;
				break;
			case 5:
				versionName = version_5;
				break;
			case 6:
				versionName = version_6;
				break;
			case 7:
				versionName = version_7;
				break;
			case 8:
				versionName = version_8;
				break;
			case 9:
				versionName = version_9;
				break;
			case 10:
				;
				versionName = version_10;
				break;
			case 11:
				versionName = version_11;
				break;
			case 12:
				versionName = version_12;
				break;
			case 13:
				versionName = version_13;
				break;
			case 14:
				versionName = version_14;
				break;
			case 15:
				versionName = version_15;
				break;
			case 16:
				versionName = version_16;
				break;
			case 17:
				versionName = version_17;
				break;
			case 18:
				versionName = version_18;
				break;
			case 19:
				versionName = version_19;
				break;
			case 20:
				versionName = version_20;
				break;
			case 21:
				versionName = version_21;
				break;
			case 22:
				versionName = version_22;
				break;
			case 23:
				versionName = version_23;
				break;
			default:
				versionName = version_7;
			}
		} catch (Exception e) {
			versionName = version_7;
		}
		return versionName;
	}

	/**
	 * 获取软件版本名称
	 */
	public static String getVersionName(Context ctx) {
		return getVersionName(ctx, ctx.getPackageName());
	}

	/**
	 * 获取versionName
	 * @param context
	 * @param packageName
	 * @return String
	 */
	public static String getVersionName(Context context, String packageName) {
		String versionName = "";
		try {
			PackageInfo packageinfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_META_DATA);
			versionName = packageinfo.versionName;
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
		return versionName;
	}

	/**
	 * 获取软件版本号 code
	 * @param ctx
	 * @return int
	 */
	public static int getVersionCode(Context ctx) {
		return getVersionCode(ctx, ctx.getPackageName());
	}

	/**
	 * 获取软件版本号 code
	 * @param ctx
	 * @param packageName
	 * @return int
	 */
	public static int getVersionCode(Context ctx, String packageName) {
		int versionCode = 0;
		try {
			PackageInfo packageinfo = ctx.getPackageManager().getPackageInfo(packageName, PackageManager.GET_INSTRUMENTATION);
			versionCode = packageinfo.versionCode;
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
		return versionCode;
	}

	/**
	 * 网络是否可用
	 * @param context
	 * @return boolean
	 */
	public synchronized static boolean isNetworkAvailable(Context context) {
		boolean result = false;
		if (context == null) {
			return result;
		}
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = null;
		if (null != connectivityManager) {
			networkInfo = connectivityManager.getActiveNetworkInfo();
			if (null != networkInfo && networkInfo.isAvailable() && networkInfo.isConnected()) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * wifi是否启动
	 * @param ctx
	 * @return boolean
	 */
	public static boolean isWifiEnable(Context ctx) {
		ConnectivityManager tele = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (tele.getActiveNetworkInfo() == null || !tele.getActiveNetworkInfo().isAvailable()) {
			return false;
		}
		int type = tele.getActiveNetworkInfo().getType();
		return type == ConnectivityManager.TYPE_WIFI;
	}

	/**
	 * 返回网络连接方式
	 * @param ctx
	 * @return int
	 */
	public static int getNetworkState(Context ctx) {
		if (isWifiEnable(ctx)) {
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * sim卡是否存在
	 * @param ctx
	 * @return boolean
	 */
	public static boolean isSimExist(Context ctx) {
		TelephonyManager manager = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
		if (manager.getSimState() == TelephonyManager.SIM_STATE_ABSENT)
			return false;
		else
			return true;
	}

	/**
	 * sd卡是否存在
	 * @return boolean
	 */
	public static boolean isSdcardExist() {
		return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}

	/**
	 * 返回屏幕分辨率,字符串型。如 320x480
	 * @param ctx
	 * @return String
	 */
	public static String getScreenResolution(Context ctx) {
		DisplayMetrics metrics = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) ctx.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(metrics);
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;
		String resolution = width + "x" + height;
		return resolution;
	}

	/**
	 * 返回屏幕分辨率,数组型
	 * @param ctx
	 * @return int[]
	 */
	public static int[] getScreenResolutionXY(Context ctx) {
		int[] resolutionXY = new int[2];
		if (resolutionXY[0] != 0) {
			return resolutionXY;
		}
		DisplayMetrics metrics = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) ctx.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(metrics);
		resolutionXY[0] = metrics.widthPixels;
		resolutionXY[1] = metrics.heightPixels;
		return resolutionXY;
	}

	/**
	 * 返回屏幕密度
	 * @param ctx
	 * @return float
	 */
	public static float getScreenDensity(Context ctx) {
		return ctx.getResources().getDisplayMetrics().density;
	}

	/**
	 * 查询系统广播
	 * @param ctx
	 * @param actionName
	 * @return boolean
	 */
	public static boolean queryBroadcastReceiver(Context ctx, String actionName) {
		PackageManager pm = ctx.getPackageManager();
		try {
			Intent intent = new Intent(actionName);
			List<ResolveInfo> apps = pm.queryBroadcastReceivers(intent, 0);
			if (apps.isEmpty())
				return false;
			else
				return true;
		} catch (Exception e) {
			Log.d(TAG, "queryBroadcastReceivers: " + e.toString());
			return false;
		}
	}

	/**
	 * 获取数字型API_LEVEL 如：4、6、7
	 * @return int
	 */
	@SuppressWarnings("deprecation")
	public static int getApiLevel() {
		int apiLevel = 7;
		try {
			apiLevel = Integer.parseInt(Build.VERSION.SDK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return apiLevel;
		// android.os.Build.VERSION.SDK_INT Since: API Level 4
		// return android.os.Build.VERSION.SDK_INT;
	}

	/**
	 * 获取CPU_ABI
	 * @return String
	 */
	public static String getCPUABI() {
		String abi = Build.CPU_ABI;
		abi = (abi == null || abi.trim().length() == 0) ? "" : abi;
		// 检视是否有第二类型，1.6没有这个字段
		try {
			String cpuAbi2 = Build.class.getField("CPU_ABI2").get(null).toString();
			cpuAbi2 = (cpuAbi2 == null || cpuAbi2.trim().length() == 0) ? null : cpuAbi2;
			if (cpuAbi2 != null) {
				abi = abi + "," + cpuAbi2;
			}
		} catch (Exception e) {
		}
		return abi;
	}

	private static String intToIp(int i) {
		return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + (i >> 24 & 0xFF);
	}

	/**
	 * 是否中文环境
	 * @param ctx
	 * @return boolean
	 */
	public static boolean isZh(Context ctx) {
		Locale lo = ctx.getResources().getConfiguration().locale;
		if (lo.getLanguage().equals("zh"))
			return true;
		return false;
	}

	/**
	 * 是否拥有root权限
	 */
	public static boolean hasRootPermission() {
		boolean rooted = true;
		try {
			File su = new File("/system/bin/su");
			if (su.exists() == false) {
				su = new File("/system/xbin/su");
				if (su.exists() == false) {
					rooted = false;
				}
			}
		} catch (Exception e) {
			rooted = false;
		}
		return rooted;
	}

	/**
	 * 获取当前语言
	 * @return String
	 */
	public static String getLanguage() {
		return Locale.getDefault().getLanguage();
	}

	/**
	 * 获取手机上网类型(cmwap/cmnet/wifi/uniwap/uninet)
	 * @param ctx
	 * @return String
	 */
	public static String getNetworkTypeName(Context ctx) {
		ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (null == info || null == info.getTypeName()) {
			return "unknown";
		}
		return info.getTypeName();
	}

	/**
	 * 获取网络类型值，获取服务器端数据URL中需要用到
	 * @param ctx
	 * @return String
	 */
	public static String getNT(Context ctx) {
		/**
		 * 0 未知
		 * 
		 * 10 WIFI网络
		 * 
		 * 20 USB网络
		 * 
		 * 31 联通
		 * 
		 * 32 电信
		 * 
		 * 53 移动
		 * 
		 * IMSI是国际移动用户识别码的简称(International Mobile Subscriber Identity)
		 * 
		 * IMSI共有15位，其结构如下： MCC+MNC+MIN MNC:Mobile NetworkCode，移动网络码，共2位
		 * 在中国，移动的代码为电00和02，联通的代码为01，电信的代码为03
		 */
		String imsi = getIMSI(ctx);
		String nt = "0";
		if (TelephoneUtil.isWifiEnable(ctx)) {
			nt = "10";
		} else if (imsi == null) {
			nt = "0";
		} else if (imsi.length() > 5) {
			String mnc = imsi.substring(3, 5);
			if (mnc.equals("00") || mnc.equals("02")) {
				nt = "53";
			} else if (mnc.equals("01")) {
				nt = "31";
			} else if (mnc.equals("03")) {
				nt = "32";
			}
		}
		return nt;
	}

	/**
	 * 获取制造商
	 * @return String
	 */
	public static String getManufacturer() {
		return Build.MANUFACTURER;
	}
	
	/**
	 * 是否是Note2
	 * @return boolean
	 */
	public static boolean isNote2() {
		try {
			return (getMachineName().contains("GT-N71")|| getMachineName().contains("SCH-N719"));
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 是否是小米手机
	 * @return boolean
	 */
	public static boolean isMIMoble() {
		try {
			return TelephoneUtil.getManufacturer().equalsIgnoreCase("xiaomi");
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 获取当前用户手机的具体状态
	 * 包括：no,wifi,2g,3g,4g,unknow
	 * <p>Title: getTelephoneConcreteNetworkState</p>
	 * <p>Description: </p>
	 * @return
	 * @author maolinnan_350804
	 */
	public static final int NETWORK_CLASS_UNKNOWN = 0;
	public static final int NETWORK_CLASS_YD_2G = 1;
	public static final int NETWORK_CLASS_LT_2G = 2;
	public static final int NETWORK_CLASS_DX_2G = 3;
	public static final int NETWORK_CLASS_2G = 4;
	public static final int NETWORK_CLASS_YD_3G = 5;
	public static final int NETWORK_CLASS_LT_3G = 6;
	public static final int NETWORK_CLASS_DX_3G = 7;
	public static final int NETWORK_CLASS_3G = 8;
	public static final int NETWORK_CLASS_4G = 9;
	public static final int NETWORK_CLASS_WIFI = 10;
	public static final int NETWORK_CLASS_NO = 11;

	public static String getTelephoneConcreteNetworkStateString(Context context){
		switch (getTelephoneConcreteNetworkState(context)) {
			case NETWORK_CLASS_YD_2G:return "yd2g";
			case NETWORK_CLASS_LT_2G:return "lt2g";
			case NETWORK_CLASS_DX_2G:return "dx2g";
			case NETWORK_CLASS_2G:return "2g";
			case NETWORK_CLASS_YD_3G:return "yd3g";
			case NETWORK_CLASS_LT_3G:return "lt3g";
			case NETWORK_CLASS_DX_3G:return "dx3g";
			case NETWORK_CLASS_3G:return "3g";
			case NETWORK_CLASS_4G:return "4g";
			case NETWORK_CLASS_WIFI:return "wifi";
			case NETWORK_CLASS_NO:return "no";
			default:
				return "unknow";
		}
	}

	public static int getTelephoneConcreteNetworkState(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager == null){
			return NETWORK_CLASS_UNKNOWN;
		}
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo == null || !networkInfo.isConnected()) {// 没网络
			return NETWORK_CLASS_NO;
		}
		String type = networkInfo.getTypeName();
		if ("WIFI".equals(type) || "wifi".equals(type)) {
			return NETWORK_CLASS_WIFI;
		} else if ("MOBILE".equals(type) || "mobile".equals(type)) {
			return getNetworkClass(networkInfo.getType());
		} else {
			return NETWORK_CLASS_UNKNOWN;
		}
	}

	private static int getNetworkClass(int networkType) {
		switch (networkType) {
			case TelephonyManager.NETWORK_TYPE_GPRS:// 联通2g
				return NETWORK_CLASS_LT_2G;
			case TelephonyManager.NETWORK_TYPE_EDGE:// 移动2g
				return NETWORK_CLASS_YD_2G;
			case TelephonyManager.NETWORK_TYPE_CDMA:// 电信2g
				return NETWORK_CLASS_DX_2G;
			case TelephonyManager.NETWORK_TYPE_1xRTT:
			case TelephonyManager.NETWORK_TYPE_IDEN:
				return NETWORK_CLASS_2G;
			case TelephonyManager.NETWORK_TYPE_UMTS:// 联通3g
			case TelephonyManager.NETWORK_TYPE_HSDPA:// 联通3g
				return NETWORK_CLASS_LT_3G;
			case TelephonyManager.NETWORK_TYPE_EVDO_0:// 电信3g
			case TelephonyManager.NETWORK_TYPE_EVDO_A:// 电信3g
			case TelephonyManager.NETWORK_TYPE_EVDO_B:// 电信3g
				return NETWORK_CLASS_DX_3G;
			case TelephonyManager.NETWORK_TYPE_HSUPA:
			case TelephonyManager.NETWORK_TYPE_HSPA:

			case 14:// TelephonyManager.NETWORK_TYPE_EHRPD
			case 15:// TelephonyManager.NETWORK_TYPE_HSPAP
				return NETWORK_CLASS_3G;
			case 13:// TelephonyManager.NETWORK_TYPE_LTE	LTE标准的4g
				return NETWORK_CLASS_4G;
			default:
				return NETWORK_CLASS_UNKNOWN;
		}
	}
	
}
