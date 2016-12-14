package ming.com.googleplay01.protocol;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.util.Map;

import ming.com.googleplay01.base.BaseProtocol;
import ming.com.googleplay01.bean.DetailBean;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/21 10:59
 * 描述：    TODO
 */
public class DetailProtocol extends BaseProtocol<DetailBean>{

    private String mPackageName;

    public DetailProtocol(String packageName) {
        mPackageName = packageName;
    }

    @Override
    protected DetailBean parseData(Gson gson, String jsonString) {
        DetailBean detailBean = gson.fromJson(jsonString, DetailBean.class);
        return detailBean;
    }

    @NonNull
    @Override
    public String getInterfaceKey() {
        return "detail";
    }

    /**
     * 传入uil参数的键和值
     * */
    @Override
    public void getParams(int index, Map<String, Object> paramsMap) {

        paramsMap.put("packageName",mPackageName);
    }
}
