package com.dean.googleplay.fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dean.googleplay.R;
import com.dean.googleplay.domain.CategoryList;
import com.dean.googleplay.http.CategoryRequestParams;
import com.dean.googleplay.http.Http;
import com.dean.googleplay.http.MyImgOpt;

import org.xutils.x;

/**
 * Created by Administrator on 2016/1/10.
 */
public class CategoryFragment extends BaseFragment<CategoryList> {

    private final CategoryRequestParams mParams;
    private CategoryList mDatas;

    public CategoryFragment() {
        mParams = new CategoryRequestParams();
    }

    @Override
    protected CategoryList fetchDataFromServer() {
        return Http.getSync(mParams, CategoryList.class);
    }

    @Override
    protected View createPagerSuccess(CategoryList serverResult) {
        mDatas = serverResult;

        ScrollView scrollView = new ScrollView(x.app());
        LinearLayout root = new LinearLayout(x.app());
        root.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(root);
        for (int i = 0; i < mDatas.size(); i++) {
            CategoryList.Category category = mDatas.get(i);
            View infoRoot = LayoutInflater.from(root.getContext())
                                          .inflate(R.layout.item_category, root,false);
            root.addView(infoRoot);

            TextView tv_title = (TextView) infoRoot.findViewById(R.id.tv_title);
            tv_title.setText(category.title);

            LinearLayout infoContainer = (LinearLayout) infoRoot.findViewById(R.id.ll_container);
            for (int j = 0; j < category.infos.size(); j++) {
                View infoView = LayoutInflater.from(x.app())
                                              .inflate(R.layout.item_content_category, infoContainer, false);
                infoContainer.addView(infoView);
                TextView tv_1 = (TextView) infoView.findViewById(R.id.tv_1);
                TextView tv_2 = (TextView) infoView.findViewById(R.id.tv_2);
                TextView tv_3 = (TextView) infoView.findViewById(R.id.tv_3);
                ImageView iv_1 = (ImageView) infoView.findViewById(R.id.iv_1);
                ImageView iv_2 = (ImageView) infoView.findViewById(R.id.iv_2);
                ImageView iv_3 = (ImageView) infoView.findViewById(R.id.iv_3);

                CategoryList.Category.InfosEntity info = category.infos.get(j);
                setInfo(tv_1, iv_1, info.name1, info.url1);
                setInfo(tv_2, iv_2, info.name2, info.url2);
                setInfo(tv_3, iv_3, info.name3, info.url3);
            }
        }
        return scrollView;
    }

    private void setInfo(TextView tv, ImageView iv, String name, String url) {
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(url)) {
            tv.setText(name);
            x.image()
             .bind(iv, x.app().getString(R.string.server_addr) + "/image?name=" + url, MyImgOpt
                     .build(R.drawable.ic_default));
        } else {
            tv.setVisibility(View.INVISIBLE);
            iv.setVisibility(View.INVISIBLE);
        }
    }
}
