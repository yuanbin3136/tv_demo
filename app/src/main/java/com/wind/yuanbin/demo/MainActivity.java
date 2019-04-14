package com.wind.yuanbin.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wind.yuanbin.demo.waterfall.WaterfallActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textView;
    Button button,button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        button = findViewById(R.id.button);
        button1 = findViewById(R.id.button1);

        button.setOnClickListener(this);
        button.post(new Runnable() {
            @Override
            public void run() {
                int wid0 = button.getWidth();
                int wid1 = textView.getWidth();
                int wid2 = button1.getWidth();

                button.setText("match  " + wid0);
                textView.setText("200dp:" + wid1);
                button1.setText("100dp: " + wid2);

                // 1280*720  3/4
                // 1920*1080 1/2
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                WaterfallActivity.toWaterfall(this);
                break;
        }
    }
}
