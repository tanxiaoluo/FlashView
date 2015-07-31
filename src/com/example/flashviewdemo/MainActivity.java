package com.example.flashviewdemo;

import java.util.ArrayList;
import java.util.List;

import com.example.flashviewdemo.model.BaseModel;
import com.example.flashviewdemo.view.FlashView;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
    
    private String[] imageUrls = new String[] { "http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg",
                                               "http://img.my.csdn.net/uploads/201407/26/1406383291_6518.jpg",
                                               "http://img.my.csdn.net/uploads/201407/26/1406383291_8239.jpg",
                                               "http://img.my.csdn.net/uploads/201407/26/1406383290_9329.jpg" };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FlashView flashView = (FlashView) findViewById(R.id.flash_main);
        List<BaseModel> mBList = new ArrayList<BaseModel>();
        BaseModel model;
        for (int i = 0; i < 4; i++) {
            model = new BaseModel();
            model.mDes = "第" + i + "张";
            model.mImageId = i;
            model.mImageUrl = imageUrls[i % 4];
            mBList.add(model);
        }
        flashView.setData(mBList);
    }
}
