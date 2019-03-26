package com.duxl.newbie.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.duxl.newbie.NewbieGuideManager;
import com.duxl.newbie.NewbieGuideView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn1;
    private Button btn2;
    private Button btn3;

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
    }

    @Override
    public void onClick(View view) {
        if(view == btn1) {
            show(btn1, NewbieGuideView.Style.RECT);

        } else if(view == btn2) {
            show(btn2, NewbieGuideView.Style.CIRCLE);

        } else if(view == btn3) {
            show(btn3, NewbieGuideView.Style.OVA);
        }
    }

    private void show(Button btn, NewbieGuideView.Style style) {
        final NewbieGuideManager newbieGuideManager = new NewbieGuideManager(this, btn);
        newbieGuideManager.paddint(20, 20, 20, 20);
        newbieGuideManager.style(style);

        View custom = getLayoutInflater().inflate(R.layout.newbie, null);
        custom.findViewById(R.id.btnClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newbieGuideManager.missing();
            }
        });
        newbieGuideManager.addView(custom, NewbieGuideManager.Position.Bottom, 0, 0);
        newbieGuideManager.show();
    }
}
