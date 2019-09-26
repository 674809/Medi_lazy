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
 * PAGE - BT Music
 */
public class BtMusicMainFragment extends BaseMediaFragment {
    // TAG
    private static final String TAG = "BtMusicMainFrag";

    //==========Widgets in this Fragment==========
    private View contentV;

    //==========Variables in this Fragment==========
    // Attached activity of this fragment.
    private MainActivity mAttachedActivity;
    private static boolean state = true;

    private int test = 0;

    @Override
    public int getPageIdx() {
        return Configs.PAGE_IDX_BT_MUSIC;
    }

    @Override
    public void onWindowChangeFull() {
        LogUtil.i("onWindowChangeFull");
        state = false;
        getActivity().recreate();
    }

    @Override
    public void onWindowChangeHalf() {
        LogUtil.i("onWindowChangeHalf");
        state = true;
        getActivity().recreate();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAttachedActivity = (MainActivity) context;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.i("state=" + state);
        if (contentV != null) {
            contentV =null;
        }
        if (state) {
            contentV = inflater.inflate(R.layout.bt_frag_main, container, false);
        } else {
            contentV = inflater.inflate(R.layout.bt_frag_full_main, container, false);
        }
        return contentV;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }


    private void init() {
        LogUtil.i("init");

    }




    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}