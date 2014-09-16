package com.funi.news.adapter;

import com.funi.news.common.ImageLoader;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class NewsGalleryAdapter extends BaseAdapter {

	private Context context;
	private String[] array;
	private ImageLoader iloader = null;

	private static final int IMAGE_PX_HEIGHT = 198;

	public NewsGalleryAdapter(Context context, String[] array) {
		this.context = context;
		this.array = array;
		this.iloader = new ImageLoader(context);
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;// 实现循环显示
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = new ImageView(context);
		Log.v("当前索引", position % array.length + "        " + array[position % array.length]);
		try {
			imageView.setImageBitmap(iloader.getImage(array[position % array.length]));
		} catch (Exception e) {
			e.printStackTrace();
		}

		imageView.setScaleType(ImageView.ScaleType.CENTER);
		imageView.setLayoutParams(new Gallery.LayoutParams(Gallery.LayoutParams.FILL_PARENT, IMAGE_PX_HEIGHT));
		RelativeLayout borderImg = new RelativeLayout(context);
		borderImg.setPadding(0, 0, 0, 0);
		// borderImg.setBackgroundResource(R.drawable.bg_gallery);//设置ImageView边框
		borderImg.addView(imageView);
		return borderImg;
	}
}
