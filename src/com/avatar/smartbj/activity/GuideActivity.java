package com.avatar.smartbj.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

import com.avatar.smartbj.R;
import com.avatar.smartbj.utils.DensityUtil;
import com.avatar.smartbj.utils.MyConstanse;
import com.avatar.smartbj.utils.SpTool;

/**
 * @author Lenovo
 * @创建时间2016年7月9日上午10:38:32
 * @描述 引导界面
 */
public class GuideActivity extends Activity {
	private static final String TAG = "GuideActivity";
	private ViewPager viewPager;
	private LinearLayout points;
	private Button startExpre;
	private List<ImageView> guides;
	private MyAdapter adapter;
	private View red_point;
	private int distance;
	private float marginLeft;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.guide_activity);
		initView();
		initData();
		initEvent();
	}

	private void initEvent() {

		startExpre.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent mainIntent = new Intent(GuideActivity.this,
						MainActivity.class);
				startActivity(mainIntent);
				// Save data
				SpTool.setBoolean(getApplicationContext(), MyConstanse.ISSETUP,
						true);

				finish();
			}
		});

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				if (position == guides.size() - 1) {
					startExpre.setVisibility(View.VISIBLE);
				} else {
					startExpre.setVisibility(View.INVISIBLE);
				}
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				marginLeft = distance * (position + positionOffset);

				// 设置红点左边距

				RelativeLayout.LayoutParams layoutParams = (android.widget.RelativeLayout.LayoutParams) red_point
						.getLayoutParams();

				layoutParams.leftMargin = Math.round(marginLeft);
				red_point.setLayoutParams(layoutParams);
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

		// 设置view布局完成事件监听
		red_point.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						red_point.getViewTreeObserver()
								.removeOnGlobalLayoutListener(this);
						distance = points.getChildAt(1).getLeft()
								- points.getChildAt(0).getLeft();
						Log.i(TAG, distance + "");
					}
				});
	}

	private void initData() {
		int[] pics = new int[] { R.drawable.guide_1, R.drawable.guide_2,
				R.drawable.guide_3 };

		guides = new ArrayList<ImageView>();

		for (int i = 0; i < pics.length; i++) {
			ImageView tempImage = new ImageView(getApplicationContext());
			tempImage.setBackgroundResource(pics[i]);
			guides.add(tempImage);
			//动态添加底部灰点
			View v_point = new View(getApplicationContext());
			v_point.setBackgroundResource(R.drawable.gray_point);
			int dp_value = 10;
			LayoutParams param = new LayoutParams(DensityUtil.dip2px(
					getApplicationContext(), dp_value), DensityUtil.dip2px(
					getApplicationContext(), dp_value));
			if (i != 0)
				param.leftMargin = 10;
			v_point.setLayoutParams(param);
			points.addView(v_point);
		}

		adapter = new MyAdapter();
		viewPager.setAdapter(adapter);
	}

	private void initView() {
		viewPager = (ViewPager) findViewById(R.id.vp_guide_pages);
		points = (LinearLayout) findViewById(R.id.ll_guide_points);
		startExpre = (Button) findViewById(R.id.bt_guide_startexp);
		red_point = findViewById(R.id.v_guide_redpoint);
	}

	private class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return guides.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			return view == obj;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View v_cild = guides.get(position);
			container.addView(v_cild);
			return v_cild;
		}

	}
}
