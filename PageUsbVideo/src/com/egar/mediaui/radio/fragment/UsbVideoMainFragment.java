package com.egar.mediaui.radio.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.egar.mediaui.MainActivity;
import com.egar.mediaui.R;
import com.egar.mediaui.engine.Configs;
import com.egar.mediaui.fragment.BaseMediaFragment;
import com.egar.mediaui.util.LogUtil;

/**
 * PAGE - Usb Video
 */
public class UsbVideoMainFragment extends BaseMediaFragment {
    // TAG
    private static final String TAG = "UsbVideoMainFrag";

    //==========Widgets in this Fragment==========
    private View contentV;

    //==========Variables in this Fragment==========
    // Attached activity of this fragment.
    private MainActivity mAttachedActivity;
    public  boolean isPlay = false;
    int i = 1;
  
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentV = inflater.inflate(R.layout.usb_video_frag_main, container, false);
        return contentV;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }



    private void init() {
        LogUtil.i("init");

        contentV.findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                if (i % 2 == 0) {
                    isPlay = true;
                } else {
                    isPlay = false;
                }
                LogUtil.i("isPlay =" + isPlay);
            }
        });
    }


    public boolean getpalyState(){
        return isPlay;
    }
}