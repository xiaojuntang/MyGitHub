package com.funi.news.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import android.util.Log;
import com.funi.news.Const;
import com.funi.news.common.Communicate;
import com.funi.news.modle.NewBase;
import com.funi.news.modle.Parameter;

public class Acquired {
	private Communicate synchttp = null;

	public Acquired() {
		synchttp = new Communicate();
	}

	public int GetNewsPost(int newid, List<NewBase> newslist, int startnid, Boolean isfirst) {
		if (isfirst)
			newslist.clear();
		String url = "http://192.168.0.143:8085/MobileService.asmx/GetNewListByCategory";
		List<Parameter> params = new ArrayList<Parameter>();
		params.add(new Parameter("category", newid + ""));
		try {
			String retStr = synchttp.HttpPost(url, params);
			Log.v("Result", retStr);
			JSONObject jsonObject = new JSONObject(retStr);

			JSONArray array = jsonObject.getJSONArray("d");
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = (JSONObject) array.opt(i);
				NewBase nb = new NewBase();
				nb.setId(obj.getInt("Id"));
				nb.setTitle(obj.getString("Title"));
				nb.setImageUrl(obj.getString("ImageUrl"));
				nb.setDegree(obj.getInt("Degree"));
				nb.setReview(obj.getInt("Review"));
				nb.setIntr(obj.getString("Intr"));		
				newslist.add(nb);
			}
			return Const.SUCCESS;
			// int retCode = jsonObject.getInt("ret");
			// if (retCode == 0) {
			// JSONObject dataObject = jsonObject.getJSONObject("data");
			// int totalnum = dataObject.getInt("totalnum");
			// if (totalnum > 0) {
			// return Const.SUCCESS;
			// } else {
			// if (isfirst)
			// return Const.NONEWS;
			// else
			// return Const.NOMORENEWS;
			// }
			// }
		} catch (Exception e) {
			return Const.LOADERROR;
		}
	}
}
