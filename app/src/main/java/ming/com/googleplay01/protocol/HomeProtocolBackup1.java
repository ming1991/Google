package ming.com.googleplay01.protocol;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ming.com.googleplay01.bean.HomeBean;
import ming.com.googleplay01.utils.Constants;
import ming.com.googleplay01.utils.HttpUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/18 0:39
 * 描述：    完成homeFragment的网络请求的部分,
 */

public class HomeProtocolBackup1 {

    //异常选择抛出,调用者需要用到该结果

    public HomeBean loadHomeData() throws IOException {

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

            return homeBean;

        }

        return null;
    }
}
