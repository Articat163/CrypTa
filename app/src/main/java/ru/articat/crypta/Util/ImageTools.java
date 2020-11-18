package ru.articat.crypta.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Юрий on 22.09.2017.
 */

public class ImageTools {

    public int getImageWidth(String photoPath){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);
        return options.outWidth;
    }

    public int getImageHeight(String photoPath){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);
        return options.outHeight;
    }
}
