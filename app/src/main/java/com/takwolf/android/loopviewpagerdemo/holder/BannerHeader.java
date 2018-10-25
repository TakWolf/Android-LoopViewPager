package com.takwolf.android.loopviewpagerdemo.holder;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;
import com.takwolf.android.loopviewpager.LoopViewPager;
import com.takwolf.android.loopviewpagerdemo.R;
import com.takwolf.android.loopviewpagerdemo.adapter.BannerPagerAdapter;
import com.takwolf.android.loopviewpagerdemo.model.Banner;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BannerHeader {

    @BindView(R.id.view_pager)
    LoopViewPager viewPager;

    private final BannerPagerAdapter adapter;

    public BannerHeader(@NonNull Activity activity, @NonNull HeaderAndFooterRecyclerView recyclerView) {
        View headerView = LayoutInflater.from(activity).inflate(R.layout.header_banner, recyclerView.getHeaderContainer(), false);
        recyclerView.addHeaderView(headerView);
        ButterKnife.bind(this, headerView);

        adapter = new BannerPagerAdapter(activity, 0);
        viewPager.setDataAdapter(adapter);
    }

    public void setBannerListAndNotify(@NonNull List<Banner> bannerList) {
        adapter.getBannerList().clear();
        adapter.getBannerList().addAll(bannerList);
        adapter.notifyDataSetChanged();
        viewPager.setFillOffscreenPageLimit();
        viewPager.setCurrentItem(0, false);
    }

}