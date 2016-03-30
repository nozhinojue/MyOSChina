package com.example.cjj.myoschina.util;

import android.content.Context;
import android.net.ConnectivityManager;

import com.example.cjj.myoschina.MyApplication;

/**
 * Created by CJJ on 2016/3/29.
 */
public class TDevice {


    //判断网络是否可用。
    public static boolean hasInternet(){
        ConnectivityManager cm = (ConnectivityManager) MyApplication.context.getSystemService(Context.CONNECTIVITY_SERVICE);
       if(cm.getActiveNetworkInfo()!=null)
           return true;
        return false;
    }
}
