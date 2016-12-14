package ming.com.googleplay01.fragement;

import android.os.SystemClock;
import android.view.View;
import android.widget.ListView;

import java.io.IOException;
import java.util.List;

import ming.com.googleplay01.adapter.ItemAdapter;
import ming.com.googleplay01.base.BaseFragment;
import ming.com.googleplay01.base.LoadingPage;
import ming.com.googleplay01.bean.HomeBean;
import ming.com.googleplay01.bean.ItemBean;
import ming.com.googleplay01.factory.ListViewFactory;
import ming.com.googleplay01.holder.HomePictureHolder;
import ming.com.googleplay01.protocol.HomeProtocol;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/15 13:15
 * 描述：    TODO
 */

public class HomeFragment extends BaseFragment {


    private List<ItemBean> mItemBeanList;
    private List<String> mPictureList;
    private HomeProtocol mHomeProtocol;


    /**
     * 该方法在子线程中执行,并还回请求的结果  三种 : 成功 ,空 ,异常
     */
    @Override
    protected LoadingPage.LoadResultEnum loadNetData() {

        //添加网络权限
        try {

            mHomeProtocol = new HomeProtocol();

            HomeBean homeBean = mHomeProtocol.loadData(0);

            //创建方法校验还回数据是否 为空(共性放在父类中完成)
            LoadingPage.LoadResultEnum resultEnum1 = checkNetData(homeBean);

            if (resultEnum1 != LoadingPage.LoadResultEnum.SUCCESS) { //homeBean有问题
                return resultEnum1;
            }

            //校验list 是否有问题
            LoadingPage.LoadResultEnum resultEnum2 = checkNetData(homeBean.list);

            if (resultEnum2 != LoadingPage.LoadResultEnum.SUCCESS) {  //list有问题
                return resultEnum2;
            }

            //经过上面的验证之后,说明list集合中有数据,可以给successView赋值了

            //保存数据
            mItemBeanList = homeBean.list;

            mPictureList = homeBean.picture;

            /*} else { //服务器有响应 ,但是报异常

                return LoadFragmentPageView.LoadResultEnum.ERROR;
            }*/

        } catch (IOException e) { //服务器无响应 ,报异常
            e.printStackTrace();
            return LoadingPage.LoadResultEnum.ERROR;

        }

        //获取数据成功
        return LoadingPage.LoadResultEnum.SUCCESS;

    }


    /**
     * 创建一个 成功视图,并绑定数据
     * */
    @Override
    public View initSuccessView() {

        ListView listView = ListViewFactory.creatListView();

        //添加一个无限自动轮播图viewPager
        HomePictureHolder homePictureHolder = new HomePictureHolder();
        View holderView = homePictureHolder.mHolderView;

        homePictureHolder.setViewDataAndFresh(mPictureList);
        //添加头到容器中
        listView.addHeaderView(holderView);


        HomeAdapter adapter = new HomeAdapter(mItemBeanList,listView);

        listView.setAdapter(adapter);

        return listView;
    }

    //泛型写在后面,应用泛型
    class HomeAdapter extends ItemAdapter {


        public HomeAdapter(List<ItemBean> list,ListView listView) {
            super(list,listView);
        }


       /*
         * 加载更多,请求网络加载,在子线程中执行,,并还回结果
         * */
        @Override
        public List<ItemBean> onLoadMoreData() throws Exception {

            SystemClock.sleep(2000);

            List<ItemBean> list = null;

            HomeBean homeBean = mHomeProtocol.loadData(mItemBeanList.size());//请求加载数据的url 关键字 随着集合的长度发生改变 0 --20  ---40--
            if (homeBean!=null){
                list = homeBean.list;
            }
            return list;
        }




    }
}
