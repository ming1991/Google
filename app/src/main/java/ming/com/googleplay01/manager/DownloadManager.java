package ming.com.googleplay01.manager;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import ming.com.googleplay01.bean.DownLoadInfo;
import ming.com.googleplay01.factory.ThreadPoolProxyFactory;
import ming.com.googleplay01.utils.Constants;
import ming.com.googleplay01.utils.HttpUtils;
import ming.com.googleplay01.utils.IOUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/22 20:59
 * 描述：    TODO 具体负责下载的任务
 * 1.时刻记录当前的状态
 * 2.
 */

public class DownloadManager {

    public static final int STATE_UNDOWNLOAD      = 0;//未下载
    public static final int STATE_DOWNLOADING     = 1;//下载中
    public static final int STATE_PAUSEDOWNLOAD   = 2;//暂停下载
    public static final int STATE_WAITINGDOWNLOAD = 3;//等待下载
    public static final int STATE_DOWNLOADFAILED  = 4;//下载失败
    public static final int STATE_DOWNLOADED      = 5;//下载完成
    public static final int STATE_INSTALLED       = 6;//已安装

    //public int currentState = STATE_UNDOWNLOAD;

    //单利
    private static DownloadManager instance;
    private DownloadManager(){}

    //获取实例
    public static DownloadManager getInstance(){
        if (instance==null){
            synchronized (DownloadManager.class){
                if (instance==null){
                    instance = new DownloadManager();
                }
            }
        }
        return instance;
    }

    /**
     * 触发下载的任务
     *
     * @param downLoadInfo
     */

    public void  download(DownLoadInfo downLoadInfo){
        /*-----------------------*/
        downLoadInfo.currentState = STATE_UNDOWNLOAD;

        /*---------------------------------*/
        downLoadInfo.currentState = STATE_WAITINGDOWNLOAD;

        DownloadTask downloadTask = new DownloadTask(downLoadInfo);

        ThreadPoolProxyFactory.creatDownLoadThreadPoolProxy().submit(downloadTask);


    }
    class DownloadTask implements Runnable{

        private DownLoadInfo mDownLoadInfo;

        public DownloadTask(DownLoadInfo downLoadInfo) {

            mDownLoadInfo = downLoadInfo;
        }

        @Override
        public void run() {

            /*-------------------*/
            mDownLoadInfo.currentState = STATE_DOWNLOADING;

            InputStream inputStream = null;
            BufferedOutputStream bos = null;

            try {
                OkHttpClient httpClient = new OkHttpClient();
               // http://localhost:8080/GooglePlayServer/download?name=app/com.itheima.www/com.itheima.www.apk&range=0

                String url = mDownLoadInfo.url;
                Map<String,Object> parametersMap = new HashMap<>();
                parametersMap.put("name",mDownLoadInfo.url);
                parametersMap.put("range",0);
                String parameters = HttpUtils.getUrlParamsByMap(parametersMap);

                url = Constants.URLS.BASEURL + "download?" + parameters;

                String savePath = mDownLoadInfo.savePath;

                Request request = new Request.Builder()
                        .get()
                        .url(url)
                        .build();
                Response response = httpClient.newCall(request).execute();
                if (response.isSuccessful()){
                    inputStream = response.body().byteStream();
                    bos = new BufferedOutputStream(new FileOutputStream(new File(savePath)));

                    byte [] buff = new byte[1024];
                    int len ;
                    while((len=inputStream.read(buff))!=-1){
                        bos.write(buff,0,len);

                        /*-------------------*/
                        mDownLoadInfo.currentState = STATE_DOWNLOADING;
                    }

                    /*-------------------*/
                    mDownLoadInfo.currentState = STATE_DOWNLOADED;
                }else{
                     /*-------------------*/
                    mDownLoadInfo.currentState = STATE_DOWNLOADFAILED;

                }

            } catch (IOException e) {
                e.printStackTrace();
                 /*-------------------*/
                mDownLoadInfo.currentState = STATE_DOWNLOADFAILED;

            } finally {
                IOUtils.close(bos);
                IOUtils.close(inputStream);
            }


        }
    }


}
