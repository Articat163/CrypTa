package ru.articat.crypta.Util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.articat.crypta.Settings.Constants.TAG;


public class Converter {

	public String dateToSqlFormat(String oldDate) {
		Date date=null;
		if (oldDate.length() < 12) oldDate = "0" + oldDate;
		SimpleDateFormat fmt = new SimpleDateFormat("ddMMyyyyHHmm");
		try
		{
			date = fmt.parse(oldDate);
		}
		catch (ParseException e)
		{
			Log.e(TAG, "Converter.dateToSqlFormat " + String.valueOf(e));}

		SimpleDateFormat fmtOut = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return fmtOut.format(date);

	}

	public String dateToRussFormat(String sqlDate) {

		Date date=null;

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try
		{
			date = fmt.parse(sqlDate);
		}
		catch (ParseException e)
		{}

		SimpleDateFormat fmtOut = new SimpleDateFormat("dd MMMM yyyy  HH:mm");
		return fmtOut.format(date);
	}


	public Drawable getDrawable(String bitmapUrl) {
		try
		{
			URL url = new URL(bitmapUrl);
			Drawable d =new BitmapDrawable(BitmapFactory.decodeStream(url.openConnection().getInputStream()));
			return d; 
		}
		catch (Exception ex)
		{return null;}
    }



	/** вычисляем суррогатные пары и преобразуем в web hex */

	public static String stringToServer(String input) {
		String unicode;
		if (input == null) return null;
		StringBuilder sb = new StringBuilder();
		//	sb.append(" ");
		Log.i(TAG, "input " + input);
		Log.i(TAG, "input.length= " + input.length());
		for (int i = 0; i < input.length(); i++) {

			if (i < (input.length() - 1)) { // Emojis are two characters long in java, e.g. a rocket emoji is "\uD83D\uDE80";

				if (Character.isSurrogatePair(input.charAt(i), input.charAt(i + 1))) {

					unicode = "\\u" + Integer.toHexString(input.charAt(i)) + "\\u" + Integer.toHexString(input.charAt(i + 1));
					String hi=Integer.toHexString(input.charAt(i));
					String low=Integer.toHexString(input.charAt(i + 1));
					Log.i(TAG, "unicode: " + unicode);

					// C = (H - 0xD800) * 0x400 + L - 0xDC00 + 0x10000
					int summ = ((Integer.parseInt(hi, 16) - Integer.parseInt("D800", 16)) * Integer.parseInt("400", 16) + Integer.parseInt(low, 16) - Integer.parseInt("DC00", 16) + Integer.parseInt("10000", 16));
					Log.i(TAG, "summ: " + Integer.toHexString(summ));
					sb.append("&#x" + Integer.toHexString(summ));

					unicode = "";
					i += 1;
				}
				else {

					//	sb.append("&#x"+Integer.toHexString(input.charAt(i)));
					if (Character.UnicodeBlock.of(input.charAt(i)).equals(Character.UnicodeBlock.CYRILLIC))
					{
						// contains Cyrillic
						sb.append("&#x" + Integer.toHexString(input.charAt(i)));
					}
					else sb.append(input.charAt(i));
				} // end if(Character)
			} 

			else {

				//	sb.append("&#x"+Integer.toHexString(input.charAt(i)));
				if (Character.UnicodeBlock.of(input.charAt(i)).equals(Character.UnicodeBlock.CYRILLIC))
				{
					// contains Cyrillic
					sb.append("&#x" + Integer.toHexString(input.charAt(i)));
				}
				else sb.append(input.charAt(i));
			} // end if(input.length)
		} // end for

		return sb.toString();
	} 

//	public boolean chechCyrillic(){
//		
//		return false;
//	}


	// Convert Dp to Pixel
	public static int dpToPx(float dp, Resources resources) {
		//	float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
		float scale = resources.getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
//		DisplayMetrics metrics = resources.getDisplayMetrics();
//		float px = dp * (metrics.densityDpi / 160f);
		//	return (int) px;
//		return (int) (dp/resources.getDisplayMetrics().density);
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {
		Bitmap bitmap = null;

		if (drawable instanceof BitmapDrawable) {
			BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
			if (bitmapDrawable.getBitmap() != null) {
				return bitmapDrawable.getBitmap();
			}
		}

		if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
			bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
		}
		else {
			bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
		}

		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	/** Получаем ID видео youtube из адреса */

	static String extractYTId(String ytUrl) {
		String vId = null;
		Pattern pattern = Pattern.compile(
				"^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(ytUrl);
		if (matcher.matches()){
			vId = matcher.group(1);
		}
		return vId;
	}

	public String convertCyrillic(String text){
		String converted = "";
//		Pattern pattern = Pattern.compile(
//		        "//d+",
//				Pattern.CASE_INSENSITIVE);
//		Matcher matcher = pattern.matcher(converted);
		String regText = text.replaceAll(".+/\\d+|\\-|\\/\\?.+", " ");
		try {
			converted = URLDecoder.decode(regText.trim(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
//		try {
//			String s = new String("Æàìáûë".getBytes(StandardCharsets.ISO_8859_1), "Windows-1251");
//			Files.write(Paths.get("C:/cyrillic.txt"),
//					("\uFEFF" + s).getBytes(StandardCharsets.UTF_8));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return converted;
	}

}
