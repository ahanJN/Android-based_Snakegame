package com.fcmanager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.fcmanager.adapter.RankingAdapter;
import com.fcmanager.bean.Ranking;
import com.fcmanager.db.RankingManager;

import java.util.List;

/**
 * Created by Administrator on 2017/4/19.
 */
public class RankingActivity extends Activity {
    //listview
    public ListView listview;
    //数据库
    public RankingManager rankingManager;
    //adapter
    public RankingAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranking);
        InitView();
        rankingManager =new RankingManager(this);
        //查询所有的
        List<Ranking> list = rankingManager.queryById();
        //adapter
        adapter = new RankingAdapter(this,list);
        //set数据
        listview.setAdapter(adapter);

    }

    private void InitView() {
        listview = (ListView) findViewById(R.id.listview);

    }
}
