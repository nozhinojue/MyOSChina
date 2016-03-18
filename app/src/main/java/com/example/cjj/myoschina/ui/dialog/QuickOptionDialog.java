package com.example.cjj.myoschina.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.cjj.myoschina.R;

/**
 * Created by CJJ on 2016/3/18.
 */
public class QuickOptionDialog extends Dialog implements View.OnClickListener {

    public QuickOptionDialog(Context context) {
       this(context, R.style.quick_option_dialog);
    }

    private QuickOptionDialog(Context context, int themeResId) {
        super(context, themeResId);
        //设置布局
        View view = getLayoutInflater().inflate(R.layout.dialog_quick_option,null);
        view.findViewById(R.id.ll_text_quick_option).setOnClickListener(this);
        view.findViewById(R.id.ll_album_quick_option).setOnClickListener(this);
        view.findViewById(R.id.ll_note_quick_option).setOnClickListener(this);
        view.findViewById(R.id.ll_scan_quick_option).setOnClickListener(this);
        view.findViewById(R.id.ll_voice_quick_option).setOnClickListener(this);
        view.findViewById(R.id.ll_photo_quick_option).setOnClickListener(this);

        setContentView(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window=getWindow();
        window.setGravity(Gravity.BOTTOM); //设置窗口的位置，底部

        Point point = new Point();
        window.getWindowManager().getDefaultDisplay().getSize(point);//获取屏幕的宽高
        WindowManager.LayoutParams layoutParams=window.getAttributes();
        layoutParams.width=point.x; //把屏幕的宽设置给dialog
        window.setAttributes(layoutParams);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_text_quick_option:
                //文字
                break;
            case R.id.ll_album_quick_option:
                //相册
                break;
            case R.id.ll_note_quick_option:
                //便签
                break;
            case R.id.ll_scan_quick_option:
                //扫一扫
                break;
            case R.id.ll_voice_quick_option:
                //语音
                break;
            case R.id.ll_photo_quick_option:
                //拍照
                break;
        }
    }
}