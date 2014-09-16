package com.funi.news.common;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Environment;
import android.util.Log;

public class ImageSdCard {

	private final static String Album_Path = Environment.getExternalStorageDirectory() + "/funicach/";

	public ImageSdCard() {
	}

	/** 获取图片从SDCard **/
	public static Bitmap getmapByCard(String url, boolean isThumbnail) {
		String filename = MD5.GetMD5Code(url);
		try{
		if (isThumbnail)
			return getImageThumbnail(Album_Path + filename, 100, 100);
		else
			return getImageThumbnail(Album_Path + filename);
		}catch(Exception e){
			return null;
		}
	}

	/** 存储图片到SDCard **/
	public static boolean SaveBitmap(String url, Bitmap map) throws IOException {
		String mdv = MD5.GetMD5Code(url);
		SaveFile(mdv, map);
		return true;
	}

	private static void SaveFile(String name, Bitmap bm) throws IOException {
		File dirFile = new File(Album_Path);
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		File mycapfile = new File(Album_Path + name);
		if (mycapfile.exists()) {
			return;
		}
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(mycapfile));
		bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
		bos.flush();
		bos.close();
	}

	private static Bitmap getImageThumbnail(String imagePath, int width, int height) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// 获取这个图片的宽和高，注意此处的bitmap为null
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		options.inJustDecodeBounds = false; // 设为 false
		// 计算缩放比
		int h = options.outHeight;
		int w = options.outWidth;
		int beWidth = w / width;
		int beHeight = h / height;
		int be = 1;
		if (beWidth < beHeight) {
			be = beWidth;
		} else {
			be = beHeight;
		}
		if (be <= 0) {
			be = 1;
		}
		options.inSampleSize = be;
		// 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		// 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
		// bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
		// ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}

	private static Bitmap getImageThumbnail(String imagePath) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// 获取这个图片的宽和高，注意此处的bitmap为null
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		options.inJustDecodeBounds = false; // 设为 false
		return bitmap;
	}
}
