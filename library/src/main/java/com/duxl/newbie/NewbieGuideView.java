package com.duxl.newbie;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.ColorInt;
import android.view.View;

/**
 * create by duxl 2019/3/22 0022
 * 新手引导View
 */
public class NewbieGuideView extends View {

    private Activity mActivity;
    private Rect mShowyViewsRect; // 需要高亮Views所在区域
    private View[] mShowyViews; // 需要高亮的视图
    private Style mStyle;
    private int mRoundRectRadius; // 圆角矩形样式的圆角大小
    private int mBgColor = Color.parseColor("#B2222222");

    public NewbieGuideView(Activity activity) {
        super(activity);
        mActivity = activity;
    }

    /**
     * 高亮部分呈现的形状
     *
     * @param style
     */
    public void setStyle(Style style) {
        this.mStyle = style;
    }

    public void setBgColor(@ColorInt int color) {
        this.mBgColor = color;
    }

    /**
     * 圆角矩形样式的圆角大小，只有 {@link #setStyle(Style)} 传 {@link Style#ROUND_RECT} 和 {@link Style#ROUND_RECT_OUT} 才生效
     *
     * @param radius 圆角大小，单位px。默认最大圆角值
     */
    public void setRoundRectRadius(int radius) {
        this.mRoundRectRadius = radius;
    }

    /*** 高亮区域样式 */
    public enum Style {
        /*** 矩形 */
        RECT,

        /*** 圆角矩形（内切） */
        ROUND_RECT,
        /*** 圆角矩形（外接） */
        ROUND_RECT_OUT,

        /*** 圆圈（内切） */
        CIRCLE,
        /*** 圆圈（外接） */
        CIRCLE_OUT,

        /*** 椭圆（内切） */
        OVAL,
        /*** 椭圆（外接） */
        OVAL_OUT,

        /*** 不高亮 */
        NONE
    }

    /**
     * 设置需要高亮的视图
     *
     * @param views
     */
    public void setShowyViews(View... views) {
        mShowyViews = views;
        locationViews();
    }

    /**
     * 确定需要高亮Views所在区域
     */
    private void locationViews() {
        mShowyViewsRect = null;
        if (mShowyViews != null) {
            for (int i = 0; i < mShowyViews.length; i++) {
                View v = mShowyViews[i];
                if (i == 0) {
                    mShowyViewsRect = new Rect();
                    v.getGlobalVisibleRect(mShowyViewsRect);
                } else {
                    Rect rect = new Rect();
                    v.getGlobalVisibleRect(rect);
                    if (rect.left < mShowyViewsRect.left) {
                        mShowyViewsRect.left = rect.left;
                    }
                    if (rect.top < mShowyViewsRect.top) {
                        mShowyViewsRect.top = rect.top;
                    }

                    if (rect.right > mShowyViewsRect.right) {
                        mShowyViewsRect.right = rect.right;
                    }

                    if (rect.bottom > mShowyViewsRect.bottom) {
                        mShowyViewsRect.bottom = rect.bottom;
                    }
                }
            }
        }
    }

    /**
     * 视图高亮显示区域的Padding，及实际显示高亮区域要比{@link #setShowyViews(View...)}给定的视图要大
     *
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
     *
     * @return
     */
    public Rect getShowyRect() {
        Rect rect = new Rect(mShowyViewsRect);
        rect.left -= getPaddingLeft();
        rect.top -= getPaddingTop();
        rect.right += getPaddingRight();
        rect.bottom += getPaddingBottom();

        if (mStyle == Style.ROUND_RECT_OUT) { // 如果是外接圆角矩形


        } else if (mStyle == Style.CIRCLE) { // 如果是内切圆
            int width = rect.width();
            int height = rect.height();

            if (width > height) {
                rect.top -= (width - height) / 2;
                rect.bottom += (width - height) / 2;
            } else {
                rect.left -= (height - width) / 2;
                rect.right += (height - width) / 2;
            }
        } else if (mStyle == Style.CIRCLE_OUT) { // 如果是外接圈
            int width = rect.width();
            int height = rect.height();
            double radius = Math.sqrt(width * width + height * height);
            rect.left -= (radius - width) / 2;
            rect.right += (radius - width) / 2;
            rect.top -= (radius - height) / 2;
            rect.bottom += (radius - height) / 2;

        } else if(mStyle == Style.OVAL_OUT) { // 外接椭圆
            int width = rect.width();
            int height = rect.height();
            int outWidth = (int)(width * Math.sqrt(2));
            int outHeight = (int)(height * Math.sqrt(2));

            rect.left -= (outWidth - width) / 2;
            rect.right += (outWidth - width) / 2;
            rect.top -= (outHeight - height) / 2;
            rect.bottom += (outHeight - height) / 2;
        }

        return rect;
    }

    /**
     * 获取圆角矩形的圆角大小，如果没有设置大小，返回最大圆角值
     *
     * @return
     */
    private int getRoundRectRadius() {
        int radius = 0;
        if (mStyle == Style.ROUND_RECT || mStyle == Style.ROUND_RECT_OUT) {
            Rect drawRect = new Rect(getShowyRect());
            if (mRoundRectRadius <= 0) {
                radius = drawRect.width() > drawRect.height() ? drawRect.height() / 2 : drawRect.width() / 2;
            } else {
                radius = mRoundRectRadius;
            }
        }
        return radius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mShowyViewsRect != null) {
            canvas.save();
            // 去掉锯齿，好像在anvas.clipXXX()上面无效
            canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));

            Rect drawRect = new Rect(getShowyRect());

            if (mStyle == Style.RECT) { // 裁剪矩形
                canvas.clipRect(drawRect, Region.Op.DIFFERENCE);

            } else if (mStyle == Style.ROUND_RECT || mStyle == Style.ROUND_RECT_OUT) { // 裁剪圆角矩形
                Path path = new Path();
                int radius = getRoundRectRadius();
                RectF rectF = new RectF(drawRect);
                if (mStyle == Style.ROUND_RECT_OUT) {
                    // 外接圆角，需要扩大高亮区域才容得下高亮view
                    if (rectF.width() > rectF.height()) {
                        rectF.left -= radius;
                        rectF.right += radius;
                    } else {
                        rectF.top -= radius;
                        rectF.bottom += radius;
                    }
                }
                path.addRoundRect(rectF, radius, radius, Path.Direction.CCW);
                canvas.clipPath(path, Region.Op.DIFFERENCE);

            } else if (mStyle == Style.CIRCLE || mStyle == Style.CIRCLE_OUT) { // 裁剪圆形
                Path path = new Path();
                int radius = drawRect.width() / 2;
                path.addCircle(drawRect.exactCenterX(), drawRect.exactCenterY(), radius, Path.Direction.CCW);
                canvas.clipPath(path, Region.Op.DIFFERENCE);

            } else if (mStyle == Style.OVAL || mStyle == Style.OVAL_OUT) { // 剪切椭圆
                Path path = new Path();
                path.addOval(new RectF(drawRect), Path.Direction.CCW);
                canvas.clipPath(path, Region.Op.DIFFERENCE);
            }
            canvas.drawColor(mBgColor); // 将裁剪后，剩余其它部分用阴影颜色填充
            canvas.restore();
        }
    }
}
