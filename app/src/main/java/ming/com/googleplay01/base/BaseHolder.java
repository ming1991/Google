package ming.com.googleplay01.base;

import android.view.View;

import butterknife.ButterKnife;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/16 8:01
 * 描述：   所有 Holder的基类
 *
 * 作用:提供视图View(inflate), findviewbyId  , /对外提供方法,
 * //接受数据,设置控件数据
 */

public abstract class BaseHolder<HOLDERBEANTYPE> {

    public  View mHolderView;
    public HOLDERBEANTYPE data;

    public BaseHolder() {

        //提供视图
        mHolderView = initView();

        //获取子控件对象
        ButterKnife.bind(this, mHolderView);

        //将ViewHolder与holder绑定
        mHolderView.setTag(this);

    }

    //交给子类去实现,强制,提供视图
    public abstract View initView();


    //对外提供方法,接受数据,设置控件数据
    public abstract void setViewDataAndFresh(HOLDERBEANTYPE data);
}
