package com.dean.googleplay.pager;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dean.googleplay.R;
import com.dean.googleplay.domain.AppList;
import com.dean.googleplay.http.MyImgOpt;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * 应用详情安全下拉页面
 */
public class PagerDetailSafe extends PagerView<AppList.AppInfo> implements View.OnClickListener {

    @ViewInject(R.id.safe_title_layout)
    RelativeLayout safe_title_layout;
    @ViewInject(R.id.safe_arrow)
    ImageView safe_arrow;
    @ViewInject(R.id.safe_content)
    LinearLayout safe_content;
    @ViewInject(R.id.des_iv_1)
    ImageView des_iv_1;
    @ViewInject(R.id.des_iv_2)
    ImageView des_iv_2;
    @ViewInject(R.id.des_iv_3)
    ImageView des_iv_3;
    @ViewInject(R.id.des_iv_4)
    ImageView des_iv_4;
    @ViewInject(R.id.des_tv_1)
    TextView des_tv_1;
    @ViewInject(R.id.des_tv_2)
    TextView des_tv_2;
    @ViewInject(R.id.des_tv_3)
    TextView des_tv_3;
    @ViewInject(R.id.des_tv_4)
    TextView des_tv_4;
    @ViewInject(R.id.iv_1)
    ImageView iv_1;
    @ViewInject(R.id.iv_2)
    ImageView iv_2;
    @ViewInject(R.id.iv_3)
    ImageView iv_3;
    @ViewInject(R.id.iv_4)
    ImageView iv_4;
    @ViewInject(R.id.des_layout_1)
    LinearLayout des_layout_1;
    @ViewInject(R.id.des_layout_2)
    LinearLayout des_layout_2;
    @ViewInject(R.id.des_layout_3)
    LinearLayout des_layout_3;
    @ViewInject(R.id.des_layout_4)
    LinearLayout des_layout_4;

    ImageView[] iv_Array;
    ImageView[] des_iv_Array;
    TextView[] des_tv_Array;
    LinearLayout[] des_layout_Array;

    private ViewGroup.LayoutParams safe_contentLayoutParams;


    public PagerDetailSafe(AppList.AppInfo data) {
        super(data);
    }

    @Override
    public View onCreateView() {
        return View.inflate(x.app(), R.layout.app_detail_safe, null);
    }

    @Override
    public void initView() {
        //false表示隐藏安全正文下拉页，显示向下箭头
        //true表示显示安全正文下拉页，显示向上箭头
        safe_arrow.setTag(false);
        safe_title_layout.setOnClickListener(this);
        //默认高度为0，不显示下拉安全列表
        safe_contentLayoutParams = safe_content.getLayoutParams();
        safe_contentLayoutParams.height = 0;
        safe_content.setLayoutParams(safe_contentLayoutParams);

        iv_Array = new ImageView[4];
        iv_Array[0] = iv_1;
        iv_Array[1] = iv_2;
        iv_Array[2] = iv_3;
        iv_Array[3] = iv_4;

        des_iv_Array = new ImageView[4];
        des_iv_Array[0] = des_iv_1;
        des_iv_Array[1] = des_iv_2;
        des_iv_Array[2] = des_iv_3;
        des_iv_Array[3] = des_iv_4;

        des_tv_Array = new TextView[4];
        des_tv_Array[0] = des_tv_1;
        des_tv_Array[1] = des_tv_2;
        des_tv_Array[2] = des_tv_3;
        des_tv_Array[3] = des_tv_4;

        des_layout_Array = new LinearLayout[4];
        des_layout_Array[0] = des_layout_1;
        des_layout_Array[1] = des_layout_2;
        des_layout_Array[2] = des_layout_3;
        des_layout_Array[3] = des_layout_4;
    }

    @Override
    public void onUpdateView() {
        AppList.AppInfo appInfo = getData();
        //隐藏所有下拉布局
        for (int i = 0; i < des_layout_Array.length; i++) {
            des_layout_Array[i].setVisibility(View.GONE);
        }
        if (appInfo == null) {
            return;
        }
        List<AppList.AppInfo.SafeEntity> safeEntityList = appInfo.getSafe();
        if (safeEntityList != null) {
            for (int i = 0; i < safeEntityList.size(); i++) {
                //显示下拉列表父布局
                des_layout_Array[i].setVisibility(View.VISIBLE);
                //设置下拉列表文字和图片
                AppList.AppInfo.SafeEntity safeEntity = safeEntityList.get(i);
                x.image().bind(iv_Array[i], x.app()
                                             .getString(R.string.server_addr) + "/image?name=" + safeEntity
                        .getSafeUrl(), MyImgOpt
                        .build(R.drawable.ic_default));

                x.image().bind(des_iv_Array[i], x.app()
                                                 .getString(R.string.server_addr) + "/image?name=" + safeEntity
                        .getSafeDesUrl(), MyImgOpt
                        .build(R.drawable.ic_default));
                des_tv_Array[i].setText(safeEntity.getSafeDes());
                int color = getColor(safeEntity);
                des_tv_Array[i].setTextColor(color);
            }
        }
    }

    /**
     * 根据服务器传过来的文本颜色枚举值计算出具体的颜色值
     * @param safeEntity
     * @return
     */
    private int getColor(AppList.AppInfo.SafeEntity safeEntity) {
        int color;
        int colorType = safeEntity.getSafeDesColor();
        if (colorType >= 1 && colorType <= 3) {
            color = Color.rgb(255, 153, 0);
        } else if (colorType == 4) {
            color = Color.rgb(0, 177, 62);
        } else {
            color = Color.rgb(122, 122, 122);
        }
        return color;
    }

    public void onClick(View v) {
        boolean tag = (boolean) safe_arrow.getTag();
        int startHeight = safe_content.getLayoutParams().height;//下拉列表起始的高度
        int targetHeight = 0;//下拉列表终止的高度
        if (tag) {
            //已经显示，需要收缩安全下拉页
            targetHeight = 0;
            //显示向下箭头
            safe_arrow.setImageResource(R.drawable.arrow_down);
        } else {
            //已经隐藏，需要展开安全下拉页
            //测量下拉页面高度
            targetHeight = measureContentHeight();
            //显示向上箭头
            safe_arrow.setImageResource(R.drawable.arrow_up);
        }
        //以动画形式展开/收缩安全下拉列表
        ValueAnimator animator = ValueAnimator.ofInt(startHeight, targetHeight);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                safe_contentLayoutParams.height = (int) animation.getAnimatedValue();
                safe_content.setLayoutParams(safe_contentLayoutParams);
            }
        });
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(300);
        animator.start();
        tag = !tag;
        safe_arrow.setTag(tag);
    }

    /**
     * 测量下拉页面高度
     *
     * @return
     */
    private int measureContentHeight() {
        int measuredWidth = safe_content.getMeasuredWidth();
        //第一个参数表示宽度的大小
        //第二个参数表示宽度的模式
        int widthMeasureSpec = View.MeasureSpec
                .makeMeasureSpec(measuredWidth, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec
                .makeMeasureSpec(0xffffff, View.MeasureSpec.AT_MOST);
        //测量内容
        safe_content.measure(widthMeasureSpec, heightMeasureSpec);
        //LogUtil.d("测量的高度值是：" + safe_content.getMeasuredHeight());
        return safe_content.getMeasuredHeight();
    }
}
