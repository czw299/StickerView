package com.silver.stickerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class StickerLayout extends View implements View.OnTouchListener {

    private Context mContext;
    private Paint mPaint;

    private BaseSticker mStick;

    public int mMode;
    public static final int MODE_NONE = 0;
    public static final int MODE_SINGLE = 1;
    public static final int MODE_MULTIPLE = 2;

    private PointF mLastSinglePoint = new PointF();
    private PointF mLastDistanceVector = new PointF();
    private PointF mDistanceVector = new PointF();
    private float mLastDistance;

    //记录点坐标，减少对象在onTouch中的创建
    private PointF mFirstPoint = new PointF();
    private PointF mSecondPoint = new PointF();

    public StickerLayout(Context context) {
        super(context);
        init(context);
    }

    public StickerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StickerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        //设置触摸监听
        setOnTouchListener(this);
    }

    public Paint getPaint() {
        if (mPaint == null) {
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(Color.BLACK);
            mPaint.setStrokeWidth(2);
        }
        return mPaint;
    }

    public void setPaint(Paint mPaint) {
        this.mPaint = mPaint;
    }


    public void addSticker(BaseSticker sticker) {
        int size = StickerManager.getInstance().getStickerList().size();
        if (size < 9) {
            StickerManager.getInstance().addSticker(sticker);
            StickerManager.getInstance().setFocusSticker(sticker);
            invalidate();
        } else {
            Toast.makeText(mContext, "A maximum of 9 stickers can be added", Toast.LENGTH_SHORT).show();
        }
    }

    public void removeSticker(PicSticker picSticker) {
        if (picSticker.isFocus()) {
            StickerManager.getInstance().removeSticker(picSticker);
            invalidate();
        }
    }

    /**
     * 清空贴纸
     */
    public void removeAllSticker() {
        StickerManager.getInstance().removeAllSticker();
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        List<BaseSticker> stickerList = StickerManager.getInstance().getStickerList();
        BaseSticker focusSticker = null;
        for (int i = 0; i < stickerList.size(); i++) {
            BaseSticker sticker = stickerList.get(i);
            if (sticker.isFocus()) {
                focusSticker = sticker;
            } else {
                sticker.onDraw(canvas, getPaint());
            }
        }
        if (focusSticker != null) {
            focusSticker.onDraw(canvas, getPaint());
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                mStick = StickerManager.getInstance().getSticker(event.getX(), event.getY());
                if (mStick == null) {
                    if (event.getPointerCount() == 2) {
                        mStick = StickerManager.getInstance().getSticker(event.getX(1), event.getY(1));
                    }
                }
                if (mStick != null) {
                    StickerManager.getInstance().setFocusSticker(mStick);
                }
                break;
            default:
                break;
        }
        if (mStick != null) {
            stickerTouch(event);
        } else {
            StickerManager.getInstance().clearAllFocus();
        }
        invalidate();
        return true;
    }

    public void reset() {
        mLastSinglePoint.set(0f, 0f);
        mLastDistanceVector.set(0f, 0f);
        mDistanceVector.set(0f, 0f);
        mLastDistance = 0f;
        mMode = MODE_NONE;
    }

    public float calculateDistance(PointF firstPointF, PointF secondPointF) {
        float x = firstPointF.x - secondPointF.x;
        float y = firstPointF.y - secondPointF.y;
        return (float) Math.sqrt(x * x + y * y);
    }

    public float calculateDegrees(PointF lastVector, PointF currentVector) {
        float lastDegrees = (float) Math.atan2(lastVector.y, lastVector.x);
        float currentDegrees = (float) Math.atan2(currentVector.y, currentVector.x);
        return (float) Math.toDegrees(currentDegrees - lastDegrees);
    }

    public void stickerTouch(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mMode = MODE_SINGLE;
                mLastSinglePoint.set(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if (event.getPointerCount() == 2) {
                    mMode = MODE_MULTIPLE;
                    mFirstPoint.set(event.getX(0), event.getY(0));
                    mSecondPoint.set(event.getX(1), event.getY(1));
                    mLastDistanceVector.set(mFirstPoint.x - mSecondPoint.x, mFirstPoint.y - mSecondPoint.y);
                    mLastDistance = calculateDistance(mFirstPoint, mSecondPoint);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                mStick.isChange=true;
                if (mMode == MODE_SINGLE) {
                    float tx=0;
                    float ty=0;
                    if(mStick.getMidPointF().x + event.getX() - mLastSinglePoint.x >= getWidth()){
                        tx = getWidth() - mStick.getMidPointF().x;
                    }else if(mStick.getMidPointF().x + event.getX() - mLastSinglePoint.x <= 0){
                        tx = 0 - mStick.getMidPointF().x;
                    }else{
                        tx = event.getX() - mLastSinglePoint.x;
                    }
                    if(mStick.getMidPointF().y + event.getY() - mLastSinglePoint.y >= getHeight()){
                        ty = getHeight() - mStick.getMidPointF().y;
                    }else if(mStick.getMidPointF().y + event.getY() - mLastSinglePoint.y <= 0){
                        ty = 0 - mStick.getMidPointF().y;
                    }else {
                        ty = event.getY() - mLastSinglePoint.y;
                    }
                    mStick.translate(tx, ty);
                    mLastSinglePoint.set(event.getX(), event.getY());
                }
                if (mMode == MODE_MULTIPLE && event.getPointerCount() == 2) {
                    mFirstPoint.set(event.getX(0), event.getY(0));
                    mSecondPoint.set(event.getX(1), event.getY(1));
                    float distance = calculateDistance(mFirstPoint, mSecondPoint);
                    float scale = distance / mLastDistance;
                    mStick.scale(scale, scale);
                    mLastDistance = distance;
                }
                break;
            case MotionEvent.ACTION_UP:
                reset();
                break;
        }
    }
}
