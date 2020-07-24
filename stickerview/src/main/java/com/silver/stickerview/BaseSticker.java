package com.silver.stickerview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

public abstract class BaseSticker{

    public RectF mStickerBound;//贴纸范围,用于计算是否包含某给点，onTouch返回的是float，所以要用RectF
    public boolean isFocus;//当前是否聚焦

    public PointF MidPointF;//贴纸中心的点坐标
    public PointF BottomRightF;

    public boolean isChange=false;
    public BaseSticker() {

    }

    public RectF getStickerBound() {
        return mStickerBound;
    }

    public PointF getBottomRightF() {
        return BottomRightF;
    }

    public void setBottomRightF(PointF bottomRightF) {
        BottomRightF = bottomRightF;
    }

    public abstract void translate(float dx, float dy);

    public abstract void scale(float sx, float sy);

    public abstract void rotate(float degrees);

    public abstract void onDraw(Canvas canvas, Paint paint);

    public abstract void clear();

    public abstract boolean isContainPoint(float x,float y);

    public boolean isFocus() {
        return isFocus;
    }

    public void setFocus(boolean focus) {
        isFocus = focus;
    }

    public PointF getMidPointF(){
        return MidPointF;
    }

}
