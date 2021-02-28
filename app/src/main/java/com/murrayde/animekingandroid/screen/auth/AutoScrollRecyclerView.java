package com.murrayde.animekingandroid.screen.auth;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class AutoScrollRecyclerView extends RecyclerView {

    private static final String TAG = AutoScrollRecyclerView.class.getSimpleName();
    private static final int SPEED = 10;
    /**
     * Sliding estimator
     */
    private UniformSpeedInterpolator mInterpolator;
    /**
     * Dx and dy between units
     */
    private int mSpeedDx, mSpeedDy;
    /**
     * Sliding speed, default 100
     */
    private int mCurrentSpeed = SPEED;
    /**
     * Whether to display the list infinitely
     */
    private boolean mLoopEnabled;
    /**
     * Whether to slide backwards
     */
    private boolean mReverse;
    /**
     * Whether to turn on automatic sliding
     */
    private boolean mIsOpenAuto;
    /**
     * Whether the user can manually slide the screen
     */
    private boolean mCanTouch = true;
    /**
     * Whether the user clicks on the screen
     */
    private boolean mPointTouch;
    /**
     * Are you ready for data?
     */
    private boolean mReady;
    /**
     * Whether initialization is complete
     */
    private boolean mInflate;

    /**
     * Whether to stop scroll
     */
    private boolean isStopAutoScroll = false;

    public AutoScrollRecyclerView(Context context) {
        this(context, null);
    }

    public AutoScrollRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoScrollRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mInterpolator = new UniformSpeedInterpolator();
        mReady = false;
    }

    /**
     * Start sliding
     */
    public void startAutoScroll() {
        isStopAutoScroll = false;
        openAutoScroll(mCurrentSpeed, false);
    }

    /**
     * Start sliding
     *
     * @param speed   Sliding distance (determining the sliding speed)
     * @param reverse Whether to slide backwards
     */
    public void openAutoScroll(int speed, boolean reverse) {
        mReverse = reverse;
        mCurrentSpeed = speed;
        mIsOpenAuto = true;
        notifyLayoutManager();
        startScroll();
    }

    /**
     * Is it possible to manually slide when swiping automatically?
     */
    public void setCanTouch(boolean b) {
        mCanTouch = b;
    }

    public boolean canTouch() {
        return mCanTouch;
    }

    /**
     * Set whether to display the list infinitely
     */
    public void setLoopEnabled(boolean loopEnabled) {
        this.mLoopEnabled = loopEnabled;

        if (getAdapter() != null) {
            getAdapter().notifyDataSetChanged();
            startScroll();
        }
    }

    /**
     * Whether to slide infinitely
     */
    public boolean isLoopEnabled() {
        return mLoopEnabled;
    }

    /**
     * Set whether to reverse
     */
    public void setReverse(boolean reverse) {
        mReverse = reverse;
        notifyLayoutManager();
        startScroll();
    }

    /**
     * @param isStopAutoScroll
     */
    public void pauseAutoScroll(boolean isStopAutoScroll) {
        this.isStopAutoScroll = isStopAutoScroll;
    }

    public boolean getReverse() {
        return mReverse;
    }

    /**
     * Start scrolling
     */
    private void startScroll() {
        if (!mIsOpenAuto)
            return;
        if (getScrollState() == SCROLL_STATE_SETTLING)
            return;
        if (mInflate && mReady) {
            mSpeedDx = mSpeedDy = 0;
            smoothScroll();
        }
    }

    private void smoothScroll() {
        if (!isStopAutoScroll) {

            int absSpeed = Math.abs(mCurrentSpeed);
            int d = mReverse ? -absSpeed : absSpeed;
            smoothScrollBy(d, d, mInterpolator);
        }
    }

    private void notifyLayoutManager() {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {

            LinearLayoutManager linearLayoutManager = ((LinearLayoutManager) layoutManager);
            if (linearLayoutManager != null) {
                linearLayoutManager.setReverseLayout(mReverse);
            }

        } else {
            StaggeredGridLayoutManager staggeredGridLayoutManager = ((StaggeredGridLayoutManager) layoutManager);
            if (staggeredGridLayoutManager != null) {
                staggeredGridLayoutManager.setReverseLayout(mReverse);
            }
        }
    }

    @Override
    public void swapAdapter(Adapter adapter, boolean removeAndRecycleExistingViews) {
        super.swapAdapter(generateAdapter(adapter), removeAndRecycleExistingViews);
        mReady = true;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(generateAdapter(adapter));
        mReady = true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        if (mCanTouch) {
            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mPointTouch = true;
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    if (mIsOpenAuto) {
                        return true;
                    }
            }
            return super.onInterceptTouchEvent(e);
        } else return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (mCanTouch) {
            switch (e.getAction()) {
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    if (mIsOpenAuto) {
                        mPointTouch = false;
                        smoothScroll();
                        return true;
                    }
            }
            return super.onTouchEvent(e);
        } else return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        startScroll();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mInflate = true;
    }

    @Override
    public void onScrolled(int dx, int dy) {
        if (mPointTouch) {
            mSpeedDx = 0;
            mSpeedDy = 0;
            return;
        }
        boolean vertical;
        if (dx == 0) {//Vertical scrolling
            mSpeedDy += dy;
            vertical = true;
        } else {//Horizontal scrolling
            mSpeedDx += dx;
            vertical = false;
        }

        if (vertical) {
            if (Math.abs(mSpeedDy) >= Math.abs(mCurrentSpeed)) {
                mSpeedDy = 0;
                smoothScroll();
            }
        } else {
            if (Math.abs(mSpeedDx) >= Math.abs(mCurrentSpeed)) {
                mSpeedDx = 0;
                smoothScroll();
            }
        }
    }

    @NonNull
    @SuppressWarnings("unchecked")
    private NestingRecyclerViewAdapter generateAdapter(Adapter adapter) {
        return new NestingRecyclerViewAdapter(this, adapter);
    }

    /**
     * Custom estimator
     * Swipe the list at a constant speed
     */
    private static class UniformSpeedInterpolator implements Interpolator {
        @Override
        public float getInterpolation(float input) {
            return input;
        }
    }

    /**
     * Customize the Adapter container so that the list can be displayed in an infinite loop
     */
    private static class NestingRecyclerViewAdapter<VH extends RecyclerView.ViewHolder>
            extends RecyclerView.Adapter<VH> {

        private AutoScrollRecyclerView mRecyclerView;
        RecyclerView.Adapter<VH> mAdapter;


        NestingRecyclerViewAdapter(AutoScrollRecyclerView recyclerView, RecyclerView.Adapter<VH> adapter) {
            mAdapter = adapter;
            mRecyclerView = recyclerView;
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return mAdapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
            super.registerAdapterDataObserver(observer);
            mAdapter.registerAdapterDataObserver(observer);
        }

        @Override
        public void unregisterAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
            super.unregisterAdapterDataObserver(observer);
            mAdapter.unregisterAdapterDataObserver(observer);
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
            mAdapter.onBindViewHolder(holder, generatePosition(position));
        }

        @Override
        public void setHasStableIds(boolean hasStableIds) {
            super.setHasStableIds(hasStableIds);
            mAdapter.setHasStableIds(hasStableIds);
        }

        @Override
        public int getItemCount() {
            //If it is an infinite scroll mode, set an unlimited number of items
            return getLoopEnable() ? Integer.MAX_VALUE : mAdapter.getItemCount();
        }

        @Override
        public int getItemViewType(int position) {
            return mAdapter.getItemViewType(generatePosition(position));
        }

        @Override
        public long getItemId(int position) {
            return mAdapter.getItemId(generatePosition(position));
        }

        /**
         * Returns the corresponding position according to the current scroll mode
         */
        private int generatePosition(int position) {

            if (getLoopEnable()) {
                return getActualPosition(position);
            } else {
                return position;
            }
        }

        /**
         * Returns the actual position of the item
         *
         * @param position The position after starting to scroll will grow indefinitely
         * @return Item actual location
         */
        private int getActualPosition(int position) {
            int itemCount = mAdapter.getItemCount();
            Log.d("AutoScrollRecyclerView", "Testing: " + itemCount);
            return position >= itemCount ? position % itemCount : position;

        }

        private boolean getLoopEnable() {
            return mRecyclerView.mLoopEnabled;
        }

        public boolean getReverse() {
            return mRecyclerView.mReverse;
        }
    }
}