package com.example.cjj.myoschina.ui.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cjj.myoschina.AppConfig;
import com.example.cjj.myoschina.R;
import com.example.cjj.myoschina.api.ApiImpl;
import com.example.cjj.myoschina.api.okhttp.OkHttpUtil;
import com.example.cjj.myoschina.model.MyInformation;
import com.example.cjj.myoschina.model.User;
import com.example.cjj.myoschina.ui.UIHelper;
import com.example.cjj.myoschina.util.Contants;
import com.example.cjj.myoschina.util.TDevice;
import com.example.cjj.myoschina.util.XmlUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment implements View.OnClickListener {
    private boolean isLoginFlag;//是否已经登录标识
    private User userInfo;
    private ImageView ivAvatar;
    private TextView tvUserName;
    private TextView tvScore;
    private TextView tvFavor;
    private TextView tvFollow;
    private TextView tvFan;

    private BroadcastReceiver mReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //当收到广播后
            String action = intent.getAction();
            switch (action){
                case Contants.INTENT_ACTION_USER_CHANGE:
                    isLoginFlag=true;
                    loadData();
                    break;
            }
        }
    };



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isLoginFlag= AppConfig.getInstance(getContext()).isLogin();
        //注册receiver来监听广播
        IntentFilter filter= new IntentFilter();
        filter.addAction(Contants.INTENT_ACTION_USER_CHANGE); //用户改变广播
        getActivity().registerReceiver(mReceiver,filter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_me, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData();
    }

    private void initView(View view) {
        RelativeLayout rlAvator= (RelativeLayout) view.findViewById(R.id.rl_avator_fgMe);
        ivAvatar= (ImageView) view.findViewById(R.id.iv_avatar_fgMe);//用户头像
        tvUserName= (TextView) view.findViewById(R.id.tv_userName_fgMe);//用户名
        tvScore= (TextView) view.findViewById(R.id.tv_score_fgMe);
        tvFavor= (TextView) view.findViewById(R.id.tv_favor_fgMe);
        tvFollow= (TextView) view.findViewById(R.id.tv_following_fgMe);
        tvFan= (TextView) view.findViewById(R.id.tv_fans_fgMe);

        rlAvator.setOnClickListener(this);
    }

    //加载数据
    private void loadData() {
        if(isLoginFlag){
            //已登录，请求网络加载数据。// TODO: 2016/3/30 缓存处理
           if(TDevice.hasInternet()){
               requestData();
           }else {
               Toast.makeText(getContext(), "网络不可用！", Toast.LENGTH_SHORT).show();
           }
        }
    }

    //从网络请求数据。
    private void requestData(){
        int uid=AppConfig.getInstance(getContext()).getUid();
        ApiImpl.getMyInformation(uid, new OkHttpUtil.MyCallBack() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(String resultStr) {
                userInfo= XmlUtils.toBean(MyInformation.class,
                        new ByteArrayInputStream(resultStr.getBytes())).getUser();
                if(userInfo!=null){
                    fillUI(userInfo);
                    AppConfig.getInstance(getContext()).updateUserInfo(userInfo);
                }
            }
        });
    }


    //界面赋值
    private void fillUI(User user) {
        if(userInfo==null)
            return;
        tvUserName.setText(user.getName());
        tvScore.setText(String.valueOf(user.getScore()));
        tvFavor.setText(String.valueOf(user.getFavoritecount()));
        tvFollow.setText(String.valueOf(user.getFollowers()));
        tvFan.setText(String.valueOf(user.getFans()));
    }


    @Override
    public void onClick(View v) {
        if(!isLoginFlag){
            Toast.makeText(getContext(), R.string.unlogin, Toast.LENGTH_SHORT).show();
            UIHelper.showLoginActivity(getContext());
        }
        switch (v.getId()){
//            case R.id.rl_avator_fgMe:
//                UIHelper.showLoginActivity(getContext());
//                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销广播监听
        getActivity().unregisterReceiver(mReceiver);
    }
}
