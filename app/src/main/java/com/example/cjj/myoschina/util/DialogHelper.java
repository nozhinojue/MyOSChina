package com.example.cjj.myoschina.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * 对话框辅助类
 * Created by CJJ on 2016/4/22.
 */
public class DialogHelper {

    /**
     * 获取dialogbuilder
     * @param context
     * @return
     */
    public static AlertDialog.Builder getDialogBuilder(Context context){
        AlertDialog.Builder builder= new AlertDialog.Builder(context);
        return builder;
    }


    /**
     * 获取确认对话框框
     * @param context
     * @param msg
     * @param onOkClickListener
     * @param onCancelClickListener
     * @return
     */
    public static AlertDialog.Builder getConfirmDialog(Context context, String msg, DialogInterface.OnClickListener onOkClickListener,DialogInterface.OnClickListener onCancelClickListener){
        AlertDialog.Builder builder= getDialogBuilder(context);
        builder.setMessage(msg);
        builder.setPositiveButton("确定",onOkClickListener);
        builder.setNegativeButton("取消",onCancelClickListener);
        return builder;
    }

}
