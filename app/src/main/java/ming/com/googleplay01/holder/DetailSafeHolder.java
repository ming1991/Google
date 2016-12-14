package ming.com.googleplay01.holder;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import ming.com.googleplay01.R;
import ming.com.googleplay01.base.BaseHolder;
import ming.com.googleplay01.bean.DetailBean;
import ming.com.googleplay01.utils.Constants;
import ming.com.googleplay01.utils.UIUtils;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/21 13:31
 * 描述：    TODO
 */
public class DetailSafeHolder extends BaseHolder<DetailBean> {

    @BindView(R.id.app_detail_safe_iv_arrow)
    ImageView mAppDetailSafeIvArrow;
    @BindView(R.id.app_detail_safe_pic_container)
    LinearLayout mAppDetailSafePicContainer;
    @BindView(R.id.app_detail_safe_des_container)
    LinearLayout mAppDetailSafeDesContainer;

    private boolean isOpen = true;


    @Override
    public View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_detail_safe, null);

        //设置动画的监听,height发生改变
        //下面的描述,实现折叠的效果
        DetailSafeHolderOnClickListener onClickListener = new DetailSafeHolderOnClickListener();
        view.setOnClickListener(onClickListener);

        return view;
    }

    class DetailSafeHolderOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) { //实现折叠效果

            DetailDesContainerChangeHeigh(true);

        }
    }

    private void DetailDesContainerChangeHeigh(boolean isAnimator) { //是否展示动画
        //判断是否打开

        if (isOpen){

            //原来的打开的话,现在 height --> 0
            //mAppDetailSafeDesContainer.measure(0,0);  //oncreat 方法中取不到height
            int start =mAppDetailSafeDesContainer.getMeasuredHeight();
            int end = 0;

            if (isAnimator){
                //执行动画
                performAnimator(start, end);
            }else{ //不执行动画
                //直接设置高度
                ViewGroup.LayoutParams params = mAppDetailSafeDesContainer.getLayoutParams();
                params.height = end;
                //把属性设置回去
                mAppDetailSafeDesContainer.setLayoutParams(params);
            }


        }else{

            //申请重新测量一下height ,现在为0;
            mAppDetailSafeDesContainer.measure(0,0);
            int start =0;
            int end = mAppDetailSafeDesContainer.getMeasuredHeight();

            if (isAnimator){
                performAnimator(start, end);
            }else{
                //直接设置高度
                ViewGroup.LayoutParams params = mAppDetailSafeDesContainer.getLayoutParams();
                params.height = end;
                //把属性设置回去
                mAppDetailSafeDesContainer.setLayoutParams(params);
            }


        }
        isOpen = !isOpen;
    }

    private void performAnimator(int start, final int end) {

        //package com.nineoldandroids.animation; 兼容低版本
        final ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end); //产生渐变值
        valueAnimator.setDuration(200);  //设置时间
        valueAnimator.start();

        //设置监听,获取中间的渐变值
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {  //多次被调用
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {

                int tempHeight = (int) valueAnimator.getAnimatedValue();

                //根据渐变值给控件设置高度
                ViewGroup.LayoutParams params = mAppDetailSafeDesContainer.getLayoutParams();
                params.height = tempHeight;

                //把属性设置回去
                mAppDetailSafeDesContainer.setLayoutParams(params);

            }
        });

        //箭头也跟着转动
//        mAppDetailSafeIvArrow
        //mAppDetailSafeIvArrow.setRotation();

        if (isOpen){
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mAppDetailSafeIvArrow, "rotation", 180, 0);
            objectAnimator.setDuration(200);
            objectAnimator.start();

        }else{
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mAppDetailSafeIvArrow, "rotation", 0, 180);
            objectAnimator.setDuration(200);
            objectAnimator.start();


        }


    }

    @Override
    public void setViewDataAndFresh(DetailBean data) {

        List<DetailBean.SafeBean> safeBeanList = data.safe;
        //循环给容器添加内容
        for (int i = 0; i < safeBeanList.size(); i++) {

            DetailBean.SafeBean safeBean = safeBeanList.get(i);

            //添加上面容器官方,安全的图片
            String safeUrl = safeBean.safeUrl;
            ImageView imageView = new ImageView(UIUtils.getContext());
            Picasso.with(UIUtils.getContext()).load(Constants.URLS.PIC_BASEURL+safeUrl).into(imageView);

            //添加到容器
            mAppDetailSafePicContainer.addView(imageView);

            //动态向下面的容器添加一行 ,文本和图片
            LinearLayout linearLayout = new LinearLayout(UIUtils.getContext());
            int padding = UIUtils.dip2px(5);
            linearLayout.setPadding(padding,padding,padding,padding);
            linearLayout.setGravity(Gravity.CENTER_VERTICAL);

            String safeDesUrl = safeBean.safeDesUrl;
            String safeDes = safeBean.safeDes;
            int safeDesColor = safeBean.safeDesColor;

            //图片
            ImageView imageView1 = new ImageView(UIUtils.getContext());
            Picasso.with(UIUtils.getContext()).load(Constants.URLS.PIC_BASEURL+safeDesUrl).into(imageView1);
            linearLayout.addView(imageView1);

            //文本
            TextView textView = new TextView(UIUtils.getContext());
            //显示一行
            textView.setSingleLine(true);
            textView.setText(safeDes);
            if (safeDesColor == 0) {
                textView.setTextColor(UIUtils.getColor(R.color.app_detail_safe_normal));
            } else {
                textView.setTextColor(UIUtils.getColor(R.color.app_detail_safe_warning));
            }

            linearLayout.addView(textView);

            //将一行添加到下面的容器中
            mAppDetailSafeDesContainer.addView(linearLayout);

        }

        //默认设置为关闭,执行一次点击时间;
        DetailDesContainerChangeHeigh(false); //第一次进入去掉 动画效果
    }
}
