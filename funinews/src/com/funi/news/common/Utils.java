package com.funi.news.common;

import java.io.InputStream;
import java.io.OutputStream;
import android.content.Context;


public class Utils {
	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
				is.close();
				os.close();
			}
		} catch (Exception ex) {
		}
	}

	/**
	 * 从字符串转换成整形
	 * 
	 * @param str
	 *            待转换字符串
	 * @return
	 */
	public static int String2Int(String str) {
		try {
			int value = Integer.valueOf(str);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue)
	{
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue)
	{
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}



}
