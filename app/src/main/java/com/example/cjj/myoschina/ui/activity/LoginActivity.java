package com.example.cjj.myoschina.ui.activity;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;

import com.example.cjj.api.ApiCallbackListener;
import com.example.cjj.api.ApiImpl;
import com.example.cjj.myoschina.R;
import com.example.cjj.myoschina.util.MyCookieStore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText etUserName;
    private EditText etPwd;

    private Handler mainHanlder;

    OkHttpClient client;

    private MyCookieStore myCookieStore;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(View view) {
        getToolBar().setTitle("登录界面");
        view.findViewById(R.id.iv_qq_share_loginA).setOnClickListener(this);
        view.findViewById(R.id.iv_wechat_share_loginA).setOnClickListener(this);
        view.findViewById(R.id.iv_sinawweibo_share_loginA).setOnClickListener(this);
        view.findViewById(R.id.btn_login_loginA).setOnClickListener(this);

        etUserName= (EditText) view.findViewById(R.id.et_username_loginA);
        etUserName.setText("nozhinojue@163.com");
        etPwd= (EditText) view.findViewById(R.id.et_pwd_loginA);
        etPwd.setText("oschina1233308");

        mainHanlder = new Handler(Looper.getMainLooper());

        myCookieStore= new MyCookieStore(getApplicationContext());

        client=new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
                        cookieStore.put(url.host(),cookies);
                        myCookieStore.saveCookie(cookieStore);//保存cookie到sp
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies =myCookieStore.getCookie(url.host());
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                })
                .build();
    }


    private class MyCookieJar implements CookieJar{

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
            cookieStore.put(url.host(),cookies);
            myCookieStore.saveCookie(cookieStore);//保存cookie到sp
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies =myCookieStore.getCookie(url.host());
            return cookies != null ? cookies : new ArrayList<Cookie>();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login_loginA:
                String uname=etUserName.getText().toString();
                String pwd=etPwd.getText().toString();
                new ApiImpl().login(uname, pwd, mApicallback,new MyCookieJar());
//                MyLogin(uname,pwd);
                break;
            case R.id.iv_qq_share_loginA:
                 new ApiImpl().getFavoriteList("1765395", 1, 0, mApicallback,new MyCookieJar());
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

    private ApiCallbackListener mApicallback = new ApiCallbackListener() {
        @Override
        public void onSuccessed(String jsonResult) {
            showToast(jsonResult);
        }

        @Override
        public void onFailure(String errorMsg) {
            showToast(errorMsg);
        }
    };


    private void MyLogin(String username,String password){
        String loginurl ="http://www.oschina.net/action/api/login_validate";

        RequestBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("pwd", password)
                .add("keep_login", "1")
                .build();

        Request request = new Request.Builder()
                .url(loginurl)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mainHanlder.post(new Runnable() {
                    @Override
                    public void run() {
                        showToast("请求失败。");
                    }
                });

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                mainHanlder.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            showToast(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }


    private void getFavoriteList(String uid, int type, int page){
        String FavoriteList ="http://www.oschina.net/action/api/favorite_list?"
                +"uid="+uid+"&type="+type+"&pageIndex="+page+"&pageSize=20";

        Request request = new Request.Builder()
                .url(FavoriteList)
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mainHanlder.post(new Runnable() {
                    @Override
                    public void run() {
                        showToast("请求失败。");
                    }
                });

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                mainHanlder.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            showToast(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }
}
