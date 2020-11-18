package ru.articat.crypta.Util;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

import ru.articat.crypta.R;
import ru.articat.crypta.Settings.Constants;

import static ru.articat.crypta.Settings.Constants.TAG;

public class SaveBitmap extends AsyncTask<Void, Void, Void> {

	private Context context;
//	public Bitmap photo=null;
	private String url;
//	private int ID;
	private boolean showDialog=false;
	private ProgressDialog pd;
	private String fname;
//	GetSendMessagesInterface gsmi;

	public SaveBitmap(Context cnt, String url, String imgName, int Id, boolean showDialog){
		this.context=cnt;
		this.showDialog=showDialog;
		this.url=url;
//		this.ID=Id;
		fname = imgName+Id+".jpg";
	//	this.gsmi= (GetSendMessagesInterface)cnt;
	}

	public SaveBitmap(Context cnt, String url, String imgName, boolean showDialog){
		this.context=cnt;
		this.showDialog=showDialog;
		this.url=url;

		fname = imgName;
	}

	public SaveBitmap(Context cnt){
		this.context=cnt;
		this.showDialog=false;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if(showDialog) LoadDialog(context.getResources().getString(R.string.getting_data)+"-2");
	}


	@Override
	protected Void doInBackground(Void[] p1) {
		Log.d(TAG, "**** SAVE BITMAP doInBackground ");
		// TODO: Implement this method


//			Picasso.with(context)
//				.load(urls)
//				.into(target);
//
//		ImageLoader imageLoader;
//		imageLoader = ImageLoader.getInstance(); // Получили экземпляр
//		imageLoader.init(ImageLoaderConfiguration.createDefault(context)); // Проинициализировали конфигом по умолчанию
//		imageLoader.loadImage(url, new SimpleImageLoadingListener()
//			{
//				@Override
//				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
//				{
//					toSDCard(loadedImage, fname);
					// Do whatever you want with Bitmap
					//	toSDCard(loadedImage);
//					String root = Environment.getExternalStorageDirectory().toString();
//					File myDir = new File(root + Constants.FILENAME);
//					myDir.mkdirs();
//
//					//	String fname = Constants.IMAGENAME+Id+".jpg";
//					File file = new File (myDir, fname);
//					if (file.exists ()) file.delete ();
//					try {
//						FileOutputStream out = new FileOutputStream(file);
//						loadedImage.compress(Bitmap.CompressFormat.JPEG, 90, out);
//						out.flush();
//						out.close();
//						Log.e(TAG, "SaveBitmap complete");
//					}
//					catch (Exception e) {
//						e.printStackTrace();
//						Log.e(TAG, "SaveBitmap toSdCard Exeption: "+ e);
//					}
					//	photo=loadedImage;
					//	photo.setImageBitmap(loadedImage);

//				}
//			});


		return null;
	}


	@Override
	protected void onPostExecute(Void v) {
		if(showDialog){
			if (pd.isShowing()) {
				pd.dismiss();
			}
		}
	}


	public void toSDCard(Bitmap finalBitmap, String imageName){
	String root = Environment.getExternalStorageDirectory().toString();
	File myDir = new File(root + Constants.FILENAME);
	myDir.mkdirs();

	//	String fname = Constants.IMAGENAME+Id+".jpg";
		File file = new File (myDir, imageName);
		if (file.exists ()) file.delete ();
			try {
				FileOutputStream out = new FileOutputStream(file);
				finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
				out.flush();
				out.close();
				// если messageActivity открыто, то обновляем listview
			//	if(Constants.MESSAGESRUNING) new MessageActivity().updateActivity();
			}
			catch (Exception e) {
				e.printStackTrace();
				Log.e(TAG, "SaveBitmap toSdCard Exeption: "+ e);
			}
		}


//	@Override
private void LoadDialog(String caption)
	{
		pd = new ProgressDialog(context);
		pd.setTitle(caption);
		pd.setMessage(context.getResources().getString(R.string.wait));
		pd.show();
	}

}
