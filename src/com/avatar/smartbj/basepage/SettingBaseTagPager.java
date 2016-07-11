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
public class SettingBaseTagPager extends BaseTagPage {

	public SettingBaseTagPager(MainActivity context) {
		super(context);
	}

	@Override
	public void initData() {
		im_menu.setVisibility(View.GONE);
		tv_title.setText("设置中心");
		TextView tv = new TextView(mainActivity);
		tv.setTextSize(25);
		tv.setText("设置的内容");
		tv.setGravity(Gravity.CENTER);
		content_fr.addView(tv);
		super.initData();
	}
}
