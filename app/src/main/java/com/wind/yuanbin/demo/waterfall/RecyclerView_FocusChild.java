package com.wind.yuanbin.demo.waterfall;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;

import com.wind.yuanbin.demo.utils.L;

public class RecyclerView_FocusChild extends RecyclerView {
    public RecyclerView_FocusChild(@NonNull Context context) {
        this(context,null);
    }

    public RecyclerView_FocusChild(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context,null,0);
    }

    public RecyclerView_FocusChild(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setChildrenDrawingOrderEnabled(true);
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        int x = AbsListView.INVALID_POSITION;
        if (mSelectedPosition != AbsListView.INVALID_POSITION) {
            if (i == mSelectedPosition) {
                x = childCount - 1;
            }
            if (i == childCount - 1) {
                x = mSelectedPosition;
            }
            if (x < childCount && x != AbsListView.INVALID_POSITION){
                return x;
            }
        }
        return super.getChildDrawingOrder(childCount, i);
    }

    private int mSelectedPosition;
    @Override
    public void bringChildToFront(View child) {
        mSelectedPosition = indexOfChild(child);
        invalidate();
    }
}
