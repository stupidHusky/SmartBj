package com.avatar.smartbj.basepage;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.avatar.smartbj.R;
import com.avatar.smartbj.activity.MainActivity;
import com.avatar.smartbj.activity.SplashActivity;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * @author Lenovo
 * @创建时间2016年7月10日下午9:25:17
 * @描述TODO
 */
public class BaseTagPage {
	protected View root;
	protected TextView tv_title;
	protected ImageView im_menu;
	protected FrameLayout content_fr;

	protected MainActivity mainActivity;
	public BaseTagPage(MainActivity context) {
		this.mainActivity = context;
		initView();
		initData();
		initEvent();
	}

	public void initEvent() {
		im_menu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mainActivity.getSlidingMenu().toggle();
			}
		});
	}

	public void initData() {

	}

	public void initView() {
		root = View.inflate(mainActivity, R.layout.fragment_content_base_content,
				null);
		tv_title = (TextView) root.findViewById(R.id.main_base_title);
		im_menu = (ImageView) root.findViewById(R.id.main_base_menu);
		content_fr = (FrameLayout) root
				.findViewById(R.id.main_base_framelayout);
	}

	public View getView() {
		return root;
	}
}
