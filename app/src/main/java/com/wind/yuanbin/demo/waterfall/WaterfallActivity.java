package com.wind.yuanbin.demo.waterfall;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;

import com.wind.yuanbin.demo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO 瀑布流的Demo
 *
 *
 */
public class WaterfallActivity extends AppCompatActivity {

    RecyclerView_MoreScroll rv_water;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waterfall);

        rv_water = findViewById(R.id.rv_water);


        rv_water.setLayoutManager(new LinearLayoutManager(this));

        waterFallAdapter = new WaterFallAdapter();

        Map<Integer, WaterFallAdapter.Items> mapContents = new HashMap<>();
        WaterFallAdapter.Items items;

        for (int i = 0; i < 10;i++){
            items = new WaterFallAdapter.Items();
            items.name = "111";
            items.itemList = new ArrayList<>();
            if (i % 2 == 0){
                items.type = WaterFallAdapter.WATERFALLTYPE_ONE;
            }else {
                items.type = WaterFallAdapter.WATERFALLTYPE_TWO;
            }
            for (int k = 0;k < 10;k++){
                WaterFallAdapter.Item item = new WaterFallAdapter.Item();
                item.name = i + "k " + k;
                items.itemList.add(item);
            }
            mapContents.put(i,items);
        }

        waterFallAdapter.setMapContents(mapContents);
        waterFallAdapter.setOnItemLis(new WaterFallAdapter.OnItemAllLis() {
            @Override
            public void onFocus(View v, boolean hasFocus, int menuPosition, int position) {
                if (hasFocus){
                    v.animate().scaleX(1.2f);
                    v.animate().scaleY(1.2f);
                }else {
                    v.animate().scaleX(1.0f);
                    v.animate().scaleY(1.0f);
                }
            }

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event, int menuPosition, int position) {
                return false;
            }

            @Override
            public void onClick(View v, int menuPosition, int position) {

            }
        });
//        rv_water.setAdapter(waterFallAdapter);
        WaterFallAdapter2 adapter2 = new WaterFallAdapter2();
        adapter2.datas = WaterFallAdapter2.getTestData();
        adapter2.setOnItemLis(new WaterFallAdapter2.OnItemAllLis() {
            @Override
            public void onFocus(View v, boolean hasFocus, int menuPosition, int position) {
                if (hasFocus){
                    v.animate().scaleX(1.2f);
                    v.animate().scaleY(1.2f);
                }else {
                    v.animate().scaleX(1.0f);
                    v.animate().scaleY(1.0f);
                }
            }

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event, int menuPosition, int position) {
                return false;
            }

            @Override
            public void onClick(View v, int menuPosition, int position) {

            }
        });
        rv_water.setAdapter(adapter2);
    }
    WaterFallAdapter waterFallAdapter;


    public static void toWaterfall(Context context){
        Intent intent = new Intent(context,WaterfallActivity.class);
        context.startActivity(intent);
    }
}
