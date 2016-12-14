package ming.com.googleplay01.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import ming.com.googleplay01.R;
import ming.com.googleplay01.base.BaseHolder;
import ming.com.googleplay01.bean.ItemBean;
import ming.com.googleplay01.utils.Constants;
import ming.com.googleplay01.utils.StringUtils;
import ming.com.googleplay01.utils.UIUtils;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/16 1:27
 * 描述：
 * <p>
 * 作用:1.提供视图View
 * 2.接受数据并完成数据的赋值
 */

public class ItemHolder extends BaseHolder<ItemBean> {


    @BindView(R.id.item_appinfo_iv_icon)
    ImageView mItemAppinfoIvIcon;
    @BindView(R.id.item_appinfo_tv_title)
    TextView mItemAppinfoTvTitle;
    @BindView(R.id.item_appinfo_rb_stars)
    RatingBar mItemAppinfoRbStars;
    @BindView(R.id.item_appinfo_tv_size)
    TextView mItemAppinfoTvSize;
    @BindView(R.id.item_appinfo_tv_des)
    TextView mItemAppinfoTvDes;

    @Override
    public View initView() {
        return View.inflate(UIUtils.getContext(), R.layout.item_home_list, null);
    }

    /**
     * 控件和数据进行绑定
     * */
    @Override
    public void setViewDataAndFresh(ItemBean data) {
        //设置listview的数据
        mItemAppinfoTvTitle.setText(data.name);
        mItemAppinfoRbStars.setRating(data.stars);
        //格式化文件大小
        mItemAppinfoTvSize.setText(StringUtils.formatFileSize(data.size));
        mItemAppinfoTvDes.setText(data.des);

        String url = Constants.URLS.PIC_BASEURL+data.iconUrl;

        //加载网络图片(自带淡入淡出的效果)
        Picasso.with(UIUtils.getContext()).load(url).into(mItemAppinfoIvIcon);

    }
}
