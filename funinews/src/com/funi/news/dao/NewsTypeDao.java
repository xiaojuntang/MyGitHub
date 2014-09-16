package com.funi.news.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.funi.news.database.DBHelper;
import com.funi.news.modle.NewsType;

public class NewsTypeDao {
	private DBHelper mDbHepler = null;
	private final String Tag = "NewsDao";

	public NewsTypeDao(Context ctx) {
		mDbHepler = new DBHelper(ctx);
	}

	/** 新增类别 **/
	public boolean Insert(NewsType newstype) {
		SQLiteDatabase db = mDbHepler.getWritableDatabase();
		String sql = String.format(
				"insert into newstype (typename) values ('%s')",
				newstype.getTypeName());
		Log.v(Tag, sql);
		db.execSQL(sql);
		return true;
	}

	/** 批量新增类别 **/
	public boolean InsertAll(List<NewsType> nList) {
		SQLiteDatabase db = mDbHepler.getWritableDatabase();
		Iterator<NewsType> itr = nList.iterator();
		while (itr.hasNext()) {
			NewsType newstype = (NewsType) itr.next();
			String sql = String.format(
					"insert into newstype (typename) values ('%s')",
					newstype.getTypeName());
			Log.v(Tag, sql);
			db.execSQL(sql);
		}
		return true;
	}

	/** 查询所有新闻类别 **/
	public List<NewsType> GetAll() {
		List<NewsType> newslist = new ArrayList<NewsType>();
		SQLiteDatabase db = mDbHepler.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from newstype", null);
		while (cursor.moveToNext()) {
			NewsType news = new NewsType();
			news.setTypeId(cursor.getInt(0));
			news.setTypeName(cursor.getString(1));
			newslist.add(news);
		}
		return newslist;
	}
}
