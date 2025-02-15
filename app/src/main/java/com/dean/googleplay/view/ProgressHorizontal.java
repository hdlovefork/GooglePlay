package com.dean.googleplay.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Process;
import android.text.TextUtils;
import android.view.View;
import android.widget.RemoteViews.RemoteView;

@RemoteView
public class ProgressHorizontal extends View {
	private static final int MAX_SMOOTH_ANIM_DURATION = 2000;
	private long mThreadId;
	private int mResBackground;
	private Drawable mDrawableBackground;
	private int mResProgress;
	private Drawable mDrawableProgress;
	private int mProgressDrbMinWidth;

	private int mProgressTextSize;

	private ColorStateList mProgressTextColorStateList;

	private int mProgressTextColor;

	private Typeface mTypeface = Typeface.DEFAULT;

	private Paint mTextPaint;

	private boolean mProgressTextVisible = true;

	private int mMaxProgress = 100;

	private float mProgress;

	private float mSmoothProgress = 0;

	private float mStartProgress = 0;

	private long mProgressSetTime;

	private int mSmoothAnimDuration;

	private Rect mRawProgressBounds = new Rect();

	private StringBuilder mSb = new StringBuilder(4);

	private String mText;

	private OnProgressChangeListener mOnProgressChangeListener;

	public ProgressHorizontal(Context context) {
		super(context);
		this.setFocusable(false);
		this.setClickable(false);
		this.setFocusableInTouchMode(false);
		mThreadId = Process.myTid();
		mTextPaint = new Paint();
	}

	public synchronized float getProgress() {
		return mProgress;
	}

	public void setProgressBackgroundResource(int resId) {
		if (mResBackground == resId) {
			return;
		}
		mResBackground = resId;
		try {
			mDrawableBackground = getResources().getDrawable(resId);
			if (null != mDrawableBackground) {
				mDrawableBackground.setBounds(0, 0, getWidth(), getHeight());
			}
		} catch (Exception e) {
			mDrawableBackground = null;
			mResBackground = -1;
		}
		invalidate();
	}

	public void setProgressDrawble(Drawable drawable) {
		if (mDrawableProgress == drawable) {
			return;
		}
		mDrawableProgress = drawable;
		invalidate();
	}

	public void setProgressResource(int resId) {
		if (mResProgress == resId) {
			return;
		}
		mDrawableProgress = getResources().getDrawable(resId);
		invalidate();
	}

	public void setMinProgressWidth(int minWidth) {
		mProgressDrbMinWidth = minWidth;
		invalidateSafe();
	}

	public void setMax(int max) {
		if (max > 0) {
			mMaxProgress = max;
		}
	}

	public void setProgress(int progress) {
		setProgress(progress, false);
	}

	public void setProgress(int progress, boolean smooth) {
		setProgress(progress / (float) mMaxProgress, smooth);
	}

	public void setProgress(float progress) {
		setProgress(progress, false);
	}

	public synchronized void setProgress(float progress, boolean smooth) {
		if (progress < 0) {
			progress = 0;
		}
		if (progress > 1) {
			progress = 1;
		}
		mProgress = progress;
		mProgressSetTime = System.currentTimeMillis();
		if (smooth) {
			mSmoothAnimDuration = (int) (MAX_SMOOTH_ANIM_DURATION * (1 - mProgress));
		} else {
			mSmoothAnimDuration = 0;
			mSmoothProgress = mProgress;
		}
		mStartProgress = mSmoothProgress;
		invalidateSafe();
	}

	public void setProgressTextSize(int px) {
		mProgressTextSize = px;
	}

	public void setProgressTextColor(ColorStateList color) {
		mProgressTextColorStateList = color;
		mProgressTextColor = mProgressTextColorStateList.getColorForState(getDrawableState(), mProgressTextColor);
	}

	public void setProgressTextColor(int color) {
		mProgressTextColorStateList = null;
		mProgressTextColor = color;
	}

	public void setProgressTextVisible(boolean visible) {
		mProgressTextVisible = visible;
	}

