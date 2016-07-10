package com.avatar.smartbj.view;

import com.avatar.smartbj.activity.MainActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Lenovo
 * @创建时间2016年7月10日下午1:18:35
 * @描述fragement的基类
 */
public abstract class BaseFragment extends Fragment {

	protected MainActivity mainActivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mainActivity = (MainActivity) getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = initView();
		return root;

	}

	public abstract View initView();
	


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData();
		initEvent();
	}

	public void initData() {
		
	}

	public void initEvent() {
		
	}

}
