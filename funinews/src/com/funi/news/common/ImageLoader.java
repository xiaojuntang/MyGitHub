package com.funi.news.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.funi.news.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

/** ͼƬ������ **/
public class ImageLoader {
	MemoryCache memoryCache = new MemoryCache();
	FileCache fileCache;
	private Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
	ExecutorService executorService;
	final int stub_id = R.drawable.house_header;

	/** ���췽�� **/
	public ImageLoader(Context context) {
		fileCache = new FileCache(context);
		executorService = Executors.newFixedThreadPool(5);
	}

	/** ��ʾͼƬ **/
	public void DisplayImage(String url, ImageView imageView) {
		imageViews.put(imageView, url);
		Bitmap bitmap = memoryCache.get(url);// ��ȡ����ͼƬ
		if (bitmap == null) {
			// �ٴ�SD���в���
			Bitmap sdmap = ImageSdCard.getmapByCard(url, true);
			if (sdmap == null) {
				queuePhoto(url, imageView);
				imageView.setImageResource(stub_id);// ������ռλ
			} else {
				imageView.setImageBitmap(sdmap);
			}
		} else {
			imageView.setImageBitmap(bitmap);
		}
	}

	private void queuePhoto(String url, ImageView imageView) {
		PhotoToLoad p = new PhotoToLoad(url, imageView);
		executorService.submit(new PhotosLoader(p));
	}

	public static byte[] getImageToBytes(String path) throws Exception {
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5 * 1000);
		conn.setRequestMethod("GET");
		InputStream inStream = conn.getInputStream();
		if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			return readStream(inStream);
		}
		return null;
	}

	public static byte[] readStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		outStream.close();
		inStream.close();
		return outStream.toByteArray();
	}

	/** ��ȡ����ͼƬ �������Ǹ������� **/
	private Bitmap getImageBytes(String url) throws Exception {
		byte[] data = getImageToBytes(url);
		return BitmapFactory.decodeByteArray(data, 0, data.length);
	}

	public Bitmap getImage(String url) throws Exception {
		Bitmap bitmap = memoryCache.get(url);// ��ȡ����ͼƬ
		if (bitmap == null) {
			// �ٴ�SD���в���
			Bitmap sdmap = ImageSdCard.getmapByCard(url, false);
			if (sdmap == null) {
				Bitmap netmap = getImageBytes(url);
				if (netmap == null)
					return null;
				else {
					memoryCache.put(url, netmap);// �洢������
					try {
						ImageSdCard.SaveBitmap(url, netmap);//�洢��sdcard 
					} catch (IOException e) {
						e.printStackTrace();
					}
					return netmap;
				}
			} else {
				return sdmap;
			}
		} else
			return bitmap;
	}

	/**
	 * Ŀǰ�÷���������
	 * 
	 * @param url
	 * @return
	 */
	private Bitmap getBitmap(String url) {
		File f = fileCache.getFile(url);

		// ��sd��
		Bitmap b = decodeFile(f);
		if (b != null)
			return b;

		// ������
		try {
			Bitmap bitmap = null;
			URL imageUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setInstanceFollowRedirects(true);
			InputStream is = conn.getInputStream();
			OutputStream os = new FileOutputStream(f);
			Utils.CopyStream(is, os);
			os.close();
			bitmap = decodeFile(f);
			return bitmap;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/** ����ͼ�����������ڴ����� **/
	private Bitmap decodeFile(File f) {
		try {
			// ����ͼ���С
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// �ҵ���ȷ�Ŀ̶�ֵ����Ӧ����2���ݡ�
			final int REQUIRED_SIZE = 70;
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}

			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
	}

	/** ������� **/
	private class PhotoToLoad {
		public String url;
		public ImageView imageView;

		public PhotoToLoad(String u, ImageView i) {
			url = u;
			imageView = i;
		}
	}

	class PhotosLoader implements Runnable {
		PhotoToLoad photoToLoad;

		PhotosLoader(PhotoToLoad photoToLoad) {
			this.photoToLoad = photoToLoad;
		}

		@Override
		public void run() {
			if (imageViewReused(photoToLoad))
				return;
			Bitmap bmp;
			try {
				bmp = getImageBytes(photoToLoad.url);// getBitmap(photoToLoad.url);
			} catch (Exception e) {
				bmp = null;
			}
			memoryCache.put(photoToLoad.url, bmp);

			/** �洢��sdcard **/
			try {
				ImageSdCard.SaveBitmap(photoToLoad.url, bmp);
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (imageViewReused(photoToLoad))
				return;
			BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
			Activity a = (Activity) photoToLoad.imageView.getContext();
			a.runOnUiThread(bd);
		}
	}

	boolean imageViewReused(PhotoToLoad photoToLoad) {
		String tag = imageViews.get(photoToLoad.imageView);
		if (tag == null || !tag.equals(photoToLoad.url))
			return true;
		return false;
	}

	// ������ʾλͼ��UI�߳�
	class BitmapDisplayer implements Runnable {
		Bitmap bitmap;
		PhotoToLoad photoToLoad;

		public BitmapDisplayer(Bitmap b, PhotoToLoad p) {
			bitmap = b;
			photoToLoad = p;
		}

		public void run() {
			if (imageViewReused(photoToLoad))
				return;
			if (bitmap != null)
				photoToLoad.imageView.setImageBitmap(bitmap);
			else
				photoToLoad.imageView.setImageResource(stub_id);
		}
	}

	public void clearCache() {
		memoryCache.clear();
		fileCache.clear();
	}

}
