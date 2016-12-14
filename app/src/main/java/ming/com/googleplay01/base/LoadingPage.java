package ming.com.googleplay01.base;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import ming.com.googleplay01.R;
import ming.com.googleplay01.factory.ThreadPoolProxyFactory;
import ming.com.googleplay01.utils.UIUtils;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/15 12:57
 * 描述：    加载fragment显示的视图,
 * 分 加载中, 加载失败 ,为空, 加载成功 四种状态
 *
 * 作用:
 * 1.提供视图
 * 2.加载数据
 * 3.数据和视图的绑定
 */
/*###loadingPager几个细节优化
        1. 数据触发加载时机
        2. LoadingPager首页未加载
        3. LoadingPager重复创建-->保证某一个类的对象只被创建一次
        4. 开始加载数据的时候应该显示加载中视图
        5. 加载成功无需再次加载
        6. 加载中无需再次加载
        7. 错误页面点击重试*/

public abstract class LoadingPage extends FrameLayout {

    private static final int LOADING_STATE = 0;
    private static final int EMPTY_STATE = 1;
    private static final int ERROR_STATE = 2;
    private static final int SUCCESS_STATE = 3;

    //默认显示加载中的视图
    private int currentState = LOADING_STATE;

    private View mLoadingView;
    private View mEmptyView;
    private View mErrorView;
    private View mSuccessView;
    private LoadNetDataTask mTask;

    public LoadingPage(Context context) {
        this(context,null);
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView();

    }

    private void initView() {

        mLoadingView = View.inflate(UIUtils.getContext(), R.layout.pager_loading, null);
        addView(mLoadingView);

        mEmptyView = View.inflate(UIUtils.getContext(), R.layout.pager_empty, null);
        addView(mEmptyView);

        mErrorView = View.inflate(UIUtils.getContext(), R.layout.pager_error, null);
        addView(mErrorView);

        //错误页面点击重试
        Button btn_retry = (Button) mErrorView.findViewById(R.id.error_btn_retry);
        btn_retry.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                triggerLoadData();
            }
        });

        //根据条件具体显示那个view
        switchFragmentView();

    }

    private void switchFragmentView() {
        //先显示所有的fragment 再显示具体的那个fragment
        mLoadingView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);

        if (mSuccessView!=null){
            mSuccessView.setVisibility(View.GONE);
        }
        
        switch (currentState){
            case LOADING_STATE:
                mLoadingView.setVisibility(View.VISIBLE);

                break;
            case EMPTY_STATE:
                mEmptyView.setVisibility(View.VISIBLE);

                break;
            case ERROR_STATE:
                mErrorView.setVisibility(View.VISIBLE);

                break;
            case SUCCESS_STATE:
                if (mSuccessView==null){

                    //创建成功视图
                    mSuccessView = initSuccessView();

                    addView(mSuccessView);
                }

                mSuccessView.setVisibility(View.VISIBLE);

                break;
            default:
                break;
        }
    }

    /**
    *基类不知道怎么执行,强制子类器执行的,加载成功视图,并绑定数据
    */
    public abstract View initSuccessView();


    /**
     * 触发加载数据
     * 对外提供方法,触发加载数据, 加载时机,后面确定
     * 1.开闭子线程记载数据
     * 2.处理数据
     * 3.更新UI
     *
     *
     * */
    public  void triggerLoadData(){

        //加载成功无需再次加载
        if (currentState==SUCCESS_STATE){
            return;
        }

        //加载中无需再次加载
        if (mTask==null){

            //点击切换页面才次加载数据的时候,显示加载中的视图
            currentState = LOADING_STATE;
            //刷新视图
            switchFragmentView();

            mTask = new LoadNetDataTask();

           // new Thread(mTask).start();

            //使用线程池工厂 创建线程代理  执行任务
            ThreadPoolProxyFactory.creatNormalThreadPoolProxy().submit(mTask);
        }


    }

    class LoadNetDataTask implements  Runnable{

        @Override
        public void run() {

            LoadResultEnum loadResultEnum = loadNetData();

            //根据子类加载数据的结果 刷新UI
            currentState = loadResultEnum.state;

            //主线程中更新UI
            Handler handler = UIUtils.getHandler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    switchFragmentView();
                }
            });

            mTask=null;

        }
    }

    /**
     * 子类在子线程中加载数据,并还回加载的结果状态
     * 三种状态 : 成功 为空 错误
     * */
    public abstract LoadResultEnum loadNetData();

    /**
     * 定义枚举类,限定加载网络数据还回值的数据;
     * */

    public enum  LoadResultEnum {
       /* private static final int EMPTY_STATE = 1;
        private static final int ERROR_STATE = 2;
        private static final int SUCCESS_STATE = 3;*/

        //每个枚举项就是枚举类的一个实例(括号里面的变量就是枚举类的成员变量)
       EMPTY(EMPTY_STATE),ERROR(ERROR_STATE),SUCCESS(SUCCESS_STATE);

        public int state;

        //枚举的构造方法必须私有
       LoadResultEnum (int state){
            this.state = state;
        }

    }
   /* ###27.13_JDK5新特性(自己实现枚举类)
    * A:枚举概述
    * 是指将变量的值一一列出来,变量的值只限于列举出来的值的范围内。举例：一周只有7天，一年只有12个月等。
            * B:回想单例设计模式：单例类是一个类只有一个实例
    * 那么多例类就是一个类有多个实例，但不是无限个数的实例，而是有限个数的实例。这才能是枚举类。*/

   /* ###27.15_JDK5新特性(枚举的注意事项)
    * A:案例演示
    * 定义枚举类要用关键字enum
    * 所有枚举类都是Enum的子类
    * 枚举类的第一行上必须是枚举项，最后一个枚举项后的分号是可以省略的，但是如果枚举类有其他的东西，这个分号就不能省略。建议不要省略
    * 枚举类可以有构造器，但必须是private的，它默认的也是private的。
            * 枚举类也可以有抽象方法，但是枚举项必须重写该方法
    * 枚举在switch语句中的使用*/
}


