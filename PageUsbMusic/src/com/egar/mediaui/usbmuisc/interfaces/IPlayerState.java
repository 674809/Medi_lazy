package com.egar.mediaui.usbmuisc.interfaces;

/**
 * @des: Created by ybf
 * @version: 3.3.2
 * @date: 2019/10/14 17:55
 * @see {@link }
 */
public interface IPlayerState {
    void playStateChange(int state);

    void PlayProgressChanged(String path ,int progress,int duration);
}
