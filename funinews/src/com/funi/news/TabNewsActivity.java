package com.funi.news;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import com.funi.news.GalleryActivity.ImageAdapter;
import com.funi.news.adapter.NewsAdapter;
import com.funi.news.adapter.NewsGalleryAdapter;
import com.funi.news.adapter.TypeSimpleAdapter;
import com.funi.news.common.Communicate;
import com.funi.news.common.Utils;
import com.funi.news.manage.Acquired;
import com.funi.news.modle.Category;
import com.funi.news.modle.NewBase;
import com.funi.news.modle.Parameter;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class TabNewsActivity extends Activity {

	// ---------------------Gallery变量----------------------
	private int subIndex = 0;
	private int imageCount = 3;
	private ImageView[] dotImages;
	String[] array = new String[3];
	private Gallery mGallery;
	// ---------------------Gallery变量----------------------
	private List<NewBase> NewsData;
	private int mColumnWidthDip;
	private int mFlingVelocityDip;
	private int COLUMNWIDTHPX = 0;
	private int FLINGVELOCITYPX = 800; // 滚动距离
	private int NewId;
	private String NewType;
	private Acquired Acq;
	private ProgressBar LoadnewsProgress;
	private LoadNewsAsyncTask LoadNewsAsyncTask;
	private LayoutInflater mInflater;
	private ListView ListViewNews;
	private NewsAdapter NewsListAdapter;
	private Button BtnLoadMore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tabnews);
		Acq = new Acquired();

		GetWidth();
		FindViews();
		InitControl();
	}

	private void FindViews() {
		mGallery = (Gallery) findViewById(R.id.gallery_news);
		mGallery.setOnItemSelectedListener(onItemSelectedListener);
		dotImages = new ImageView[] { (ImageView) findViewById(R.id.dot_new_1), (ImageView) findViewById(R.id.dot_new_2), (ImageView) findViewById(R.id.dot_new_3), };
		dotImages[0].setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.dot_selected));

		array[0] = "http://img3.cache.netease.com/cnews/2014/5/8/2014050809262447b80.jpg";
		array[1] = "http://img1.cache.netease.com/cnews/2014/5/9/20140509101846e7c97.jpg";
		array[2] = "http://img4.cache.netease.com/cnews/2014/5/8/20140508103417aacc2.jpg";

		BingGallery(array);
	}

	/**
	 * 绑定Gallery数据
	 * 
	 * @param array
	 */
	private void BingGallery(String[] array) {
		NewsGalleryAdapter adapter = new NewsGalleryAdapter(this, array);
		mGallery.setAdapter(adapter);
		Timer timer = new Timer();
		timer.schedule(task, 5000, 5000);
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

	/**
	 * 调整宽度
	 */
	private void GetWidth() {
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		int width = metric.widthPixels; // 屏幕宽度（像素）
		COLUMNWIDTHPX = (int)((width/5)*1.3);
		Log.v("ColumnWidth", COLUMNWIDTHPX+"");
		FLINGVELOCITYPX = width * 2;
	}

	private void InitControl() {
		NewsData = new ArrayList<NewBase>();

		mInflater = getLayoutInflater();
		ListViewNews = (ListView) findViewById(R.id.newslist);

		View loadMoreLayout = mInflater.inflate(R.layout.user_layout_loadmore, null);
		ListViewNews.addFooterView(loadMoreLayout);

		LoadnewsProgress = (ProgressBar) findViewById(R.id.loadmore_progress);
		BtnLoadMore = (Button) findViewById(R.id.loadmore_btn);

		NewsListAdapter = new NewsAdapter(this, NewsData);
		ListViewNews.setAdapter(NewsListAdapter);

		// 获取新闻分类
		String[] categoryArray = getResources().getStringArray(R.array.categories);
		// 把新闻分类保存到List中
		final List<Map<String, Category>> categories = new ArrayList<Map<String, Category>>();
		// 分割新闻类型字符串
		for (int i = 0; i < categoryArray.length; i++) {
			String[] temp = categoryArray[i].split("[|]");
			if (temp.length == 2) {
				int cid = Utils.String2Int(temp[0]);
				String title = temp[1];
				Category type = new Category(cid, title);
				Map<String, Category> hashMap = new HashMap<String, Category>();
				hashMap.put("category_title", type);
				categories.add(hashMap);
			}
		}

		// 创建Adapter，指明映射字段
		TypeSimpleAdapter categoryAdapter = new TypeSimpleAdapter(this, categories, R.layout.user_layout_category, new String[] { "category_title" }, new int[] { R.id.category_title });

		// 把px转换成dip
		mColumnWidthDip = Utils.px2dip(this, COLUMNWIDTHPX);
		mFlingVelocityDip = Utils.px2dip(this, FLINGVELOCITYPX);

		GridView category = new GridView(this);
		category.setColumnWidth(mColumnWidthDip);// 每个单元格宽度
		category.setNumColumns(GridView.AUTO_FIT);// 单元格数目
		category.setGravity(Gravity.CENTER);// 设置对其方式
		// 设置单元格选择是背景色为透明，这样选择时就不现实黄色北京
		 category.setSelector(new ColorDrawable(Color.TRANSPARENT));
		// 根据单元格宽度和数目计算总宽度
		int width = mColumnWidthDip * categories.size();
		LayoutParams params = new LayoutParams(width, LayoutParams.FILL_PARENT);
		// 更新category宽度和高度，这样category在一行显示
		category.setLayoutParams(params);
		// 设置适配器
		category.setAdapter(categoryAdapter);
		// 把category加入到容器中
		LinearLayout categoryList = (LinearLayout) findViewById(R.id.category_layout);
		categoryList.addView(category);

		// 添加单元格点击事件
		category.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				TextView categoryTitle;
				// 恢复每个单元格背景色
				for (int i = 0; i < parent.getChildCount(); i++) {
					categoryTitle = (TextView) (parent.getChildAt(i));
					categoryTitle.setBackgroundDrawable(null);
					categoryTitle.setTextColor(getResources().getColor(R.color.menunoselect));
				}
				// 设置选择单元格的背景色
				categoryTitle = (TextView) (parent.getChildAt(position));
				categoryTitle.setBackgroundResource(R.drawable.title_textview_style);
				categoryTitle.setTextColor(getResources().getColor(R.color.menuselect));
				// 获取选中的新闻分类id
				NewId = categories.get(position).get("category_title").getCid();
				NewType = categories.get(position).get("category_title").getTitle();

				// 获取该栏目下新闻
				// getSpeCateNews(mCid,mNewsData,0,true);
				// 通知ListView进行更新
				// mNewsListAdapter.notifyDataSetChanged();

				LoadNewsAsyncTask = new LoadNewsAsyncTask();
				LoadNewsAsyncTask.execute(NewId, 0, true);
			}
		});

		// 箭头
		final HorizontalScrollView categoryScrollview = (HorizontalScrollView) findViewById(R.id.category_scrollview);
		Button categoryArrowRight = (Button) findViewById(R.id.category_arrow_right);
		categoryArrowRight.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				categoryScrollview.fling(Utils.px2dip(TabNewsActivity.this, mFlingVelocityDip));
			}
		});

	}

	/*
	 * 异步获取数据
	 */
	private class LoadNewsAsyncTask extends AsyncTask<Object, Integer, Integer> {
		@Override
		protected void onPreExecute() {
			// 隐藏刷新按钮
			// mTitlebarRefresh.setVisibility(View.GONE);
			// 显示进度条
			LoadnewsProgress.setVisibility(View.VISIBLE);
			// 设置LoadMore Button 显示文本
			BtnLoadMore.setText("正在加载,请稍后……");
		}

		@Override
		protected Integer doInBackground(Object... params) {
			return Acq.GetNewsPost((Integer) params[0], NewsData, (Integer) params[1], (Boolean) params[2]);
		}

		@Override
		protected void onPostExecute(Integer result) {
			// 根据返回值显示相关的Toast
			switch (result) {
			case Const.NONEWS:
				Toast.makeText(TabNewsActivity.this, "无新闻", Toast.LENGTH_LONG).show();
				break;
			case Const.NOMORENEWS:
				Toast.makeText(TabNewsActivity.this, "无更多新闻", Toast.LENGTH_LONG).show();
				break;
			case Const.LOADERROR:
				Toast.makeText(TabNewsActivity.this, "加载失败", Toast.LENGTH_LONG).show();
				break;
			}
			NewsListAdapter.notifyDataSetChanged();
			// 显示刷新按钮
			// mTitlebarRefresh.setVisibility(View.VISIBLE);
			// 隐藏进度条
			LoadnewsProgress.setVisibility(View.GONE);
			// 设置LoadMore Button 显示文本
			BtnLoadMore.setText("加载更多");
		}
	}
}
