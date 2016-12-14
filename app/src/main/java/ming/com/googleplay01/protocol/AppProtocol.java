package ming.com.googleplay01.protocol;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import ming.com.googleplay01.base.BaseProtocol;
import ming.com.googleplay01.bean.ItemBean;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/19 16:00
 * 描述：    TODO
 */
public class AppProtocol extends BaseProtocol<List<ItemBean>>{
    @Override
    protected List<ItemBean> parseData(Gson gson, String jsonString) {

        //泛型解析 jsonString-->List<T>
        List<ItemBean> list = gson.fromJson(jsonString, new TypeToken<List<ItemBean>>() {
        }.getType());

        return list;
    }

    @NonNull
    @Override
    public String getInterfaceKey() {
        return "app";
    }
}
