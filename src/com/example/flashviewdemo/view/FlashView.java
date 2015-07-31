/**
 * @项目名称：FlashViewDemo
 * @文件名：FlashView.java
 * @版本信息：
 * @日期：2015年7月30日
 * @Copyright 2015 www.517na.com Inc. All rights reserved.
 */
package com.example.flashviewdemo.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.flashviewdemo.R;
import com.example.flashviewdemo.model.BaseModel;
import com.squareup.picasso.Picasso;

/**
 * @项目名称：FlashViewDemo
 * @类名称：FlashView
 * @类描述：
 * @创建人：zhaonan
 * @创建时间：2015年7月30日 上午11:10:00
 * @修改人：zhaonan
 * @修改时间：2015年7月30日 上午11:10:00
 * @修改备注：
 * @version
 */
public class FlashView extends FrameLayout {
    
    /** 轮播广告最大项 */
    private int mMaxItem;
    
    private int mActualItem;
    
    /** 当前的位置 */
    private int mCurrentPosition = 0;
    
    /** 用户是否在滑动 */
    private boolean mIsUserTouched = false;
    
    private List<ImageView> mIvs;
    
    /** 存放切换小圆点的布局,当然你也可以在这里面放一个textView之类的 */
    private LinearLayout mCircleLayout;
    
    private ViewPager mVPager;
    
    private Context mContext;
    
    private Timer mTimer = new Timer();
    
    private TimerTask mTimerTask;
    
    private Runnable mRunnable = new Runnable() {
        
        @Override
        public void run() {
            mVPager.setCurrentItem(mCurrentPosition);
        }
    };
    
    private ImageInterFace mImageInterFace;
    
    public FlashView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        preInit();
        initUI();
        
    }
    
    public FlashView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public FlashView(Context context) {
        this(context, null);
    }
    
    /** 设置加载图片的东西 */
    public void setloadImageInterFace(ImageInterFace loadImageInterFace) {
        this.mImageInterFace = loadImageInterFace;
    }
    
    /**
     * @description
     * @date 2015年7月30日
     */
    private void preInit() {
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                if (!mIsUserTouched) {
                    mCurrentPosition = (mCurrentPosition + 1) % mMaxItem;
                    ((Activity) mContext).runOnUiThread(mRunnable);
                }
            }
        };
        
        if (null == mImageInterFace) {
            mImageInterFace = new ImageInterFace() {
                
                @Override
                public void loadImage(ImageView iv, final int position, String url) {
                    if (!TextUtils.isEmpty(url)) {
                        Picasso.with(mContext).load(url).placeholder(R.drawable.ic_onload).error(R.drawable.ic_nodata).into(iv);
                    }
                    else {
                        iv.setImageResource(R.drawable.ic_nodata);
                    }
                    iv.setOnClickListener(new OnClickListener() {
                        
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(mContext, "第" + position + "张", Toast.LENGTH_SHORT).show();
                            Log.d("TL", "第" + position + "张");
                        }
                    });
                    
                }
            };
        }
    }
    
    private void initUI() {
        LayoutInflater.from(mContext).inflate(R.layout.viewpaper_base, this, true);
        mCircleLayout = (LinearLayout) findViewById(R.id.ll_viewpaper);
        mVPager = (ViewPager) findViewById(R.id.viewpager_base);
    }
    
    private void setCircle() {
        ImageView mIv;
        mIvs = new ArrayList<ImageView>();
        for (int i = 0; i < mActualItem; i++) {
            mIv = new ImageView(mContext);
            mIv.setBackgroundResource(R.drawable.ic_viewpager_uncheck);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.leftMargin = 4;
            params.rightMargin = 4;
            mCircleLayout.addView(mIv, params);
            mIvs.add(mIv);
        }
    }
    
    public void setData(List<BaseModel> mList) {
        
        mActualItem = mList == null ? 0 : mList.size();
        mMaxItem = mActualItem * 100;
        setCircle();
        
        MyPagerAdapter mAdapter = new MyPagerAdapter(mContext, mList);
        mVPager.setAdapter(mAdapter);
        mVPager.setOnPageChangeListener(mAdapter);
        mVPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN && action == MotionEvent.ACTION_MOVE) {
                    mIsUserTouched = true;
                }
                else if (action == MotionEvent.ACTION_UP) {
                    mIsUserTouched = false;
                }
                
                return false;
            }
        });
        // startSwitch(5000, 3000);
    }
    
    /** 启动轮播效果（默认启动） */
    public void startSwitch(long delay, long period) {
        mTimer.schedule(mTimerTask, delay, period);
    }
    
    /** 关闭轮播计时器 */
    public void caSwitch() {
        if (null != mTimer) {
            mTimer.cancel();
        }
    }
    
    /**
     * @项目名称：FlashViewDemo
     * @类名称：MyPagerAdapter
     * @类描述：
     * @创建人：zhaonan
     * @创建时间：2015年7月30日 下午2:44:54
     * @修改人：zhaonan
     * @修改时间：2015年7月30日 下午2:44:54
     * @修改备注：
     * @version
     */
    class MyPagerAdapter extends PagerAdapter implements OnPageChangeListener {
        
        private LayoutInflater mInflater;
        
        private List<BaseModel> mList;
        
        private BaseModel mModel;
        
        public MyPagerAdapter(Context context, List<BaseModel> mList) {
            mInflater = LayoutInflater.from(context);
            this.mList = mList;
        }
        
        @Override
        public int getCount() {
            return mList == null ? 0 : mMaxItem;
        }
        
        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }
        
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
        
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position %= mActualItem;
            
            View view = mInflater.inflate(R.layout.viewpaper_item, container, false);
            ImageView iv = (ImageView) view.findViewById(R.id.iv_viewpager);
            
            mModel = mList.get(position);
            mImageInterFace.loadImage(iv, position, mModel.mImageUrl);
            
            container.addView(view);
            
            return view;
        }
        
        @Override
        public void finishUpdate(ViewGroup container) {
            super.finishUpdate(container);
            int position = mVPager.getCurrentItem();
            
            if (position == 0) {
                position = mActualItem;
            }
            else if (position == mMaxItem - 1) {
                position = mActualItem - 1;
            }
            mVPager.setCurrentItem(position, false);
        }
        
        // 以下是接口的实现
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
        
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
        
        @Override
        public void onPageSelected(int position) {
            mCurrentPosition = position;
            position %= mActualItem;
            setDotView(position);
        }
        
        /**
         * @description
         * @date 2015年7月30日
         */
        private void setDotView(int position) {
            if (null != mIvs) {
                for (ImageView iv : mIvs) {
                    iv.setBackgroundResource(R.drawable.ic_viewpager_uncheck);
                }
                mIvs.get(position).setBackgroundResource(R.drawable.ic_viewpager_check);
            }
        }
    }
    
    /**
     * @项目名称：FlashViewDemo
     * @类名称：LoadImageInterFace 加载图片使用自定义实现的接口
     * @类描述：
     * @创建人：zhaonan
     * @创建时间：2015年7月30日 上午11:57:18
     * @修改人：zhaonan
     * @修改时间：2015年7月30日 上午11:57:18
     * @修改备注：
     * @version
     */
    public interface ImageInterFace {
        void loadImage(ImageView iv, int position, String url);
    }
}
