package com.silver.stickerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

public class PicSticker extends BaseSticker {

    private Bitmap mStickerBitmap;//贴纸图像
    private Matrix mMatrix;//维护图像变化的矩阵

    private float[] mSrcPoints;//矩阵变换前的点坐标
    private float[] mDstPoints;//矩阵变换后的点坐标

    public PicSticker(Context context, Bitmap bitmap) {
        super();
        this.mStickerBitmap = bitmap;
        mMatrix = new Matrix();
        MidPointF = new PointF();

        mSrcPoints = new float[]{
                0, 0,//左上
                bitmap.getWidth(), 0,//右上
                bitmap.getWidth(), bitmap.getHeight(),//右下
                0, bitmap.getHeight(),//左下
                bitmap.getWidth() / 2f, bitmap.getHeight() / 2f//中间点
        };
        mDstPoints = mSrcPoints.clone();
        mStickerBound = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
    }

    public Bitmap getBitmap() {
        return mStickerBitmap;
    }

    public Matrix getMatrix() {
        return mMatrix;
    }

    @Override
    public void clear() {
        Bitmap bitmap = mStickerBitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

    @Override
    public boolean isContainPoint(float x, float y) {
        float[] dstPoints = new float[2];
        float[] srcPoints = new float[]{x, y};

        Matrix matrix = new Matrix();
        getMatrix().invert(matrix);
        matrix.mapPoints(dstPoints, srcPoints);
        if (getStickerBound().contains(dstPoints[0], dstPoints[1])) {//RectF应该是去除matrix变化之前的RectF
            return true;
        }else {
            return false;
        }
    }

    /**
     * 平移操作
     */
    @Override
    public void translate(float dx, float dy) {
        mMatrix.postTranslate(dx, dy);
        updatePoints();
    }

    /**
     * 缩放操作
     */
    @Override
    public void scale(float sx, float sy) {
        mMatrix.postScale(sx, sy, MidPointF.x, MidPointF.y);
        updatePoints();
    }

    /**
     * 旋转操作
     */
    @Override
    public void rotate(float degrees) {
        mMatrix.postRotate(degrees, MidPointF.x, MidPointF.y);
        updatePoints();
    }

    /**
     * 当矩阵发生变化的时候，更新坐标点（src坐标点经过matrix映射变成了dst坐标点）
     */
    private void updatePoints() {
        //更新贴纸点坐标
        mMatrix.mapPoints(mDstPoints, mSrcPoints);
        //更新贴纸中心点坐标
        MidPointF.set(mDstPoints[8], mDstPoints[9]);
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint) {
        //绘制贴纸
        paint.setColor(Color.BLUE);
        canvas.drawBitmap(mStickerBitmap, mMatrix, paint);
        if (isFocus) {
            //绘制贴纸边框
            canvas.drawLine(mDstPoints[0], mDstPoints[1], mDstPoints[2], mDstPoints[3], paint);
            canvas.drawLine(mDstPoints[2], mDstPoints[3], mDstPoints[4], mDstPoints[5], paint);
            canvas.drawLine(mDstPoints[4], mDstPoints[5], mDstPoints[6], mDstPoints[7], paint);
            canvas.drawLine(mDstPoints[6], mDstPoints[7], mDstPoints[0], mDstPoints[1], paint);
        }
    }

}
