package ru.articat.crypta.Settings;

import android.content.Context;
import android.util.Log;

import ru.articat.crypta.DataBases.DBHelper;

import static ru.articat.crypta.Settings.Constants.TAG;

class DeleteMessages{
	
	private Context context;
	private int rowId;
	private DBHelper dbHelper;
//	int widgetID;

	public DeleteMessages(Context context, int id) {
		Log.i(TAG, "DeleteMessages.class");
		this.context = context;
		this.rowId=id;
	//	this.widgetID=widgetId;
		
		dbHelper=new DBHelper(context);
	}
	
	
	public void fromBase(){
	//	for(int i=0; i<=arrayOfRowId.size()-1; i++){
			dbHelper.deleteRowById(rowId);
	//	}
	}
	
}
