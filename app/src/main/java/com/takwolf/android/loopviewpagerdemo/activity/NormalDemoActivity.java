package com.takwolf.android.loopviewpagerdemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;

import com.takwolf.android.loopviewpager.LoopViewPager;
import com.takwolf.android.loopviewpagerdemo.R;
import com.takwolf.android.loopviewpagerdemo.adapter.BannerPagerAdapter;
import com.takwolf.android.loopviewpagerdemo.listener.NavigationFinishClickListener;
import com.takwolf.android.loopviewpager.FixPositionPageTransformer;
import com.takwolf.android.loopviewpagerdemo.listener.RotateDownTransformer;
import com.takwolf.android.loopviewpagerdemo.model.Banner;
import com.takwolf.android.loopviewpagerdemo.util.HandlerUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class NormalDemoActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.view_pager)
    LoopViewPager viewPager;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.switch_looping)
    SwitchCompat switchLooping;

    @BindView(R.id.switch_auto_play)
    SwitchCompat switchAutoPlay;

    @BindView(R.id.switch_padding_mode)
    SwitchCompat switchPaddingMode;

    private BannerPagerAdapter adapter;

    private int gapSpace;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_demo);
        ButterKnife.bind(this);

        gapSpace = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));

        viewPager.setPageTransformer(true, new FixPositionPageTransformer(new RotateDownTransformer()));
        adapter = new BannerPagerAdapter(this, gapSpace);
        adapter.getBannerList().addAll(Banner.buildList());
        viewPager.setDataAdapter(adapter);
        viewPager.setFillOffscreenPageLimit();

        tabLayout.setupWithViewPager(viewPager);

        switchLooping.setChecked(viewPager.isLooping());

        refreshLayout.setColorSchemeResources(R.color.color_accent);
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        HandlerUtils.handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                adapter.getBannerList().clear();
                adapter.getBannerList().addAll(Banner.buildList());
                adapter.notifyDataSetChanged();
                viewPager.setFillOffscreenPageLimit();
                refreshLayout.setRefreshing(false);
            }

        }, 1000);
    }

    @OnCheckedChanged(R.id.switch_looping)
    void onSwitchLoopingChanged(boolean checked) {
        viewPager.setLooping(checked);
    }

    @OnCheckedChanged(R.id.switch_auto_play)
    void onSwitchAutoPlayChanged(boolean checked) {

        // TODO

    }

    @OnCheckedChanged(R.id.switch_padding_mode)
    void onSwitchPaddingModeChanged(boolean checked) {
        if (checked) {
            viewPager.setPadding(gapSpace * 4, gapSpace, gapSpace * 4, gapSpace);
        } else {
            viewPager.setPadding(0, 0, 0, 0);
        }
        adapter.setPaddingMode(checked);
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.btn_set_prev_item)
    void onBtnSetPrevItemClick() {
        viewPager.setPrevItem();
    }

    @OnClick(R.id.btn_set_next_item)
    void onBtnSetNextItemClick() {
        viewPager.setNextItem();
    }

    @OnClick(R.id.btn_replace_adapter)
    void onBtnReplaceAdapterClick() {
        adapter = new BannerPagerAdapter(this, gapSpace);
        adapter.getBannerList().addAll(Banner.buildList());
        adapter.setPaddingMode(switchPaddingMode.isChecked());
        viewPager.setDataAdapter(adapter);
        viewPager.setFillOffscreenPageLimit();
    }

}
