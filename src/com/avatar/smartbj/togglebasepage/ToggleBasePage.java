package com.avatar.smartbj.togglebasepage;

import android.view.View;

import com.avatar.smartbj.activity.MainActivity;

public abstract class ToggleBasePage {

	protected MainActivity mainActivity;
	protected View root;

	public ToggleBasePage(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
		initView();
		initEvent();
		root = initView();
	}

	public void initEvent() {

	}

	public abstract View initView();;

	public void initData() {

	};

	public View getView() {
		return root;
	}
}
