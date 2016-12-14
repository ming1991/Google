package ming.com.googleplay01.holder;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ming.com.googleplay01.R;
import ming.com.googleplay01.base.BaseHolder;
import ming.com.googleplay01.utils.UIUtils;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/16 1:27
 * 描述：
 * <p>
 * 作用:1.提供视图View
 * 2.接受数据并完成数据的赋值
 */

public class HomeHolderBackUp1 extends BaseHolder<String> {

    public View mHomeHolder;
    public String data;
    @BindView(R.id.tmp_tv_1)
    TextView mTmpTv1;
    @BindView(R.id.tmp_tv_2)
    TextView mTmpTv2;

    public HomeHolderBackUp1() {

        //提供视图
        mHomeHolder = initView();

        //获取子控件对象
        ButterKnife.bind(this,mHomeHolder);

        //将ViewHolder与holder绑定
        mHomeHolder.setTag(this);

    }

    public View initView() {
        return View.inflate(UIUtils.getContext(), R.layout.item_temp, null);
    }


    //对外提供方法,设置控件数据
   public void setViewDataAndFresh(String data){
       mTmpTv1.setText(data + "上");
       mTmpTv2.setText(data + "下");
    }

}
