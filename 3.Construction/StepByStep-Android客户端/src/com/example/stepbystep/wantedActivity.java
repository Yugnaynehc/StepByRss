package com.example.stepbystep;

import com.example.utility.DBUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class wantedActivity extends Activity{
	
    //返回主页
	private Intent mainintent;	
	//悬赏经验页面
	private EditText themeName;//主题名
	private EditText EXcontent;//概要描述
	private EditText wantedEX_num;//悬赏EX数目
	private Button want_btn;//悬赏按钮
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wanted);
		
		// 初始化控件
        initeViews();
	}
	private void initeViews()
	{
		
		themeName = (EditText)findViewById(R.id.themeName);
		EXcontent = (EditText)findViewById(R.id.EXcontent);
		wantedEX_num = (EditText)findViewById(R.id.wantedEX_num);
		want_btn = (Button)findViewById(R.id.want_btn);
		//悬赏提交按钮功能
		want_btn.setOnClickListener(new Button.OnClickListener(){  
			    	public void onClick(View v) { 
			    		Toast.makeText(wantedActivity.this, "正在提交悬赏请求", Toast.LENGTH_SHORT)
						.show();
			    		String username, sbsname, mark, result;
			    		username = AVB.getUserName();
			    		sbsname = themeName.getText().toString();
			    		mark = wantedEX_num.getText().toString();
			    		result = DBUtil.addWanted(username, sbsname, mark);
			    		Toast.makeText(wantedActivity.this, result, Toast.LENGTH_SHORT)
						.show();
			    	}
			    });
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		  if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			//实例化intent
	    		mainintent =new Intent();
	    		//设置跳转的界面
	    		mainintent.setClass(wantedActivity.this,SBSMainActivity.class);
	    		//启动Activity
	    		startActivity(mainintent);
	    		wantedActivity.this.finish();
	    			  
		  }
		  return false;
		 }

}