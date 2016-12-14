package ming.com.googleplay01.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import ming.com.googleplay01.R;
import ming.com.googleplay01.base.BaseHolder;
import ming.com.googleplay01.bean.DetailBean;
import ming.com.googleplay01.utils.Constants;
import ming.com.googleplay01.utils.UIUtils;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/21 13:33
 * 描述：    TODO
 */
public class DetailPicHolder extends BaseHolder<DetailBean> {


    @BindView(R.id.app_detail_pic_iv_container)
    LinearLayout mAppDetailPicIvContainer;

    @Override
    public View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_detail_pic, null);
        return view;
    }

    @Override
    public void setViewDataAndFresh(DetailBean data) {
        List<String> stringList = data.screen;
        for (int i = 0; i < stringList.size(); i++) {
            String string = stringList.get(i);
            ImageView imageView = new ImageView(UIUtils.getContext());
            Picasso.with(UIUtils.getContext()).load(Constants.URLS.PIC_BASEURL+string).into(imageView);


            //布局参数layout要与父容器相同
            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);

            if (i!=0){
                layoutParams.leftMargin = UIUtils.dip2px(5);
            }

            mAppDetailPicIvContainer.addView(imageView,layoutParams);

        }

    }
}
