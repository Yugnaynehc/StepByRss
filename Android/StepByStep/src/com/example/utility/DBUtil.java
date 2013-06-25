package com.example.utility;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.Toast;

import com.example.stepbystep.LoginActivity;

public class DBUtil {
	private static ArrayList<String> arrayList = new ArrayList<String>();
	private static ArrayList<String> brrayList = new ArrayList<String>();
	private static ArrayList<String> crrayList = new ArrayList<String>();
	private static HttpConnSoap Soap = new HttpConnSoap();

	public static Connection getConnection() {
		Connection con = null;
		try {
			// Class.forName("org.gjt.mm.mysql.Driver");
			// con=DriverManager.getConnection("jdbc:mysql://192.168.0.106:3306/test?useUnicode=true&characterEncoding=UTF-8","root","initial");
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return con;
	}

	/**
	 * 用户登录
	 * 
	 * @param userName
	 * @param pw
	 */
	public static String login(String userName, String pw) {

		arrayList.clear();
		brrayList.clear();
		crrayList.clear();

		arrayList.add("username");
		brrayList.add(userName);
		arrayList.add("userpwd");
		brrayList.add(pw);
		crrayList = Soap.GetWebServre("login", arrayList, brrayList);
		return crrayList.get(0);
	}

	public static String register(String userName, String pw, String email) {

		arrayList.clear();
		brrayList.clear();
		crrayList.clear();

		arrayList.add("username");
		brrayList.add(userName);
		arrayList.add("userpwd");
		brrayList.add(pw);
		arrayList.add("email");
		brrayList.add(email);
		crrayList = Soap.GetWebServre("register", arrayList, brrayList);
		return crrayList.get(0);
	}

	public static void exit(String userName) {

		arrayList.clear();
		brrayList.clear();
		crrayList.clear();

		arrayList.add("username");
		brrayList.add(userName);
		Soap.GetWebServre("exitsbs", arrayList, brrayList);
	}

	public static String submitHeadline(String username, String sbsname,
			String keywords, String classes, String sbsdescrible, String sbstool) {
		arrayList.clear();
		brrayList.clear();
		crrayList.clear();

		arrayList.add("username");
		brrayList.add(username);
		arrayList.add("sbsname");
		brrayList.add(sbsname);
		arrayList.add("keywords");
		brrayList.add(keywords);
		arrayList.add("classes");
		brrayList.add(classes);
		arrayList.add("sbsdescrible");
		brrayList.add(sbsdescrible);
		arrayList.add("sbstool");
		brrayList.add(sbstool);
		crrayList = Soap.GetWebServre("CreateNodea", arrayList, brrayList);

		return crrayList.get(0);
	}

	public static void submitDetail(String time, String info, String ID,
			String username) {
		arrayList.clear();
		brrayList.clear();
		crrayList.clear();

		arrayList.add("time");
		brrayList.add(time);
		arrayList.add("info");
		brrayList.add(info);
		arrayList.add("ID");
		brrayList.add(ID);
		arrayList.add("username");
		brrayList.add(username);

		Soap.GetWebServre("CreateNodeb", arrayList, brrayList);

	}

	public static void submitImage(String ID, String time, String textString,
			String textstringlen) {
		arrayList.clear();
		brrayList.clear();
		crrayList.clear();

		arrayList.add("ID");
		brrayList.add(ID);
		arrayList.add("time");
		brrayList.add(time);
		arrayList.add("textString");
		brrayList.add(textString);
		arrayList.add("textstringlen");
		brrayList.add(textstringlen);

		Soap.GetWebServre("createNodee", arrayList, brrayList);

	}

	public static ArrayList<String> bitmaptoString(Bitmap bitmap) {
		// 将Bitmap转换成字符串
		String string = null;

		crrayList.clear();
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 100, bStream);
		byte[] bytes = bStream.toByteArray();

		string = Base64.encodeToString(bytes, Base64.NO_WRAP);

		crrayList.add(string);
		crrayList.add(bytes.length + "");
		return crrayList;
	}

