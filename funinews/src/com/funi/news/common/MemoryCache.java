package com.funi.news.common;

import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;

/**
 * 程序缓存的处理，主要是内存缓存+文件缓存
 * 
 * @author ThinkPad
 * 
 */
public class MemoryCache {
	private Map<String, SoftReference<Bitmap>> cache = Collections.synchronizedMap(new HashMap<String, SoftReference<Bitmap>>());// 软引用

	public Bitmap get(String id) {
		if (!cache.containsKey(id))
			return null;
		SoftReference<Bitmap> ref = cache.get(id);
		return ref.get();
	}

	public void put(String id, Bitmap bitmap) {
		cache.put(id, new SoftReference<Bitmap>(bitmap));
	}

	public void clear() {
		cache.clear();
	}
}
