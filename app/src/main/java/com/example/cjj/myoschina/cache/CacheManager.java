package com.example.cjj.myoschina.cache;

import android.content.Context;
import android.os.AsyncTask;

import com.example.cjj.myoschina.util.TDevice;

import java.io.Serializable;

/**
 * Created by CJJ on 2016/4/4.
 */
public class CacheManager {
    // wifi缓存时间为5分钟
    private static int wifi_cache_time = 5*60;
    // 其他网络环境为1小时
    private static int other_cache_time = 60 * 60;

    private String cacheName="myoschinaCache"; //缓存文件名
    private static CacheManager cacheManager;
    private ACache aCache;
    private Context mContext;

    public CacheManager(Context context){
        mContext=context;
        aCache = ACache.get(context,cacheName);
    }

    public static CacheManager get(Context context){
        if(cacheManager==null){
            cacheManager= new CacheManager(context);
        }
        return cacheManager;
    }

    //保存对象
    public  void saveObject(String key,Serializable seri){
        new SaveCacheTask(mContext,key,seri,getCacheTime()).execute();
    }

    //获取对象
    public Object getObject(String key){
       return   aCache.getAsObject(key);
    }


    //获取缓存时间
    private  int getCacheTime(){
        if(TDevice.getNetworkType()==TDevice.NETTYPE_WIFI){
           return wifi_cache_time;
        }else{
            return  other_cache_time;
        }
    }

    //异步保存
    private class SaveCacheTask extends AsyncTask<Void, Void, Void> {
        private final Context mContext;
        private final Serializable seri;
        private final String key;
        private final int time;

        private SaveCacheTask(Context context,String key,Serializable seri,int time) {
            mContext = context;
            this.seri = seri;
            this.key = key;
            this.time=time;
        }

        @Override
        protected Void doInBackground(Void... params) {
            ACache.get(mContext).put(key,seri,time);
            return null;
        }
    }

}
