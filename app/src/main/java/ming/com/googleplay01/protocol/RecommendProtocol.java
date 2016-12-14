package ming.com.googleplay01.protocol;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import ming.com.googleplay01.base.BaseProtocol;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/20 23:26
 * 描述：    TODO
 */
public class RecommendProtocol extends BaseProtocol<List<String>>{
    @Override
    protected List<String> parseData(Gson gson, String jsonString) {
        List<String> list = gson.fromJson(jsonString, new TypeToken<List<String>>() {
        }.getType());

        return list;
    }

    @NonNull
    @Override
    public String getInterfaceKey() {
        return "recommend";
    }
}
