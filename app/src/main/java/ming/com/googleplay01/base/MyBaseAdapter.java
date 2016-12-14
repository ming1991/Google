package ming.com.googleplay01.base;

import android.widget.BaseAdapter;

import java.util.List;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/16 0:07
 * 描述：    所有adapter的基类
 *
 * 泛型写在前面时定义泛型
 */

public abstract class MyBaseAdapter<ITEMBEANTYPE> extends BaseAdapter {

    public List<ITEMBEANTYPE> mList;

    public MyBaseAdapter(List<ITEMBEANTYPE> list) {
        mList = list;
    }

    @Override
    public int getCount() {
        return mList==null?0:mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
