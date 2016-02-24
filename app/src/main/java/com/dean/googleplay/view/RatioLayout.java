package com.dean.googleplay.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.dean.googleplay.R;

/**
 * 用于ImageView的父布局，根据提供的宽高比例（ratio）计算ImageView的大小
 <pre>
 XML示例代码：
 &lt;com.dean.googleplay.view.RatioLayout
 android:layout_width=&quot;match_parent&quot;
 android:layout_height=&quot;match_parent&quot;
 app:ratio=&quot;2.5&quot;
 &gt;
	 &lt;com.dean.googleplay.view.viewpager.AutoScrollViewPager
	 android:id=&quot;@+id/vp_pager&quot;
	 android:layout_width=&quot;match_parent&quot;
	 android:layout_height=&quot;match_parent&quot;/&gt;
 &lt;/com.dean.googleplay.view.RatioLayout&gt;
 </pre>
 */
public class RatioLayout extends FrameLayout {
	// 宽和高的比例
	private float ratio = 0.0f;

	public RatioLayout(Context context) {
		this(context, null);
	}

	public RatioLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);
		ratio = a.getFloat(R.styleable.RatioLayout_ratio, 0.0f);
		a.recycle();
	}

	public void setRatio(float f) {
		ratio = f;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
		int height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();
		if (widthMode == MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY && ratio != 0.0f) {
			height = (int) (width / ratio + 0.5f);
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(height + getPaddingTop() + getPaddingBottom(),
					MeasureSpec.EXACTLY);
		} else if (widthMode != MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY && ratio != 0.0f) {
			width = (int) (height * ratio + 0.5f);
			widthMeasureSpec = MeasureSpec.makeMeasureSpec(width + getPaddingLeft() + getPaddingRight(),
					MeasureSpec.EXACTLY);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
