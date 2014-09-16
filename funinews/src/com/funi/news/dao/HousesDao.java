package com.funi.news.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.funi.news.database.DBHelper;
import com.funi.news.modle.Houses;
import com.funi.news.modle.NewsType;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class HousesDao {
	private DBHelper mDbHepler = null;

	private final String Tag = "HousesDao";

	public HousesDao(Context ctx) {
		mDbHepler = new DBHelper(ctx);
	}

	public boolean InsertAll(List<Houses> mList) {
		SQLiteDatabase db = mDbHepler.getWritableDatabase();
		Iterator<Houses> itr = mList.iterator();
		while (itr.hasNext()) {
			Houses hous = (Houses) itr.next();
			String sql = String
					.format("insert into house (houseno,housename,address,price,imagepath) values ('%s','%s','%s','%s','%s')",
							hous.getHouseNo(), 
							hous.getHouseName(),
							hous.getAddress(), 
							hous.getPrice(),
							hous.getImagePath());
			Log.v(Tag, sql);
			db.execSQL(sql);
		}
		return true;
	}

	public List<Houses> GetAll() {
		List<Houses> houlist = new ArrayList<Houses>();
		SQLiteDatabase db = mDbHepler.getReadableDatabase();
		Cursor cursor = db
				.rawQuery("select * from house order by id asc", null);
		while (cursor.moveToNext()) {
			Houses hou = new Houses();
			hou.setHouseNo(cursor.getString(1));
			hou.setHouseName(cursor.getString(2));
			hou.setAddress(cursor.getString(3));
			hou.setPrice(cursor.getString(4));
			hou.setImagePath(cursor.getString(5));
			houlist.add(hou);
		}
		return houlist;
	}
}
