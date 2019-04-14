package com.wind.yuanbin.demo.waterfall;

import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.wind.yuanbin.demo.R;

import java.util.List;
import java.util.Map;

public class WaterFallAdapter extends RecyclerView.Adapter<WaterFallAdapter.BaseViewHolder>{
    Map<Integer, Items> mapContents;

    public Map<Integer, Items> getMapContents() {
        return mapContents;
    }

    public void setMapContents(Map<Integer, Items> mapContents) {
        this.mapContents = mapContents;
    }

    static class Items{
        String name;
        String type;
        List<Item> itemList;
    }

    static class Item{
        String name;
        String imgUrl;
    }

    final int TYPE_ONE = 0;
    final int TYPE_TWO = 1;
    final int TYPE_THREE = 2;
    final int TYPE_Four = 3;
    final int TYPE_Five = 4;

    public static final String WATERFALLTYPE_ONE = "waterfall_one";
    public static final String WATERFALLTYPE_TWO = "waterfall_two";
    public static final String WATERFALLTYPE_THREE = "waterfall_three";
    public static final String WATERFALLTYPE_FOUR = "waterfall_four";
    public static final String WATERFALLTYPE_FIVE = "waterfall_five";

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BaseViewHolder holder;
        View view = LayoutInflater.from(
                parent.getContext()).inflate(OneViewHolder.ResID, parent,
                false);
        holder = new OneViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseViewHolder holder, final  int position) {
        int type = getItemViewType(position);
        holder.setTitle(mapContents.get(position).name);
        BaseChildViewAdapter baseViewAdapter = null;
        int DpResId = 0;
        switch (type){
            case TYPE_ONE:
                baseViewAdapter = new OneViewChildAdapter();
                DpResId = OneViewChildAdapter.DpResID;
                break;
            case TYPE_TWO:
                baseViewAdapter = new TwoViewChildAdapter();
                DpResId = TwoViewChildAdapter.DpResID;
                break;
        }
        baseViewAdapter.list = mapContents.get(position).itemList;
        baseViewAdapter.setOnItemLis(new BaseChildViewAdapter.OnItemLis() {
            @Override
            public void onFocus(View v, boolean hasFocus, int childPosition) {
                if (onItemAllLis != null){
                    onItemAllLis.onFocus(v,hasFocus,position,childPosition);
                }
                BaseChildHolder baseChildHolder = (BaseChildHolder)((OneViewHolder)holder)
                        .rv_rv_item.getChildViewHolder(v);
                baseChildHolder.showFocus(hasFocus);
            }

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event, int childPosition) {
                if (onItemAllLis != null){
                    return onItemAllLis.onKey(v,keyCode,event,position,childPosition);
                }else return false;

            }

            @Override
            public void onClick(View v, int childPosition) {
                if (onItemAllLis != null){
                    onItemAllLis.onClick(v,position, childPosition);
                }
            }
        });
        holder.setAdapter(baseViewAdapter,DpResId);
    }

    @Override
    public void onViewRecycled(@NonNull BaseViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return mapContents.size();
    }

    @Override
    public int getItemViewType(int position) {
//        int y = position % 13;
//        if (true)
//        return y - 1;

        int x = -1;
        String type = mapContents.get(position).type;
        switch (type){
            case WATERFALLTYPE_ONE:
                x = TYPE_ONE;
                break;
            case WATERFALLTYPE_TWO:
                x = TYPE_TWO;
                break;
            case WATERFALLTYPE_THREE:
                x = TYPE_THREE;
                break;
            case WATERFALLTYPE_FOUR:
                x = TYPE_Four;
                break;
            case WATERFALLTYPE_FIVE:
                x = TYPE_Five;
                break;
        }
        return x;
    }

    public interface OnItemAllLis{
        void onFocus(View v, boolean hasFocus, int menuPosition, int position);
        boolean onKey(View v, int keyCode, KeyEvent event, int menuPosition, int position);
        void onClick(View v, int menuPosition, int position);
    }
    OnItemAllLis onItemAllLis;

    public void setOnItemLis(OnItemAllLis onItemLis) {
        this.onItemAllLis = onItemLis;
    }


    public static abstract class BaseViewHolder extends RecyclerView.ViewHolder{
        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        abstract void setTitle(String title);
        abstract void setAdapter(BaseChildViewAdapter baseViewAdapter, int DecDP);
    }
    static abstract class BaseChildViewAdapter extends RecyclerView.Adapter<BaseChildHolder>{
        List<Item> list;
        @Override
        public void onBindViewHolder(@NonNull BaseChildHolder holder, final int position) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemLis != null){
                        onItemLis.onClick(v,position);
                    }
                }
            });
            holder.itemView.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (onItemLis != null){
                        return onItemLis.onKey(v,keyCode,event,position);
                    }
                    return false;
                }
            });
            holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (onItemLis != null){
                        onItemLis.onFocus(v,hasFocus,position);
                    }
                }
            });

        }
        interface OnItemLis{
            void onFocus(View v, boolean hasFocus, int childPosition);
            boolean onKey(View v, int keyCode, KeyEvent event, int childPosition);
            void onClick(View v, int childPosition);
        }
        OnItemLis onItemLis;

        public void setOnItemLis(OnItemLis onItemLis) {
            this.onItemLis = onItemLis;
        }
    }
    static abstract class BaseChildHolder extends RecyclerView.ViewHolder{
        int focusHei;
        public BaseChildHolder(View itemView) {
            super(itemView);
            itemView.setFocusable(true);
            focusHei = itemView.getResources().getDimensionPixelOffset(R.dimen.water_text_hei);
        }
        abstract void setText(String text);
        abstract ImageView getBackImageView();
        abstract void showFocus(boolean isFocus);
        public void set_textBlue(TextView textView){
            textView.getLayoutParams().height += 12;
            textView.requestLayout();
            textView.setBackgroundColor(itemView
                    .getResources().getColor(R.color.item_select_bg_color));
        }
        public void set_textGrey(TextView textView){
            textView.getLayoutParams().height = focusHei;
            textView.requestLayout();
            textView.setBackgroundColor(Color.parseColor("#99000000"));
        }
    }

    static class OneViewHolder extends BaseViewHolder {
        public static final int ResID = R.layout.layout_water_item;
        TextView textView;
        RecyclerView rv_rv_item;
        public OneViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_water_item_title);
            rv_rv_item = itemView.findViewById(R.id.rv_water_item);
        }

        @Override
        void setTitle(String title) {
            textView.setText(title);
            if (title.startsWith("- -")){
                textView.setVisibility(View.GONE);
            }else {
                textView.setVisibility(View.VISIBLE);
            }
        }

        @Override
        void setAdapter(BaseChildViewAdapter baseViewAdapter, final int DpRecId) {
            RecyclerView.LayoutManager layoutManager = rv_rv_item.getLayoutManager();
            if (layoutManager == null){
                layoutManager = new LinearLayoutManager(rv_rv_item.getContext()
                        ,LinearLayoutManager.HORIZONTAL,false);
//                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            }
            if (rv_rv_item.getItemDecorationCount() > 0){
                rv_rv_item.removeItemDecorationAt(0);
            }
            if (DpRecId != 0){
                rv_rv_item.addItemDecoration(new RecyclerView.ItemDecoration() {
                    @Override
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                        outRect.right = (int) view.getResources(). getDimension(DpRecId);
//                    super.getItemOffsets(outRect, view, parent, state);
                    }
                });
            }
            rv_rv_item.setLayoutManager(layoutManager);
            rv_rv_item.setAdapter(baseViewAdapter);
        }
    }
    static class OneViewChildAdapter extends BaseChildViewAdapter {
        public static final int DpResID = 0;
        @NonNull
        @Override
        public BaseChildHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            BaseChildHolder holder;
            View view = LayoutInflater.from(
                    viewGroup.getContext()).inflate(OneChildViewHolder.ResID, viewGroup,
                    false);
            holder = new OneChildViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull BaseChildHolder holder, int position) {
            super.onBindViewHolder(holder, position);
            holder.setText(list.get(position).name);
            holder.getBackImageView().setImageResource(R.mipmap.ic_launcher);
        }

        @Override
        public int getItemCount() {
            int size = list.size();
//            if (size > 1){
//                return 1;
//            }
            return size;
        }
    }

    static class OneChildViewHolder extends BaseChildHolder {
        public static final int ResID = R.layout.item_waterfall_one;

        TextView textview;
        ImageView iv_water_one_icon,iv_water_one;
        public OneChildViewHolder(View itemView) {
            super(itemView);
            textview = itemView.findViewById(R.id.tv_water_one);
            iv_water_one_icon = itemView.findViewById(R.id.iv_water_one_icon);
            iv_water_one = itemView.findViewById(R.id.iv_water_one);
            // 不显示文字
            textview.setVisibility(View.INVISIBLE);
        }

        @Override
        void setText(String text) {
            textview.setText(text);
        }

        @Override
        ImageView getBackImageView() {
            return iv_water_one;
        }
        @Override
        void showFocus(boolean isFocus) {
            textview.setSelected(isFocus);
            if (isFocus){
                textview.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            }else {
                textview.setEllipsize(TextUtils.TruncateAt.END);
            }
        }
    }
    static class TwoViewChildAdapter extends BaseChildViewAdapter {
        public static final int DpResID = R.dimen.dp_dec;
        @NonNull
        @Override
        public BaseChildHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            BaseChildHolder holder;
            View view = LayoutInflater.from(
                    viewGroup.getContext()).inflate(TwoChildViewHolder.ResID, viewGroup,
                    false);
            holder = new TwoChildViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull BaseChildHolder holder, int position) {
            super.onBindViewHolder(holder, position);
            holder.setText(list.get(position).name);
            holder.getBackImageView().setImageResource(R.mipmap.ic_launcher);
        }

        @Override
        public int getItemCount() {
            int size = list.size();
//            if (size > 2){
//                return 2;
//            }
            return size;
        }
    }

    static class TwoChildViewHolder extends BaseChildHolder {
        public static final int ResID = R.layout.item_waterfall_two;

        TextView textview;
        ImageView iv_water_one_icon,iv_water_one;
        public TwoChildViewHolder(View itemView) {
            super(itemView);
            textview = itemView.findViewById(R.id.tv_water_one);
            iv_water_one_icon = itemView.findViewById(R.id.iv_water_one);
            iv_water_one = itemView.findViewById(R.id.iv_water_one);
            set_textGrey(textview);
        }

        @Override
        void setText(String text) {
            textview.setText(text);
        }

        @Override
        ImageView getBackImageView() {
            return iv_water_one;
        }
        @Override
        void showFocus(boolean isFocus) {
            textview.setSelected(isFocus);
            if (isFocus){
                set_textBlue(textview);
            }else {
                set_textGrey(textview);
            }
            if (isFocus){
                textview.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            }else {
                textview.setEllipsize(TextUtils.TruncateAt.END);
            }
        }
    }

}
