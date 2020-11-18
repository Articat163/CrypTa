package ru.articat.crypta.Settings;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AppSettings{
	
//	public static String user_name;
//	public static String profile_link;
	public static String profile_photo_url;
	public static Bitmap profile_photo;
	public static boolean unlock=false;
//	public static Map<String, String> loginCookies;
private Context context;
	
	public AppSettings(){}
	
	public AppSettings(Context c){
		this.context=c;
	}
	
	public void getBitmapFromUrl(Context cnt, String url){
//		ImageLoader imageLoader;
//		imageLoader = ImageLoader.getInstance(); // Получили экземпляр
//		imageLoader.init(ImageLoaderConfiguration.createDefault(cnt)); // Проинициализировали конфигом по умолчанию
//		imageLoader.loadImage(url, new SimpleImageLoadingListener()
//			{
//				@Override
//				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
//				{
//					// Do whatever you want with Bitmap
//					profile_photo=loadedImage;
//
//				}
//			});
		
	}
	
	public void getBitmapFromURL(String src) {
		try {
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			profile_photo = myBitmap;
		} catch (IOException e) {
			// Log exception
			
		}
	}
}
