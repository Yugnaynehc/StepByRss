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

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class myexshow extends Activity {

	private ArrayList<String> userKnowledge = null;// �û���������
	private int count; // �û���������
	// �ҵľ���ҳ��
	private ListView EXlist;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myexshow);
		userKnowledge = DBUtil.getUserKnowledge(AVB.getSelectUserName());
		count = userKnowledge.size() / 3;
		System.out.println(userKnowledge);

		initeViews();
	}

	private void initeViews() {

		// ��Layout�����ListView
		EXlist = (ListView) findViewById(R.id.EXList);
		// ���ɶ�̬���飬��������
		final ArrayList<HashMap<String, String>> EXlistItem = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < userKnowledge.size(); i = i + 3) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("EX_Title", userKnowledge.get(i));// ͼ����Դ��ID
			map.put("EX_ID", userKnowledge.get(i + 1));
			map.put("EX_Time", userKnowledge.get(i + 2));
			map.put("EX_Man", AVB.getUserName());
			EXlistItem.add(map);
		}
		// ������������Item�Ͷ�̬�����Ӧ��Ԫ��
		SimpleAdapter EXlistItemAdapter = new SimpleAdapter(this, EXlistItem,// ����Դ
				R.layout.ex,// ListItem��XMLʵ��
				// ��̬������EXItem��Ӧ������
				new String[] { "EX_Title", "EX_Time", "EX_Man" },
				// EXItem��XML�ļ����������TextView ID
				new int[] { R.id.EX_Title, R.id.EX_Time, R.id.EX_Man });

		// ��Ӳ�����ʾ
		EXlist.setAdapter(EXlistItemAdapter);

		// ��ӵ��
		EXlist.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// ʵ����intent
				Intent intent = new Intent();
				// ������ת�Ľ���
				AVB.setKnowledgeID((EXlistItem.get(arg2)).get("EX_ID")
						.toString());
				DBUtil.addHit(AVB.getKnowledgeID());
				intent.setClass(myexshow.this, LookActivity.class);
				// ����Activity
				startActivity(intent);
				myexshow.this.finish();
			}
		});
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// ʵ����intent
			Intent intent = new Intent();
			// ������ת�Ľ���
			intent.setClass(myexshow.this, SBSMainActivity.class);
			// ����Activity
			startActivity(intent);
			myexshow.this.finish();

		}
		return false;
	}
}
