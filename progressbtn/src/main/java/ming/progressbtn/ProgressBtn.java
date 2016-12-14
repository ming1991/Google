package ming.progressbtn;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/22 18:58
 * 描述：    继承已有的控件提高拓展性
 *
 * //对外可以设置 max 和 Progress 是否显示
 */

public class ProgressBtn extends Button {

    private long mMax = 100;
    private long mProgress = 0;

    //设置是否重新绘制
    private boolean isProgressEnable = true;

    public ProgressBtn(Context context) {
        this(context,null);
    }

    public ProgressBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {


        if (isProgressEnable){

        //画一个背景颜色\
        Drawable drawable = new ColorDrawable(Color.BLUE);

        int left = 0;
        int top = 0;
        int right = (int) (((mProgress*1f/mMax)*getMeasuredWidth() + 0.5f));
        int bottom = getBottom();
        drawable.setBounds(left,top,right,bottom);
        drawable.draw(canvas);

        }
        //先画浅颜色的背景
        super.onDraw(canvas);



    }

    public void setMax(long max){
        mMax = max;
    }

    public void setProgress(long progress){
        mProgress = progress;

        //频繁的调用
        invalidate();

    }
}
