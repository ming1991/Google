package ming.com.googleplay01.protocol;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import ming.com.googleplay01.base.BaseProtocol;
import ming.com.googleplay01.bean.HomeBean;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/18 0:39
 * 描述：    完成homeFragment的网络请求的部分,并提供方法返回解析结果,
 */

public class HomeProtocol extends BaseProtocol<HomeBean> {

    @Override
    protected HomeBean parseData(Gson gson, String result) {

        HomeBean homeBean = gson.fromJson(result, HomeBean.class);

        return homeBean;
    }


    @NonNull
    @Override
    public String getInterfaceKey() {

        return "home";
    }

}
