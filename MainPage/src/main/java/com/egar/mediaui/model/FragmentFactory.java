package com.egar.mediaui.model;

import com.egar.mediaui.fragment.BaseMediaFragment;
import com.egar.mediaui.fragment.BaseUsbFragment;
import com.egar.mediaui.radio.fragment.BtMusicMainFragment;
import com.egar.mediaui.radio.fragment.RadioMainFragment;
import com.egar.mediaui.radio.fragment.UsbImageMainFragment;
import com.egar.mediaui.radio.fragment.UsbMusicMainFragment;
import com.egar.mediaui.radio.fragment.UsbVideoMainFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @des: Created by ybf
 * @version: 3.3.2
 * @date: 2019/9/23 14:16
 * @see {@link }
 */
public class FragmentFactory {
    private static FragmentFactory mFragmentFactory;
    private List<BaseMediaFragment> mListFrags;
    private List<BaseMediaFragment> mUsbListFrags;

   /* public static FragmentFactory getInstance() {

        if (mFragmentFactory == null) {
            synchronized (FragmentFactory.class) {
                mFragmentFactory = new FragmentFactory();
            }
        }
        return mFragmentFactory;
    }*/

    public List<BaseMediaFragment> loadFragments() {
        if(mListFrags == null){
            mListFrags = new ArrayList<>();
        }else {
            mListFrags.clear();
        }

        BaseMediaFragment fragRadio = new RadioMainFragment();
        mListFrags.add(fragRadio);

        BaseMediaFragment usbfrag = new BaseUsbFragment();
        mListFrags.add(usbfrag);

        BaseMediaFragment fragBTMusic = new BtMusicMainFragment();
        mListFrags.add(fragBTMusic);
        return mListFrags;
    }

    /**
     * 根据position 获取对应页面
     * @param position
     * @return
     */
    public BaseMediaFragment getMainCurrentFragmet(int position){
        return mListFrags.get(position);
    }

    public BaseMediaFragment getUsbCurrentFragmet(int position){
        return mUsbListFrags.get(position);
    }
    public List<BaseMediaFragment> loadUsbFragments(){
        if(mUsbListFrags == null){
            mUsbListFrags = new ArrayList<>();
        }else {
            mUsbListFrags.clear();
        }
        BaseMediaFragment fragUsbMusic = new UsbMusicMainFragment();
        mUsbListFrags.add(fragUsbMusic);

        BaseMediaFragment fragUsbVideo = new UsbVideoMainFragment();
        mUsbListFrags.add(fragUsbVideo);

        BaseMediaFragment fragUsbImage = new UsbImageMainFragment();
        mUsbListFrags.add(fragUsbImage);
        return mUsbListFrags;
    }

}
