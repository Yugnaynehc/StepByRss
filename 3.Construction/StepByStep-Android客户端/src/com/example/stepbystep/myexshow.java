package com.example.stepbystep;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.utility.DBUtil;
import com.example.utility.DimensionUtility;

import android.R.string;
import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class myexshow extends Activity {

	private ArrayList<String> userKnowledge = null;// 用户经验数组
	private int count; // 用户经验总数
	// 我的经验页面
	private ListView EXlist;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myexshow);
		userKnowledge = DBUtil.getUserKnowledge(AVB.getSelectUserName());
		count = userKnowledge.size() / 3;
		System.out.println(userKnowledge);

		initeViews();
	}

	private void initeViews() {

		// 绑定Layout里面的ListView
		EXlist = (ListView) findViewById(R.id.EXList);
		// 生成动态数组，加入数据
		final ArrayList<HashMap<String, String>> EXlistItem = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < userKnowledge.size(); i = i + 3) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("EX_Title", userKnowledge.get(i));// 图像资源的ID
			map.put("EX_ID", userKnowledge.get(i + 1));
			map.put("EX_Time", userKnowledge.get(i + 2));
			map.put("EX_Man", AVB.getUserName());
			EXlistItem.add(map);
		}
		// 生成适配器的Item和动态数组对应的元素
		SimpleAdapter EXlistItemAdapter = new SimpleAdapter(this, EXlistItem,// 数据源
				R.layout.ex,// ListItem的XML实现
				// 动态数组与EXItem对应的子项
				new String[] { "EX_Title", "EX_Time", "EX_Man" },
				// EXItem的XML文件里面的三个TextView ID
				new int[] { R.id.EX_Title, R.id.EX_Time, R.id.EX_Man });

		// 添加并且显示
		EXlist.setAdapter(EXlistItemAdapter);

		// 添加点击
		EXlist.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// 实例化intent
				Intent intent = new Intent();
				// 设置跳转的界面
				AVB.setKnowledgeID((EXlistItem.get(arg2)).get("EX_ID")
						.toString());
				DBUtil.addHit(AVB.getKnowledgeID());
				intent.setClass(myexshow.this, LookActivity.class);
				// 启动Activity
				startActivity(intent);
				myexshow.this.finish();
			}
		});
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// 实例化intent
			Intent intent = new Intent();
			// 设置跳转的界面
			intent.setClass(myexshow.this, SBSMainActivity.class);
			// 启动Activity
			startActivity(intent);
			myexshow.this.finish();

		}
		return false;
	}
}
