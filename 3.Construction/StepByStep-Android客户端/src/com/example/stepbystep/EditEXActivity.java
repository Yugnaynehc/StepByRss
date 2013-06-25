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

	// 返回主页
	private Intent mainintent;
	// 发布经验页面
	private EditText themeName;// 主题名
	private EditText needtools;// 所需工具
	private Button publish_btn;// 发布按钮
	public String ID = "-1";// 经验ID
	private ImageView xiugai;
	// 上传图片
	private Button addpicture;// 添加图片按钮
	private static int RESULT_LOAD_IMAGE = 1;
	private ImageView picture;// 添加的图片
	private Bitmap bit;// 把blank转化为位图
	private BitmapDrawable BtoD;// Bitmap to Drawable
	public BitmapDrawable blank;// 空白图片
	private static boolean isChanged = false;
	// 经验步骤
	private String temp;// 把步骤转化为String
	private int count;// 步骤计数器
	private int max;// 当前已编辑的步骤数
	private ImageButton next;// 下一步
	private ImageButton last;// 上一步
	private TextView content;// 当前步骤
	private EditText EXcontent;// 经验编辑栏
	private List<String> EXlist = new ArrayList<String>();// 经验存放的数组
	private List<String> Templist = new ArrayList<String>(); // 临时数组，用于经验图片的获取
	private List<Bitmap> PIClist = new ArrayList<Bitmap>();// 经验图片的数组

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.publish);

		Templist = AVB.getKnowledgeDetail();

		for (int i = 0; i < Templist.size(); ++i)
			EXlist.add(Templist.get(i));

		max = EXlist.size() / 2;// 步骤总数

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

		// 初始化控件
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
		publish_btn.setText("修    改");
		publish_btn.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					publish_btn.setBackgroundColor(0xff663388);
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					publish_btn.setBackgroundColor(0xff6699cc);

					if (themeName.getText().toString().equals("")
							|| EXlist.get(0) == null)
						Toast.makeText(EditEXActivity.this, "您还没编辑主题或概要！",
								Toast.LENGTH_LONG).show();
					else
						submit();

				}
				return false;

			}
		});

		addpicture = (Button) findViewById(R.id.addpicture);
		// 添加图片按钮功能
		addpicture.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {

				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, RESULT_LOAD_IMAGE);

			}
		});

		// 通过openRawResource获取一个inputStream对象
		InputStream inputStream = getResources().openRawResource(
				R.drawable.blank);
		// 通过一个InputStream创建一个BitmapDrawable对象
		blank = new BitmapDrawable(inputStream);
		EXcontent = (EditText) findViewById(R.id.EXcontent);
		content = (TextView) findViewById(R.id.content);
		// 经验步骤编辑栏
		count = 0;// 计数器归0
		EXcontent.setText(EXlist.get(count));
		content.setText("概要");
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
					next();// 执行下一步函数
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
					last();// 执行上一步函数
				}
				return false;
			}

		});

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// 实例化intent
			mainintent = new Intent();
			// 设置跳转的界面
			mainintent.setClass(EditEXActivity.this, SBSMainActivity.class);
			// 启动Activity
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
			opts.inPreferredConfig = Bitmap.Config.ARGB_4444; // 默认是Bitmap.Config.ARGB_8888
			/* 下面两个字段需要组合使用 */
			opts.inPurgeable = true;
			opts.inInputShareable = true;
			/* 这样才能真正的返回一个Bitmap */

			opts.inJustDecodeBounds = false;
			bit = BitmapFactory.decodeFile(picturePath, opts);

			BtoD = new BitmapDrawable(bit);
			picture.setBackgroundDrawable(BtoD);

			isChanged = true;// 已经更改

		}

	}

	// 下一步函数
	private void next() {
		if (EXcontent.getText().toString().equals("")) {
			Toast.makeText(EditEXActivity.this, "您还没编辑当前经验步骤！",
					Toast.LENGTH_LONG).show();
		} else {
			BtoD = new BitmapDrawable(bit);
			count++;
			if (max == count) {

				// 若修改后，保存当前步
				EXlist.set(count - 1, EXcontent.getText().toString());
				if (isChanged) {
					PIClist.set(count - 1, bit);
					isChanged = false;
				}

				picture.setBackgroundResource(R.drawable.blank);
				bit = blank.getBitmap();

				EXcontent.setText("");
				EXcontent.setHint("");

			} else if (max > count)// 判断当前步骤是否<当前步骤总数
			{
				// 若修改后，保存当前步
				EXlist.set(count - 1, EXcontent.getText().toString());
				if (isChanged) {
					PIClist.set(count - 1, bit);
					isChanged = false;
				}
				// 显示当前内容
				EXcontent.setText(EXlist.get(count));

				if (PIClist.get(count) != null) {
					BtoD = new BitmapDrawable(PIClist.get(count));
					picture.setBackgroundDrawable(BtoD);
				} else
					picture.setBackgroundResource(R.drawable.blank);

			} else {

				// 增加一步骤内容
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
			content.setText("第" + temp + "步");
		}
	}

	// 上一步函数
	private void last() {
		if (count == 0) {
			Toast.makeText(EditEXActivity.this, "不能再上了！", Toast.LENGTH_LONG)
					.show();
		} else {

			// 若修改后，保存当前步
			if (max == count) {
				if (!EXcontent.getText().toString().equals("")) {
					// 增加一步骤内容
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
			// 显示上一步
			count--;
			if (PIClist.get(count) != null) {
				BtoD = new BitmapDrawable(PIClist.get(count));
				picture.setBackgroundDrawable(BtoD);
			} else
				picture.setBackgroundResource(R.drawable.blank);

			EXcontent.setText(EXlist.get(count));// 显示上一步的编辑内容

			temp = String.valueOf(count);
			if (count == 0) {
				content.setText("概要");
			} else
				content.setText("第" + temp + "步");
		}
	}

	// 提交按钮函数
	private void submit() {
		// publish_btn.setClickable(false);
		Toast.makeText(EditEXActivity.this, "生成提交数据", Toast.LENGTH_LONG).show();
		String sbsname = themeName.getText().toString();
		String sbsDescrible = EXlist.get(0);
		String sbstool = needtools.getText().toString();
		Bitmap image = null;
		ArrayList<String> ls = null;
		ID = AVB.getKnowledgeID();
		if (!ID.equals("-1")) {
			Toast.makeText(EditEXActivity.this, "提交中", Toast.LENGTH_LONG)
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

			Toast.makeText(EditEXActivity.this, "提交成功！", Toast.LENGTH_SHORT)
					.show();
		} else {
			Toast.makeText(EditEXActivity.this, "提交失败！", Toast.LENGTH_SHORT)
					.show();
		}
	}

	private void setcontent() {

	}

	private void answerwant() {

	}
}
