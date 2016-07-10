package com.avatar.smartbj.basepage;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

/**
 *@author Lenovo
 *@创建时间2016年7月10日下午9:53:25
 *@描述TODO
 */
public class SmartServiceBaseTagPager extends BaseTagPage {

	public SmartServiceBaseTagPager(Context context) {
		super(context);
	}

	@Override
	public void initData() {
		tv_title.setText("智慧服务");
		TextView tv = new TextView(context);
		tv.setTextSize(25);
		tv.setText("智慧服务的内容");
		tv.setGravity(Gravity.CENTER);
		super.initData();
	}
}
