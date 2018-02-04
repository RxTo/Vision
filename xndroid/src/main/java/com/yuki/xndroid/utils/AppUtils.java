package com.yuki.xndroid.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.TextView;


import com.yuki.xndroid.base.IApp;
import com.yuki.xndroid.di.component.AppComponent;

import java.security.MessageDigest;

/**
 * ================================================
 * 本框架常用的工具
 * ================================================
 */
public class AppUtils {
	
	/**
	 * 设置hint大小
	 */
	public static void setViewHintSize(Context context, int size, TextView v, int res) {
		SpannableString ss = new SpannableString(getResources(context).getString(
				res));
		// 新建一个属性对象,设置文字的大小
		AbsoluteSizeSpan ass = new AbsoluteSizeSpan(size, true);
		// 附加属性到文本
		ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		// 设置hint
		v.setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失
	}
	
	
	/**
	 * dip转pix
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = getResources(context).getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	
	/**
	 * 获得资源
	 */
	public static Resources getResources(Context context) {
		return context.getResources();
	}
	
	/**
	 * 得到字符数组
	 */
	public static String[] getStringArray(Context context, int id) {
		return getResources(context).getStringArray(id);
	}
	
	/**
	 * pix转dip
	 */
	public static int pix2dip(Context context, int pix) {
		final float densityDpi = getResources(context).getDisplayMetrics().density;
		return (int) (pix / densityDpi + 0.5f);
	}
	
	
	/**
	 * 从 dimens 中获得尺寸
	 */
	public static int getDimens(Context context, int id) {
		return (int) getResources(context).getDimension(id);
	}
	
	/**
	 * 从 dimens 中获得尺寸
	 */
	public static float getDimens(Context context, String dimenName) {
		return getResources(context).getDimension(getResources(context).getIdentifier(dimenName,
				"dimen", context.getPackageName()));
	}
	
	/**
	 * 从String 中获得字符
	 */
	
	public static String getString(Context context, int stringID) {
		return getResources(context).getString(stringID);
	}
	
	
	/**
	 * 通过资源id获得drawable
	 */
	public static Drawable getDrawablebyResource(Context context, int rID) {
		return getResources(context).getDrawable(rID);
	}
	
	
	/**
	 * 跳转界面 3
	 */
	public static void startActivity(Activity activity, Class homeActivityClass) {
		Intent intent = new Intent(activity.getApplicationContext(), homeActivityClass);
		activity.startActivity(intent);
	}
	
	/**
	 * 跳转界面 4
	 */
	public static void startActivity(Activity activity, Intent intent) {
		activity.startActivity(intent);
	}
	
	/**
	 * 获得屏幕的宽度
	 */
	public static int getScreenWidth(Context context) {
		return getResources(context).getDisplayMetrics().widthPixels;
	}
	
	/**
	 * 获得屏幕的高度
	 */
	public static int getScreenHeidth(Context context) {
		return getResources(context).getDisplayMetrics().heightPixels;
	}
	
	
	/**
	 * 获得颜色
	 */
	public static int getColor(Context context, int rid) {
		return getResources(context).getColor(rid);
	}
	
	/**
	 * 获得颜色
	 */
	public static int getColor(Context context, String colorName) {
		return getColor(context, getResources(context).getIdentifier(colorName, "color", context
				.getPackageName()));
	}
	
	/**
	 * 移除孩子
	 */
	public static void removeChild(View view) {
		ViewParent parent = view.getParent();
		if (parent instanceof ViewGroup) {
			ViewGroup group = (ViewGroup) parent;
			group.removeView(view);
		}
	}
	
	
	/**
	 * MD5
	 */
	public static String encodeToMD5(String string) {
		byte[] hash = new byte[0];
		try {
			hash = MessageDigest.getInstance("MD5").digest(
					string.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10) {
				hex.append("0");
			}
			hex.append(Integer.toHexString(b & 0xFF));
		}
		return hex.toString();
	}
	
	
	/**
	 * 全屏,并且沉侵式状态栏
	 */
	public static void statuInScreen(Activity activity) {
		WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
		attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
		activity.getWindow().setAttributes(attrs);
		activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
		activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
	}
	
	
	/**
	 * 获取AppComponent实例
	 */
	public static AppComponent initAppComponent(Context context) {
		return ((IApp) context.getApplicationContext()).getAppComponent();
	}
	
}
