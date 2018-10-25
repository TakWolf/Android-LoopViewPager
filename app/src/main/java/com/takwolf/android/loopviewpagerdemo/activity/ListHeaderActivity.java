package com.takwolf.android.loopviewpagerdemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;
import com.takwolf.android.loopviewpagerdemo.R;
import com.takwolf.android.loopviewpagerdemo.adapter.DataListAdapter;
import com.takwolf.android.loopviewpagerdemo.holder.BannerHeader;
import com.takwolf.android.loopviewpagerdemo.listener.NavigationFinishClickListener;
import com.takwolf.android.loopviewpagerdemo.model.Banner;
import com.takwolf.android.loopviewpagerdemo.util.HandlerUtils;
import com.takwolf.android.loopviewpagerdemo.util.RandomUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListHeaderActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.recycler_view)
    HeaderAndFooterRecyclerView recyclerView;

    private BannerHeader bannerHeader;
    private DataListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_header);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bannerHeader = new BannerHeader(this, recyclerView);
        adapter = new DataListAdapter(this);
        recyclerView.setAdapter(adapter);

        refreshLayout.setColorSchemeResources(R.color.color_accent);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        HandlerUtils.handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                bannerHeader.setBannerListAndNotify(Banner.buildList());
                adapter.setCount(Math.abs(RandomUtils.random.nextInt()) % 50);
                adapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }

        }, 1000);
    }

}
