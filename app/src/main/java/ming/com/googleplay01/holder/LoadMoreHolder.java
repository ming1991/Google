package ming.com.googleplay01.holder;

import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import ming.com.googleplay01.R;
import ming.com.googleplay01.base.BaseHolder;
import ming.com.googleplay01.utils.UIUtils;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/19 0:42
 * 描述：    TODO
 */
public class LoadMoreHolder extends BaseHolder<Integer> {

    @BindView(R.id.item_loadmore_container_loading)
    LinearLayout mItemLoadmoreContainerLoading;
    @BindView(R.id.item_loadmore_container_retry)
    LinearLayout mItemLoadmoreContainerRetry;

    public static final int LOADMORE_LOADING = 0;//正在加载更多
    public static final int LOADMORE_ERROR   = 1;//加载更多失败,点击重试
    public static final int LOADMORE_NONE    = 2;//没有加载更多

    /**
     * 提供视图
     */
    @Override
    public View initView() { //需要进行判断,再提供不同的视图,三种情况:加载更多.加载失败,没有加载跟多

        View view = View.inflate(UIUtils.getContext(), R.layout.item_loadmore, null);
        return view;
    }

    /**
     *绑定数据
     * 特殊:根据状态显示和影藏控件
     * */
    @Override
    public void setViewDataAndFresh(Integer data) {  //静态视图的显示和影藏
        //影藏所有的控件
        mItemLoadmoreContainerLoading.setVisibility(View.GONE);
        mItemLoadmoreContainerRetry.setVisibility(View.GONE);

        switch (data){
            case LOADMORE_LOADING:
                mItemLoadmoreContainerLoading.setVisibility(View.VISIBLE);

            break;
            case LOADMORE_ERROR:
                mItemLoadmoreContainerRetry.setVisibility(View.VISIBLE);

            break;

            case LOADMORE_NONE: //都影藏

                break;
            default:
                break;
        }

    }



}
