package com.example.cjj.myoschina.ui;

import android.content.Context;
import android.content.Intent;

import com.example.cjj.myoschina.ui.activity.LoginActivity;

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
}
