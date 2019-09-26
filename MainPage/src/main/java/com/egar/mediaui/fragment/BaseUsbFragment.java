package com.egar.mediaui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.egar.mediaui.MainActivity;
import com.egar.mediaui.R;
import com.egar.mediaui.adapter.ViewPagerAdapter;
import com.egar.mediaui.present.Present;
import com.egar.mediaui.radio.fragment.UsbVideoMainFragment;
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
public class BaseUsbFragment extends BaseMediaFragment  {
    private String TAG = "BaseMediaFragment";
    //==========Widgets in this Fragment==========
    private View contentV;
    //==========Variables in this Fragment==========
    // Attached activity of this fragment.
    private MainActivity mAttachedActivity;
    private CustomViewPager usbViewPager;
    private ViewPagerAdapter<BaseMediaFragment> mVpFragStateAdapter;
    // Current page
    private BaseMediaFragment mFragCurrent;
    private ViewPagerOnChange mViewPagerOnChange;
    private NiceViewPagerIndicator indicator;
    List<String> mTitles = new ArrayList<>();

    @Override
    public int getPageIdx() {
        return 0;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentV = inflater.inflate(R.layout.fragment_usb, container, false);
        initView();
        return contentV;
    }

    private void initView() {
        mTitles.add("UsbMusic");
        mTitles.add("UsbVideo");
        mTitles.add("UsbImage");
        usbViewPager = (CustomViewPager) contentV.findViewById(R.id.usbViewPage);
        mVpFragStateAdapter = new ViewPagerAdapter<>(getActivity().getSupportFragmentManager());
        mVpFragStateAdapter.setListFrags(Present.getInstatnce().getUsbFragmentList(), mTitles);
        usbViewPager.setAdapter(mVpFragStateAdapter);
        usbViewPager.addOnPageChangeListener((mViewPagerOnChange = new ViewPagerOnChange()));
        usbViewPager.post(new Runnable() {
            @Override
            public void run() {
                initPage();
            }
        });

       indicator = (NiceViewPagerIndicator) contentV.findViewById(R.id.niceIndicator2);
        indicator.setIndicatorLengthType(NiceViewPagerIndicator.IndicatorType.EQUAL_TEXT)
                .setIndicatorShapeType(NiceViewPagerIndicator.IndicatorShape.LINEAR)
                .setIndicatorColor(Color.BLUE);
        indicator.setUpViewPager(usbViewPager);
        usbViewPager.canScrollHorizontally(0);

    }

    /**
     * 初始化页面
     */
    public void initPage() {
       int position = (int) SharedPreferencesUtils.getParam(getActivity(), "usbCurrentPage", 0);
//        mVpFragStateAdapter.refresh(Present.getInstatnce().getUsbFragmentList(), mTitles, true);
        usbViewPager.setCurrentItem(position);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        LogUtil.i("init");

    }



    private class ViewPagerOnChange implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int state) {

        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            //可以同步按钮状态
            SharedPreferencesUtils.setParam(getActivity(), "usbCurrentPage", position);
            Log.i(TAG, "onPageSelected(" + position + ")");
            try {
                mFragCurrent = Present.getInstatnce().getUsbCurrenFragmen(position);// mListFrags.get(position);
            } catch (Exception e) {
                Log.i(TAG, "ViewPagerOnChange >> onPageSelected() >> [e: " + e.getMessage());
            }
            if(mFragCurrent instanceof UsbVideoMainFragment){

            }
        }
    }

}
