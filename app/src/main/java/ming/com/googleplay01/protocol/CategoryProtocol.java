package ming.com.googleplay01.protocol;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ming.com.googleplay01.base.BaseProtocol;
import ming.com.googleplay01.bean.CategoryBean;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/20 19:32
 * 描述：    TODO
 *
 * //利用listView实现gridview的效果
 * //采用节点解析
 */
public class CategoryProtocol extends BaseProtocol<List<CategoryBean>>{

    @Override
    protected List<CategoryBean> parseData(Gson gson, String jsonString) {
        List<CategoryBean> list = new ArrayList<>();
        //节点解析 ,采用sdk里面的json  import org.json.JSONArray;包名下
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);


                String title = jsonObject.getString("title");
                boolean isTitle = true; //标记为标题

                //当成一个bean存入集合中
                CategoryBean categoryBean = new CategoryBean();
                categoryBean.title=title;
                categoryBean.isTitle=isTitle;

                //添加集合
                list.add(categoryBean);

                JSONArray infosJSONArray = jsonObject.getJSONArray("infos");
                for (int j = 0; j < infosJSONArray.length(); j++) {
                    JSONObject infosJSONObject = infosJSONArray.getJSONObject(j);
                    String name1 = infosJSONObject.getString("name1");
                    String name2 = infosJSONObject.getString("name2");
                    String name3 = infosJSONObject.getString("name3");

                    String url1 = infosJSONObject.getString("url1");
                    String url2 = infosJSONObject.getString("url2");
                    String url3 = infosJSONObject.getString("url3");

                    //保存到bean中
                    CategoryBean bean = new CategoryBean();
                    bean.name1 = name1;
                    bean.name2 = name2;
                    bean.name3 = name3;

                    bean.url1 = url1;
                    bean.url2 = url2;
                    bean.url3 = url3;

                    //添加到集合中
                    list.add(bean);

                }

            }

            return  list;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @NonNull
    @Override
    public String getInterfaceKey() {
        return "category";
    }
}
