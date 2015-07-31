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


### 自定义加载图片的方式 ###
> 后续这部分会优化
        

    flashView.setloadImageInterFace(new ImageInterFace() {
	    @Override
	    public void loadImage(ImageView iv, int position, String url) {
	    	//TODO 加载你的图片
		}
	   });
实现ImageInterFace这个接口去实现你自己加载图片的方式，目前加载图片用的是picasso库



## 轮播广告的原理 ##
...待续


