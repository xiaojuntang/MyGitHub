package com.funi.news.dao;

import java.util.ArrayList;
import java.util.List;

import com.funi.news.database.DBHelper;
import com.funi.news.modle.News;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class NewsDao {

	private DBHelper mDbHepler=null;

	private final String Tag="NewsDao";

	public NewsDao(Context ctx) {
		mDbHepler=new DBHelper(ctx);
	}

	public List<News> getAll() {
		List<News> newslist = new ArrayList<News>();
		SQLiteDatabase db = mDbHepler.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from newstype", null);
		while (cursor.moveToNext()) {
			News news = new News();
			news.setNewsId(cursor.getInt(0));
			news.setNewsTitle(cursor.getString(1));
			news.setNewsAuth(cursor.getString(2));
			//news.setNewsDate(cursor.getString(3));
			news.setImagePath(cursor.getString(4));
			newslist.add(news);
		}
		return newslist;
	}
}
