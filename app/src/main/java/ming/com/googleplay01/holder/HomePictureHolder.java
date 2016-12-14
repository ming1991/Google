package ming.com.googleplay01.holder;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import ming.com.googleplay01.R;
import ming.com.googleplay01.base.BaseHolder;
import ming.com.googleplay01.utils.Constants;
import ming.com.googleplay01.utils.UIUtils;
import ming.com.googleplay01.view.HomeViewPager;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/19 19:24
 * 描述：    实现无限轮播,自动切换的效果ViewPager
 */
public class HomePictureHolder extends BaseHolder<List<String>> {

    private static final int MAXPAGE = 10000;
    @BindView(R.id.item_home_picture_pager)
    HomeViewPager mItemHomePicturePager;
    @BindView(R.id.item_home_picture_container_indicator)
    LinearLayout mItemHomePictureContainerIndicator;
    private List<String> mData;
    private AutoScrollTask mAutoScrollTask;

    /**
     * 提供视图
     */
    @Override
    public View initView() {

        return View.inflate(UIUtils.getContext(), R.layout.item_home_picture, null);
    }

    /**
     * 绑定数据
     */
    @Override
    public void setViewDataAndFresh(List<String> data) {

        mData = data;
        HomePagerAdapter homePagerAdapter = new HomePagerAdapter();
        mItemHomePicturePager.setAdapter(homePagerAdapter);

        //初始化点
        initPoint();

        HomeOnPageChangeListener pageChangeListener = new HomeOnPageChangeListener();
        //圆点设置轮播切换效果
        mItemHomePicturePager.addOnPageChangeListener(pageChangeListener);

        //设置位置到中间位置,且显示第一个页面,默认显示第一页
        int position = MAXPAGE/2 -MAXPAGE /2% mData.size();
        mItemHomePicturePager.setCurrentItem(position);

        //设置自动轮播的效果
        mAutoScrollTask = new AutoScrollTask();
        mAutoScrollTask.start();

        //实现按下去的时候停止轮播
        HomeOnTouchListener homeOnTouchListener =  new HomeOnTouchListener();
        mItemHomePicturePager.setOnTouchListener(homeOnTouchListener);

        //问题解决:侧滑会抢夺轮播图的焦点 ,停止自动轮播 TODO

        //TODO 解决低版本ViewPager嵌套抢夺焦点的问题
        //自定义子viewPager 覆写OntouchEvent方法 ,请求父容器不拦截事件



    }

    class HomeOnTouchListener implements View.OnTouchListener{

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    mAutoScrollTask.stop();

                    break;
                case  MotionEvent.ACTION_MOVE:

                    break;
                case  MotionEvent.ACTION_UP:
                    mAutoScrollTask.start();

                    break;
                case  MotionEvent.ACTION_CANCEL: //被抢夺焦点的时候调用
                   // mAutoScrollTask.start();

                    break;
                default:
                    break;
            }

            return false;  //此处为false ,只是做一些操作
        }
    }

    //设置自动轮播的效果
    class AutoScrollTask implements Runnable{
        public void start(){
            UIUtils.getHandler().postDelayed(this,3000);
        }

        public void stop(){
            UIUtils.getHandler().removeCallbacks(this);
        }

        @Override
        public void run() {
            //获取当前的位置
            int currentItem = mItemHomePicturePager.getCurrentItem();
            currentItem++;
            mItemHomePicturePager.setCurrentItem(currentItem,true);

            //递归调用
            start();
        }
    }

    class HomeOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //处理索引
            position =  position%mData.size();

            //循环遍历孩子,设置被选中的孩子
            for (int i = 0; i < mData.size(); i++) {
                View childAt = mItemHomePictureContainerIndicator.getChildAt(i);
                if (i==position){
                    ( (ImageView)childAt).setImageResource(R.drawable.indicator_selected);
                }else{
                    ( (ImageView)childAt).setImageResource(R.drawable.indicator_normal);
                }

            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void initPoint() {
        for (int i = 0; i < mData.size(); i++) {
            ImageView imageView = new ImageView(UIUtils.getContext());
            imageView.setImageResource(R.drawable.indicator_normal);

            //设置大小和外边距
            int width = UIUtils.dip2px(6);
            int height = UIUtils.dip2px(6);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width,height);
            layoutParams.bottomMargin = UIUtils.dip2px(6);
            layoutParams.leftMargin = UIUtils.dip2px(6);

            imageView.setLayoutParams(layoutParams);

            //默认选中第一个
            if (i==0){
                imageView.setImageResource(R.drawable.indicator_selected);
            }

            //添加到容器中
            mItemHomePictureContainerIndicator.addView(imageView);

        }
    }

    class HomePagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {  //加大条目个数实现无限轮播的效果
            return MAXPAGE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //处理索引
           position =  position%mData.size();

            ImageView imageView = new ImageView(UIUtils.getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            //不是完整的地址
            String url = Constants.URLS.PIC_BASEURL  + mData.get(position);
            //设置网络图片
            Picasso.with(UIUtils.getContext()).load(url).into(imageView);
            container.addView(imageView);

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
           container.removeView((View) object);
        }

    }
}
