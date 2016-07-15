package com.avatar.smartbj.activity;

import com.avatar.smartbj.R;
import com.avatar.smartbj.view.BehindFragment;
import com.avatar.smartbj.view.MainFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author Lenovo
 * @创建时间2016年7月9日下午4:34:35
 * @描述TODO
 */
public class MainActivity extends SlidingFragmentActivity {
	private static final String TAG = "MainActivity";
	private static final String BEHINDFRAME = "BEHINDFRAME";
	private static final String CONTENTFRAME = "CONTENTFRAME";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		initView();
		initData();

	}

	private void initData() {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction beginTransaction = fragmentManager
				.beginTransaction();
		beginTransaction.replace(R.id.frame_behind, new BehindFragment(),
				BEHINDFRAME);
		beginTransaction.replace(R.id.frame_main, new MainFragment(),
				CONTENTFRAME);
		beginTransaction.commit();

	}

	private void initView() {
		setContentView(R.layout.main_activity);
		// sliding interface
		setBehindContentView(R.layout.behind_frage);
		SlidingMenu menu = getSlidingMenu();
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		WindowManager windowManager = this.getWindowManager();
		int slidingWidth = (int) (windowManager.getDefaultDisplay().getWidth() * 0.75);
		Log.d(TAG, slidingWidth + "");
		menu.setBehindOffset(Math.round(slidingWidth));

	}
	
	public BehindFragment getBehindFagment(){
		FragmentManager fragmentManager = getSupportFragmentManager();
		Fragment fragmentByTag = fragmentManager.findFragmentByTag(BEHINDFRAME);
		return (BehindFragment) fragmentByTag;
	}
	
	public MainFragment getMainFagment(){
		FragmentManager fragmentManager = getSupportFragmentManager();
		Fragment fragmentByTag = fragmentManager.findFragmentByTag(CONTENTFRAME);
		return (MainFragment) fragmentByTag;
	}
}
