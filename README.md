# StickerView
## 简介
StickerView是一款简易的贴纸视图，已实现边缘碰撞判定，防止贴纸移出布局外。支持图片和文本两种贴纸模式，也可以通过重写BaseSticker来自定义其他贴纸。
您可以通过以下方法来启用它：

## 使用方法
Gradle:
```groovy
repositories {
    jcenter()
}

dependencies {
    implementation 'com.silver:stickerview:1.0.3'
}
```
您可以在XML中创建视图
```
<com.silver.stickerview.StickerView
        android:id="@+id/stickerview"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```
再在代码中添加贴纸
```java
StickerView mStickerView = findViewById(R.id.stickerview);
TextSticker mSticker = new TextSticker(getResources().getString(R.string.demo_text));
mSticker.setTextSize(50f);
mStickerView.addSticker(mSticker);
```
```java
StickerView mStickerLayout = findViewById(R.id.stickerview);
PicSticker mSticker = new PicSticker(context, bitmap);
mStickerView.addSticker(mSticker);
```
## License

    Copyright 2020 Silver.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
