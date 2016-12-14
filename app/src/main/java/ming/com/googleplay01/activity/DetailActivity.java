package ming.com.googleplay01.activity;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import ming.com.googleplay01.R;
import ming.com.googleplay01.base.BaseActivity;
import ming.com.googleplay01.base.LoadingPage;
import ming.com.googleplay01.bean.DetailBean;
import ming.com.googleplay01.holder.DetailDesHolder;
import ming.com.googleplay01.holder.DetailDownloadHolder;
import ming.com.googleplay01.holder.DetailInfoHolder;
import ming.com.googleplay01.holder.DetailPicHolder;
import ming.com.googleplay01.holder.DetailSafeHolder;
import ming.com.googleplay01.protocol.DetailProtocol;
import ming.com.googleplay01.utils.UIUtils;

public class DetailActivity extends BaseActivity {

    @BindView(R.id.flDetailDownload)
    FrameLayout mFlDetailDownload;
    @BindView(R.id.flDetailInfo)
    FrameLayout mFlDetailInfo;
    @BindView(R.id.flDetailSafe)
    FrameLayout mFlDetailSafe;
    @BindView(R.id.flDetailPic)
    FrameLayout mFlDetailPic;
    @BindView(R.id.flDetailDes)
    FrameLayout mFlDetailDes;

    private String mPackageName;
    private DetailBean mDetailBean;


    /**
     * 该方法在子线程中执行,并还回请求的结果  三种 : 成功 ,空 ,异常
     */
    public LoadingPage.LoadResultEnum loadNetData() {

        DetailProtocol detailProtocol = new DetailProtocol(mPackageName);

        try {

            mDetailBean = detailProtocol.loadData(0);

            LoadingPage.LoadResultEnum detailBeanEnum = checkNetData(mDetailBean);

            return detailBeanEnum;

        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPage.LoadResultEnum.ERROR;
        }

    }

    /**
     * 创建一个 成功视图,并绑定数据
     */
    public View initSuccessView() {
        //mDetailBean 数据
        //将一个复杂的布局拆分成多个模板(holder),拆分成五个容器
        View view = View.inflate(UIUtils.getContext(), R.layout.item_detail, null);

        //在此处添加注解
        ButterKnife.bind(this,view);

        //向容器添加(View+data=holder)

        //信息
        DetailInfoHolder detailInfoHolder = new DetailInfoHolder();
        View detailInfoHolderView = detailInfoHolder.mHolderView;

        detailInfoHolder.setViewDataAndFresh(mDetailBean);

        mFlDetailInfo.addView(detailInfoHolderView);

        //安全
        DetailSafeHolder detailSafeHolder = new DetailSafeHolder();
        View detailSafeHolderView = detailSafeHolder.mHolderView;
        detailSafeHolder.setViewDataAndFresh(mDetailBean);
        mFlDetailSafe.addView(detailSafeHolderView);

        //图片
        DetailPicHolder detailPicHolder = new DetailPicHolder();
        View detailPicHolderView = detailPicHolder.mHolderView;
        detailPicHolder.setViewDataAndFresh(mDetailBean);
        mFlDetailPic.addView(detailPicHolderView);

        //描述
        DetailDesHolder detailDesHolder = new DetailDesHolder();
        View detailDesHolderView = detailDesHolder.mHolderView;
        detailDesHolder.setViewDataAndFresh(mDetailBean);

        mFlDetailDes.addView(detailDesHolderView);

        //下载
        DetailDownloadHolder detailDownloadHolder = new DetailDownloadHolder();
        View detailDownloadHolderView = detailDownloadHolder.mHolderView;
        detailDownloadHolder.setViewDataAndFresh(mDetailBean);

        mFlDetailDownload.addView(detailDownloadHolderView);

        return view;

    }

    public void init() {
        Intent intent = getIntent();
        mPackageName = intent.getStringExtra("packageName");

    }

    @Override
    public void initEvent() {

    }
}
