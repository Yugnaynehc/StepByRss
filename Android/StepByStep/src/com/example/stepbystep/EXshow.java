package com.example.stepbystep;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.stepbystep.R;
import com.example.utility.DBUtil;
import com.example.layout.SlideImageLayout;
import com.example.parser.NewsXmlParser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class EXshow extends Activity {

	private EditText search = null; // ������
	private ImageButton ObjectButton;// ���������������
	private ImageButton search_btn;// ������ť
	private LinearLayout SearchLayout = null; 
	// ���а�
	private ImageButton zuire;// ���Ⱦ���
	private ImageButton zuixin;// ���¾���
	private ImageButton zuizan;// ���޾���
	private ImageButton xuans;// ��������
	// ����չʾ
	private ListView EXlist;// �����б�
	private ArrayList<String> knowledge = null;// ��������

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exshow);
		// ��ʼ��
		initeViews();
	}

	/**
	 * ��ʼ��
	 */
	private void initeViews() {
		SearchLayout = (LinearLayout) findViewById(R.id.SearchLayout);
		SearchLayout.setBackgroundColor(AVB.getshang());

		// ����
		zuire = (ImageButton) findViewById(R.id.I1);
		zuire.setBackgroundResource(R.drawable.hot);
		zuire.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(EXshow.this, "�����ύ��������", Toast.LENGTH_SHORT)
						.show();
				knowledge = DBUtil.getKnowledgeSortByHot();
				if (knowledge.size() != 0) {
					showex();
					Toast.makeText(EXshow.this, "�������", Toast.LENGTH_SHORT)
							.show();
				} else
					Toast.makeText(EXshow.this, "û�����������ʵĽ��", Toast.LENGTH_LONG)
							.show();
			}
		});
		zuixin = (ImageButton) findViewById(R.id.I2);
		zuixin.setBackgroundResource(R.drawable.newha);
		zuixin.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(EXshow.this, "�����ύ��������", Toast.LENGTH_SHORT)
						.show();
				knowledge = DBUtil.getKnowledgeSortByID();
				if (knowledge.size() != 0) {
					showex();
					Toast.makeText(EXshow.this, "�������", Toast.LENGTH_SHORT)
							.show();
				} else
					Toast.makeText(EXshow.this, "û�����������ʵĽ��",
							Toast.LENGTH_SHORT).show();
			}
		});
		zuizan = (ImageButton) findViewById(R.id.I3);
		zuizan.setBackgroundResource(R.drawable.awesome);
		zuizan.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(EXshow.this, "�����ύ��������", Toast.LENGTH_SHORT)
						.show();
				knowledge = DBUtil.getKnowledgeSortByZan();
				if (knowledge.size() != 0) {
					showex();
					Toast.makeText(EXshow.this, "�������", Toast.LENGTH_SHORT)
							.show();
				} else
					Toast.makeText(EXshow.this, "û�����������ʵĽ��",
							Toast.LENGTH_SHORT).show();
			}
		});
		xuans = (ImageButton) findViewById(R.id.I4);
		xuans.setBackgroundResource(R.drawable.want);
		xuans.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(EXshow.this, "�����ύ��������", Toast.LENGTH_SHORT)
						.show();
				knowledge = DBUtil.getKnowledgeSortByWanted();
				if (knowledge.size() != 0) {
					showwant();
					Toast.makeText(EXshow.this, "�������", Toast.LENGTH_SHORT)
							.show();
				} else
					Toast.makeText(EXshow.this, "û�����������ʵĽ��",
							Toast.LENGTH_SHORT).show();
			}
		});
		// ����������
		search = (EditText) findViewById(R.id.SearchText);
		ObjectButton = (ImageButton) findViewById(R.id.ObjectButton);
		ObjectButton.setTag("EX");
		ObjectButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {

				if (ObjectButton.getTag() == "EX") {
					ObjectButton.setTag("PER");
					ObjectButton.setBackgroundResource(R.drawable.peop);
				} else {
					if (!AVB.getIsUser()) {
						Toast.makeText(EXshow.this, "���ȵ�¼", Toast.LENGTH_SHORT)
								.show();
					} else {
						ObjectButton.setTag("EX");
						ObjectButton.setBackgroundResource(R.drawable.know);
					}
				}
			}
		});
		search_btn = (ImageButton) findViewById(R.id.SearchButton);
		search_btn.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					search_btn.setBackgroundResource(R.drawable.search2);
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					search_btn.setBackgroundResource(R.drawable.search);

					if (ObjectButton.getTag() == "EX") {
						Toast.makeText(EXshow.this, "�����ύ��������",
								Toast.LENGTH_SHORT).show();
						knowledge = DBUtil.searchKnowledge(search.getText()
								.toString());
						if (knowledge.size() != 0) {
							showex();
							Toast.makeText(EXshow.this, "�������",
									Toast.LENGTH_SHORT).show();
						} else
							Toast.makeText(EXshow.this, "û�����������ʵĽ��",
									Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(EXshow.this, "�����ύ��������",
								Toast.LENGTH_SHORT).show();
						knowledge = DBUtil.searchUser(search.getText()
								.toString());
						if (knowledge.size() != 0) {
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
		knowledge = DBUtil.getKnowledgeSortByID();
		if (knowledge.size() != 0)
			showex();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			AlertDialog.Builder builder = new Builder(EXshow.this);
			builder.setMessage("ȷ���˳���");

			builder.setTitle("��ʾ");

			builder.setPositiveButton("ȷ��", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					if (AVB.getIsUser() == true) {
						DBUtil.exit(AVB.getUserName());
						AVB.setIsUser(false);
					}

					EXshow.this.finish();
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

	// ��������ҳ��
	private void showex() {
		EXlist = (ListView) findViewById(R.id.EXList2);

		// ���ɶ�̬���飬��������
		final ArrayList<HashMap<String, String>> EXlistItem = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < knowledge.size(); i = i + 4) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("EX_Title", knowledge.get(i));// ͼ����Դ��ID
			map.put("EX_Man", knowledge.get(i + 1));
			map.put("EX_ID", knowledge.get(i + 2));
			map.put("EX_Time", knowledge.get(i + 3));

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
				intent.setClass(EXshow.this, LookActivity.class);
				// ����Activity
				startActivity(intent);
				EXshow.this.finish();
			}
		});
	}

	// �����û�ҳ��
	private void showpeo() {
		EXlist = (ListView) findViewById(R.id.EXList2);

		// ���ɶ�̬���飬��������
		final ArrayList<HashMap<String, String>> EXlistItem = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < knowledge.size(); ++i) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("user_ID", knowledge.get(i));// �û�ID
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
				intent.setClass(EXshow.this, TAmainpage.class);
				// ����Activity
				startActivity(intent);
				EXshow.this.finish();
			}
		});

	}

	// ��������ҳ��
	private void showwant() {

	}
}
