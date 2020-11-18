package ru.articat.crypta.DataBases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;

import ru.articat.crypta.Settings.Constants;
import ru.articat.crypta.Settings.DoPreferences;



public class DBHelperForumList extends SQLiteOpenHelper{

	private static final String DATABASE_NAME = "CryptaForumsDB.db_";
	private static final int DATABASE_VERSION = 1;
	public static final String TABLE_NAME = "forumlinks";
	public static final String COLUMN_ID = "id";
	public static final String FORUM_LINK = "forum_link";
	public static final String FORUM_NAME = "forum_name";
	public static final String TOTAL_THEMES = "total_themes";
	public static final String STAR = "star";
	
	private Context context;
	private String TAG = Constants.TAG;
	
	public DBHelperForumList(Context context){
        super(context, DATABASE_NAME+new DoPreferences(context).loadData(Constants.LOGIN, ""), null, DATABASE_VERSION);
		this.context=context;
    }
	
	
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		// TODO: Implement this method
		db.execSQL(
			"create table "+ TABLE_NAME +
			"("+ COLUMN_ID +" integer primary key,"+FORUM_LINK+" text,"+FORUM_NAME+" text,"+TOTAL_THEMES+" integer,"+STAR+" text"+")");
	}
	

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		// TODO: Implement this method
		db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
		onCreate(db);
	}

	public boolean insertLinks  (SQLiteDatabase db, String link, String title, int total, String star)
	{
	//	SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(FORUM_LINK, link);
		contentValues.put(FORUM_NAME, title);
		contentValues.put(TOTAL_THEMES, total);
		contentValues.put(STAR, star);
		
		db.insert(TABLE_NAME, null, contentValues);
	//	db.close();
		return true;
	}
	
	public boolean updateTotal  (SQLiteDatabase db, String id, int total)
	{
	//	SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		
		contentValues.put(TOTAL_THEMES, total);
		
		db.update(TABLE_NAME, contentValues, COLUMN_ID+ "= ? ", new String[] { id } );
	//	db.close();
		return true;
	}
	
	public Cursor getData(SQLiteDatabase db, int id){
	//	SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur =  db.rawQuery( "select * from " + TABLE_NAME + " where "+ COLUMN_ID+" = "+id, null );
		cur.close();
		return cur;
	}
	
	public String getForumLink(SQLiteDatabase db, String id){
		String rq="select * from " + TABLE_NAME + " where "+ COLUMN_ID+" = "+id;
		Cursor cur =  db.rawQuery( rq, null );
		cur.moveToFirst();
		String res=cur.getString(cur.getColumnIndex(FORUM_LINK));
		//	cur.close();
		return res;
	}
	
	public String getForumName(SQLiteDatabase db, String id){
		String rq="select * from " + TABLE_NAME + " where "+ COLUMN_ID+" = "+id;
		Cursor cur =  db.rawQuery( rq, null );
		cur.moveToFirst();
		String res=cur.getString(cur.getColumnIndex(FORUM_NAME));
		//	cur.close();
		return res;
	}
	
	public String getTotalThemes(SQLiteDatabase db, String id){
		String rq="select * from " + TABLE_NAME + " where "+ COLUMN_ID+" = "+id;
		Cursor cur =  db.rawQuery( rq, null );
		cur.moveToFirst();
		String res=cur.getString(cur.getColumnIndex(TOTAL_THEMES));
	//	cur.close();
		return res;
		
		/*
		String selecttSqlStr2 = "SELECT * FROM Tablename  WHERE TRIM(QST_Id) = '" + _id;

Cursor c = datasource.execSelectSQL(selecttSqlStr);//datasource is object for opening database
c.moveToFirst();
String name=c.getString(c.getColumnIndex("name"));
		*/
	}
	
//	public boolean isStar(String link){
//		SQLiteDatabase db = this.getReadableDatabase();
//		Cursor cur =  db.rawQuery( "select "+ STAR+ " from " + TABLE_NAME + " where "+ FORUM_LINK+" = "+link, null );
//		Log.i(TAG, "res.toString()= "+ cur.toString());
//		if(cur.toString().equals("true")) {
//			cur.close();
//			db.close();
//			return true;
//		}
//		cur.close();
//		db.close();
//		return false;
//	}
	
	public boolean updateLinks (SQLiteDatabase db, String link, String title, int total, String star)
	{
		Log.i(TAG, "updateLinks");
	//	SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(FORUM_LINK, link);
		contentValues.put(FORUM_NAME, title);
		contentValues.put(TOTAL_THEMES, total);
		contentValues.put(STAR, star);
	
		db.update(TABLE_NAME, contentValues, FORUM_LINK+ "= ? ", new String[] { link } );
	//	db.close();
		return true;
	}
	
	public void deleteDb(Context c){
		
		if(doesDatabaseExist(c)){
			c.deleteDatabase(DATABASE_NAME+new DoPreferences(context).loadData(Constants.LOGIN, ""));
		}
	}


	private static boolean doesDatabaseExist(Context context) {
		File dbFile = context.getDatabasePath(DATABASE_NAME+new DoPreferences(context).loadData(Constants.LOGIN, ""));
		return dbFile.exists();
	}
	
	public boolean isDbFilled(SQLiteDatabase db, Context context){
		// проверка существования записей
		Log.w(Constants.TAG, "isDbFilled");
	//	SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.query(TABLE_NAME, null, null, null, null, null, null);
		if (cur.getCount() != 0) {
		//	записи есть
			Log.w(Constants.TAG, "true");
			cur.close();
		//	db.close();
			return true;
		}
		Log.w(Constants.TAG, "false");
		cur.close();
	//	db.close();
		return false;
	}
	
//	public int getRowId(Context c, String link){
//		int id=0;
//		SQLiteDatabase db = this.getReadableDatabase();
//		Cursor cur=db.rawQuery("SELECT "+COLUMN_ID+", * FROM "+TABLE_NAME+" WHERE "+FORUM_LINK+" = "+link, null);
//		id=Integer.parseInt(cur.toString());
//		return id;
//	}
	
}
