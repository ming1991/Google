package ming.com.googleplay01.factory;

import android.support.v4.app.Fragment;
import android.util.SparseArray;

import ming.com.googleplay01.base.BaseFragment;
import ming.com.googleplay01.fragement.AppFragment;
import ming.com.googleplay01.fragement.CategoryFragment;
import ming.com.googleplay01.fragement.GameFragment;
import ming.com.googleplay01.fragement.HomeFragment;
import ming.com.googleplay01.fragement.HotFragment;
import ming.com.googleplay01.fragement.RecommendFragment;
import ming.com.googleplay01.fragement.SubjectFragment;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/15 9:26
 * 描述：    TODO
 */
public class FragmentFactory {

    //建立视图缓存类
    public static SparseArray<BaseFragment> mSparseArray = new SparseArray<>();

    public static final int FRAGMENT_HOME      = 0;//首页
    public static final int FRAGMENT_APP       = 1;//应用
    public static final int FRAGMENT_GAME      = 2;//游戏
    public static final int FRAGMENT_SUBJECT   = 3;//专题
    public static final int FRAGMENT_RECOMMEND = 4;//推荐
    public static final int FRAGMENT_CATEGORY  = 5;//分类
    public static final int FRAGMENT_HOT        = 6;//排行
    
    public static Fragment creatFragment(int position) {

        if (mSparseArray.get(position)!=null&&mSparseArray.size()!=0){
            return  mSparseArray.get(position);
        }

        BaseFragment fragment = null;
        switch (position) {
            case FRAGMENT_HOME:
                fragment = new HomeFragment();
                break;
            case FRAGMENT_APP:
                fragment = new AppFragment();
                break;
            case FRAGMENT_GAME:
                fragment = new GameFragment();
                break;
            case FRAGMENT_SUBJECT:
                fragment = new SubjectFragment();
                break;
            case FRAGMENT_RECOMMEND:
                fragment = new RecommendFragment();
                break;
            case FRAGMENT_CATEGORY:
                fragment = new CategoryFragment();
                break;
            case FRAGMENT_HOT:
                fragment = new HotFragment();
                break;

            default:
                break;
        }

        //保存到缓存数组中
        mSparseArray.put(position,fragment);

        return  fragment;
    }
}
