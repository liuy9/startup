package com.cicada.cicada;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        new AppDownLoadManager(this).downLoad(FileUtils.getDownloadPath(this) + File.separator + "6666666.apk", "http://imzhiliao.com/zhiliao.apk");
    }
}
