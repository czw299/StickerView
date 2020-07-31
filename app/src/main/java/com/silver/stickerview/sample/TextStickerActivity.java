package com.silver.stickerview.sample;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.silver.stickerview.StickerView;
import com.silver.stickerview.TextSticker;

public class TextStickerActivity extends AppCompatActivity {

    private StickerView mStickerView;
    private TextSticker mSticker;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_sticker);
        initView();
    }

    private void initView(){
        mStickerView = findViewById(R.id.text_sticker_layout);
        mSticker = new TextSticker(getResources().getString(R.string.demo_text));
        mSticker.setTextSize(50f);
        mSticker.getPaint().setColor(Color.WHITE);
        mStickerView.addSticker(mSticker);
        mSticker.translateTo(250,150);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mStickerView.removeAllSticker();
    }
}
