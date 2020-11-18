//package ru.articat.nicy.Settings;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//
//import java.lang.reflect.Field;
//
//import ru.articat.nicy.R;
//
//public class ThemesChanger
//{
//	public final static int THEME_FIRST = 1;
//	public final static int THEME_SECOND = 2;
//	public final static int THEME_THIRD = 3;
//	public final static int THEME_FORTH = 4;
//
//	Context context;
//
//	public static void changeToTheme(Activity activity)
//	{
//		activity.finish();
//		activity.startActivity(new Intent(activity, activity.getClass()));
//	}
//
//// Применяем выбранную тему
//	public static void onActivityCreateSetTheme(Activity activity, int sT)
//	{
//		switch (sT)
//		{
//			case THEME_FIRST:
//				activity.setTheme(R.style.Theme_First);
//				break;
//			case THEME_SECOND:
//				activity.setTheme(R.style.Theme_Second);
//				break;
//
//			case THEME_THIRD:
//				activity.setTheme(R.style.Theme_Third);
//				break;
//
//			case THEME_FORTH:
//				activity.setTheme(R.style.Theme_Forth);
//				break;
//
//
//
//			default:
//				break;
//		}
//	}
//
//	public int getColorByName(Context context, String colorName, int theme){
//
//		this.context=context;
////		SharedPreferences sharedPrefs = PreferenceManager
////			.getDefaultSharedPreferences(context);
////		int THEMES = Integer.parseInt(sharedPrefs.getString("select_themes_list", "1"));
//		int returnedColor=R.color.text_color_dark_1;
////		switch(colorName){
////			case "primary":
////				Log.d(TAG, "case primary, theme= "+theme);
//				try {
//					Class res = R.color.class;
//					Field field = res.getField( colorName+theme );
//				//	Log.d(TAG, field.toString());
//					returnedColor = field.getInt(null);
//				} catch ( Exception e ) {
//					e.printStackTrace();
//				}
////			//	returnedColor=context.getResources().getIdentifier("primary_color"+String.valueOf(theme), "color", context.getPackageName());
////				break;
////
////			case "secondary":
////				Log.d(TAG, "case secondary, theme= "+theme);
////				try {
////					Class res = R.color.class;
////					Field field = res.getField( "secondary_color1" );
////					returnedColor = field.getInt(null);
////				} catch ( Exception e ) {
////					e.printStackTrace();
////				}
////			//	returnedColor=context.getResources().getIdentifier("secondary_color"+String.valueOf(theme), "color", context.getPackageName());
////				break;
////		}
////		Log.d(TAG, "secondary_2: "+context.getResources().getColor(R.color.primary_color2));
////		Log.w(Constants.TAG, "returnedColor: "+returnedColor);
//		return context.getResources().getColor(returnedColor);
//	}
//
//}
