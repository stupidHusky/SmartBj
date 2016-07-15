package com.avatar.smartbj.view;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.avatar.smartbj.R;
import com.avatar.smartbj.domain.NewsCenterData.NewsData;

/**
 * @author Lenovo
 * @创建时间2016年7月10日下午1:31:01
 * @描述TODO 左侧fragment界面 功能分析： 获取NewCenterTagPage发送过来的json数据显示在左侧的listview中
 */
public class BehindFragment extends BaseFragment {

	public static final int FOCUS_BLOCK_DESCENDANTS = 0x60000;
	private List<NewsData> data = new ArrayList<NewsData>();
	private ListView lv_data;
	private MyAdapter adapter;

	private int selectIndex;

	@Override
	public View initView() {
		lv_data = new ListView(mainActivity);
		lv_data.setPadding(0, 100, 0, 0);
		lv_data.setDividerHeight(0);
		lv_data.setCacheColorHint(Color.TRANSPARENT);
		lv_data.setSelector(new ColorDrawable(Color.TRANSPARENT));
		return lv_data;
	}


	/**
	 * @param data
	 *            json数据 提供外部设置数据方法
	 */
	public void setLeftMenuData(List<NewsData> data) {
		this.data = data;
		adapter.notifyDataSetChanged();
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// 内容填充
			TextView view;
			if (convertView == null) {
				view = (TextView) View.inflate(mainActivity,
						R.layout.behind_frage_list_data, null);
			} else {
				view = (TextView) convertView;
			}

			view.setText(data.get(position).title);
			view.setEnabled(selectIndex == position);
			return view;
		}

	}

	@Override
	public void initData() {
		adapter = new MyAdapter();
		lv_data.setAdapter(adapter);
		super.initData();
	}
	
	@Override
	public void initEvent() {
		
		
		lv_data.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d("onItemClick----",position + "" );
				selectIndex = position;
				adapter.notifyDataSetChanged();
				//选择切换到左侧菜单所对应的页面
//				mainActivity.getMainFagment().selectLeftMenuClickToPage(selectIndex);
				if(pagerListener != null){
					pagerListener.switchPager(selectIndex);
				}
				mainActivity.getSlidingMenu().toggle();
			}
		});
		super.initEvent();
	}
	
	public interface OnSwitchPagerListener{
		void switchPager(int selectIndex);
	}
	
	private OnSwitchPagerListener pagerListener;
	
	public void setOnSwitchPagerListener(OnSwitchPagerListener listener){
		this.pagerListener =listener;
	}
}
