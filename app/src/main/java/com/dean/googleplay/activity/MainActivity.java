package com.dean.googleplay.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.dean.googleplay.R;
import com.dean.googleplay.factory.FragmentFactory;
import com.dean.googleplay.fragment.BaseFragment;
import com.dean.googleplay.pager.PagerMenu;
import com.viewpagerindicator.TabPageIndicator;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity{

    private static final String TAG = "MainActivity";
    @ViewInject(R.id.indicator)
    private TabPageIndicator mIndicator;
    @ViewInject(R.id.vp_pager)
    private ViewPager mViewPager;
    @ViewInject(R.id.drawer_layout)
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    @ViewInject(R.id.start_drawer)
    FrameLayout mStartDrawer;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        LogUtil.d(item.toString());
        return mToggle.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            //在标题栏中显示应用程序图标
            ActionBar actionBar = getSupportActionBar();
            int flags = actionBar.getDisplayOptions() | ActionBar.DISPLAY_HOME_AS_UP;
            actionBar.setDisplayOptions(flags);

            //设置左侧菜单开关
            mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close);
            mToggle.syncState();
            mDrawerLayout.setDrawerListener(mToggle);
            PagerMenu pagerMenu=new PagerMenu();
            mStartDrawer.addView(pagerMenu.getView());
        }

        //项目框架以ViewPager+indicator布局
        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                BaseFragment fragment = FragmentFactory.create(position);
                if (fragment != null) {
                    fragment.show();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        mIndicator.setViewPager(mViewPager);
        mIndicator.onPageSelected(0);
    }

    //ViewPager适配器
    class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getStringArray(R.array.tab_names)[position];
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentFactory.create(position);
        }

        @Override
        public int getCount() {
            return getResources().getStringArray(R.array.tab_names).length;
        }
    }


}
