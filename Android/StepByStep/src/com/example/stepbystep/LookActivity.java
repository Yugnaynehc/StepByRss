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
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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

public class LookActivity extends Activity {

	// ������ҳ
	private Intent mainintent;
	// ����ҳ��
	private TextView themeName;// ������
	private TextView needtools;// ���蹤��
	private TextView zan;// ������ͳ��
	private TextView tool;// ����ͼ��
	private TextView now;// ��ǰ����
	// �˵���ť
	private ImageView caozuo;
	private ImageView zan_btn;// �ް�ť
	private ImageView message;// ���԰�ť
	private ImageView writer;// �����˰�ť
	private ImageView jubao;// �ٱ���ť

	public String ID = "-1";// ����ID
	// ����ͼƬ
	private static int RESULT_LOAD_IMAGE = 1;
	private ImageView picture;// �����ͼƬ
	private Bitmap bit;//
	private BitmapDrawable BtoD;// Bitmap to Drawable

	// ���鲽��
	private String temp;// �Ѳ���ת��ΪString
	private int count;// ���������
	private int max;// ��ǰ�ѱ༭�Ĳ�����
	private ImageButton next;// ��һ��
	private ImageButton last;// ��һ��
	private TextView content;// ��ǰ����
	private List<String> EXlist = new ArrayList<String>();// �����ŵ�����
	private List<String> Templist = new ArrayList<String>(); // ��ʱ���飬���ھ���ͼƬ�Ļ�ȡ
	private List<Bitmap> PIClist = new ArrayList<Bitmap>();// ����ͼƬ������

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.look);
		Templist = AVB.getKnowledgeDetail();
		for (int i = 0; i < Templist.size(); ++i)
			EXlist.add(Templist.get(i));

		max = EXlist.size() / 2;// ��������

		Templist = AVB.getKnowledgeImage();
		for (int i = 0; i < max; ++i) {
			PIClist.add(null);
		}
		System.out.println(Templist.size() / 3);
		for (int i = 0; i < Templist.size() / 3; ++i) {
			PIClist.set(Integer.parseInt(Templist.get(3 * i + 1)),
					DBUtil.stringtoBitmap(Templist.get(3 * i + 2)));
		}

		// ��ʼ���ؼ�
		initeViews();
	}

	private void initeViews() {

		themeName = (TextView) findViewById(R.id.show_EX);
		themeName.setText(DBUtil.getKnowledgeName(AVB.getKnowledgeID()));
		zan = (TextView) findViewById(R.id.zan);
		zan.setText("��" + DBUtil.getZan(AVB.getKnowledgeID()) + "�˾����ޣ�");
		content = (TextView) findViewById(R.id.EXshow);
		tool = (TextView) findViewById(R.id.needtools);
		now = (TextView) findViewById(R.id.content_show);
		needtools = (TextView) findViewById(R.id.needtools_show);
		needtools.setText(DBUtil.getNeedTools(AVB.getKnowledgeID()));
		picture = (ImageView) findViewById(R.id.picture_show);

		// ���鲽��༭��
		count = 0;// ��������0
		content.setText(EXlist.get(count * 2 + 1));
		bit = PIClist.get(count);
		if (bit != null) {
			BtoD = new BitmapDrawable(bit);
			picture.setBackgroundDrawable(BtoD);
		}

		next = (ImageButton) findViewById(R.id.next_btn);
		next.setBackgroundResource(R.drawable.next);
		next.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					next.setBackgroundResource(R.drawable.next2);
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					next.setBackgroundResource(R.drawable.next);
					if (count < max - 1) {
						count++;
						now.setText("�� " + count + " ��");
						content.setText(EXlist.get(count * 2 + 1));
						bit = PIClist.get(count);
						if (bit != null) {
							BtoD = new BitmapDrawable(bit);
							picture.setBackgroundDrawable(BtoD);
						}
					}
				}
				return false;
			}

		});
		last = (ImageButton) findViewById(R.id.last_btn);
		last.setBackgroundResource(R.drawable.last);
		last.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					last.setBackgroundResource(R.drawable.last2);
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					last.setBackgroundResource(R.drawable.last);
					if (count > 0) {
						count--;
						if (count == 0) {
							now.setText("��Ҫ");
						} else {
							now.setText("�� " + count + " ��");
						}
						content.setText(EXlist.get(count * 2 + 1));
						bit = PIClist.get(count);
						if (bit != null) {
							BtoD = new BitmapDrawable(bit);
							picture.setBackgroundDrawable(BtoD);
						}
					}
				}
				return false;
			}

		});
		// �޻������ť
		if ((AVB.getUserName() == null)
				|| (!AVB.getUserName().equals(
						DBUtil.getKnowledgeEditer(AVB.getKnowledgeID())))) {
			zan_btn = (ImageButton) findViewById(R.id.zan_btn);
			zan_btn.setBackgroundResource(R.drawable.zan_btn1);

			zan_btn.setOnTouchListener(new OnTouchListener() {

				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						zan_btn.setBackgroundResource(R.drawable.zan_btn2);
					}
					if (event.getAction() == MotionEvent.ACTION_UP) {
						zan_btn.setBackgroundResource(R.drawable.zan_btn1);
						DBUtil.addZan(AVB.getKnowledgeID());
						zan.setText("��" + DBUtil.getZan(AVB.getKnowledgeID())
								+ "�˾����ޣ�");
					}
					return false;
				}

			});

		} else {
			caozuo = (ImageButton) findViewById(R.id.zan_btn);
			caozuo.setBackgroundResource(R.drawable.cao2);

			caozuo.setOnTouchListener(new OnTouchListener() {

				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						caozuo.setBackgroundResource(R.drawable.cao1);
					}
					if (event.getAction() == MotionEvent.ACTION_UP) {
						caozuo.setBackgroundResource(R.drawable.cao2);
						caozuo();
					}
					return false;
				}

			});

		}
		// ���԰�ť
		message = (ImageButton) findViewById(R.id.message);
		message.setBackgroundResource(R.drawable.message2);

		message.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					message.setBackgroundResource(R.drawable.message);
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					message.setBackgroundResource(R.drawable.message2);
					jump();
				}
				return false;
			}

		});
		// ������
		writer = (ImageButton) findViewById(R.id.publishman);
		writer.setBackgroundResource(R.drawable.writer2);

		writer.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					writer.setBackgroundResource(R.drawable.writer);
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					writer.setBackgroundResource(R.drawable.writer2);
					AVB.setSelectUserName(DBUtil.getKnowledgeEditer(AVB
							.getKnowledgeID()));

					Intent intent = new Intent();
					intent.setClass(LookActivity.this, TAmainpage.class);
					// ����Activity
					startActivity(intent);
					LookActivity.this.finish();

				}
				return false;
			}

		});
		// �ٱ�
		jubao = (ImageButton) findViewById(R.id.report);
		jubao.setBackgroundResource(R.drawable.jubao2);
		
		jubao.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					jubao.setBackgroundResource(R.drawable.jubao);
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					jubao.setBackgroundResource(R.drawable.jubao2);
					if (!AVB.getIsUser())
					{
						Toast.makeText(LookActivity.this, "ֻ�е�¼����ܾٱ���",
								Toast.LENGTH_SHORT).show();
					}
					else
						jubao();
				}
				return false;
			}

		});

	}

	private void jubao() {
		AlertDialog.Builder builder = new Builder(LookActivity.this);
		final EditText ed = new EditText(this);
		builder.setView(ed);

		builder.setTitle("������ٱ�ԭ��");

		builder.setPositiveButton("ȷ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				if(ed.getText().toString().equals(""))
					Toast.makeText(LookActivity.this, "�ٱ���Ϣ����Ϊ��",
							Toast.LENGTH_SHORT).show();
				else if (DBUtil.addReport(
						DBUtil.getKnowledgeEditer(AVB.getKnowledgeID()),
						DBUtil.getKnowledgeName(AVB.getKnowledgeID()),
						ed.getText().toString(), AVB.getUserName(),
						AVB.getKnowledgeID()).equals("true"))
					Toast.makeText(LookActivity.this, "�ٱ��ɹ�",
							Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(LookActivity.this, "�ٱ�ʧ��",
							Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
		});

		builder.setNegativeButton("ȡ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.create().show();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// ʵ����intent
			mainintent = new Intent();
			// ������ת�Ľ���
			mainintent.setClass(LookActivity.this, SBSMainActivity.class);
			// ����Activity
			startActivity(mainintent);
			LookActivity.this.finish();

		}
		return false;
	}

	private void caozuo() {
		Dialog dialog = new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.btn_star).setTitle("�û�����")
				.setPositiveButton("�޸ľ���", new OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent();
						intent.setClass(LookActivity.this, publishActivity.class);

						startActivity(intent);
						LookActivity.this.finish();
					}
				}).setNegativeButton("ɾ������", new OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						DBUtil.deleteKnowledge(AVB.getUserName(),
								AVB.getKnowledgeID());
						
						Intent intent = new Intent();
						intent.setClass(LookActivity.this, SBSMainActivity.class);

						startActivity(intent);
						LookActivity.this.finish();
					}
				}).setNeutralButton("����", new OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

					}
				}).create();

		dialog.show();
	}

	void jump() {

		// ʵ����intent
		Intent intent = new Intent();
		if (AVB.getIsUser()) {

			// ������ת�Ľ���
			intent.setClass(LookActivity.this, message.class);
			// ����Activity
			startActivity(intent);
			LookActivity.this.finish();
		} else {
			AVB.setType("lookactivity");
			// ������ת�Ľ���
			intent.setClass(LookActivity.this, LoginActivity.class);
			// ����Activity
			startActivity(intent);
			LookActivity.this.finish();
		}

	}
}
