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

	private EditText zhanghao;//账号
	private EditText nicheng;//昵称
	private EditText youxiang;//邮箱
	private EditText shouji;//手机
	private EditText aihao;//爱好
	private EditText qianming;//个人签名
	private Button queding;//确定
	private Button change;//更改头像
	private ImageView pic;//头像
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_person_data);
		
		
		initeViews();
	}

	private void initeViews() {
		
		zhanghao = (EditText)findViewById(R.id.T11);
		nicheng = (EditText)findViewById(R.id.T22);
		youxiang = (EditText)findViewById(R.id.T33);
		shouji = (EditText)findViewById(R.id.T44);
		aihao = (EditText)findViewById(R.id.T55);
		qianming = (EditText)findViewById(R.id.T66);
		change = (Button)findViewById(R.id.changepic);
		pic = (ImageView)findViewById(R.id.pic);
		
		queding = (Button)findViewById(R.id.queding);	

		queding.setOnClickListener(new Button.OnClickListener()
				{    
			public void onClick(View e){			
					queding();
				};
			
				}
				);
	}

	private void queding()
	{
		Intent intent = new Intent ();
		
		intent.setClass(changepersonActivity.this, SBSMainActivity.class);
		// 启动Activity
		startActivity(intent); 
		changepersonActivity.this.finish();
	}

}
