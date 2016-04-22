package com.example.cjj.myoschina.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cjj.myoschina.R;
import com.example.cjj.myoschina.util.DialogHelper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TweetActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener, View.OnClickListener {
    /** 请求相机 */
    public static final int REQUEST_CODE_GETIMAGE_BYCAMERA = 11;
    /** 请求相册 */
    public static final int REQUEST_CODE_GETIMAGE_BYSDCARD=22;

    public final static String BUNDLE_KEY_ARGS = "BUNDLE_KEY_ARGS";
    public static final String ACTION_TYPE = "action_type";
    public static final int ACTION_TYPE_ALBUM = 0;  //相册
    public static final int ACTION_TYPE_PHOTO = 1;  //拍照

    private static final int MAX_TEXT_LENGTH = 160;  //动弹内容的最大字数

    //定义一个保存图片uri的变量
    private Uri saveImageUri=null;
    private Bitmap bm = null;

    private ImageView ivImg,ivImgClear;
    private EditText etContent;
    private MenuItem miSend;
    private TextView tvCountClear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle("我的动弹");
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_tweet;
    }

    @Override
    protected void initView(View view) {
        ivImg= (ImageView) findViewById(R.id.iv_img_tweetA);    //发送的图片
        ivImgClear= (ImageView) findViewById(R.id.iv_clearImg_tweetA); //清除img图片
        tvCountClear= (TextView) findViewById(R.id.tv_count_clear_tweetA); //发送内容字数统计
        tvCountClear.setText(MAX_TEXT_LENGTH+"");
        tvCountClear.setOnClickListener(this);
        etContent= (EditText) findViewById(R.id.et_content_tweetA); //发送的内容
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvCountClear.setText((MAX_TEXT_LENGTH-charSequence.length())+"");
                updateMenuState();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        //设置toolbar右上角图标
        getToolBar().inflateMenu(R.menu.tweet_menu);
        miSend= getToolBar().getMenu().findItem(R.id.public_menu_send);
        getToolBar().setOnMenuItemClickListener(this);
        updateMenuState();

        Bundle bundle= getIntent().getBundleExtra(BUNDLE_KEY_ARGS); //获取传过来的bundle
        if(bundle!=null){
            int action_type = bundle.getInt(ACTION_TYPE, -1);
            gotoSelectPic(action_type);
        }
    }

    //更新发送按钮状态
    private void updateMenuState() {
        if(miSend==null)
            return;
        if(etContent.getText().length()==0){
            miSend.setEnabled(false);
            miSend.setIcon(R.mipmap.actionbar_unsend_icon);
        }else {
            miSend.setEnabled(true);
            miSend.setIcon(R.mipmap.actionbar_send_icon);
        }
    }

    private void gotoSelectPic(int actiontype) {
        switch (actiontype){
            case ACTION_TYPE_ALBUM:
                selectImgFromAlBum();
                break;
            case ACTION_TYPE_PHOTO:
                selectImgFromCamera();
                break;
            default:
                break;
        }

    }

    //启动摄像头，选择拍的图片
    private void selectImgFromCamera() {
        String savePath = "";
        String storageState = Environment.getExternalStorageState();
        // 判断是否挂载了SD卡
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            savePath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/MyOschina/Camera/";
            File savedir = new File(savePath);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
            String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
                    .format(new Date());
            String fileName = "osc_" + timeStamp + ".jpg";// 照片命名
            File currentImageFile = new File(savePath, fileName);
            saveImageUri = Uri.fromFile(currentImageFile);

            //调用系统相机，把照片保存到sd卡上
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, saveImageUri);     //把拍到的图片保存到sd卡上。
            startActivityForResult(intent, TweetActivity.REQUEST_CODE_GETIMAGE_BYCAMERA);

        }else {
            Toast.makeText(TweetActivity.this, "无法保存照片，请检查SD卡是否挂载", Toast.LENGTH_SHORT).show();
        }
    }

    //从相册中获取图片
    private void selectImgFromAlBum(){
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "选择图片"), REQUEST_CODE_GETIMAGE_BYSDCARD);
        } else {
            intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "选择图片"), REQUEST_CODE_GETIMAGE_BYSDCARD);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK )
            return;

        if(requestCode==REQUEST_CODE_GETIMAGE_BYCAMERA){
            //拍照返回
            if(saveImageUri==null)
                return;
            try {
                //img.setImageURI(saveImageUri);  //得到的是缩略图

                bm= MediaStore.Images.Media.getBitmap(getContentResolver(),saveImageUri);  //根据缩略图uri，返回原始图片bitmap
                ivImg.setImageBitmap(bm); //原图设置到iv上

            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(requestCode==REQUEST_CODE_GETIMAGE_BYSDCARD){
            //相册返回
            if(data==null)
                return;

            try {
                Uri selectedUri = data.getData(); //获取选择的图片缩略图的uri
                bm= MediaStore.Images.Media.getBitmap(getContentResolver(),selectedUri);  //根据缩略图uri，返回原始图片bitmap
                ivImg.setImageBitmap(bm); //原图设置到iv上

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_count_clear_tweetA:
                //清除输入的内容
                clearWords();
                break;

        }
    }

    //清除输入内容
    private void clearWords() {
        if(TextUtils.isEmpty(etContent.getText().toString()))
            return;

        DialogHelper.getConfirmDialog(this,"是否清空内容？",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                etContent.getText().clear();//清空输入框内容。
            }
        },null).show();

    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if(item.getItemId()==R.id.public_menu_send){
            Toast.makeText(TweetActivity.this, "send 动弹", Toast.LENGTH_SHORT).show();
        }
        return true;
    }


}
