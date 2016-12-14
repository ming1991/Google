package ming.com.googleplay01.fragement;

import android.os.SystemClock;
import android.view.View;
import android.widget.ListView;

import java.io.IOException;
import java.util.List;

import ming.com.googleplay01.base.BaseFragment;
import ming.com.googleplay01.base.BaseHolder;
import ming.com.googleplay01.base.LoadingPage;
import ming.com.googleplay01.base.SuperBaseAdapter;
import ming.com.googleplay01.bean.SubjectBean;
import ming.com.googleplay01.factory.ListViewFactory;
import ming.com.googleplay01.holder.SubjectHolder;
import ming.com.googleplay01.protocol.SubjectProtocol;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/15 13:15
 * 描述：    TODO
 */

public class SubjectFragment extends BaseFragment {

    private List<SubjectBean> mSubjectBeanList;
    private SubjectProtocol mSubjectProtcol;

    @Override
    protected LoadingPage.LoadResultEnum loadNetData() {
        SystemClock.sleep(2000);

        mSubjectProtcol = new SubjectProtocol();
        try {
            mSubjectBeanList = mSubjectProtcol.loadData(0);

            //校验数据
            LoadingPage.LoadResultEnum resultEnum = checkNetData(mSubjectBeanList);

            return resultEnum;

        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPage.LoadResultEnum.ERROR;
        }

    }

    @Override
    public View initSuccessView() {

        ListView listView = ListViewFactory.creatListView();
        SubjectAdapter subjectAdapter = new SubjectAdapter(mSubjectBeanList, listView);
        listView.setAdapter(subjectAdapter);

        return listView;
    }
    class SubjectAdapter extends SuperBaseAdapter<SubjectBean>{

        public SubjectAdapter(List<SubjectBean> list, ListView listView) {
            super(list, listView);
        }

        @Override
        public BaseHolder getSpecialBaseHolder(int position) {
            return new SubjectHolder();
        }

        /**
         * 加载更多数据
         * */
        @Override
        public boolean hasLoadMore() {
            return true;
        }

        @Override
        public List<SubjectBean> onLoadMoreData() throws Exception {
            SystemClock.sleep(2000);

            List<SubjectBean> subjectBeanList = mSubjectProtcol.loadData(mSubjectBeanList.size());

            return  subjectBeanList;
        }
    }
}
