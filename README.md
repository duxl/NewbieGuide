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
implementation ('com.github.duxl:NewbieGuide:1.0.0') {
	exclude group: 'com.android.support'
}
```

###### 3、在Activity创建并显示
```java
NewbieGuideManager newbieGuideManager = new NewbieGuideManager(activity, views);
newbieGuideManager.show();
```

#### 使用示例 [MainActivity](/app/src/main/java/com/duxl/newbie/demo/MainActivity.java "点击查看源码")

![demo.gif](/app/pics/demo.gif)

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

###### 设置高亮区域样式，支持：RECT矩形、CIRCLE圆形、OVA椭圆（仅对自动创建蒙层有效）
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

###### 点击蒙层是否自动消失，默认false
```java
NewbieGuideManager clickAutoMissing(boolean hide)
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





