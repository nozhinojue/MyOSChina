package com.example.cjj.myoschina.ui.activity;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.cjj.myoschina.R;

/**
 * Created by CJJ on 2016/3/20.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private FrameLayout baseContentView; //父容器
    private Toolbar toolbar;

    public Context context;
    private LayoutInflater layoutInflater;

    /*
    * 两个属性
    * 1、toolbar是否悬浮在窗口之上
    * 2、toolbar的高度获取
    * */
    private static int[] ATTRS = {
            R.attr.windowActionBarOverlay,
            R.attr.actionBarSize
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getApplicationContext();
        layoutInflater=LayoutInflater.from(this);

        initContentView();
        initToolBar();
        initChildView();

        setContentView(baseContentView);

        initView(baseContentView);

        //把toolbar添加到界面中
       // setSupportActionBar(toolbar);
    }


   //初始化父容器
    private void initContentView() {
        /*直接创建一个帧布局，作为视图容器的父容器*/
        baseContentView = new FrameLayout(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        baseContentView.setLayoutParams(params);

    }

    //初始化toolBar
    private void initToolBar(){
        View toolbarView= layoutInflater.inflate(R.layout.toolbar, baseContentView);
        toolbar= (Toolbar) toolbarView.findViewById(R.id.toolbar2);
        //导航返回图标的点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //初始化子类view
    private void initChildView() {
        View childView= layoutInflater.inflate(setLayoutId(),null);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(ATTRS);
        /*获取主题中定义的悬浮标志*/
        boolean overly = typedArray.getBoolean(0, false);
        /*获取主题中定义的toolbar的高度*/
        int toolBarSize = (int) typedArray.getDimension(1,(int) context.getResources().getDimension(R.dimen.abc_action_bar_default_height_material));
        typedArray.recycle();
        /*如果是悬浮状态，则不需要设置间距*/
        params.topMargin = overly ? 0 : toolBarSize;
        baseContentView.addView(childView,params); //添加到父容器中
    }

    public Toolbar getToolBar(){
        return toolbar;
    }

    public void setToolbarTitle(String title){
        toolbar.setTitle(title);
    }

    public void showToast(String msg){
        Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT).show();
    }


    /**
     * 设置LayoutId
     * @return
     */
    protected abstract int setLayoutId();

    /**
     * 初始化view
     */
    protected abstract void initView(View view);

}
