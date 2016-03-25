package com.example.cjj.api.okhttp;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by CJJ on 2016/2/22.
 */
public class OkHttpUtil {

    private static OkHttpUtil mInstance=new OkHttpUtil();
    private static Handler mainHanlder;
    private  OkHttpClient mOkHttpClient;

    private static HashMap<String, List<Cookie>> cookieMap = new HashMap<>(); //存取cookie


    private OkHttpUtil() {
//        mOkHttpClient=new OkHttpClient.Builder().cookieJar(new SimpleCookieJar()).build();
        mOkHttpClient=new OkHttpClient();

//        mOkHttpClient = new OkHttpClient.Builder()
//                .cookieJar(new CookieJar() {
//                    @Override
//                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
//                        if(cookies!=null && cookies.size()>0) {
//                            cookieMap.put(url.host(), cookies);
//                        }
//                    }
//
//                    @Override
//                    public List<Cookie> loadForRequest(HttpUrl url) {
//                        List<Cookie> cookies =cookieMap.get(url.host());
//                        return cookies != null ? cookies : new ArrayList<Cookie>();
//                    }
//                }).build();
//        mOkHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
//        mOkHttpClient.setWriteTimeout(20, TimeUnit.SECONDS);
//        mOkHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
//

        //更新UI线程
        mainHanlder = new Handler(Looper.getMainLooper());

    }

    public interface MyCallBack {
        void onFailure(Call call, IOException e);
        void onResponse(String resultStr);
    }

    public static OkHttpUtil getInstance(){
        return mInstance;
    }

    public  OkHttpClient getOkHttpClient(){
        return mOkHttpClient;
    }

    //设置cookie监听
    public void setCookieJar(CookieJar cookieJar){
        mOkHttpClient= mOkHttpClient.newBuilder().cookieJar(cookieJar).build();
    }

    public void execute(Request request, final MyCallBack myCallBack){
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                mainHanlder.post(new Runnable() { //运行在主线程。
                    @Override
                    public void run() {
                        myCallBack.onFailure(call,e);
                    }
                });
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException{
                final String result=response.body().string(); //获取返回的字符串
                mainHanlder.post(new Runnable() {
                    @Override
                    public void run() {
                        myCallBack.onResponse(result);
                    }
                });
            }
        });
    }

//    private static class OkHttpUtilHolder{
//        public static OkHttpUtil mInstance=new OkHttpUtil();
//    }
//
//    public static OkHttpUtil getInstance() {
//        return OkHttpUtilHolder.mInstance;
//    }

//    public void getJsonFromServer(Request request, final MyCallBack myCallBack){
//            mOkHttpClient.newCall(request).enqueue(new Callback() {
//                @Override
//                public void onFailure(final Call call, final IOException e) {
//                    mainHanlder.post(new Runnable() {   //运行在主线程。
//                        @Override
//                        public void run() {
//                            myCallBack.onFailure(call, e);
//                        }
//                    });
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    final String jsonResult=response.body().string(); //获取返回的json字符串
//                    mainHanlder.post(new Runnable() {   //运行在主线程。
//                        @Override
//                        public void run() {
//                            myCallBack.onResponse(jsonResult);
//                        }
//                    });
//
//                }
//            });
//    }


}
