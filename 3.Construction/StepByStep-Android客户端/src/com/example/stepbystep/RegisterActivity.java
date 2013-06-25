package com.example.stepbystep;

import java.util.ArrayList;
import java.util.List;

import com.example.utility.DBUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	// 返回登录页面
	private Intent loginintent;
	// 省份选择控件
	Spinner spinner;
	// 必要信息
	private EditText appuserID;// 申请账号
	private EditText userPassword1;// 账号密码
	private EditText userPassword2;// 申请账号
	private EditText emailAdress;// 昵邮箱
	private Button submit;// 提交按钮

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		// 初始化控件
		initeViews();

	}

	private void initeViews() {

		appuserID = (EditText) findViewById(R.id.appuserID);
		userPassword1 = (EditText) findViewById(R.id.userPassword1);
		userPassword2 = (EditText) findViewById(R.id.userPassword2);
		emailAdress = (EditText) findViewById(R.id.useremail);

		submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				System.out.println("点击提交按钮");
				String userName = appuserID.getText().toString();
				String pw1 = userPassword1.getText().toString();
				String pw2 = userPassword1.getText().toString();
				String email = emailAdress.getText().toString();
				if (userName.equals(""))
					Toast.makeText(RegisterActivity.this, "请输入用户名",
							Toast.LENGTH_LONG).show();
				else if (pw1.equals("")) {
					Toast.makeText(RegisterActivity.this, "请输入密码",
							Toast.LENGTH_LONG).show();
				} else if (pw2.equals("")) {
					Toast.makeText(RegisterActivity.this, "请再次输入密码",
							Toast.LENGTH_LONG).show();
				} else if (!pw1.equals(pw2)) {
					Toast.makeText(RegisterActivity.this, "两次密码输入不一致，请重新输入",
							Toast.LENGTH_LONG).show();

				} else if (email.equals("")) {
					Toast.makeText(RegisterActivity.this, "请输入邮箱",
							Toast.LENGTH_LONG).show();

				} else {
					String ans = DBUtil.register(userName, pw1, email);
					Toast.makeText(RegisterActivity.this, ans,
							Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// 实例化intent
			loginintent = new Intent();
			// 设置跳转的界面
			loginintent.setClass(RegisterActivity.this, LoginActivity.class);
			// 启动Activity
			startActivity(loginintent);
			RegisterActivity.this.finish();

		}
		return false;
	}

}