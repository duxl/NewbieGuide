package com.duxl.newbie;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * create by duxl 2019/3/22 0022
 * 新手蒙层引导管理
 */
public class NewbieGuideManager {

    private Activity mActivity;
    private FrameLayout mDecorView;
    private FrameLayout mFlContainer;
    private View mCustomView;
    private View[] mViews;
    private NewbieGuideView.Style mStyle = NewbieGuideView.Style.NONE;
    private int mRoundRectRadius;
    private int[] mPaddding = new int[4];
    private boolean mClickMissing = false;
    private int mBgColor = 0;
    private List<AddView> mAddViews = new ArrayList<>();
    private OnMissingListener mOnMissingListener;
    private View.OnClickListener mOnShowyClickListener;
    private boolean mIsShowing;
    private boolean mShowyClickThroughEnable;

    /**
     * 构造函数
     *
     * @param activity Activity
     * @param views    需要高亮的控件
     */
    public NewbieGuideManager(Activity activity, @NonNull View... views) {
        this(activity);
        mViews = views;
        if (mViews == null) {
            throw new IllegalArgumentException("views at least one");
        }
    }

    /**
     * 构造函数
     *
     * @param layoutResId 自定义蒙层布局
     * @param activity    Activity
     */
    public NewbieGuideManager(@NonNull @LayoutRes int layoutResId, Activity activity) {
        this(activity);
        View view = LayoutInflater.from(activity).inflate(layoutResId, null);
        mCustomView = view;
    }

    /**
     * 构造函数
     *
     * @param customView 自定义蒙层视图
     * @param activity   Activity
     */
    public NewbieGuideManager(@NonNull View customView, Activity activity) {
        this(activity);
        mCustomView = customView;
    }


    @SuppressLint("ClickableViewAccessibility")
    private NewbieGuideManager(Activity activity) {
        this.mActivity = activity;
        mDecorView = (FrameLayout) mActivity.getWindow().getDecorView();
        mFlContainer = new FrameLayout(activity);
        mFlContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean isIntercept = true; // 是否拦截事件
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    int rawX = (int) event.getRawX();
                    int rawY = (int) event.getRawY();
                    for (int i = 0; i < mFlContainer.getChildCount(); i++) {
                        View child = mFlContainer.getChildAt(i);
                        if (child instanceof NewbieGuideView) {
                            NewbieGuideView newbieGuideView = (NewbieGuideView) child;
                            Rect showyRect = newbieGuideView.getShowyRect();
                            boolean in = showyRect.contains(rawX, rawY);
                            if (in) {
                                if (mShowyClickThroughEnable) {
                                    isIntercept = false; // 不拦截事件，将事件透传到蒙层下面
                                }

                                if (mOnShowyClickListener != null) {
                                    mOnShowyClickListener.onClick(v);
                                }

                                break;
                            }
                        }
                    }

                    if (mClickMissing) {
                        missing();
                    }
                }

