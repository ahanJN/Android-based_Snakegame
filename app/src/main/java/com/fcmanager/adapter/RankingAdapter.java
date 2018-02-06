package com.fcmanager.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fcmanager.R;
import com.fcmanager.bean.Ranking;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by Administrator on 2017/2/28.
 */
public class RankingAdapter extends BaseAdapter{
    public List<Ranking> list;
    public Context context;
    public RankingAdapter(Context context,List<Ranking> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.ranking_item, null);
            holder.tv_rangking = (TextView) convertView.findViewById(R.id.tv_ranking);
            holder.tv_userName = (TextView) convertView.findViewById(R.id.tv_userName);
            holder.tv_fraction = (TextView) convertView.findViewById(R.id.tv_fraction);
            holder.tv_av = (TextView) convertView.findViewById(R.id.tv_av);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Ranking ranking = list.get(position);
        int data = position+1;
        holder.tv_rangking.setText(data+"");
        holder.tv_userName.setText(ranking.getUserName());
        holder.tv_fraction.setText(ranking.getFraction());

        holder.tv_av.setText("第"+data+"名");
        return convertView;
    }
    class ViewHolder{
        TextView tv_rangking,tv_userName,tv_fraction,tv_av;

    }
}
