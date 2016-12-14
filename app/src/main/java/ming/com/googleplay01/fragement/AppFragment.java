package ming.com.googleplay01.fragement;

import android.os.SystemClock;
import android.view.View;
import android.widget.ListView;

import java.io.IOException;
import java.util.List;

import ming.com.googleplay01.adapter.ItemAdapter;
import ming.com.googleplay01.base.BaseFragment;
import ming.com.googleplay01.base.LoadingPage;
import ming.com.googleplay01.bean.ItemBean;
import ming.com.googleplay01.factory.ListViewFactory;
import ming.com.googleplay01.protocol.AppProtocol;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/15 13:15
 * 描述：    TODO
 */

public class AppFragment extends BaseFragment {

    private List<ItemBean> mItemBeanList;
    private AppProtocol    mAppProtocol;

    /**
     * 在在子线程中加载网络数据,并返回处理后的结果
     */
    @Override
    protected LoadingPage.LoadResultEnum loadNetData() {
        SystemClock.sleep(2000);

       /* LoadFragmentPageView.LoadResultEnum [] LoadResultarr = {LoadFragmentPageView.LoadResultEnum.EMPTY
                , LoadFragmentPageView.LoadResultEnum.ERROR, LoadFragmentPageView.LoadResultEnum.SUCCESS};*/
        //用协议去请求网络,加载数据
        mAppProtocol = new AppProtocol();

        try {
            //请求加载数据
            mItemBeanList = mAppProtocol.loadData(0);

            //校验list 是否有问题
            LoadingPage.LoadResultEnum resultEnum1 = checkNetData(mItemBeanList);

            return resultEnum1;


        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPage.LoadResultEnum.ERROR;

        }
    }

    /**
     * 提供一个成功的视图,并完成数据的绑定
     */
    @Override
    public View initSuccessView() {

        ListView listView = ListViewFactory.creatListView();

        AppAdapter appAdapter = new AppAdapter(mItemBeanList, listView);

        listView.setAdapter(appAdapter);

        return listView;
    }

    class AppAdapter extends ItemAdapter{

        public AppAdapter(List<ItemBean> list, ListView listView) {
            super(list, listView);
        }

        /**
         * 在子线程中加载更多数据
         */
        @Override
        public List<ItemBean> onLoadMoreData() throws Exception {

            SystemClock.sleep(2000);

            List<ItemBean> itemBeenList = mAppProtocol.loadData(mItemBeanList.size());

            return itemBeenList;

        }
    }
}
