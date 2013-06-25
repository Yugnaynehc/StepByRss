package com.example.stepbystep;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.utility.DBUtil;

import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.view.LayoutInflater;
import android.view.View.OnTouchListener;

public class attentionpeopleActivity extends Activity {

	// 我关注的人页面
	private ViewGroup main = null;

	// 搜索栏
	private EditText SearchText;// 搜索词
	private ImageButton SearchButton;// 搜索按钮

	// 关注展示
	private ListView EXlist;// 关注列表
	private ArrayList<String> attention = null;// 关注数组

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.attentionpeople);
		// 初始化控件
		initeViews();

	}

	private void initeViews() {

		EXlist = (ListView) findViewById(R.id.EXList3);
		attention = DBUtil.getAttentionList(AVB.getUserName());
		// 生成动态数组，加入数据
		final ArrayList<HashMap<String, String>> EXlistItem = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < attention.size(); ++i) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("user_ID", attention.get(i));// 用户ID
			EXlistItem.add(map);
		}
		// 生成适配器的Item和动态数组对应的元素
		SimpleAdapter EXlistItemAdapter = new SimpleAdapter(this, EXlistItem,// 数据源
				R.layout.peo,// ListItem的XML实现
				// 动态数组与EXItem对应的子项
				new String[] { "user_ID" },
				// EXItem的XML文件里面的三个TextView ID
				new int[] { R.id.user_ID });

		// 添加并且显示
		EXlist.setAdapter(EXlistItemAdapter);

		// 添加点击
		EXlist.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// 实例化intent
				Intent intent = new Intent();
				AVB.setSelectUserName((EXlistItem.get(arg2)).get("user_ID")
						.toString());
				intent.setClass(attentionpeopleActivity.this, TAmainpage.class);
				// 启动Activity
				startActivity(intent);
				attentionpeopleActivity.this.finish();
			}
		});

		SearchButton = (ImageButton) findViewById(R.id.SearchButton2);
		SearchText = (EditText) findViewById(R.id.SearchName);
		
		SearchButton = (ImageButton) findViewById(R.id.SearchButton);
		/*
		SearchButton.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					SearchButton.setBackgroundResource(R.drawable.search2);
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					SearchButton.setBackgroundResource(R.drawable.search);

					if (ObjectButton.getTag() == "EX") {
						Toast.makeText(EXshow.this, "正在提交搜索请求",
								Toast.LENGTH_SHORT).show();
						attention = DBUtil.searchKnowledge(search.getText()
								.toString());
						if (attention.size() != 0) {
							showex();
							Toast.makeText(EXshow.this, "搜索完成",
									Toast.LENGTH_SHORT).show();
						} else
							Toast.makeText(EXshow.this, "没有搜索到合适的结果",
									Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(EXshow.this, "正在提交搜索请求",
								Toast.LENGTH_SHORT).show();
						attention = DBUtil.searchUser(search.getText()
								.toString());
						if (attention.size() != 0) {
							showpeo();
							Toast.makeText(EXshow.this, "搜索完成",
									Toast.LENGTH_SHORT).show();
						} else
							Toast.makeText(EXshow.this, "没有搜索到合适的结果",
									Toast.LENGTH_LONG).show();
					}

				}
				return false;
			}

		});
		*/

	}

}