package ming.com.googleplay01.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.util.List;
import java.util.Map;

import ming.com.googleplay01.utils.UIUtils;

/**
 * 创建者:   ming001
 * 创建时间: 所有fragment 的基类
 */

public abstract class BaseFragment extends Fragment {

    public LoadingPage mView;

    //onCreateView该方法被多次调用
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //避免LoadFragmentPageView重复被创建
        if (mView == null) {

            mView = new LoadingPage(UIUtils.getContext()) {
                @Override
                public View initSuccessView() {
                /*TextView textView = new TextView(UIUtils.getContext());
                textView.setText("成功视图");

                return textView;*/

                    return BaseFragment.this.initSuccessView();
                }

                @Override
                public LoadResultEnum loadNetData() {

              /*  Random random = new Random();
                int  i = random.nextInt(3);
                return i;*/

                    return BaseFragment.this.loadNetData();
                }
            };

        }else{
            //处理在2.3上面的兼容问题,重复添加相同的对象
            ViewParent parent = mView.getParent();
            if(parent instanceof  ViewGroup){
                ((ViewGroup)parent).removeView(mView);
            }
        }

        return mView;
    }

    /**
     * 子类在子线程中加载数据,并还回加载的结果状态
     * 三种状态 : 成功 为空 错误
     */
    protected abstract LoadingPage.LoadResultEnum loadNetData();

    /**
     * 基类不知道怎么执行,强制子类器执行的,加载成功视图,并绑定数据
     */
    public abstract View initSuccessView();


    /**
     * 检查网络还回的数据
     * */
    public  LoadingPage.LoadResultEnum checkNetData(Object object) {
        if (object==null){
            return LoadingPage.LoadResultEnum.EMPTY;
        }

        if (object instanceof List){
            if (((List) object).size()==0){
                return LoadingPage.LoadResultEnum.EMPTY;
            }
        }

        if (object instanceof Map){
            if (((Map) object).size()==0){
                return LoadingPage.LoadResultEnum.EMPTY;
            }
        }

        return LoadingPage.LoadResultEnum.SUCCESS;
    }
}
