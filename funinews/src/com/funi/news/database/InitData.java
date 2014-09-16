package com.funi.news.database;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.funi.news.dao.HousesDao;
import com.funi.news.modle.Houses;

public class InitData {
	private HousesDao housedao;

	public InitData(Context ctx) {
		housedao = new HousesDao(ctx);
	}

	
}
