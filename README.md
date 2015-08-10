# FlashView
一个可以基于viewPaper可以循环切换的轮播广告控件

> 目前项目是写在eclipse上，后续会迁移到Android Studio上。

## 使用说明 ##
### 在布局文件中配置 ###
    <com.example.flashviewdemo.view.FlashView
        android:id="@+id/flash_main"
        android:layout_width="match_parent"
        android:layout_height="300dp" >
    </com.example.flashviewdemo.view.FlashView>

### 再来看看java代码 ###
    FlashView flashView = (FlashView) findViewById(R.id.flash_main);
    // 虚拟数据
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

> 退出时记得关闭计时器
    
    @Override
    protected void onDestroy() {
    	flashView.cancelSwitch();
    	super.onDestroy();
    }


通过这两步，便可以为你的应用加上轮播广告，下面来看看效果  

![Aaron Swartz](https://github.com/tanxiaoluo/FlashView/raw/master/screen_shot/screenshot_1.gif)

### 自定义加载图片的方式 ###
        

    flashView.setloadImageInterFace(new ImageLoadInterFace() {
	    @Override
            public <T> View loadImage(LayoutInflater mInflater,ViewGroup container, T model, final int position) {
	    	//TODO 加载你的布局
		}
	    });
实现ImageLoadInterFace这个接口去实现你自己加载图片的方式，如果不调用这个方法将会采用默认的实现，目前加载图片用的是picasso库



## 轮播广告的原理 ##
...待续


