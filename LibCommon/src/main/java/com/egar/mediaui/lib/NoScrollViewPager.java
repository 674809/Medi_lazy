package com.egar.mediaui.lib;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class NoScrollViewPager extends ViewPager {
    private String TAG="NoScrollViewPager";
    private boolean noScroll = true;
 
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
        if (noScroll)
            return false;
        else
            return super.onTouchEvent(arg0);
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
    	// TODO Auto-generated method stub
    	if(event.getKeyCode() == 66){
    		return true;
    	}else{
    		return super.dispatchKeyEvent(event);
    	}
    	
    }
 @Override
     public boolean executeKeyEvent(KeyEvent event){
	 			Log.e(TAG,"code="+event.getKeyCode());
        if(event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT | event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT  ){
                  return true; 
            }else {
               return super.executeKeyEvent(event);
            }	 
     }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (noScroll)
            return false;
        else
            return super.onInterceptTouchEvent(arg0);
    }
 
    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }
 
    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item,false);
    }
 
}