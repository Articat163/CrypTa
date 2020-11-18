package ru.articat.crypta.Settings;

import org.jsoup.nodes.Document;

import java.util.ArrayList;

public class Constants{
	
	public final static String FILENAME="/crypta_saved_images";
	public final static String IMAGENAME="UserImage_";
	public final static String TAG = "crypta_logs";
	final static String NICY_PREF = "crypta_preferences";
	public final static String LOGIN = "user_login_";
	public final static String PASSWORD = "user_password_";
	public final static String REMEMBER = "remember";
	public final static String USERID = "user_id_";
//	public static Calendar EXITTIME;
	public final static String USERNAME = "user_name_";
	final static String USERCITY = "user_city_";
	public final static String USERLINK = "user_link_";
	final static String USERPHOTOLINK = "user_photo_link_";
//	public final static String VALIDWIDGET= "valid_widget_";
//	public final static String UNREADMESSAGES= "unread_messages_";
	public static int UNREADMESSAGES= 0;
	public final static String PINCODE= "pin_code_";
	public final static int ERROR=100;
	public final static int OK=200;
	public final static int NOINTERNET=300;
//	public final static String UNDERSTANDSYNC="understand_1_";
//	public final static String UNDERSTANDDELETE="understand_2_";
//	public final static String WIDGETTYPE="widget_type";
	public static boolean MESSAGESRUNING=false;
//	public static int DISPLAYHEIGHT;
//	public static int DISPLAYWIDTH;
	public static int NOTIFICATION_ID;
	public static int WHATOPEN=0; // 0- открыт форум; 1- открыта тема; 2- открыт профиль; 3- закладки, 4- пинкод, 5-новое
	public static int SELECTEDITEM;
	public static boolean FABLOCK=false; // блокировать или нет FAB
	public static String LINKTOTHEME="";
	public static boolean JUSTSENDTOTHEME=false;
	public static String THEMETITLE;
	public static String FORUMSLINK;
	public static String FORUMNAME;
//	public static String FORUMSLISTDATABASE="FLdbCreated";
	public static boolean AUTHORIZED=false; // для изменения настроек
//	public static ArrayList<NewsByDay> NEWSBYDAY;
	public static Document MAINPAGE;
	
//	public static enum Open {
//	FORUM, THEME, PROFILE, MARK, LOCK
//	}
	
	public static ArrayList<Integer> arrayForDelete= new ArrayList<>();
}
