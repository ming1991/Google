package ming.com.googleplay01.factory;

import ming.com.googleplay01.proxy.ThreadPoolProxy;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/17 20:33
 * 描述：    创建线程池代理的工厂   两类 普通的线程池代理 和 下载的线程池代理
 */

public class ThreadPoolProxyFactory {

    private static ThreadPoolProxy mNormalThreadPoolProxy;
    private static ThreadPoolProxy mDownLoadThreadPoolProxy;

    /**
     * 创建普通的线程池代理
     *同时可以拉取5个网络请求
     * 保证只创建一个,并考虑线程安全问题
     * */
    public static  ThreadPoolProxy creatNormalThreadPoolProxy(){

        if (mNormalThreadPoolProxy==null){

            synchronized (ThreadPoolProxyFactory.class){

                if (mNormalThreadPoolProxy==null) {

                    mNormalThreadPoolProxy = new ThreadPoolProxy(5);
                }
            }

        }
        return mNormalThreadPoolProxy;
    }

    /**
     * 创建下载的线程池代理
     *同时只能下载3个任务
     * 保证只创建一个,并考虑线程安全问题
     * */

    public static  ThreadPoolProxy creatDownLoadThreadPoolProxy(){

        if (mDownLoadThreadPoolProxy==null){

            synchronized (ThreadPoolProxyFactory.class){

                if (mDownLoadThreadPoolProxy==null) {

                    mDownLoadThreadPoolProxy = new ThreadPoolProxy(3);
                }
            }

        }
        return mDownLoadThreadPoolProxy;
    }

}