	public static Bitmap stringtoBitmap(String string) {
		// 将字符串转换成Bitmap类型
		Bitmap bitmap = null;
		try {
			byte[] bitmapArray;
			bitmapArray = Base64.decode(string, Base64.NO_WRAP);
			bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
					bitmapArray.length);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bitmap;
	}

	public static ArrayList<String> getUserKnowledge(String username) {
		arrayList.clear();
		brrayList.clear();
		crrayList.clear();

		arrayList.add("username");
		brrayList.add(username);

		crrayList = Soap.GetWebServre("viewsbs", arrayList, brrayList);
		return crrayList;
	}

	public static String getKnowledgeDescribe(String ID) {
		arrayList.clear();
		brrayList.clear();
		crrayList.clear();

		arrayList.add("ID");
		brrayList.add(ID);

		crrayList = Soap.GetWebServre("Getsbsdescrible", arrayList, brrayList);
		return crrayList.get(0);
	}

	public static ArrayList<String> getKnowledgeDetail(String ID) {
		arrayList.clear();
		brrayList.clear();
		crrayList.clear();

		arrayList.add("ID");
		brrayList.add(ID);

		crrayList = Soap.GetWebServre("getstep", arrayList, brrayList);
		return crrayList;
	}

	public static ArrayList<String> getKnowledgeImage(String ID) {
		arrayList.clear();
		brrayList.clear();
		crrayList.clear();

		arrayList.add("ID");
		brrayList.add(ID);

		crrayList = Soap.GetWebServre("GetPhoto", arrayList, brrayList);
		return crrayList;
	}

	public static ArrayList<String> searchKnowledge(String info) {
		arrayList.clear();
		brrayList.clear();
		crrayList.clear();

		arrayList.add("info");
		brrayList.add(info);

		crrayList = Soap.GetWebServre("viewsomesbs", arrayList, brrayList);
		return crrayList;
	}

	public static String getKnowledgeName(String ID) {
		arrayList.clear();
		brrayList.clear();
		crrayList.clear();

		arrayList.add("ID");
		brrayList.add(ID);

		crrayList = Soap.GetWebServre("viewsbsname", arrayList, brrayList);
		return crrayList.get(0);
	}

	public static String getNeedTools(String ID) {
		arrayList.clear();
		brrayList.clear();
		crrayList.clear();

		arrayList.add("ID");
		brrayList.add(ID);

		crrayList = Soap.GetWebServre("viewsbstool", arrayList, brrayList);
		return crrayList.get(0);
	}

	public static String getKnowledgeEditer(String ID) {
		arrayList.clear();
		brrayList.clear();
		crrayList.clear();

		arrayList.add("ID");
		brrayList.add(ID);

		crrayList = Soap.GetWebServre("viewsbsusername", arrayList, brrayList);
		return crrayList.get(0);
	}

	public static ArrayList<String> getKnowledgeSortByID() {
		crrayList = Soap.GetWebServre("searchnew", arrayList, brrayList);
		return crrayList;
	}

	public static ArrayList<String> getKnowledgeSortByZan() {
		crrayList = Soap.GetWebServre("searchzan", arrayList, brrayList);
		return crrayList;
	}

	public static ArrayList<String> getKnowledgeSortByHot() {
		crrayList = Soap.GetWebServre("searchhot", arrayList, brrayList);
		return crrayList;
	}
	
	public static ArrayList<String> getKnowledgeSortByWanted() {
		crrayList = Soap.GetWebServre("searchex", arrayList, brrayList);
		return crrayList;
	}

	public static String getZan(String ID) {
		arrayList.clear();
		brrayList.clear();
		crrayList.clear();

		arrayList.add("ID");
		brrayList.add(ID);

		crrayList = Soap.GetWebServre("viewsbszan", arrayList, brrayList);
		return crrayList.get(0);
	}

	public static String addZan(String ID) {
		arrayList.clear();
		brrayList.clear();
		crrayList.clear();

		arrayList.add("ID");
		brrayList.add(ID);

		crrayList = Soap.GetWebServre("zan", arrayList, brrayList);
		return crrayList.get(0);
	}

