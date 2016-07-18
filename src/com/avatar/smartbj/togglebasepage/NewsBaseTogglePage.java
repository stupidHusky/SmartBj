package com.avatar.smartbj.togglebasepage;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.avatar.smartbj.R;
import com.avatar.smartbj.activity.MainActivity;
import com.avatar.smartbj.domain.NewsCenterData.NewsData.ViewTagData;
import com.avatar.smartbj.tpipagers.NewsTpiBasePage;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.viewpagerindicator.TabPageIndicator;

//import com.viewpagerindicator.TabPageIndicator;
public class NewsBaseTogglePage extends ToggleBasePage {

	@ViewInject(R.id.news_center_pager)
	private ViewPager nCenViewPager;
	@ViewInject(R.id.news_center_indicator)
	private TabPageIndicator nCenTpi;
	private List<ViewTagData> viewData;

	public NewsBaseTogglePage(MainActivity mainActivity,
			List<ViewTagData> children) {
		super(mainActivity);
		this.viewData = children;
	}

	@Override
	public View initView() {
		View newsCenterView = View.inflate(mainActivity,
				R.layout.news_center__tabs, null);
		ViewUtils.inject(this, newsCenterView);

		// TextView tv = new TextView(mainActivity);
		// tv.setTextSize(25);
		// tv.setText("新闻的新闻");
		// tv.setGravity(Gravity.CENTER);
		return newsCenterView;
	}

	@Override
	public void initData() {
		super.initData();
		MyAdapter adapter = new MyAdapter();
		nCenViewPager.setAdapter(adapter);
		nCenTpi.setViewPager(nCenViewPager);

	}

	@OnClick(R.id.news_cen_indi_next)
	private void Next(View view){
		//点击滚动tagPageIndicator
		nCenViewPager.setCurrentItem(nCenViewPager.getCurrentItem() + 1);
	}
	@Override
	public void initEvent() {
		
		nCenTpi.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// 新闻页面中第一个可以滑动左侧
				if (position == 0) {
					Log.d("setOnPageChangeListener--->", position + "");
					mainActivity.getSlidingMenu().setTouchModeAbove(
							SlidingMenu.TOUCHMODE_FULLSCREEN);
				} else {
					mainActivity.getSlidingMenu().setTouchModeAbove(
							SlidingMenu.TOUCHMODE_NONE);
				}

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		super.initEvent();
	}

	class MyAdapter extends PagerAdapter {


		@Override
		public int getCount() {
			return viewData.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			return view == obj;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			NewsTpiBasePage tpiBasePage = new NewsTpiBasePage(mainActivity, viewData.get(position));
			View rootView = tpiBasePage.getRootView();
			container.addView(rootView);
			return rootView;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return viewData.get(position).title;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {

			container.removeView((View) object);
		}
	}
}
