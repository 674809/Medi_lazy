package com.egar.mediaui.radio.fragment;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.egar.mediaui.Icallback.ITouchListener;
import com.egar.mediaui.MainActivity;
import com.egar.mediaui.R;
import com.egar.mediaui.engine.Configs;
import com.egar.mediaui.fragment.BaseLazyLoadFragment;
import com.egar.mediaui.fragment.BaseUsbFragment;
import com.egar.mediaui.present.Present;
import com.egar.mediaui.util.LogUtil;

/**
 * PAGE - Usb Image
 */
public class UsbImageMainFragment extends BaseLazyLoadFragment implements ITouchListener {
    // TAG
    private static final String TAG = "UsbImageMainFrag";

    //==========Widgets in this Fragment==========
    private View contentV;

    //==========Variables in this Fragment==========
    // Attached activity of this fragment.
    private MainActivity mAttachedActivity;
    private BaseUsbFragment fragment;
    private FrameLayout frameleft, frameright;

    @Override
    public int getPageIdx() {
        return Configs.PAGE_IDX_USB_IMAGE;
    }

    @Override
    public void onWindowChangeFull() {
        LogUtil.i("onWindowChangeFull");
    }

    @Override
    public void onWindowChangeHalf() {
        LogUtil.i("onWindowChangeHalf");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAttachedActivity = (MainActivity) context;

    }


    @Override
    public void initView() {

    }

    @Override
    protected int setContentView() {
        return R.layout.usb_image_frag_main;
    }

    @Override
    protected void lazyLoad() {
        fragment = (BaseUsbFragment) Present.getInstatnce().getCurrenFragmen(Configs.PAGE_INDX_USB);
        fragment.registerMyTouchListener(this);
        LogUtil.i("init");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(fragment !=null){
            fragment.unRegisterMyTouchListener(this);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
      //  LogUtil.i("onTouchEvent");
        return true;
    }
}