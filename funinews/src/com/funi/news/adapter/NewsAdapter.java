package com.funi.news.adapter;

import java.util.List;

import com.funi.news.R;
import com.funi.news.common.ImageLoader;
import com.funi.news.modle.NewBase;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsAdapter extends BaseAdapter {

	private Activity activity;
	private List<NewBase> data;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader; // ��������ͼƬ���࣬�����н���

	public NewsAdapter(Activity act, List<NewBase> data) {
		activity = act;
		this.data = data;
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.list_item_newslist, null);

		TextView title = (TextView) vi.findViewById(R.id.text_title); // ����
		TextView intr = (TextView) vi.findViewById(R.id.text_intr); // ���
		TextView review = (TextView) vi.findViewById(R.id.text_review); // ����
		ImageView thumb_image = (ImageView) vi.findViewById(R.id.new_image); // ����ͼ

		NewBase hou = data.get(position);
		Log.v("yyyyyyyyy", hou.getTitle());
		title.setText(hou.getTitle());
		intr.setText(hou.getIntr());
		review.setText(hou.getReview()+"��");
		imageLoader.DisplayImage(hou.getImageUrl(), thumb_image);
		return vi;
	}
}
