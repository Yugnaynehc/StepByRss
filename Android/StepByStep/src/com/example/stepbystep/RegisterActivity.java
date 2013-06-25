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

	// ���ص�¼ҳ��
	private Intent loginintent;
	// ʡ��ѡ��ؼ�
	Spinner spinner;
	// ��Ҫ��Ϣ
	private EditText appuserID;// �����˺�
	private EditText userPassword1;// �˺�����
	private EditText userPassword2;// �����˺�
	private EditText emailAdress;// ������
	private Button submit;// �ύ��ť

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		// ��ʼ���ؼ�
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
				System.out.println("����ύ��ť");
				String userName = appuserID.getText().toString();
				String pw1 = userPassword1.getText().toString();
				String pw2 = userPassword1.getText().toString();
				String email = emailAdress.getText().toString();
				if (userName.equals(""))
					Toast.makeText(RegisterActivity.this, "�������û���",
							Toast.LENGTH_LONG).show();
				else if (pw1.equals("")) {
					Toast.makeText(RegisterActivity.this, "����������",
							Toast.LENGTH_LONG).show();
				} else if (pw2.equals("")) {
					Toast.makeText(RegisterActivity.this, "���ٴ���������",
							Toast.LENGTH_LONG).show();
				} else if (!pw1.equals(pw2)) {
					Toast.makeText(RegisterActivity.this, "�����������벻һ�£�����������",
							Toast.LENGTH_LONG).show();

				} else if (email.equals("")) {
					Toast.makeText(RegisterActivity.this, "����������",
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
			// ʵ����intent
			loginintent = new Intent();
			// ������ת�Ľ���
			loginintent.setClass(RegisterActivity.this, LoginActivity.class);
			// ����Activity
			startActivity(loginintent);
			RegisterActivity.this.finish();

		}
		return false;
	}

}