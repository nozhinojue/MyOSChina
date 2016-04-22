package com.example.cjj.myoschina.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.cjj.myoschina.AppConfig;
import com.example.cjj.myoschina.R;
import com.example.cjj.myoschina.api.ApiImpl;
import com.example.cjj.myoschina.api.okhttp.OkHttpUtil;
import com.example.cjj.myoschina.model.LoginUserBean;
import com.example.cjj.myoschina.util.Contants;
import com.example.cjj.myoschina.util.TDevice;
import com.example.cjj.myoschina.util.XmlUtils;

import java.io.IOException;

import okhttp3.Call;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText etUserName;
    private EditText etPwd;
    private String uname;
    private String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置标题
        setToolbarTitle("登录界面");
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(View view) {
        view.findViewById(R.id.iv_qq_share_loginA).setOnClickListener(this);
        view.findViewById(R.id.iv_wechat_share_loginA).setOnClickListener(this);
        view.findViewById(R.id.iv_sinawweibo_share_loginA).setOnClickListener(this);
        view.findViewById(R.id.btn_login_loginA).setOnClickListener(this);

        etUserName= (EditText) view.findViewById(R.id.et_username_loginA);
        etUserName.setText("nozhinojue@163.com");
        etPwd= (EditText) view.findViewById(R.id.et_pwd_loginA);
        etPwd.setText("oschina1233308");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login_loginA:
                handleLogin();
                break;
            case R.id.iv_qq_share_loginA:
                 ApiImpl.getFavoriteList("1765395", 1, 0, new OkHttpUtil.MyCallBack() {
                     @Override
                     public void onFailure(Call call, IOException e) {

                     }

                     @Override
                     public void onResponse(String resultStr) {
                         showToast(resultStr);
                     }
                 });
//                 new ApiImpl().getFavoriteList("1765395", 1, 0, mApicallback);
//                getFavoriteList("1765395",1,0);
                break;
            case R.id.iv_wechat_share_loginA:


//                myCookieStore.removeAll();
//                showToast("wechat clear");
                break;
            case R.id.iv_sinawweibo_share_loginA:

               // showToast("weibo"+cookieStore.size());
                break;
        }
    }

    private void handleLogin(){
        if(!TDevice.hasInternet()) {
            showToast("当前网络不可用！");
            return;
        }

        if(etUserName.length()==0){    //判断用户名是否为空
            etUserName.setError("请输入邮箱/用户名");
            etUserName.requestFocus();
            return;
        }
        if(etPwd.length()==0){    //判断密码是否为空
            etPwd.setError("请输入密码");
            etPwd.requestFocus();
            return;
        }
         uname=etUserName.getText().toString();
         pwd=etPwd.getText().toString();
        ApiImpl.login(uname, pwd, new OkHttpUtil.MyCallBack() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(String resultStr) {
                LoginUserBean loginUserBean = XmlUtils.toBean(LoginUserBean.class,resultStr.getBytes());
                if (loginUserBean != null) {
                    handleLoginBean(loginUserBean);
                }
            }
        });
    }

    private void  handleLoginBean(LoginUserBean loginUserBean){
        if(loginUserBean.getResult().OK()){
            // 保存登录信息
            loginUserBean.getUser().setAccount(uname);
            loginUserBean.getUser().setPwd(pwd);
            loginUserBean.getUser().setRememberMe(true);
            AppConfig.getInstance(context).saveUserInfo(loginUserBean.getUser());

            //发送登录成功广播
            Intent intent = new Intent(Contants.INTENT_ACTION_USER_CHANGE);
            this.sendBroadcast(intent);

            finish();
        }else {
            showToast(loginUserBean.getResult().getErrorMessage());
        }
    }
}
