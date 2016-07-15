package com.avatar.smartbj.view;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.avatar.smartbj.R;
import com.avatar.smartbj.basepage.BaseTagPage;
import com.avatar.smartbj.basepage.GovAffairsBaseTagPager;
import com.avatar.smartbj.basepage.HomeBaseTagPager;
import com.avatar.smartbj.basepage.NewsCenterBaseTagPager;
import com.avatar.smartbj.basepage.SettingBaseTagPager;
import com.avatar.smartbj.basepage.SmartServiceBaseTagPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @author Lenovo
 * @创建时间2016年7月10日下午1:32:06
 * @描述TODO
 */
public class MainFragment extends BaseFragment {

	@ViewInject(R.id.main_content_viewpager)
	private MyViewPager viewPager;
	@ViewInject(R.id.main_radiogroup)
	private RadioGroup group;
	private List<BaseTagPage> pagers;
	private View view;
	protected int selectIndex;

	@Override
	public View initView() {
		View root = View
				.inflate(mainActivity, R.layout.main_content_view, null);
		ViewUtils.inject(this, root);
		group.check(R.id.rb_main_home);
		switchPager();
		return root;
	}

	@Override
	public void initData() {
		pagers = new ArrayList<BaseTagPage>();
		pagers.add(new HomeBaseTagPager(mainActivity));
		pagers.add(new NewsCenterBaseTagPager(mainActivity));
		pagers.add(new SmartServiceBaseTagPager(mainActivity));
		pagers.add(new GovAffairsBaseTagPager(mainActivity));
		pagers.add(new SettingBaseTagPager(mainActivity));

		MyAapter adapter = new MyAapter();
		viewPager.setAdapter(adapter);
//		viewPager.setOffscreenPageLimit(3);
	}

	public void selectLeftMenuClickToPage(int subSelectIndex){
		BaseTagPage baseTagPage = pagers.get(selectIndex);
		baseTagPage.switchPager(subSelectIndex);
	}
	
	@Override
	public void initEvent() {
		// 手动点击切换viewpager
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_main_home:
					selectIndex = 0;
					break;
				case R.id.rb_main_newscenter:
					selectIndex = 1;
					break;
				case R.id.rb_main_smartservice:
					selectIndex = 2;
					break;
				case R.id.rb_main_govaffairs:
					selectIndex = 3;
					break;
				case R.id.rb_main_setting:
					selectIndex = 4;
					break;

				default:
					break;
				}

				switchPager();
			}
		});
	}

	private void switchPager() {
		viewPager.setCurrentItem(selectIndex);
		
		if(selectIndex == 0 || selectIndex == 4){
			mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}else{
			mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		}
	}

	class MyAapter extends PagerAdapter {

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
//			Log.d("MainFragment", "instantiateItem----:"+position);
			BaseTagPage baseTagPage = pagers.get(position);
			view = baseTagPage.getView();
			container.addView(view);
			baseTagPage.initData();
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
//			Log.d("MainFragment", "destroyItem----:"+position);
			container.removeView((View) object);
		}
	}

}
