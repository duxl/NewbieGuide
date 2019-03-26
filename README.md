# NewbieGuide
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
style(NewbieGuideView.Style style)
```





