package com.egar.mediaui.usbmuisc.fragment;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.egar.mediaui.Icallback.IKeyBack;
import com.egar.mediaui.MainActivity;
import com.egar.mediaui.R;
import com.egar.mediaui.engine.Configs;
import com.egar.mediaui.fragment.BaseLazyLoadFragment;
import com.egar.mediaui.fragment.BaseUsbFragment;
import com.egar.mediaui.present.Present;
import com.egar.mediaui.usbmuisc.interfaces.IPlayerState;
import com.egar.mediaui.usbmuisc.ptesent.MusicPresent;
import com.egar.mediaui.usbmuisc.utils.AudioUtils;
import com.egar.mediaui.util.LogUtil;
import com.egar.mediaui.view.MyButton;

import juns.lib.java.utils.EmptyUtil;
import juns.lib.media.bean.ProAudio;

/**
 * @des: Created by ybf
 * @version: 3.3.2
 * @date: 2019/10/11 10:20
 * 音乐播放页面
 * @see {@link }
 */
public class UsbMusicPlayerFragment extends BaseLazyLoadFragment implements View.OnClickListener,IPlayerState,IKeyBack {
    private String TAG = "UsbMusicPlayerFragment";
    private UsbMusicMainFragment fragment;
    private MainActivity mAttachedActivity;
    private MusicPresent present;
    private Button bt_prev, bt_palyOrPause, bt_next ;
    private MyButton bt_loop,bt_folder;
    private TextView tv_name,tv_songer;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAttachedActivity = (MainActivity) context;
    }

    @Override
    public int getPageIdx() {
        return Configs.PAGE_USB_MUSIC_PLAY;
    }

    @Override
    public void onWindowChangeFull() {

    }

    @Override
    public void onWindowChangeHalf() {

    }

    @Override
    public void initView() {
       fragment = (UsbMusicMainFragment) Present.getInstatnce().getCurrenUsbFragmen(Configs.PAGE_USB_MUSIC_PLAY);
        findView();

    }

    private void findView() {
        bt_prev = findViewById(R.id.bt_prev);
        bt_folder = findViewById(R.id.bt_folder);
        bt_palyOrPause = findViewById(R.id.bt_palyOrPause);
        bt_next = findViewById(R.id.bt_next);
        bt_loop = findViewById(R.id.bt_loop);
        tv_name = findViewById(R.id.tv_name);
        tv_songer = findViewById(R.id.tv_songer);

        bt_prev.setOnClickListener(this);
        bt_palyOrPause.setOnClickListener(this);
        bt_next.setOnClickListener(this);
        bt_loop.setOnClickListener(this);
        bt_folder.setOnClickListener(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.usb_music_frag_player;
    }

    @Override
    protected void lazyLoad() {
        ((BaseUsbFragment) Present.getInstatnce().getCurrenFragmen(Configs.PAGE_INDX_USB)).registBackEvent(this);
        LogUtil.i("player init");
            init();

    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.i("player onResume");
    }

    private void init() {
        if(present == null){
            present = fragment.getMusicPresent();
            present.setIPlayerStateLinsteren(this);
        }

        if (present.isPlayServiceConnected() && !present.isPlaying()) {
            present.focusPlayer();
            present.playOrPauseByUser();
        }


    }


    /**
     * 设置歌曲信息
     */
    public void setMuiscInfo(){
        ProAudio media = present.getCurrMedia();
        if(media !=null){
            StringBuilder sbInfo = new StringBuilder();
            String strTitle = AudioUtils.getMediaTitle(mAttachedActivity, -1, media, true);
            if (ProAudio.UNKNOWN.equals(strTitle)) {
                sbInfo.append(getString(R.string.unknown_title));
            } else {
                sbInfo.append(strTitle);
            }
            //Artist
            String artist = media.getArtist();
            if (ProAudio.UNKNOWN.equals(artist) || EmptyUtil.isEmpty(artist)) {
                sbInfo.append("\n").append(getString(R.string.unknow));
            } else {
                sbInfo.append("\n").append(artist);
            }
            //Album
            String strAlbum = media.getAlbum();
            if (ProAudio.UNKNOWN.equals(strAlbum) || EmptyUtil.isEmpty(strAlbum)) {
                sbInfo.append("\n").append(getString(R.string.unknow));
            } else {
                sbInfo.append("\n").append(strAlbum);
            }
            tv_name.setText(sbInfo);
        }else {
            LogUtil.i(TAG,"media ="+media);
        }
    }

    @Override
    protected void stopLoad() {
        super.stopLoad();
        ((BaseUsbFragment) Present.getInstatnce().getCurrenFragmen(Configs.PAGE_INDX_USB)).unRegistBackEvent();
        LogUtil.i("stopLoad");
        if( present.isPlaying() ){
          //  present.playOrPauseByUser();
        }

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_folder:
                fragment.setUsbPlayerCurrentItem(1);
                Present.getInstatnce().setInditeHide(true);

                break;
            case R.id.bt_prev:
                present.playPrevByUser();
                break;
            case R.id.bt_next:
                present.playNextByUser();
                break;
            case R.id.bt_palyOrPause:
                present.playOrPauseByUser();
                break;
            case R.id.bt_loop:
                break;
        }
    }

    @Override
    public void playStateChange(int state) {
        LogUtil.i(TAG,"playStateChange");
        setMuiscInfo();
    }

    @Override
    public void PlayProgressChanged(String path, int progress, int duration) {
     //   LogUtil.i(TAG,"progress ="+ progress);
     //   LogUtil.i(TAG,"duration ="+ duration);
    }

    @Override
    public void onBack() {
        LogUtil.i(TAG,"onBack");
        mAttachedActivity.finish();
    }
}
