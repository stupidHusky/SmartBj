package com.avatar.smartbj.basepage;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

/**
 * @author Lenovo
 * @创建时间2016年7月10日下午9:53:25
 * @描述TODO
 */
public class NewsCenterBaseTagPager extends BaseTagPage {

	public NewsCenterBaseTagPager(Context context) {
		super(context);
	}

	@Override
	public void initData() {
		tv_title.setText("新闻中心");
		TextView tv = new TextView(context);
		tv.setTextSize(25);
		tv.setText("新闻中心内容");
		tv.setGravity(Gravity.CENTER);
		super.initData();
	}
}
