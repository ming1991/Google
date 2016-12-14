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
import ming.com.googleplay01.protocol.GameProtocol;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/15 13:15
 * 描述：    TODO
 */

public class GameFragment extends BaseFragment {

    private List<ItemBean> mItemBeanList;
    private GameProtocol mGameProtocol;

    /**
     * 子线程中加载数据
     * */
    @Override
    protected LoadingPage.LoadResultEnum loadNetData() {
        SystemClock.sleep(2000);

        mGameProtocol = new GameProtocol();
        try {
            mItemBeanList = mGameProtocol.loadData(0);

            //检测结果
            return checkNetData(mItemBeanList);

        } catch (IOException e) {
            e.printStackTrace();
            return  LoadingPage.LoadResultEnum.ERROR;
        }

    }

    /**
     * 提供一个绑定数据的成功视图
     * */
    @Override
    public View initSuccessView() {
        ListView listView = ListViewFactory.creatListView();
        GameAdapter gameAdapter = new GameAdapter(mItemBeanList, listView);
        listView.setAdapter(gameAdapter);

        return listView;
    }

    class GameAdapter extends ItemAdapter {

        public GameAdapter(List<ItemBean> list, ListView listView) {
            super(list, listView);
        }


        /**
         * 在子线程中加载更多数据
         */
        @Override
        public List<ItemBean> onLoadMoreData() throws Exception {
            SystemClock.sleep(2000);

            List<ItemBean> list = mGameProtocol.loadData(mItemBeanList.size());

            return list;
        }
    }
}
