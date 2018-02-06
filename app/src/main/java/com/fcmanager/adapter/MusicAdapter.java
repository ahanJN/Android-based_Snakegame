package com.fcmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fcmanager.R;
import com.fcmanager.bean.Music;
import com.fcmanager.bean.Ranking;

import java.util.List;

/**
 * Created by Administrator on 2017/2/28.
 */
public class MusicAdapter extends BaseAdapter{
    public List<Music> list;
    public Context context;
    public MusicAdapter(Context context, List<Music> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }
    // How many items are in the data set represented by this Adapter.
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }
    // Get the data item associated with the specified position in the data set.
    @Override
    public long getItemId(int position) {
        return position;
    }
    // Get the row id associated with the specified position in the list.
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        // Get a View that displays the data at the specified position in the data set.
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.music_item, null);
            holder.tv_music = (TextView) convertView.findViewById(R.id.tv_music);
            holder.tv_musicName = (TextView) convertView.findViewById(R.id.tv_musicName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Music music = list.get(position);
        holder.tv_music.setText(music.getMusic());
        holder.tv_musicName.setText(music.getMusicName());
        return convertView;
    }
    class ViewHolder{
        TextView tv_music,tv_musicName;
    }
}
