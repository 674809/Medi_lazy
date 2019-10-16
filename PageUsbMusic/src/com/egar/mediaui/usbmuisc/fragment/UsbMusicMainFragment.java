package com.egar.mediaui.usbmuisc.fragment;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.egar.mediaui.Icallback.ITouchListener;
import com.egar.mediaui.MainActivity;
import com.egar.mediaui.MyApplication;
import com.egar.mediaui.R;
import com.egar.mediaui.adapter.MainFragAdapter;
import com.egar.mediaui.engine.Configs;
import com.egar.mediaui.fragment.BaseLazyLoadFragment;
import com.egar.mediaui.fragment.BaseUsbFragment;
import com.egar.mediaui.lib.NoScrollViewPager;
import com.egar.mediaui.present.Present;
import com.egar.mediaui.usbmuisc.ptesent.MusicPresent;
import com.egar.mediaui.util.LogUtil;

/**
 * PAGE - Usb Music
 */
public class UsbMusicMainFragment extends BaseLazyLoadFragment implements ITouchListener ,ViewPager.OnPageChangeListener {
    // TAG
    private static final String TAG = "UsbMusicMainFrag";

    //==========Variables in this Fragment==========
    // Attached activity of this fragment.
    private MainActivity mAttachedActivity;
    private BaseUsbFragment fragment;
    private FrameLayout frameleft, frameright;
    NoScrollViewPager viewPager;
    private MusicPresent musicPresent; //音乐操作类
    public boolean isScroll = true;
    private  boolean isFrist = true;
    private  int position = 0;

    @Override
    public int getPageIdx() {
        return Configs.PAGE_IDX_USB_MUSIC;
    }

    @Override
    public void onWindowChangeFull() {
        LogUtil.i("onWindowChangeFull");
    }

    @Override
    public void onWindowChangeHalf() {
        LogUtil.i("onWindowChangeHalf");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
      //  LogUtil.i("onAttach()");
        mAttachedActivity = (MainActivity) context;
    }

    @Override
    public void initView() {
        fragment = (BaseUsbFragment) Present.getInstatnce().getCurrenFragmen(Configs.PAGE_INDX_USB);

    }

    @Override
    protected int setContentView() {
        isFrist = true;
        return R.layout.usb_music_frag_main;
    }


    @Override
    protected void lazyLoad() {
        fragment.registerMyTouchListener(this);
        if(isFrist){
            LogUtil.i("one");
            // 将myTouchListener注册到分发列表
            viewPager =  findViewById(R.id.viewpager);
            MainFragAdapter adapter = new MainFragAdapter<BaseLazyLoadFragment>(mAttachedActivity.getSupportFragmentManager());
            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(this);
            adapter.refresh(Present.getInstatnce().getUsbMusicFragmentList());
            isFrist = false;
        }else { //第一次默认会调用lazyLoad
            init();
        }
        initMusicClient();
    }

    /**
     * 初始化音乐播放器句柄
     */
    private void initMusicClient() {
        if(musicPresent == null){
            musicPresent = new MusicPresent();
            musicPresent.creatMuiscService(MyApplication.getContext());
        }
    }

    public MusicPresent getMusicPresent(){
        return musicPresent;
    }

    private void init() {
        LogUtil.i("Music init");
         position =  viewPager.getCurrentItem();
        if(position == 0){
            ((UsbMusicPlayerFragment) Present.getInstatnce().getUsbMuiscChiledFragment(Configs.PAGE_USB_MUSIC_PLAY)).lazyLoad();
        }else if(position == 1){
            ((UsbFolderFragment) Present.getInstatnce().getUsbMuiscChiledFragment(Configs.PAGE_USB_MUSIC_FOLDER)).lazyLoad();
        }


    }


    @Override
    public void onResume() {
        super.onResume();
        LogUtil.i("onResume()");
    }

    /**
     * 设置页面切换
     * @param position
     */
    public void setUsbPlayerCurrentItem(int position){
        viewPager.setCurrentItem(position,false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");

    }

    @Override
    protected void stopLoad() {
        super.stopLoad();
        LogUtil.i("stopLoad()");
        ((UsbMusicPlayerFragment)Present.getInstatnce().getUsbMuiscChiledFragment(Configs.PAGE_USB_MUSIC_PLAY)).stopLoad();
        if(fragment !=null){
            fragment.unRegisterMyTouchListener(this);
        }
        musicPresent.bindPlayService(false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
       // LogUtil.i("isScroll ="+isScroll);
        return isScroll;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        LogUtil.i(TAG,"position");
       if(position == 0){
            isScroll = true;
        }else {
            isScroll = false;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


}