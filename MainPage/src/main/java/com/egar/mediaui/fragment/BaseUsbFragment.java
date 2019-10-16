package com.egar.mediaui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.egar.mediaui.Icallback.IKeyBack;
import com.egar.mediaui.Icallback.ITouchListener;
import com.egar.mediaui.MainActivity;
import com.egar.mediaui.R;
import com.egar.mediaui.adapter.UsbFragAdapter;
import com.egar.mediaui.engine.Configs;
import com.egar.mediaui.present.Present;
import com.egar.mediaui.usbmuisc.fragment.UsbImageMainFragment;
import com.egar.mediaui.usbmuisc.fragment.UsbMusicMainFragment;
import com.egar.mediaui.usbmuisc.fragment.UsbVideoMainFragment;
import com.egar.mediaui.util.LogUtil;
import com.egar.mediaui.util.SharedPreferencesUtils;
import com.egar.mediaui.view.CustomViewPager;
import com.egar.mediaui.view.NiceViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * @des: Created by ybf
 * @version: 3.3.2
 * @date: 2019/9/25 14:07
 * @see {@link }
 */
public class BaseUsbFragment extends BaseLazyLoadFragment implements ViewPager.OnPageChangeListener {
    private String TAG = "BaseUsbFragment";
    //==========Widgets in this Fragment==========
    private View contentV;
    //==========Variables in this Fragment==========
    // Attached activity of this fragment.
    private MainActivity mAttachedActivity;
    // Current page
    private NiceViewPagerIndicator indicator;
    private CustomViewPager mUsbViewPager;
    private UsbFragAdapter adapter;
    private BaseLazyLoadFragment mUsbCurrenfrag;
    private BaseUsbFragment baseUsbFragment;
    private LinearLayout usb_frag;
    private
    List<String> mTitles = new ArrayList<>();
    //回调touche事件
    private ArrayList<ITouchListener> touchListeners = new ArrayList<>();

    private ITouchListener iTouchListener;
    //back事件
    private IKeyBack iKeyBack;
    private  boolean isFrist = true;
    @Override
    public int getPageIdx() {
        return Configs.PAGE_INDX_USB;
    }

    @Override
    public void onWindowChangeFull() {
    }

    @Override
    public void onWindowChangeHalf() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAttachedActivity = (MainActivity) context;
    }

    @Override
    public void initView() {

    }

    /**
     * 初始化页面
     */
    public void initPage() {
        int position = (int) SharedPreferencesUtils.getParam(mAttachedActivity, "currentUsbPage", 0);
        mUsbCurrenfrag = Present.getInstatnce().getCurrenUsbFragmen(position);//usbmusic
        mUsbViewPager.setCurrentItem(position, false);
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_usb;
    }

    @Override
    protected void lazyLoad() {
        if(isFrist){
            LogUtil.i("Usb init");
            mTitles.add("UsbMusic");
            mTitles.add("UsbVideo");
            mTitles.add("UsbImage");
            mUsbViewPager = findViewById(R.id.usbViepager);
            usb_frag = findViewById(R.id.usb_frag);
            adapter = new UsbFragAdapter(getChildFragmentManager());
            indicator = (NiceViewPagerIndicator) findViewById(R.id.niceIndicator2);
            indicator.setIndicatorLengthType(NiceViewPagerIndicator.IndicatorType.EQUAL_TEXT)
                    .setIndicatorShapeType(NiceViewPagerIndicator.IndicatorShape.LINEAR)
                    .setIndicatorColor(Color.BLUE);
            adapter.refresh(Present.getInstatnce().getUsbFragmentList(), mTitles);
            mUsbViewPager.setAdapter(adapter);
            mUsbViewPager.setOffscreenPageLimit(3);
            mUsbViewPager.addOnPageChangeListener(this);
            indicator.setUpViewPager(mUsbViewPager);
            initPage();
            isFrist = false;
        }else {
            if(getUsbCurrenfrag() instanceof UsbMusicMainFragment ||
                    getUsbCurrenfrag() instanceof UsbVideoMainFragment ||
                    getUsbCurrenfrag() instanceof UsbImageMainFragment){
                getUsbCurrenfrag().lazyLoad();
            }
            LogUtil.i("Usb lazyLoad");
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    public void setIndicatorVisib(boolean visable){
        if(visable){
            indicator.setVisibility(View.GONE);
        }else {
            indicator.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onPageSelected(int position) {
        //可以同步按钮状态
        SharedPreferencesUtils.setParam(mAttachedActivity, "currentUsbPage", position);
        Log.i(TAG, "currentUsbPage(" + position + ")");
        try {
            mUsbCurrenfrag = Present.getInstatnce().getCurrenUsbFragmen(position);
            mAttachedActivity.setItemBackage(mUsbCurrenfrag);
        } catch (Exception e) {
            Log.i(TAG, "ViewPagerOnChange >> onPageSelected() >> [e: " + e.getMessage());
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void dispatchTouchEvent(MotionEvent ev) {
        for (ITouchListener listener : touchListeners) {
            if (listener != null) {
              //  LogUtil.i("dispatchTouchEvent ="+listener.onTouchEvent(ev));
                mUsbViewPager.setIsScanScroll(listener.onTouchEvent(ev));
            }
        }
    }

    /**
     * 提供给Fragment通过getActivity()方法来注册自己的触摸事件的方法
     */
    public void registerMyTouchListener(ITouchListener listener) {
       LogUtil.i("listener =>"+touchListeners.size());
        touchListeners.add(listener);
    }

    /**
     * 提供给Fragment通过getActivity()方法来取消注册自己的触摸事件的方法
     */
    public void unRegisterMyTouchListener(ITouchListener listener) {
        touchListeners.remove(listener);
    }

    // key back事件
    public void onBackPressed(){
        iKeyBack.onBack();
    }
    public void registBackEvent(IKeyBack iKeyBack){
        this.iKeyBack = iKeyBack;
    }
    public void unRegistBackEvent(){
        this.iKeyBack = null;
    }

    public BaseLazyLoadFragment getUsbCurrenfrag() {
        return mUsbCurrenfrag;
    }

    @Override
    protected void stopLoad() {
        super.stopLoad();
        LogUtil.i("Usb stopLoad");
        if(getUsbCurrenfrag() instanceof UsbMusicMainFragment ||
                getUsbCurrenfrag() instanceof UsbVideoMainFragment ||
                getUsbCurrenfrag() instanceof UsbImageMainFragment){
            getUsbCurrenfrag().stopLoad();
        }
    }


}
