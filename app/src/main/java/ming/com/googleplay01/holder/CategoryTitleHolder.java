package ming.com.googleplay01.holder;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import ming.com.googleplay01.base.BaseHolder;
import ming.com.googleplay01.bean.CategoryBean;
import ming.com.googleplay01.utils.UIUtils;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/20 20:29
 * 描述：    TODO
 */
public class CategoryTitleHolder extends BaseHolder<CategoryBean> {

    private TextView mTextView;

    @Override
    public View initView() {
        mTextView = new TextView(UIUtils.getContext());
        int px = UIUtils.dip2px(5);

        mTextView.setPadding(px,px,px,px);
        mTextView.setTextColor(Color.BLACK);
        return mTextView;
    }

    @Override
    public void setViewDataAndFresh(CategoryBean data) {
        mTextView.setText(data.title);

    }
}
