package com.wind.yuanbin.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wind.yuanbin.demo.utils.L;

public class ChannelActivity extends AppCompatActivity {
    MyAdapter adapter;
    RecyclerView rv_c;
    LinearLayoutManager linearLayoutManager;

    Button btn_up,btn_down;

    String [] s ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        rv_c = findViewById(R.id.rv_c);
        linearLayoutManager = new LinearLayoutManager(this);
        rv_c.setLayoutManager(linearLayoutManager);
        btn_up = findViewById(R.id.btn_up);
        btn_down = findViewById(R.id.btn_down);

        s = new String[100];
        for (int i = 0;i<100 ; i++){
            s[i] = "CCTV----------------"+i;
        }

        adapter = new MyAdapter(s);
        setLis();
        rv_c.setAdapter(adapter);
        rv_c.scrollToPosition(position);
        rv_c.postDelayed(new Runnable() {
            @Override
            public void run() {
                View view = rv_c.getChildAt(5);
                view.requestFocus();
            }
        },1000);

    }

    private void setLis() {
        adapter.setOnItemFocusChangeListener(new MyAdapter.OnItemFocusChangeListener() {
            @Override
            public void OnFocusChangeListener(View v, boolean hasFocus, int position) {
                L.o("tag onfocus id:" + v.getId() + "xxxxxxxxxxxxxx" + position + " has " + hasFocus);
                if (hasFocus){
                    v.setBackgroundResource(R.drawable.shadow);
//                    L.o("v.getY(): " + v.getY());
                }else {
                    v.setBackgroundResource(0);
                }
            }
        });

        adapter.setOnItemKeyDownListener(new MyAdapter.OnItemKeyDownListener() {
            @Override
            public boolean OnItemKeyDown(View v, int keyCode, KeyEvent event, boolean isLast) {

                switch (keyCode){
                    case KeyEvent.KEYCODE_DPAD_DOWN:
                        if (event.getAction() == KeyEvent.ACTION_DOWN){
                            handle_down();
                            btn_down.setPressed(true);
                            return true;
                        }
                        btn_down.setPressed(false);
                        break;

                    case KeyEvent.KEYCODE_DPAD_UP:
                        if (event.getAction() == KeyEvent.ACTION_DOWN){
                            handle_up();
                            btn_up.setPressed(true);
                            return true;
                        }
                        btn_up.setPressed(false);
                        break;
                }
                return false;
            }
        });
    }

    private void handle_up() {
        if (position > 0){
            position--;
            move(position);
            View view = rv_c.getChildAt(rv_c.getChildCount() /2 - 1);
            view.requestFocus();
        }
    }

    private void handle_down() {
        if (position < adapter.getItemCount() - 1){
            position++;
            move(position);
            View view = rv_c.getChildAt(rv_c.getChildCount() /2);
            view.requestFocus();
        }
    }

    private void move(int position) {
        L.o("position: " + position);
        moveToPosition(position);
    }
    private void moveToPosition(int n) {
        int firstItem = linearLayoutManager.findFirstVisibleItemPosition();
        int lastItem = linearLayoutManager.findLastVisibleItemPosition();
        if (n <= firstItem ){
            rv_c.scrollToPosition(n);
        }else if ( n <= lastItem ){
            int top = rv_c.getChildAt(n - firstItem).getTop();
            rv_c.scrollBy(0, top);
        }else{
            rv_c.scrollToPosition(n);
        }
    }
    int position = 500000;

    static class MyAdapter extends RecyclerView.Adapter<ChannelActivity.MyAdapter.MyViewHolder>{
        public interface OnItemFocusChangeListener{
            void OnFocusChangeListener(View v, boolean hasFocus, int position);
        }
        private ChannelActivity.MyAdapter.OnItemFocusChangeListener onItemFocusChangeListener;
        public interface OnItemKeyDownListener{
            boolean OnItemKeyDown(View v, int keyCode, KeyEvent event, boolean isLast);
        }
        private ChannelActivity.MyAdapter.OnItemKeyDownListener onItemKeyDownListener;

        public void setOnItemFocusChangeListener(ChannelActivity.MyAdapter.OnItemFocusChangeListener onItemFocusChangeListener) {
            this.onItemFocusChangeListener = onItemFocusChangeListener;
        }

        public void setOnItemKeyDownListener(ChannelActivity.MyAdapter.OnItemKeyDownListener onItemKeyDownListener) {
            this.onItemKeyDownListener = onItemKeyDownListener;
        }

        private String [] s ;
        public MyAdapter(String [] s){
            this.s = s;
        }

        @Override
        public ChannelActivity.MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ChannelActivity.MyAdapter.MyViewHolder(LayoutInflater.from(
                    parent.getContext()).inflate(R.layout.layout_tv_item, parent,
                    false));
        }

        @Override
        public void onBindViewHolder(ChannelActivity.MyAdapter.MyViewHolder holder, final int position) {
            //0---299
            //0---2999
            holder.tv.setText(s[position % 100]);
            holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (onItemFocusChangeListener != null)
                        onItemFocusChangeListener.OnFocusChangeListener(v,hasFocus,position);
                }
            });
            holder.itemView.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (onItemKeyDownListener != null){
                        boolean isLast = position == s.length - 1;
                        return onItemKeyDownListener.OnItemKeyDown(v,keyCode,event,isLast);
                    }
                    return false;
                }
            });
        }

        @Override
        public int getItemCount() {
//            return 0;
            return Integer.MAX_VALUE;
//            return s.length;
        }
        class MyViewHolder extends RecyclerView.ViewHolder{
            TextView tv;
            public MyViewHolder(View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.tv_item);
                itemView.setFocusable(true);
            }
        }
    }
}
