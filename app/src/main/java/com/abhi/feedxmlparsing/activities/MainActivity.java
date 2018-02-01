package com.abhi.feedxmlparsing.activities;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.abhi.feedxmlparsing.R;
import com.abhi.feedxmlparsing.adapter.FeedListAdapter;
import com.abhi.feedxmlparsing.model.Entry;
import com.abhi.feedxmlparsing.net.ConnectionDetector;
import com.abhi.feedxmlparsing.net.DownloadNetworkTask;
import com.abhi.feedxmlparsing.net.ServiceCallBack;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ServiceCallBack {

    private RecyclerView mFeedListView;
    private String URL="https://stackoverflow.com/feeds/tag?tagnames=android&sort=newest";
    private ArrayList<Entry> mFeedList=new ArrayList<>();
    FeedListAdapter mListadapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mSwipeRefreshLayout=findViewById(R.id.swipeRefreshLayout);
        mFeedListView=findViewById(R.id.recyclerViewRssFeed);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        mFeedListView.setLayoutManager(layoutManager);
        mListadapter=new FeedListAdapter(this,mFeedList);
        mFeedListView.setAdapter(mListadapter);


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callService();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void callService(){
        if(ConnectionDetector.isNetworkAvailable(this)) {
            DownloadNetworkTask mTask = new DownloadNetworkTask(this, this, CALL_FEED_SERVICE);
            mTask.execute(URL);
        }else{
            mFeedList.clear();
            mListadapter.notifyDataSetChanged();
            mSwipeRefreshLayout.setRefreshing(false);
            Toast.makeText(this,"Please check internet connection",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        callService();
    }

    @Override
    public void onResult(Object data, int caller) {
        if(caller==CALL_FEED_SERVICE){
            mFeedList.clear();
            ArrayList<Entry> list=(ArrayList<Entry>) data;
            mFeedList.addAll(list);
            mListadapter.notifyDataSetChanged();
            mSwipeRefreshLayout.setRefreshing(false);
            Toast.makeText(this,"Success!!!",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onError(String error, int caller) {
        Toast.makeText(this,"Error:"+error,Toast.LENGTH_LONG).show();
        mListadapter.notifyDataSetChanged();

    }

    @Override
    public void onRequestCancel(String error, int caller) {
        Toast.makeText(this,"Error:"+error,Toast.LENGTH_LONG).show();
        mListadapter.notifyDataSetChanged();

    }
}
