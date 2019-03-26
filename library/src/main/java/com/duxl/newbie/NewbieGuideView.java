package com.duxl.newbie;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.view.View;

/**
 * create by duxl 2019/3/22 0022
 * 新手引导View
 */
public class NewbieGuideView extends View {

    private Activity mActivity;
    private Rect mShowyRect; // 高亮区域
    private View[] mShowyViews; // 需要高亮的视图
    private Style mStyle;
    private int mBgColor = Color.parseColor("#B2222222");

    public NewbieGuideView(Activity activity) {
        super(activity);
        mActivity = activity;
    }

    /**
     * 高亮部分呈现的形状
     * @param style
     */
    public void setStyle(Style style) {
        this.mStyle = style;
        locationShowy();
    }

    public void setBgColor(@ColorInt int color) {
        this.mBgColor = color;
    }

    public enum Style {
        /** 圆圈 */
        CIRCLE,
        /** 矩形 */
        RECT,
        /** 椭圆 */
        OVA
    }

    /**
     * 设置需要高亮的视图
     * @param views
     */
    public void setShowyViews(View... views) {
        mShowyViews = views;
        locationShowy();
    }

    /**
     * 确定高亮区域位置
     */
    private void locationShowy() {
        if(mStyle != null && mShowyViews != null) {
            for (int i=0; i<mShowyViews.length; i++) {
                View v = mShowyViews[i];
                if(i == 0) {
                    mShowyRect = new Rect();
                    v.getGlobalVisibleRect(mShowyRect);
                } else {
                    Rect rect = new Rect();
                    v.getGlobalVisibleRect(rect);
                    if (rect.left < mShowyRect.left) {
                        mShowyRect.left = rect.left;
                    }
                    if(rect.top < mShowyRect.top) {
                        mShowyRect.top = rect.top;
                    }

                    if(rect.right > mShowyRect.right) {
                        mShowyRect.right = rect.right;
                    }

                    if(rect.bottom > mShowyRect.bottom) {
                        mShowyRect.bottom = rect.bottom;
                    }
                }
            }
            invalidate();
        }
    }

    /**
     * 视图高亮显示区域的Padding，及实际显示高亮区域要比{@link #setShowyViews(View...)}给定的视图要大
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);
    }

    /**
     * 获取高亮位置区域
     * @return
     */
    public Rect getShowyRect() {
        Rect rect = new Rect(mShowyRect);
        rect.left += getPaddingLeft();
        rect.top += getPaddingTop();
        rect.right += getPaddingRight();
        rect.bottom += getPaddingBottom();

        if(mStyle == Style.CIRCLE) { // 如果是圆
            int width = rect.right - rect.left;
            int height = rect.bottom - rect.top;

            if(width > height) {
                rect.top -= (width - height) / 2;
                rect.bottom += (width - height) / 2;
            } else {
                rect.left -= (height - width) / 2;
                rect.right += (height - width) / 2;
            }
        }

        return rect;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mShowyRect != null) {
            canvas.save();
            Rect drawRect = new Rect(mShowyRect);
            drawRect.left -= getPaddingLeft();
            drawRect.top -= getPaddingTop();
            drawRect.right += getPaddingRight();
            drawRect.bottom += getPaddingBottom();

            if(mStyle == Style.RECT) { // 裁剪矩形
                canvas.clipRect(drawRect, Region.Op.DIFFERENCE);

            } else if(mStyle == Style.CIRCLE) { // 裁剪圆形
                Path path = new Path();
                int rectWidth = drawRect.right - drawRect.left;
                int rectHeight = drawRect.bottom - drawRect.top;
                int x = drawRect.left + rectWidth / 2;
                int y = drawRect.top + rectHeight / 2;
                int radius = rectWidth > rectHeight ? rectWidth / 2 : rectHeight / 2;
                path.addCircle(x, y, radius, Path.Direction.CCW);
                canvas.clipPath(path, Region.Op.DIFFERENCE);

            } else if(mStyle == Style.OVA) { // 剪切椭圆
                Path path = new Path();
                path.addOval(new RectF(drawRect), Path.Direction.CCW);
                canvas.clipPath(path, Region.Op.DIFFERENCE);
            }
            canvas.drawColor(mBgColor); // 将裁剪后，剩余其它部分用阴影颜色填充
            canvas.restore();
        }
    }
}
