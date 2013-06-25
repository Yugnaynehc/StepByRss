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

public class LookpersonActivity extends Activity {

	private TextView zhanghao;// 账号
	private TextView nicheng;// 昵称
	private TextView youxiang;// 邮箱
	private TextView shouji;// 手机
	private TextView aihao;// 爱好
	private TextView qianming;// 个人签名
	private Button xiugai;// 修改
	// 选择的当前用户的用户名
		private String usernameString;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_data);

		initeViews();
	}

	private void initeViews() {

		usernameString = AVB.getSelectUserName();
		
		nicheng = (TextView) findViewById(R.id.T2);
		nicheng.setText(DBUtil.getUserNickName(usernameString));
		youxiang = (TextView) findViewById(R.id.T3);
		youxiang.setText(DBUtil.getUserEmailAddress(usernameString));
		shouji = (TextView) findViewById(R.id.T4);
		shouji.setText(DBUtil.getUserTele(usernameString));
		aihao = (TextView) findViewById(R.id.T5);
		aihao.setText(DBUtil.getUserHobby(usernameString));
		qianming = (TextView) findViewById(R.id.T6);
		qianming.setText(DBUtil.getUserSignaure(usernameString));

		xiugai = (Button) findViewById(R.id.xiugai);
		if (AVB.getSelectUserName() != AVB.getUserName())
			xiugai.setVisibility(View.INVISIBLE);

		xiugai.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View e) {		
					xiugaijy();
			};

		});
	}

	private void xiugaijy() {
		Intent intent = new Intent();

		intent.setClass(LookpersonActivity.this, changepersonActivity.class);
		// 启动Activity
		startActivity(intent);
		LookpersonActivity.this.finish();
	}

}
