package com.funi.news;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.funi.news.usercontrol.UserListView;
import android.app.Activity;
import android.os.Bundle;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class SettingActivity extends Activity {
	private UserListView cornerListView = null;

	private List<Map<String, String>> listData = null;
	private SimpleAdapter adapter = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_test);


		cornerListView = (UserListView) findViewById(R.id.setting_list);
		setListData();

		adapter = new SimpleAdapter(getApplicationContext(), listData, R.layout.list_item_setting, new String[] { "text" }, new int[] { R.id.setting_list_item_text });
		cornerListView.setAdapter(adapter);
	}

	/**
	 * 设置列表数据
	 */
	private void setListData() {
		listData = new ArrayList<Map<String, String>>();

		Map<String, String> map = new HashMap<String, String>();
		map.put("text", "图库更新");
		listData.add(map);

		map = new HashMap<String, String>();
		map.put("text", "收藏图片");
		listData.add(map);

		map = new HashMap<String, String>();
		map.put("text", "下载目录");
		listData.add(map);
	}
}
