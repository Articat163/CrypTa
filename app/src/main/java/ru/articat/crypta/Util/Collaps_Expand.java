package ru.articat.crypta.Util;

import android.view.*;
import android.view.animation.*;
import android.widget.LinearLayout.*;

public class Collaps_Expand
{
	public static void expand(final View v) {
    v.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    final int targetHeight = v.getMeasuredHeight();

    // Older versions of android (pre API 21) cancel animations for views with a height of 0.
    v.getLayoutParams().height = 1;
    v.setVisibility(View.VISIBLE);
    Animation a = new Animation()
    {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            v.getLayoutParams().height = interpolatedTime == 1
                    ? LayoutParams.WRAP_CONTENT
                    : (int)(targetHeight * interpolatedTime);
            v.requestLayout();
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    };

    // 1dp/ms
    a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
    v.startAnimation(a);
}

public static void collapse(final View v) {
    final int initialHeight = v.getMeasuredHeight();

    Animation a = new Animation()
    {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            if(interpolatedTime == 1){
                v.setVisibility(View.GONE);
            }else{
                v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                v.requestLayout();
            }
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    };

    // 1dp/ms
    a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
    v.startAnimation(a);
}
}
