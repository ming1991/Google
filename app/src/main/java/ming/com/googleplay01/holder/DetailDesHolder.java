package ming.com.googleplay01.holder;

import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

import butterknife.BindView;
import ming.com.googleplay01.R;
import ming.com.googleplay01.base.BaseHolder;
import ming.com.googleplay01.bean.DetailBean;
import ming.com.googleplay01.utils.UIUtils;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/21 13:38
 * 描述：    针对textView的高度
            1.除了measure(0,0)之外一定要记得设置具体的行高
 */

public class DetailDesHolder extends BaseHolder<DetailBean> {
    @BindView(R.id.app_detail_des_tv_des)
    TextView mAppDetailDesTvDes;
    @BindView(R.id.app_detail_des_tv_author)
    TextView mAppDetailDesTvAuthor;
    @BindView(R.id.app_detail_des_iv_arrow)
    ImageView mAppDetailDesIvArrow;

    private boolean isOpen = true;
    private String mDes;
    private int mAppDetailDesTvDesMeasuredHeight;


    @Override
    public View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_detail_des, null);

        //设置点击事件
        DetailDesOnClickListener detailDesOnClickListener = new DetailDesOnClickListener();
        view.setOnClickListener(detailDesOnClickListener);

        return view;
    }

    class DetailDesOnClickListener implements View.OnClickListener{



        @Override
        public void onClick(View v) {

            detailDesChangeHeight(true);
        }
    }

    private void detailDesChangeHeight(boolean isAnimator) {
        if (isOpen){

            //原始高度保存成成员变量
            if (mAppDetailDesTvDesMeasuredHeight==0){
                mAppDetailDesTvDesMeasuredHeight = mAppDetailDesTvDes.getMeasuredHeight();
            }

            // textView.measure(0,0); 申请测量只有一行的高度
            int start = mAppDetailDesTvDesMeasuredHeight;
            int end = getFiveLineHeight();

            if (isAnimator){
                //mAppDetailDesTvDes.setHeight();
                performAnimator(start, end);
            }else{

                //错误写法

                //直接设置高度
               /* LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mAppDetailDesTvDes.getLayoutParams();
                params.height=end;
                mAppDetailDesTvDes.setLayoutParams(params);*/
                mAppDetailDesTvDes.setHeight(end);

            }



        }else{
           // textView.measure(0,0);
            //不能直接去测量否则,只有一行的高度

            int start = getFiveLineHeight();
            int end =  mAppDetailDesTvDesMeasuredHeight;

            if (isAnimator){
                performAnimator(start, end);
            }else{
                //直接设置高度
                //ViewGroup.LayoutParams params = mAppDetailDesTvDes.getLayoutParams();
               /* LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mAppDetailDesTvDes.getLayoutParams();
                params.height=end;
                mAppDetailDesTvDes.setLayoutParams(params);*/

                mAppDetailDesTvDes.setHeight(end);
            }

        }

        isOpen = !isOpen;
    }

    private void performAnimator(int start, int end) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(mAppDetailDesTvDes,"height", start,end);
        objectAnimator.start();

        if (isOpen){
            ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(mAppDetailDesIvArrow, "rotation", 180, 0);
            objectAnimator1.start();

        }else{
            ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(mAppDetailDesIvArrow, "rotation", 0, 180);
            objectAnimator1.start();

        }

        //设置动画的监听,打开之后srollviews滑动到底部
        ObjectAnimatorListener animatorListener = new ObjectAnimatorListener();
        objectAnimator.addListener(animatorListener);

    }

    class ObjectAnimatorListener implements Animator.AnimatorListener{

        @Override
        public void onAnimationStart(Animator animator) {

        }

        @Override
        public void onAnimationEnd(Animator animator) {

            ViewParent parent = mAppDetailDesTvDes.getParent();
            while (true){
                parent = parent.getParent();
                if (parent instanceof ScrollView){
                    ((ScrollView)parent).fullScroll(View.FOCUS_DOWN);
                    break;
                }

            }

        }

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    }

    /* ###针对textView的高度
     1.除了measure(0,0)之外一定要记得设置具体的行高*/
    //获取五行的高度
    private int getFiveLineHeight() {
        TextView textView = new TextView(UIUtils.getContext());
        //设置为五行
        textView.setLines(5);
        textView.setText(mDes);
        //测量高度
        textView.measure(0,0);
        int fiveLineHeight = textView.getMeasuredHeight();

        return fiveLineHeight;
    }

    @Override
    public void setViewDataAndFresh(DetailBean data) {
        String author = data.author;
        mDes = data.des;
        mAppDetailDesTvDes.setText(mDes);
        mAppDetailDesTvAuthor.setText(author);

        //手动调用一次,折叠
        //直接在oncreat 中调用获取高度为0;
        //设置布局完成后的监听

        mAppDetailDesTvDes.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                detailDesChangeHeight(false);

                //取消监听
                mAppDetailDesTvDes.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });



    }
}
