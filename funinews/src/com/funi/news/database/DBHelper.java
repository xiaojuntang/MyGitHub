package com.funi.news.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	/** 数据库名称 **/
	private static final String DBName = "funi.db";
	/** 数据库版本号 **/
	private static final int Version = 4;

	private static DBHelper mInstance = null;

	private final String Tag = "DBHelper";
	
	private Context ctx;
	
	public DBHelper(Context context) {
		super(context, DBName, null, Version);
		ctx = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		Log.v(Tag, NewsTypeSql);
		db.execSQL(NewsTypeSql);
		Log.v(Tag, HousesSql);
		db.execSQL(HousesSql);
		
		
	}

	/** 更新数据库sql **/
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldver, int newver) {
		Log.v(Tag, "更新了数据库");
		db.execSQL("Drop table if exists newstype");
		db.execSQL("Drop table if exists house");
		onCreate(db);
	}

	public boolean onDeleteDb() {
		Log.v(Tag, "删除了数据库funinews.db");
		return ctx.deleteDatabase("funinews.db");
	}

	private static final String Excemple = "create table news("
			+ "newsId INTEGER PRIMARY KEY AUTOINCREMENT," + "name TEXT,"
			+ "hp INTEGER DEFAULT 100," + "mp INTEGER DEFAULT 100,"
			+ "number INTEGER);";

	/** 创建新闻类别表 **/
	private static final String NewsTypeSql = "create table if not exists newstype("
			+ "typeid INTEGER PRIMARY KEY AUTOINCREMENT," + "typename TEXT);";

	private static final String NewsSql = "create table if not exists news("
			+ "newsid integer primary key autoincrement,"
			+ "newstitle text not null," + ");";

	/** 创建项目表 **/
	private static final String HousesSql = "create table if not exists house("
			+ "id integer primary key autoincrement,"
			+ "houseno text not null," + "housename text not null,"
			+ "address text not null," + "price text not null,"
			+ "imagepath text not null);";
}
