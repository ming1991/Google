package ming.com.googleplay01.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import ming.com.googleplay01.R;
import ming.com.googleplay01.base.BaseHolder;
import ming.com.googleplay01.bean.SubjectBean;
import ming.com.googleplay01.utils.Constants;
import ming.com.googleplay01.utils.UIUtils;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/19 23:21
 * 描述：    TODO
 */
public class SubjectHolder extends BaseHolder<SubjectBean> {

    @BindView(R.id.item_subject_iv_icon)
    ImageView mItemSubjectIvIcon;
    @BindView(R.id.item_subject_tv_title)
    TextView mItemSubjectTvTitle;

    @Override
    public View initView() {

        View view = View.inflate(UIUtils.getContext(), R.layout.item_subject, null);
        return view;
    }

    @Override
    public void setViewDataAndFresh(SubjectBean data) {
        String url = Constants.URLS.PIC_BASEURL + data.url;
        mItemSubjectTvTitle.setText(data.des);
        Picasso.with(UIUtils.getContext()).load(url).into(mItemSubjectIvIcon);

    }
}
