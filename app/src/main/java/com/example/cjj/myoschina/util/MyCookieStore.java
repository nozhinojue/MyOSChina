package com.example.cjj.myoschina.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.Cookie;

/**
 * Created by CJJ on 2016/3/24.
 */
public class MyCookieStore {
    private static final String LOG_TAG = "MyCookieStore";
    private final SharedPreferences sp;
    private Map<String, ?> allCookieContent; //sp中所有cookie信息。

    public MyCookieStore(Context context){
        sp=context.getSharedPreferences("mycookiestore",0);
        allCookieContent = sp.getAll(); //从sp中读取所有数据
    }

    /**
     * 保存cookie记录
     * @param dataHM
     */
    public void saveCookie(HashMap<String, List<Cookie>> dataHM){
        Iterator it = dataHM.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (java.util.Map.Entry) it.next();
            String hostName = entry.getKey().toString();
            List<Cookie> cookieList = (List<Cookie>) entry.getValue();

            SharedPreferences.Editor editor = sp.edit();
            for (Cookie cookie : cookieList) {
                String cookName = hostName + "@" + cookie.name();
                editor.putString(cookName, encodeCookie(new SerializableOkHttpCookies(cookie)));
            }
            editor.commit();
        }

        allCookieContent = sp.getAll(); //保存数据后，重新读取下sp中的数据。
    }

    /**
     * 获取host对应的cookies
     * @param host 主机名
     * @return
     */
    public List<Cookie> getCookie(String host){
        List<Cookie> cookieList=new ArrayList<>();

        for(Map.Entry<String, ?>  entry : allCookieContent.entrySet()){
            String keystr=entry.getKey();
            if(keystr.contains(host)) {
                String encodecookieStr = (String) entry.getValue();
                Cookie cookie=  decodeCookie(encodecookieStr);
                cookieList.add(cookie);
            }
        }
        return cookieList;
    }

    /**
     *清除所有cookie记录
     * @return
     */
    public boolean removeAll(){
        allCookieContent.clear();
        return   sp.edit().clear().commit();
    }

    /**
     * cookies 序列化成 string
     *
     * @param cookie 要序列化的cookie
     * @return 序列化之后的string
     */
    protected String encodeCookie(SerializableOkHttpCookies cookie) {
        if (cookie == null)
            return null;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(os);
            outputStream.writeObject(cookie);
        } catch (IOException e) {
            Log.d(LOG_TAG, "IOException in encodeCookie", e);
            return null;
        }
        return byteArrayToHexString(os.toByteArray());
    }
    /**
     * 将字符串反序列化成cookies
     *
     * @param cookieString cookies string
     * @return cookie object
     */
    protected Cookie decodeCookie(String cookieString) {
        byte[] bytes = hexStringToByteArray(cookieString);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        Cookie cookie = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            cookie = ((SerializableOkHttpCookies) objectInputStream.readObject()).getCookies();
        } catch (IOException e) {
            Log.d(LOG_TAG, "IOException in decodeCookie", e);
        } catch (ClassNotFoundException e) {
            Log.d(LOG_TAG, "ClassNotFoundException in decodeCookie", e);
        }
        return cookie;
    }
    /**
     * 二进制数组转十六进制字符串
     *
     * @param bytes byte array to be converted
     * @return string containing hex values
     */
    protected String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte element : bytes) {
            int v = element & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase(Locale.US);
    }
    /**
     * 十六进制字符串转二进制数组
     *
     * @param hexString string of hex-encoded values
     * @return decoded byte array
     */
    protected byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }

}
