package ru.articat.crypta.Util;


import android.widget.*;
import android.view.*;
import android.content.*;
import android.util.*;
import android.widget.AbsListView.*;
import android.view.animation.*;



public class MySwipeList extends ListView implements OnScrollListener {

    public final static double NO_ZOOM = 1;
    public final static double ZOOM_X2 = 2;

    private ImageView mImageView;
    private int mDrawableMaxHeight = -1;
    private int mImageViewHeight = -1;
	private int mImageViewMaxHeight = 50;
    private int mDefaultImageViewHeight = 0;
	private double mZoomRatio;

    private interface OnOverScrollByListener {
        public boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                                    int scrollY, int scrollRangeX, int scrollRangeY,
                                    int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent);
    }

    private interface OnTouchEventListener {
        public void onTouchEvent(MotionEvent ev);
    }

    public MySwipeList(Context context, AttributeSet attrs,
					   int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public MySwipeList(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MySwipeList(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        mDefaultImageViewHeight = 0;
    }

    @Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		initViewsBounds(mZoomRatio);
	}

	@Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                                   int scrollY, int scrollRangeX, int scrollRangeY,
                                   int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        boolean isCollapseAnimation = false;

        isCollapseAnimation = scrollByListener.overScrollBy(deltaX, deltaY,
															scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX,
															maxOverScrollY, isTouchEvent)
			|| isCollapseAnimation;

        return isCollapseAnimation ? true : super.overScrollBy(deltaX, deltaY,
															   scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX,
															   maxOverScrollY, isTouchEvent);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        View firstView = (View) mImageView.getParent();
        // firstView.getTop < getPaddingTop means mImageView will be covered by top padding,
        // so we can layout it to make it shorter
        if (firstView.getTop() < getPaddingTop() && mImageView.getHeight() > mImageViewHeight) {
            mImageView.getLayoutParams().height = Math.max(mImageView.getHeight() - (getPaddingTop() - firstView.getTop()), mImageViewHeight);
            // to set the firstView.mTop to 0,
            // maybe use View.setTop() is more easy, but it just support from Android 3.0 (API 11)
            firstView.layout(firstView.getLeft(), 0, firstView.getRight(), firstView.getHeight());
            mImageView.requestLayout();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        touchListener.onTouchEvent(ev);
        return super.onTouchEvent(ev);
    }

    public void setParallaxImageView(ImageView iv) {
        mImageView = iv;
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    private void initViewsBounds(double zoomRatio) {
        if (mImageViewHeight == -1) {
            mImageViewHeight = mImageView.getHeight();
            if (mImageViewHeight <= 0) {
                mImageViewHeight = mDefaultImageViewHeight;
            }
            double ratio = ((double) mImageView.getDrawable().getIntrinsicWidth()) / ((double) mImageView.getWidth());

            mDrawableMaxHeight = (int) ((mImageView.getDrawable().getIntrinsicHeight() / ratio) * (zoomRatio > 1 ?
				zoomRatio : 1));
        }
    }

    public void setZoomRatio(double zoomRatio) {
    	mZoomRatio = zoomRatio;
    }

    private final OnOverScrollByListener scrollByListener = new OnOverScrollByListener() {
        @Override
        public boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                                    int scrollY, int scrollRangeX, int scrollRangeY,
                                    int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
			//	Log.i("nicyinfolog", "deltaY= "+deltaY+" maxOverScrollY= "+maxOverScrollY+" scrollRangeY= "+scrollRangeY);
            if (mImageView.getHeight() <= mDrawableMaxHeight && isTouchEvent) {
                if (deltaY < 0) {
                    if (mImageView.getHeight() - deltaY *2 >= mImageViewHeight) {
                        mImageView.getLayoutParams().height = mImageView.getHeight() - deltaY *2 < mDrawableMaxHeight ?
							mImageView.getHeight() - deltaY *2 : mDrawableMaxHeight;
                        mImageView.requestLayout();
                    }
                } else {
                    if (mImageView.getHeight() > mImageViewHeight) {
                        mImageView.getLayoutParams().height = mImageView.getHeight() - deltaY > mImageViewHeight ?
							mImageView.getHeight() - deltaY : mImageViewHeight;
                        mImageView.requestLayout();
                        return true;
                    }
                }
            }
            return false;
        }
    };

    private final OnTouchEventListener touchListener = new OnTouchEventListener() {
        @Override
        public void onTouchEvent(MotionEvent ev) {

            if (ev.getAction() == MotionEvent.ACTION_UP) {
				//	Log.i("nicyinfolog", "ACTION_UP");
                if (mImageViewHeight - 1 < mImageView.getHeight() /*&& ev.getAction() == MotionEvent.ACTION_DOWN*/) {
                    ResetAnimimation animation = new ResetAnimimation(
						mImageView, mImageViewHeight);
                    animation.setDuration(300);
                    mImageView.startAnimation(animation);
                }



            }


//			if(ev.getAction() == MotionEvent.ACTION_DOWN){
//				if (mImageViewMaxHeight - 1 < mImageView.getHeight() && ev.getAction() == MotionEvent.ACTION_DOWN) {
//                    ResetAnimimation animation = new ResetAnimimation(
//						mImageView, mImageViewMaxHeight);
//                    animation.setDuration(300);
//                    mImageView.startAnimation(animation);
//				}
//				
//			}
        }
    };

    public class ResetAnimimation extends Animation {
        final int targetHeight;
        final int originalHeight;
        final int extraHeight;
        final View mView;

        ResetAnimimation(View view, int targetHeight) {
            this.mView = view;
            this.targetHeight = targetHeight;
            originalHeight = view.getHeight();
            extraHeight = this.targetHeight - originalHeight;
        }

        @Override
        protected void applyTransformation(float interpolatedTime,
                                           Transformation t) {

            int newHeight;
            newHeight = (int) (targetHeight - extraHeight * (1 - interpolatedTime));
            mView.getLayoutParams().height = newHeight;
            mView.requestLayout();
        }
    }
}
