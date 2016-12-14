package ming.com.googleplay01.bean;

import java.util.List;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/21 11:01
 * 描述：    TODO
 */

public class DetailBean {

    /**
     * author : 黑马程序员
     * date : 2015-06-10
     * des : 黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员
     * downloadNum : 40万+
     * downloadUrl : app/com.itheima.www/com.itheima.www.apk
     * iconUrl : app/com.itheima.www/icon.jpg
     * id : 1631363
     * name : 黑马程序员
     * packageName : com.itheima.www
     * safe : [{"safeDes":"已通过安智市场安全检测，请放心使用","safeDesColor":0,"safeDesUrl":"app/com.itheima.www/safeDesUrl0.jpg","safeUrl":"app/com.itheima.www/safeIcon0.jpg"},{"safeDes":"无任何形式的广告","safeDesColor":0,"safeDesUrl":"app/com.itheima.www/safeDesUrl1.jpg","safeUrl":"app/com.itheima.www/safeIcon1.jpg"}]
     * screen : ["app/com.itheima.www/screen0.jpg","app/com.itheima.www/screen1.jpg","app/com.itheima.www/screen2.jpg","app/com.itheima.www/screen3.jpg","app/com.itheima.www/screen4.jpg"]
     * size : 91767
     * stars : 5.0
     * version : 1.1.0605.0
     */

    //需要手动修改一些参数的类型

    public String author;
    public String date;
    public String des;
    public String downloadNum;
    public String downloadUrl;
    public String iconUrl;
    public int id;
    public String name;
    public String packageName;
    public long size;
    public float stars;
    public String version;
    /**
     * safeDes : 已通过安智市场安全检测，请放心使用
     * safeDesColor : 0
     * safeDesUrl : app/com.itheima.www/safeDesUrl0.jpg
     * safeUrl : app/com.itheima.www/safeIcon0.jpg
     */

    public List<SafeBean> safe;
    public List<String> screen;

    public static class SafeBean {
        public String safeDes;
        public int safeDesColor;
        public String safeDesUrl;
        public String safeUrl;
    }
}
