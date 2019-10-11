package com.egar.mediaui.model;

import com.egar.mediaui.fragment.BaseLazyLoadFragment;
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
    private List<BaseLazyLoadFragment> mListFrags;
    private List<BaseLazyLoadFragment> mListUsbFrags;

   /* public static FragmentFactory getInstance() {

        if (mFragmentFactory == null) {
            synchronized (FragmentFactory.class) {
                mFragmentFactory = new FragmentFactory();
            }
        }
        return mFragmentFactory;
    }*/

    public List<BaseLazyLoadFragment> loadFragments() {
        if (mListFrags == null) {
            mListFrags = new ArrayList<>();
        } else {
            mListFrags.clear();
        }
        BaseLazyLoadFragment fragRadio = new RadioMainFragment();
        mListFrags.add(fragRadio);

        BaseLazyLoadFragment fragUsbMusic = new BaseUsbFragment();
        mListFrags.add(fragUsbMusic);
        BaseLazyLoadFragment fragBTMusic = new BtMusicMainFragment();
        mListFrags.add(fragBTMusic);
        return mListFrags;
    }

    /**
     * 根据position 获取对应页面
     *
     * @param position
     * @return
     */
    public BaseLazyLoadFragment getMainCurrentFragmet(int position) {
        return mListFrags.get(position);
    }


    public List<BaseLazyLoadFragment> loadUsbFragments() {
        if (mListUsbFrags == null) {
            mListUsbFrags = new ArrayList<>();
        } else {
            mListUsbFrags.clear();
        }
        BaseLazyLoadFragment fragUsbMusic = new UsbMusicMainFragment();
        mListUsbFrags.add(fragUsbMusic);

        BaseLazyLoadFragment fragUsbVideo = new UsbVideoMainFragment();
        mListUsbFrags.add(fragUsbVideo);

        BaseLazyLoadFragment fragUsbImage = new UsbImageMainFragment();
        mListUsbFrags.add(fragUsbImage);


        return mListUsbFrags;
    }

    /**
     * 根据position 获取对应页面
     *
     * @param position
     * @return
     */
    public BaseLazyLoadFragment getUsbCurrentFragmet(int position) {
        return  mListUsbFrags.get(position);
    }
}
