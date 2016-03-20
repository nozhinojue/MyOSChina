package com.example.cjj.myoschina.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.cjj.myoschina.R;

/**
 * Created by CJJ on 2016/3/20.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private FrameLayout baseContentView; //父容器
    private Toolbar toolbar;

    private Context context;
    private LayoutInflater layoutInflater;

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
        setSupportActionBar(toolbar);
    }

    //初始化子类view
    private void initChildView() {
        View childView= layoutInflater.inflate(setLayoutId(),null);
        baseContentView.addView(childView); //添加到父容器中
    }

    public Toolbar getToolBar(){
        return toolbar;
    }

//    /**
//     * 设置toolbar标题
//     * @param titleStr
//     */
//    public void setToolBarTitle(String titleStr){
//        toolbar.setTitle(titleStr);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                //toolbar上的返回按钮事件
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
