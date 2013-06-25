package com.example.stepbystep;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.utility.DBUtil;

import android.R.string;
import android.app.Activity;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.Dialog;
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

public class TAmainpage extends ActivityGroup {

	// ��ת��ҳ��
	private Intent intent;
	// ѡ��ĵ�ǰ�û����û���
	private String usernameString;
	// �ҵ���ҳҳ��
	private TextView userName;
	private TextView userInfo;
	private TextView userEXNum;
	private ImageButton user_img;
	private ImageButton mainpage_btn;
	private Button button1, button3;	
	// ����չʾ����
	private View showMain = null;// չʾ����
	private LinearLayout lshowMain = null;
	private LayoutParams params = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mymainpage);
		// ��ʼ���ؼ�
		initeViews();

	}

	private void initeViews() {

		// ��ʼҳ�棨�ҵľ��飩
		show_TAex();

		userName = (TextView) findViewById(R.id.user_name);
		usernameString = AVB.getSelectUserName();
		userName.setText(usernameString);
		userInfo = (TextView) findViewById(R.id.user_info);
		userInfo.setText(DBUtil.getSign(usernameString));
		userEXNum = (TextView) findViewById(R.id.user_EX_num);
		userEXNum.setText(DBUtil.getMark(usernameString));
		// ͷ��ť
		user_img = (ImageButton) findViewById(R.id.user_img);
		user_img.setImageResource(R.drawable.user_img);
		// ͷ��ť����
		if (!AVB.getIsUser()) 
			user_img.setClickable(false);
		user_img.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				head();
			}
		});
		// ������ʾ���ܰ�ť
		mainpage_btn = (ImageButton) findViewById(R.id.mainpage_btn);
		mainpage_btn.setBackgroundResource(R.drawable.more);
		if (!AVB.getIsUser()) 
			mainpage_btn.setClickable(false);
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
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// ʵ����intent
			Intent intent = new Intent();
			// ������ת�Ľ���
			intent.setClass(TAmainpage.this, SBSMainActivity.class);
			// ����Activity
			startActivity(intent);
			TAmainpage.this.finish();

		}
		return false;
	}

	private void verifyDialog() {
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.mainpage_btn2, null);

		final Dialog dialog = new AlertDialog.Builder(this).setView(layout)
				.setNegativeButton("����", null).create();

		// TA�ľ���
		button1 = (Button) layout.findViewById(R.id.TAEX);
		button1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				show_TAex();
				dialog.dismiss();
			}

		});
		// ��ע
		button3 = (Button) layout.findViewById(R.id.payattention);
		if (DBUtil.isAttention(usernameString, AVB.getUserName())
				.equals("true")) {
			button3.setTag("ȡ����ע");
			button3.setText("ȡ����ע");
		} else {
			button3.setTag("��ע");
			button3.setText("��ע");
		}
		button3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (button3.getTag() == "��ע") {
					DBUtil.setAttention(usernameString, AVB.getUserName());
					button3.setTag("ȡ����ע");
					button3.setText("ȡ����ע");
				} else {
					DBUtil.cancelAttention(usernameString, AVB.getUserName());
					button3.setTag("��ע");
					button3.setText("��ע");
				}
				dialog.dismiss();
			}

		});
		dialog.show();
	}

	// ͷ���ܰ�ť
	private void head() {
		intent = new Intent(TAmainpage.this, LookpersonActivity.class);
		showMain = getLocalActivityManager().startActivity(
				"LookpersonActivity", intent).getDecorView();

		params = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		lshowMain = (LinearLayout) findViewById(R.id.show_layout);
		lshowMain.removeAllViews();
		lshowMain.addView(showMain, params);
	}

	// TA�ľ�����ʾ
	private void show_TAex() {

		intent = new Intent(TAmainpage.this, myexshow.class);
		showMain = getLocalActivityManager().startActivity("myexshow", intent)
				.getDecorView();

		params = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		lshowMain = (LinearLayout) findViewById(R.id.show_layout);
		lshowMain.removeAllViews();
		lshowMain.addView(showMain, params);

	}

}
