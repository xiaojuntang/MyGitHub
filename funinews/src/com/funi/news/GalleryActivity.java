package com.funi.news;

import java.util.Timer;
import java.util.TimerTask;

import com.funi.news.common.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;

public class GalleryActivity extends Activity {

	private int subIndex = 0;
	private int imageCount = 3;
	private ImageView[] dotImages;
	String[] array = new String[3];
	private Gallery mGallery;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);

		FindViews();
		
		dotImages[0].setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.dot_selected));

		array[0] = "http://img1.gtimg.com/cd/pics/hv1/214/97/1539/100098424.jpg";
		array[1] = "http://img1.gtimg.com/cd/pics/hv1/66/158/1540/100178856.jpg";
		array[2] = "http://img1.gtimg.com/house_chengdu/pics/hv1/100/125/1541/100235500.jpg";

		ImageAdapter adapter = new ImageAdapter(this, array);
		mGallery.setAdapter(adapter);

		Timer timer = new Timer();
		timer.schedule(task, 5000, 5000);
		mGallery.setOnItemSelectedListener(onItemSelectedListener);
	}

	private void FindViews() {
		mGallery = (Gallery) findViewById(R.id.gallery);
		dotImages = new ImageView[] { (ImageView) findViewById(R.id.dot_1), (ImageView) findViewById(R.id.dot_2), (ImageView) findViewById(R.id.dot_3), };
	}

	private TimerTask task = new TimerTask() {
		@Override
		public void run() {
			Message message = new Message();
			message.what = 2;
			subIndex = mGallery.getSelectedItemPosition();
			subIndex++;
			handler.sendMessage(message);
		}
	};

	/**
	 * 开一个线程执行耗时操作
	 */
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 2:
				mGallery.setSelection(subIndex);
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 设置小圆点显示，position会一直增加，如果要循环显示图片，需要对position取余，否则数组越界
	 */
	private OnItemSelectedListener onItemSelectedListener = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			int pos = position % imageCount;
			dotImages[pos].setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.dot_unselected));
			if (pos > 0) {
				dotImages[pos - 1].setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.dot_selected));
			}
			if (pos < (imageCount - 1)) {
				dotImages[pos + 1].setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.dot_selected));
			}
			if (pos == 0) {
				dotImages[imageCount - 1].setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.dot_selected));
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
		}
	};

	class ImageAdapter extends BaseAdapter {

		private Context context;
		private String[] array;
		private ImageLoader iloader = null;

		private static final int IMAGE_PX_HEIGHT = 198;

		public ImageAdapter(Context context, String[] array) {
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
}
