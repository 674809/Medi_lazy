package com.egar.mediaui.usbmuisc.fragment;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.egar.mediaui.Icallback.ITouchListener;
import com.egar.mediaui.MainActivity;
import com.egar.mediaui.R;
import com.egar.mediaui.engine.Configs;
import com.egar.mediaui.fragment.BaseLazyLoadFragment;
import com.egar.mediaui.fragment.BaseUsbFragment;
import com.egar.mediaui.present.Present;
import com.egar.mediaui.util.LogUtil;

/**
 * PAGE - Usb Video
 */
public class UsbVideoMainFragment extends BaseLazyLoadFragment implements ITouchListener {
    // TAG
    private static final String TAG = "UsbVideoMainFrag";

    //==========Widgets in this Fragment==========
    private View contentV;

    //==========Variables in this Fragment==========
    // Attached activity of this fragment.
    private MainActivity mAttachedActivity;
    public boolean isPlay = true;
    int i = 1;
    private BaseUsbFragment fragment;


    @Override
    public int getPageIdx() {
        return Configs.PAGE_IDX_USB_VIDEO;
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
        fragment = (BaseUsbFragment) Present.getInstatnce().getCurrenFragmen(Configs.PAGE_INDX_USB);

    }

    @Override
    protected int setContentView() {
        return R.layout.usb_video_frag_main;
    }

    @Override
    protected void lazyLoad() {
        fragment.registerMyTouchListener(this);
        LogUtil.i("video init");

    init();
    }

    private void init() {
            findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i++;
                    if(i %2 == 0){
                        isPlay = false;
                    }else {
                        isPlay = true;
                    }
                    LogUtil.i("isplay ="+isPlay);
                }

            });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
       // LogUtil.i("onTouchEvent");
        return isPlay;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void stopLoad() {
        super.stopLoad();
        if(fragment !=null){
            fragment.unRegisterMyTouchListener(this);
        }
    }
}