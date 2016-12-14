package ming.com.googleplay01.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/19 22:33
 * 描述：    TODO 解决低版本ViewPager嵌套抢夺焦点的问题
 * 1.自定义子viewPager 覆写OntouchEvent方法 ,左右滑动的时候请求父容器不拦截事件
 * 2.父容器选择拦截滑动的事件
 */

public class HomeViewPager extends ViewPager {

    private float mDownX;
    private float mDownY;

    public HomeViewPager(Context context) {
        super(context);
    }

    public HomeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getRawX();
                mDownY = ev.getRawY();

                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = ev.getRawX();
                float moveY = ev.getRawY();

                float dX = Math.abs(moveX-mDownX);
                float dY = Math.abs(moveY-mDownY);
                if (dX>dY){
                   requestDisallowInterceptTouchEvent(true);
                }else{
                    requestDisallowInterceptTouchEvent(false);
                }

                break;
            case MotionEvent.ACTION_UP:

                break;

            default:
                break;
        }


        return super.onTouchEvent(ev);
    }
}
