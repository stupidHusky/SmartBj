package com.avatar.smartbj.togglebasepage;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avatar.smartbj.R;
import com.avatar.smartbj.activity.MainActivity;
import com.avatar.smartbj.domain.NewsCenterData;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
//import com.viewpagerindicator.TabPageIndicator;
public class NewsBaseTogglePage extends ToggleBasePage {

	@ViewInject(R.id.news_center_pager)
	private ViewPager nCenViewPager;
	@ViewInject(R.id.news_center_indicator)
//	private TabPageIndicator nCenTpi;
	private List<NewsCenterData.NewsData>  data = new ArrayList<NewsCenterData.NewsData>();
	public NewsBaseTogglePage(MainActivity mainActivity) {
		super(mainActivity);
	}

	@Override
	public View initView() {
//		View newsCenterView = View.inflate(mainActivity, R.layout.news_center__tabs, null);
//		ViewUtils.inject(this, newsCenterView);
//		MyAdapter adapter = new MyAdapter();
////		nCenViewPager.setAdapter(adapter);
		TextView tv = new TextView(mainActivity);
		tv.setTextSize(25);
		tv.setText("新闻的新闻");
		tv.setGravity(Gravity.CENTER);
		return tv;
	}
	
	class MyAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			return view == obj;
		}
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			return super.instantiateItem(container, position);
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			View view = null;
			container.removeView(view);
		}
	}
}
