package ming.com.googleplay01;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import ming.com.googleplay01.base.BaseFragment;
import ming.com.googleplay01.factory.FragmentFactory;
import ming.com.googleplay01.utils.UIUtils;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fl_menu_left)
    FrameLayout mFlMenuLeft;
    @BindView(R.id.fl_menu_right)
    FrameLayout mFlMenuRight;
    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.tabs)
    PagerSlidingTabStrip mTabs;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    private ActionBar mActionBar;
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] mTitles;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //初始化ActionBar设置
        initActionBar();

        initData();

        initEvent();



    }

    private void initEvent() {

        final HomeOnPageChangeListener homeOnPageChangeListener = new HomeOnPageChangeListener();

        //当页面切换的时候,请求加载网络数据
        mTabs.setOnPageChangeListener(homeOnPageChangeListener);

        //当mViewPager的视图树都挂载好了之后,再调用onPageSelected(0)方法,防止空指针
        mViewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //手动选中第一页-->早了一些-->应该晚一些调用-->找一个触发调用的时机
                //手动调用加载第一页,首页的数据
                homeOnPageChangeListener.onPageSelected(0);

                //移除该监听,兼容低版本 ,不然会循环调用
                //removeGlobalOnLayoutListener(this);与removeOnGlobalLayoutListener(this)
                mViewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

    }
    class HomeOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //默认不切换第一页的fragment  ,触发加载数据
            BaseFragment baseFragment = FragmentFactory.mSparseArray.get(position);  //获取当前显示的的fragment
            if (baseFragment!=null){

                //触发加载数据
                baseFragment.mView.triggerLoadData();
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void initData() {
        //获取指示器的标题
        mTitles = UIUtils.getStrings(R.array.main_titles);

        //HomePagerAdapter homePagerAdapter = new HomePagerAdapter();

        HomeFragmentAdapter homeFragmentAdapter = new HomeFragmentAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(homeFragmentAdapter);

        //绑定指示器
        mTabs.setViewPager(mViewPager);
    }


    private void initActionBar() {
        //获取v7 包中的actionbar
        mActionBar = getSupportActionBar();
        mActionBar.setTitle("GooglePlay");
//        actionBar.setSubtitle("标题2");

        //设置图标
        mActionBar.setIcon(R.mipmap.ic_launcher);
        mActionBar.setLogo(R.mipmap.ic_action_edit);

        //显示图标-->默认是隐藏的,默认是icon优先
        mActionBar.setDisplayShowHomeEnabled(false);
//        设置logo图标优先于icon图标-->默认是不用logo
        mActionBar.setDisplayUseLogoEnabled(true);

        //显示返回按钮/显示回退部分-->默认是隐藏

        mActionBar.setDisplayHomeAsUpEnabled(true);


        //初始化ActionBarDrawerToggle控件设置
        initActionBarDrawerToggle();


    }

    private void initActionBarDrawerToggle() {

        // public class ActionBarDrawerToggle implements DrawerLayout.DrawerListener

        //1.创建ActionBarDrawerToggle对象
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        //2.调用ActionBarDrawerToggle同步方法-->回退按钮ui发生了改变(变成三条横杠)
        mDrawerToggle.syncState();

        //3.让ActionBarDrawerToggle按钮可以跟随DrawerLayout拖动而发生变化
        mDrawerLayout.addDrawerListener(mDrawerToggle);

    }

    //覆写该方法,设置actionbar 每个条目空控件的点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Toast.makeText(MainActivity.this, "我被点击了", Toast.LENGTH_SHORT).show();

                //4.点击ActionBarDrawerToggle可以控制DrawerLayout打开和关闭
                mDrawerToggle.onOptionsItemSelected(item);

                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    class HomePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mTitles == null ? 0 : mTitles.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {


            TextView textView = new TextView(MainActivity.this);
            textView.setText(mTitles[position]);
            container.addView(textView);
            return textView;
        }

        //设置指示器的标题
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }


    //FragmentPagerAdapter  与 HomeFragmentStatePagerAdapter 的区别
    class HomeFragmentAdapter extends FragmentPagerAdapter {

        public HomeFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

           Fragment fragment =  FragmentFactory.creatFragment(position);

            return fragment;
        }

        @Override
        public int getCount() {

            return mTitles == null ? 0 : mTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return mTitles[position];
        }
    }

    class HomeFragmentStatePagerAdapter extends FragmentStatePagerAdapter{

        public HomeFragmentStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment =  FragmentFactory.creatFragment(position);

            return fragment;
        }

        @Override
        public int getCount() {
            return mTitles == null ? 0 : mTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return mTitles[position];
        }
    }
}
