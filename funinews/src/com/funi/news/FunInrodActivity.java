package com.funi.news;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.*;

/**
 * ���ܽ���ҳ
 * 
 * @author ThinkPad
 * 
 */
public class FunInrodActivity extends Activity {

	private WebView mWebviewpag;
	private Button mBtnrefresh;
	private ProgressBar mLoadnewsProgress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_introdfun);

		InitView();
	}

	private void InitView() {
		mWebviewpag = (WebView) findViewById(R.id.webview_introdfun);
		mBtnrefresh = (Button) findViewById(R.id.titlebar_refresh);
		mLoadnewsProgress = (ProgressBar) findViewById(R.id.loadnews_progress);
	}

	private OnClickListener BtnOnClickListener=new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	
//	private class LoadAsyncTask extends AsyncTask<Object, Integer,Integer>{
//		@Override
//		protected void onPreExecute()
//		{
//			//����ˢ�°�ť
//			mBtnrefresh.setVisibility(View.GONE);
//			//��ʾ������
//			mLoadnewsProgress.setVisibility(View.VISIBLE); 
//			//����LoadMore Button ��ʾ�ı�
//			mLoadMoreBtn.setText(R.string.loadmore_txt);
//		}
//
//		@Override
//		protected Integer doInBackground(Object... params)
//		{
//			return getSpeCateNews((Integer)params[0],mNewsData,(Integer)params[1],(Boolean)params[2]);
//		}
//
//		@Override
//		protected void onPostExecute(Integer result)
//		{
//			//���ݷ���ֵ��ʾ��ص�Toast
//			switch (result)
//			{
//			case NONEWS:
//				Toast.makeText(MainActivity.this, R.string.no_news, Toast.LENGTH_LONG).show();
//			break;
//			case NOMORENEWS:
//				Toast.makeText(MainActivity.this, R.string.no_more_news, Toast.LENGTH_LONG).show();
//				break;
//			case LOADERROR:
//				Toast.makeText(MainActivity.this, R.string.load_news_failure, Toast.LENGTH_LONG).show();
//				break;
//			}
//			mNewsListAdapter.notifyDataSetChanged();
//			//��ʾˢ�°�ť
//			mTitlebarRefresh.setVisibility(View.VISIBLE);
//			//���ؽ�����
//			mLoadnewsProgress.setVisibility(View.GONE); 
//			//����LoadMore Button ��ʾ�ı�
//			mLoadMoreBtn.setText(R.string.loadmore_btn);
//		}
//	}
}
