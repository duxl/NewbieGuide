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
