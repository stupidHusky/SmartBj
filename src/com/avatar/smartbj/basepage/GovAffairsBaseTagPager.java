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
public class GovAffairsBaseTagPager extends BaseTagPage {

	public GovAffairsBaseTagPager(MainActivity context) {
		super(context);
	}

	@Override
	public void initData() {
		tv_title.setText("政府");
		TextView tv = new TextView(mainActivity);
		tv.setTextSize(25);
		tv.setText("政府内容");
		tv.setGravity(Gravity.CENTER);
		content_fr.addView(tv);
		super.initData();
	}

}
