package ming.com.googleplay01.holder;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import ming.com.googleplay01.R;
import ming.com.googleplay01.base.BaseHolder;
import ming.com.googleplay01.bean.CategoryBean;
import ming.com.googleplay01.utils.Constants;
import ming.com.googleplay01.utils.UIUtils;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/20 20:29
 * 描述：    TODO
 */
public class CategoryNormalHolder extends BaseHolder<CategoryBean> {
    @BindView(R.id.item_category_icon_1)
    ImageView mItemCategoryIcon1;
    @BindView(R.id.item_category_name_1)
    TextView mItemCategoryName1;
    @BindView(R.id.item_category_item_1)
    LinearLayout mItemCategoryItem1;
    @BindView(R.id.item_category_icon_2)
    ImageView mItemCategoryIcon2;
    @BindView(R.id.item_category_name_2)
    TextView mItemCategoryName2;
    @BindView(R.id.item_category_item_2)
    LinearLayout mItemCategoryItem2;
    @BindView(R.id.item_category_icon_3)
    ImageView mItemCategoryIcon3;
    @BindView(R.id.item_category_name_3)
    TextView mItemCategoryName3;
    @BindView(R.id.item_category_item_3)
    LinearLayout mItemCategoryItem3;

    @Override
    public View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_category_normal, null);
        return view;
    }

    @Override
    public void setViewDataAndFresh(CategoryBean data) {
      /*  mItemCategoryName1.setText(data.name1);
        mItemCategoryName2.setText(data.name2);
        mItemCategoryName3.setText(data.name3);

        Picasso.with(UIUtils.getContext()).load(Constants.URLS.PIC_BASEURL + data.url1).into(mItemCategoryIcon1);
        Picasso.with(UIUtils.getContext()).load(Constants.URLS.PIC_BASEURL + data.url2).into(mItemCategoryIcon2);
        Picasso.with(UIUtils.getContext()).load(Constants.URLS.PIC_BASEURL + data.url3).into(mItemCategoryIcon3);*/

        //绑定数据,和去掉空白格子的边框
        //以每个格子作为单位
        bindViewData(mItemCategoryIcon1,mItemCategoryName1,data.url1,data.name1);
        bindViewData(mItemCategoryIcon2,mItemCategoryName2,data.url2,data.name2);
        bindViewData(mItemCategoryIcon3,mItemCategoryName3,data.url3,data.name3);


    }

    //绑定数据和设置点击事件
    private void bindViewData(ImageView icon, TextView tv, String url, String name) {

        ViewParent parent = tv.getParent();
        if (TextUtils.isEmpty(name)||TextUtils.isEmpty(url)){  //空白的格子影藏掉
            ((ViewGroup)parent).setVisibility(View.INVISIBLE);
        }else{
            ((ViewGroup)parent).setVisibility(View.VISIBLE); //会被复用
            Picasso.with(UIUtils.getContext()).load(Constants.URLS.PIC_BASEURL + url).into(icon);
            tv.setText(name);

            //设置点击事件
            NormalItemOnClickListener normalItemOnClickListener = new NormalItemOnClickListener();
            ((ViewGroup)parent).setOnClickListener(normalItemOnClickListener);
        }
    }

    class NormalItemOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            View childAt = ((ViewGroup) v).getChildAt(1);
            String text = ((TextView) childAt).getText().toString();
            Toast.makeText(UIUtils.getContext(), text, Toast.LENGTH_SHORT).show();
        }
    }


}
