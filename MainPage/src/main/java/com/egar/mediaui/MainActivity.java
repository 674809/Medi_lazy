package com.egar.mediaui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.egar.mediaui.adapter.MainFragAdapter;
import com.egar.mediaui.engine.Configs;
import com.egar.mediaui.fragment.BaseLazyLoadFragment;
import com.egar.mediaui.fragment.BaseUsbFragment;
import com.egar.mediaui.lib.NoScrollViewPager;
import com.egar.mediaui.present.Present;
import com.egar.mediaui.usbmuisc.fragment.BtMusicMainFragment;
import com.egar.mediaui.usbmuisc.fragment.RadioMainFragment;
import com.egar.mediaui.usbmuisc.fragment.UsbImageMainFragment;
import com.egar.mediaui.usbmuisc.fragment.UsbMusicMainFragment;
import com.egar.mediaui.usbmuisc.fragment.UsbVideoMainFragment;
import com.egar.mediaui.util.LogUtil;
import com.egar.mediaui.util.SharedPreferencesUtils;
import com.egar.mediaui.view.MyButton;

import java.util.List;

public class MainActivity extends BaseSubActivity implements View.OnClickListener {
    //TAG
    private static final String TAG = "MediaMainActivity";

    //==========Widgets in this Activity==========
    //View pager
    private NoScrollViewPager mVPager;
    private MainFragAdapter<BaseLazyLoadFragment> mVpFragStateAdapter;
    private ViewPagerOnChange mViewPagerOnChange;
    // Current page
    private BaseLazyLoadFragment mFragCurrent;
    // Fragment list
    private List<BaseLazyLoadFragment> mListFrags;
    //present;
    private Present mPresent;
    //Button
    private MyButton star,jinhao;
    private Button mBtnRadio, mBtnBtMusic ;
    private LinearLayout mBtnUsbMedia, mpage_num;
    private ImageView imageView;
    private TextView tv_usb;
    //full layout
    private RelativeLayout mRelativeLayout;
    //当前屏幕标志位 0,半屏，1全屏
    private int currentScreen = 0;
    //当前usb标志位
    private int usbCurrentPage = Configs.PAGE_IDX_USB_MUSIC;
    //底部指示点
    private ImageView[] mImageView;
    //全屏标记
    public boolean isFull = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initScreenState();
        overridePendingTransition(0, 0);
        initView();
        initButton();
        mPresent = Present.getInstatnce();

    }


    private void initView() {
        mRelativeLayout = (RelativeLayout) findViewById(R.id.main);
        mBtnRadio = (Button) findViewById(R.id.btn_radio);
        mBtnUsbMedia = (LinearLayout) findViewById(R.id.btn_usb_media);
        mBtnBtMusic = (Button) findViewById(R.id.btn_bt_music);
        star = (MyButton) findViewById(R.id.star);
        jinhao = (MyButton) findViewById(R.id.jinhao);
        mpage_num = (LinearLayout) findViewById(R.id.page_num);
        tv_usb = (TextView) findViewById(R.id.tv_usb);


    }


    private void initButton() {
        // -- Variables --
        // -- Widgets --
        // Button
        mBtnRadio.setOnClickListener(this);
        mBtnUsbMedia.setOnClickListener(this);
        mBtnBtMusic.setOnClickListener(this);
        star.setOnClickListener(this);
        jinhao.setOnClickListener(this);


        // View pager
        mVPager = (NoScrollViewPager) findViewById(R.id.v_pager);
       mVPager.setOffscreenPageLimit(3);
        mVpFragStateAdapter = new MainFragAdapter<>(getSupportFragmentManager());
        mVPager.setAdapter(mVpFragStateAdapter);
        mVpFragStateAdapter.refresh(Present.getInstatnce().getMainFragmentList());
        mVPager.addOnPageChangeListener((mViewPagerOnChange = new ViewPagerOnChange()));
        initPage();
        addPagePoit();
        requestPermission();
    }

    /**
     * 初始化页面
     */
    public void initPage() {
        int position = (int) SharedPreferencesUtils.getParam(getApplicationContext(), "currentPage", 0);
        mFragCurrent = Present.getInstatnce().getCurrenFragmen(position);
        mVPager.setCurrentItem(position, false);
        setButtonBackground(position);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
            if(mFragCurrent instanceof BaseUsbFragment){
                ((BaseUsbFragment) mFragCurrent).dispatchTouchEvent(ev);
            }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onBackPressed() {
        if (mFragCurrent instanceof BaseUsbFragment) {
            ((BaseUsbFragment) mFragCurrent).onBackPressed();
            return;
        }else if(mFragCurrent instanceof RadioMainFragment){
            ((RadioMainFragment) mFragCurrent).onBackPressed();
        }else if(mFragCurrent instanceof BtMusicMainFragment){
            ((BtMusicMainFragment) mFragCurrent).onBackPressed();
        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_radio:
                mVPager.setCurrentItem(Configs.PAGE_IDX_RADIO, false);
                setButtonBackground(Configs.PAGE_IDX_RADIO);
                break;
            case R.id.btn_usb_media:
                mVPager.setCurrentItem(Configs.PAGE_INDX_USB, false);
                setButtonBackground(Configs.PAGE_INDX_USB);
                break;
            case R.id.btn_bt_music:
                mVPager.setCurrentItem(Configs.PAGE_IDX_BT_MUSIC, false);
                setButtonBackground(Configs.PAGE_IDX_BT_MUSIC);
                break;
            case R.id.star:
                setWindowSize(0.5f);
                mPresent.addOnWindowChangeHalf();
                break;
            case R.id.jinhao:
                setWindowSize(1);
                mPresent.setOnWindowChangeFull();
                break;
        }
    }


    /**
     * 设置按钮背景颜色
     */
    public void setButtonBackground(int position) {
        if (position == Configs.PAGE_IDX_RADIO) {
            LogUtil.i("radio");
            mBtnRadio.setBackgroundResource(R.drawable.tuoyuan);
            mBtnRadio.setTextColor(getResources().getColor(R.color.wiht));
            mBtnBtMusic.setBackgroundResource(R.color.transparent);
            mBtnBtMusic.setTextColor(getResources().getColor(R.color.black));
            mBtnUsbMedia.setBackgroundResource(R.color.transparent);
            tv_usb.setTextColor(getResources().getColor(R.color.black));
            mpage_num.setVisibility(View.GONE);
        } else if (position == Configs.PAGE_INDX_USB) {
            mBtnRadio.setBackgroundResource(R.color.transparent);
            mBtnRadio.setTextColor(getResources().getColor(R.color.black));
            mBtnBtMusic.setBackgroundResource(R.color.transparent);
            mBtnBtMusic.setTextColor(getResources().getColor(R.color.black));
            mBtnUsbMedia.setBackgroundResource(R.drawable.tuoyuan);
            tv_usb.setTextColor(getResources().getColor(R.color.wiht));
            tv_usb.setCompoundDrawables(null,null,null,null);
            mpage_num.setVisibility(View.VISIBLE);
        } else if (position == Configs.PAGE_IDX_BT_MUSIC) {
            mBtnRadio.setBackgroundResource(R.color.transparent);
            mBtnRadio.setTextColor(getResources().getColor(R.color.black));
            mBtnBtMusic.setBackgroundResource(R.drawable.tuoyuan);
            mBtnBtMusic.setTextColor(getResources().getColor(R.color.wiht));
            mBtnUsbMedia.setBackgroundResource(R.color.transparent);
            tv_usb.setTextColor(getResources().getColor(R.color.black));
            mpage_num.setVisibility(View.GONE);
        }

    }

    private class ViewPagerOnChange implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int state) {
            //Log.i(TAG, "onPageScrollStateChanged(" + state + ")");
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            //可以同步按钮状态
            SharedPreferencesUtils.setParam(getApplicationContext(), "currentPage", position);
          //  Log.i(TAG, "onPageSelected(" + position + ")");
            try {
                mFragCurrent = mPresent.getCurrenFragmen(position);// mListFrags.get(position);
                if (mFragCurrent instanceof BaseUsbFragment) {

                }
            } catch (Exception e) {
                Log.i(TAG, "ViewPagerOnChange >> onPageSelected() >> [e: " + e.getMessage());
            }
        }
    }

    /**
     * 设置USB 页面中当前页面高亮指示条
     *
     * @param mFragCurrent
     */
    public void setItemBackage(BaseLazyLoadFragment mFragCurrent) {
        if (mFragCurrent instanceof UsbMusicMainFragment) {
            pagePoitChange(0);
        } else if (mFragCurrent instanceof UsbVideoMainFragment) {
            pagePoitChange(1);
        } else if (mFragCurrent instanceof UsbImageMainFragment) {
            pagePoitChange(2);
        }
    }

    /**
     * 模拟
     * 设置全屏或半屏
     *
     * @param size
     */
    public void setWindowSize(float size) {
        DisplayMetrics dm = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.gravity = Gravity.TOP;
        p.height = (int) (dm.heightPixels * size);
        p.width = (int) (dm.widthPixels * 1);
        getWindow().setAttributes(p);
    }

    /**
     * 添加指示条
     */
    private void addPagePoit() {
        mImageView = null;
        mImageView = new ImageView[3];
        for (int i = 0; i < 3; i++) {
            ImageView imageView = new ImageView(this);
            mImageView[i] = imageView;
            mImageView[i].setBackgroundResource(R.drawable.home_page_off);
            if (i == 0) {
                mImageView[i].setBackgroundResource(R.drawable.home_page_on);
            }
            LinearLayout.LayoutParams mLinearLayout = new LinearLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            mLinearLayout.setMargins(0, 0, 20, 0);
            mLinearLayout.width = 30;
            mLinearLayout.height = 30;
            imageView.setLayoutParams(mLinearLayout);
            mpage_num.addView(mImageView[i]);
        }
    }


    /**
     * 页面切换，高亮当前页面
     *
     * @param position
     */
    private void pagePoitChange(int position) {
        for (int i = 0; i < 3; i++) {
            if (position == i) {
                mImageView[i].setBackgroundResource(R.drawable.home_page_on);
            } else {
                mImageView[i].setBackgroundResource(R.drawable.home_page_off);
            }
        }
    }

    /**
     * 返回屏幕是否为全屏
     */
    public boolean getScreenState(){
        return isFull;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresent.Destory();
        overridePendingTransition(0, 0);
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            } else {
                String[] requestPermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
                this.requestPermissions(requestPermissions, 0);
            }
        }
    }

}