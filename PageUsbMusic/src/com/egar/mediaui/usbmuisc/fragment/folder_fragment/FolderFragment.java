package com.egar.mediaui.usbmuisc.fragment.folder_fragment;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.egar.mediaui.MainActivity;
import com.egar.mediaui.R;
import com.egar.mediaui.fragment.BaseLazyLoadFragment;
import com.egar.mediaui.usbmuisc.adapter.UsbFolderAdapter;
import com.egar.mediaui.util.LogUtil;

/**
 * @des: Created by ybf
 * @version: 3.3.2
 * @date: 2019/10/15 20:21
 * @see {@link }
 * folder 二级页面
 */
public class FolderFragment extends BaseLazyLoadFragment  {
    private String TAG = "FolderFragment";
    private ListView listView;
    private UsbFolderAdapter  folderAdapter;
    private MainActivity activity;
    private int page = 0;
    private Button bt;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity)context;
    }
    @Override
    public int getPageIdx() {
        return page;
    }

    @Override
    public void onWindowChangeFull() {

    }

    @Override
    public void onWindowChangeHalf() {

    }


    @Override
    public void initView() {
        listView =   findViewById(R.id.lv_folder);
        folderAdapter = new UsbFolderAdapter(activity);
        listView.setAdapter(folderAdapter);
        bt =  findViewById(R.id.bt);
        bt.setText("文件夹页面");
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.i(TAG,"onclick");
                  page = 1;
                bt.setText("歌曲列表页面");
            }
        });
    }

    @Override
    protected int setContentView() {
        return R.layout.usb_music_folder_fragment;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void stopLoad() {
        super.stopLoad();

    }


    public void onBack() {
        page = 0;
        LogUtil.i(TAG,"onBack");
    }
}
