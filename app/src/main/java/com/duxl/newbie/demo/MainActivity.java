package com.duxl.newbie.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.duxl.newbie.NewbieGuideManager;
import com.duxl.newbie.NewbieGuideView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        TextView tvMsg = findViewById(R.id.tvMsg);
        tvMsg.setText("新手引导");

        final NewbieGuideManager newbieGuideManager = new NewbieGuideManager(this, tvMsg);
        newbieGuideManager.paddint(20, 20, 20, 20);
        newbieGuideManager.style(NewbieGuideView.Style.CIRCLE);

        View custom = getLayoutInflater().inflate(R.layout.newbie, null);
        custom.findViewById(R.id.btnClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newbieGuideManager.missing();
            }
        });
        newbieGuideManager.addView(custom, NewbieGuideManager.Position.Bottom, 100, 10);
        newbieGuideManager.show();
    }
}
