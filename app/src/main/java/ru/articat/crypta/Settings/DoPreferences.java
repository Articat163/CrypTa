package ru.articat.crypta.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;

import java.io.File;

//import ru.articat.nicyinformer.util.*;

public class DoPreferences{
	
	private Context context;
	private SharedPreferences sp;
	private SharedPreferences.Editor edit;
//	SecurePreferences sp;
	
	public DoPreferences(Context c){
		this.context=c;
	//	sp=new SecurePreferences(context, Constants.WIDGET_PREF, "NisySecureKey", true);
		sp = context.getSharedPreferences(Constants.NICY_PREF, Context.MODE_PRIVATE);
		edit=sp.edit();
	}
	
	
	public void saveData(String key, String value){
		edit.putString(key, value);
		edit.commit();
	}
	
	
	public String loadData(String key, String value){
		String v=sp.getString(key, value);
		return v;
	}
	
	
	public void clearData(){
		SharedPreferences.Editor editor = context.getSharedPreferences(
			Constants.NICY_PREF, Context.MODE_PRIVATE).edit();
//
		editor.remove(Constants.LOGIN);
		editor.remove(Constants.PASSWORD);
		editor.remove(Constants.USERNAME);
		editor.remove(Constants.USERLINK);
		editor.remove(Constants.USERPHOTOLINK);
	//	editor.remove(Constants.COOKIESVALUE);
		editor.remove(Constants.USERCITY);
		editor.remove(Constants.REMEMBER);
	//	editor.remove(Constants.VALIDWIDGET);
	//	editor.remove(Constants.UNREADMESSAGES);
		editor.remove(Constants.PINCODE);
		editor.remove(Constants.USERID);
	//	editor.remove(Constants.UNDERSTANDSYNC);
	//	editor.remove(Constants.UNDERSTANDDELETE);
	//	editor.remove(Constants.WIDGETTYPE);
		
		editor.commit();
		
		String root = Environment.getExternalStorageDirectory().toString();
		File file = new File(root+Constants.FILENAME+"/"+Constants.IMAGENAME+".jpg");
		file.delete();
		Log.w(Constants.TAG, "FileDelete path: "+root+Constants.FILENAME+"/"+Constants.IMAGENAME+".jpg");
		// widgetID
	}
}
