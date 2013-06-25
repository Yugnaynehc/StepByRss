package com.example.stepbystep;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.utility.DBUtil;

import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.view.LayoutInflater;
import android.view.View.OnTouchListener;

public class attentionpeopleActivity extends Activity {

	// �ҹ�ע����ҳ��
	private ViewGroup main = null;

	// ������
	private EditText SearchText;// ������
	private ImageButton SearchButton;// ������ť

	// ��עչʾ
	private ListView EXlist;// ��ע�б�
	private ArrayList<String> attention = null;// ��ע����

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.attentionpeople);
		// ��ʼ���ؼ�
		initeViews();

	}

	private void initeViews() {

		EXlist = (ListView) findViewById(R.id.EXList3);
		attention = DBUtil.getAttentionList(AVB.getUserName());
		// ���ɶ�̬���飬��������
		final ArrayList<HashMap<String, String>> EXlistItem = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < attention.size(); ++i) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("user_ID", attention.get(i));// �û�ID
			EXlistItem.add(map);
		}
		// ������������Item�Ͷ�̬�����Ӧ��Ԫ��
		SimpleAdapter EXlistItemAdapter = new SimpleAdapter(this, EXlistItem,// ����Դ
				R.layout.peo,// ListItem��XMLʵ��
				// ��̬������EXItem��Ӧ������
				new String[] { "user_ID" },
				// EXItem��XML�ļ����������TextView ID
				new int[] { R.id.user_ID });

		// ��Ӳ�����ʾ
		EXlist.setAdapter(EXlistItemAdapter);

		// ��ӵ��
		EXlist.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// ʵ����intent
				Intent intent = new Intent();
				AVB.setSelectUserName((EXlistItem.get(arg2)).get("user_ID")
						.toString());
				intent.setClass(attentionpeopleActivity.this, TAmainpage.class);
				// ����Activity
				startActivity(intent);
				attentionpeopleActivity.this.finish();
			}
		});

		SearchButton = (ImageButton) findViewById(R.id.SearchButton2);
		SearchText = (EditText) findViewById(R.id.SearchName);
		
		SearchButton = (ImageButton) findViewById(R.id.SearchButton);
		/*
		SearchButton.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					SearchButton.setBackgroundResource(R.drawable.search2);
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					SearchButton.setBackgroundResource(R.drawable.search);

					if (ObjectButton.getTag() == "EX") {
						Toast.makeText(EXshow.this, "�����ύ��������",
								Toast.LENGTH_SHORT).show();
						attention = DBUtil.searchKnowledge(search.getText()
								.toString());
						if (attention.size() != 0) {
							showex();
							Toast.makeText(EXshow.this, "�������",
									Toast.LENGTH_SHORT).show();
						} else
							Toast.makeText(EXshow.this, "û�����������ʵĽ��",
									Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(EXshow.this, "�����ύ��������",
								Toast.LENGTH_SHORT).show();
						attention = DBUtil.searchUser(search.getText()
								.toString());
						if (attention.size() != 0) {
							showpeo();
							Toast.makeText(EXshow.this, "�������",
									Toast.LENGTH_SHORT).show();
						} else
							Toast.makeText(EXshow.this, "û�����������ʵĽ��",
									Toast.LENGTH_LONG).show();
					}

				}
				return false;
			}

		});
		*/

	}

}