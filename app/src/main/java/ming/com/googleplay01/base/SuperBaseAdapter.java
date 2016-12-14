package ming.com.googleplay01.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import ming.com.googleplay01.factory.ThreadPoolProxyFactory;
import ming.com.googleplay01.holder.LoadMoreHolder;
import ming.com.googleplay01.utils.UIUtils;

import static ming.com.googleplay01.holder.LoadMoreHolder.LOADMORE_ERROR;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/16 8:35
 * 描述：    TODO
 * <p>
 * 加载更多
 * <p>
 * 默认有加载更多,没有更多的时候影藏加载更在更多的视图
 */

public abstract class SuperBaseAdapter<ITEMBEANTYPE> extends MyBaseAdapter<ITEMBEANTYPE> {

    public static final int VIEWTYPE_LOADMORE = 0;
    public static final int VIEWTYPE_NORMAL   = 1;
    private List<ITEMBEANTYPE> mList;
    private ListView mListView;
    private LoadMoreHolder     mLoadMoreHolder;
    private LoadMoreTask mLoadMoreTask;
    private int mCurrentLoadMoreState;

    public SuperBaseAdapter(List<ITEMBEANTYPE> list, ListView listView) {
        super(list);
        mList = list;
        mListView = listView;

        //设置条目的点击事件
        MyOnItemClickListener itemClickListener = new MyOnItemClickListener();
        listView.setOnItemClickListener(itemClickListener);
    }

    /**
     * 只在点击事件中position考虑轮播图个数,其他的方法不用考虑
     * */
    class MyOnItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            //需要去掉轮播图 等头
            //只在点击事件中position考虑轮播图个数,其他的方法不用考虑
            position = position - ((ListView)parent).getHeaderViewsCount();

            //上拉刷新加载更多失败时,点击重试

