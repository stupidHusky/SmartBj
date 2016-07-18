package com.avatar.smartbj.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.avatar.smartbj.R;
import com.avatar.smartbj.utils.MyConstanse;
import com.avatar.smartbj.utils.SpTool;

/**
 * @author Lenovo
 * @创建时间2016年7月9日上午10:25:00
 * @描述 动画界面
 */
public class SplashActivity extends Activity {
	private ImageView splashView;
	private AnimationSet animationSet;
	private static final String TAG = "SplashActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash_activity);
		initView();
		StartAnimaton();
		initEvent();
	}

	private void initEvent() {
		animationSet.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// 判断进入主界面还是向导界面
				if (SpTool.getBoolean(getApplicationContext(),
						MyConstanse.ISSETUP, false)) {
					Intent mainIntent = new Intent(SplashActivity.this,
							MainActivity.class);
					startActivity(mainIntent);
				} else {
					Intent intent = new Intent(SplashActivity.this,
							GuideActivity.class);
					startActivity(intent);
				}
				finish();
			}
		});
	}

	private void StartAnimaton() {
		animationSet = new AnimationSet(false);

		RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		rotateAnimation.setDuration(2000);
		rotateAnimation.setFillAfter(true);
		animationSet.addAnimation(rotateAnimation);

		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
		alphaAnimation.setDuration(2000);
		alphaAnimation.setFillAfter(true);
		animationSet.addAnimation(rotateAnimation);

		ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scaleAnimation.setDuration(2000);
		scaleAnimation.setFillAfter(true);
		animationSet.addAnimation(scaleAnimation);

		splashView.startAnimation(animationSet);
	}

	private void initView() {
		splashView = (ImageView) findViewById(R.id.iv_splash_mainview);
	}
}
