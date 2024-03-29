// THIS IS A BETA! I DON'T RECOMMEND USING IT IN PRODUCTION CODE JUST YET

/*
 * Copyright 2012 Roman Nurik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.articat.crypta.Util.Swipes;

import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
//import com.nineoldandroids.animation.Animator;
//import com.nineoldandroids.animation.AnimatorListenerAdapter;
//import com.nineoldandroids.animation.ValueAnimator;
//
//import static com.nineoldandroids.view.ViewHelper.setAlpha;
//import static com.nineoldandroids.view.ViewHelper.setTranslationX;
//import static com.nineoldandroids.view.ViewPropertyAnimator.animate;
import android.animation.*;

/**
 * A {@link android.view.View.OnTouchListener} that makes any {@link View} dismissable when the
 * user swipes (drags her finger) horizontally across the view.
 *
 * <p><em>For {@link android.widget.ListView} list items that don't manage their own touch events
 * (i.e. you're using
 * {@link android.widget.ListView#setOnItemClickListener(android.widget.AdapterView.OnItemClickListener)}
 * or an equivalent listener on {@link android.app.ListActivity} or
 * {@link android.app.ListFragment}, use {@link SwipeDismissListViewTouchListener} instead.</em></p>
 *
 * <p>Example usage:</p>
 *
 * <pre>
 * view.setOnTouchListener(new SwipeDismissTouchListener(
 *         view,
 *         null, // Optional token/cookie object
 *         new SwipeDismissTouchListener.OnDismissCallback() {
 *             public void onDismiss(View view, Object token) {
 *                 parent.removeView(view);
 *             }
 *         }));
 * </pre>
 *
 * <p>This class Requires API level 12 or later due to use of {@link
 * android.view.ViewPropertyAnimator}.</p>
 *
 * @see SwipeDismissListViewTouchListener
 */
public class SwipeDismissTouchListener implements View.OnTouchListener {
    // Cached ViewConfiguration and system-wide constant values
    private int mSlop;
    private long mAnimationTime;

    // Fixed properties
    private View mView;
    private DismissCallbacks mCallbacks;
    private int mViewWidth = 1; // 1 and not 0 to prevent dividing by zero

    // Transient properties
    private float mDownX;
    private float mDownY;
    private boolean mSwiping;
    private int mSwipingSlop;
    private Object mToken;
    private VelocityTracker mVelocityTracker;
    private float mTranslationX;

    /**
     * The callback interface used by {@link SwipeDismissTouchListener} to inform its client
     * about a successful dismissal of the view for which it was created.
     */
    public interface DismissCallbacks {
        /**
         * Called to determine whether the view can be dismissed.
         */
        boolean canDismiss(Object token);

        /**
         * Called when the user has indicated they she would like to dismiss the view.
         *
         * @param view  The originating {@link View} to be dismissed.
         * @param token The optional token passed to this object's constructor.
         */
        void onDismiss(View view, Object token);
    }

    /**
     * Constructs a new swipe-to-dismiss touch listener for the given view.
     *
     * @param view     The view to make dismissable.
     * @param token    An optional token/cookie object to be passed through to the callback.
     * @param callbacks The callback to trigger when the user has indicated that she would like to
     *                 dismiss this view.
     */
    public SwipeDismissTouchListener(View view, Object token, DismissCallbacks callbacks) {
        ViewConfiguration vc = ViewConfiguration.get(view.getContext());
        mSlop = vc.getScaledTouchSlop();
        int mMinFlingVelocity = vc.getScaledMinimumFlingVelocity() * 16;
        int mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity();
        mAnimationTime = view.getContext().getResources().getInteger(
			android.R.integer.config_shortAnimTime);
        mView = view;
        mToken = token;
        mCallbacks = callbacks;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        // offset because the view is translated during swipe
        motionEvent.offsetLocation(mTranslationX, 0);

        if (mViewWidth < 2) {
            mViewWidth = mView.getWidth();
        }

        switch (motionEvent.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
					// TODO: ensure this is a finger, and set a flag
					mDownX = motionEvent.getRawX();
					mDownY = motionEvent.getRawY();
					if (mCallbacks.canDismiss(mToken)) {
						mVelocityTracker = VelocityTracker.obtain();
						mVelocityTracker.addMovement(motionEvent);
					}
					return false;
				}

			case MotionEvent.ACTION_MOVE: {
					if (mVelocityTracker == null) {
						break;
					}

					mVelocityTracker.addMovement(motionEvent);
					float deltaX = motionEvent.getRawX() - mDownX;
					float deltaY = motionEvent.getRawY() - mDownY;
					if (Math.abs(deltaX) > mSlop && Math.abs(deltaY) < Math.abs(deltaX) / 2) {
						mSwiping = true;
						mSwipingSlop = (deltaX > 0 ? mSlop : -mSlop);
						mView.getParent().requestDisallowInterceptTouchEvent(true);

						// Cancel listview's touch
						MotionEvent cancelEvent = MotionEvent.obtain(motionEvent);
						cancelEvent.setAction(MotionEvent.ACTION_CANCEL |
											  (motionEvent.getActionIndex() <<
											  MotionEvent.ACTION_POINTER_INDEX_SHIFT));
						mView.onTouchEvent(cancelEvent);
						cancelEvent.recycle();
					}

					if (mSwiping) {
						mTranslationX = deltaX;
						mView.setTranslationX(deltaX - mSwipingSlop);
						// TODO: use an ease-out interpolator or such
						mView.setAlpha(Math.max(0f, Math.min(1f,
															 1f - 2f * Math.abs(deltaX) / mViewWidth)));
						return true;
					}
					break;
				}
        }
        return false;
    }

    private void performDismiss() {
        // Animate the dismissed view to zero-height and then fire the dismiss callback.
        // This triggers layout on each animation frame; in the future we may want to do something
        // smarter and more performant.

        final ViewGroup.LayoutParams lp = mView.getLayoutParams();
        final int originalHeight = mView.getHeight();

        ValueAnimator animator = ValueAnimator.ofInt(originalHeight, 1).setDuration(mAnimationTime);

        animator.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					mCallbacks.onDismiss(mView, mToken);
					// Reset view presentation
					mView.setAlpha(1f);
					mView.setTranslationX(0);
					lp.height = originalHeight;
					mView.setLayoutParams(lp);
				}
			});

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
				@Override
				public void onAnimationUpdate(ValueAnimator valueAnimator) {
					lp.height = (Integer) valueAnimator.getAnimatedValue();
					mView.setLayoutParams(lp);
				}
			});

        animator.start();
    }
}
