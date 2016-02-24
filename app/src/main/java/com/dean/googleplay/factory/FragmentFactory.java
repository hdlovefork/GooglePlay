package com.dean.googleplay.factory;

import com.dean.googleplay.fragment.AppFragment;
import com.dean.googleplay.fragment.BaseFragment;
import com.dean.googleplay.fragment.CategoryFragment;
import com.dean.googleplay.fragment.GameFragment;
import com.dean.googleplay.fragment.HomeFragment;
import com.dean.googleplay.fragment.RankingFragment;
import com.dean.googleplay.fragment.RecommendFragment;
import com.dean.googleplay.fragment.TopicFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/11.
 */
public class FragmentFactory {

    private static final int PAGE_HOME = 0;
    private static final int PAGE_APP = 1;
    private static final int PAGE_GAME = 2;
    private static final int PAGE_TOPIC = 3;
    private static final int PAGE_RECOMMEND = 4;
    private static final int PAGE_CATEGORY = 5;
    private static final int PAGE_RANKING = 6;

    public static Map<Integer, BaseFragment> mMap = new HashMap<>();

    public synchronized static BaseFragment create(int position) {
        BaseFragment fragment = mMap.get(position);
        if (fragment == null) {
            switch (position) {
                case PAGE_HOME:
                    fragment = new HomeFragment();
                    break;
            case PAGE_APP:
                    fragment = new AppFragment();
                    break;
            case PAGE_GAME:
                    fragment = new GameFragment();
                    break;
            case PAGE_TOPIC:
                    fragment = new TopicFragment();
                    break;
            case PAGE_RECOMMEND:
                    fragment = new RecommendFragment();
                    break;
            case PAGE_CATEGORY:
                    fragment = new CategoryFragment();
                    break;
            case PAGE_RANKING:
                    fragment = new RankingFragment();
                    break;
            }
            mMap.put(position, fragment);
        }
        return fragment;
    }
}
