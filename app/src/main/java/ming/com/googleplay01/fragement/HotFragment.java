package ming.com.googleplay01.fragement;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import ming.com.googleplay01.base.BaseFragment;
import ming.com.googleplay01.base.LoadingPage;
import ming.com.googleplay01.protocol.HotProtocol;
import ming.com.googleplay01.utils.UIUtils;
import ming.com.googleplay01.view.FlowLayout;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/15 13:15
 * 描述：    TODO
 */

public class HotFragment extends BaseFragment {
    private List<String> mStringList;

    @Override
    protected LoadingPage.LoadResultEnum loadNetData() {
        SystemClock.sleep(1000);

        HotProtocol hotProtocol = new HotProtocol();
        try {
            mStringList = hotProtocol.loadData(0);
            LoadingPage.LoadResultEnum resultEnum = checkNetData(mStringList);

            return resultEnum;
        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPage.LoadResultEnum.ERROR;
        }
    }

    @Override
    public View initSuccessView() { //用scollview 包裹的流式布局

        ScrollView scrollView = new ScrollView(UIUtils.getContext());
        FlowLayout flowLayout = new FlowLayout(UIUtils.getContext());

        //添加孩子textView
        for (int i = 0; i < mStringList.size(); i++) {
            TextView textView = new TextView(UIUtils.getContext());

            final String text = mStringList.get(i);
            textView.setText(text);
            int padding = UIUtils.dip2px(5);
            textView.setPadding(padding,padding,padding,padding);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(UIUtils.dip2px(10));

            //设置属性,代码实现背景选择器 和  shape

            //d代码实现shape
            GradientDrawable normalGradientDrawable = new GradientDrawable();
            //设置填充颜色和半角
            Random random = new Random();
            int alpha = 255;
            int red = random.nextInt(171) + 50;//50-220
            int green = random.nextInt(171) + 50;//50-220
            int blue = random.nextInt(171) + 50;//50-220
            int color = Color.argb(alpha, red, green, blue);

            normalGradientDrawable.setColor(color);

            normalGradientDrawable.setCornerRadius(UIUtils.dip2px(5));

            //按压是的状态
            GradientDrawable pressedlGradientDrawable = new GradientDrawable();
            pressedlGradientDrawable.setColor(Color.BLACK);
            pressedlGradientDrawable.setCornerRadius(UIUtils.dip2px(5));

            //背景选择器
            StateListDrawable selectorBg = new StateListDrawable();

            //注意循序
            selectorBg.addState(new int[]{android.R.attr.state_pressed},pressedlGradientDrawable);

            selectorBg.addState(new int[]{},normalGradientDrawable);

            //注意
            textView.setBackgroundDrawable(selectorBg);
            textView.setClickable(true);

            //设置点击事件
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UIUtils.getContext(),text , Toast.LENGTH_SHORT).show();
                }
            });

            flowLayout.addView(textView);


        }

        scrollView.addView(flowLayout);

        return scrollView;
    }
}
