package ming.com.googleplay01.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.List;
import java.util.Map;

import ming.com.googleplay01.utils.UIUtils;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/21 13:53
 * 描述：    TODO
 */

public abstract class BaseActivity extends AppCompatActivity {

    public LoadingPage mLoadingPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        initView();

        setContentView(mLoadingPage);

        // ButterKnife.bind(this); 易错处,将此处的绑定删掉

        initData();

        initEvent();


    }

    public void initEvent() {

    }

    /**
     * 触发加载数据
     */
    private void initData() {
        mLoadingPage.triggerLoadData();
    }

    private void initView() {

        //提供四种视图
        mLoadingPage = new LoadingPage(UIUtils.getContext()) {

            @Override
            public View initSuccessView() {
                return BaseActivity.this.initSuccessView();
            }

            @Override
            public LoadResultEnum loadNetData() {
                return BaseActivity.this.loadNetData();
            }
        };
    }

    /**
     * 该方法在子线程中执行,并还回请求的结果  三种 : 成功 ,空 ,异常
     */
    public abstract LoadingPage.LoadResultEnum loadNetData();

    /**
     * 创建一个 成功视图,并绑定数据
     */
    public abstract View initSuccessView();

    /**
     * 初始化数据,url的key
     * */
    public abstract void init();


    /**
     * 校验网络还回的数据
     */
    public LoadingPage.LoadResultEnum checkNetData(Object object) {
        if (object == null) {
            return LoadingPage.LoadResultEnum.EMPTY;
        }

        if (object instanceof List) {
            if (((List) object).size() == 0) {
                return LoadingPage.LoadResultEnum.EMPTY;
            }
        }

        if (object instanceof Map) {
            if (((Map) object).size() == 0) {
                return LoadingPage.LoadResultEnum.EMPTY;
            }
        }

        return LoadingPage.LoadResultEnum.SUCCESS;
    }
}
