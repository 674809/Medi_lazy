package com.egar.mediaui.usbmuisc.fragment;

import com.egar.mediaui.Icallback.IAppApplication;
import com.egar.mediaui.util.LogUtil;

/**
 * @des: Created by ybf
 * @version: 3.3.2
 * @date: 2019/10/14 13:50
 * @see {@link }
 */
public class RadioApp implements IAppApplication{



    @Override
    public void AppOnCreate() {
        LogUtil.i("AppOnCreate");
    }

    @Override
    public void AppOnTerminate() {

    }
}
