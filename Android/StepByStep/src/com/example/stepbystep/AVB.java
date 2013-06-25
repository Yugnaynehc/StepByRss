package com.example.stepbystep;

import java.util.ArrayList;

import com.example.utility.DBUtil;

import android.app.Application;
import android.graphics.Bitmap;

public class AVB extends Application {

	private static String Type;
	private static String userName = null;//��ǰ��¼���û���
	private static String selectUserName = null;//ѡ��鿴���û���
	private static String knowledgeID = null; //��ǰ�鿴�����ID
	private static boolean isUser = false;// �Ƿ��ѵ�¼
	private static String knowledgeDescribe = null;
	private static ArrayList<String> knowledgeDetail = null;
	private static ArrayList<String> knowledgeImage = null;
	private static int shang= 0xff99cc33;//��������ɫ
	private static int zhong= 0xffffffff;//չʾ����ɫ
	private static int xia= 0xff0099ff;//���˵���ɫ

	public void onCreate() {
		super.onCreate();
		setType(" "); // ��ʼ��ȫ�ֱ���
	}

	public static void setUserName(String s) {
		userName = s;
	}

	public static String getUserName() {
		return userName;
	}
	
	public static void setSelectUserName(String s) {
		selectUserName = s;
	}
	
	public static String getSelectUserName() {
		return selectUserName ;
	}

	public static boolean getIsUser() {
		return isUser;
	}

	public static void setIsUser(boolean b) {
		isUser = b;
	}

	public static String getType() {
		return Type;
	}

	public static void setType(String s) {
		Type = s;
	}

	public static void setKnowledgeID(String ID) {
		knowledgeID = ID;
	}
	
	public static String getKnowledgeID() {
		return knowledgeID;
	}
	
	public static String getKnowledgeDescribe() {
		if (knowledgeID != null)
			knowledgeDescribe = DBUtil.getKnowledgeDescribe(knowledgeID);
		return knowledgeDescribe;
	}

	public static ArrayList<String> getKnowledgeDetail() {
		if (knowledgeID != null)
			knowledgeDetail = DBUtil.getKnowledgeDetail(knowledgeID);
		return knowledgeDetail;
	}

	public static ArrayList<String> getKnowledgeImage() {
		if (knowledgeID != null)
			knowledgeImage = DBUtil.getKnowledgeImage(knowledgeID);
		return knowledgeImage;
	}
	public static void setshang(int c)
	{
		shang = c;
	}
	public static int getshang()
	{
		return shang;
	}
	public static void setzhong(int c)
	{
		zhong = c;
	}
	public static int getzhong()
	{
		return zhong;
	}
	public static void setxia(int c)
	{
		xia = c;
	}
	public static int getxia()
	{
		return xia;
	}
}