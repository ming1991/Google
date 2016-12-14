package ming.com.googleplay01.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import ming.com.googleplay01.R;
import ming.com.googleplay01.utils.LogUtils;
import ming.com.googleplay01.utils.UIUtils;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/20 0:35
 * 描述：    TODO 已知宽度,可以动态的计算高度 /已知高度,动态计算宽度
 * * 描    述： 动态计算高度
 * 描    述： 公式 selfWidth/selfHeight = mPicRatio;
 */

public class RatioFrameLayout extends FrameLayout {

    //    public float mPicRatio = 2.43f;
    private static final int RELATIVE_WIDTH = 0;
    private static final int RELATIVE_HEIGHT = 1;
    private float mPicRatio;
    private int mRelative;

    public RatioFrameLayout(Context context) {
        this(context, null);
    }

    public RatioFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        //获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioFrameLayout);
        mPicRatio = typedArray.getFloat(R.styleable.RatioFrameLayout_picRatio, 1f);
        mRelative = typedArray.getInt(R.styleable.RatioFrameLayout_relative, 0);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //得到宽度的模式
        /*
        AT_MOST  至多
        UNSPECIFIED 不确定的  xml中的体现的话-->wrap_content
        EXACTLY 确定的 xml中的体现的话-->fill_parent math_parent 100dp 100px
         */
        //测量自己
        int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);


        //限定用户的输入,根据  选择相对relative 和 比例
        if (widthMeasureMode == MeasureSpec.EXACTLY &&mRelative ==RELATIVE_WIDTH) {

            //获取自身确定的宽度
            int width = MeasureSpec.getSize(widthMeasureSpec);
            //根据比例计算高度
            int height = (int) (width / mPicRatio + 0.5f);

            LogUtils.s("应有的高度->" + UIUtils.px2dp(height));

            //保存测绘结果
            setMeasuredDimension(width, height);

            //让孩子测量自身 ,只有一个孩子,matchParent
            int childWidth = width - getPaddingLeft() - getPaddingRight();
            int childHeight = height - getPaddingBottom() - getPaddingTop();

            //根据父容器改变之后宽高测量孩子
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);

            measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);
            

        } else if (heightMeasureMode == MeasureSpec.EXACTLY &&mRelative ==RELATIVE_HEIGHT) {

            //取出自身的高度
            int height = MeasureSpec.getSize(heightMeasureSpec);

            //动态计算宽度
            //selfWidth/selfHeight = mPicRatio;
            int width = (int) (height * mPicRatio + .5f);
            LogUtils.s("应有的高度->" + UIUtils.px2dp(height));

            //保存测绘结果
            setMeasuredDimension(width, height);

            //计算孩子的宽度和高度

            int childWidth = width - getPaddingLeft() - getPaddingRight();
            int childHeight = height - getPaddingBottom() - getPaddingTop();

            //让孩子测量自身
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
            measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);

        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
