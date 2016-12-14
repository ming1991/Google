package ming.com.googleplay01.fragement;

import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ming.com.googleplay01.base.BaseFragment;
import ming.com.googleplay01.base.BaseHolder;
import ming.com.googleplay01.base.LoadingPage;
import ming.com.googleplay01.base.SuperBaseAdapter;
import ming.com.googleplay01.bean.HomeBean;
import ming.com.googleplay01.bean.ItemBean;
import ming.com.googleplay01.holder.ItemHolder;
import ming.com.googleplay01.utils.Constants;
import ming.com.googleplay01.utils.HttpUtils;
import ming.com.googleplay01.utils.UIUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/15 13:15
 * 描述：    TODO
 */

public class HomeFragmentBackup2 extends BaseFragment {


    private List<ItemBean> mItemBeanList;
    private List<String> mPictureList;


    /**
     * 该方法在子线程中执行,并还回请求的结果  三种 : 成功 ,空 ,异常
     */
    @Override
    protected LoadingPage.LoadResultEnum loadNetData() {

        //添加网络权限
        try {
            //1.使用okhttp 请求网络,获取数据
            OkHttpClient okHttpClient = new OkHttpClient();

            //http://localhost:8080/GooglePlayServer/home?index=0
            String url = Constants.URLS.BASEURL + "home";

            //定义一个map装参数
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("index", "0");
            //paramsMap-->"index=0"
            String urlParamsByMap = HttpUtils.getUrlParamsByMap(paramsMap);

            url = url + "?" + urlParamsByMap;

           // url = Constants.URLS.BASEURL + "home?index=0";

            //2.创建一个请求
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            //3.发起请求
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {  //服务器有响应

                String result = response.body().string();
                //LogUtils.d(string);
                Gson gson = new Gson();
                HomeBean homeBean = gson.fromJson(result, HomeBean.class);

                //创建方法校验还回数据是否 为空
                LoadingPage.LoadResultEnum resultEnum1 = checkNetData(homeBean);

                if (resultEnum1!= LoadingPage.LoadResultEnum.SUCCESS){ //homeBean有问题
                    return resultEnum1;
                }

                //校验list 是否有问题
                LoadingPage.LoadResultEnum resultEnum2 = checkNetData(homeBean.list);

                if (resultEnum2!= LoadingPage.LoadResultEnum.SUCCESS){  //list有问题
                    return resultEnum2;
                }

                //经过上面的验证之后,说明list集合中有数据,可以给successView赋值了

                //保存数据
                mItemBeanList = homeBean.list;

                mPictureList = homeBean.picture;

            } else { //服务器有响应 ,但是报异常

                return LoadingPage.LoadResultEnum.ERROR;
            }

        } catch (IOException e) { //服务器无响应 ,报异常
            e.printStackTrace();
            return LoadingPage.LoadResultEnum.ERROR;

        }

        //获取数据成功
        return LoadingPage.LoadResultEnum.SUCCESS;

    }



    @Override
    public View initSuccessView() {

        ListView listView =  new ListView(UIUtils.getContext());

        HomeAdapter adapter = new HomeAdapter(mItemBeanList,listView);

        listView.setAdapter(adapter);

        return listView;
    }

    //泛型写在后面,应用泛型
    class HomeAdapter extends SuperBaseAdapter<ItemBean> {


        public HomeAdapter(List<ItemBean> list,ListView listView) {
            super(list,listView);
        }

        @Override
        public BaseHolder getSpecialBaseHolder(int position) {
            return new ItemHolder();
        }


    }
}
