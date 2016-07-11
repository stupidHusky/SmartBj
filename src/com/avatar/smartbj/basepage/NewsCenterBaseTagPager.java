package com.avatar.smartbj.basepage;

import com.avatar.smartbj.activity.MainActivity;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

/**
 * @author Lenovo
 * @创建时间2016年7月10日下午9:53:25
 * @描述TODO
 */
public class NewsCenterBaseTagPager extends BaseTagPage {

	public NewsCenterBaseTagPager(MainActivity context) {
		super(context);
	}

	@Override
	public void initData() {
		tv_title.setText("新闻中心");
		TextView tv = new TextView(mainActivity);
		tv.setTextSize(25);
		tv.setText("新闻中心内容");
		tv.setGravity(Gravity.CENTER);
		content_fr.addView(tv);
		super.initData();
	}
}
