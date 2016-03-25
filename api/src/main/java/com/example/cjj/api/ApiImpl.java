package com.example.cjj.api;

import com.example.cjj.api.okhttp.OkHttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by CJJ on 2016/3/10.
 */
public class ApiImpl{

    private static String API_URL = "http://www.oschina.net/";

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public void login(String username,String password, final ApiCallbackListener apiCallbackListener,CookieJar cookieJar){
        String loginurl = API_URL+"action/api/login_validate";

        RequestBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("pwd", password)
                .add("keep_login", "1")
                .build();
        Request request = new Request.Builder()
                .url(loginurl)
                .post(formBody)
                .build();

        OkHttpUtil.getInstance().setCookieJar(cookieJar);
        OkHttpUtil.getInstance().execute(request, new OkHttpUtil.MyCallBack() {
            @Override
            public void onFailure(Call call, IOException e) {
                apiCallbackListener.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(String resultStr) {
                apiCallbackListener.onSuccessed(resultStr);
            }


        });


    }

    public void getFavoriteList(String uid, int type, int page,final ApiCallbackListener apiCallbackListener,CookieJar cookieJar){
        String FavoriteList ="http://www.oschina.net/action/api/favorite_list?"
                +"uid="+uid+"&type="+type+"&pageIndex="+page+"&pageSize=20";

        Request request = new Request.Builder()
                .url(FavoriteList)
                .get()
                .build();

        OkHttpUtil.getInstance().setCookieJar(cookieJar);
        OkHttpUtil.getInstance().execute(request, new OkHttpUtil.MyCallBack() {
            @Override
            public void onFailure(Call call, IOException e) {
                apiCallbackListener.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(String resultStr) {
                apiCallbackListener.onSuccessed(resultStr);
            }
        });
    }


//    @Override
//    public void getPicJsonData(final ApiCallbackListener ApiCallbackListener) {
//        String urlStr="http://service.picasso.adesk.com/v1/wallpaper/category";
//
//        Request request = new Request.Builder()
//                .url(urlStr)
//                .build();
//        OkHttpUtil.getInstance().getJsonFromServer(request, new OkHttpUtil.MyCallBack() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                ApiCallbackListener.onFailure("请求失败");
//            }
//
//            @Override
//            public void onResponse(String json) {
//                ApiCallbackListener.onSuccessed(json);
//            }
//        });
//    }



}
