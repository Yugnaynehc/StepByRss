package com.example.stepbystep;

import java.util.ArrayList;

import com.example.utility.DBUtil;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	
	// ���ص�������Ӧ��ҳ��
	private Intent intent;
	// ��¼ҳ��
	private Button login;// ��¼��ť
	private EditText userID;
	private EditText userPassword;
	// ע��ҳ��
	private Button register;// ע�ᰴť
	private Intent registerintent;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		// ��ʼ���ؼ�
		initeViews();

	}

	private void initeViews() {
		register = (Button)findViewById(R.id.register);
		register.setOnTouchListener(new OnTouchListener(){ 
    		 
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					register.setBackgroundColor(0xff99ccff);
				}
				if(event.getAction()==MotionEvent.ACTION_UP){ 
					register.setBackgroundColor(0xff6699cc);
					// ʵ����intent
					registerintent = new Intent();
					// ������ת�Ľ���
					registerintent.setClass(LoginActivity.this,
							RegisterActivity.class);
					// ����Activity
					startActivity(registerintent);
					LoginActivity.this.finish();
					overridePendingTransition(android.R.anim.slide_in_left,
							android.R.anim.slide_out_right);
				}
				return false;
			}
    		
    	}
    	);
		

		userID = (EditText) findViewById(R.id.userID);
		userPassword = (EditText) findViewById(R.id.userPassword);
		userID.setText("test");
		userPassword.setText("test123");

		login = (Button)findViewById(R.id.login);
		login.setOnTouchListener(new OnTouchListener(){ 
    		 
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					login.setBackgroundColor(0xffFF6600);
				}
				if(event.getAction()==MotionEvent.ACTION_UP){ 
					login.setBackgroundColor(0xffFF0033);
					
					login();
				
			}
				return false;
    		
    	}}
    	);
		
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// ʵ����intent
			intent = new Intent();
			// ������ת�Ľ���
			intent.setClass(LoginActivity.this, SBSMainActivity.class);
			// ����Activity
			startActivity(intent);
			LoginActivity.this.finish();

		}
		return false;
	}
	//��¼�ɹ�����ת��ԭ��ҳ��
	private void jumpto()
	{
		intent = new Intent(); 
		String Type = AVB.getType();
		if(Type.equals("publishandwanted"))
		{
			intent.setClass(LoginActivity.this, publishActivity.class);
		}
		else if(Type.equals("mymainpage"))
		{
			intent.setClass(LoginActivity.this, SBSMainActivity.class);
		}
		else if(Type.equals("attentionpeople"))
		{
			intent.setClass(LoginActivity.this, SBSMainActivity.class);
		}
		else if(Type.equals("lookactivity"))
		{
			intent.setClass(LoginActivity.this, LookActivity.class);
		}
		startActivity(intent);
		LoginActivity.this.finish();
	}
	//��¼����
	private void login()
	{
		String userName = userID.getText().toString();
		String pw = userPassword.getText().toString();
		if (userName.equals(""))
			Toast.makeText(LoginActivity.this, "�������û���",
					Toast.LENGTH_LONG).show();
		else if (pw.equals(""))
			Toast.makeText(LoginActivity.this, "����������",
					Toast.LENGTH_LONG).show();
		else {
			
			String ans = DBUtil.login(userName, pw);
			
			if(ans.equals("true"))
			{
				AVB.setIsUser(true);
				AVB.setUserName(userName);
				Toast.makeText(LoginActivity.this, "��¼�ɹ�",
						Toast.LENGTH_LONG).show();
				jumpto();
			}
			else
				Toast.makeText(LoginActivity.this, ans,
						Toast.LENGTH_LONG).show();
	}
	}
}