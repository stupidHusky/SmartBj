package com.avatar.smartbj.domain;

import java.util.List;


/**
 *@author Lenovo
 *@创建时间2016年7月10日下午19:53:25
 *@描述TODO 新闻中心数据封装
 */
public class NewsCenterData {
	public int retcode;

	public List<NewsData> data;// 新闻的数据

	public class NewsData {
		public List<ViewTagData> children;

		public class ViewTagData {
			public String id;
			public String title;
			public int type;
			public String url;
		}

		public String id;

		public String title;
		public int type;

		public String url;
		public String url1;

		public String dayurl;
		public String excurl;

		public String weekurl;
	}

	public List<String> extend;

}
