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
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class message extends Activity {

	private ArrayList<String> userMessage = null;
	private TextView zhuti;// ����
	private EditText neirong;// ��������
	private Button fabu;// ������ť
	// ����ҳ��
	private ListView PLlist;// ������

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.messageboard);
		neirong = (EditText) findViewById(R.id.message);
		fabu = (Button) findViewById(R.id.fabu_btn);
		fabu.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Toast.makeText(message.this, "�����ύ����", Toast.LENGTH_SHORT)
						.show();
				DBUtil.addComment(AVB.getKnowledgeID(), neirong.getText()
						.toString(), AVB.getUserName());
				initeViews();
				Toast.makeText(message.this, "���۳ɹ�", Toast.LENGTH_SHORT).show();
			}
		});
		zhuti = (TextView) findViewById(R.id.show_EX);
		if (AVB.getKnowledgeID() != null) {
			zhuti.setText(DBUtil.getKnowledgeName(AVB.getKnowledgeID()));
			initeViews();
		}

	}

	private void initeViews() {
		userMessage = DBUtil.getComment(AVB.getKnowledgeID());
		// ��Layout�����ListView
		PLlist = (ListView) findViewById(R.id.PLList);
		// ���ɶ�̬���飬��������
		final ArrayList<HashMap<String, String>> PLlistItem = new ArrayList<HashMap<String, String>>();
		for (int i = userMessage.size() - 1; i >= 0; i = i - 3) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("PL_User", userMessage.get(i - 2));
			map.put("PL_Time", userMessage.get(i - 1));
			map.put("PL_Info", userMessage.get(i));
			PLlistItem.add(map);
		}
		// ������������Item�Ͷ�̬�����Ӧ��Ԫ��
		SimpleAdapter PLlistItemAdapter = new SimpleAdapter(this, PLlistItem,// ����Դ
				R.layout.pl,// ListItem��XMLʵ��
				// ��̬������EXItem��Ӧ������
				new String[] { "PL_User", "PL_Time", "PL_Info" },
				// EXItem��XML�ļ����������TextView ID
				new int[] { R.id.PL_User, R.id.PL_Time, R.id.PL_Info });

		// ��Ӳ�����ʾ
		PLlist.setAdapter(PLlistItemAdapter);

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// ʵ����intent
			Intent intent = new Intent();
			// ������ת�Ľ���
			intent.setClass(message.this, LookActivity.class);
			// ����Activity
			startActivity(intent);
			message.this.finish();

		}
		return false;
	}

}
