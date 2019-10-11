package com.egar.mediaui.radio.fragment;

import android.content.Context;

import com.egar.mediaui.MainActivity;
import com.egar.mediaui.R;
import com.egar.mediaui.engine.Configs;
import com.egar.mediaui.fragment.BaseLazyLoadFragment;
import com.egar.mediaui.util.LogUtil;

/**
 * PAGE - BT Music
 */
public class BtMusicMainFragment extends BaseLazyLoadFragment  {
    // TAG
    private static final String TAG = "BtMusicMainFrag";


    //==========Variables in this Fragment==========
    // Attached activity of this fragment.
    private MainActivity mAttachedActivity;

    @Override
    public int getPageIdx() {
        return Configs.PAGE_IDX_BT_MUSIC;
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
        return R.layout.bt_frag_main;
    }

    @Override
    protected void lazyLoad() {
        LogUtil.i("init");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}