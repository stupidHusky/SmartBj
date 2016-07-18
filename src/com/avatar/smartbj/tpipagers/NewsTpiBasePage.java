package com.avatar.smartbj.tpipagers;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap.Config;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.avatar.smartbj.R;
import com.avatar.smartbj.activity.MainActivity;
import com.avatar.smartbj.domain.NewsCenterData.NewsData.ViewTagData;
import com.avatar.smartbj.domain.TPINewsData;
import com.avatar.smartbj.domain.TPINewsData.TPINewsData_Data.TPINewsData_Data_ListNewsData;
import com.avatar.smartbj.domain.TPINewsData.TPINewsData_Data.TPINewsData_Data_LunBoData;
import com.avatar.smartbj.utils.DensityUtil;
import com.avatar.smartbj.utils.MyConstanse;
import com.avatar.smartbj.utils.SpTool;
import com.avatar.smartbj.view.RefreshListView;
import com.avatar.smartbj.view.RefreshListView.OnRefreshDataListener;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

public class NewsTpiBasePage {

	private static final Class<Object> TPINewsData = null;
	private MainActivity mainActivity;
	private View root;
	private ViewTagData viewTagData;
	// 轮播图的数据
	private List<TPINewsData_Data_LunBoData> loopPlayDatas = new ArrayList<TPINewsData.TPINewsData_Data.TPINewsData_Data_LunBoData>();
	private List<TPINewsData_Data_ListNewsData> listNews = new ArrayList<TPINewsData.TPINewsData_Data.TPINewsData_Data_ListNewsData>();
	private Gson gson;
	private BitmapUtils bitmapUtils;
	private LoopBoTask loopTask;
	private boolean isFresh = false;// 记录是否是刷新数据
	private String loadingMoreDatasUrl;// 加载更多数据的url
	private String loadingDataUrl;
	// private Handler handler;

	@ViewInject(R.id.vp_tpi_news_lunbo_pic)
	private ViewPager loopViewPager;
	@ViewInject(R.id.tv_tpi_news_pic_desc)
	private TextView tv_title;
	@ViewInject(R.id.ll_tpi_news_pic_points)
	private LinearLayout ll_dots;
	@ViewInject(R.id.lv_tpi_news_listnews)
	private RefreshListView lv_news;
	private LoopPlayAdapter loopAdapter;
	private int picSelectIndex;
	private ListNewsAdapter listNewsAdapter;

	public NewsTpiBasePage(MainActivity mainActivity, ViewTagData viewTagData) {
		this.mainActivity = mainActivity;
		this.viewTagData = viewTagData;
		gson = new Gson();
		bitmapUtils = new BitmapUtils(mainActivity);
		bitmapUtils.configDefaultBitmapConfig(Config.ARGB_4444);
		loopTask = new LoopBoTask();
		initView();
		initData();
		initEvent();
	}

	private void initView() {
		root = View.inflate(mainActivity, R.layout.tpi_news_centent, null);
		ViewUtils.inject(this, root);
		View loopView = View.inflate(mainActivity,
				R.layout.news_center_loop_pic, null);
		ViewUtils.inject(this, loopView);
		lv_news.setIsRefreshHead(true);
		//把轮播图加到listView中
		lv_news.addHeaderView(loopView);
	}

	private void initEvent() {
		loopViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				picSelectIndex = position;
				setPicDescAndPointSelect(position);

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

		lv_news.setOnRefreshDataListener(new OnRefreshDataListener() {

			public void refresdData() {
				isFresh = true;
				// 刷新数据
				getDataFromNet(MyConstanse.SERVERURL + viewTagData.url, false);
				// 改变listview的状态
			}

			@Override
			public void loadingMore() {
				// 判断是否有更多的数据
				if (TextUtils.isEmpty(loadingMoreDatasUrl)) {
					Toast.makeText(mainActivity, "没有更多数据", 1).show();
					// 关闭刷新数据的状态
					lv_news.refreshStateFinish();
				} else {
					System.out.println("url:" + loadingMoreDatasUrl);
					// 有数据
					getDataFromNet(loadingMoreDatasUrl, true);
				}
			}
		});
	}

