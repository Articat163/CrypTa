package ru.articat.crypta.Util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import ru.articat.crypta.R;

import static ru.articat.crypta.Settings.Constants.TAG;

public class DoCrop{

    private int dest;
	
	private Activity act;

    public DoCrop(Activity act){
		this.act=act;
	}
	
	public DoCrop(Activity act, int dest/*, int outputY, int aspectX, int aspectY*/){
		this.act=act;
        this.dest=dest;
//		this.aspectX=aspectX;
//		this.aspectY=aspectY;
//		this.outputX=outputX;
//		this.outputY=outputY;
	}
	
	public void performCrop(String picUri){//, int outputX, int outputY, int aspectX, int aspectY) {
        try {

//            Log.i(TAG, "ImageWidth: "+ new ImageTools().getImageWidth(picUri));
//            Log.i(TAG, "ImageHeight: "+ new ImageTools().getImageHeight(picUri));

            int iWidth = new ImageTools().getImageWidth(picUri);
            int iHeight = new ImageTools().getImageHeight(picUri);
            Log.i(TAG, "iWidth: "+ iWidth);
            Log.i(TAG, "iHeight: "+iHeight);
            int aspectX=0;
            int aspectY=0;
            int outputX;
            int outputY;
            if (iWidth >= iHeight){
                outputX =dest;
                double tempY=(double)dest/(double)iWidth*(double)iHeight;
                outputY =(int)tempY;
//                outputY=(480/iWidth)*iHeight;
//                aspectX =0;
//                aspectY =0;
//                aspectY=iHeight/iWidth;
            }
            else{
                outputY =dest;
                double tempX=(double)dest/(double)iHeight*(double)iWidth;
                outputX =(int)tempX;
//                outputX=(480/iHeight)*iWidth;
//                Log.i(TAG, "tempX="+tempX);
//                Log.i(TAG, "(int)tempX="+outputX);
//                aspectY =0;
//                aspectX =0;
//                aspectX=(double)iWidth/(double)iHeight;
            }

            Log.i(TAG, "outputY:"+outputY);
            Log.i(TAG, "outputX:"+outputX);
            Log.i(TAG, "aspectY:"+aspectY);
            Log.i(TAG, "aspectX:"+aspectX);

            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            File f = new File(picUri);
            Uri contentUri = Uri.fromFile(f);

            cropIntent.setDataAndType(contentUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", true);
			cropIntent.putExtra("scale", true);
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", aspectX);// 1);
            cropIntent.putExtra("aspectY", aspectY);// 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", outputX);// 280);
            cropIntent.putExtra("outputY", outputY);// 280);

            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            int RESULT_CROP = 400;
            Log.i(TAG, "RESULT_CROP: "+RESULT_CROP);
            act.startActivityForResult(cropIntent, RESULT_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
//            String errorMessage = act.getString(R.string.crop_error);
            Toast toast = Toast.makeText(act, act.getString(R.string.crop_error), Toast.LENGTH_SHORT);
            toast.show();
        }
    }   
}
