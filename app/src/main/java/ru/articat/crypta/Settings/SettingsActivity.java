package ru.articat.crypta.Settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import java.util.Set;

import ru.articat.crypta.R;
import ru.articat.crypta.Util.TypeFaceUtil;

public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

	// callback на изменение в настройках
	@Override
	public void onSharedPreferenceChanged(SharedPreferences p1, String p2) {
		// TODO: Implement this method
		summaryChange();
	}

//	int sTheme;
private MultiSelectListPreference multiSelectListPref;
//	int widgetID = AppWidgetManager.INVALID_APPWIDGET_ID;
//	Intent resultValue;

	protected void onCreate(Bundle savedInstanceState) {
		TypeFaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Condensed.ttf");
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.app_preferences);
	//	setContentView(R.layout.pref_layout);
	
		// Регистрируем OnSharedPreferenceChangeListener
		// чтобы настройки сразу вступали в силу
        Context context = getApplicationContext();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        	prefs.registerOnSharedPreferenceChangeListener(this);

		summaryChange();
	
		}

	
	private void summaryChange(){
		
		multiSelectListPref = (MultiSelectListPreference) findPreference("notify_list");
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		Set<String> selections = sharedPrefs.getStringSet("notify_list", null);
		String[] selected = selections.toArray(new String[] {});
		
		String st="";
		for(int i=0; i<selected.length; i++){
			st=st+selected[i].replace("\"", "")+" ";
		}
		multiSelectListPref.setSummary(st);
		
		ListPreference listPreference = (ListPreference) findPreference("key_theme");
        if(listPreference.getValue()==null) {
            // to ensure we don't get a null value
            // set first value by default
            listPreference.setValueIndex(0);
        }
        listPreference.setSummary(listPreference.getEntry().toString());
	}
	
	
	public static void changeToTheme(Activity activity)//, int theme)
     {
         // sTheme = theme;
          activity.finish();
		activity.startActivity(new Intent(activity, activity.getClass()));
     }


}
