package com.egar.mediaui.radio.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.egar.mediaui.MainActivity;
import com.egar.mediaui.R;
import com.egar.mediaui.engine.Configs;
import com.egar.mediaui.fragment.BaseMediaFragment;
import com.egar.mediaui.util.LogUtil;

/**
 * PAGE - Usb Music
 */
public class UsbMusicMainFragment extends BaseMediaFragment  {
    // TAG
    private static final String TAG = "UsbMusicMainFrag";

    //==========Widgets in this Fragment==========
    private View contentV;

    //==========Variables in this Fragment==========
    // Attached activity of this fragment.
    private MainActivity mAttachedActivity;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView()");
        contentV = inflater.inflate(R.layout.usb_music_frag_main, container, false);
        return contentV;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.i(TAG, "onActivityCreated()");
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.i(TAG, "onResume()");
    }

    private void init() {
        LogUtil.i("init");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }
}