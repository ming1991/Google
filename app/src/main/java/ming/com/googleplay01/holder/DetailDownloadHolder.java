package ming.com.googleplay01.holder;

import android.view.View;
import android.widget.Button;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import ming.com.googleplay01.R;
import ming.com.googleplay01.base.BaseHolder;
import ming.com.googleplay01.bean.DetailBean;
import ming.com.googleplay01.bean.DownLoadInfo;
import ming.com.googleplay01.manager.DownloadManager;
import ming.com.googleplay01.utils.FileUtils;
import ming.com.googleplay01.utils.UIUtils;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/21 13:40
 * 描述：    提供多种视图
 */
public class DetailDownloadHolder extends BaseHolder<DetailBean> {

    @BindView(R.id.app_detail_download_btn_favo)
    Button mAppDetailDownloadBtnFavo;
    @BindView(R.id.app_detail_download_btn_share)
    Button mAppDetailDownloadBtnShare;
    @BindView(R.id.app_detail_download_btn_download)
    Button mAppDetailDownloadBtnDownload;
    private DetailBean mData;

    @Override
    public View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_detail_download, null);

        return view;
    }

    @Override
    public void setViewDataAndFresh(DetailBean data) {
        mData = data;

        //mTextView.setText(data.name);
    }


    //设置点击事件
    @OnClick(R.id.app_detail_download_btn_download)
    public void onClick() {  //点击实现下载

        //// http://localhost:8080/GooglePlayServer/download?name=app/com.itheima.www/com.itheima.www.apk&range=0
        DownLoadInfo downLoadInfo = new DownLoadInfo();

        downLoadInfo.packageName = mData.packageName;

        //savePath = 包名+
        String dir = FileUtils.getDir("apk");
        String name = mData.packageName + ".apk";
        File file = new File(dir, name);
        downLoadInfo.savePath = file.getAbsolutePath();

        //url
        downLoadInfo.url = mData.downloadUrl;

        DownloadManager.getInstance().download(downLoadInfo);

    }
}
