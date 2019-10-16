package com.egar.mediaui.present;

import com.egar.mediaui.Icallback.IWindowChange;
import com.egar.mediaui.engine.Configs;
import com.egar.mediaui.fragment.BaseLazyLoadFragment;
import com.egar.mediaui.fragment.BaseUsbFragment;
import com.egar.mediaui.model.FragmentFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @des: Created by ybf
 * @version: 3.3.2
 * @date: 2019/9/23 14:42
 * @see {@link }
 */
public class Present {

    private static Present mPresnet;

    FragmentFactory mFragmentFactory;
    //监听集合
    private List<IWindowChange>  iWindowChanges = new ArrayList<>();
    /**
     * 初始化页面
     */
    private Present(){
        mFragmentFactory = new  FragmentFactory();
    }

    public static Present getInstatnce(){
        if (mPresnet == null){
            synchronized (Present.class){
                mPresnet = new Present();
            }
        }
        return mPresnet;
    }

    /**
     * 获取MainFragment数据
     * @return
     */
    public List<BaseLazyLoadFragment> getMainFragmentList(){
        return mFragmentFactory.loadFragments();
    }


    /**
     * 获取UsbFragment数据
     * @return
     */
    public List<BaseLazyLoadFragment> getUsbFragmentList(){
        return mFragmentFactory.loadUsbFragments();
    }


    /**
     * 获取Usbmusic数据
     * @return
     */
    public List<BaseLazyLoadFragment> getUsbMusicFragmentList(){
        return mFragmentFactory.loadUsbMusicFragments();
    }
    public BaseLazyLoadFragment getUsbMuiscChiledFragment(int position){
        return mFragmentFactory.getUsbMuiscChildFragment(position);
    }


    /**
     * 获取Main当前fragment
     * @param position
     * @return
     */
    public BaseLazyLoadFragment getCurrenFragmen(int position){
        return mFragmentFactory.getMainCurrentFragmet(position);
    }


    /**
     * 获取Usb当前fragment
     * @param position
     * @return
     */
    public BaseLazyLoadFragment getCurrenUsbFragmen(int position){
        return mFragmentFactory.getUsbCurrentFragmet(position);
    }

    /**
     * 获取UsbFragment
     * @return
     */
    public BaseUsbFragment getUSbFragment(){
        return (BaseUsbFragment) mFragmentFactory.getMainCurrentFragmet(Configs.PAGE_INDX_USB);
    }

    /**
     * 设置usbIinditer 隐藏与显示
     * @param ishide
     */
    public void setInditeHide(boolean ishide){
        BaseUsbFragment fragment1 = (BaseUsbFragment) Present.getInstatnce().getUSbFragment();
        fragment1.setIndicatorVisib(ishide);
    }
    /**
     * 添加全屏半屏监听
     */
    public void setOnWindowChange(IWindowChange iWindowChange){
        iWindowChanges.add(iWindowChange);
    }
    /**
     *添加半屏监听
     */
    public void addOnWindowChangeHalf(){
        for (IWindowChange windowChange :iWindowChanges){
            windowChange.onWindowChangeHalf();
        }
    }
    /**
     * 添加全屏监听
     */
    public void setOnWindowChangeFull(){
        for (IWindowChange windowChange :iWindowChanges){
            windowChange.onWindowChangeFull();
        }
    }


    public void Destory(){
        iWindowChanges.clear();
        mPresnet = null;
    }
}
