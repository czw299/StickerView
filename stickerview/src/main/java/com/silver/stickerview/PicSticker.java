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

    private Bitmap mStickerBitmap;
    private Matrix mMatrix;

    private float[] mSrcPoints;
    private float[] mDstPoints;

    public PicSticker(Context context, Bitmap bitmap) {
        super();
        this.mStickerBitmap = bitmap;
        mMatrix = new Matrix();
        MidPointF = new PointF();

        mSrcPoints = new float[]{
                0, 0,
                bitmap.getWidth(), 0,
                bitmap.getWidth(), bitmap.getHeight(),
                0, bitmap.getHeight(),
                bitmap.getWidth() / 2f, bitmap.getHeight() / 2f
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
        if (getStickerBound().contains(dstPoints[0], dstPoints[1])) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void translate(float dx, float dy) {
        mMatrix.postTranslate(dx, dy);
        updatePoints();
    }

    @Override
    public void scale(float sx, float sy) {
        mMatrix.postScale(sx, sy, MidPointF.x, MidPointF.y);
        updatePoints();
    }

    @Override
    public void rotate(float degrees) {
        mMatrix.postRotate(degrees, MidPointF.x, MidPointF.y);
        updatePoints();
    }

    private void updatePoints() {
        mMatrix.mapPoints(mDstPoints, mSrcPoints);
        MidPointF.set(mDstPoints[8], mDstPoints[9]);
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint) {
        paint.setColor(Color.YELLOW);
        canvas.drawBitmap(mStickerBitmap, mMatrix, paint);
        if (isFocus) {
            canvas.drawLine(mDstPoints[0], mDstPoints[1], mDstPoints[2], mDstPoints[3], paint);
            canvas.drawLine(mDstPoints[2], mDstPoints[3], mDstPoints[4], mDstPoints[5], paint);
            canvas.drawLine(mDstPoints[4], mDstPoints[5], mDstPoints[6], mDstPoints[7], paint);
            canvas.drawLine(mDstPoints[6], mDstPoints[7], mDstPoints[0], mDstPoints[1], paint);
        }
    }

}
