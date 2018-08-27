package com.close.common.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.close.common.library.R;

//  多状态View
public class MultiStateView extends FrameLayout {

    private int resIdLoading;
    private int resEmpty;
    private int resIdError;
    public static final int STATE_CONTENT = 10001;
    public static final int STATE_LOADING = 10002;
    public static final int STATE_EMPTY = 10003;
    public static final int STATE_FAIL = 10004;
    public static final int STATE_NONET = 10005;
    private int mCurrentState = STATE_CONTENT;
    private SparseIntArray mLayoutIDArray = new SparseIntArray();
    private SparseArray<View> mStateViewArray = new SparseArray<>();
    private View mContentView;

    public MultiStateView(@NonNull Context context) {
        this(context, null);
    }

    public MultiStateView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiStateView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributeSet(attrs);


    }

    private void initAttributeSet(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.MultiStateView);
        resIdLoading = array.getResourceId(R.styleable.MultiStateView_msv_loadView, -1);
        resEmpty = array.getResourceId(R.styleable.MultiStateView_msv_emptyView, -1);
        resIdError = array.getResourceId(R.styleable.MultiStateView_msv_errorView, -1);
        array.recycle();
        if (resIdLoading != -1) {
            addViewForStatus(STATE_LOADING, resIdLoading);
        }
        if (resEmpty != -1) {
            addViewForStatus(STATE_EMPTY, resEmpty);
        }
        if (resIdError != -1) {
            addViewForStatus(STATE_FAIL, resIdError);
        }

    }

    // 把 布局收藏
    private void addViewForStatus(int stateLoading, int resIdLoading) {
        mLayoutIDArray.put(stateLoading, resIdLoading);

    }

    // 控制状态的改变
    public void setViewState(int state) {
        if (getCurrentView() == null) {
            return;
        }
        if (state != mCurrentState) {
            View view = getView(state);
            getCurrentView().setVisibility(GONE);
            mCurrentState = state;
            if (view != null) {
                view.setVisibility(VISIBLE);
            } else {
                int resLayoutID = mLayoutIDArray.get(state);
                if (resLayoutID == 0) return;
                view = LayoutInflater.from(getContext()).inflate(resLayoutID, this, false);
                mStateViewArray.put(state, view);
                addView(view);
                view.setVisibility(VISIBLE);

            }


        }


    }

    @Override
    public void addView(View child) {
        validContentView(child);
        super.addView(child);
    }

    @Override
    public void addView(View child, int index) {
        super.addView(child, index);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        validContentView(child);
        super.addView(child, params);
    }

    @Override
    public void addView(View child, int width, int height) {
        validContentView(child);
        super.addView(child, width, height);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        validContentView(child);
        super.addView(child, index, params);
    }

    // 检查view 是否 为 content
    private void validContentView(View child) {
        if (isValidContentView(child)) {
            mContentView = child;
            mStateViewArray.put(STATE_CONTENT, child);
        } else if (mCurrentState != STATE_CONTENT) {
            if (mContentView != null) {
                mContentView.setVisibility(GONE);
            }
        }
    }

    private boolean isValidContentView(View child) {
        if (mContentView == null) {
            for (int i = 0; i < mStateViewArray.size(); i++) {
                if (mStateViewArray.indexOfValue(child) != -1) return false;
            }
            return true;

        }
        return false;

    }


    public View getCurrentView() {
        if (mCurrentState == -1) return null;
        return getView(mCurrentState);

    }


    public View getView(int state) {
        return mStateViewArray.get(state);
    }

    public int getViewSate() {
        return mCurrentState;
    }

    // 内容界面
    public void showContent() {
        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                setViewState(STATE_CONTENT);
            }
        }, 100);


    }
    // 加载界面
    public void showLoading() {
        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                setViewState(STATE_LOADING);
            }
        }, 100);
    }
    //空得界面
    public void showEmptyView() {

        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                setViewState(MultiStateView.STATE_EMPTY);
            }
        }, 100);


    }
    //  错误的界面
    public void showErrorView() {
        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                setViewState(MultiStateView.STATE_FAIL);
            }
        }, 100);

    }


}
