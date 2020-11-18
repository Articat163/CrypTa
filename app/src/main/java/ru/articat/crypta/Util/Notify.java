//package ru.articat.crypta.Util;
//
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.res.Resources;
//import android.graphics.BitmapFactory;
//import android.preference.PreferenceManager;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Set;
//
//import ru.articat.crypta.R;
//import ru.articat.crypta.Settings.Constants;
//
////import ru.articat.nicyinformer.*;
////import ru.articat.nicyinformer.Fragments.*;
////import ru.articat.nicyinformer.AppSettings.*;
//
//public class Notify {
//
//	private final Context context;
//	private final int notifyId;
////	String message;
//private final Resources res;
////	int appWidgetId;
//
//	public Notify(Context c, int id) {
//		this.context = c;
//	//	this.appWidgetId = widId;
//		this.notifyId = id;
//		//	this.message=msg;
//
//		res = context.getResources();
//		getVibrateNotification();
//	}
//
//	private void getVibrateNotification() {
//		Intent notificationIntent = new Intent(context, AllMessages.class);
//	//	notificationIntent.putExtra("widgetID", "");
//		notificationIntent.putExtra("page", 0); // страница сообщений
//        PendingIntent contentIntent = PendingIntent.getActivity(context,
//																0, notificationIntent,
//																PendingIntent.FLAG_CANCEL_CURRENT);
//
//
//        Notification.Builder builder = new Notification.Builder(context);
//
//		Constants.NOTIFICATION_ID=notifyId;
//
//        builder.setContentIntent(contentIntent)
//			.setSmallIcon(R.drawable.logo_alpha_mini)
//			// большая картинка
//			.setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.logo_alpha_mini))
//			//.setTicker(res.getString(R.string.warning)) // текст в строке состояния
//			.setTicker(res.getString(R.string.app_name)+" : "+res.getString(R.string.title_notify_new_message))
//			.setWhen(System.currentTimeMillis())
//			.setAutoCancel(true)
//			//.setContentTitle(res.getString(R.string.notifytitle)) // Заголовок уведомления
//			.setContentTitle(res.getString(R.string.app_name))
//			//.setContentText(res.getString(R.string.notifytext))
//			.setContentText(res.getString(R.string.title_notify_new_message)); // Текст уведомления
//
//		SharedPreferences sharedPrefs = PreferenceManager
//			.getDefaultSharedPreferences(context);
//	//	MultiSelectListPreference multiSelectListPref = (MultiSelectListPreference) findPreference("notify_list");
//
//
////		// если в настройках включены уведомления
//		if (sharedPrefs.getBoolean("key_notify_on_off", true)) {
//			Set<String> selections = sharedPrefs.getStringSet("notify_list", null);
//			String[] selected = new String[0];
//			if (selections != null) {
//				selected = selections.toArray(new String[] {});
//			}
//			List myList = new ArrayList();
//			Collections.addAll(myList, selected);
//		int defaults = Notification.DEFAULT_VIBRATE;
//
//		//	 VIBRATE
//			if(myList.contains("вибро")) defaults = defaults | Notification.DEFAULT_VIBRATE;//builder.setVibrate(new long[] {75,525,75,25,75,25,75,25,75,25,75,25,75,25,75,225,75,25,75,25,75,25,75,225,75,25,75,25,75,25,75,525});
//
//		//	 LED
//			if(myList.contains("индикатор")) defaults = defaults | Notification.FLAG_SHOW_LIGHTS;
//
//		//	 SOUND
//			if(myList.contains("звук")) defaults = defaults | Notification.DEFAULT_SOUND;
//
//			builder.setDefaults(defaults);
//		}
//
//         Notification notification = builder.getNotification(); // до API 16
//
//        NotificationManager notificationManager = (NotificationManager) context
//			.getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(notifyId, notification);
//
//	}
//
//}
