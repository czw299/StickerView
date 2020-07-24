package com.silver.stickerviewdemo;

import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.silver.stickerview.StickerLayout;
import com.silver.stickerview.TextSticker;

public class TextStickerActivity extends AppCompatActivity {

    private StickerLayout mStickerLayout;
    private TextSticker mSticker;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_sticker);
        initView();
    }

    private void initView(){
        mStickerLayout = findViewById(R.id.text_sticker_layout);
        mSticker = new TextSticker(getResources().getString(R.string.demo_text));
        mSticker.setTextSize(50f);
        mStickerLayout.addSticker(mSticker);
        mSticker.translateTo(250,150);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mStickerLayout.removeAllSticker();
    }
}
