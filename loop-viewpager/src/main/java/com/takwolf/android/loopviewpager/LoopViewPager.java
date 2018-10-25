package com.takwolf.android.loopviewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class LoopViewPager extends ViewPager {

    public static final int INVALID_TYPE = -1;
    public static final int NO_POSITION = -1;

    private boolean looping;

    private final ProxyAdapter proxyAdapter = new ProxyAdapter();
    private final ProxyOnPageChangeListener proxyOnPageChangeListener = new ProxyOnPageChangeListener();

    @Nullable
    private RecycledViewPool recycledViewPool;

    @Nullable
    private List<OnAdapterChangeListener> onAdapterChangeListenerList;

    public LoopViewPager(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public LoopViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoopViewPager);
        looping = a.getBoolean(R.styleable.LoopViewPager_lvp_looping, false);
        a.recycle();

        super.setAdapter(proxyAdapter);
        super.addOnPageChangeListener(proxyOnPageChangeListener);
    }

    public boolean isLooping() {
        return looping;
    }

    public void setLooping(boolean looping) {
        if (this.looping != looping) {
            int position = getCurrentItem();
            this.looping = looping;
            proxyAdapter.notifyDataSetChanged();
            setCurrentItem(position);
        }
    }

    private int calculateAdapterPosition(int layoutPosition) {
        if (looping) {
            return layoutPosition % proxyAdapter.getItemCount();
        } else {
            return layoutPosition;
        }
    }

    private int calculateLayoutPosition(int adapterPosition) {
        if (looping) {
            return adapterPosition + proxyAdapter.getItemCount();
        } else {
            return adapterPosition;
        }
    }

    @Override
    public void setCurrentItem(int item) {
        if (item < 0) {
            item = 0;
        } else if (item >= proxyAdapter.getItemCount()) {
            item = proxyAdapter.getItemCount() - 1;
        }
        item = calculateLayoutPosition(item);
        super.setCurrentItem(item);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        if (item < 0) {
            item = 0;
        } else if (item >= proxyAdapter.getItemCount()) {
            item = proxyAdapter.getItemCount() - 1;
        }
        item = calculateLayoutPosition(item);
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public int getCurrentItem() {
        if (looping) {
            return super.getCurrentItem() % proxyAdapter.getItemCount();
        } else {
            return super.getCurrentItem();
        }
    }

    @NonNull
    public RecycledViewPool getRecycledViewPool() {
        if (recycledViewPool == null) {
            recycledViewPool = new RecycledViewPool();
        }
        return recycledViewPool;
    }

    public void setRecycledViewPool(@Nullable RecycledViewPool recycledViewPool) {
        this.recycledViewPool = recycledViewPool;
    }

    public void addOnAdapterChangeListener(@NonNull OnAdapterChangeListener listener) {
        if (onAdapterChangeListenerList == null) {
            onAdapterChangeListenerList = new ArrayList<>();
        }
        onAdapterChangeListenerList.add(listener);
    }

    public void removeOnAdapterChangeListener(@NonNull OnAdapterChangeListener listener) {
        if (onAdapterChangeListenerList != null) {
            onAdapterChangeListenerList.remove(listener);
        }
    }

    void dispatchOnAdapterChanged(@Nullable RecycledPagerAdapter oldAdapter, @Nullable RecycledPagerAdapter newAdapter) {
        if (onAdapterChangeListenerList != null && !onAdapterChangeListenerList.isEmpty()) {
            for (OnAdapterChangeListener onAdapterChangeListener : onAdapterChangeListenerList) {
                onAdapterChangeListener.onAdapterChanged(this, oldAdapter, newAdapter);
            }
        }
    }

    @NonNull
    public ProxyAdapter getProxyAdapter() {
        return proxyAdapter;
    }

    @Nullable
    public RecycledPagerAdapter getDataAdapter() {
        return proxyAdapter.getAdapter();
    }

    public void setDataAdapter(@Nullable RecycledPagerAdapter adapter) {
        super.setAdapter(null);
        getRecycledViewPool().clear();
        RecycledPagerAdapter oldAdapter = proxyAdapter.getAdapter();
        proxyAdapter.setAdapter(adapter);
        super.setAdapter(proxyAdapter);
        dispatchOnAdapterChanged(oldAdapter, adapter);
        setCurrentItem(0, false);
    }

    /**
     * see {@link #getProxyAdapter()}
     * see {@link #getDataAdapter()}
     */
    @Deprecated
    @Nullable
    @Override
    public PagerAdapter getAdapter() {
        return proxyAdapter;
    }

    /**
     * see {@link #setDataAdapter(RecycledPagerAdapter)}
     */
    @Deprecated
    @Override
    public void setAdapter(@Nullable PagerAdapter adapter) {
        throw new UnsupportedOperationException("Function is unsupported.");
    }

    @Override
    public void addOnPageChangeListener(@NonNull OnPageChangeListener listener) {
        proxyOnPageChangeListener.addOnPageChangeListener(listener);
    }

    @Override
    public void removeOnPageChangeListener(@NonNull OnPageChangeListener listener) {
        proxyOnPageChangeListener.removeOnPageChangeListener(listener);
    }

    @Override
    public void clearOnPageChangeListeners() {
        proxyOnPageChangeListener.clearOnPageChangeListeners();
    }

    @Deprecated
    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        proxyOnPageChangeListener.setOnPageChangeListener(listener);
    }

    public void setFillOffscreenPageLimit() {
        setOffscreenPageLimit(proxyAdapter.getCount());
    }

    public static abstract class ViewHolder {

        private final View itemView;
        private int itemViewType = INVALID_TYPE;
        private int layoutPosition = NO_POSITION;
        private int adapterPosition = NO_POSITION;

        public ViewHolder(@NonNull View itemView) {
            this.itemView = itemView;
        }

        @NonNull
        public View getItemView() {
            return itemView;
        }

        public int getItemViewType() {
            return itemViewType;
        }

        public int getLayoutPosition() {
            return layoutPosition;
        }

        public int getAdapterPosition() {
            return adapterPosition;
        }

        void setItemViewType(int itemViewType) {
            this.itemViewType = itemViewType;
        }

        void setLayoutPosition(int layoutPosition) {
            this.layoutPosition = layoutPosition;
        }

        void setAdapterPosition(int adapterPosition) {
            this.adapterPosition = adapterPosition;
        }

        void resetInternal() {
            layoutPosition = NO_POSITION;
            adapterPosition = NO_POSITION;
        }

    }

    public static class RecycledViewPool {

        private final SparseArray<List<ViewHolder>> scrap = new SparseArray<>();

        @Nullable
        public ViewHolder getRecycledView(int viewType) {
            List<ViewHolder> heap = scrap.get(viewType);
            if (heap != null && !heap.isEmpty()) {
                return heap.remove(0);
            } else {
                return null;
            }
        }

        public void putRecycledView(@NonNull ViewHolder holder) {
            int viewType = holder.getItemViewType();
            List<ViewHolder> heap = scrap.get(viewType);
            if (heap == null) {
                heap = new ArrayList<>();
                scrap.put(viewType, heap);
            }
            holder.resetInternal();
            heap.add(holder);
        }

        public void clear() {
            for (int i = 0; i < scrap.size(); i++) {
                List<ViewHolder> heap = scrap.valueAt(i);
                heap.clear();
            }
        }

        public int getRecycledViewCount(int viewType) {
            List<ViewHolder> heap = scrap.get(viewType);
            return heap == null ? 0 : heap.size();
        }

    }

    public static abstract class RecycledPagerAdapter<VH extends ViewHolder> {

        private final DataSetObservable dataSetObservable = new DataSetObservable();

        public final void notifyDataSetChanged() {
            dataSetObservable.notifyChanged();
        }

        public final void registerDataSetObserver(@NonNull DataSetObserver observer) {
            dataSetObservable.registerObserver(observer);
        }

        public final void unregisterDataSetObserver(@NonNull DataSetObserver observer) {
            dataSetObservable.unregisterObserver(observer);
        }

        public abstract int getItemCount();

        public int getItemViewType(int position) {
            return 0;
        }

        @NonNull
        public abstract VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

        public abstract void onBindViewHolder(@NonNull VH holder, int position);

        public void onViewRecycled(@NonNull VH holder) {}

        public void onViewAttachedToWindow(@NonNull VH holder) {}

        public void onViewDetachedFromWindow(@NonNull VH holder) {}

        public void onAttachedToViewPager(@NonNull LoopViewPager viewPager) {}

        public void onDetachedFromViewPager(@NonNull LoopViewPager viewPager) {}

        @Nullable
        public CharSequence getPageTitle(int position) {
            return null;
        }

        public float getPageWidth(int position) {
            return 1.0f;
        }

        @Nullable
        public Parcelable saveState() {
            return null;
        }

        public void restoreState(@Nullable Parcelable state, @Nullable ClassLoader loader) {}

    }

    public final class ProxyAdapter extends PagerAdapter {

        @Nullable
        private RecycledPagerAdapter adapter;

        private final DataSetObserver dataSetObserver = new DataSetObserver() {

            @Override
            public void onChanged() {
                notifyDataSetChanged();
            }

            @Override
            public void onInvalidated() {
                notifyDataSetChanged();
            }

        };

        @Nullable
        public RecycledPagerAdapter getAdapter() {
            return adapter;
        }

        void setAdapter(@Nullable RecycledPagerAdapter adapter) {
            if (this.adapter != null) {
                this.adapter.unregisterDataSetObserver(dataSetObserver);
                this.adapter.onDetachedFromViewPager(LoopViewPager.this);
            }
            this.adapter = adapter;
            if (adapter != null) {
                adapter.registerDataSetObserver(dataSetObserver);
                adapter.onAttachedToViewPager(LoopViewPager.this);
            }
        }

        public int getItemCount() {
            if (adapter != null) {
                return adapter.getItemCount();
            } else {
                return 0;
            }
        }

        @Override
        public int getCount() {
            if (isLooping()) {
                return getItemCount() * 3;
            } else {
                return getItemCount();
            }
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            if (adapter != null) {
                int adapterPosition = calculateAdapterPosition(position);
                int viewType = adapter.getItemViewType(adapterPosition);
                ViewHolder holder = getRecycledViewPool().getRecycledView(viewType);
                if (holder == null) {
                    holder = adapter.onCreateViewHolder(container, viewType);
                    holder.setItemViewType(viewType);
                }
                holder.setLayoutPosition(position);
                holder.setAdapterPosition(adapterPosition);
                adapter.onBindViewHolder(holder, adapterPosition);
                container.addView(holder.getItemView());
                adapter.onViewAttachedToWindow(holder);
                return holder;
            } else {
                throw new RuntimeException("Raw adapter has not been set.");
            }
        }

        @SuppressWarnings("unchecked")
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            if (adapter != null) {
                ViewHolder holder = (ViewHolder) object;
                container.removeView(holder.getItemView());
                adapter.onViewDetachedFromWindow(holder);
                getRecycledViewPool().putRecycledView(holder);
                adapter.onViewRecycled(holder);
            }
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            ViewHolder holder = (ViewHolder) object;
            return view == holder.getItemView();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            if (adapter != null) {
                int adapterPosition = calculateAdapterPosition(position);
                return adapter.getPageTitle(adapterPosition);
            } else {
                return super.getPageTitle(position);
            }
        }

        @Override
        public float getPageWidth(int position) {
            if (adapter != null) {
                int adapterPosition = calculateAdapterPosition(position);
                return adapter.getPageWidth(adapterPosition);
            } else {
                return super.getPageWidth(position);
            }
        }

        @Nullable
        @Override
        public Parcelable saveState() {
            if (adapter != null) {
                return adapter.saveState();
            } else {
                return super.saveState();
            }
        }

        @Override
        public void restoreState(@Nullable Parcelable state, @Nullable ClassLoader loader) {
            if (adapter != null) {
                adapter.restoreState(state, loader);
            }
        }

    }

    private final class ProxyOnPageChangeListener implements OnPageChangeListener {

        @Nullable
        private List<OnPageChangeListener> onPageChangeListenerList;

        @Nullable
        private OnPageChangeListener onPageChangeListener;

        void addOnPageChangeListener(@NonNull OnPageChangeListener listener) {
            if (onPageChangeListenerList == null) {
                onPageChangeListenerList = new ArrayList<>();
            }
            onPageChangeListenerList.add(listener);
        }

        void removeOnPageChangeListener(@NonNull OnPageChangeListener listener) {
            if (onPageChangeListenerList != null) {
                onPageChangeListenerList.remove(listener);
            }
        }

        void clearOnPageChangeListeners() {
            if (onPageChangeListenerList != null) {
                onPageChangeListenerList.clear();
            }
        }

        void setOnPageChangeListener(@Nullable OnPageChangeListener listener) {
            onPageChangeListener = listener;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (isLooping()) {
                int itemCount = getProxyAdapter().getItemCount();
                if (positionOffsetPixels == 0) {
                    if (position <= itemCount - 1 || position >= itemCount * 2) {
                        LoopViewPager.super.setCurrentItem(position % itemCount + itemCount, false);
                    }
                }
            }
            position = calculateAdapterPosition(position);
            if (onPageChangeListenerList != null && !onPageChangeListenerList.isEmpty()) {
                for (ViewPager.OnPageChangeListener onPageChangeListener : onPageChangeListenerList) {
                    onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }
            if (onPageChangeListener != null) {
                onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageSelected(int position) {
            position = calculateAdapterPosition(position);
            if (onPageChangeListenerList != null && !onPageChangeListenerList.isEmpty()) {
                for (ViewPager.OnPageChangeListener onPageChangeListener : onPageChangeListenerList) {
                    onPageChangeListener.onPageSelected(position);
                }
            }
            if (onPageChangeListener != null) {
                onPageChangeListener.onPageSelected(position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (onPageChangeListenerList != null && !onPageChangeListenerList.isEmpty()) {
                for (ViewPager.OnPageChangeListener onPageChangeListener : onPageChangeListenerList) {
                    onPageChangeListener.onPageScrollStateChanged(state);
                }
            }
            if (onPageChangeListener != null) {
                onPageChangeListener.onPageScrollStateChanged(state);
            }
        }

    }

    public interface OnAdapterChangeListener {

        void onAdapterChanged(@NonNull ViewPager viewPager, @Nullable RecycledPagerAdapter oldAdapter, @Nullable RecycledPagerAdapter newAdapter);

    }

}
