package ming.com.googleplay01.fragement;

import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ming.com.googleplay01.base.BaseFragment;
import ming.com.googleplay01.base.LoadingPage;
import ming.com.googleplay01.base.MyBaseAdapter;
import ming.com.googleplay01.holder.ItemHolder;
import ming.com.googleplay01.utils.UIUtils;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/15 13:15
 * 描述：    TODO
 */

public class HomeFragmentBackUp1 extends BaseFragment {

    private List<String> mList;

    @Override
    protected LoadingPage.LoadResultEnum loadNetData() {
        SystemClock.sleep(2000);

       /* LoadFragmentPageView.LoadResultEnum [] LoadResultarr = {LoadFragmentPageView.LoadResultEnum.EMPTY
                , LoadFragmentPageView.LoadResultEnum.ERROR, LoadFragmentPageView.LoadResultEnum.SUCCESS};

        int i = new Random().nextInt(3);
        LoadFragmentPageView.LoadResultEnum resultEnum = LoadResultarr[i];*/

        //用listView模拟HomeFragment中的数据
        mList = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
            mList.add(i+"");
        }

        //获取数据成功
        return LoadingPage.LoadResultEnum.SUCCESS;
    }

    @Override
    public View initSuccessView() {

        ListView listView = new ListView(UIUtils.getContext());

        HomeAdapter adapter = new HomeAdapter(mList);

        listView.setAdapter(adapter);

        return listView;
    }

    //泛型写在后面,应用泛型
    class HomeAdapter extends MyBaseAdapter<String> {


        public HomeAdapter(List<String> list) {
            super(list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ItemHolder holder;
            if (convertView==null){
                //创建ViewHolder 对象
                holder = new ItemHolder();
/*
                //提供视图
                convertView = View.inflate(UIUtils.getContext(), R.layout.item_temp,null);

                //获取视图的子控件,初始化ViewHolder对象的成员变量
                holder.tv1 = (TextView) convertView.findViewById(R.id.tmp_tv_1);
                holder.tv2 = (TextView) convertView.findViewById(R.id.tmp_tv_2);

                //将ViewHolder与holder绑定
                convertView.setTag(holder);*/

            }else{
                //
                holder = (ItemHolder) convertView.getTag();
            }

            //给ViewHolder成员变量设置数据
          /*  holder.tv1.setText(mList.get(position)+"上");
            holder.tv2.setText(mList.get(position)+"下");*/


            //holder.setViewDataAndFresh(mList.get(position));

            return holder.mHolderView;
        }

      /*  class ViewHolder {
            TextView tv1;
            TextView tv2;
        }*/
    }
}
