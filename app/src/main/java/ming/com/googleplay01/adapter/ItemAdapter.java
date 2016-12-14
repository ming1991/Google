package ming.com.googleplay01.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import ming.com.googleplay01.activity.DetailActivity;
import ming.com.googleplay01.base.BaseHolder;
import ming.com.googleplay01.base.SuperBaseAdapter;
import ming.com.googleplay01.bean.ItemBean;
import ming.com.googleplay01.holder.ItemHolder;
import ming.com.googleplay01.utils.UIUtils;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/21 8:26
 * 描述：    主页,应用,游戏 adapter的抽取 , 加载更多,包含点击事件
 */

public abstract class ItemAdapter extends SuperBaseAdapter<ItemBean> {

    private List<ItemBean> mList;

    public ItemAdapter(List<ItemBean> list, ListView listView) {
        super(list,listView);
        mList = list;
    }

    @Override
    public BaseHolder getSpecialBaseHolder(int position) {
        return new ItemHolder();
    }

    /**
     * 实现加载更多
     * */
    @Override
    public boolean hasLoadMore() {
        return true;
    }

    /**
     * 加载更多,请求网络加载,在子线程中执行,,并还回结果
     * */
    @Override
    public abstract List<ItemBean> onLoadMoreData() throws Exception;

    /**
     * 处理普通条目的点击事件
     * */
    @Override
    public void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {

        //Toast.makeText(UIUtils.getContext(), mList.get(position).name, Toast.LENGTH_SHORT).show();

        //实现页面的跳转
        Intent intent = new Intent(UIUtils.getContext(), DetailActivity.class);
        intent.putExtra("packageName",mList.get(position).packageName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        UIUtils.getContext().startActivity(intent);

    }
}
