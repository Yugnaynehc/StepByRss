package com.example.stepbystep;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.utility.DBUtil;

import android.R.string;
import android.app.Activity;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View.OnTouchListener;

public class MymainpageActivity extends ActivityGroup {

	// 跳转的页面
	private Intent intent;
	// 选择的当前用户的用户名
	private String usernameString;
	// 我的主页页面
	private TextView userName;
	private TextView userInfo;
	private TextView userEXNum;
	private ImageButton user_img;
	private ImageButton mainpage_btn;
	// 设置展示主体
	private View showMain = null;// 展示主体
	private LinearLayout lshowMain = null;
	private LayoutParams params = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mymainpage);
		AVB.setSelectUserName(AVB.getUserName());
		// 初始化控件
		initeViews();

	}

	private void initeViews() {

		// 初始页面（我的经验）
		show_myex();

		userName = (TextView) findViewById(R.id.user_name);
		usernameString = AVB.getSelectUserName();
		userName.setText(usernameString);
		userInfo = (TextView) findViewById(R.id.user_info);
		userInfo.setText(DBUtil.getSign(usernameString));
		userEXNum = (TextView) findViewById(R.id.user_EX_num);
		userEXNum.setText(DBUtil.getMark(usernameString));
		// 头像按钮
		user_img = (ImageButton) findViewById(R.id.user_img);
		user_img.setImageResource(R.drawable.user_img);
		// 头像按钮功能
		user_img.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				head();
			}
		});
		// 更多显示功能按钮
		mainpage_btn = (ImageButton) findViewById(R.id.mainpage_btn);
		mainpage_btn.setBackgroundResource(R.drawable.more);
		mainpage_btn.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					mainpage_btn.setBackgroundResource(R.drawable.more2);
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					mainpage_btn.setBackgroundResource(R.drawable.more);

					verifyDialog();

				}
				return false;
			}

		});

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) 
			{	Intent intent = new Intent();
				
				intent.setClass(MymainpageActivity.this,SBSMainActivity.class);
				startActivity(intent); 
				MymainpageActivity.this.finish();
			}
		return false;
	}

	private void verifyDialog() {
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.mainpage_btn, null);

		final Dialog dialog = new AlertDialog.Builder(this).setView(layout)
				.setNegativeButton("返回", null).create();

		Button button = (Button) layout.findViewById(R.id.myEX);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				show_myex();
				dialog.dismiss();
			}

		});

		Button button2 = (Button) layout.findViewById(R.id.messageboard);
		button2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				show_messageboard();
				dialog.dismiss();
			}

		});
		dialog.show();
	}

	// 头像功能按钮
	private void head() {
		intent = new Intent(MymainpageActivity.this, LookpersonActivity.class);
		showMain = getLocalActivityManager().startActivity(
				"LookpersonActivity", intent).getDecorView();

		params = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		lshowMain = (LinearLayout) findViewById(R.id.show_layout);
		lshowMain.removeAllViews();
		lshowMain.addView(showMain, params);
	}

	// 我的经验显示
	private void show_myex() {

		intent = new Intent(MymainpageActivity.this, myexshow.class);
		showMain = getLocalActivityManager().startActivity("myexshow", intent)
				.getDecorView();

		params = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		lshowMain = (LinearLayout) findViewById(R.id.show_layout);
		lshowMain.removeAllViews();
		lshowMain.addView(showMain, params);

	}

	// 留言板显示
	private void show_messageboard() {

		intent = new Intent(MymainpageActivity.this, message.class);
		showMain = getLocalActivityManager().startActivity("message", intent)
				.getDecorView();

		params = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		lshowMain = (LinearLayout) findViewById(R.id.show_layout);
		lshowMain.removeAllViews();
		lshowMain.addView(showMain, params);

	}
}