                return isIntercept;
            }
        });
        mDecorView.addView(mFlContainer);
    }

    /**
     * 设置高亮取消监听
     *
     * @param listener
     */
    public void setOnMissingListener(OnMissingListener listener) {
        this.mOnMissingListener = listener;
    }

    /**
     * 设置高亮样式
     *
     * @param style
     * @return
     */
    public NewbieGuideManager style(NewbieGuideView.Style style) {
        mStyle = style;
        return this;
    }

    /**
     * 圆角矩形样式的圆角大小，只有 {@link NewbieGuideView#setStyle(NewbieGuideView.Style)} 传 {@link NewbieGuideView.Style#ROUND_RECT} 和 {@link NewbieGuideView.Style#ROUND_RECT_OUT} 才生效
     *
     * @param radius 圆角大小，单位px。默认最大圆角值
     */
    public NewbieGuideManager setRoundRectRadius(int radius) {
        this.mRoundRectRadius = radius;
        return this;
    }

    /**
     * 设置高亮区域上下左右间距
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @return
     */
    public NewbieGuideManager padding(int left, int top, int right, int bottom) {
        mPaddding[0] = left;
        mPaddding[1] = top;
        mPaddding[2] = right;
        mPaddding[3] = bottom;
        return this;
    }


    /**
     * 点击自动消失
     *
     * @param hide
     * @return
     */
    public NewbieGuideManager setClickAutoMissing(boolean hide) {
        mClickMissing = hide;
        return this;
    }

    /**
     * 设置高亮区域点击事件
     *
     * @param throughEnable 点击事件是否穿透蒙层，即点击到蒙层下面的view
     * @return
     */
    public NewbieGuideManager setClickShowyListener(boolean throughEnable) {
        return setClickShowyListener(throughEnable, null);
    }

    /**
     * 设置高亮区域点击事件
     *
     * @param throughEnable 点击事件是否穿透蒙层，即点击到蒙层下面的view
     * @param listener      高亮区域点击回调
     * @return
     */
    public NewbieGuideManager setClickShowyListener(boolean throughEnable, View.OnClickListener listener) {
        mShowyClickThroughEnable = throughEnable;
        mOnShowyClickListener = listener;
        return this;
    }

    /**
     * 设置背景颜色
     *
     * @param color
     * @return
     */
    public NewbieGuideManager bgColor(@ColorInt int color) {
        mBgColor = color;
        return this;
    }

    /**
     * 添加视图
     *
     * @param layoutId 布局文件
     * @param position 将视图添加到相对于高亮部分的那个位置
     * @param xOffset  添加视图在x方向的偏移量
     * @param yOffset  添加视图在y方向的偏移量
     * @return
     */
    public NewbieGuideManager addView(@LayoutRes int layoutId, Position position, int xOffset, int yOffset) {
        View view = LayoutInflater.from(mActivity).inflate(layoutId, null);
        return addView(view, position, xOffset, yOffset);
    }

    /**
     * 添加视图
     *
     * @param view     布局
     * @param position 添加的view与高亮部分的相对位置
     * @param xOffset  添加视图在x方向的偏移量
     * @param yOffset  添加视图在y方向的偏移量
     * @return
     */
    public NewbieGuideManager addView(View view, Position position, int xOffset, int yOffset) {
        return addView(view, new Position[]{position}, xOffset, yOffset);
    }

    /**
     * 添加视图
     *
     * @param view      布局
     * @param positions 添加的view与高亮部分的相对位置
     * @param xOffset   添加视图在x方向的偏移量
     * @param yOffset   添加视图在y方向的偏移量
     * @return
     */
    public NewbieGuideManager addView(View view, Positions positions, int xOffset, int yOffset) {
        return addView(view, positions.getValues(), xOffset, yOffset);
    }

    private NewbieGuideManager addView(View view, Position[] positions, int xOffset, int yOffset) {
        mAddViews.add(new AddView(view, positions, xOffset, yOffset));
        return this;
    }

    /**
     * 显示
     *
     * @return
     */
    public NewbieGuideManager show() {
        if (mCustomView != null) {
            mFlContainer.addView(mCustomView);
            mIsShowing = true;
            return this;
        }

        mDecorView.post(new Runnable() {
            @Override
            public void run() {
                NewbieGuideView guideView = new NewbieGuideView(mActivity);
                guideView.setStyle(mStyle);
                guideView.setRoundRectRadius(mRoundRectRadius);
                guideView.setShowyViews(mViews);
                guideView.setPadding(mPaddding[0], mPaddding[1], mPaddding[2], mPaddding[3]);
                mFlContainer.addView(guideView);
                if (mBgColor != 0) {
                    guideView.setBgColor(mBgColor);
                }
                mIsShowing = true;
                addViews(guideView);
                guideView.invalidate();
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
            Rect showRect = guideView.getShowyRect();

            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(viewWidth, viewHeight);

            for (Position position : addView.positions) {
                if (position == Position.ToLeft) {
                    layoutParams.leftMargin = showRect.left - viewWidth;

                } else if (position == Position.ToTop) {
                    layoutParams.topMargin = showRect.top - viewHeight;

                } else if (position == Position.ToRight) {
                    layoutParams.leftMargin = showRect.right;

                } else if (position == Position.ToBottom) {
                    layoutParams.topMargin = showRect.bottom;

                } else if (position == Position.AlignLeft) {
                    layoutParams.leftMargin = showRect.left;

                } else if (position == Position.AlignTop) {
                    layoutParams.topMargin = showRect.top;

                } else if (position == Position.AlignRight) {
                    layoutParams.leftMargin = showRect.right - viewWidth;

                } else if (position == Position.AlignBottom) {
                    layoutParams.topMargin = showRect.bottom - viewHeight;

                }
            }

            layoutParams.leftMargin += addView.xOffset;
            layoutParams.topMargin += addView.yOffset;
            mFlContainer.addView(addView.view, layoutParams);
        }
    }

    /**
     * 消失
     */
    public void missing() {
        if (mFlContainer != null) {
            mDecorView.removeView(mFlContainer);
            mFlContainer = null;

            if (mOnMissingListener != null) {
                mOnMissingListener.onMissing();
            }
        }
        mIsShowing = false;
    }

    /**
     * 蒙层是否显示
     *
     * @return
     */
    public boolean isShowing() {
        return mIsShowing;
    }

    private class AddView {
        private View view;
        private Position[] positions;
        private int xOffset;
        private int yOffset;

        public AddView(View view, Position[] positions, int xOffset, int yOffset) {
            this.view = view;
            this.positions = positions;
            this.xOffset = xOffset;
            this.yOffset = yOffset;
        }
    }

    public enum Position {
        // 在高亮view的左、上、右、下
        ToLeft, ToTop, ToRight, ToBottom,
        // 与高亮view的左、上、右、下对齐
        AlignLeft, AlignTop, AlignRight, AlignBottom
    }

    public static class Positions {
        private Position[] values;

        private Positions() {
        }

        public static Positions build(Position... values) {
            Positions instance = new Positions();
            instance.values = values;
            return instance;
        }

        public Position[] getValues() {
            return values;
        }
    }

    public interface OnMissingListener {
        void onMissing();
    }
}
