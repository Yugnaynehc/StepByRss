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
import android.graphics.BitmapFactory.Options;
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

public class EditEXActivity extends Activity {

	// ������ҳ
	private Intent mainintent;
	// ��������ҳ��
	private EditText themeName;// ������
	private EditText needtools;// ���蹤��
	private Button publish_btn;// ������ť
	public String ID = "-1";// ����ID
	private ImageView xiugai;
	// �ϴ�ͼƬ
	private Button addpicture;// ���ͼƬ��ť
	private static int RESULT_LOAD_IMAGE = 1;
	private ImageView picture;// ��ӵ�ͼƬ
	private Bitmap bit;// ��blankת��Ϊλͼ
	private BitmapDrawable BtoD;// Bitmap to Drawable
	public BitmapDrawable blank;// �հ�ͼƬ
	private static boolean isChanged = false;
	// ���鲽��
	private String temp;// �Ѳ���ת��ΪString
	private int count;// ���������
	private int max;// ��ǰ�ѱ༭�Ĳ�����
	private ImageButton next;// ��һ��
	private ImageButton last;// ��һ��
	private TextView content;// ��ǰ����
	private EditText EXcontent;// ����༭��
	private List<String> EXlist = new ArrayList<String>();// �����ŵ�����
	private List<String> Templist = new ArrayList<String>(); // ��ʱ���飬���ھ���ͼƬ�Ļ�ȡ
	private List<Bitmap> PIClist = new ArrayList<Bitmap>();// ����ͼƬ������

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.publish);

		Templist = AVB.getKnowledgeDetail();

		for (int i = 0; i < Templist.size(); ++i)
			EXlist.add(Templist.get(i));

		max = EXlist.size() / 2;// ��������

		Templist = AVB.getKnowledgeImage();
		for (int i = 0; i < max; ++i) {
			PIClist.add(null);
		}
		for (int i = 0; i < Templist.size() / 3; ++i) {
			PIClist.set(Integer.parseInt(Templist.get(3 * i + 1)),
					DBUtil.stringtoBitmap(Templist.get(3 * i + 2)));
		}

		for (int i = max * 2 - 2; i >= 0; i = i - 2)
			EXlist.remove(i);

		// ��ʼ���ؼ�
		initeViews();
	}

	private void initeViews() {

		xiugai = (ImageView) findViewById(R.id.fabuIMG);
		xiugai.setBackgroundResource(R.drawable.xiugai);

		themeName = (EditText) findViewById(R.id.themeName);
		themeName.setText(DBUtil.getKnowledgeName(AVB.getKnowledgeID()));
		themeName.setFocusable(false);
		needtools = (EditText) findViewById(R.id.EX_needtools);
		needtools.setText(DBUtil.getNeedTools(AVB.getKnowledgeID()));
		picture = (ImageView) findViewById(R.id.picture);

		publish_btn = (Button) findViewById(R.id.publish_btn);
		publish_btn.setText("��    ��");
		publish_btn.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					publish_btn.setBackgroundColor(0xff663388);
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					publish_btn.setBackgroundColor(0xff6699cc);

					if (themeName.getText().toString().equals("")
							|| EXlist.get(0) == null)
						Toast.makeText(EditEXActivity.this, "����û�༭������Ҫ��",
								Toast.LENGTH_LONG).show();
					else
						submit();

				}
				return false;

			}
		});

		addpicture = (Button) findViewById(R.id.addpicture);
		// ���ͼƬ��ť����
		addpicture.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {

				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, RESULT_LOAD_IMAGE);

			}
		});

		// ͨ��openRawResource��ȡһ��inputStream����
		InputStream inputStream = getResources().openRawResource(
				R.drawable.blank);
		// ͨ��һ��InputStream����һ��BitmapDrawable����
		blank = new BitmapDrawable(inputStream);
		EXcontent = (EditText) findViewById(R.id.EXcontent);
		content = (TextView) findViewById(R.id.content);
		// ���鲽��༭��
		count = 0;// ��������0
		EXcontent.setText(EXlist.get(count));
		content.setText("��Ҫ");
		bit = PIClist.get(count);
		if (bit != null) {
			BtoD = new BitmapDrawable(bit);
			picture.setBackgroundDrawable(BtoD);
		}

		next = (ImageButton) findViewById(R.id.next);
		next.setBackgroundResource(R.drawable.next);
		next.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					next.setBackgroundResource(R.drawable.next2);
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					next.setBackgroundResource(R.drawable.next);
					next();// ִ����һ������
				}
				return false;
			}

		});
		last = (ImageButton) findViewById(R.id.last);
		last.setBackgroundResource(R.drawable.last);
		last.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					last.setBackgroundResource(R.drawable.last2);
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					last.setBackgroundResource(R.drawable.last);
					last();// ִ����һ������
				}
				return false;
			}

		});

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// ʵ����intent
			mainintent = new Intent();
			// ������ת�Ľ���
			mainintent.setClass(EditEXActivity.this, SBSMainActivity.class);
			// ����Activity
			startActivity(mainintent);
			EditEXActivity.this.finish();

		}
		return false;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();

			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			bit = BitmapFactory.decodeFile(picturePath, opts);

			opts.inSampleSize = 4;
			opts.inPreferredConfig = Bitmap.Config.ARGB_4444; // Ĭ����Bitmap.Config.ARGB_8888
			/* ���������ֶ���Ҫ���ʹ�� */
			opts.inPurgeable = true;
			opts.inInputShareable = true;
			/* �������������ķ���һ��Bitmap */

			opts.inJustDecodeBounds = false;
			bit = BitmapFactory.decodeFile(picturePath, opts);

			BtoD = new BitmapDrawable(bit);
			picture.setBackgroundDrawable(BtoD);

			isChanged = true;// �Ѿ�����

		}

	}

	// ��һ������
	private void next() {
		if (EXcontent.getText().toString().equals("")) {
			Toast.makeText(EditEXActivity.this, "����û�༭��ǰ���鲽�裡",
					Toast.LENGTH_LONG).show();
		} else {
			BtoD = new BitmapDrawable(bit);
			count++;
			if (max == count) {

				// ���޸ĺ󣬱��浱ǰ��
				EXlist.set(count - 1, EXcontent.getText().toString());
				if (isChanged) {
					PIClist.set(count - 1, bit);
					isChanged = false;
				}

				picture.setBackgroundResource(R.drawable.blank);
				bit = blank.getBitmap();

				EXcontent.setText("");
				EXcontent.setHint("");

			} else if (max > count)// �жϵ�ǰ�����Ƿ�<��ǰ��������
			{
				// ���޸ĺ󣬱��浱ǰ��
				EXlist.set(count - 1, EXcontent.getText().toString());
				if (isChanged) {
					PIClist.set(count - 1, bit);
					isChanged = false;
				}
				// ��ʾ��ǰ����
				EXcontent.setText(EXlist.get(count));

				if (PIClist.get(count) != null) {
					BtoD = new BitmapDrawable(PIClist.get(count));
					picture.setBackgroundDrawable(BtoD);
				} else
					picture.setBackgroundResource(R.drawable.blank);

			} else {

				// ����һ��������
				EXlist.add(EXcontent.getText().toString());

				if (isChanged) {
					PIClist.add(bit);
					isChanged = false;
				} else
					PIClist.add(null);

				bit = blank.getBitmap();
				picture.setBackgroundResource(R.drawable.blank);

				EXcontent.setText("");
				EXcontent.setHint("");

				max = count;
			}

			temp = String.valueOf(count);
			content.setText("��" + temp + "��");
		}
	}

	// ��һ������
	private void last() {
		if (count == 0) {
			Toast.makeText(EditEXActivity.this, "���������ˣ�", Toast.LENGTH_LONG)
					.show();
		} else {

			// ���޸ĺ󣬱��浱ǰ��
			if (max == count) {
				if (!EXcontent.getText().toString().equals("")) {
					// ����һ��������
					EXlist.add(EXcontent.getText().toString());

					if (isChanged) {
						PIClist.add(bit);
						isChanged = false;
					} else
						PIClist.add(null);
					max = count + 1;
				}

			} else if (max > count) {
				EXlist.set(count, EXcontent.getText().toString());
				if (isChanged) {
					PIClist.set(count, bit);
					isChanged = false;
				}
			}
			// ��ʾ��һ��
			count--;
			if (PIClist.get(count) != null) {
				BtoD = new BitmapDrawable(PIClist.get(count));
				picture.setBackgroundDrawable(BtoD);
			} else
				picture.setBackgroundResource(R.drawable.blank);

			EXcontent.setText(EXlist.get(count));// ��ʾ��һ���ı༭����

			temp = String.valueOf(count);
			if (count == 0) {
				content.setText("��Ҫ");
			} else
				content.setText("��" + temp + "��");
		}
	}

	// �ύ��ť����
	private void submit() {
		// publish_btn.setClickable(false);
		Toast.makeText(EditEXActivity.this, "�����ύ����", Toast.LENGTH_LONG).show();
		String sbsname = themeName.getText().toString();
		String sbsDescrible = EXlist.get(0);
		String sbstool = needtools.getText().toString();
		Bitmap image = null;
		ArrayList<String> ls = null;
		ID = AVB.getKnowledgeID();
		if (!ID.equals("-1")) {
			Toast.makeText(EditEXActivity.this, "�ύ��", Toast.LENGTH_LONG)
					.show();
			DBUtil.setKnowledgeOutline(ID, sbsname, sbsDescrible, sbstool);
			for (int i = 0; i < EXlist.size(); ++i) {
				DBUtil.setKnowledgeDetail(ID, i + "", EXlist.get(i));
				image = PIClist.get(i);
				if (image != null) {
					ls = DBUtil.bitmaptoString(image);
					DBUtil.setKnowledgePicture(ID, ls.get(0), i + "", ls.get(1));
				}
			}

			Toast.makeText(EditEXActivity.this, "�ύ�ɹ���", Toast.LENGTH_SHORT)
					.show();
		} else {
			Toast.makeText(EditEXActivity.this, "�ύʧ�ܣ�", Toast.LENGTH_SHORT)
					.show();
		}
	}

	private void setcontent() {

	}

	private void answerwant() {

	}
}
