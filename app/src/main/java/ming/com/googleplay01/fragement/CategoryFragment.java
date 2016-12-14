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
import ming.com.googleplay01.bean.CategoryBean;
import ming.com.googleplay01.factory.ListViewFactory;
import ming.com.googleplay01.holder.CategoryNormalHolder;
import ming.com.googleplay01.holder.CategoryTitleHolder;
import ming.com.googleplay01.protocol.CategoryProtocol;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/15 13:15
 * 描述：    TODO
 */

public class CategoryFragment extends BaseFragment {

    private List<CategoryBean> mCategoryBeanList;

    /**
     * 在子线程中加载数据,并处理结果
     * */
    @Override
    protected LoadingPage.LoadResultEnum loadNetData() {
        SystemClock.sleep(2000);

       CategoryProtocol categoryProtocol = new CategoryProtocol();
        try {
            mCategoryBeanList = categoryProtocol.loadData(0);
            LoadingPage.LoadResultEnum resultEnum = checkNetData(mCategoryBeanList);
            //检验结果
            return resultEnum;

        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPage.LoadResultEnum.ERROR;
        }

    }

    /**
     * 提供绑定数据的视图
     * */
    @Override
    public View initSuccessView() {
        ListView listView = ListViewFactory.creatListView();
        CategoryAdapter categoryAdapter = new CategoryAdapter(mCategoryBeanList, listView);
        listView.setAdapter(categoryAdapter);

        return listView;
    }

    class CategoryAdapter extends SuperBaseAdapter<CategoryBean>{

        public CategoryAdapter(List<CategoryBean> list, ListView listView) {
            super(list, listView);
        }

        @Override
        public BaseHolder getSpecialBaseHolder(int position) {
            CategoryBean categoryBean = mCategoryBeanList.get(position); //数据bean中已标记是否是title
            if (categoryBean.isTitle){
                return new CategoryTitleHolder();
            }else{
               return new CategoryNormalHolder();
            }

        }

        /**
         * 有不同的条目类型
         * */
        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount()+ 1;
        }

        /**
         * 默认有加载更多,只是视图影藏了
         * */
       /* @Override
        public int getItemViewType(int position) {
            if (position == (getCount() - 1)) {
                return VIEWTYPE_LOADMORE;  //加载更多的视图类型
            } else {
                //return VIEWTYPE_NORMAL;  //一般视图的样式
                CategoryBean categoryBean = mCategoryBeanList.get(position); //数据bean中已标记是否是title

                if (categoryBean.isTitle){
                    return 1;
                }else{
                    return 2;
                }
            }
        }*/


        /**
         * 除了下拉刷新,对外提供更多的条目类型,
         * */
        @Override
        public int getNormalViewType(int position) {
            //return VIEWTYPE_NORMAL;  //一般视图的样式
            CategoryBean categoryBean = mCategoryBeanList.get(position); //数据bean中已标记是否是title

            if (categoryBean.isTitle){
                return 1;
            }else{
                return 2;
            }
        }
    }

}
