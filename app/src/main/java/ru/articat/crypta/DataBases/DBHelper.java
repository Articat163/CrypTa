package ru.articat.crypta.DataBases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;

import ru.articat.crypta.Settings.Constants;
import ru.articat.crypta.Settings.DoPreferences;


public class DBHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "CryptaDB.db_";
	public static final String TABLE_NAME = "messages";
	public static final String COLUMN_ID = "id";
	public static final String USER_COLUMN_NEW_MSG = "new_msg";
	public static final String USER_COLUMN_GETORSEND = "get_or_send";
	public static final String USER_COLUMN_ID = "user_id";
	public static final String USER_COLUMN_MESSAGE_ID = "message_id";
	public static final String USER_COLUMN_NAME = "name";
	public static final String USER_COLUMN_YEARS = "years";
	public static final String USER_COLUMN_PHOTO_LINK = "photo_link";
	public static final String USER_COLUMN_SEND = "send";
	public static final String USER_COLUMN_ONSITE = "onsite";
	public static final String USER_COLUMN_MESSAGE = "message";
//	private HashMap hp;
	private Context context;
	private String TAG = Constants.TAG;

	public DBHelper(Context context)
	{
		super(context, DATABASE_NAME+new DoPreferences(context).loadData(Constants.LOGIN, "") , null, DATABASE_VERSION);
		this.context=context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(
			"create table messages " +
			"("+COLUMN_ID+" integer primary key,new_msg integer,get_or_send text,user_id text, message_id integer,name text,years text, photo_link text,send text,onsite text, message text)"
		);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS messages");
		onCreate(db);
	}

	public boolean insertMessage  (int new_msg, String get_or_send, String user_id, int message_id, String name, String years,String photo_link, String onsite, String send,  String messages)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(USER_COLUMN_NEW_MSG, new_msg);
		contentValues.put(USER_COLUMN_GETORSEND, get_or_send);
		contentValues.put(USER_COLUMN_ID, user_id);
		contentValues.put(USER_COLUMN_MESSAGE_ID, message_id);
		contentValues.put(USER_COLUMN_NAME, name);	
		contentValues.put(USER_COLUMN_YEARS, years);
		contentValues.put(USER_COLUMN_PHOTO_LINK, photo_link);
		contentValues.put(USER_COLUMN_SEND, send);
		contentValues.put(USER_COLUMN_ONSITE, onsite);
		contentValues.put(USER_COLUMN_MESSAGE, messages);
		db.insert(TABLE_NAME, null, contentValues);
		return true;
	}

	public Cursor getData(int id){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res =  db.rawQuery( "select * from messages where id="+id+"", null );
		return res;
	}

	public int numberOfRows(){
		SQLiteDatabase db = this.getReadableDatabase();
		int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
		return numRows;
	}

//	public boolean updateMessage (Integer id, String new_msg, String get_or_send, String user_id, String message_id, String name, String years,String photo_link, String send, String onsite, String messages)
//	{
//		SQLiteDatabase db = this.getWritableDatabase();
//		ContentValues contentValues = new ContentValues();
//		contentValues.put(USER_COLUMN_NEW_MSG, new_msg);
//		contentValues.put(USER_COLUMN_GETORSEND, get_or_send);
//		contentValues.put(USER_COLUMN_ID, user_id);
//		contentValues.put(USER_COLUMN_MESSAGE_ID, message_id);
//		contentValues.put(USER_COLUMN_NAME, name);	
//		contentValues.put(USER_COLUMN_YEARS, years);
//		contentValues.put(USER_COLUMN_PHOTO_LINK, photo_link);
//		contentValues.put(USER_COLUMN_SEND, send);
//		contentValues.put(USER_COLUMN_ONSITE, onsite);
//		contentValues.put(USER_COLUMN_MESSAGE, messages);
//		db.update(TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
//		return true;
//	}

	public Integer deleteMessage (Integer id)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		return db.delete(TABLE_NAME, 
						 "id = ? ", 
						 new String[] { Integer.toString(id) });
	}
	
	
	public void deleteRowById(int id){
		SQLiteDatabase db = this.getWritableDatabase();
//		String select= "DELETE FROM "+TABLE_NAME+" WHERE ID = "+id;
//
//		db.rawQuery(select, null);
		db.delete(TABLE_NAME, COLUMN_ID + "="+id,null);
	//	DELETE FROM CUSTOMERS WHERE ID = 6
	}
	
	
	public void deleteDb(Context c){
	//	SQLiteDatabase db=this.getWritableDatabase();
		String dbName=DATABASE_NAME+new DoPreferences(context).loadData(Constants.LOGIN, "");
		if(doesDatabaseExist(c, dbName)){
		c.deleteDatabase(dbName);
			Log.i(TAG, "Database " + dbName +" -DELETED");
		}
	}
	
	
	public static boolean doesDatabaseExist(Context context, String dbName) {
		File dbFile = context.getDatabasePath(dbName);
		Log.i(Constants.TAG, "doesDatabaseExist= "+dbFile.exists());
		return dbFile.exists();
	}


	
	// есть-ли сообщение в базе?
	/** @return true if given date/cowNum combination is already in the db, false otherwise */
	public boolean dubUpCheck(String messId) {
		SQLiteDatabase db = this.getWritableDatabase();
		/* The 3rd parameter is treated as SQL WHERE clause, ? are replaced by strings
		 * from the 4th parameter */
		Cursor cur = db.query(TABLE_NAME, null, "message_id = ?", new String[] {messId}, null, null, null, null);
		if (cur != null && cur.getCount()>0) {
			// duplicate found
			cur.close();
			return true;
		}
		cur.close();
		return false;
	}
	
	
	// есть-ли юзер в базе данных?
	public boolean isUserInBase(String userId) {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cur = db.query(TABLE_NAME, null, "user_id = ?", new String[] {userId}, null, null, null, null);
		if (cur != null && cur.getCount()>0) {
			// есть
			cur.close();
			return true;
		}
		// нет
		cur.close();
		return false;
	}
	
	
	// сколько не прочитанных смс в базе
	public int getUnreadInBase(){
		int msg=0;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cur = db.query(TABLE_NAME, 
							   new String[] {"SUM(new_msg) AS counter"}, 
							   null, null, null, null, null);
		if(cur.moveToFirst()){
			msg=cur.getInt(0);
			cur.close();
			return msg;
		}
		cur.close();
		return msg;
	}
}
