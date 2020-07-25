package com.silver.stickerview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;

public class TextSticker extends BaseSticker {
    private final String TAG = "TextSticker";
    private String text;
    private Paint paint;

    public TextSticker(String text){
        this.text = text;
        this.paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);

        Rect bound = new Rect();
        paint.getTextBounds(text, 0, text.length(), bound);
        mStickerBound = new RectF(bound.left, bound.top, bound.right, bound.bottom);
        float width = mStickerBound.right - mStickerBound.left;
        float height = mStickerBound.bottom - mStickerBound.top;
        MidPointF = new PointF(width / 2, height / 2);
        BottomRightF = new PointF(width, height);
    }

    @Override
    public void translate(float dx, float dy) {//相对移动
        MidPointF.x = MidPointF.x + dx;
        MidPointF.y = MidPointF.y + dy;
        BottomRightF.x = BottomRightF.x + dx;
        BottomRightF.y = BottomRightF.y + dy;
    }

    public void translateTo(float dx, float dy){//绝对移动
        //MidPointF.x = dx;
        //MidPointF.y = dy;

        float width = mStickerBound.right - mStickerBound.left;
        float height = mStickerBound.bottom - mStickerBound.top;
        MidPointF.x = dx - width / 2;
        MidPointF.y = dy - height / 2;
        BottomRightF.x = dx;
        BottomRightF.y = dy;
    }

    @Override
    public void scale(float sx, float sy) {

    }

    @Override
    public void rotate(float degrees) {

    }


    @Override
    public void clear() {

    }

    @Override
    public boolean isContainPoint(float x, float y) {
        RectF bound = new RectF(MidPointF.x - (mStickerBound.right - mStickerBound.left) / 2,
                MidPointF.y - (mStickerBound.bottom - mStickerBound.top) / 2,
                MidPointF.x + (mStickerBound.right - mStickerBound.left) / 2,
                MidPointF.y + (mStickerBound.right - mStickerBound.left) / 2);
        if(bound.contains(x,y)){
            return true;
        }
        return false;
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint) {
        Paint.FontMetrics fontMetrics=this.paint.getFontMetrics();
        float distance=(fontMetrics.bottom - fontMetrics.top)/2 - fontMetrics.bottom;
        canvas.drawText(text, MidPointF.x, MidPointF.y + distance, this.paint);
        if (isFocus) {
            paint.setColor(Color.BLUE);
            float width = mStickerBound.right - mStickerBound.left;
            float height = mStickerBound.bottom - mStickerBound.top;
            canvas.drawLine(MidPointF.x - width / 2,
                    MidPointF.y - height / 2,
                    MidPointF.x + width / 2,
                    MidPointF.y - height / 2,
                    paint);
            canvas.drawLine(MidPointF.x + width / 2,
                    MidPointF.y - height / 2,
                    MidPointF.x + width / 2,
                    MidPointF.y + height / 2,
                    paint);
            canvas.drawLine(MidPointF.x + width / 2,
                    MidPointF.y + height / 2,
                    MidPointF.x - width / 2,
                    MidPointF.y + height / 2,
                    paint);
            canvas.drawLine(MidPointF.x - width / 2,
                    MidPointF.y + height / 2,
                    MidPointF.x - width / 2,
                    MidPointF.y - height / 2,
                    paint);
        }
    }

    public void setBold(boolean b){
        paint.setFakeBoldText(b);
        updateBound();
    }

    public void setItalic(boolean b){
        if(b){
            paint.setTextSkewX(-0.25f);
        }else {
            paint.setTextSkewX(0f);
        }
        updateBound();
    }

    public void setColor(int color){
        paint.setColor(color);
        updateBound();
    }

    public void setText(String text){
        this.text = text;
        Rect bound = new Rect();
        paint.getTextBounds(text, 0, text.length(), bound);
        mStickerBound = new RectF(bound.left, bound.top, bound.right, bound.bottom);
    }

    public void setTypeface(Typeface typeface){
        paint.setTypeface(typeface);
        updateBound();
    }

    public void setTextSize(float textSize){
        paint.setTextSize(textSize);
        updateBound();
    }

    private void updateBound(){
        Rect bound = new Rect();
        paint.getTextBounds(text, 0, text.length(), bound);
        mStickerBound = new RectF(bound.left, bound.top, bound.right, bound.bottom);

        float width = mStickerBound.right - mStickerBound.left;
        float height = mStickerBound.bottom - mStickerBound.top;
        BottomRightF = new PointF(MidPointF.x + width / 2 , MidPointF.y + height / 2);
    }
}
