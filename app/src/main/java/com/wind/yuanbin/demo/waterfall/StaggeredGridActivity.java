package com.wind.yuanbin.demo.waterfall;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wind.yuanbin.demo.R;

import java.util.List;

public class StaggeredGridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staggered_grid);


        rv_staggered = findViewById(R.id.rv_staggered);


        rv_staggered.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        SAdapter adapter = new SAdapter();
        adapter.datas = WaterFallAdapter2.getTestData();

        rv_staggered.setAdapter(adapter);



        //设置item之间的间隔
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        rv_staggered.addItemDecoration(decoration);
    }
    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpacesItemDecoration(int space) {
            this.space=space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left=space;
            outRect.right=space;
            outRect.bottom=space;
            if(parent.getChildAdapterPosition(view)==0){
                outRect.top=space;
            }
        }
    }
    class SAdapter extends RecyclerView.Adapter<SAdapter.SViewHolder>{
        List<WaterFallAdapter2.WaterData> datas;
        @NonNull
        @Override
        public SViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(
                    viewGroup.getContext()).inflate(SViewHolder.ResID, viewGroup,
                    false);
            SViewHolder holder = new SViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull SViewHolder sViewHolder, int i) {
            sViewHolder.textview.setText(datas.get(i).title);

            int hei = 200;
            if (i % 4 == 2 || i % 4 == 3){
                // 0 1      2 3   4 5    6 7      8 9   10 11
                hei = 100;
            }
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) sViewHolder.itemView.getLayoutParams();
            layoutParams.height = hei;
//            sViewHolder.itemView.setBackgroundColor(Color.BLUE);
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        class SViewHolder extends RecyclerView.ViewHolder{
            public static final int ResID = R.layout.item_waterfall_one;
            TextView textview;
            ImageView iv_water_one_icon,iv_water_one;
            public SViewHolder(@NonNull View itemView) {
                super(itemView);
                textview = itemView.findViewById(R.id.tv_water_one);
                iv_water_one_icon = itemView.findViewById(R.id.iv_water_one_icon);
                iv_water_one = itemView.findViewById(R.id.iv_water_one);
            }
        }
    }

    RecyclerView rv_staggered;

    public static void toSA(Context context){
        Intent intent = new Intent(context,StaggeredGridActivity.class);
        context.startActivity(intent);
    }
}
