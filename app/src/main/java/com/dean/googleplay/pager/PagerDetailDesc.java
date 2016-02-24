package com.dean.googleplay.pager;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dean.googleplay.R;
import com.dean.googleplay.domain.AppList;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 应用描述页面
 */
public class PagerDetailDesc extends PagerView<AppList.AppInfo> implements View.OnClickListener {

    @ViewInject(R.id.des_layout)
    RelativeLayout des_layout;
    @ViewInject(R.id.des_content)
    TextView des_content;
    @ViewInject(R.id.des_author)
    TextView des_author;
    @ViewInject(R.id.des_arrow)
    ImageView des_arrow;
    @ViewInject(R.id.des_extra_layout)
    RelativeLayout des_extra_layout;

    private ViewGroup.LayoutParams mDes_contentLayoutParams;
    private int mMinHeight;
    private ScrollView mScrollView;

    public PagerDetailDesc(AppList.AppInfo data) {
        super(data);
    }

    @Override
    public View onCreateView() {
        return View.inflate(x.app(), R.layout.app_detail_des, null);
    }

    @Override
    public void initView() {
        mDes_contentLayoutParams = des_content.getLayoutParams();
        des_content.setMaxLines(7);
        des_arrow.setTag(false);
        des_extra_layout.setOnClickListener(this);
    }

    @Override
    public void onUpdateView() {
        AppList.AppInfo data = getData();
        des_content.setText(data.getDes());
        des_author.setText(data.getAuthor());
        if (mMinHeight == 0) {
            //计算概要描述文本的高度作为动画伸缩时的最小高度
            mMinHeight = measureContentHeight();
        }
    }

    @Override
    public void onClick(View v) {
        boolean tag = (boolean) des_arrow.getTag();
        //描述正文动画开始和结束高度
        int startHeight = des_content.getMeasuredHeight();
        int endHeight = 0;
        if (tag) {
            //显示向下箭头
            des_arrow.setImageResource(R.drawable.arrow_down);
            endHeight = mMinHeight;
        } else {
            //显示向上箭头
            des_arrow.setImageResource(R.drawable.arrow_up);
            des_content.setMaxLines(Integer.MAX_VALUE);
            endHeight = measureContentHeight();
        }
        //动画伸缩描述正文
        ValueAnimator animator = ValueAnimator.ofInt(startHeight, endHeight);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDes_contentLayoutParams.height = (int) animation.getAnimatedValue();
                des_content.setLayoutParams(mDes_contentLayoutParams);
                if (mScrollView == null) {
                    mScrollView = getScrollView();
                }
                if (mScrollView != null) {
                    //将滚动条拉到底部，显示完整的描述信息
                    mScrollView.fullScroll(View.FOCUS_DOWN);
                }
            }
        });
        animator.setDuration(300);
        animator.start();
        tag = !tag;
        des_arrow.setTag(tag);
    }


    private int measureContentHeight() {
        int measureWidth = des_content.getMeasuredWidth();
        int widthSpec = View.MeasureSpec.makeMeasureSpec(measureWidth, View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(0xffffff, View.MeasureSpec.AT_MOST);
        des_content.measure(widthSpec, heightSpec);
        return des_content.getMeasuredHeight();
    }

    public ScrollView getScrollView() {
        View currentView = des_layout;
        ViewParent parent = null;
        while (true) {
            parent = currentView.getParent();
            if (parent != null && parent instanceof ScrollView) {
                break;
            }
            currentView = (View) parent;
        }
        if (parent == null) {
            return null;
        }
        return (ScrollView) parent;
    }
}
