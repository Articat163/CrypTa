package ru.articat.crypta.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.ByteArrayOutputStream;

import static ru.articat.crypta.Settings.Constants.TAG;

public class DoThumbs{
	
	private byte[] imageData = null;
	private Bitmap imageBitmap;
	private Context context;
	
	public DoThumbs(Context ctx){
		this.context=ctx;
	}
	
	public Bitmap getImage(Bitmap photo){
		
	try {

//		final int THUMBNAIL_SIZE = (int)( Constants.DISPLAYHEIGHT-
//				context.getResources().getDimension(R.dimen.large_margin_dimen)+
//			context.getResources().getDimension(R.dimen.small_margin_dimen));
		
		final int THUMBNAIL_SIZE = 200;

//		FileInputStream fis = new FileInputStream(photoUri);
//		Bitmap imageBitmap = BitmapFactory.decodeStream(fis);

		imageBitmap = Bitmap.createScaledBitmap(photo, THUMBNAIL_SIZE, THUMBNAIL_SIZE, false);

		if (photo!= imageBitmap){
			photo.recycle();
		}
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		imageData = baos.toByteArray();

	}
	catch(Exception ex) {
		Log.e(TAG, String.valueOf(ex));
	}
		return imageBitmap;
	}
}
