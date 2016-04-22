package com.example.cjj.myoschina.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.cjj.myoschina.ui.activity.LoginActivity;
import com.example.cjj.myoschina.ui.activity.MyInfoDetailActivity;
import com.example.cjj.myoschina.ui.activity.TweetActivity;
import com.example.cjj.myoschina.zxing.activity.CaptureActivity;

/**
 * 界面帮助类
 * Created by CJJ on 2016/3/20.
 */
public class UIHelper {

    /**
     * 显示登录界面
     * @param context
     */
    public static void showLoginActivity(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    /**
     * 显示用户详细信息页面
     * @param context
     */
    public static void showInfoDetailActivity(Context context){
        Intent intent = new Intent(context, MyInfoDetailActivity.class);
        context.startActivity(intent);
    }

    /**
     * 显示扫码界面
     * @param context
     */
    public static void showScanActivity(Context context){
        Intent intent = new Intent(context, CaptureActivity.class);
        context.startActivity(intent);
    }


    /**
     * 显示动弹activity
     * @param context
     */
    public  static void showTweetActivity(Context context, Bundle bundle){
        Intent intent = new Intent(context, TweetActivity.class);
        intent.putExtra(TweetActivity.BUNDLE_KEY_ARGS,bundle);
        context.startActivity(intent);
    }

}
