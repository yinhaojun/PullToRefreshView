package com.yinhaojun.library;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by victor on 2017/9/5.
 */

public class PullToRefreshVerticalRecyclerView extends PullToRefreshBase<RecyclerView> {

    public PullToRefreshVerticalRecyclerView(Context context) {
        super(context);
    }

    public PullToRefreshVerticalRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshVerticalRecyclerView(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshVerticalRecyclerView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    @Override
    public Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }


    @Override
    protected RecyclerView createRefreshableView(Context context,
                                                 AttributeSet attrs) {
        return new RecyclerView(context, attrs);
    }

    @Override
    protected boolean isReadyForPullStart() {
        return isFirstItemVisible();
    }

    @Override
    protected boolean isReadyForPullEnd() {
        return isLastItemVisible();
    }

    private boolean isFirstItemVisible() {
        final Adapter<?> adapter = getRefreshableView().getAdapter();

        // 如果未设置Adapter或者Adapter没有数据可以下拉刷新
        if (null == adapter || adapter.getItemCount() == 0) {
            if (DEBUG) {
                Log.d(LOG_TAG, "isFirstItemVisible. Empty View.");
            }
            return true;

        } else {
            // 第一个条目完全展示,可以刷新
            if (getFirstVisiblePosition() == 0) {
                return mRefreshableView.getChildAt(0).getTop() >= mRefreshableView
                        .getTop();
            }
        }

        return false;
    }

    private int getFirstVisiblePosition() {
        View firstVisibleChild = mRefreshableView.getChildAt(0);
        return firstVisibleChild != null ? mRefreshableView
                .getChildAdapterPosition(firstVisibleChild) : -1;
    }

    private boolean isLastItemVisible() {
        final Adapter<?> adapter = getRefreshableView().getAdapter();

        // 如果未设置Adapter或者Adapter没有数据可以上拉刷新
        if (null == adapter || adapter.getItemCount() == 0) {
            if (DEBUG) {
                Log.d(LOG_TAG, "isLastItemVisible. Empty View.");
            }
            return true;

        } else {
            // 最后一个条目View完全展示,可以刷新
            int lastVisiblePosition = getLastVisiblePosition();
            if (lastVisiblePosition >= mRefreshableView.getAdapter().getItemCount() - 1) {
                return mRefreshableView.getChildAt(
                        mRefreshableView.getChildCount() - 1).getBottom() <= mRefreshableView
                        .getBottom();
            }
        }

        return false;
    }

    private int getLastVisiblePosition() {
        View lastVisibleChild = mRefreshableView.getChildAt(mRefreshableView
                .getChildCount() - 1);
        return lastVisibleChild != null ? mRefreshableView
                .getChildAdapterPosition(lastVisibleChild) : -1;
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decor) {
        getRefreshableView().addItemDecoration(decor, -1);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layout) {
        getRefreshableView().setLayoutManager(layout);
    }

    public void setAdapter(Adapter adapter) {
        getRefreshableView().setAdapter(adapter);
    }

    public Adapter getAdapter() {
        return getRefreshableView().getAdapter();
    }
}
