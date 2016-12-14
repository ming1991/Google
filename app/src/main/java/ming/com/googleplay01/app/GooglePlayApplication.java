package ming.com.googleplay01.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/14 12:37
 * 描述：    TODO
 *
 * //初始化一些全局的变量
 */

public class GooglePlayApplication extends Application {

    private static Context mContext;
    private static Handler mHandler;
    private static Map<String, String> mCacheMap;

    @Override
    public void onCreate() {
        super.onCreate();

        //整个app的上下文
        mContext = getApplicationContext();

        //主线程的Handler
        mHandler = new Handler();

        //创建一个内存缓存的map集合
        mCacheMap = new HashMap<>();

    }

    public static  Map<String, String> getCacheMap(){
        return  mCacheMap;
    }

    public static Context getContext() {
        return mContext;
    }

    public static Handler getHandler() {
        return mHandler;
    }


}
