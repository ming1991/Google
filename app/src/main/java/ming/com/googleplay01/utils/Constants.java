package ming.com.googleplay01.utils;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/14 19:52
 * 描述：    TODO
 */
public class Constants {

    /*
    LEVEL_OFF:关闭日志
    LEVEL_ALL:打开日志
     */
    public static final int DEBUGLEVEL = LogUtils.LEVEL_ALL;

    public static  class URLS {
        public static final String BASEURL = "http://10.0.2.2:8080/GooglePlayServer/";
        //"http://localhost:8080/GooglePlayServer/image?name="
        public static final String PIC_BASEURL = BASEURL + "image?name=";

    }
}
