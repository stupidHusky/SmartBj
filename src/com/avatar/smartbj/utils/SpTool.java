package com.avatar.smartbj.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Lenovo
 * @创建时间2016年7月9日上午10:40:50
 * @描述TODO sharedpreference工具类
 */
public class SpTool {
	public static void setBoolean(Context context, String key, boolean value) {
		SharedPreferences sp = context.getSharedPreferences(
				MyConstanse.CONFIGLE, Context.MODE_PRIVATE);
		sp.edit().putBoolean(key, value).commit();
	}

	public static boolean getBoolean(Context context, String key,
			boolean defValue) {
		SharedPreferences sp = context.getSharedPreferences(
				MyConstanse.CONFIGLE, Context.MODE_PRIVATE);
		return sp.getBoolean(key, defValue);

	}
}
