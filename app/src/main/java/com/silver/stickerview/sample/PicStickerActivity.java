package com.silver.stickerview.sample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.silver.stickerview.PicSticker;
import com.silver.stickerview.StickerView;

public class PicStickerActivity extends AppCompatActivity {

    private Context context;
    private StickerView mStickerView;
    private PicSticker mSticker;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_sticker);
        context = this;
        initView();
    }

    private void initView(){
        mStickerView = findViewById(R.id.pic_sticker_layout);
        mSticker = new PicSticker(context, drawableToBitmap(getResources().getDrawable(R.drawable.demosticker)));
        mStickerView.addSticker(mSticker);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap( drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mStickerView.removeAllSticker();
    }
}