	public void setCenterText(String text) {
		mText = text;
		invalidate();
	}

	public void setOnProgressChangeListener(OnProgressChangeListener l) {
		mOnProgressChangeListener = l;
	}

	private void invalidateSafe() {
		if (mThreadId == Process.myTid()) {
			invalidate();
		} else {
			postInvalidate();
		}
	}

	private void notifyProgressChange(float smoothProgress, float targetProgress) {
		if (null != mOnProgressChangeListener) {
			mOnProgressChangeListener.onProgressChange(smoothProgress, targetProgress);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = 0;
		int height = 0;

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		if (widthMode == MeasureSpec.EXACTLY) {
			width = widthSize;
		} else {
			width = mDrawableBackground == null ? 0 : mDrawableBackground.getIntrinsicWidth();
			if (widthMode == MeasureSpec.AT_MOST) {
				width = Math.min(width, widthSize);
			}
		}

		if (heightMode == MeasureSpec.EXACTLY) {
			height = heightSize;
		} else {
			height = mDrawableBackground == null ? 0 : mDrawableBackground.getIntrinsicHeight();
			if (heightMode == MeasureSpec.AT_MOST) {
				height = Math.min(height, heightSize);
			}
		}

		if (null != mDrawableBackground) {
			mDrawableBackground.setBounds(0, 0, width, height);
		}
		mRawProgressBounds.set(getPaddingLeft(), getPaddingTop(), width - getPaddingRight(), height
				- getPaddingBottom());
		setMeasuredDimension(width, height);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		//父类已经绘制完背景，这里只需要绘制前景
		float factor;
		if (mProgress == 0 || mProgress == 1) {
			factor = 1;
		} else {
			long elapsed = System.currentTimeMillis() - mProgressSetTime;
			if (elapsed < 0) {
				factor = 0;
			} else if (elapsed > mSmoothAnimDuration) {
				factor = 1;
			} else {
				factor = elapsed / (float) mSmoothAnimDuration;
			}
		}
		mSmoothProgress = mStartProgress + factor * (mProgress - mStartProgress);

		// Draw progress
		if (null != mDrawableProgress) {
			if (mDrawableProgress instanceof NinePatchDrawable
					|| (mDrawableProgress instanceof DrawableContainer && ((DrawableContainer) mDrawableProgress).getCurrent() instanceof NinePatchDrawable)) {
				if (mSmoothProgress == 0) {
					mDrawableProgress.setBounds(0, 0, 0, 0);
				} else {
					mDrawableProgress.setBounds(0, mRawProgressBounds.top,
							(int) (mRawProgressBounds.left + (mRawProgressBounds.width() - mProgressDrbMinWidth)
									* mSmoothProgress)
									+ mProgressDrbMinWidth, mRawProgressBounds.bottom);
				}
			}
			mDrawableProgress.draw(canvas);
		}

		// Draw progress text
		if (mProgressTextVisible) {
			mSb.delete(0, mSb.length());
			if (TextUtils.isEmpty(mText)) {
				mSb.append((int) (mSmoothProgress * 100));
				mSb.append('%');
			} else {
				mSb.append(mText);
			}
			String text = mSb.toString();

			mTextPaint.setAntiAlias(true);
			mTextPaint.setColor(mProgressTextColor);
			mTextPaint.setTextSize(mProgressTextSize);
			mTextPaint.setTypeface(mTypeface);
			mTextPaint.setTextAlign(Align.CENTER);
			FontMetrics fm = mTextPaint.getFontMetrics();
			int fontH = (int) (Math.abs(fm.descent - fm.ascent));
			canvas.drawText(text, getWidth() >> 1, ((getHeight() - getPaddingTop() - getPaddingBottom()) >> 1) + (fontH >> 1), mTextPaint);
		}

		if (factor != 1) {
			invalidate();
		}
		notifyProgressChange(mSmoothProgress, mProgress);
	}


	public static interface OnProgressChangeListener {

		public void onProgressChange(float smoothProgress, float targetProgress);

	}

}
