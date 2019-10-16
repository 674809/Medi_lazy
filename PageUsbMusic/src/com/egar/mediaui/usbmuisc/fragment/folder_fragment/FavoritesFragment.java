package com.egar.mediaui.usbmuisc.fragment.folder_fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.egar.mediaui.MainActivity;
import com.egar.mediaui.R;
import com.egar.mediaui.engine.Configs;
import com.egar.mediaui.fragment.BaseLazyLoadFragment;
import com.egar.mediaui.present.Present;
import com.egar.mediaui.usbmuisc.adapter.UsbFavoriteAdapter;
import com.egar.mediaui.usbmuisc.bean.FilterParams;
import com.egar.mediaui.usbmuisc.fragment.UsbMusicMainFragment;
import com.egar.mediaui.usbmuisc.ptesent.MusicPresent;
import com.egar.mediaui.util.LogUtil;

import java.lang.ref.WeakReference;
import java.util.List;

import juns.lib.android.utils.Logs;
import juns.lib.media.bean.ProAudio;
import juns.lib.media.flags.FilterType;
import juns.lib.media.flags.MediaCollectState;

/**
 * @des: Created by ybf
 * @version: 3.3.2
 * @date: 2019/10/15 19:59
 * @see {@link }
 */
public class FavoritesFragment extends BaseLazyLoadFragment implements AdapterView.OnItemClickListener{
    private static String TAG = "FavoritesFragment";
    private ListView listView;
    private UsbFavoriteAdapter adapter;
    private MainActivity activity;
    private   MusicPresent muiscPresent;
    private UsbMusicMainFragment fragment;
    private List<ProAudio> mListData;
    //Task for loading medias.
    private DataLoadingTask mDataLoadingTask;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity)context;
    }
    @Override
    public int getPageIdx() {
        return 0;
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
        muiscPresent = fragment.getMusicPresent();
        listView = (ListView) findViewById(R.id.listView);
        adapter = new UsbFavoriteAdapter(activity);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        loadMedias(true);
    }

    @Override
    protected int setContentView() {
        return R.layout.usb_music_favorite_fragment;
    }

    @Override
    protected void lazyLoad() {
       // adapter.refreshData();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LogUtil.i(TAG,"position="+position);
        execItemClick(position);
        //跳转到播放页面
        fragment.setUsbPlayerCurrentItem(0);
    }

    private void execItemClick(int position) {
        ProAudio itemMedia = (ProAudio) adapter.getItemInfo(position);
        if (itemMedia == null) {
            return;
        }
        //
        playAndOpenPlayerActivity(itemMedia.getMediaUrl(), position);
    }

    public void playAndOpenPlayerActivity(String mediaUrl, int position) {
        Log.i(TAG, "playAndOpenPlayerActivity(" + mediaUrl + "," + position + ")");
        // Apply play information.
            muiscPresent.applyPlayInfo(mediaUrl, position);
        // Check if already playing.
        if (muiscPresent.isPlayingSameMedia(mediaUrl)) {
            Log.i(TAG, "### The media to play is already playing now. ###");
        } else {
            Logs.i(TAG,"TIME_COL"+ "-3-" + System.currentTimeMillis());
            muiscPresent.playByUrlByUser(mediaUrl);
        }
    }



    private void loadMedias(boolean isAllowExecLoading) {
        if (mDataLoadingTask != null) {
            mDataLoadingTask.cancel(true);
            mDataLoadingTask = null;
        }
        mDataLoadingTask = new DataLoadingTask(this, isAllowExecLoading);
        mDataLoadingTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    // 获取数据
    private static class DataLoadingTask extends AsyncTask<Void, Void, List<ProAudio>> {

        WeakReference<FavoritesFragment> mmReference;
        boolean mmIsAllowExecLoading = false;
        private MusicPresent fmuiscPresent;

        DataLoadingTask(FavoritesFragment frag, boolean isAllowExecLoading) {
            mmReference = new WeakReference<>(frag);
            fmuiscPresent = frag.muiscPresent;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            FavoritesFragment frag = mmReference.get();
            if (frag != null && mmIsAllowExecLoading) {
              LogUtil.i(TAG,"正在加载");
            }
        }

        @SuppressWarnings("unchecked")
        @Override
        protected List<ProAudio> doInBackground(Void... voids) {

            List<ProAudio> listToReturn = null;
            try {
                FavoritesFragment frag = mmReference.get();
                if (frag != null) {
                    //Construct parameters.
                    FilterParams fps = new FilterParams();
                    fps.setCollect(MediaCollectState.COLLECTED);
                    // Get all collected medias.
                    listToReturn =  fmuiscPresent.getAllMedias(FilterType.MEDIA_NAME, fps.getParams());
                    //Update play list.
                    fmuiscPresent.applyPlayList(fps.getParams());
                }
            } catch (Exception e) {
                listToReturn = null;
            }
            return listToReturn;
        }

        @Override
        protected void onPostExecute(List<ProAudio> audios) {
            super.onPostExecute(audios);
            try {
                Logs.i(TAG, "DataLoadingTask - onPostExecute()");
                FavoritesFragment  frag = mmReference.get();
                LogUtil.i(TAG,"加载完成 size =" +audios.size());
                frag.mListData = audios;
                frag.refreshData();
            } catch (Exception e) {
                Log.i(TAG, "");
            }
        }
    }
    private void refreshData() {
        if (mListData != null) {
            adapter.refreshData(mListData, muiscPresent.getCurrMedia());
        }
    }
}
