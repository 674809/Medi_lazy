package com.egar.mediaui.usbmuisc.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.egar.mediaui.R;

import java.util.ArrayList;
import java.util.List;

import juns.lib.media.bean.ProAudio;

/**
 * @des: Created by ybf
 * @version: 3.3.2
 * @date: 2019/10/11 15:28
 * @see {@link }
 */
public class UsbFavoriteAdapter extends BaseAdapter {

    private Context context;
    private List<ProAudio> mListData;
    /**
     * Playing media.
     */
    private ProAudio mPlayingMedia;
    private String mPlayingMediaFolderPath = "";

    public UsbFavoriteAdapter(Context context){
        this.context = context;
        mListData = new ArrayList<>();
    }
    public void refreshData(List<ProAudio> lists, ProAudio currMedia){
        synchronized (this) {
            mListData.clear();
            mListData.addAll(lists);
            setPlayingMedia(currMedia);
            notifyDataSetChanged();
        }

    }
    private void setPlayingMedia(ProAudio playingMedia) {
        try {
            this.mPlayingMedia = playingMedia;
            String playingMediaUrl = mPlayingMedia.getMediaUrl();
            this.mPlayingMediaFolderPath = playingMediaUrl.substring(0, playingMediaUrl.lastIndexOf("/"));
        } catch (Exception e) {
        }
    }
    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public Object getItem(int position) {
        return getItemId(position);
    }

    public ProAudio getItemInfo(int position) {
        try {
            return mListData.get(position);
        } catch (Exception e) {
            return null;
        }
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;

        if(null==convertView){
            convertView = View.inflate(context, R.layout.usb_music_favorite_item, null);
            holder =new ViewHolder();
            holder.tv_text=(TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);

        }else{

            holder=(ViewHolder) convertView.getTag();
        }

        holder.tv_text.setText(mListData.get(position).getTitle());

        //////////////
        if(position==selectItme){
            convertView.setBackgroundColor(Color.BLUE);
        }else{
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }
        return convertView;

    }
    public  int selectItme=-1;
    public void setSelectItem(int selectItme){
        this.selectItme=selectItme;

    }

    static class ViewHolder{
        TextView tv_text;
    }
}
