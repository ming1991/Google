package ming.progressview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/22 19:52
 * 描述：    TODO
 * ProgressView extends LinearLayout--->  ViewGroup 的子类想走OnDraw方法必须设置背景
 */

public class ProgressView extends LinearLayout {


    private ImageView mIvIcon;
    private TextView mTvNote;

    private boolean isProgressEnable = true;
    private long mMax = 100;
    private long mProgress = 0;
    private RectF mOval;
    private Paint mPaint;

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //挂载视图
        View.inflate(getContext(), R.layout.infalte_progressview, this);

        mIvIcon = (ImageView) findViewById(R.id.ivIcon);
        mTvNote = (TextView) findViewById(R.id.tvNote);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);  //绘制背景
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);  //绘制孩子,绘制图标 和文字

        if (isProgressEnable) {

            if (mOval == null) {
                //以图片为原型设置边界
                float left = mIvIcon.getLeft();
                float top = mIvIcon.getTop();
                float right = mIvIcon.getRight();
                float bottom = mIvIcon.getBottom();
                mOval = new RectF(left, top, right, bottom);
            }


            float startAngle = -90;
            float sweepAngle = mProgress * 1f / mMax * 360;
            boolean useCenter = false;


            if (mPaint == null) {
                mPaint = new Paint();
                mPaint.setAntiAlias(true);
                mPaint.setColor(Color.BLUE);
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setStrokeWidth(3);
            }


            //绘制一个弧形
            canvas.drawArc(mOval, startAngle, sweepAngle, useCenter, mPaint);

        }

    }

    /**
     * 对外提供方法设置内容
     */


    public void setProgressEnable(boolean progressEnable) {
        isProgressEnable = progressEnable;
    }

    public void setMax(long max) {
        mMax = max;
    }

    public void setProgress(long progress) {

        mProgress = progress;
        //重新绘制ui
        invalidate();
    }

    /**
     * 设置图标
     *
     * @param resId
     */
    public void setIcon(int resId) {
        mIvIcon.setImageResource(resId);
    }

    /**
     * 设置对应的说明文字
     *
     * @param content
     */
    public void setNote(String content) {
        mTvNote.setText(content);
    }
}
