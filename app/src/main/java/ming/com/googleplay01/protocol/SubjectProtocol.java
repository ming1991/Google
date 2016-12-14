package ming.com.googleplay01.protocol;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import ming.com.googleplay01.base.BaseProtocol;
import ming.com.googleplay01.bean.SubjectBean;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/19 23:09
 * 描述：    TODO
 */
public class SubjectProtocol extends BaseProtocol<List<SubjectBean>>{

    @Override
    protected List<SubjectBean> parseData(Gson gson, String jsonString) {

        //错误处 TypeToken<List<SubjectBean>> 与前面的变量类型应相同
        List<SubjectBean> list = gson.fromJson(jsonString, new TypeToken<List<SubjectBean>>(){}.getType());
        return list;
    }

    @NonNull
    @Override
    public String getInterfaceKey() {
        return "subject";
    }
}
