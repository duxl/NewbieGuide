# NewbieGuide
新手引导蒙层，与业务代码隔离，简单易用

# 使用步骤
###### 1、在项目的根build.gradle文件中，添加仓库地址，就像下面一样
```xml
allprojects {  
	repositories {  
		...  
		maven { url 'https://jitpack.io' }  
	}  
}
```

###### 2、在app的build.gradle中添加如下依赖
```xml
implementation ('com.github.duxl:NewbieGuide:v1.1.2') {
	exclude group: 'com.android.support'
}
```

###### 3、在Activity创建并显示
```java
new NewbieGuideManager(activity, views)
	.clickAutoMissing(true)
	.show();
```

#### 使用示例 [MainActivity](/app/src/main/java/com/duxl/newbie/demo/MainActivity.java "点击查看源码")

#### demo下载 [demo.apk](/app/release/app-release.apk "下载")

# API介绍
### 构造函数 
###### 自动创建蒙层，并将指定的views高亮显示
```java
NewbieGuideManager(Activity activity, @NonNull View...views)
```

###### 根据指定的布局文件创建蒙层
```java
NewbieGuideManager(@NonNull @LayoutRes int layoutResId, Activity activity)
```

###### 根据指定的view创建蒙层
```java
NewbieGuideManager(@NonNull View customView, Activity activity)
```
### 成员函数 
###### 设置高亮区域样式，支持：RECT矩形、CIRCLE圆形、OVA椭圆、NONE无（仅对自动创建蒙层有效）
```java
NewbieGuideManager style(NewbieGuideView.Style style)
```

###### 设置高亮区域上下左右间距（仅对自动创建蒙层有效）
```java
NewbieGuideManager padding(int left, int top, int right, int bottom)
```

###### 设置蒙层背景颜色（仅对自动创建蒙层有效）
```java
NewbieGuideManager bgColor(@ColorInt int color)
```

###### 在蒙层上面添加view，position用于指定添加的view位于高亮区域的位置（仅对自动创建蒙层有效）
```java
NewbieGuideManager addView(View view, Position position, int xOffset, int yOffset)
```

###### 在蒙层上面添加view，positions用于指定添加的view位于高亮区域的位置（仅对自动创建蒙层有效）

与position相比，positions可以同时指定多个位置参数，使用Positions.build(Position... values)

```java
NewbieGuideManager addView(View view, Positions positions, int xOffset, int yOffset)
```

###### 点击蒙层是否自动消失，默认false

```java
NewbieGuideManager setClickAutoMissing(boolean hide)
```

###### 取消蒙层
```java
void missing(OnMissingListener listener)
```

###### 设置蒙层消失监听
```java
void setOnMissingListener(OnMissingListener listener)
```

###### 显示蒙层
```java
void show()
```

###### 蒙层是否显示
```java
boolean isShowing()
```

------

### position参数解释

| Position.ToLeft       | 添加View的右边在高亮区域的左边     |
| :-------------------- | ---------------------------------- |
| **Position.ToTop**    | **添加View的下边在高亮区域的上边** |
| **Position.ToRight**  | **添加View的左边在高亮区域的右边** |
| **Position.ToBottom** | **添加View的上边在高亮区域的下边** |
| Position.AlignLeft    | 添加View的左边与高亮区域的左边对齐 |
| Position.AlignTop     | 添加View的上边与高亮区域的上边对齐 |
| Position.AlignRight   | 添加View的右边与高亮区域的右边对齐 |
| Position.AlignBottom  | 添加View的下边与高亮区域的下边对齐 |