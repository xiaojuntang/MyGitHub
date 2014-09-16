package com.funi.news.adapter;

import java.util.List;
import java.util.Map;
import com.funi.news.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class TypeSimpleAdapter extends SimpleAdapter {

	private Context context;

	public TypeSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = super.getView(position, convertView, parent);
		// 更新第一个TextView的背景
		TextView categoryTitle = (TextView) v;
		categoryTitle.setTextSize(18);

		LayoutParams p = categoryTitle.getLayoutParams();
		p.height = 45;
		categoryTitle.setLayoutParams(p);

		if (position == 0) {
			categoryTitle.setBackgroundResource(R.drawable.title_textview_style);
			categoryTitle.setTextColor(this.context.getResources().getColor(R.color.menuselect));
		}
		return v;
	}
}
