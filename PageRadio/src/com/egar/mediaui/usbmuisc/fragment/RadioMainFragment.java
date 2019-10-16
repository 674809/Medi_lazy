package com.egar.mediaui.usbmuisc.fragment;

import android.content.Context;
import android.view.View;

import com.egar.mediaui.MainActivity;
import com.egar.mediaui.R;
import com.egar.mediaui.engine.Configs;
import com.egar.mediaui.fragment.BaseLazyLoadFragment;
import com.egar.mediaui.util.LogUtil;

/**
 * PAGE - Radio
 */
public class RadioMainFragment extends BaseLazyLoadFragment {
    // TAG
    private static final String TAG = "BaseAudioGroupsFrag";

    //==========Widgets in this Fragment==========
    private View contentV;

    //==========Variables in this Fragment==========
    // Attached activity of this fragment.
    private MainActivity mAttachedActivity;

    @Override
    public int getPageIdx() {
        return Configs.PAGE_IDX_RADIO;
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
    public void onBackPressed(){

    }
    @Override
    protected int setContentView() {
        return R.layout.radio_frag_main;
    }

    @Override
    protected void lazyLoad() {
        LogUtil.i("radio init");
    }

}