# LetterBar
#### 简介
LetterBar：自定义View实现字母导航栏目，支持动态添加字符，Drawable，String等   

#### 使用

[**最新版本号：1.0.1**](https://bintray.com/beta/#/ricelee/maven/LetterBar?tab=overview)
```gradle
    implementation 'ricelee.ui.letterbar:LetterBar:1.0.1'
```
 
1.xml中的使用
 
 ```xml
   <ricelee.ui.letterbar.LetterBar
        android:id="@+id/bar4"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:layout_marginTop="20dp"
        android:layout_marginBottom="20dp"
        app:barTouchBackground="#13000000"
        app:letterColor="#313131"
        app:letterHorizontalPadding="6dp"
        app:letterShowChecked="true"
        app:letterSize="16sp"
        app:letterTouchColor="#df3030"
        app:letterTouchDrawable="@drawable/drawable_letter_touch"
        app:letterVerticalPadding="6dp" />
```
<br>自定义属性

属性|描述|使用范围
----|---- |----
letterDynamicMode|字母是否动态添加|A-Z以及特殊字符的添加模式
letterSize|字母大小
letterColor|字母颜色
letterVerticalPadding|字母竖直间隔
letterHorizontalPadding|字母水平间隔
barBackground|背景
letterTouchShow|默认是否显示全部char|主要用于触摸Pop的显示规则
letterTouchDrawable|触摸字母背景
barTouchBackground|触摸时背景
letterTouchColor|触摸时字母颜色
letterShowChecked|触摸结束后是否显示当前选中

2.代码中添加PopWindow,用于控制Letter的显示
   <br> 
    TextViewBuilder中设置TextView相关属性
   <br> 
   PopupBehavior设置PopWindow的显示位置，在PopupBehavior中有相关方法
    比如createCenterBehavior(),createStableBehavior(),createFollowYBehavior(),createCenterItemBehavior()
```java
 letterBar.setLetterTouchListener(new SingleLetterPopup()
                .setDuration(2000)
                .setTextViewBuilder(new TextViewBuilder()
                        .setBackgroundDrawable(buildDrawable())
                        .setPadding(dp_10, dp_10, dp_10, dp_10)
                        .setTextSize(20f).setTextColor(Color.RED))
                .setPopupBehavior(PopupBehavior.createCenterBehavior())
                .createPopup());
```


3.代码中添加 LetterScrollListener，添加列表滚动

```java
     letterBar.setLetterScrollListener((letter, position) -> {
         //这里的position是在LetterBar中的显示位置
         ....
        });
```


4.动态模式以及非动态模式的说明

<br> 这些方法可以很方便的添加一些你需要显示的东西
```java
LetterBar addFirstChar(char ch);
LetterBar addLastChar(char ch);
LetterBar addFirstString(String string);
LetterBar addLastString(String string);
LetterBar addFirstDrawable(DrawableLetter drawableLetter);
LetterBar addFirstDrawable(@DrawableRes int drawable);
LetterBar addLastDrawable(DrawableLetter drawableLetter);
LetterBar addLastDrawable(@DrawableRes int drawable);
```
<br>但是在动态模式下，addLast..方法会有出入，不过在动态模式下字母导航栏最下面要显示的通常是一些非汉语姓名之类的，所以你可能需要这个方法
```java
 LetterBar addAutoSpecialLetter(char ch, int compareInt);
```
<br>
ch 是你需要的字符，比如‘#’ 或者‘*’之类的 ，而compareInt是用于比较char的值

**注：‘A’=65 'Z'=90 ,'#'和 ‘*’ 都小于65**


<br>快速搭配 
```java
    //列表类实现CharLetter
    public class RecyclerBean implements CharLetter{
      ...
      
      @Override
      public char getCharLetter() {
        return '';
      }
    }
    //通过attachCharLetter完成匹配，
    public <T extends CharLetter> void attachCharLetter(List<T> charLetterList);
    public <T extends CharLetter> void attachCharLetter(T t);
```

**注：attachCharLetter是不负责RecyclerBean的排序的，
 <br>在动态模式下负责添加Letter
 <br>在非动态模式下你完全可以不用它，如果在非动态模式下，你的Letter 在没有ListBean的时候不显示，
 你可以搭配letterTouchShow快速完成**


5.其他方法 
```java
   //设置触摸时不显示的Letter
    public void setNoneShowLetter(char... chars) ;
    public void setNoneShowLetter(int... positions) ;
```


