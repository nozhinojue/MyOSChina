package com.example.cjj.myoschina;

import android.app.Application;
import android.content.Context;

import com.example.cjj.myoschina.api.okhttp.OkHttpUtil;
import com.example.cjj.myoschina.api.okhttp.cookie.CookieJarImpl;
import com.example.cjj.myoschina.api.okhttp.cookie.store.PersistentCookieStore;

/**
 * Created by CJJ on 2016/3/28.
 */
public class MyApplication extends Application {
    public static Context context;
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        instance=this;

        //设置okhttp的cookie持久化管理。
       OkHttpUtil.getInstance().setCookieJar(new CookieJarImpl(new PersistentCookieStore(context)));
    }

    public static MyApplication getInstance(){
        return instance;
    }


}
