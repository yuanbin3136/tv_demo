package com.wind.yuanbin.demo.waterfall;

import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.wind.yuanbin.demo.App;
import com.wind.yuanbin.demo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WaterFallAdapter2 extends RecyclerView.Adapter<WaterFallAdapter2.WaterViewHolder>{

    public static List<WaterData> getTestData(){
        Random random = new Random();
        List<WaterData> datas = new ArrayList<>();
        for (int i = 0;i<100;i++){
//            List<WaterFallAdapter.DataItem> list;
            WaterData waterData = new WaterData();
            waterData.title = i + "x";
            int x = new Random().nextInt(2);
            if (x == 0){
                waterData.type = Module.TwoModule.type;
            }else {
                waterData.type = Module.ThreeModule.type;
            }

            int randomx = random.nextInt(10) + 2;
            waterData.itemList = new ArrayList<>();
            DataItem item;
            for (int k = 0;k < randomx;k++){
                item = new DataItem();
                item.name = "k" + k;
                waterData.itemList.add(item);
            }
            datas.add(waterData);
        }
        return datas;
    }
    List<WaterData> datas;

    enum Module{
        TwoModule(2,4, getDimen(R.dimen.water_Decoration2), getDimen(R.dimen.water_hei2))
        ,ThreeModule(3,6,getDimen(R.dimen.water_Decoration3), getDimen(R.dimen.water_hei3));
        // type,id,span,
        static int getDimen(int id){
            return App.getContext().getResources()
                    .getDimensionPixelOffset(id);
        }
        int type;// 类型
        int span;// 一行几个
        int resid;// 行间间隔dp
        int hei;// 一行高度

        private String typex;
        private final String value = "";//final其实没有用

        Module(int type,int span,int resId,int hei){
            this.type = type;
            this.span = span;
            this.resid = resId;
            this.hei = hei;
        }
        //根据type获取枚举
        public static Module getEnumByKey(int type){
            for(Module temp:Module.values()){
                if(temp.type == type){
                    return temp;
                }
            }
            return null;
        }
    }
    static class WaterData{
        String title;
        List<DataItem> itemList;
        int type;
    }
    static class DataItem{
        String name;
        String x;
    }

    public void setDatas(List<WaterData> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public WaterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(
                viewGroup.getContext()).inflate(OneViewHolder.ResID, viewGroup,
                false);
        WaterViewHolder holder = new OneViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull WaterViewHolder waterViewHolder, int i) {
        waterViewHolder.setTitle(datas.get(i).title);
        int type = getItemViewType(i);
        WaterViewAdapter adapter = waterViewHolder.getWaterViewAdapter();
        if (adapter == null){
            adapter = new WaterViewAdapter(type,datas.get(i).itemList);
        }else {
            adapter.init(type,datas.get(i).itemList);
        }
        adapter.setOnItemLis(new WaterViewAdapter.OnItemLis() {
            @Override
            public void onFocus(View v, boolean hasFocus, int position) {
                if (onItemAllLis != null){
                    onItemAllLis.onFocus(v,hasFocus,i,position);
                }
            }

            @Override
            public void onKey(View v, int keyCode, KeyEvent event, int position) {

            }

            @Override
            public void onClick(View v, int position) {

            }
        });
        waterViewHolder.setAdapter(adapter);
    }

    @Override
    public int getItemViewType(int position) {

//        return super.getItemViewType(position);
//        int x = new Random().nextInt(2);
//        return position % 3;
        return datas.get(position).type;
    }

    @Override
    public int getItemCount() {
        return datas.size();
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
    static class OneViewHolder extends WaterViewHolder{
        public static final int ResID = R.layout.layout_item_rv;
        TextView textView;
        RecyclerView rv_rv_item;
        public OneViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_rv_item);
            rv_rv_item = itemView.findViewById(R.id.rv_rv_item);
        }

        @Override
        void setTitle(String title) {
            textView.setText(title);
        }

        @Override
        void setAdapter(WaterViewAdapter baseViewAdapter) {
            int type = baseViewAdapter.type;
            Module module = Module.getEnumByKey(type);
            final int span = module.span;
            GridLayoutManager layoutManager = (GridLayoutManager) rv_rv_item.getLayoutManager();
            if(layoutManager == null){
                layoutManager = new GridLayoutManager(rv_rv_item.getContext(),span);
            }else {
                layoutManager.setSpanCount(span);
            }
            if(rv_rv_item.getItemDecorationCount() == 0){
                rv_rv_item.addItemDecoration(new RecyclerView.ItemDecoration() {
                    @Override
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                        int i = parent.getChildAdapterPosition(view);
                        int top;
                        if (i >= span){// 大于一行的才增加上边距
                            top = module.resid;
                            outRect.top = top;
                        }
                    }
                });
            }
            rv_rv_item.setLayoutManager(layoutManager);
            rv_rv_item.setAdapter(baseViewAdapter);
        }
    }

    static class WaterViewAdapter extends RecyclerView.Adapter<WaterItemViewHolder>{
        List<DataItem> list;
        int type;
        public WaterViewAdapter(int type,List<DataItem> list){
            this.type = type;
            this.list = list;
        }
        public void init(int type,List<DataItem> list){
            this.type = type;
            this.list = list;
        }

        @NonNull
        @Override
        public WaterItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(
                    viewGroup.getContext()).inflate(R.layout.layout_item_water, viewGroup,
                    false);
//            View view = LayoutInflater.from(viewGroup.getContext())
//                    .inflate(R.layout.layout_water_item,null);
            // 以上写法高度异常
            WaterItemViewHolder holder = new WaterItemViewHolder(view);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

            Module module = Module.getEnumByKey(type);
            layoutParams.height = module.hei;
            holder.imageView.setImageResource(R.mipmap.ic_launcher);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull WaterItemViewHolder holder, final int position) {
            holder.textView.setText(list.get(position).name);
            holder.itemView.setOnClickListener(v -> {
                if (onItemLis != null){
                    onItemLis.onClick(v,position);
                }
            });
            holder.itemView.setOnKeyListener((v, keyCode, event) -> false);
            holder.itemView.setOnFocusChangeListener((v, hasFocus) -> {
                if (onItemLis != null){
                    onItemLis.onFocus(v,hasFocus,position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        interface OnItemLis{
            void onFocus(View v, boolean hasFocus,int position);
            void onKey(View v, int keyCode, KeyEvent event,int position);
            void onClick(View v,int position);
        }
        OnItemLis onItemLis;

        public void setOnItemLis(OnItemLis onItemLis) {
            this.onItemLis = onItemLis;
        }
    }

    static abstract class WaterViewHolder extends RecyclerView.ViewHolder{

        public WaterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        abstract void setTitle(String title);
        abstract void setAdapter(WaterViewAdapter waterViewAdapter);
        WaterViewAdapter waterViewAdapter;

        public WaterViewAdapter getWaterViewAdapter() {
            return waterViewAdapter;
        }
    }

    static class WaterItemViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        ImageView imageView;
        public WaterItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_item_water);
            imageView = itemView.findViewById(R.id.iv_item_water);
            textView.setBackgroundColor(Color.parseColor("#99000000"));
        }
    }
}
