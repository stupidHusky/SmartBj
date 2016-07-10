package com.avatar.smartbj.basepage;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.avatar.smartbj.R;

/**
 * @author Lenovo
 * @创建时间2016年7月10日下午9:25:17
 * @描述TODO
 */
public class BaseTagPage {
	protected Context context;
	protected View root;
	protected TextView tv_title;
	protected ImageView im_menu;
	protected FrameLayout content_fr;

	public BaseTagPage(Context context) {
		this.context = context;
		initView();
		initData();
		initEvent();
	}

	public void initEvent() {

	}

	public void initData() {

	}

	public void initView() {
		root = View.inflate(context, R.layout.fragment_content_base_content,
				null);
		tv_title = (TextView) root.findViewById(R.id.main_base_title);
		im_menu = (ImageView) root.findViewById(R.id.main_base_menu);
		content_fr = (FrameLayout) root
				.findViewById(R.id.main_base_framelayout);
	}

	public View getView() {
		return root;
	}
}