	private void initData() {
		loopAdapter = new LoopPlayAdapter();
		loopViewPager.setAdapter(loopAdapter);

		// 新闻列表的适配器
		listNewsAdapter = new ListNewsAdapter();
		// 设置新闻列表适配
		lv_news.setAdapter(listNewsAdapter);
		// 先读取本地数据
		String jsonCache = SpTool.getString(mainActivity, MyConstanse.SERVERURL
				+ viewTagData.url, "");
		// 从本地获取数据
		if (!TextUtils.isEmpty(jsonCache)) {
			// 有数据，解析数据
			TPINewsData newsData = parseJson(jsonCache);
			// 处理数据
			processData(newsData);
		}
		loadingDataUrl = MyConstanse.SERVERURL + viewTagData.url;
		getDataFromNet(loadingDataUrl, false);// 从网络获取数据
	}

	private void processData(TPINewsData newsData) {

		// 完成数据的处理
		// 1.设置轮播图的数据
		setLoopPlayData(newsData);
		// 2.轮播图对应的点处理
		initPoints();// 初始化轮播图的点
		// 3. 设置图片描述和点的选中效果
		setPicDescAndPointSelect(picSelectIndex);
		// 4. 开始轮播图
		loopTask.startLunbo();
		// 5. 新闻列表的数据
		setListViewNews(newsData);
	}

	private void setPicDescAndPointSelect(int picSelectIndex) {
		// 设置描述信息
		tv_title.setText(loopPlayDatas.get(picSelectIndex).title);

		// 设置点是否是选中
		for (int i = 0; i < loopPlayDatas.size(); i++) {
			ll_dots.getChildAt(i).setEnabled(i == picSelectIndex);
		}
	}

	private void initPoints() {
		ll_dots.removeAllViews();
		// 动态添加点
		for (int i = 0; i < loopPlayDatas.size(); i++) {
			View v_point = new View(mainActivity);
			v_point.setBackgroundResource(R.drawable.point_seletor);
			v_point.setEnabled(false);// 默认是默认的灰色点
			LayoutParams params = new LayoutParams(DensityUtil.dip2px(
					mainActivity, 5), DensityUtil.dip2px(mainActivity, 5));
			params.leftMargin = DensityUtil.dip2px(mainActivity, 10);
			v_point.setLayoutParams(params);
			ll_dots.addView(v_point);
		}
	}

	private void setLoopPlayData(TPINewsData newsData) {
		// 获取轮播图的数据
		loopPlayDatas = newsData.data.topnews;

		loopAdapter.notifyDataSetChanged();// 更新界面

	}

	/**
	 * 设置新闻列表的数据
	 * 
	 * @param newsData
	 */
	private void setListViewNews(TPINewsData newsData) {

		listNews = newsData.data.news;
		// 更新界面
		listNewsAdapter.notifyDataSetChanged();
	}

	private class LoopPlayAdapter extends PagerAdapter {

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView iv_Loop_pic = new ImageView(mainActivity);
			iv_Loop_pic.setScaleType(ScaleType.FIT_XY);

			// 设备默认的图片,网络缓慢
			iv_Loop_pic.setImageResource(R.drawable.home_scroll_default);

			// 给图片添加数据
			TPINewsData_Data_LunBoData tpiNewsData_Data_LunBoData = loopPlayDatas
					.get(position);

			// 图片的url
			String topimageUrl = tpiNewsData_Data_LunBoData.topimage;

			// 把url的图片给iv_Loop_pic
			// 异步加载图片，并且显示到组件中
			bitmapUtils.display(iv_Loop_pic, topimageUrl);

			// 给图片添加触摸事件
			iv_Loop_pic.setOnTouchListener(new OnTouchListener() {

				private float downX;
				private float downY;
				private long downTime;

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:// 按下停止轮播
						System.out.println("按下");
						downX = event.getX();
						downY = event.getY();
						downTime = System.currentTimeMillis();
						loopTask.stopLunbo();
						break;
					case MotionEvent.ACTION_CANCEL:// 事件取消
						loopTask.startLunbo();
						break;
					case MotionEvent.ACTION_UP:// 松开
						float upX = event.getX();
						float upY = event.getY();

						if (upX == downX && upY == downY) {
							long upTime = System.currentTimeMillis();
							if (upTime - downTime < 500) {
								// 点击
								lunboPicClick("被单击了。。。。。");
							}
						}
						System.out.println("松开");
						loopTask.startLunbo();// 开始轮播
						break;
					default:
						break;
					}
					return true;
				}

