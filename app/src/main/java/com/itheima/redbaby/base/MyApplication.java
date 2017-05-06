package com.itheima.redbaby.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

import com.android.volley.toolbox.ImageLoader;

import org.senydevpkg.net.HttpLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * .::::.
 * .::::::::.
 * :::::::::::
 * ..:::::::::::'
 * '::::::::::::'
 * .::::::::::
 * '::::::::::::::..
 * ..::::::::::::.
 * ``::::::::::::::::
 * ::::``:::::::::'        .:::.
 * ::::'   ':::::'       .::::::::.
 * .::::'      ::::     .:::::::'::::.
 * .:::'       :::::  .:::::::::' ':::::.
 * .::'        :::::.:::::::::'      ':::::.
 * .::'         ::::::::::::::'         ``::::.
 * ...:::           ::::::::::::'              ``::.
 * ```` ':.          ':::::::::'                  ::::..
 * '.:::::'                    ':'````..
 * Created by lx on 2016/11/14.
 * 由于应用中处处会用到Context和Handler，
 * 因此我们在base包下创建一个MyApplication，继承自Android的Application，
 * 在Application的onCreate方法中初始化一些公共的变量，比如我们的Context和Handler。
 * 注意全局单例西需要在清单文件中配置,当系统读取清单文件时,会选择以自己创建的这个类为程序入口而进入
 * 从而在程序的一开始就加载所需的上下文,和handler
 * 该类为线程的入口,
 * 随着软件的开启而存在,退出而消失
 */
public class MyApplication extends Application {

    public static Context mContext;
    public static Handler mMainThreadHandler;
    public static int mMainThreadId;

    public static HttpLoader HL;
    public static ImageLoader IL;

    public static final int STATE_EMPTY = 0;
    public static final int STATE_FULL = 1;
    public static int mCurState = STATE_FULL;//默认是有数据的情况



    /**
     * 声明一个内存缓存的集合,用来存放内存缓存的数据
     */
    private Map<String, String> mMemProtocolCacheMap = new HashMap<>();

    public Map<String, String> getMemProtocolCacheMap() {
        return mMemProtocolCacheMap;
    }

    @Override
    public void onCreate() { //程序的入口方法
        //上下文
        mContext = getApplicationContext();
        //主线程的handler
        mMainThreadHandler = new Handler();
        //获取主线程id;
        mMainThreadId = Process.myTid();
        super.onCreate();

        HL = HttpLoader.getInstance(mContext);
        IL = HL.getImageLoader();
    }
    /**得到上下文*/
    public static Context getContext() {
        return mContext;
    }

    /**得到主线程id*/
    public static Handler getmMainThreadHandler() {
        return mMainThreadHandler;
    }

    /**得到主线程id**
     * 得到主线程id**
     * **/
    public static int getMainThreadId() {
        return mMainThreadId;
    }

}
