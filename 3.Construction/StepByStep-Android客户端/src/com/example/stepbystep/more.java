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

		// ��ʼ���ؼ�
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
				builder.setMessage("ȷ���˳���");

				builder.setTitle("��ʾ");

				builder.setPositiveButton("ȷ��", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						if (AVB.getIsUser() == true) {
							DBUtil.exit(AVB.getUserName());
							AVB.setIsUser(false);
						}

						more.this.finish();
					}
				});
				builder.setNegativeButton("ȡ��", new OnClickListener() {

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
				.setNegativeButton("����", null).create();

		// �����б�
		Spinner Spinner1;
		Spinner Spinner2;
		Spinner Spinner3;
		final TextView view1;
		final TextView view2;
		final TextView view3;
		final String[] mList = { "��ɫ", "����ɫ", "����ɫ", "ѩ��ɫ", "�ۺ�ɫ", "����ɫ", "����ɫ" };
		ArrayAdapter adapter1, adapter2, adapter3; // ����������

		Spinner1 = (Spinner) layout.findViewById(R.id.Spinner1);
		Spinner2 = (Spinner) layout.findViewById(R.id.Spinner2);
		Spinner3 = (Spinner) layout.findViewById(R.id.Spinner3);
		view1 = (TextView) layout.findViewById(R.id.shang);
		view2 = (TextView) layout.findViewById(R.id.zhong);
		view3 = (TextView) layout.findViewById(R.id.xia);
		// ����ѡ������ArrayAdapter��������
		adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mList);

		// ���������б�ķ��
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// ��adapter ��ӵ�spinner��
		Spinner1.setAdapter(adapter1);

		// ����¼�Spinner�¼�����
		Spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@SuppressLint("ResourceAsColor")
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				String s = mList[arg2];
				if (s.equals("��ɫ")) {
					int color = getResources().getColor(R.color.white);
					view1.setBackgroundColor(color);
					AVB.setshang(color);
				} else if (s.equals("����ɫ")) {

					int color = getResources().getColor(R.color.ivory);
					view1.setBackgroundColor(color);
					AVB.setshang(color);
				} else if (s.equals("����ɫ")) {
					int color = getResources().getColor(R.color.lightyellow);
					view1.setBackgroundColor(color);
					AVB.setshang(color);

				} else if (s.equals("ѩ��ɫ")) {
					int color = getResources().getColor(R.color.snow);
					view1.setBackgroundColor(color);
					AVB.setshang(color);
					
				} else if (s.equals("�ۺ�ɫ")) {
					int color = getResources().getColor(R.color.pink);
					view1.setBackgroundColor(color);
					AVB.setshang(color);
					
				} else if (s.equals("����ɫ")) {
					int color = getResources().getColor(R.color.azure);
					view1.setBackgroundColor(color);
					AVB.setshang(color);
					
				} else if (s.equals("����ɫ")) {
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

		// ����Ĭ��ֵ
		Spinner1.setVisibility(View.VISIBLE);

		// ����ѡ������ArrayAdapter��������
		adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mList);

		// ���������б�ķ��
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// ��adapter ��ӵ�spinner��
		Spinner2.setAdapter(adapter2);

		// ����¼�Spinner�¼�����
		Spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@SuppressLint("ResourceAsColor")
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				String s = mList[arg2];
				if (s.equals("��ɫ")) {
					int color = getResources().getColor(R.color.white);
					view2.setBackgroundColor(color);
					AVB.setzhong(color);
				} else if (s.equals("����ɫ")) {

					int color = getResources().getColor(R.color.ivory);
					view2.setBackgroundColor(color);
					AVB.setzhong(color);
				} else if (s.equals("����ɫ")) {
					int color = getResources().getColor(R.color.lightyellow);
					view2.setBackgroundColor(color);
					AVB.setzhong(color);

				} else if (s.equals("ѩ��ɫ")) {
					int color = getResources().getColor(R.color.snow);
					view2.setBackgroundColor(color);
					AVB.setzhong(color);
					
				} else if (s.equals("�ۺ�ɫ")) {
					int color = getResources().getColor(R.color.pink);
					view2.setBackgroundColor(color);
					AVB.setzhong(color);
					
				} else if (s.equals("����ɫ")) {
					int color = getResources().getColor(R.color.azure);
					view2.setBackgroundColor(color);
					AVB.setzhong(color);
					
				} else if (s.equals("����ɫ")) {
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

		// ����Ĭ��ֵ
		Spinner2.setVisibility(View.VISIBLE);

		// ����ѡ������ArrayAdapter��������
		adapter3 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mList);

		// ���������б�ķ��
		adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// ��adapter ��ӵ�spinner��
		Spinner3.setAdapter(adapter3);

		// ����¼�Spinner�¼�����
		Spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@SuppressLint("ResourceAsColor")
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				String s = mList[arg2];
				if (s.equals("��ɫ")) {
					int color = getResources().getColor(R.color.white);
					view3.setBackgroundColor(color);
					AVB.setxia(color);
				} else if (s.equals("����ɫ")) {

					int color = getResources().getColor(R.color.ivory);
					view3.setBackgroundColor(color);
					AVB.setxia(color);
				} else if (s.equals("����ɫ")) {
					int color = getResources().getColor(R.color.lightyellow);
					view3.setBackgroundColor(color);
					AVB.setxia(color);

				} else if (s.equals("ѩ��ɫ")) {
					int color = getResources().getColor(R.color.snow);
					view3.setBackgroundColor(color);
					AVB.setxia(color);
					
				} else if (s.equals("�ۺ�ɫ")) {
					int color = getResources().getColor(R.color.pink);
					view3.setBackgroundColor(color);
					AVB.setxia(color);
					
				} else if (s.equals("����ɫ")) {
					int color = getResources().getColor(R.color.azure);
					view3.setBackgroundColor(color);
					AVB.setxia(color);
					
				} else if (s.equals("����ɫ")) {
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

		// ����Ĭ��ֵ
		Spinner3.setVisibility(View.VISIBLE);

		dialog.show();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Intent intent = new Intent();

			intent.setClass(more.this, SBSMainActivity.class);
			// ����Activity
			startActivity(intent);
			more.this.finish();
		}
		return false;
	}

}
