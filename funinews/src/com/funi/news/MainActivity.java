package com.funi.news;


import com.funi.news.common.MoveBackGround;
import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.widget.*;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends TabActivity {

	TabHost tabHost;
	TabHost.TabSpec tabSpec;
	RadioGroup radioGroup;
	RelativeLayout bottomLayout;
	ImageView img;
	int startLeft;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		InitControl();
	}

	private void InitControl() {
		bottomLayout = (RelativeLayout) findViewById(R.id.layout_bottom);
		radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
		radioGroup.setOnCheckedChangeListener(checkedChangeListener);

		img = new ImageView(this);
		img.setImageResource(R.drawable.tab_front_bg);
		bottomLayout.addView(img);
		
        tabHost = getTabHost();
        tabHost.addTab(tabHost.newTabSpec("news").setIndicator("News").setContent(new Intent(this, TabNewsActivity.class)));
        tabHost.addTab(tabHost.newTabSpec("topic").setIndicator("Topic").setContent(new Intent(this, TabTopicActivity.class)));
//        tabHost.addTab(tabHost.newTabSpec("picture").setIndicator("Picture").setContent(new Intent(this, TabPicActivity.class)));
//        tabHost.addTab(tabHost.newTabSpec("follow").setIndicator("Follow").setContent(new Intent(this, TabFollowActivity.class)));
//        tabHost.addTab(tabHost.newTabSpec("vote").setIndicator("Vote").setContent(new Intent(this, TabVoteActivity.class)));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private OnCheckedChangeListener checkedChangeListener = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.radio_news:
				tabHost.setCurrentTabByTag("news");
				MoveBackGround.moveFrontBg(img, startLeft, 0, 0, 0);
				startLeft = 0;
				break;
			case R.id.radio_topic:
				tabHost.setCurrentTabByTag("topic");
				MoveBackGround.moveFrontBg(img, startLeft, img.getWidth(), 0, 0);
				startLeft = img.getWidth();
				break;
			case R.id.radio_pic:
				tabHost.setCurrentTabByTag("picture");
				MoveBackGround.moveFrontBg(img, startLeft, img.getWidth() * 2, 0, 0);
				startLeft = img.getWidth() * 2;
				break;
			case R.id.radio_follow:
				tabHost.setCurrentTabByTag("follow");
				MoveBackGround.moveFrontBg(img, startLeft, img.getWidth() * 3, 0, 0);
				startLeft = img.getWidth() * 3;
				break;
			case R.id.radio_vote:
				tabHost.setCurrentTabByTag("vote");
				MoveBackGround.moveFrontBg(img, startLeft, img.getWidth() * 4, 0, 0);
				startLeft = img.getWidth() * 4;
				break;
			default:
				break;
			}
		}

	};
}
