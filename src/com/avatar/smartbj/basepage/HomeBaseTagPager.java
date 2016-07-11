package com.avatar.smartbj.basepage;

import com.avatar.smartbj.activity.MainActivity;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 *@author Lenovo
 *@创建时间2016年7月10日下午9:53:25
 *@描述TODO
 */
public class HomeBaseTagPager extends BaseTagPage {

	public HomeBaseTagPager(MainActivity context) {
		super(context);
	}

	@Override
	public void initData() {
		im_menu.setVisibility(View.GONE);
		tv_title.setText("主页");
		TextView tv = new TextView(mainActivity);
		tv.setTextSize(25);
		tv.setText("主页内容");
		tv.setGravity(Gravity.CENTER);
		content_fr.addView(tv);
		super.initData();
	}
	
	
}
