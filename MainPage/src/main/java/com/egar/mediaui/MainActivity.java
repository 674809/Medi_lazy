package com.egar.mediaui;

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

import com.egar.mediaui.adapter.MainFragAdapter;
import com.egar.mediaui.engine.Configs;
import com.egar.mediaui.fragment.BaseMediaFragment;
import com.egar.mediaui.lib.NoScrollViewPager;
import com.egar.mediaui.present.Present;
import com.egar.mediaui.radio.fragment.BtMusicMainFragment;
import com.egar.mediaui.radio.fragment.RadioMainFragment;
import com.egar.mediaui.util.LogUtil;
import com.egar.mediaui.util.SharedPreferencesUtils;

import java.util.List;

public class MainActivity extends BaseSubActivity implements View.OnClickListener {
    //TAG
    private static final String TAG = "MediaMainActivity";

    //==========Widgets in this Activity==========
    //View pager
    private NoScrollViewPager mVPager;
    private MainFragAdapter<BaseMediaFragment> mVpFragStateAdapter;
    private ViewPagerOnChange mViewPagerOnChange;
    // Current page
    private BaseMediaFragment mFragCurrent;
    // Fragment list
    private List<BaseMediaFragment> mListFrags;
    //present;
    private Present mPresent;
    //Button
    private Button mBtnRadio, mBtnBtMusic, star, jinhao;
    private LinearLayout mBtnUsbMedia;
    private ImageView imageView;
    //full layout
    private RelativeLayout mRelativeLayout;
    //当前屏幕标志位 0,半屏，1全屏
    private int currentScreen = 0;
    //当前usb标志位
    private int usbCurrentPage = Configs.PAGE_IDX_USB_MUSIC;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initScreenState();
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
        star = (Button) findViewById(R.id.star);
        jinhao = (Button) findViewById(R.id.jinhao);


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
        mVPager.addOnPageChangeListener((mViewPagerOnChange = new ViewPagerOnChange()));
        mVPager.post(new Runnable() {
            @Override
            public void run() {
                initPage();
            }
        });
    }

    /**
     * 初始化全屏或半屏
     */
    public void initScreenState() {
        int state = (int) SharedPreferencesUtils.getParam(getApplicationContext(), "currentScreen", 0);
        if (state == 0) {
            setWindowSize(0.5f);
        } else {
            setWindowSize(1);
        }
    }

    /**
     * 初始化页面
     */
    public void initPage() {
        int position = (int) SharedPreferencesUtils.getParam(getApplicationContext(), "currentPage", 0);
        mVpFragStateAdapter.refresh(mPresent.getMainFragmentList(), true);
        mVPager.setCurrentItem(position);
    }

    int xLast = 0;
    int count = 0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int state = ev.getAction();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xLast = (int) ev.getX();
                LogUtil.i("(" + ev.getX() + ")");
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtil.i("(" + ev.getX() + "）");
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.i("(" + ev.getX() + "）");
                break;
        }
        count = 0;
     return super.dispatchTouchEvent(ev);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_radio:
                if (!(mFragCurrent instanceof RadioMainFragment)) {
                    mVPager.setCurrentItem(Configs.PAGE_IDX_RADIO);
                }

                break;
            case R.id.btn_usb_media:
                mVPager.setCurrentItem(Configs.PAGE_INDX_USB);

                LogUtil.i("usbCurrentPage = " + usbCurrentPage);
                break;
            case R.id.btn_bt_music:
                if (!(mFragCurrent instanceof BtMusicMainFragment)) {
                    mVPager.setCurrentItem(Configs.PAGE_IDX_BT_MUSIC);
                }
                mBtnBtMusic.setBackgroundColor(getResources().getColor(R.color.black));
                mBtnBtMusic.setTextColor(getResources().getColor(R.color.wiht));
                break;
            case R.id.star:
                setWindowSize(0.5f);
                setScreenState(0);
                mPresent.addOnWindowChangeHalf();

                break;
            case R.id.jinhao:
                setWindowSize(1);
                setScreenState(1);
                mPresent.setOnWindowChangeFull();
                break;
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
            Log.i(TAG, "onPageSelected(" + position + ")");
            try {
                mFragCurrent = mPresent.getCurrenFragmen(position);// mListFrags.get(position);
            } catch (Exception e) {
                Log.i(TAG, "ViewPagerOnChange >> onPageSelected() >> [e: " + e.getMessage());
            }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresent.Destory();
        overridePendingTransition(0, 0);
    }

    /**
     * 设置屏幕标记位
     *
     * @param state 0: 半屏，1：全屏
     */
    public void setScreenState(int state) {
        currentScreen = state;
        SharedPreferencesUtils.setParam(getApplicationContext(), "currentScreen", state);
    }
}