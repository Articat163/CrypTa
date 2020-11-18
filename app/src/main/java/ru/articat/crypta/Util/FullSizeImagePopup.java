package ru.articat.crypta.Util;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import ru.articat.crypta.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static ru.articat.crypta.Settings.Constants.TAG;


/**
 * Created by Юрий on 11.10.2017.
 */

public class FullSizeImagePopup {

    Context cnt;

    public FullSizeImagePopup(Context c){
        this.cnt=c;
    }

    public void showImage(int layoutID, String fotoUrl, boolean privatePhoto){
        Log.i(TAG, "FullSizeImagePopup. fotoUrl: "+ fotoUrl);
        try {
            LayoutInflater layoutInflater = (LayoutInflater)cnt.getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = layoutInflater.inflate(R.layout.full_image_popup, null);
            final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//								View parent = view.findViewById(R.layout.fragment_profile);
            View layout = layoutInflater.inflate(layoutID, null);
            popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);

            if(privatePhoto) {
                TextView tvPrivate = (TextView)popupView.findViewById(R.id.textViewPrivate);
                tvPrivate.setVisibility(View.VISIBLE);
            }

            // кнопка закрыть окно
            ImageView fullImageView = (ImageView)popupView.findViewById(R.id.full_imageView);
            ImageButton ibClose = (ImageButton)popupView.findViewById(R.id.imageButton_close);
            ibClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
            final ProgressBar i_progressBar=(ProgressBar)popupView.findViewById(R.id.full_progressBar);
            Glide.with(cnt)
                    .load(fotoUrl)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            i_progressBar.setVisibility(View.GONE);
                            Log.e(TAG, "Fragment Profile. Glide onException: "+e);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            i_progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .error(R.drawable.no_img)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(fullImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
