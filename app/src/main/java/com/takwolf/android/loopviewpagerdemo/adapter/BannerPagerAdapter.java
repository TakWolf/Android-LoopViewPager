package com.takwolf.android.loopviewpagerdemo.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.takwolf.android.loopviewpager.LoopViewPager;
import com.takwolf.android.loopviewpagerdemo.R;
import com.takwolf.android.loopviewpagerdemo.model.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BannerPagerAdapter extends LoopViewPager.RecycledPagerAdapter<BannerPagerAdapter.ViewHolder> {

    private final Activity activity;
    private final LayoutInflater inflater;
    private final List<Banner> bannerList = new ArrayList<>();
    private final int gapSpace;

    private boolean paddingMode = false;

    public BannerPagerAdapter(@NonNull Activity activity, int gapSpace, boolean paddingMode) {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        this.gapSpace = gapSpace;
        this.paddingMode = paddingMode;
    }

    public int getGapSpace() {
        return gapSpace;
    }

    public boolean isPaddingMode() {
        return paddingMode;
    }

    public void setPaddingMode(boolean paddingMode) {
        this.paddingMode = paddingMode;
    }

    @NonNull
    public List<Banner> getBannerList() {
        return bannerList;
    }

    @Override
    public int getItemCount() {
        return bannerList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_banner, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return String.valueOf(position);
    }

    class ViewHolder extends LoopViewPager.ViewHolder {

        @BindView(R.id.layout_card)
        CardView layoutCard;

        @BindView(R.id.img_image)
        ImageView imgImage;

        @BindView(R.id.tv_title)
        TextView tvTitle;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(int position) {
            Banner banner = bannerList.get(position);
            Glide.with(activity).load(banner.getImage()).into(imgImage);
            tvTitle.setText(getAdapterPosition() + " - " + getLayoutPosition());
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) layoutCard.getLayoutParams();
            if (layoutParams != null) {
                if (isPaddingMode()) {
                    layoutParams.setMargins(getGapSpace(), getGapSpace(), getGapSpace(), getGapSpace());
                } else {
                    layoutParams.setMargins(0, 0, 0, 0);
                }
                layoutCard.setLayoutParams(layoutParams);
            }
        }

    }

}
