package com.duxl.newbie.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.duxl.newbie.NewbieGuideManager;
import com.duxl.newbie.NewbieGuideView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;

    private NewbieGuideManager mNewbieGuideManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(this);

        btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(this);

        btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(this);

        btn4 = findViewById(R.id.btn4);
        btn4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == btn1) {
            show(NewbieGuideView.Style.RECT, Color.parseColor("#B2222222"), btn1, btn2);

        } else if(view == btn2) {
            show(NewbieGuideView.Style.CIRCLE, Color.parseColor("#B2222222"), btn2);

        } else if(view == btn3) {
            show(NewbieGuideView.Style.OVA, Color.parseColor("#B2FF0000"), btn3);

        } else if(view == btn4) {
            showCustom();
        }
    }

    private void show(NewbieGuideView.Style style, int bgColor, View... views) {
        mNewbieGuideManager = new NewbieGuideManager(this, views);
        mNewbieGuideManager.padding(20, 20, 20, 20);
        mNewbieGuideManager.style(style);
        mNewbieGuideManager.bgColor(bgColor);

        View custom = getLayoutInflater().inflate(R.layout.newbie, null);
        custom.findViewById(R.id.btnClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNewbieGuideManager.missing();
            }
        });
        mNewbieGuideManager.addView(custom, NewbieGuideManager.Position.Bottom, 0, 0);
        mNewbieGuideManager.show();
    }

    private void showCustom() {
        mNewbieGuideManager = new NewbieGuideManager(R.layout.custom_newbie, this);
        mNewbieGuideManager.clickAutoMissing(true);
        mNewbieGuideManager.setOnMissingListener(new NewbieGuideManager.OnMissingListener() {
            @Override
            public void onMissing() {
                Toast.makeText(MainActivity.this, "Hello Newbie", Toast.LENGTH_SHORT).show();
            }
        });
        mNewbieGuideManager.show();
    }

    @Override
    public void onBackPressed() {
        if(mNewbieGuideManager != null && mNewbieGuideManager.isShowing()) {
            return;
        }
        super.onBackPressed();
    }
}
