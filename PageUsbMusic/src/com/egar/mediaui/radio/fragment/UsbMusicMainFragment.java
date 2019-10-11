package com.egar.mediaui.radio.fragment;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
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
 * PAGE - Usb Music
 */
public class UsbMusicMainFragment extends BaseLazyLoadFragment implements ITouchListener{
    // TAG
    private static final String TAG = "UsbMusicMainFrag";

    //==========Variables in this Fragment==========
    // Attached activity of this fragment.
    private MainActivity mAttachedActivity;
    private BaseUsbFragment fragment;
    private FrameLayout frameleft, frameright;

    @Override
    public int getPageIdx() {
        return Configs.PAGE_IDX_USB_MUSIC;
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
        LogUtil.i(TAG, "onAttach()");
        mAttachedActivity = (MainActivity) context;

    }

    @Override
    public void initView() {


    }

    @Override
    protected int setContentView() {
        return R.layout.usb_music_frag_main;
    }

    @Override
    protected void lazyLoad() {
        fragment = (BaseUsbFragment) Present.getInstatnce().getCurrenFragmen(Configs.PAGE_INDX_USB);
        fragment.registerMyTouchListener(this);
        // 将myTouchListener注册到分发列表
        LogUtil.i("init");
    }


    @Override
    public void onResume() {
        super.onResume();
        LogUtil.i(TAG, "onResume()");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
        if(fragment !=null){
            fragment.unRegisterMyTouchListener(this);
        }


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}