	public static ArrayList<String> searchUser(String info) {
		arrayList.clear();
		brrayList.clear();
		crrayList.clear();

		arrayList.add("info");
		brrayList.add(info);

		crrayList = Soap.GetWebServre("SearchUser", arrayList, brrayList);
		return crrayList;
	}

	public static void addHit(String ID) {

		arrayList.clear();
		brrayList.clear();
		crrayList.clear();

		arrayList.add("ID");
		brrayList.add(ID);

		crrayList = Soap.GetWebServre("Addhot", arrayList, brrayList);

	}

	public static String getSign(String username) {

		arrayList.clear();
		brrayList.clear();
		crrayList.clear();

		arrayList.add("username");
		brrayList.add(username);

		crrayList = Soap.GetWebServre("Getqianming", arrayList, brrayList);
		return crrayList.get(0);
	}

	public static String getMark(String username) {

		arrayList.clear();
		brrayList.clear();
		crrayList.clear();

		arrayList.add("username");
		brrayList.add(username);

		crrayList = Soap.GetWebServre("Getmark", arrayList, brrayList);
		return crrayList.get(0);
	}
	
	public static ArrayList<String> getComment(String ID) {
		arrayList.clear();
		brrayList.clear();
		crrayList.clear();

		arrayList.add("ID");
		brrayList.add(ID);

		crrayList = Soap.GetWebServre("GetCommentsbs", arrayList, brrayList);
		return crrayList;
	}
	
	public static ArrayList<String> addComment(String ID, String info, String usernamea) {
		arrayList.clear();
		brrayList.clear();
		crrayList.clear();

		arrayList.add("ID");
		brrayList.add(ID);
		arrayList.add("info");
		brrayList.add(info);
		arrayList.add("usernamea");
		brrayList.add(usernamea);

		crrayList = Soap.GetWebServre("commentsbsa", arrayList, brrayList);
		return crrayList;
	}
	
	public static String isAttention(String username, String usernamea) {
		arrayList.clear();
		brrayList.clear();
		crrayList.clear();

		arrayList.add("username");
		brrayList.add(username);
		arrayList.add("usernamea");
		brrayList.add(usernamea);

		crrayList = Soap.GetWebServre("Isattention", arrayList, brrayList);
		return crrayList.get(0);
	}
	
	public static void setAttention(String username, String usernamea) {
		arrayList.clear();
		brrayList.clear();
		crrayList.clear();

		arrayList.add("username");
		brrayList.add(username);
		arrayList.add("usernamea");
		brrayList.add(usernamea);

		Soap.GetWebServre("attention", arrayList, brrayList);
	}
	
	public static void cancelAttention(String username, String usernamea) {
		arrayList.clear();
		brrayList.clear();
		crrayList.clear();

		arrayList.add("username");
		brrayList.add(username);
		arrayList.add("usernamea");
		brrayList.add(usernamea);

		Soap.GetWebServre("cancelattention", arrayList, brrayList);
	}

	public static ArrayList<String> getAttentionList(String usernamea) {
		
		arrayList.clear();
		brrayList.clear();
		crrayList.clear();

		arrayList.add("usernamea");
		brrayList.add(usernamea);

		crrayList = Soap.GetWebServre("Viewattention", arrayList, brrayList);
		return crrayList;
	}
	
	public static String addReport(String username, String sbsname, String reason, String usernamea, String ID) {
		arrayList.clear();
		brrayList.clear();
		crrayList.clear();

		arrayList.add("username");
		brrayList.add(username);
		arrayList.add("sbsname");
		brrayList.add(sbsname);
		arrayList.add("reason");
		brrayList.add(reason);
		arrayList.add("usernamea");
		brrayList.add(usernamea);
		arrayList.add("ID");
		brrayList.add(ID);

		crrayList = Soap.GetWebServre("report", arrayList, brrayList);
		return crrayList.get(0);
	}
	
	public static void deleteKnowledge(String username, String ID) {
		arrayList.clear();
		brrayList.clear();
		crrayList.clear();

		arrayList.add("username");
		brrayList.add(username);
		arrayList.add("ID");
		brrayList.add(ID);

		Soap.GetWebServre("Deletesbs", arrayList, brrayList);
	}

}
