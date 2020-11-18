package ru.articat.crypta.DataBases;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.articat.crypta.Settings.Constants;
import ru.articat.crypta.Settings.DoPreferences;

public class DBHelperMarkedList extends SQLiteOpenHelper
{
	private Context context;
	private DBHelper mDBHelper;
	private SQLiteDatabase db;
	
	
	private static final String DATABASE_NAME = "CryptaMarkedDB.db_";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "marks";
	private static final String COLUMN_ID = "_id";
	public static final String SECTION = "section";
	private static final String LINK = "link";
	public static final String TITLE = "title";
	public static final String TEXT = "text";
	public static final String NICK = "nick";
	public static final String DATE = "date";
	public static final String USER = "user";
	public static final String UDATE = "udate";
	public static final String UTIME = "utime";
	public static final String CITY = "city";
	public static final String ANSWERS = "answers";
	public static final String VIEWS = "views";
	private static final String VIDEO = "video";
	
	
	public DBHelperMarkedList(Context context){
        super(context, DATABASE_NAME+new DoPreferences(context).loadData(Constants.LOGIN, ""), null, DATABASE_VERSION);
		this.context=context;
    }
	

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		// TODO: Implement this method
		db.execSQL(
			"create table "+ TABLE_NAME +
			"("+ COLUMN_ID +" integer primary key,"+
			SECTION+" text,"+
			LINK+" text,"+
			TITLE+" text,"+
			TEXT+" text,"+
			NICK+" text,"+
			DATE+" text,"+
			USER+" text,"+
			UDATE+" text,"+
			UTIME+" text,"+
			CITY+" text,"+
			ANSWERS+" text,"+
			VIEWS+" text,"+
			VIDEO+" text"+
			")");
		
	}
	
//	// открыть подключение
//	public void open(SQLiteDatabase db) {
//	//	mDBHelper= new DBHelper(context);
//		db = mDBHelper.getWritableDatabase();
//	}
//
//	// закрыть подключение
//	public void close() {
//		if (mDBHelper!=null) mDBHelper.close();
//	}
	

	@Override
	public void onUpgrade(SQLiteDatabase db, int p2, int p3)
	{
		// TODO: Implement this method
		db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
		onCreate(db);
	}
	
	
	public boolean insertMark  (SQLiteDatabase db,String section, String link, String title, String text, 
									String nick, String date, String user,String udate, String utime, String city,
									String answers, String views, String video)
	{
		//	SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(SECTION, section);
		contentValues.put(LINK, link);
		contentValues.put(TITLE, title);
		contentValues.put(TEXT, text);
		contentValues.put(NICK, nick);
		contentValues.put(DATE, date);
		contentValues.put(USER, user);
		contentValues.put(UDATE, udate);
		contentValues.put(UTIME, utime);
		contentValues.put(CITY, city);
		contentValues.put(ANSWERS, answers);
		contentValues.put(VIEWS, views);
		contentValues.put(VIDEO, video);

		db.insert(TABLE_NAME, null, contentValues);

		return true;
	}
	
	// удалить из закладок
	public Integer deleteMark (SQLiteDatabase db, String link)
	{
		//SQLiteDatabase db = this.getWritableDatabase();
		return db.delete(TABLE_NAME, 
						 "link = ? ", 
						 new String[] { link });
	}

	
	// база заполнена? есть записи?
	public boolean isDbFilled(SQLiteDatabase db, Context context){
		// проверка существования записей
		
		Cursor cur = db.query(TABLE_NAME, null, null, null, null, null, null);
		if (cur.getCount() != 0) {
			//	записи есть
			cur.close();
			return true;
		}
		// записей нет
		cur.close();
		return false;
	}
	
	
	// есть-ли тема в базе?
	/** @return true if given date/cowNum combination is already in the db, false otherwise */
	public boolean isMarked(SQLiteDatabase db, String link) {
		
		/* The 3rd parameter is treated as SQL WHERE clause, ? are replaced by strings
		 * from the 4th parameter */
		Cursor cur = db.query(TABLE_NAME, null, "link = ?", new String[] {link}, null, null, null, null);
		if (cur != null && cur.getCount()>0) {
			// duplicate found
			cur.close();
			return true;
		}
		cur.close();
		return false;
	}


	// получить все данные из таблицы DB_TABLE
	public Cursor getAllData(SQLiteDatabase db) {
		return db.query(TABLE_NAME, null, null, null, null, null, null);
	}
	
}
