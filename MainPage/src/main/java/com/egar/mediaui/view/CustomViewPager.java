package com.egar.mediaui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @des: Created by ybf
 * @version: 3.3.2
 * @date: 2019/9/26 11:36
 * @see {@link }
 */
public class CustomViewPager extends ViewPager {

    public static final int ACTION_DOWN             = 0;
    static final int ACTION_UP               = 1;
    public static final int ACTION_MOVE             = 2;
    private int downX = 0;
    private boolean noScroll = true;
    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {

            return super.onInterceptTouchEvent(arg0);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
      //  LogUtil.i(super.dispatchTouchEvent(ev)+"::dispatchTouchEvent");
    /*   switch (ev.getAction()){
            case 0:
                LogUtil.i("down");
                break;
            case 1:
                LogUtil.i("up");
                break;
            case 2:
                LogUtil.i("move");
                break;
        }*/
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

            return super.onTouchEvent(ev);
    }



}
