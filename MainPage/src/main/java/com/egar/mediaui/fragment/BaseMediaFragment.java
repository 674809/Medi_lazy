package com.egar.mediaui.fragment;

import android.content.Context;

import com.egar.mediaui.Icallback.IWindowChange;
import com.egar.mediaui.lib.BaseAppV4Fragment;
import com.egar.mediaui.present.Present;

public abstract class BaseMediaFragment extends BaseAppV4Fragment implements IWindowChange {
    public abstract int getPageIdx();
   public abstract void onWindowChangeFull();
    public abstract void onWindowChangeHalf();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //注册全屏或半屏监听
        Present.getInstatnce().setOnWindowChange(this);
    }

}