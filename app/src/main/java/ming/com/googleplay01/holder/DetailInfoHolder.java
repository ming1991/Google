package ming.com.googleplay01.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import ming.com.googleplay01.R;
import ming.com.googleplay01.base.BaseHolder;
import ming.com.googleplay01.bean.DetailBean;
import ming.com.googleplay01.utils.Constants;
import ming.com.googleplay01.utils.StringUtils;
import ming.com.googleplay01.utils.UIUtils;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/21 13:20
 * 描述：    TODO
 * 1.对外提供视图
 * 2.接受数据并完成绑定数据(需手动调用,传入参数)
 */
public class DetailInfoHolder extends BaseHolder<DetailBean> {


    @BindView(R.id.app_detail_info_iv_icon)
    ImageView mAppDetailInfoIvIcon;
    @BindView(R.id.app_detail_info_tv_name)
    TextView mAppDetailInfoTvName;
    @BindView(R.id.app_detail_info_rb_star)
    RatingBar mAppDetailInfoRbStar;
    @BindView(R.id.app_detail_info_tv_downloadnum)
    TextView mAppDetailInfoTvDownloadnum;
    @BindView(R.id.app_detail_info_tv_version)
    TextView mAppDetailInfoTvVersion;
    @BindView(R.id.app_detail_info_tv_time)
    TextView mAppDetailInfoTvTime;
    @BindView(R.id.app_detail_info_tv_size)
    TextView mAppDetailInfoTvSize;

    @Override
    public View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_detail_info, null);
        return view;
    }

    @Override
    public void setViewDataAndFresh(DetailBean data) {
        mAppDetailInfoTvName.setText(data.name);
        mAppDetailInfoRbStar.setRating(data.stars);

        //从xml文件中出去定义好的字符串 ,和占位符的拼接
        String downLoadNum = UIUtils.getResources().getString(R.string.detailDownloadNum, data.downloadNum);
        String size = UIUtils.getResources().getString(R.string.detailSize, StringUtils.formatFileSize(data.size));
        String date = UIUtils.getResources().getString(R.string.detailDate, data.date);
        String version = UIUtils.getResources().getString(R.string.detailVersion, data.version);

        mAppDetailInfoTvVersion.setText(version);
        mAppDetailInfoTvSize.setText(size);
        mAppDetailInfoTvTime.setText(date);
        mAppDetailInfoTvDownloadnum.setText(downLoadNum);


        Picasso.with(UIUtils.getContext()).load(Constants.URLS.PIC_BASEURL + data.iconUrl).into(mAppDetailInfoIvIcon);



    }
}
