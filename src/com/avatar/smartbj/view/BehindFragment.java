package com.avatar.smartbj.view;

import com.avatar.smartbj.activity.MainActivity;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * @author Lenovo
 * @创建时间2016年7月10日下午1:31:01
 * @描述TODO
 */
public class BehindFragment extends BaseFragment {

	@Override
	public View initView() {
		TextView tv = new TextView(mainActivity);
		tv.setTextSize(25);
		tv.setText("左侧菜单");
		tv.setGravity(Gravity.CENTER);
		return tv;
	}

}
