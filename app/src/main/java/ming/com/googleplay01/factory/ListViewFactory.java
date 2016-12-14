package ming.com.googleplay01.factory;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ListView;

import ming.com.googleplay01.utils.UIUtils;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/19 16:37
 * 描述：    创造listView的工厂
 */
public class ListViewFactory {

    public static ListView creatListView() {

        ListView listView = new ListView(UIUtils.getContext());
        //设置快速滑动棒
        listView.setFastScrollEnabled(true);
        //取消自带的选择器

        listView.setSelector(new ColorDrawable(Color.TRANSPARENT));

        //兼容性设置
        listView.setDividerHeight(0);
        listView.setCacheColorHint(Color.TRANSPARENT);

        return  listView;

    }
}