            //判断条目时刷新类型的条目
            if (getItemViewType(position)==VIEWTYPE_LOADMORE){

                //判断是刷新条目显示的是刷新错误的界面,
                // 就是触发加载时的条目
                if (mCurrentLoadMoreState==LoadMoreHolder.LOADMORE_ERROR){

                    //重新请求加载数据
                    triggerLoadMoreData();
                }

            }else{

                //其他的条目对外设置点击事件

                onNormalItemClick(parent, view, position, id);
            }

        }
    }

    /**
     * 普通条目的点击事件
     * 子类覆写方法,处理普通条目的点击事件
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     *
     * 需要去掉轮播图的条目
     */
    public   void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
       // 需要去掉轮播图的条目parent, View view, int position, long id

    }

    /**
     * 不需要考虑轮播图
     * */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //根据 当前视图的类型 ,创建holder 和数据的绑定
        int viewType = getItemViewType(position);

        BaseHolder holder = null;

        if (convertView == null) {

            if (viewType == VIEWTYPE_LOADMORE) {
                //创建加载更多的holder
                holder = getLoadMoreHolder();
            } else {
                //创建ViewHolder 对象
                holder = getSpecialBaseHolder(position);
            }


        } else {
            //
            holder = (BaseHolder) convertView.getTag();
        }

        //进行数据的绑定,需判断类型
        if (viewType == VIEWTYPE_LOADMORE) {  //加载更多的条目

            //需判断是否有更多数据
            if (hasLoadMore()) {
                mLoadMoreHolder.setViewDataAndFresh(LoadMoreHolder.LOADMORE_LOADING); //显示加载更多的视图

                //触发加载更多
                triggerLoadMoreData();
            } else {
                mLoadMoreHolder.setViewDataAndFresh(LoadMoreHolder.LOADMORE_NONE); //影藏加载更多的所有视图
            }


        } else { //普通条目
            holder.setViewDataAndFresh(mList.get(position));
        }

        return holder.mHolderView;

    }

    /**
     * 触发加载更多
     * <p>
     * 异步请求网络加载数据
     */
    private void triggerLoadMoreData() {
        //避免正在加载的时候重复加载, 加载完毕之后 将mLoadMoreTask值为null,还可以加载
        if (mLoadMoreTask==null) {

            //再次加载之前显示, 正在加载的视图
            mLoadMoreHolder.setViewDataAndFresh(LoadMoreHolder.LOADMORE_LOADING); //显示加载更多的视图

            mLoadMoreTask = new LoadMoreTask();
            ThreadPoolProxyFactory.creatNormalThreadPoolProxy().execute(mLoadMoreTask);
        }

    }

    //默认每次加载的数据条目数为20
    private static final int PAGERSIZE = 20;

    class LoadMoreTask implements Runnable {
        @Override
        public void run() {



            //正真的异步加载数据
            /*
            对于还回结果的分析
            1.对于有加载更多类型的view,我们需要intege 类型 的
            2.对于普通类型的View,我们需要的是list集合类型的数据集
            最终选择 list结合类型
             */

            mCurrentLoadMoreState = LoadMoreHolder.LOADMORE_LOADING;

            List<ITEMBEANTYPE> loadMoreList = null;

            try {

                //加载的过程可能出现异常
                loadMoreList = onLoadMoreData(); //表示有响应,对外提供方法请求网络数据

                //对结果进行集中处理
                if (loadMoreList == null) {
                    mCurrentLoadMoreState = LoadMoreHolder.LOADMORE_NONE;
                } else if (loadMoreList.size() < PAGERSIZE) {
                    mCurrentLoadMoreState = LoadMoreHolder.LOADMORE_NONE;
                } else {
                    mCurrentLoadMoreState = LoadMoreHolder.LOADMORE_LOADING;
                }
            } catch (Exception e) {
                e.printStackTrace();
                //加载的过程可能出现异常
                mCurrentLoadMoreState = LOADMORE_ERROR;  //刷新加载失败时,点击重新加载
            }

            //两种方法解决 1.临时变量 2.成员变量(内部类使用外部类的临时变量,而临时变量不能变final)

            final int finalCurrentLoadMoreState = mCurrentLoadMoreState;

            final List<ITEMBEANTYPE> finalLoadMoreList = loadMoreList;

            UIUtils.getHandler().post(new Runnable() {  //更新数据
                @Override
                public void run() {
                    //对加载的结果进行分析
                    if (finalLoadMoreList != null && finalLoadMoreList.size() > 0) {  //请求回来有数据

                        //数据添加到原集合,并通知更新数据

                        mList.addAll(finalLoadMoreList);

                        //并通知listview更新数据
                        notifyDataSetChanged();
                    }

                    //更改加载更多条目的UI
                    mLoadMoreHolder.setViewDataAndFresh(finalCurrentLoadMoreState); //影藏加载更多的所有视图

                    //加载完  , 更改标识
                    mLoadMoreTask = null;
                }
            });


        }
    }

    /**
     * 正真的异步请求加载数据
     * 在子线程中完成
     * 子类选择实现
     * <p>
     * 在异步加载的过程中可能出现 异常
     */
    public List<ITEMBEANTYPE> onLoadMoreData() throws Exception {
        return null;
    }

    /**
     * 子类选择实现,是否有更多数据,默认没有
     */
    public boolean hasLoadMore() {
        return false;
    }

    /**
     * 每个子类都有加载更多 ,就放在父类中
     */
    private BaseHolder getLoadMoreHolder() { //加载更多的视图时通用的,只创建一个就行

        if (mLoadMoreHolder == null) {

            mLoadMoreHolder = new LoadMoreHolder();
        }
        return mLoadMoreHolder;
    }

    /**
     * 创建ViewHolder 对象
     * @param position
     */
    public abstract BaseHolder getSpecialBaseHolder(int position);


    /**
     * 加载更多
     * 添加一个下拉刷新
     */

    //添加一个下拉刷新
    @Override
    public int getCount() {

        return super.getCount() + 1;
    }

    //默认值为1 ,
    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;
    }


    //对外提供更对的条目类型,除了下拉刷新
    //范围在0- getViewTypeCount-1 之间变化
    @Override
    public int getItemViewType(int position) {
        if (position == (getCount() - 1)) {
            return VIEWTYPE_LOADMORE;  //加载更多的视图类型
        } else {
           // return VIEWTYPE_NORMAL;  //一般视图的样式

            return  getNormalViewType(position);
        }

    }

    /**
     * 除了下拉刷新,对外提供更多的条目类型,
     * */
    public int getNormalViewType(int position) {
        return VIEWTYPE_NORMAL;
    }
}
