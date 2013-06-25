package com.example.stepbystep;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.stepbystep.R;
import com.example.stepbystep.SBSMainActivity;
import com.example.stepbystep.EXshow;
import com.example.utility.DBUtil;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ActivityGroup;
import android.app.Application;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;

import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

@SuppressLint("ResourceAsColor")
public class more extends ActivityGroup {

	private Button changecolor;
	private Button exit;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more);

		// 初始化控件
		initeViews();

	}

	private void initeViews() {
		changecolor = (Button) findViewById(R.id.change_color);
		changecolor.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View arg0) {
				ColorDialog();
			}

		});

		exit = (Button) findViewById(R.id.exit);
		exit.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View v) {
				AlertDialog.Builder builder = new Builder(more.this);
				builder.setMessage("确认退出吗？");

				builder.setTitle("提示");

				builder.setPositiveButton("确认", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						if (AVB.getIsUser() == true) {
							DBUtil.exit(AVB.getUserName());
							AVB.setIsUser(false);
						}

						more.this.finish();
					}
				});
				builder.setNegativeButton("取消", new OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				builder.create().show();

			}

		});

	}

	@SuppressLint("ResourceAsColor")
	private void ColorDialog() {
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.color, null);

		final Dialog dialog = new AlertDialog.Builder(this).setView(layout)
				.setNegativeButton("返回", null).create();

		// 下拉列表
		Spinner Spinner1;
		Spinner Spinner2;
		Spinner Spinner3;
		final TextView view1;
		final TextView view2;
		final TextView view3;
		final String[] mList = { "白色", "象牙色", "亮黄色", "雪白色", "粉红色", "天蓝色", "淡紫色" };
		ArrayAdapter adapter1, adapter2, adapter3; // 定义适配器

		Spinner1 = (Spinner) layout.findViewById(R.id.Spinner1);
		Spinner2 = (Spinner) layout.findViewById(R.id.Spinner2);
		Spinner3 = (Spinner) layout.findViewById(R.id.Spinner3);
		view1 = (TextView) layout.findViewById(R.id.shang);
		view2 = (TextView) layout.findViewById(R.id.zhong);
		view3 = (TextView) layout.findViewById(R.id.xia);
		// 将可选内容与ArrayAdapter连接起来
		adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mList);

		// 设置下拉列表的风格
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// 将adapter 添加到spinner中
		Spinner1.setAdapter(adapter1);

		// 添加事件Spinner事件监听
		Spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@SuppressLint("ResourceAsColor")
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				String s = mList[arg2];
				if (s.equals("白色")) {
					int color = getResources().getColor(R.color.white);
					view1.setBackgroundColor(color);
					AVB.setshang(color);
				} else if (s.equals("象牙色")) {

					int color = getResources().getColor(R.color.ivory);
					view1.setBackgroundColor(color);
					AVB.setshang(color);
				} else if (s.equals("亮黄色")) {
					int color = getResources().getColor(R.color.lightyellow);
					view1.setBackgroundColor(color);
					AVB.setshang(color);

				} else if (s.equals("雪白色")) {
					int color = getResources().getColor(R.color.snow);
					view1.setBackgroundColor(color);
					AVB.setshang(color);
					
				} else if (s.equals("粉红色")) {
					int color = getResources().getColor(R.color.pink);
					view1.setBackgroundColor(color);
					AVB.setshang(color);
					
				} else if (s.equals("天蓝色")) {
					int color = getResources().getColor(R.color.azure);
					view1.setBackgroundColor(color);
					AVB.setshang(color);
					
				} else if (s.equals("淡紫色")) {
					int color = getResources().getColor(R.color.lavender);
					view1.setBackgroundColor(color);
					AVB.setshang(color);
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});

		// 设置默认值
		Spinner1.setVisibility(View.VISIBLE);

		// 将可选内容与ArrayAdapter连接起来
		adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mList);

		// 设置下拉列表的风格
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// 将adapter 添加到spinner中
		Spinner2.setAdapter(adapter2);

		// 添加事件Spinner事件监听
		Spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@SuppressLint("ResourceAsColor")
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				String s = mList[arg2];
				if (s.equals("白色")) {
					int color = getResources().getColor(R.color.white);
					view2.setBackgroundColor(color);
					AVB.setzhong(color);
				} else if (s.equals("象牙色")) {

					int color = getResources().getColor(R.color.ivory);
					view2.setBackgroundColor(color);
					AVB.setzhong(color);
				} else if (s.equals("亮黄色")) {
					int color = getResources().getColor(R.color.lightyellow);
					view2.setBackgroundColor(color);
					AVB.setzhong(color);

				} else if (s.equals("雪白色")) {
					int color = getResources().getColor(R.color.snow);
					view2.setBackgroundColor(color);
					AVB.setzhong(color);
					
				} else if (s.equals("粉红色")) {
					int color = getResources().getColor(R.color.pink);
					view2.setBackgroundColor(color);
					AVB.setzhong(color);
					
				} else if (s.equals("天蓝色")) {
					int color = getResources().getColor(R.color.azure);
					view2.setBackgroundColor(color);
					AVB.setzhong(color);
					
				} else if (s.equals("淡紫色")) {
					int color = getResources().getColor(R.color.lavender);
					view2.setBackgroundColor(color);
					AVB.setzhong(color);
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});

		// 设置默认值
		Spinner2.setVisibility(View.VISIBLE);

		// 将可选内容与ArrayAdapter连接起来
		adapter3 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mList);

		// 设置下拉列表的风格
		adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// 将adapter 添加到spinner中
		Spinner3.setAdapter(adapter3);

		// 添加事件Spinner事件监听
		Spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@SuppressLint("ResourceAsColor")
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				String s = mList[arg2];
				if (s.equals("白色")) {
					int color = getResources().getColor(R.color.white);
					view3.setBackgroundColor(color);
					AVB.setxia(color);
				} else if (s.equals("象牙色")) {

					int color = getResources().getColor(R.color.ivory);
					view3.setBackgroundColor(color);
					AVB.setxia(color);
				} else if (s.equals("亮黄色")) {
					int color = getResources().getColor(R.color.lightyellow);
					view3.setBackgroundColor(color);
					AVB.setxia(color);

				} else if (s.equals("雪白色")) {
					int color = getResources().getColor(R.color.snow);
					view3.setBackgroundColor(color);
					AVB.setxia(color);
					
				} else if (s.equals("粉红色")) {
					int color = getResources().getColor(R.color.pink);
					view3.setBackgroundColor(color);
					AVB.setxia(color);
					
				} else if (s.equals("天蓝色")) {
					int color = getResources().getColor(R.color.azure);
					view3.setBackgroundColor(color);
					AVB.setxia(color);
					
				} else if (s.equals("淡紫色")) {
					int color = getResources().getColor(R.color.lavender);
					view3.setBackgroundColor(color);
					AVB.setxia(color);
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});

		// 设置默认值
		Spinner3.setVisibility(View.VISIBLE);

		dialog.show();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Intent intent = new Intent();

			intent.setClass(more.this, SBSMainActivity.class);
			// 启动Activity
			startActivity(intent);
			more.this.finish();
		}
		return false;
	}

}
