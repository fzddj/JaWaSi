package com.makedream.jawasi.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.makedream.jawasi.R;
import com.makedream.jawasi.ui.fragment.MainFragment;
import com.makedream.jawasi.ui.fragment.StockFragment;
import com.makedream.jawasi.util.BackupUtil;

import org.greenrobot.greendao.DbUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ddj on 16-7-16.
 */
public class MainActivity extends AppCompatActivity {

    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页","进度"};

    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.tl);
        mViewPager = (ViewPager) findViewById(R.id.vp);
        initUI();
    }

    private void initUI() {

        mFragments.add(MainFragment.newInstance("1", "2"));
        mFragments.add(new StockFragment());
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        initTl();
    }

    private void initTl() {
        mSlidingTabLayout.setViewPager(mViewPager, mTitles);
        mSlidingTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mSlidingTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setCurrentItem(1);
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.back_up:
                BackupUtil backupUtil = new BackupUtil(this);
                backupUtil.backUp();
                return true;
            case R.id.restore:
                backupUtil = new BackupUtil(this);
                backupUtil.restore();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
