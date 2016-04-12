package com.example.cjj.myoschina.cache.Snappydb;

import android.content.Context;
import android.util.Log;

import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import java.io.Serializable;

/**
 * Created by CJJ on 2016/4/10.
 */
public class MySnappyDBManager {


    private static final String TAG = "MySnappyDBManager";

    public static void put(Context context,String key,Serializable value){
        DB snappydb = null;
        try {
            snappydb = DBFactory.open(context);
            snappydb.put(key,value);

        } catch (SnappydbException e) {
            e.printStackTrace();
        }finally {
            try {
                snappydb.close();
            } catch (SnappydbException e) {
                e.printStackTrace();
            }
        }
    }

    public static <T extends Serializable> T get(Context context,String key, Class<T> className){
        DB snappydb = null;
        try {

            snappydb = DBFactory.open(context);
            if(snappydb.exists(key)) {
                return snappydb.get(key, className);
            }

        } catch (SnappydbException e) {
            e.printStackTrace();
        }finally {
            try {
                snappydb.close();
            } catch (SnappydbException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void delete(Context context,String key){
        DB snappydb = null;
        try {
            snappydb = DBFactory.open(context);
            snappydb.del(key);
            Log.i(TAG,"snappydb删除缓存："+key+" success!");
        } catch (SnappydbException e) {
            e.printStackTrace();
        }finally {
            try {
                snappydb.close();
            } catch (SnappydbException e) {
                e.printStackTrace();
            }
        }
    }


}
