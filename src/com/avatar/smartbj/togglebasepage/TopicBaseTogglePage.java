package com.avatar.smartbj.togglebasepage;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.avatar.smartbj.activity.MainActivity;

public class TopicBaseTogglePage extends ToggleBasePage {

	public TopicBaseTogglePage(MainActivity mainActivity) {
		super(mainActivity);
	}

	@Override
	public View initView() {
		TextView tv = new TextView(mainActivity);
		tv.setTextSize(25);
		tv.setText("专题内容");
		tv.setGravity(Gravity.CENTER);
		return tv;
	}
}
