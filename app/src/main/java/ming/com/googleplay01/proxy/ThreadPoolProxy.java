package ming.com.googleplay01.proxy;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 代理模式
 * 创建者:   ming001
 * 创建时间: 2016/10/17 19:38
 * 描述：    线程池的代理,替线程池完成一些操作,  任务的提交,执行,取消
 */

public class ThreadPoolProxy {
    private ThreadPoolExecutor mThreadPoolExecutor;
    private int mCorePoolSize;

    //核心数由调用者决定
    public ThreadPoolProxy(int corePoolSize) {
        mCorePoolSize = corePoolSize;
    }

    //初始线程池
    private void initThreadPoolExecutor() {

        //任务队列 设置成 无界队列corePoolSize和maximumPoolSize相等之后 就不再考虑额外线程的存活时间和异常捕捉器

        //保证只创建一个线程池
        //考虑多线程并发 执行的的问题,会创建多个线程池,加同步锁
        //线程池没创建   线程池关闭  线程池任务执行完 都需要创建线程池
        if (mThreadPoolExecutor == null || mThreadPoolExecutor.isShutdown() || mThreadPoolExecutor.isTerminated()) {

            synchronized (ThreadPoolProxy.class) {

                if (mThreadPoolExecutor == null || mThreadPoolExecutor.isShutdown() || mThreadPoolExecutor.isTerminated()) {

                    int corePoolSize = mCorePoolSize;
                    int maximumPoolSize = mCorePoolSize;
                    long keepAliveTime = 0;
                    TimeUnit unit = TimeUnit.MILLISECONDS;
                    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
                    ThreadFactory threadFactory = Executors.defaultThreadFactory();
                    RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();
                    mThreadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
                }
            }
        }
    }

    //提交任务
    public Future<?> submit(Runnable task) {
        //初始化线程池
        initThreadPoolExecutor();

        //有返回值 future 描述异步执行完成之后的结果
        Future<?> future = mThreadPoolExecutor.submit(task);

        return future;

    }

    //执行任务
    public void execute(Runnable task) {
        initThreadPoolExecutor();
        mThreadPoolExecutor.execute(task);
    }


    //取消任务
    public void cancle(Runnable task) {
        initThreadPoolExecutor();
        mThreadPoolExecutor.remove(task);

    }


}
