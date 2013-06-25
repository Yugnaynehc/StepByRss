package com.example.stepbystep;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.stepbystep.R;
import com.example.utility.DBUtil;
import com.example.layout.SlideImageLayout;
import com.example.parser.NewsXmlParser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class EXshow extends Activity {

	private EditText search = null; // 搜索栏
	private ImageButton ObjectButton;// 按经验或作者搜索
	private ImageButton search_btn;// 搜索按钮
	private LinearLayout SearchLayout = null; 
	// 排行榜
	private ImageButton zuire;// 最热经验
	private ImageButton zuixin;// 最新经验
	private ImageButton zuizan;// 最赞经验
	private ImageButton xuans;// 悬赏排行
	// 经验展示
	private ListView EXlist;// 经验列表
	private ArrayList<String> knowledge = null;// 经验数组

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exshow);
		// 初始化
		initeViews();
	}

	/**
	 * 初始化
	 */
	private void initeViews() {
		SearchLayout = (LinearLayout) findViewById(R.id.SearchLayout);
		SearchLayout.setBackgroundColor(AVB.getshang());

		// 排行
		zuire = (ImageButton) findViewById(R.id.I1);
		zuire.setBackgroundResource(R.drawable.hot);
		zuire.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(EXshow.this, "正在提交搜索请求", Toast.LENGTH_SHORT)
						.show();
				knowledge = DBUtil.getKnowledgeSortByHot();
				if (knowledge.size() != 0) {
					showex();
					Toast.makeText(EXshow.this, "搜索完成", Toast.LENGTH_SHORT)
							.show();
				} else
					Toast.makeText(EXshow.this, "没有搜索到合适的结果", Toast.LENGTH_LONG)
							.show();
			}
		});
		zuixin = (ImageButton) findViewById(R.id.I2);
		zuixin.setBackgroundResource(R.drawable.newha);
		zuixin.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(EXshow.this, "正在提交搜索请求", Toast.LENGTH_SHORT)
						.show();
				knowledge = DBUtil.getKnowledgeSortByID();
				if (knowledge.size() != 0) {
					showex();
					Toast.makeText(EXshow.this, "搜索完成", Toast.LENGTH_SHORT)
							.show();
				} else
					Toast.makeText(EXshow.this, "没有搜索到合适的结果",
							Toast.LENGTH_SHORT).show();
			}
		});
		zuizan = (ImageButton) findViewById(R.id.I3);
		zuizan.setBackgroundResource(R.drawable.awesome);
		zuizan.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(EXshow.this, "正在提交搜索请求", Toast.LENGTH_SHORT)
						.show();
				knowledge = DBUtil.getKnowledgeSortByZan();
				if (knowledge.size() != 0) {
					showex();
					Toast.makeText(EXshow.this, "搜索完成", Toast.LENGTH_SHORT)
							.show();
				} else
					Toast.makeText(EXshow.this, "没有搜索到合适的结果",
							Toast.LENGTH_SHORT).show();
			}
		});
		xuans = (ImageButton) findViewById(R.id.I4);
		xuans.setBackgroundResource(R.drawable.want);
		xuans.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(EXshow.this, "正在提交搜索请求", Toast.LENGTH_SHORT)
						.show();
				knowledge = DBUtil.getKnowledgeSortByWanted();
				if (knowledge.size() != 0) {
					showwant();
					Toast.makeText(EXshow.this, "搜索完成", Toast.LENGTH_SHORT)
							.show();
				} else
					Toast.makeText(EXshow.this, "没有搜索到合适的结果",
							Toast.LENGTH_SHORT).show();
			}
		});
		// 搜索栏设置
		search = (EditText) findViewById(R.id.SearchText);
		ObjectButton = (ImageButton) findViewById(R.id.ObjectButton);
		ObjectButton.setTag("EX");
		ObjectButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {

				if (ObjectButton.getTag() == "EX") {
					ObjectButton.setTag("PER");
					ObjectButton.setBackgroundResource(R.drawable.peop);
				} else {
					if (!AVB.getIsUser()) {
						Toast.makeText(EXshow.this, "请先登录", Toast.LENGTH_SHORT)
								.show();
					} else {
						ObjectButton.setTag("EX");
						ObjectButton.setBackgroundResource(R.drawable.know);
					}
				}
			}
		});
		search_btn = (ImageButton) findViewById(R.id.SearchButton);
		search_btn.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					search_btn.setBackgroundResource(R.drawable.search2);
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					search_btn.setBackgroundResource(R.drawable.search);

					if (ObjectButton.getTag() == "EX") {
						Toast.makeText(EXshow.this, "正在提交搜索请求",
								Toast.LENGTH_SHORT).show();
						knowledge = DBUtil.searchKnowledge(search.getText()
								.toString());
						if (knowledge.size() != 0) {
							showex();
							Toast.makeText(EXshow.this, "搜索完成",
									Toast.LENGTH_SHORT).show();
						} else
							Toast.makeText(EXshow.this, "没有搜索到合适的结果",
									Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(EXshow.this, "正在提交搜索请求",
								Toast.LENGTH_SHORT).show();
						knowledge = DBUtil.searchUser(search.getText()
								.toString());
						if (knowledge.size() != 0) {
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
		knowledge = DBUtil.getKnowledgeSortByID();
		if (knowledge.size() != 0)
			showex();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			AlertDialog.Builder builder = new Builder(EXshow.this);
			builder.setMessage("确认退出吗？");

			builder.setTitle("提示");

			builder.setPositiveButton("确认", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					if (AVB.getIsUser() == true) {
						DBUtil.exit(AVB.getUserName());
						AVB.setIsUser(false);
					}

					EXshow.this.finish();
				}
			});
			builder.setNegativeButton("取消", new OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder.create().show();
		}
		return false;
	}

	// 搜索经验页面
	private void showex() {
		EXlist = (ListView) findViewById(R.id.EXList2);

		// 生成动态数组，加入数据
		final ArrayList<HashMap<String, String>> EXlistItem = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < knowledge.size(); i = i + 4) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("EX_Title", knowledge.get(i));// 图像资源的ID
			map.put("EX_Man", knowledge.get(i + 1));
			map.put("EX_ID", knowledge.get(i + 2));
			map.put("EX_Time", knowledge.get(i + 3));

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
				intent.setClass(EXshow.this, LookActivity.class);
				// 启动Activity
				startActivity(intent);
				EXshow.this.finish();
			}
		});
	}

	// 搜索用户页面
	private void showpeo() {
		EXlist = (ListView) findViewById(R.id.EXList2);

		// 生成动态数组，加入数据
		final ArrayList<HashMap<String, String>> EXlistItem = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < knowledge.size(); ++i) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("user_ID", knowledge.get(i));// 用户ID
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
				intent.setClass(EXshow.this, TAmainpage.class);
				// 启动Activity
				startActivity(intent);
				EXshow.this.finish();
			}
		});

	}

	// 搜索悬赏页面
	private void showwant() {

	}
}
