package com.avatar.smartbj.view;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.avatar.smartbj.R;
import com.avatar.smartbj.basepage.BaseTagPage;
import com.avatar.smartbj.basepage.GovAffairsBaseTagPager;
import com.avatar.smartbj.basepage.HomeBaseTagPager;
import com.avatar.smartbj.basepage.NewsCenterBaseTagPager;
import com.avatar.smartbj.basepage.SettingBaseTagPager;
import com.avatar.smartbj.basepage.SmartServiceBaseTagPager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @author Lenovo
 * @创建时间2016年7月10日下午1:32:06
 * @描述TODO
 */
public class MainFragment extends BaseFragment {

	@ViewInject(R.id.main_content_viewpager)
	private ViewPager viewPager;
	@ViewInject(R.id.main_radiogroup)
	private RadioButton radioButton;
	private List<BaseTagPage> pagers;
	@Override
	public View initView() {
		View root = View
				.inflate(mainActivity, R.layout.main_content_view, null);
		ViewUtils.inject(this, root);
		return root;
	}
	
	@Override
	public void initData() {
		pagers = new ArrayList<BaseTagPage>();
		pagers.add(new HomeBaseTagPager(mainActivity));
		pagers.add(new GovAffairsBaseTagPager(mainActivity));
		pagers.add(new NewsCenterBaseTagPager(mainActivity));
		pagers.add(new SmartServiceBaseTagPager(mainActivity));
		pagers.add(new SettingBaseTagPager(mainActivity));
		
		MyAapter adapter = new MyAapter();
		viewPager.setAdapter(adapter);
	}
	
	class MyAapter extends PagerAdapter{

		@Override
		public int getCount() {
			return pagers.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			return view == obj;
		}
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			BaseTagPage baseTagPage = pagers.get(position);
			View view = baseTagPage.getView();
			container.addView(view);
			return view;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}
}
