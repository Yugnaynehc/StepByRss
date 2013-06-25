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

	// 返回主页
	private Intent mainintent;
	// 经验页面
	private TextView themeName;// 主题名
	private TextView needtools;// 所需工具
	private TextView zan;// 赞数的统计
	private TextView tool;// 工具图标
	private TextView now;// 当前步骤
	// 菜单按钮
	private ImageView caozuo;
	private ImageView zan_btn;// 赞按钮
	private ImageView message;// 留言按钮
	private ImageView writer;// 发布人按钮
	private ImageView jubao;// 举报按钮

	public String ID = "-1";// 经验ID
	// 经验图片
	private static int RESULT_LOAD_IMAGE = 1;
	private ImageView picture;// 步骤的图片
	private Bitmap bit;//
	private BitmapDrawable BtoD;// Bitmap to Drawable

	// 经验步骤
	private String temp;// 把步骤转化为String
	private int count;// 步骤计数器
	private int max;// 当前已编辑的步骤数
	private ImageButton next;// 下一步
	private ImageButton last;// 上一步
	private TextView content;// 当前步骤
	private List<String> EXlist = new ArrayList<String>();// 经验存放的数组
	private List<String> Templist = new ArrayList<String>(); // 临时数组，用于经验图片的获取
	private List<Bitmap> PIClist = new ArrayList<Bitmap>();// 经验图片的数组

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.look);
		Templist = AVB.getKnowledgeDetail();
		for (int i = 0; i < Templist.size(); ++i)
			EXlist.add(Templist.get(i));

		max = EXlist.size() / 2;// 步骤总数

		Templist = AVB.getKnowledgeImage();
		for (int i = 0; i < max; ++i) {
			PIClist.add(null);
		}
		System.out.println(Templist.size() / 3);
		for (int i = 0; i < Templist.size() / 3; ++i) {
			PIClist.set(Integer.parseInt(Templist.get(3 * i + 1)),
					DBUtil.stringtoBitmap(Templist.get(3 * i + 2)));
		}

		// 初始化控件
		initeViews();
	}

	private void initeViews() {

		themeName = (TextView) findViewById(R.id.show_EX);
		themeName.setText(DBUtil.getKnowledgeName(AVB.getKnowledgeID()));
		zan = (TextView) findViewById(R.id.zan);
		zan.setText("有" + DBUtil.getZan(AVB.getKnowledgeID()) + "人觉得赞！");
		content = (TextView) findViewById(R.id.EXshow);
		tool = (TextView) findViewById(R.id.needtools);
		now = (TextView) findViewById(R.id.content_show);
		needtools = (TextView) findViewById(R.id.needtools_show);
		needtools.setText(DBUtil.getNeedTools(AVB.getKnowledgeID()));
		picture = (ImageView) findViewById(R.id.picture_show);

		// 经验步骤编辑栏
		count = 0;// 计数器归0
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
						now.setText("第 " + count + " 步");
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
							now.setText("概要");
						} else {
							now.setText("第 " + count + " 步");
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
		// 赞或操作按钮
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
						zan.setText("有" + DBUtil.getZan(AVB.getKnowledgeID())
								+ "人觉得赞！");
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
		// 留言按钮
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
		// 发布人
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
					// 启动Activity
					startActivity(intent);
					LookActivity.this.finish();

				}
				return false;
			}

		});
		// 举报
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
						Toast.makeText(LookActivity.this, "只有登录后才能举报！",
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

		builder.setTitle("请输入举报原因");

		builder.setPositiveButton("确认", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				if(ed.getText().toString().equals(""))
					Toast.makeText(LookActivity.this, "举报信息不能为空",
							Toast.LENGTH_SHORT).show();
				else if (DBUtil.addReport(
						DBUtil.getKnowledgeEditer(AVB.getKnowledgeID()),
						DBUtil.getKnowledgeName(AVB.getKnowledgeID()),
						ed.getText().toString(), AVB.getUserName(),
						AVB.getKnowledgeID()).equals("true"))
					Toast.makeText(LookActivity.this, "举报成功",
							Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(LookActivity.this, "举报失败",
							Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
		});

		builder.setNegativeButton("取消", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.create().show();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// 实例化intent
			mainintent = new Intent();
			// 设置跳转的界面
			mainintent.setClass(LookActivity.this, SBSMainActivity.class);
			// 启动Activity
			startActivity(mainintent);
			LookActivity.this.finish();

		}
		return false;
	}

	private void caozuo() {
		Dialog dialog = new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.btn_star).setTitle("用户操作")
				.setPositiveButton("修改经验", new OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent();
						intent.setClass(LookActivity.this, publishActivity.class);

						startActivity(intent);
						LookActivity.this.finish();
					}
				}).setNegativeButton("删除经验", new OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						DBUtil.deleteKnowledge(AVB.getUserName(),
								AVB.getKnowledgeID());
						
						Intent intent = new Intent();
						intent.setClass(LookActivity.this, SBSMainActivity.class);

						startActivity(intent);
						LookActivity.this.finish();
					}
				}).setNeutralButton("返回", new OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

					}
				}).create();

		dialog.show();
	}

	void jump() {

		// 实例化intent
		Intent intent = new Intent();
		if (AVB.getIsUser()) {

			// 设置跳转的界面
			intent.setClass(LookActivity.this, message.class);
			// 启动Activity
			startActivity(intent);
			LookActivity.this.finish();
		} else {
			AVB.setType("lookactivity");
			// 设置跳转的界面
			intent.setClass(LookActivity.this, LoginActivity.class);
			// 启动Activity
			startActivity(intent);
			LookActivity.this.finish();
		}

	}
}
