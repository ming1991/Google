package ming.com.googleplay01.base;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ming.com.googleplay01.utils.Constants;
import ming.com.googleplay01.utils.FileUtils;
import ming.com.googleplay01.utils.HttpUtils;
import ming.com.googleplay01.utils.IOUtils;
import ming.com.googleplay01.utils.UIUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 创建者:   ming001
 * 创建时间: 2016/10/18 1:02
 * 描述：    所有Protocol的基类 ,完成网络请求,并还回bean对象
 *
 * 建立协议的三级缓存 ,内存,本地,网络
 */

public abstract class BaseProtocol<BaseType> {
    //协议保存到本地文件的有效时长
    private static final long PROTOCOL_TIMEOUT = 5*60*1000;

    //异常选择抛出,调用者需要用到该结果


    public BaseType loadData(int index) throws IOException {

        BaseType baseType = null;

        //1.先从内存缓存中获取数据
        baseType = LoadDataFromMen(index);

        if (baseType!=null){
            return  baseType;
        }

        //2.从本地缓存中获取数据
        baseType = LoadDataFromLocal(index);

        if (baseType!=null){
            return  baseType;
        }


        //3.从网络加载数据
        baseType =  LoadDataFromNet(index
        );

        if (baseType!=null){
            return  baseType;
        }

        return  null;


    }

    /**
     * //3.从网络加载数据
     * */
    private BaseType LoadDataFromNet(int index) throws IOException {

        BaseType baseType;//1.使用okhttp 请求网络,获取数据

        OkHttpClient okHttpClient = new OkHttpClient();

        //http://localhost:8080/GooglePlayServer/home?index=0
        //基类中 home 要变成动态的变量
        String url = Constants.URLS.BASEURL + getInterfaceKey();

        //定义一个map装参数
        Map<String, Object> paramsMap = new HashMap<>();

        //由外界传入参数,提高通用性

        getParams(index, paramsMap);

        //paramsMap-->"index=0"
        String urlParamsByMap = HttpUtils.getUrlParamsByMap(paramsMap);

        url = url + "?" + urlParamsByMap;

        // url = Constants.URLS.BASEURL + "home?index=0";

        //2.创建一个请求
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();

        //3.发起请求
        Response response = okHttpClient.newCall(request).execute(); //在子线程中执行,同步方法

        if (response.isSuccessful()) {  //服务器有响应

            String jsonString = response.body().string();

            /*----------------缓存协议到本地-------------------*/
            write2Local(index,jsonString);


             /*----------------缓存协议到内存-------------------*/
            write2Mem(index,jsonString);

            //LogUtils.d(string);
            Gson gson = new Gson();
           // HomeBean homeBean = gson.fromJson(result, HomeBean.class);

            //可能解析的结果是bean对象和list对象
           baseType =  parseData(gson,jsonString);

            return baseType;

        }

        return null;
    }

    /**
     * 选择实现
     * 参数默认是"index", index + ""
     * 子类可以修改参数
     * */
    public void getParams(int index, Map<String, Object> paramsMap) {
        paramsMap.put("index", index + "");
    }

    /***
     *
     * 从本地缓存中获取数据
     * 需要判断有效时间 (定义5分钟)
     * @param index
     */

    private BaseType LoadDataFromLocal(int index) {
        //获取缓存的文件
        File file = getCacheFile(index);
        BufferedReader br = null;
        if (file.exists()){
            try {
               br = new BufferedReader(new FileReader(file));
                String stringTime = br.readLine();
                long saveTime = Long.valueOf(stringTime);
                if (System.currentTimeMillis()-saveTime<PROTOCOL_TIMEOUT){

                    //获取,并解析缓存的协议内容
                    String jsonString = br.readLine();

                      /*----------------缓存协议到内存-------------------*/
                    write2Mem(index,jsonString);

                    //解析并还回数据类型
                    Gson gson = new Gson();
                    BaseType baseType = parseData(gson, jsonString);

                    return  baseType;

                }

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                IOUtils.close(br);
            }
        }


        return null;
    }

    /**
     * 从内存中加载数据 //不需要考虑过期的问题
     *
     * @param index*/
    private BaseType LoadDataFromMen(int index) {

        //保存到全局的map中
        Map<String, String> cacheMap = UIUtils.getCacheMap();
        String key = generateKey(index);

        if (cacheMap.containsKey(key)){
            String jsonString = cacheMap.get(key);

            //用Gson解析数据类型
            Gson gson = new Gson();
            BaseType baseType = parseData(gson, jsonString);
            return  baseType;
        }
        return null;
    }

    /**
     * 缓存协议到内存中
     * */
    private void write2Mem(int index, String jsonString) {
       // mCacheMap

        //generateKey
        Map<String, String> cacheMap = UIUtils.getCacheMap();

        //键interfacekey+"."+index ,值 协议内容
        cacheMap.put(generateKey(index),jsonString);

    }

    /*1. 数据按照什么形式存储?  存一个file
    2. 数据存到哪里?  sdcard/Android/data/包名/json
    3. 如何保证数据的唯一性(缓存的唯一命中)? 一对一的关系,一条协议,对应一个文件 文件名形式:interfacekey+"."+index
    4. 保证数据的时效性? 是否过期   在文件的第一行写入缓存插入时间  	第二行写入具体内容*/
    /**
     * 将网络协议缓存到本地,
     * file
     *
     * */
    private void write2Local(int index, String jsonString) {
        //获取缓存到本地的文件
        File file = getCacheFile(index);
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file));
            long currentTeime = System.currentTimeMillis();

            //记录写入的时间
            bw.write(currentTeime+"");
            //换行
            bw.newLine();
            //写入协议的内容
            bw.write(jsonString);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关流
            IOUtils.close(bw);
        }

    }

    /**
     * 获取缓存到本地的文件
     * */
    @NonNull
    private File getCacheFile(int index) {
        String dir = FileUtils.getDir("json"); //sdcard/Android/data/包目录/json 获取存储的目录
        String fileName = generateKey(index); //获取文件名
        return new File(dir, fileName);
    }

    /**
     * 生成缓存协议的key(文件名,map的键值)
     * */
    @NonNull
    private String generateKey(int index) {
        return getInterfaceKey() + "." + index;
    }

    /**
     * 解析数据交给子类完成
     *
     * @param gson
     * @param jsonString*/
    protected abstract BaseType parseData(Gson gson, String jsonString);

    /**
     * @return
     * @des 得到协议对应的关键字
     * @des 在BaseProtocl中不知道具体的协议关键字是啥, 交给子类
     * @des 子类是必须实现, 定义成为抽象方法即可
     */
    @NonNull
    public abstract String getInterfaceKey();
}


/**
 *
 * //基类中 home 要变成动态的变量
 *
 * 三种实现 方法
 * 1.直接调用方法
 * 2.通过构造方法传参数
 * 3.子类覆写相应的方法
 * */
