package ming.com.googleplay01.fragement;

import android.graphics.Color;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import ming.com.googleplay01.base.BaseFragment;
import ming.com.googleplay01.base.LoadingPage;
import ming.com.googleplay01.protocol.RecommendProtocol;
import ming.com.googleplay01.utils.UIUtils;
import ming.com.googleplay01.view.flyinflyout.StellarMap;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/15 13:15
 * 描述：    TODO
 */

public class RecommendFragment extends BaseFragment {

    private List<String> mStringList;

    @Override
    protected LoadingPage.LoadResultEnum loadNetData() {
        SystemClock.sleep(2000);

        RecommendProtocol recommendProtocol = new RecommendProtocol();
        try {
            mStringList = recommendProtocol.loadData(0);
            LoadingPage.LoadResultEnum resultEnum = checkNetData(mStringList);

            return resultEnum;
        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPage.LoadResultEnum.ERROR;
        }

    }

    @Override
    public View initSuccessView() {
        StellarMap stellarMap = new StellarMap(UIUtils.getContext());

        RecommendAdapter recommendAdapter = new RecommendAdapter();
        stellarMap.setAdapter(recommendAdapter);

        //处理两个小问题
        stellarMap.setGroup(0, true);  //指定显示第一页
        stellarMap.setRegularity(15, 20); //分格显示 300个格子

        return stellarMap;
    }

    class RecommendAdapter implements StellarMap.Adapter {
        private static final int PAGESIZE = 15; //每页15个文本

        //根据数据集获取相应的数据
        @Override
        public int getGroupCount() {   //获取分组数
            if (mStringList.size()%PAGESIZE==0){  //有无余数
                return  mStringList.size()/PAGESIZE;
            }else{
                return mStringList.size()/PAGESIZE+1;
            }
        }

        @Override
        public int getCount(int group) {  //获取每组中条目的个数
            if (mStringList.size()%PAGESIZE==0){
                return PAGESIZE;
            }else{
                if (group==getGroupCount()-1){ //最后一组
                    //余数
                    return mStringList.size()%PAGESIZE;
                }else{
                    return PAGESIZE;
                }
            }
        }

        //注意position 的含义
        @Override
        public View getView(int group, int position, View convertView) { //获取对应组中对应位置的条目的view
            position = PAGESIZE*group + position;
             TextView textView = new TextView(UIUtils.getContext());
              textView.setTextColor(Color.BLUE);
              textView.setText(mStringList.get(position));

            //控制textivew的样式
            //随机大小(12-16)
            Random random = new Random();
//            tv.setTextSize(random.nextInt(大小差值+1) + 最小值);
            int i = random.nextInt(5) + 12;
            textView.setTextSize(i);

            //颜色  //100-200
            int alpha = 255;
            int red = random.nextInt(101)+100;
            int green = random.nextInt(101)+100;
            int blue = random.nextInt(101)+100;
            int color = Color.argb(alpha,red,green,blue);
            textView.setTextColor(color);

            return textView;
        }

        @Override
        public int getNextGroupOnPan(int group, float degree) {
            return 0;
        }

        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            return 0;
        }

    }
}
