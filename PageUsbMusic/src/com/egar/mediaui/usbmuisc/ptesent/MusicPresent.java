package com.egar.mediaui.usbmuisc.ptesent;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.egar.mediaui.usbmuisc.interfaces.IPlayerState;
import com.egar.mediaui.usbmuisc.utils.SettingsSysUtil;
import com.egar.mediaui.util.LogUtil;
import com.egar.music.api.AudioPlayRespFactory;
import com.egar.music.api.EgarApiMusic;
import com.egar.music.api.IApiAudioActions;

import java.util.List;
import java.util.Map;

import juns.lib.media.bean.ProAudio;
import juns.lib.media.play.IAudioPlayListener;

/**
 * @des: Created by ybf
 * @version: 3.3.2
 * @date: 2019/10/12 17:02
 * @see {@link }
 */
public class MusicPresent {
    private String TAG = "MuiscPresent";
    //音乐操作 api
    private EgarApiMusic musicApi;
    private IAudioPlayListener mAudioPlayListener;
    private  Context mContext;
    private IPlayerState iPlayerState;

    public void creatMuiscService(Context context){
        mContext = context;
        musicApi = new EgarApiMusic(context, new ApiAudioActions());

        bindPlayService(true);
        loadLocalMedias();
        musicApi.autoPlay();
    }

    public void focusPlayer() {
        if (musicApi != null) {
            try {
                musicApi.focusPlayer();
            } catch (Exception e) {

            }
        }
    }

    public boolean isPlaying() {
        return musicApi != null && musicApi.isPlaying();
    }

    public void setMusicState(){
        SettingsSysUtil.setAudioState(mContext, isPlaying() ? 2 : 1);
    }
    /**
     * 绑定播放服务
     * @param isConnected
     */
    public void bindPlayService(boolean isConnected) {
        if (isConnected) {
            musicApi.bindPlayService();
        } else {
            musicApi.unbindPlayService();
        }
    }
    public void addPlayListener(boolean isRespDelta, String tag, IAudioPlayListener l) {
        if (musicApi != null) {
            musicApi.addPlayListener(isRespDelta, tag, l);
        }
    }

    public boolean isPlayServiceConnected() {
        if (musicApi != null) {
            return musicApi.isPlayServiceConnected();
        }
        return false;
    }


    private void loadLocalMedias() {
        Log.i(TAG, "loadLocalMedias()");
        refreshCurrMediaInfo();
    }
    private void refreshCurrMediaInfo() {
        Log.i(TAG, "refreshCurrMediaInfo()");
        final ProAudio media = getCurrMedia();
        if (media != null) {
            Toast.makeText(mContext,"title="+media.getTitle(),Toast.LENGTH_SHORT).show();
           LogUtil.i(TAG,"Title()="+media.getTitle());
        }
    }


    public ProAudio getCurrMedia() {
        if (musicApi != null) {
            return musicApi.getCurrMedia();
        }
        return null;
    }

    public boolean isScanning() {
        return musicApi != null && musicApi.isScanning();
    }
    public int getPlayMode() {
        if (musicApi != null) {
            return musicApi.getPlayMode();
        }
        return 0;
    }

    /**
     * mode SINGLE(1);RANDOM(2);LOOP(3);ORDER(4)
     */
    public void setPlayMode(int model){
        if(musicApi !=null){
            musicApi.setPlayMode(model);
        }
    }

    public void playPrevByUser() {
        if (musicApi != null) {
            musicApi.playPrevByUser();
        }
    }

    public void playNextByUser() {
        if (musicApi != null) {
            musicApi.playNextByUser();
        }
    }
    public void playOrPauseByUser() {
        if (musicApi != null) {
            musicApi.playOrPauseByUser();
        }
    }

    /**
     * 获取存储列表设备
     * @return
     */
    public List getStorageDevices() {
        if (musicApi != null) {
            return musicApi.getStorageDevices();
        }
        return null;
    }
    /**
     * playByUrlByUser
     * 指定播放
     */
    public void playByUrlByUser(String path){
        if(musicApi !=null){
            musicApi.playByUrlByUser(path);
        }

    }



    public List getAndSyncMediasByColumns(Map<String,String> whereColumns, String sortOrder, String[] params){
        return  musicApi.getAndSyncMediasByColumns(whereColumns,sortOrder,params);
    }


    public void setIPlayerStateLinsteren(IPlayerState iPlayerState){
        this.iPlayerState = iPlayerState;
    }

    public List getAllMedias(int sortBy, String[] params) {
        if (musicApi != null) {
            return musicApi.getAllMedias(sortBy, params);
        }
        return null;
    }
    //通知音频服务更新播放列表
    public void applyPlayList(String[] params) {
        musicApi.applyPlayList(params);
    }
    public void applyPlayInfo(String mediaUrl, int pos) {
        if (musicApi != null) {
            musicApi.applyPlayInfo(mediaUrl, pos);
        }
    }

    public String getCurrMediaPath() {
        if (musicApi != null) {
            return musicApi.getCurrMediaPath();
        }
        return null;
    }

    public boolean isPlayingSameMedia(String mediaUrl) {
        boolean isPlayingSameMedia = musicApi.isPlaying() && TextUtils.equals(getCurrMediaPath(), mediaUrl);
        Log.i(TAG, "isPlayingSameMedia : " + isPlayingSameMedia);
        return isPlayingSameMedia;
    }

    //clasee---------------------------------------------------------------------------------------
    public class  ApiAudioActions implements IApiAudioActions {


        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onAudioPlayServiceConnected() {
            Log.i(TAG, "onAudioPlayServiceConnected()");
            if (musicApi != null) {
                mAudioPlayListener = new AudioPlayRespFactory(new ApiAudioActions()).getRespCallback();
                addPlayListener(true, "MUSIC_PLAYER", mAudioPlayListener);
            }
            if(isScanning()){
                LogUtil.i(TAG,"isScanning ="+isScanning());
            }
            focusPlayer();
            int playMode = getPlayMode();
            Log.i(TAG, "getPlayMode ="+getPlayMode());
            if(!isPlaying()){
                playOrPauseByUser();
            }

        }

        @Override
        public void onAudioPlayServiceDisconnected() {
            musicApi.removePlayListener("MUSIC_PLAYER",mAudioPlayListener);
        }
        /**
         * 上报挂载状态发生改变的设备
         */
        @Override
        public void onMountStateChanged(List list) throws RemoteException {

        }

        /**
         * MediaScanService - 扫描状态
         * <p>1 START</p>
         * <p>2 REFRESHING</p>
         * <p>3 END</p>
         */
        @Override
        public void onScanStateChanged(int i) throws RemoteException {

        }

        @Override
        public void onGotDeltaMedias(List list) throws RemoteException {

        }
        /**
         * playStateValue - 通知播放器状态
         *
         * @param
         */
        @Override
        public void onPlayStateChanged(int i) throws RemoteException {
            iPlayerState.playStateChange(i);
        }
        /**
         * PlayProgress - 进度改变回调
         *
         * @param mediaPath 正在播放的媒体路径
         * @param progress 当前进度
         * @param duration 总时长
         */
        @Override
        public void onPlayProgressChanged(String mediaPath, int progress, int duration) throws RemoteException {
            iPlayerState.PlayProgressChanged(mediaPath,progress,duration);
        }
        //循环模式
        @Override
        public void onPlayModeChanged(int i) throws RemoteException {
            LogUtil.i(TAG,"循环模式 = "+i);
        }
    }
}
