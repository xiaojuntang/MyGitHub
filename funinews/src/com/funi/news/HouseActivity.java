package com.funi.news;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.funi.news.common.LazyAdapter;
import com.funi.news.dao.HousesDao;
import com.funi.news.modle.Houses;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class HouseActivity extends Activity {
	ListView list;
	LazyAdapter adapter;
	private HousesDao housedao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_house);

		housedao = new HousesDao(this);
		
		
		Init();
		

		List<Houses> cursor = housedao.GetAll();
		Iterator<Houses> iter = cursor.iterator();
		while (iter.hasNext()) {
			Houses hous = (Houses) iter.next();
			System.out.println(String.format(
					"项目号：%s   项目名称：%s   项目地址：%s   项目价格：%s   项目图片：%s",
					hous.getHouseNo(), hous.getHouseName(), hous.getAddress(),
					hous.getPrice(), hous.getImagePath()));
		}

		list = (ListView) findViewById(R.id.listview_house);
		adapter = new LazyAdapter(this, cursor);
		list.setAdapter(adapter);
	}
	
	
	public void Init() {
		List<Houses> array = new ArrayList<Houses>();
		for (int i = 0; i < 10; i++) {
			Houses h = new Houses();
			h.setHouseNo("00" + i);
			h.setHouseName("太阳公元（成都）" + i);
			h.setAddress("成华区电子科大-建设路 成都市成华区建设");
			h.setPrice("16,729元/平方米");
			switch (i) {
			case 0:
				h.setImagePath("http://image1.funi365.com/images/upload/1358306888346.jpg.500x375.jpg");
				break;
			case 1:
				h.setImagePath("http://img1.gtimg.com/cd/pics/hv1/214/97/1539/100098424.jpg");
				break;
			case 2:
				h.setImagePath("http://img1.gtimg.com/cd/pics/hv1/66/158/1540/100178856.jpg");
				break;
			case 3:
				h.setImagePath("http://img1.gtimg.com/cd/pics/hv1/80/25/1540/100144955.jpg");
				break;
			case 4:
				h.setImagePath("http://img1.gtimg.com/cd/pics/hv1/81/25/1540/100144956.jpg");
				break;
			case 5:
				h.setImagePath("http://img1.gtimg.com/cd/pics/hv1/47/210/1533/99736922.jpg");
				break;
			case 6:
				h.setImagePath("http://img1.gtimg.com/cd/pics/hv1/70/208/1532/99671410.jpg");
				break;
			default:
				h.setImagePath("http://img1.gtimg.com/cd/pics/hv1/61/230/1533/99742036.jpg");
				break;
			}
			array.add(h);
		}
		housedao.InsertAll(array);
	}
}
