package ru.articat.crypta;

import android.content.*;
import android.graphics.drawable.*;
import android.text.*;

public class ImageGetter implements Html.ImageGetter
{
    private final Context c;

    public ImageGetter(Context c)
    {
        this.c = c;
    }
    public Drawable getDrawable(String source) {
        Drawable d = null;
        int resID = c.getResources().getIdentifier(source, "drawable", c.getPackageName());

        d = c.getApplicationContext().getResources().getDrawable(resID);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());

        return d;
    }
	
}