				private void lunboPicClick(Object data) {
					// 处理图片的点击事件
					System.out.println(data);

				}
			});

			container.addView(iv_Loop_pic);

			return iv_Loop_pic;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public int getCount() {
			return loopPlayDatas.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	}

	private void getDataFromNet(final String url, final boolean isLoadingMore) {
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// 请求数据成功
				String jsonData = responseInfo.result;

				// 保存数据到本地
				SpTool.setString(mainActivity, viewTagData.url, jsonData);

				// 解析数据
				TPINewsData newsData = parseJson(jsonData);

				// 判断是否是加载更多数据
				if (isLoadingMore) {
					// 原有的数据 +　新数据
					System.out.println(listNews.size() + "<<");
					listNews.addAll(newsData.data.news);
					System.out.println(listNews.size() + ">>");
					// 更新界面
					listNewsAdapter.notifyDataSetChanged();
					Toast.makeText(mainActivity, "加载数据成功", 1).show();
				} else {
					// 第一次取数据或刷新数据
					// 处理数据
					processData(newsData);
					if (isFresh) {
						// 设置listview头隐藏

						Toast.makeText(mainActivity, "刷新数据成功", 1).show();
					}
				}
				lv_news.refreshStateFinish();

			}

			@Override
			public void onFailure(HttpException error, String msg) {
				if (isFresh) {
					lv_news.refreshStateFinish();
					Toast.makeText(mainActivity, "刷新数据失败", 1).show();
				}
			}
		});
	}

	private TPINewsData parseJson(String jsonData) {
		TPINewsData tpiNewsData = gson.fromJson(jsonData, TPINewsData.class);
		System.out.println("tpiNewsData.data.more:" + tpiNewsData.data.more);
		if (!TextUtils.isEmpty(tpiNewsData.data.more)) {
			loadingMoreDatasUrl = MyConstanse.SERVERURL + tpiNewsData.data.more;
			System.out.println("loadingMoreDatasUrl:" + loadingMoreDatasUrl);
		} else {
			loadingMoreDatasUrl = "";
			// System.out.println("loadingMoreDatasUrl:" + loadingMoreDatasUrl);
		}
		return tpiNewsData;
	}

	public View getRootView() {
		return root;
	}

	private class LoopBoTask extends Handler implements Runnable {

		public void stopLunbo() {
			// 移除当前所有的任务
			removeCallbacksAndMessages(null);
		}

		public void startLunbo() {
			stopLunbo();
			postDelayed(this, 2000);
		}

		@Override
		public void run() {
			// 控制轮播图的显示
			loopViewPager.setCurrentItem((loopViewPager.getCurrentItem() + 1)
					% loopViewPager.getAdapter().getCount());
			postDelayed(this, 2000);
		}
	}

	private class ListNewsAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listNews.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(mainActivity,
						R.layout.tpi_news_listview_item, null);
				holder = new ViewHolder();
				holder.iv_icon = (ImageView) convertView
						.findViewById(R.id.iv_tpi_news_listview_item_icon);
				holder.iv_newspic = (ImageView) convertView
						.findViewById(R.id.iv_tpi_news_listview_item_pic);
				holder.tv_title = (TextView) convertView
						.findViewById(R.id.tv_tpi_news_listview_item_title);
				holder.tv_time = (TextView) convertView
						.findViewById(R.id.tv_tpi_news_listview_item_time);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			// 设置数据

			TPINewsData_Data_ListNewsData tpiNewsData_Data_ListNewsData = listNews
					.get(position);
			// 设置标题
			holder.tv_title.setText(tpiNewsData_Data_ListNewsData.title);

			// 设置时间
			holder.tv_time.setText(tpiNewsData_Data_ListNewsData.pubdate);

			// 设置图片
			bitmapUtils.display(holder.iv_newspic,
					tpiNewsData_Data_ListNewsData.listimage);

			return convertView;
		}

	}

	private class ViewHolder {
		ImageView iv_newspic;
		TextView tv_title;
		TextView tv_time;
		ImageView iv_icon;
	}
	// /**
	// * 处理轮播图
	// */
	// private void lunboProcess() {
	// if (handler == null) {
	//
	// handler = new Handler();
	// }
	// //清空掉原来所有的任务
	// handler.removeCallbacksAndMessages(null);
	//
	// handler.postDelayed(new Runnable() {
	//
	// @Override
	// public void run() {
	// //任务
	// //控制轮播图的显示
	// loopViewPager.setCurrentItem((loopViewPager.getCurrentItem() + 1) %
	// loopViewPager.getAdapter().getCount());
	// handler.postDelayed(this, 1500);
	// }
	// }, 1500);
	// }
}
