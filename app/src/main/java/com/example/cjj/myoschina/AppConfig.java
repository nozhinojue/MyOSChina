package com.example.cjj.myoschina;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.cjj.myoschina.api.okhttp.cookie.store.PersistentCookieStore;
import com.example.cjj.myoschina.model.User;
import com.example.cjj.myoschina.util.CyptoUtils;

/**
 * 用户信息配置类
 * Created by CJJ on 2016/3/29.
 */
public class AppConfig {
    private static String CONFIG_NAME="oschina_config";
    private static AppConfig mInstance;
    private SharedPreferences sharedPreferences;

    private int loginUid;    //登录用户id
    private boolean loginFlag=false; //登录标识

//    public static SharedPreferences getSP(Context context){
//       SharedPreferences sharedPreferences=  context.getSharedPreferences(CONFIG_NAME, 0);
//        return sharedPreferences;
//    }

    private AppConfig(Context context){
        sharedPreferences=context.getSharedPreferences(CONFIG_NAME, 0);
        User user=getUserInfo();
        if(user!=null && user.getId() > 0){
            loginUid=user.getId();
            loginFlag=true;
        }
    }

    public static AppConfig getInstance(Context context){
        if(mInstance==null){
            mInstance=new AppConfig(context);
        }
        return mInstance;
    }

    public boolean isLogin(){
        return loginFlag;
    }

    public int getUid(){
        return loginUid;
    }

    //保存user
    public void saveUserInfo(User user){
        this.loginUid=user.getId();
        this.loginFlag=true;
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString("user.uid",String.valueOf(user.getId()));
        editor.putString("user.name", user.getName());
        editor.putString("user.face", user.getPortrait());
        editor.putString("user.account", user.getAccount());
        editor.putString("user.pwd", CyptoUtils.encode("oschinaApp", user.getPwd()));
        editor.putString("user.location", user.getLocation());
        editor.putString("user.followers", String.valueOf(user.getFollowers()));
        editor.putString("user.fans", String.valueOf(user.getFans()));
        editor.putString("user.score", String.valueOf(user.getScore()));
        editor.putString("user.favoritecount", String.valueOf(user.getFavoritecount()));
        editor.putString("user.gender", String.valueOf(user.getGender()));
        editor.putString("user.isRememberMe", String.valueOf(user.isRememberMe()));
        editor.commit();
    }

    /**
     * 获取用户信息。
     * @return
     */
    public User  getUserInfo(){
        User user= new User();
        user.setId(Integer.parseInt(sharedPreferences.getString("user.uid", "0")));
        user.setName(sharedPreferences.getString("user.name", ""));
        user.setPortrait(sharedPreferences.getString("user.face", ""));
        user.setAccount(sharedPreferences.getString("user.account", ""));
        user.setLocation(sharedPreferences.getString("user.location", ""));
        user.setFollowers(Integer.parseInt(sharedPreferences.getString("user.followers", "0")));
        user.setFans(Integer.parseInt(sharedPreferences.getString("user.fans", "0")));
        user.setScore(Integer.parseInt(sharedPreferences.getString("user.score", "0")));
        user.setFavoritecount(Integer.parseInt(sharedPreferences.getString("user.favoritecount", "0")));
        user.setGender(sharedPreferences.getString("user.gender", ""));
        return user;
    }

    /**
     * 更新用户数据
     * @param user
     */
    public void updateUserInfo(User user){
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString("user.name", user.getName());
        editor.putString("user.face", user.getPortrait());
        editor.putString("user.followers", String.valueOf(user.getFollowers()));
        editor.putString("user.fans", String.valueOf(user.getFans()));
        editor.putString("user.score", String.valueOf(user.getScore()));
        editor.putString("user.favoritecount", String.valueOf(user.getFavoritecount()));
        editor.putString("user.gender", String.valueOf(user.getGender()));
        editor.putString("user.isRememberMe", String.valueOf(user.isRememberMe()));
        editor.commit();
    }


    //用户注销
    public void logOut(){
        this.loginUid=0;
        this.loginFlag=false;
        sharedPreferences.edit().clear().commit();
        //清除cookie
        new PersistentCookieStore(MyApplication.context).removeAll();
    }

    /**
     * 保存字符串到sp
     * @param key
     * @param value
     */
    public  void setPrefString(String key, String value) {
        sharedPreferences.edit().putString(key, value).commit();
    }
}
