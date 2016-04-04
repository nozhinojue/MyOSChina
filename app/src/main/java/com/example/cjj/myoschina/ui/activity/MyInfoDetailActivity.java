package com.example.cjj.myoschina.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cjj.myoschina.AppConfig;
import com.example.cjj.myoschina.R;
import com.example.cjj.myoschina.model.User;
import com.example.cjj.myoschina.util.Contants;

public class MyInfoDetailActivity extends BaseActivity implements View.OnClickListener {
    private ImageView ivAvator;
    private TextView tvName,tvJoinTime,tvArea,tvPlat,tvFocus;
    @Override
    protected int setLayoutId() {
        return R.layout.activity_my_info_detail;
    }

    @Override
    protected void initView(View view) {
        getToolBar().setTitle("我的资料");
        ivAvator= (ImageView) view.findViewById(R.id.iv_avator_myInfoDetailA);
        tvName= (TextView) view.findViewById(R.id.tv_name_myInfoDetailA);
        tvJoinTime= (TextView)  view.findViewById(R.id.tv_jointime_myInfoDetailA);
        tvArea= (TextView) view.findViewById(R.id.tv_area_myInfoDetailA);
        tvPlat= (TextView) view.findViewById(R.id.tv_devplatform_myInfoDetailA);
        tvFocus= (TextView) view.findViewById(R.id.tv_focus_myInfoDetailA);
        Button btnLogOut= (Button) view.findViewById(R.id.btn_logout_myInfoDetailA);
        btnLogOut.setOnClickListener(this);

        loadData();
    }

    private void loadData() {
        User user= AppConfig.getInstance(this).getUserInfo();
        fillUI(user);
    }

    private void fillUI(User user){
        if(user==null)
            return;
        tvName.setText(user.getName());
        tvJoinTime.setText(user.getJointime());
        tvArea.setText(user.getLocation());
        tvPlat.setText(user.getDevplatform());
        tvFocus.setText(user.getExpertise());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_logout_myInfoDetailA:
                AppConfig.getInstance(this).logOut();

                //发送注销登录广播
                Intent intent = new Intent(Contants.INTENT_ACTION_LOGOUT);
                this.sendBroadcast(intent);

                finish();
                break;
        }
    }
}
