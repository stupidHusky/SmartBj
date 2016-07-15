package com.avatar.smartbj.togglebasepage;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.avatar.smartbj.activity.MainActivity;

public class PhotoBaseTogglePage extends ToggleBasePage {

	public PhotoBaseTogglePage(MainActivity mainActivity) {
		super(mainActivity);
	}

	@Override
	public View initView() {
		TextView tv = new TextView(mainActivity);
		tv.setTextSize(25);
		tv.setText("组图");
		tv.setGravity(Gravity.CENTER);
		return tv;
	}
}
