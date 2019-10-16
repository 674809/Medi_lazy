package com.egar.mediaui.usbmuisc.fragment.folder_fragment;

import android.content.Context;

import com.egar.mediaui.MainActivity;
import com.egar.mediaui.R;
import com.egar.mediaui.fragment.BaseLazyLoadFragment;

/**
 * @des: Created by ybf
 * @version: 3.3.2
 * @date: 2019/10/15 20:21
 * @see {@link }
 * folder 二级页面
 */
public class AlbumsFragment extends BaseLazyLoadFragment  {

    private MainActivity activity;
    private int page = 0;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity)context;
    }
    @Override
    public int getPageIdx() {
        return page;
    }

    @Override
    public void onWindowChangeFull() {

    }

    @Override
    public void onWindowChangeHalf() {

    }



    @Override
    public void initView() {

    }

    @Override
    protected int setContentView() {
        return R.layout.usb_music_albums_fragment ;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void stopLoad() {
        super.stopLoad();

    }
}
