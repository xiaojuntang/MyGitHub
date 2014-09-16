package com.funi.news;

import android.app.Application;
import android.util.Log;

public class BaseApplication extends Application {

	private static final String Tag="BaseApplication";
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.v(Tag, "÷¥––¡ÀApplication");
	}

}
