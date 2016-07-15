package com.avatar.smartbj.basepage;

import java.util.ArrayList;
import java.util.List;

import com.avatar.smartbj.activity.MainActivity;
import com.avatar.smartbj.domain.NewsCenterData;
import com.avatar.smartbj.togglebasepage.IntteractBaseTogglePage;
import com.avatar.smartbj.togglebasepage.NewsBaseTogglePage;
import com.avatar.smartbj.togglebasepage.PhotoBaseTogglePage;
import com.avatar.smartbj.togglebasepage.ToggleBasePage;
import com.avatar.smartbj.togglebasepage.TopicBaseTogglePage;
import com.avatar.smartbj.utils.MyConstanse;
import com.avatar.smartbj.view.BehindFragment.OnSwitchPagerListener;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

/**
 * @author Lenovo
 * @创建时间2016年7月10日下午9:53:25
 * @描述TODO
 */
public class NewsCenterBaseTagPager extends BaseTagPage {

	private List<ToggleBasePage> togglePagers = new ArrayList<ToggleBasePage>();
	private NewsCenterData newsCenterData;

	public NewsCenterBaseTagPager(MainActivity context) {
		super(context);
	}

	@Override
	public void initData() {
		// 获取网络json数据
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.POST, MyConstanse.NEWSCENTERURL,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String json = responseInfo.result;
						// 解析Json
						ParseJson(json);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						System.out.println("网络请求异常： " + error);
					}

				});
		super.initData();
	}

	private void ParseJson(String json) {
		Gson gson = new Gson();
		newsCenterData = gson.fromJson(json, NewsCenterData.class);
		// 将新闻json显示到左侧behindfagment页签中。
		mainActivity.getBehindFagment().setLeftMenuData(newsCenterData.data);
		// new BehindFragment().setLeftMenuData(newsCenterData.data); //I don't
		// know that the new object will cause the nullPointerException.
		// System.out.println(fromJson.data.get(0).children.get(0).title);

		// 将左侧页标签分别对应的标题放入容器，遍历
		for (NewsCenterData.NewsData data : newsCenterData.data) {
			ToggleBasePage basePage = null;
			switch (data.type) {
			case 1:// news
				basePage = new NewsBaseTogglePage(mainActivity);
				break;
			case 10:// topic
				basePage = new TopicBaseTogglePage(mainActivity);
				break;
			case 2:// photo
				basePage = new PhotoBaseTogglePage(mainActivity);
				break;
			case 3:// Interact
				basePage = new IntteractBaseTogglePage(mainActivity);
				break;

			default:
				break;

			}
			togglePagers.add(basePage);

		}

		switchPager(0);

	}

	@Override
	public void switchPager(int possition) {
		ToggleBasePage page = togglePagers.get(possition);
		tv_title.setText(newsCenterData.data.get(possition).title);
		content_fr.removeAllViews();
		content_fr.addView(page.getView());
	}

	@Override
	public void initEvent() {
		mainActivity.getBehindFagment().setOnSwitchPagerListener(new OnSwitchPagerListener() {
			
			@Override
			public void switchPager(int selectIndex) {
				NewsCenterBaseTagPager.this.switchPager(selectIndex);
			}
		});
		super.initEvent();
		
	}
}
