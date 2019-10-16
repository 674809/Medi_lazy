package com.egar.mediaui.usbmuisc.fragment;

import android.content.Context;
import android.view.View;

import com.egar.mediaui.Icallback.IKeyBack;
import com.egar.mediaui.MainActivity;
import com.egar.mediaui.R;
import com.egar.mediaui.engine.Configs;
import com.egar.mediaui.fragment.BaseLazyLoadFragment;
import com.egar.mediaui.fragment.BaseUsbFragment;
import com.egar.mediaui.present.Present;
import com.egar.mediaui.usbmuisc.fragment.folder_fragment.AlbumsFragment;
import com.egar.mediaui.usbmuisc.fragment.folder_fragment.AllSongsFragment;
import com.egar.mediaui.usbmuisc.fragment.folder_fragment.ArtistFragment;
import com.egar.mediaui.usbmuisc.fragment.folder_fragment.FavoritesFragment;
import com.egar.mediaui.usbmuisc.fragment.folder_fragment.FolderFragment;
import com.egar.mediaui.usbmuisc.ptesent.MusicPresent;
import com.egar.mediaui.usbmuisc.utils.FragUtil;
import com.egar.mediaui.util.LogUtil;

/**
 * @des: Created by ybf
 * @version: 3.3.2
 * @date: 2019/10/11 09:25
 * 文件夹页面
 * @see {@link }
 */
public class UsbFolderFragment extends BaseLazyLoadFragment implements View.OnClickListener,IKeyBack {
    private String TAG = "UsbFolderFragment";
    private MainActivity activity;
    private UsbMusicMainFragment musicMainFrag;
    private MusicPresent musicPresent;

    private FavoritesFragment favoritesFrag;
    private FolderFragment folderFrag;
    private BaseLazyLoadFragment fragToLoad;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public int getPageIdx() {
        return Configs.PAGE_USB_MUSIC_FOLDER;
    }

    @Override
    public void onWindowChangeFull() {

    }

    @Override
    public void onWindowChangeHalf() {

    }

    @Override
    protected int setContentView() {
        return R.layout.usb_music_frag_folder;
    }

    @Override
    public void initView() {
        musicMainFrag = (UsbMusicMainFragment) Present.getInstatnce().getCurrenUsbFragmen(Configs.PAGE_USB_MUSIC_PLAY);
        initfindView();
    }


    private void initfindView() {
        findViewById(R.id.tv_back).setOnClickListener(this);
        findViewById(R.id.bt_favorites).setOnClickListener(this);
        findViewById(R.id.bt_folder).setOnClickListener(this);
        findViewById(R.id.bt_songs).setOnClickListener(this);
        findViewById(R.id.bt_art).setOnClickListener(this);
        findViewById(R.id.bt_album).setOnClickListener(this);

    }


    @Override
    protected void lazyLoad() {
        LogUtil.i("folder lazyLoad");
        ((BaseUsbFragment) Present.getInstatnce().getCurrenFragmen(Configs.PAGE_INDX_USB)).registBackEvent(this);
        initMusicPresent();
        intFavorites();
    }

    private void intFavorites() {
        if(fragToLoad != null){
            FragUtil.removeV4Fragment(fragToLoad,getFragmentManager());
            fragToLoad = null;
        }
        fragToLoad = new  FavoritesFragment();
        FragUtil.loadV4ChildFragment(R.id.usb_folder,fragToLoad,getFragmentManager());

    }

    private void intFoler() {
        if(fragToLoad != null){
            FragUtil.removeV4Fragment(fragToLoad,getFragmentManager());
            fragToLoad = null;
        }
        fragToLoad = new  FolderFragment();
        FragUtil.loadV4ChildFragment(R.id.usb_folder,fragToLoad,getFragmentManager());

    }

    private void intAllSongs() {
        if(fragToLoad != null){
            FragUtil.removeV4Fragment(fragToLoad,getFragmentManager());
            fragToLoad = null;
        }
        fragToLoad = new AllSongsFragment();
        FragUtil.loadV4ChildFragment(R.id.usb_folder,fragToLoad,getFragmentManager());
    }
    private void intArtist() {
        if(fragToLoad != null){
            FragUtil.removeV4Fragment(fragToLoad,getFragmentManager());
            fragToLoad = null;
        }
        fragToLoad = new ArtistFragment();
        FragUtil.loadV4ChildFragment(R.id.usb_folder,fragToLoad,getFragmentManager());
    }
    private void intAlbums() {
        if(fragToLoad != null){
            FragUtil.removeV4Fragment(fragToLoad,getFragmentManager());
            fragToLoad = null;
        }
        fragToLoad = new AlbumsFragment();
        FragUtil.loadV4ChildFragment(R.id.usb_folder,fragToLoad,getFragmentManager());
    }


    /**
     * 初始化音乐操作句柄
     */
    private void initMusicPresent() {
        if (musicPresent == null) {
            musicPresent = musicMainFrag.getMusicPresent();
        }
    }

    @Override
    protected void stopLoad() {
        super.stopLoad();
        LogUtil.i(TAG, "stopLoad");
        ((BaseUsbFragment) Present.getInstatnce().getCurrenFragmen(Configs.PAGE_INDX_USB)).unRegistBackEvent();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                onBackKey();
                break;
            case R.id.bt_favorites:
                intFavorites();
                break;
            case R.id.bt_folder:
                intFoler();
                break;
            case R.id.bt_songs:
                intAllSongs();
                break;
            case R.id.bt_art:
                intArtist();
                break;
            case R.id.bt_album:
                intAlbums();
                break;
        }
    }

    @Override
    public void onBack() {
        LogUtil.i(TAG, "onBack");
        onBackKey();
    }

    /**
     * 返回键
     */
    public void onBackKey(){
        if(fragToLoad instanceof FolderFragment ){
            int pageLayer = fragToLoad.getPageIdx();
            if(pageLayer == 1){ //如果是列表页面，返回时，重新创建第一文件夹页面
                intFoler();
            }else {
                BackUsbPlayerFragmet();
            }

        }else {
            BackUsbPlayerFragmet();
        }
    }

    /**
     * 返回到Usb 播放歌曲页面
     */
    public void BackUsbPlayerFragmet(){
        UsbMusicMainFragment fragment = (UsbMusicMainFragment) Present.getInstatnce().getCurrenUsbFragmen(0);
        fragment.setUsbPlayerCurrentItem(0);
        Present.getInstatnce().setInditeHide(false);
    }
}
