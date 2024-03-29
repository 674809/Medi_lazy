package com.egar.mediaui.lib;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.egar.mediaui.util.LogUtil;

public class NoScrollViewPager extends ViewPager {
    private String TAG = "NoScrollViewPager";
    private boolean noScroll = false;

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
       // LogUtil.i(TAG,"noScroll ="+noScroll);
        if (noScroll) {
            return super.onTouchEvent(arg0);
        } else {
            return false;
        }
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        // 是否允许滑动
        LogUtil.i(TAG,"noScroll ="+noScroll);
        if (noScroll) {
            return super.onInterceptTouchEvent(arg0);
        } else {
            return false;
        }
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }

}