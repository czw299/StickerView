package com.silver.stickerview;

import java.util.ArrayList;
import java.util.List;

public class StickerManager {

    private static volatile StickerManager mInstance;

    private List<BaseSticker> mStickerList = new ArrayList<>();

    public static StickerManager getInstance() {
        if (mInstance == null) {
            synchronized (StickerManager.class) {
                if (mInstance == null) {
                    mInstance = new StickerManager();
                }
            }
        }
        return mInstance;
    }

    public void addSticker(BaseSticker sticker) {
        mStickerList.add(sticker);
    }

    public List<BaseSticker> getStickerList() {
        return mStickerList;
    }

    public void removeSticker(PicSticker picSticker) {
        picSticker.clear();
        mStickerList.remove(picSticker);

    }

    public void removeAllSticker() {
        for (int i = 0; i < mStickerList.size(); i++) {
            mStickerList.get(i).clear();
        }
        mStickerList.clear();
    }

    public void setFocusSticker(BaseSticker focusSticker) {
        for (int i = 0; i < mStickerList.size(); i++) {
            BaseSticker sticker = mStickerList.get(i);
            if (sticker == focusSticker) {
                sticker.setFocus(true);
            } else {
                sticker.setFocus(false);
            }
        }
    }

    public void clearAllFocus() {
        for (int i = 0; i < mStickerList.size(); i++) {
            BaseSticker sticker = mStickerList.get(i);
            sticker.setFocus(false);
        }
    }

    public BaseSticker getSticker(float x, float y) {
        for (int i = mStickerList.size() - 1; i >= 0; i--) {
            BaseSticker sticker = mStickerList.get(i);
            if(sticker.isContainPoint(x,y)){
                return sticker;
            }
        }
        return null;
    }
}
