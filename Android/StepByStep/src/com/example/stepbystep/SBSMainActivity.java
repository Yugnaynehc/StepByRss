package com.example.stepbystep;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.layout.SlideImageLayout;
import com.example.parser.NewsXmlParser;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

@SuppressLint("ResourceAsColor")
public class SBSMainActivity extends ActivityGroup {


	// ��¼����
	private Intent loginintent;
	// ��ҳ
	private Intent Mainintent;
	// ����ת��ҳ��
	private Intent intent;
	// ���˵�
	private ImageButton publishandwanted;
	private ImageButton mainpage;
	private ImageButton attentionpeople;
	private ImageButton mymainpage;
	private ImageButton more;
	private LinearLayout xia = null;
	// ���þ���չʾ����
	private View EXMain = null;// չʾ����
	private LinearLayout lEXMain = null;
	private LayoutParams params = null;
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sbsmain);
		
		// ��ʼ���ؼ�
		initeViews();

	}

	private void initeViews() {
		

		// ���˵�����
		publishandwanted = (ImageButton) findViewById(R.id.publishandwanted);
		publishandwanted.setBackgroundResource(R.drawable.publishandwanted);
		mainpage = (ImageButton) findViewById(R.id.mainpage);
		mainpage.setBackgroundResource(R.drawable.mainpage);
		mymainpage = (ImageButton) findViewById(R.id.mymainpage);
		mymainpage.setBackgroundResource(R.drawable.mymainpage);
		attentionpeople = (ImageButton) findViewById(R.id.attentionpeople);
		attentionpeople.setBackgroundResource(R.drawable.attentionpeople);
		more = (ImageButton) findViewById(R.id.more);
		more.setBackgroundResource(R.drawable.more);
		more.setOnTouchListener(new OnTouchListener(){ 
	   		 
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					more.setBackgroundResource(R.drawable.more2);
				}
				if(event.getAction()==MotionEvent.ACTION_UP){ 
					more.setBackgroundResource(R.drawable.more);
					gengduo();
				}return false;
			}});
		// ���������Ͱ�ť����
		publishandwanted.setOnTouchListener(new OnTouchListener(){ 
	   		 
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					publishandwanted.setBackgroundResource(R.drawable.publishandwanted2);
				}
				if(event.getAction()==MotionEvent.ACTION_UP){ 
					publishandwanted.setBackgroundResource(R.drawable.publishandwanted);
					
					AVB.setType("publishandwanted");
					if (!AVB.getIsUser())
						login();
					else
						dialog();
				}return false;
			}});
		
		// ��ҳ��ť����
		mainpage.setOnTouchListener(new OnTouchListener(){ 
	   		 
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					mainpage.setBackgroundResource(R.drawable.mainpage2);
				}
				if(event.getAction()==MotionEvent.ACTION_UP){ 
					mainpage.setBackgroundResource(R.drawable.mainpage);
					
					intent = new Intent(SBSMainActivity.this, EXshow.class);
					EXMain = getLocalActivityManager().startActivity("TopicNews",
							intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
							.getDecorView();

					params = new LayoutParams(LayoutParams.FILL_PARENT,
							LayoutParams.FILL_PARENT);
					lEXMain = (LinearLayout) findViewById(R.id.main_layout);
					lEXMain.removeAllViews();
					lEXMain.addView(EXMain, params);
				}return false;
			}});
		
		// �ҵ���ҳ��ť����
		mymainpage.setOnTouchListener(new OnTouchListener(){ 
   		 
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					mymainpage.setBackgroundResource(R.drawable.mymainpage2);
				}
				if(event.getAction()==MotionEvent.ACTION_UP){ 
					mymainpage.setBackgroundResource(R.drawable.mymainpage);
					AVB.setType("mymainpage");
					if (!AVB.getIsUser())
						login();
					else {
						intent = new Intent(SBSMainActivity.this,
								MymainpageActivity.class);
						EXMain = getLocalActivityManager().startActivity(
								"MymainpageActivity",
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
								.getDecorView();

						params = new LayoutParams(LayoutParams.FILL_PARENT,
								LayoutParams.FILL_PARENT);
						lEXMain = (LinearLayout) findViewById(R.id.main_layout);
						lEXMain.removeAllViews();
						lEXMain.addView(EXMain, params);
					
						}
				}return false;
			}});
		
		// �ҹ�ע���˰�ť����
		attentionpeople.setOnTouchListener(new OnTouchListener(){ 
    		 
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					attentionpeople.setBackgroundResource(R.drawable.attentionpeople2);
				}
				if(event.getAction()==MotionEvent.ACTION_UP){ 
					attentionpeople.setBackgroundResource(R.drawable.attentionpeople);
					AVB.setType("attentionpeople");
					if (!AVB.getIsUser())
					{
						login();
						
					}
					else {
						intent = new Intent(SBSMainActivity.this,
								attentionpeopleActivity.class);
						EXMain = getLocalActivityManager().startActivity(
								"attentionpeopleActivity",
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
								.getDecorView();

						params = new LayoutParams(LayoutParams.FILL_PARENT,
								LayoutParams.FILL_PARENT);
						lEXMain = (LinearLayout) findViewById(R.id.main_layout);
						lEXMain.removeAllViews();
						lEXMain.addView(EXMain, params);
					}
					
				}
				return false;
			}});
		
		// ����չʾ����
		intent = new Intent(SBSMainActivity.this, EXshow.class);

		EXMain = getLocalActivityManager().startActivity("TopicNews", intent)
				.getDecorView();
		
		xia = (LinearLayout) findViewById(R.id.main_muen);
		xia.setBackgroundColor(AVB.getxia());


		params = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		lEXMain = (LinearLayout) findViewById(R.id.main_layout);
		lEXMain.setBackgroundColor(AVB.getzhong());
		lEXMain.addView(EXMain, params);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sbsmain, menu);
		return true;
	}

	// ���������;��鵯��
	protected void dialog() {
		Dialog dialog = new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.btn_star).setTitle("�û�����")
				.setPositiveButton("���;���", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// ʵ����intent
						intent = new Intent();
						// ������ת�Ľ���
						intent.setClass(SBSMainActivity.this,
								wantedActivity.class);
						// ����Activity
						startActivity(intent);
						SBSMainActivity.this.finish();
					}
				}).setNegativeButton("��������", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						// ʵ����intent
						intent = new Intent();
						// ������ת�Ľ���
						intent.setClass(SBSMainActivity.this,
								publishActivity.class);
						// ����Activity
						startActivity(intent);
						SBSMainActivity.this.finish();
					}
				}).setNeutralButton("����", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				}).create();

		dialog.show();

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			
			AlertDialog.Builder builder = new Builder(SBSMainActivity.this);
			builder.setMessage("ȷ���˳���");

			builder.setTitle("��ʾ");

			builder.setPositiveButton("ȷ��", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					if (AVB.getIsUser() == true) {
						DBUtil.exit(AVB.getUserName());
						AVB.setIsUser(false);
					}

					SBSMainActivity.this.finish();
				}
			});
			builder.setNegativeButton("ȡ��", new OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder.create().show();
		}
		return false;
	}

	// ��ת��¼ҳ��
	private void login() {
		loginintent = new Intent();
		// ������ת�Ľ���
		loginintent.setClass(SBSMainActivity.this, LoginActivity.class);
		// ����Activity
		startActivity(loginintent);
		SBSMainActivity.this.finish();
		overridePendingTransition(android.R.anim.slide_in_left,
				android.R.anim.slide_out_right);
	}
	private void gengduo()
	{
		Intent intent =new Intent();
		intent.setClass(SBSMainActivity.this, more.class);
		// ����Activity
		startActivity(intent);
		SBSMainActivity.this.finish();
		
	}
	
}
