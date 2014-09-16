package com.funi.news.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.funi.news.HouseActivity;
import com.funi.news.R;
import com.funi.news.modle.Houses;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyAdapter extends BaseAdapter {

	private Activity activity;
	private List<Houses> data;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader; // ��������ͼƬ���࣬�����н���

	// public LazyAdapter(Activity act, ArrayList<HashMap<String, String>> d) {
	// activity = act;
	// data = d;
	// inflater = (LayoutInflater) activity
	// .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	// imageLoader = new ImageLoader(activity.getApplicationContext());
	// }

	public LazyAdapter(Activity act, List<Houses> data) {
		activity = act;
		this.data = data;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

	// @Override
	// public View getView(int position, View convertView, ViewGroup parent) {
	// View vi = convertView;
	// if (convertView == null)
	// vi = inflater.inflate(R.layout.list_item_house, null);
	//
	// TextView housename = (TextView) vi.findViewById(R.id.text_housename); //
	// ��Ŀ����
	// TextView address = (TextView) vi.findViewById(R.id.text_address); // ��ַ
	// TextView price = (TextView) vi.findViewById(R.id.text_price); // �۸�
	// TextView updatetime = (TextView) vi.findViewById(R.id.text_duration); //
	// ����ʱ��
	// ImageView thumb_image = (ImageView) vi.findViewById(R.id.house_image); //
	// ����ͼ
	//
	// HashMap<String, String> song = new HashMap<String, String>();
	// song = data.get(position);
	//
	// // ����ListView�����ֵ
	// housename.setText(song.get(HouseActivity.KEY_HouseName));
	// address.setText(song.get(HouseActivity.KEY_Address));
	// price.setText(song.get(HouseActivity.KEY_Price));
	// updatetime.setText(song.get(HouseActivity.KEY_DURATION));
	// imageLoader.DisplayImage(song.get(HouseActivity.KEY_THUMB_URL),
	// thumb_image);
	// return vi;
	// }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.list_item_house, null);
		
		TextView housename = (TextView) vi.findViewById(R.id.text_housename); // ��Ŀ����
		TextView address = (TextView) vi.findViewById(R.id.text_address); // ��ַ
		TextView price = (TextView) vi.findViewById(R.id.text_price); // �۸�
		TextView updatetime = (TextView) vi.findViewById(R.id.text_duration); // ����ʱ��
		ImageView thumb_image = (ImageView) vi.findViewById(R.id.house_image); // ����ͼ

		Houses hou = data.get(position);

		// ����ListView�����ֵ
		housename.setText(hou.getHouseName());
		address.setText(hou.getAddress());
		price.setText(hou.getPrice());
		// updatetime.setText(hou.getHouseNo());
		imageLoader.DisplayImage(hou.getImagePath(), thumb_image);
		return vi;
	}
}
