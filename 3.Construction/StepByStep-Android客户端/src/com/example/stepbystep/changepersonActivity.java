package com.example.stepbystep;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.utility.DBUtil;
import com.example.utility.DimensionUtility;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class changepersonActivity extends Activity {
	private String userName;//�û���
	private EditText zhanghao;// �˺�
	private EditText nicheng;// �ǳ�
	private EditText youxiang;// ����
	private EditText shouji;// �ֻ�
	private EditText aihao;// ����
	private EditText qianming;// ����ǩ��
	private Button queding;// ȷ��
	private Button change;// ����ͷ��
	private ImageView pic;// ͷ��

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_person_data);

		initeViews();
	}

	private void initeViews() {
		userName = AVB.getUserName();
		nicheng = (EditText) findViewById(R.id.T22);
		nicheng.setText(DBUtil.getUserNickName(userName));
		youxiang = (EditText) findViewById(R.id.T33);
		youxiang.setText(DBUtil.getUserEmailAddress(userName));
		shouji = (EditText) findViewById(R.id.T44);
		shouji.setText(DBUtil.getUserTele(userName));
		aihao = (EditText) findViewById(R.id.T55);
		aihao.setText(DBUtil.getUserHobby(userName));
		qianming = (EditText) findViewById(R.id.T66);
		qianming.setText(DBUtil.getUserSignaure(userName));
		change = (Button) findViewById(R.id.changepic);
		pic = (ImageView) findViewById(R.id.pic);
		queding = (Button) findViewById(R.id.queding);

		queding.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View e) {
				queding();
			};

		});
	}

	private void queding() {
		Intent intent = new Intent();
		DBUtil.setUserNickName(userName, nicheng.getText().toString());
		DBUtil.setUserEmailAddress(userName, youxiang.getText().toString());
		DBUtil.setUserTele(userName, shouji.getText().toString());
		DBUtil.setUserHobby(userName, aihao.getText().toString());
		DBUtil.setUserSignaure(userName, qianming.getText().toString());
		intent.setClass(changepersonActivity.this, SBSMainActivity.class);
		// ����Activity
		startActivity(intent);
		changepersonActivity.this.finish();
	}

}
