package com.example.cjj.myoschina.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cjj.myoschina.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TweetActivity extends Activity {
    /** 请求相机 */
    public static final int REQUEST_CODE_GETIMAGE_BYCAMERA = 1;

    //定义一个保存图片的File变量
    private File currentImageFile = null;

    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);

        img= (ImageView) findViewById(R.id.iv_TweetA);

        startPhotoActivity();
    }


    private void startPhotoActivity() {

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
            currentImageFile = new File(savePath, fileName);
            Uri uri = Uri.fromFile(currentImageFile);

            //调用系统相机
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, TweetActivity.REQUEST_CODE_GETIMAGE_BYCAMERA);

        }else {
            Toast.makeText(TweetActivity.this, "无法保存照片，请检查SD卡是否挂载", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;

        if(requestCode==TweetActivity.REQUEST_CODE_GETIMAGE_BYCAMERA){
            img.setImageURI(Uri.fromFile(currentImageFile));
        }


    }
}
