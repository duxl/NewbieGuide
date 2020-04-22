package com.duxl.newbie.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.duxl.newbie.NewbieGuideManager;
import com.duxl.newbie.NewbieGuideView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnStyle;
    private TextView tvStyle;
    private NewbieGuideView.Style mStyle = NewbieGuideView.Style.RECT;

    private Button btnBgColor;
    private TextView tvBgColor;
    private int mBgColor = Color.parseColor("#7FFF0000");

    private Button btnShowyClickEnable;
    private TextView tvShowyClickEnable;
    private boolean mShowyClickEnable = true;

    private Button btnAutoMiss;
    private TextView tvAutoMiss;
    private boolean mAutoMiss = false;

    private Button btnPosition;
    private TextView tvPosition;
    private NewbieGuideManager.Position mPosition = NewbieGuideManager.Position.ToLeft;
    private NewbieGuideManager.Positions mPositions;

    private Button btnShow;
    private Button btnShowCustom;
    private Button btnAnchor;

    private NewbieGuideManager mNewbieGuideManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStyle = findViewById(R.id.btn_style);
        tvStyle = findViewById(R.id.tv_style);
        btnStyle.setOnClickListener(v -> {
            String[] items = {"圆圈", "矩形", "椭圆", "无高亮"};
            NewbieGuideView.Style[] values = {
                    NewbieGuideView.Style.CIRCLE,
                    NewbieGuideView.Style.RECT,
                    NewbieGuideView.Style.OVA,
                    NewbieGuideView.Style.NONE
            };
            showDialog(btnStyle.getText(), items, values, tvStyle, 0);
        });

        btnBgColor = findViewById(R.id.btn_bg_color);
        tvBgColor = findViewById(R.id.tv_bg_color);
        btnBgColor.setOnClickListener(v -> {
            String[] items = {"红色", "绿色", "蓝色", "黑色"};
            Integer[] values = {
                    Color.parseColor("#7FFF0000"),
                    Color.parseColor("#7F00FF00"),
                    Color.parseColor("#7F0000FF"),
                    Color.parseColor("#7F000000")
            };
            showDialog(btnStyle.getText(), items, values, tvBgColor, 1);
        });

        btnShowyClickEnable = findViewById(R.id.btn_showy_click_enable);
        tvShowyClickEnable = findViewById(R.id.tv_showy_click_enable);
        btnShowyClickEnable.setOnClickListener(v -> {
            String[] items = {"是", "否"};
            Boolean[] values = {
                    true, false
            };
            showDialog(btnShowyClickEnable.getText(), items, values, tvShowyClickEnable, 2);
        });

        btnAutoMiss = findViewById(R.id.btn_auto_miss);
        tvAutoMiss = findViewById(R.id.tv_auto_miss);
        btnAutoMiss.setOnClickListener(v -> {
            String[] items = {"是", "否"};
            Boolean[] values = {
                    true, false
            };
            showDialog(btnAutoMiss.getText(), items, values, tvAutoMiss, 3);
        });

        btnPosition = findViewById(R.id.btn_position);
        tvPosition = findViewById(R.id.tv_position);
        btnPosition.setOnClickListener(v -> {
            String[] items = {
                    "ToLeft", "ToTop", "ToRight", "ToBottom",
                    "AlignLeft", "AlignTop", "AlignRight", "AlignBottom",
                    "ToLeft - ToTop", "ToLeft - ToBottom", "ToLeft - AlignTop", "ToLeft - AlignBottom",
                    "ToRight - ToTop", "ToRight - ToBottom", "ToRight - AlignTop", "ToRight - AlignBottom",
                    "AlignLeft - ToTop", "AlignLeft - ToBottom", "AlignLeft - AlignTop", "AlignLeft - AlignBottom",
                    "AlignRight - ToTop", "AlignRight - ToBottom", "AlignRight - AlignTop", "AlignRight - AlignBottom",
            };
            Object[] values = {
                    NewbieGuideManager.Position.ToLeft,
                    NewbieGuideManager.Position.ToTop,
                    NewbieGuideManager.Position.ToRight,
                    NewbieGuideManager.Position.ToBottom,

                    NewbieGuideManager.Position.AlignLeft,
                    NewbieGuideManager.Position.AlignTop,
                    NewbieGuideManager.Position.AlignRight,
                    NewbieGuideManager.Position.AlignBottom,

                    NewbieGuideManager.Positions.build(NewbieGuideManager.Position.ToLeft, NewbieGuideManager.Position.ToTop),
                    NewbieGuideManager.Positions.build(NewbieGuideManager.Position.ToLeft, NewbieGuideManager.Position.ToBottom),
                    NewbieGuideManager.Positions.build(NewbieGuideManager.Position.ToLeft, NewbieGuideManager.Position.AlignTop),
                    NewbieGuideManager.Positions.build(NewbieGuideManager.Position.ToLeft, NewbieGuideManager.Position.AlignBottom),

                    NewbieGuideManager.Positions.build(NewbieGuideManager.Position.ToRight, NewbieGuideManager.Position.ToTop),
                    NewbieGuideManager.Positions.build(NewbieGuideManager.Position.ToRight, NewbieGuideManager.Position.ToBottom),
                    NewbieGuideManager.Positions.build(NewbieGuideManager.Position.ToRight, NewbieGuideManager.Position.AlignTop),
                    NewbieGuideManager.Positions.build(NewbieGuideManager.Position.ToRight, NewbieGuideManager.Position.AlignBottom),

                    NewbieGuideManager.Positions.build(NewbieGuideManager.Position.AlignLeft, NewbieGuideManager.Position.ToTop),
                    NewbieGuideManager.Positions.build(NewbieGuideManager.Position.AlignLeft, NewbieGuideManager.Position.ToBottom),
                    NewbieGuideManager.Positions.build(NewbieGuideManager.Position.AlignLeft, NewbieGuideManager.Position.AlignTop),
                    NewbieGuideManager.Positions.build(NewbieGuideManager.Position.AlignLeft, NewbieGuideManager.Position.AlignBottom),

                    NewbieGuideManager.Positions.build(NewbieGuideManager.Position.AlignRight, NewbieGuideManager.Position.ToTop),
                    NewbieGuideManager.Positions.build(NewbieGuideManager.Position.AlignRight, NewbieGuideManager.Position.ToBottom),
                    NewbieGuideManager.Positions.build(NewbieGuideManager.Position.AlignRight, NewbieGuideManager.Position.AlignTop),
                    NewbieGuideManager.Positions.build(NewbieGuideManager.Position.AlignRight, NewbieGuideManager.Position.AlignBottom),


            };
            showDialog(btnPosition.getText(), items, values, tvPosition, 4);
        });

        btnShow = findViewById(R.id.btn_show);
        btnShow.setOnClickListener(this);

        btnShowCustom = findViewById(R.id.btn_show_custom);
        btnShowCustom.setOnClickListener(this);

        btnAnchor = findViewById(R.id.btn_anchor);
        btnAnchor.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnShow) {
            show();
        } else if (view == btnShowCustom) {
            showCustom();
        }
        if (view == btnAnchor) {
            Toast.makeText(this, "我是一个按钮", Toast.LENGTH_SHORT).show();
        }
    }

    private void show() {
        mNewbieGuideManager = new NewbieGuideManager(this, btnAnchor);
        //mNewbieGuideManager.padding(20, 20, 20, 20);
        mNewbieGuideManager.style(mStyle);
        mNewbieGuideManager.bgColor(mBgColor);
        mNewbieGuideManager.setShowyClickEnable(mShowyClickEnable);
        mNewbieGuideManager.setClickAutoMissing(mAutoMiss);

        View custom = getLayoutInflater().inflate(R.layout.newbie, null);
        custom.findViewById(R.id.btnClose).setOnClickListener(view -> mNewbieGuideManager.missing());
        if (mPosition != null) {
            mNewbieGuideManager.addView(custom, mPosition, 0, 0);
        } else {
            mNewbieGuideManager.addView(custom, mPositions, 0, 0);
        }
        mNewbieGuideManager.show();
    }

    private void showCustom() {
        mNewbieGuideManager = new NewbieGuideManager(R.layout.custom_newbie, this);
        mNewbieGuideManager.setClickAutoMissing(true);
        mNewbieGuideManager.setOnMissingListener(() -> Toast.makeText(MainActivity.this, "Hello Newbie", Toast.LENGTH_SHORT).show());
        mNewbieGuideManager.show();
    }

    @Override
    public void onBackPressed() {
        if (mNewbieGuideManager != null && mNewbieGuideManager.isShowing()) {
            return;
        }
        super.onBackPressed();
    }

    /**
     * @param title
     * @param items
     * @param values
     * @param tvValue
     * @param type    类型：0高亮样式、1蒙层背景、2高亮区域是否可点击、3点击蒙层自动消失、4添加view位置
     */
    private void showDialog(CharSequence title, String[] items, Object[] values, TextView tvValue, int type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setItems(items, (dialog, which) -> {
            tvValue.setText(items[which]);

            if (type == 0) {
                mStyle = (NewbieGuideView.Style) values[which];
            } else if (type == 1) {
                mBgColor = (Integer) values[which];
            } else if (type == 2) {
                mShowyClickEnable = (Boolean) values[which];
            } else if (type == 3) {
                mAutoMiss = (Boolean) values[which];
            } else if (type == 4) {
                if (values[which] instanceof NewbieGuideManager.Position) {
                    mPosition = (NewbieGuideManager.Position) values[which];
                    mPositions = null;
                } else if (values[which] instanceof NewbieGuideManager.Positions) {
                    mPositions = (NewbieGuideManager.Positions) values[which];
                    mPosition = null;
                }
            }

        });
        builder.create().show();
    }
}
