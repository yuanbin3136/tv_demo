package com.wind.yuanbin.demo.waterfall;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.wind.yuanbin.demo.utils.L;


public class NewRecycleView_MoreScroll extends RecyclerView {
    public NewRecycleView_MoreScroll(Context context) {
        super(context);
    }

    public NewRecycleView_MoreScroll(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NewRecycleView_MoreScroll(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private int moreScrollY;

    public final static int MORESCROLLY_UP = -1;
    public final static int MORESCROLLY_NO = 0;
    public final static int MORESCROLLY_DOWN = 1;
    /**
     * 0
     * 1 向下多滚动
     * -1 向上多滚动
     * @param moreScrollY
     */
    public void setMoreScrollY(int moreScrollY) {
        this.moreScrollY = moreScrollY;
    }
    private int distance = 150;

    /**
     * 设置一个距离，会多滚动这段距离
     * @param distance
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public void smoothScrollBy(int dx, int dy) {
//        L.o("test more" + dy);
        if (dy < 0){
            direction = dy;
            setMoreScrollY(MORESCROLLY_UP);
        }else if (dy > 0){
            direction = dy;
            setMoreScrollY(MORESCROLLY_DOWN);
        }
        if (moreScrollY > 0){
            dy += distance;
        }else if (moreScrollY < 0){
            dy -= distance;
        }
        if (onScrollStateLis != null){
            onScrollStateLis.setDirection(direction);
        }
        super.smoothScrollBy(dx, dy);
    }
    private int direction;
    public abstract static class OnScrollStateLis extends OnScrollListener {
        private int direction;

        public void setDirection(int direction) {
            this.direction = direction;
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            onScrollStateChanged(recyclerView,newState,direction);
        }


        public abstract void onScrollStateChanged(RecyclerView recyclerView, int newState, int direction);
    }
    OnScrollStateLis onScrollStateLis;

    public void setOnScrollStateLis(OnScrollStateLis onScrollStateLis) {
        this.onScrollStateLis = onScrollStateLis;
        this.addOnScrollListener(onScrollStateLis);
    }
}
