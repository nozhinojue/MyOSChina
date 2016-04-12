package com.example.cjj.myoschina.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.cjj.myoschina.R;
import com.example.cjj.myoschina.ui.dialog.QuickOptionDialog;
import com.example.cjj.myoschina.ui.fragment.ExploreFragment;
import com.example.cjj.myoschina.ui.fragment.MeFragment;
import com.example.cjj.myoschina.ui.fragment.NewFragment;
import com.example.cjj.myoschina.ui.fragment.TweetFragment;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private Fragment currentFragment;
    private NewFragment newFragment;
    private TweetFragment tweetFragment;
    private ExploreFragment exploreFragment;
    private MeFragment meFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolbar.setTitle("我的开源首页");//标题
//        toolbar.setSubtitle("子标题");  //子标题
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_main2Activity);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        RadioGroup radioGroup= (RadioGroup) findViewById(R.id.radioGp_tab);
        radioGroup.setOnCheckedChangeListener(this);
        ImageView iv_quickspeed= (ImageView) findViewById(R.id.imgView_tab_speed);
        iv_quickspeed.setOnClickListener(this);

        currentFragment = new Fragment();
        if (newFragment == null) {
            newFragment = new NewFragment();
        }
        switchFragment(currentFragment, newFragment, "");   //第一次进入时，加载NewFragment
    }

    /**
     * radioGroup状态切换事件
     * @param radioGroup
     * @param i
     */
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.radioBtn_tab_new:
                if (newFragment == null) {
                    newFragment = new NewFragment();
                }
                switchFragment(currentFragment, newFragment, "");
                break;
            case R.id.radioBtn_tab_tweet:
                if (tweetFragment == null) {
                    tweetFragment = new TweetFragment();
                }
                switchFragment(currentFragment, tweetFragment, "");
                break;

            case R.id.radioBtn_tab_explore:
                if (exploreFragment == null) {
                    exploreFragment = new ExploreFragment();
                }
                switchFragment(currentFragment, exploreFragment, "");
                break;
            case R.id.radioBtn_tab_me:
                if (meFragment == null) {
                    meFragment = new MeFragment();
                }
                switchFragment(currentFragment, meFragment, "");
                break;
        }
    }

    /**
     * 点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgView_tab_speed:
                QuickOptionDialog quickOptionDialog=new QuickOptionDialog(this);
                quickOptionDialog.setCancelable(true);
                quickOptionDialog.setCanceledOnTouchOutside(true);
                quickOptionDialog.show();
                break;
        }
    }

    /**
     * 切换fragment
     */
    private void switchFragment(Fragment fromFg,Fragment toFg,String titleText){
        if (fromFg == null || toFg == null)
            return;
        if(currentFragment!=toFg){
            FragmentManager fragmentManager= getSupportFragmentManager();
            if(!toFg.isAdded()){   //如果没有被添加过,隐藏当前 添加下一个
                fragmentManager.beginTransaction().hide(fromFg).add(R.id.frameLay_drawer_content,toFg).commit();
            }else {
                getSupportFragmentManager().beginTransaction().hide(fromFg).show(toFg).commit();
            }
            currentFragment=toFg;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 创建toolbar上的menu
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //处理toolbar上的menuItem点击事件
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.ab_search:
                Toast.makeText(MainActivity.this, "点击了搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_settings:
                Toast.makeText(MainActivity.this, "点击了设置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_share:
                Toast.makeText(MainActivity.this, "点击了分享", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }



}
