package com.duxl.newbie;

import android.app.Activity;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * create by duxl 2019/3/22 0022
 * 新手引导管理
 */
public class NewbieGuideManager implements View.OnClickListener {

    private Activity mActivity;
    private FrameLayout mDecorView;
    private FrameLayout mFlNewbieGuide;
    private View[] mViews;
    private NewbieGuideView.Style mStyle = NewbieGuideView.Style.CIRCLE;
    private int[] mPaddding = new int[4];
    private boolean mClickMissing = false;
    private int mBgColor = 0;
    private List<AddView> mAddViews = new ArrayList<>();
    private OnMissingListener mOnMissingListener;

    /**
     * 构造函数
     * @param activity Activity
     * @param views 需要高亮的控件
     */
    public NewbieGuideManager(Activity activity,@NonNull View...views) {
        this(activity);
        mViews = views;
        if(mViews == null) {
            throw new IllegalArgumentException("views at least one");
        }
    }

    /**
     * 构造函数
     * @param layoutResId 自定义引导布局
     * @param activity Activity
     */
    public NewbieGuideManager(@NonNull @LayoutRes int layoutResId, Activity activity) {
        this(activity);
        View view = LayoutInflater.from(activity).inflate(layoutResId, null);
        mFlNewbieGuide.addView(view);
    }

    /**
     * 构造函数
     * @param customView 自定义引导视图
     * @param activity Activity
     */
    public NewbieGuideManager(@NonNull View customView, Activity activity) {
        this(activity);
        mFlNewbieGuide.addView(customView);
    }


    private NewbieGuideManager(Activity activity) {
        this.mActivity = activity;
        mDecorView = (FrameLayout) mActivity.getWindow().getDecorView();
        mFlNewbieGuide = new FrameLayout(activity);
        mFlNewbieGuide.setOnClickListener(this);
        mDecorView.addView(mFlNewbieGuide);
    }

    /**
     * 设置高亮取消监听
     * @param listener
     */
    public void setOnMissingListener(OnMissingListener listener) {
        this.mOnMissingListener = listener;
    }

    /**
     * 设置高亮样式
     * @param style
     * @return
     */
    public NewbieGuideManager style(NewbieGuideView.Style style) {
        mStyle = style;
        return this;
    }

    /**
     * 设置高亮区域上下左右间距
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @return
     */
    public NewbieGuideManager paddint(int left, int top, int right, int bottom) {
        mPaddding[0] = left;
        mPaddding[1] = top;
        mPaddding[2] = right;
        mPaddding[3] = bottom;
        return this;
    }


    /**
     * 点击自动消失
     * @param hide
     * @return
     */
    public NewbieGuideManager clickAutoMissing(boolean hide) {
        mClickMissing = hide;
        return this;
    }

    /**
     * 设置背景颜色
     * @param color
     * @return
     */
    public NewbieGuideManager bgColor(@ColorInt int color) {
        mBgColor = color;
        return this;
    }

    /**
     * 添加视图
     * @param layoutId 布局文件
     * @param position 将视图添加到相对于高亮部分的那个位置
     * @param xOffset 添加视图在x方向的偏移量
     * @param yOffset 添加视图在y方向的偏移量
     * @return
     */
    public NewbieGuideManager addView(@LayoutRes int layoutId, Position position, int xOffset, int yOffset) {
        View view = LayoutInflater.from(mActivity).inflate(layoutId, null);
        return addView(view, position, xOffset, yOffset);
    }

    /**
     * 添加视图
     * @param view 布局
     * @param position 将视图添加到相对于高亮部分的那个位置
     * @param xOffset 添加视图在x方向的偏移量
     * @param yOffset 添加视图在y方向的偏移量
     * @return
     */
    public NewbieGuideManager addView(View view, Position position, int xOffset, int yOffset) {
        mAddViews.add(new AddView(view, position, xOffset, yOffset));
        return this;
    }

    /**
     *  显示
     * @return
     */
    public NewbieGuideManager show() {
        mDecorView.post(new Runnable() {
            @Override
            public void run() {
                NewbieGuideView guideView = new NewbieGuideView(mActivity);
                guideView.setShowyViews(mViews);
                guideView.setStyle(mStyle);
                guideView.setPadding(mPaddding[0], mPaddding[1], mPaddding[2], mPaddding[3]);
                mFlNewbieGuide.addView(guideView);
                if(mBgColor != 0) {
                    guideView.setBgColor(mBgColor);
                }

                addViews(guideView);
            }
        });
        return this;
    }

    private void addViews(NewbieGuideView guideView) {
        for (final AddView addView : mAddViews) {
            //制定测量规则 参数表示size + mode
            int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            //调用measure方法之后就可以获取宽高
            addView.view.measure(width, height);

            int viewWidth = addView.view.getMeasuredWidth();
            int viewHeight = addView.view.getMeasuredHeight();
            Rect showyRect = guideView.getShowyRect();

            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            if(addView.position == Position.Left) {
                layoutParams.leftMargin = showyRect.left - viewWidth;
                layoutParams.topMargin = showyRect.top;
            } else if(addView.position == Position.Top) {
                layoutParams.topMargin = showyRect.top - viewHeight;
                layoutParams.leftMargin = showyRect.left;
            } else if(addView.position == Position.Right) {
                layoutParams.leftMargin = showyRect.right;
                layoutParams.topMargin = showyRect.top;
            } else if(addView.position == Position.Bottom) {
                layoutParams.topMargin = showyRect.bottom;
                layoutParams.leftMargin = showyRect.left;
            }
            layoutParams.leftMargin += addView.xOffset;
            layoutParams.topMargin += addView.yOffset;
            mFlNewbieGuide.addView(addView.view, layoutParams);
        }
    }

    @Override
    public void onClick(View view) {
        if(mClickMissing) {
            missing();
        }
    }

    /**
     * 消失
     */
    public void missing() {
        if(mFlNewbieGuide != null) {
            mDecorView.removeView(mFlNewbieGuide);
            mFlNewbieGuide = null;

            if(mOnMissingListener != null) {
                mOnMissingListener.onMissing();
            }
        }
    }

    private class AddView {
        private View view;
        private Position position;
        private int xOffset;
        private int yOffset;

        public AddView(View view, Position position, int xOffset, int yOffset) {
            this.view = view;
            this.position = position;
            this.xOffset = xOffset;
            this.yOffset = yOffset;
        }
    }

    public enum Position {
        Left, Top, Right, Bottom
    }

    public interface OnMissingListener {
        void onMissing();
    }
}
