package ricelee.ui.letterbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ricelee.ui.letterbar.utils.Utils;

public class LetterBar extends View {

    private int mWidth, mHeight;
    private Rect mRect;
    private int itemHeight, itemWidth;

    private List<ILetter> mLetterList = new ArrayList<>();
    private List<CharLetter.SpecialCharLetter> mSpecialCharList;
    private List<CharLetter.ComparableCharLetter> mCompareCharList;

    private static final char CHAR_A = 'A';
    private static final char CHAR_Z = 'Z';

    //  字母的模式 是否动态添加
    private boolean mLetterDynamicMode;
    //触摸时 是否显示Letter(全部)
    private boolean mCharLetterTouchShow;
    //字母大小
//    private int mLetterSize;
    //两个字母之间的竖直间距
    private int mLetterVerticalPadding;
    //字母距水平边界的间距
    private int mLetterHorizontalPadding;
    //字母颜色
    private int mLetterColor;
    //背景
    private Drawable mBarBackground;
    //    //触摸模式
//    private int mLetterTouchMode;
    //    //触摸时绘制模式
////    private int mLetterDrawMode;
    //触摸时字母Drawable
    private Drawable mLetterTouchDrawable;
    //触摸时LetterBar背景Drawable
    private Drawable mBarTouchBackground;
    //触摸时字母颜色
    private int mLetterTouchColor;
    //触摸结束时是否显示当前字母颜色
    private boolean mLetterShowChecked = false;

    private int mTouchIndex = -1;
    private Rect mTouchRect;
    private Paint mPaint;
    private boolean mTouchState = false;

    public static final int POPUP_BEHAVIOR_NONE = 0;
    public static final int POPUP_BEHAVIOR_CENTER = 1;
    public static final int POPUP_BEHAVIOR_STABLE = 2;
    public static final int POPUP_BEHAVIOR_CENTER_ITEM = 3;
    public static final int POPUP_BEHAVIOR_FOLLOW_Y = 4;
    public static final int POPUP_BEHAVIOR_AUTO = 5;


    private LetterTouchListener mLetterTouchListener;
    private LetterScrollListener mLetterScrollListener;
    private PopupHandler mPopupHandler;

    interface LetterTouchListener {
        void setLetterBar(LetterBar letterBar);
    }

    public LetterBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public LetterBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initTextPaint();
        initAttrs(context, attrs, defStyleAttr);
        init();
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LetterBar, defStyleAttr, 0);
        int letterSize = (int) Utils.sp2px(14f);
        int letterColor = Color.parseColor("#313131");
        int letterVerticalPadding = 0;
        int letterHorizontalPadding = (int) Utils.dp2px(4f);
        Drawable barBackground = null;
        Drawable barTouchBackground = null;
        int n = typedArray.getIndexCount();
        int letterTouchColor = letterColor;
        boolean letterShowChecked = false;
        Drawable letterTouchDrawable = null;
        boolean letterDynamicMode = false;
        boolean charLetterTouchShow = true;
        for (int i = 0; i < n; i++) {
            int attr = typedArray.getIndex(i);
            if (attr == R.styleable.LetterBar_barBackground) {
                barBackground = typedArray.getDrawable(attr);
            } else if (attr == R.styleable.LetterBar_letterSize) {
                letterSize = typedArray.getDimensionPixelSize(attr, letterSize);
            } else if (attr == R.styleable.LetterBar_letterColor) {
                letterColor = typedArray.getColor(attr, letterColor);
            } else if (attr == R.styleable.LetterBar_letterVerticalPadding) {
                letterVerticalPadding = typedArray.getDimensionPixelSize(attr, letterVerticalPadding);
            } else if (attr == R.styleable.LetterBar_letterHorizontalPadding) {
                letterHorizontalPadding = typedArray.getDimensionPixelSize(attr, letterHorizontalPadding);
            } else if (attr == R.styleable.LetterBar_barTouchBackground) {
                barTouchBackground = typedArray.getDrawable(attr);
            } else if (attr == R.styleable.LetterBar_letterTouchColor) {
                letterTouchColor = typedArray.getColor(attr, letterTouchColor);
            } else if (attr == R.styleable.LetterBar_letterShowChecked) {
                letterShowChecked = typedArray.getBoolean(attr, letterShowChecked);
            } else if (attr == R.styleable.LetterBar_letterTouchDrawable) {
                letterTouchDrawable = typedArray.getDrawable(attr);
            } else if (attr == R.styleable.LetterBar_letterDynamicMode) {
                letterDynamicMode = typedArray.getBoolean(attr, letterDynamicMode);
            } else if (attr == R.styleable.LetterBar_letterTouchShow) {
                charLetterTouchShow = typedArray.getBoolean(attr, charLetterTouchShow);
            }
        }
        typedArray.recycle();

        mPaint.setTextSize(letterSize);
        mLetterColor = letterColor;
        mBarBackground = barBackground;
        mLetterVerticalPadding = letterVerticalPadding;
        mLetterHorizontalPadding = letterHorizontalPadding;
//        mExpandLeftWidth = expandLeftWidth;
//        mExpandRightWidth = expandRightWidth;
        mBarTouchBackground = barTouchBackground;
        mLetterTouchColor = letterTouchColor;
        mLetterShowChecked = letterShowChecked;
        mLetterTouchDrawable = letterTouchDrawable;
        mLetterDynamicMode = letterDynamicMode;
        mCharLetterTouchShow = charLetterTouchShow;
    }

    private void initTextPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(false);
//        mPaint.setStrokeWidth(5);
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    private void init() {
        if (!mLetterDynamicMode) {
            for (char c = CHAR_A; c <= CHAR_Z; c++) {
                mLetterList.add(new CharLetter.DefaultCharLetter(c));
            }
        }
        mRect = new Rect();
        mPaint.getTextBounds("W", 0, 1, mRect);
        itemWidth = mRect.width() + 2 * mLetterHorizontalPadding;
        mPaint.getTextBounds("Q", 0, 1, mRect);
        itemHeight = mRect.height() + mLetterVerticalPadding;
        mTouchRect = new Rect();
        mPopupHandler = new PopupHandler(this);
        if (mLetterList != null && mLetterList.size() > 0) {
            for (ILetter iLetter : mLetterList) {
                if (iLetter instanceof CharLetter) {
                    iLetter.setLetterTouchShow(mCharLetterTouchShow);
                }
            }
        }
    }

    public void refresh() {
        requestLayout();
    }

    public <T extends CharLetter> void attachCharLetter(List<T> charLetterList) {
        if (mLetterDynamicMode) {
            addAutoCharLetter(charLetterList);
        } else {
            attachDefaultCharLetter(charLetterList);
        }
    }

    public <T extends CharLetter> void attachCharLetter(T t) {
        if (mLetterDynamicMode) {
            addAutoCharLetter(t);
        } else {
            attachDefaultCharLetter(t);
        }
    }

    private <T extends CharLetter> void attachDefaultCharLetter(List<T> charLetterList) {
        for (T t : charLetterList) {
            for (ILetter iLetter : mLetterList) {
                if (iLetter instanceof CharLetter.DefaultCharLetter) {
                    CharLetter.DefaultCharLetter charLetter = (CharLetter.DefaultCharLetter) iLetter;
                    if (charLetter.equals(t.getCharLetter())) {
                        charLetter.setLetterTouchShow(true);
                        break;
                    }
                }
            }
        }
    }

    private <T extends CharLetter> void attachDefaultCharLetter(T t) {
        for (ILetter iLetter : mLetterList) {
            if (iLetter instanceof CharLetter.DefaultCharLetter) {
                CharLetter.DefaultCharLetter charLetter = (CharLetter.DefaultCharLetter) iLetter;
                if (charLetter.equals(t)) {
                    charLetter.setLetterTouchShow(true);
                    break;
                }
            }
        }
    }

    /**
     * 自动补全LetterBar添加 SpecialLetter
     */
    public LetterBar addAutoSpecialLetter(char ch, int compareInt) {
        if (!mLetterDynamicMode) {
            throw new IllegalStateException("LetterBar is not dynamicMode, can't use the method.");
        }
        if (compareInt >= CHAR_A && compareInt <= CHAR_Z) {
            throw new RuntimeException("compareInt can't be within range A-Z.");
        }
        if (mSpecialCharList == null) mSpecialCharList = new ArrayList<>();
        mSpecialCharList.add(new CharLetter.SpecialCharLetter(ch, compareInt));
        return this;
    }

    private <T extends CharLetter> void addAutoCharLetter(List<T> charLetterList) {
        if (!mLetterDynamicMode) {
            throw new IllegalStateException("LetterBar is not dynamicMode, can't use the method.");
        }
        for (CharLetter charLetter : charLetterList) {
            if (mSpecialCharList != null && charLetter instanceof CharLetter.SpecialCharLetter) {
                CharLetter.SpecialCharLetter specialCharLetter = (CharLetter.SpecialCharLetter) charLetter;
                addSpecialLetter(specialCharLetter);
            } else {
                addCompareCharLetter(charLetter);
            }
        }
        refresh();
    }

    private <T extends CharLetter> void addAutoCharLetter(T charLetter) {
        if (!mLetterDynamicMode) {
            throw new IllegalStateException("LetterBar is not dynamicMode, can't use the method.");
        }
        if (mSpecialCharList != null && !Character.isUpperCase(charLetter.getCharLetter())) {
            for (CharLetter.SpecialCharLetter specialCharLetter : mSpecialCharList) {
                if (specialCharLetter.equals(charLetter.getCharLetter())) {
                    addSpecialLetter(specialCharLetter);
                    break;
                }
            }
        } else {
            addCompareCharLetter(charLetter);
        }
        refresh();
    }

    private void addSpecialLetter(CharLetter.SpecialCharLetter specialCharLetter) {
        if (mLetterList.contains(specialCharLetter)) return;
        if (specialCharLetter.compare(CHAR_Z) > 0) {
            for (int i = mLetterList.size() - 1; i >= 0; i--) {
                ILetter iLetter = mLetterList.get(i);
                if (iLetter instanceof CharLetter) {
                    CharLetter charLetter = (CharLetter) iLetter;
                    if (specialCharLetter.compare(charLetter.getCharLetter()) > 0) {
                        mLetterList.add(specialCharLetter);
                        break;
                    }
                }
            }
        }
        if (specialCharLetter.compare(CHAR_A) < 0) {
            for (int i = 0; i < mLetterList.size(); i++) {
                ILetter iLetter = mLetterList.get(i);
                if (iLetter instanceof CharLetter) {
                    CharLetter charLetter = (CharLetter) iLetter;
                    if (specialCharLetter.compareTo(charLetter) < 0) {
                        mLetterList.add(i, specialCharLetter);
                        break;
                    }
                }
            }
        }
    }

    private void addCompareCharLetter(CharLetter charLetter) {
        CharLetter.ComparableCharLetter comparableCharLetter = new CharLetter.ComparableCharLetter(charLetter);
        if (mCompareCharList == null || mCompareCharList.size() == 0) {
            mLetterList.add(comparableCharLetter);
            mCompareCharList = new ArrayList<>();
            mCompareCharList.add(comparableCharLetter);
        } else {
            if (mLetterList.contains(comparableCharLetter)) return;
            int addIndex = mLetterList.indexOf(mCompareCharList.get(0));
            if (comparableCharLetter.getCharLetter() == CHAR_A) {
                addIndex = mLetterList.indexOf(mCompareCharList.get(0));
            } else if (comparableCharLetter.getCharLetter() == CHAR_Z) {
                addIndex = mLetterList.indexOf(mCompareCharList.get(mCompareCharList.size() - 1)) + 1;
            } else {
                for (int ch = comparableCharLetter.getCharLetter() - 1; ch >= 'A'; ch--) {
                    CharLetter.ComparableCharLetter instanceCharLetter = CharLetter.ComparableCharLetter.getInstance((char) ch);
                    if (mCompareCharList.contains(instanceCharLetter)) {
                        addIndex = mLetterList.indexOf(instanceCharLetter) + 1;
                        break;
                    }
                }
            }
            mLetterList.add(addIndex, comparableCharLetter);
            mCompareCharList.add(comparableCharLetter);
            Collections.sort(mCompareCharList);
        }
    }

    public <T extends ILetter> T getLetter(int position) {
        return (T) mLetterList.get(position);
    }

    public void setNoneShowLetter(char... chars) {
        if (mLetterDynamicMode)
            throw new IllegalStateException("LetterBar is dynamicMode, can't use the method.");
        for (char ch : chars) {
            for (ILetter iLetter : mLetterList) {
                if (iLetter instanceof CharLetter) {
                    CharLetter charLetter = (CharLetter) iLetter;
                    if (charLetter.getCharLetter() == ch) {
                        charLetter.setLetterTouchShow(false);
                        break;
                    }
                }
            }
        }
    }

    public void setNoneShowLetter(int... positions) {
        if (mLetterDynamicMode)
            throw new IllegalStateException("LetterBar is dynamicMode, can't use the method.");
        for (int pos : positions) {
            mLetterList.get(pos).setLetterTouchShow(false);
        }
    }

    public LetterBar addFirstChar(char ch) {
        mLetterList.add(0, new CharLetter.DefaultCharLetter(ch));
        return this;
    }

    public LetterBar addLastChar(char ch) {
        mLetterList.add(new CharLetter.DefaultCharLetter(ch));
        return this;
    }

    public LetterBar addFirstString(String string) {
        mLetterList.add(0, new StringLetter(string));
        return this;
    }

    public LetterBar addLastString(String string) {
        mLetterList.add(new StringLetter(string));
        return this;
    }

    public LetterBar addFirstDrawable(DrawableLetter drawableLetter) {
        mLetterList.add(0, drawableLetter);
        return this;
    }

    public LetterBar addFirstDrawable(@DrawableRes int drawable) {
        return addFirstDrawable(new DrawableLetter(getResources().getDrawable(drawable)));
    }

    public LetterBar addLastDrawable(DrawableLetter drawableLetter) {
        mLetterList.add(drawableLetter);
        return this;
    }

    public LetterBar addLastDrawable(@DrawableRes int drawable) {
        return addLastDrawable(new DrawableLetter(getResources().getDrawable(drawable)));
    }

    public void setLetterScrollListener(LetterScrollListener letterScrollListener) {
        this.mLetterScrollListener = letterScrollListener;
    }

    public void setLetterTouchListener(LetterTouchListener letterTouchListener) {
        this.mLetterTouchListener = letterTouchListener;
        mLetterTouchListener.setLetterBar(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBarBackground(canvas);
        drawLetter(canvas);
    }

    //绘制slideBar背景
    private void drawBarBackground(Canvas canvas) {
        if (mBarBackground != null) {
            mBarBackground.setBounds(0, 0, mWidth, mHeight);
            mBarBackground.draw(canvas);
        }
        if (mBarTouchBackground != null && mTouchState) {
            mBarTouchBackground.setBounds(0, 0, mWidth, mHeight);
            mBarTouchBackground.draw(canvas);
        }

    }

    private void drawLetter(Canvas canvas) {
        mPaint.setColor(mLetterColor);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float baseline;
        mRect.set(0, 0, mWidth, itemHeight);
        int size = mLetterList.size();
        for (int i = 0; i < size; i++) {
            ILetter letterBean = mLetterList.get(i);
            baseline = (mRect.bottom + mRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
            mPaint.setColor(mLetterColor);
            if (letterBean instanceof DrawableLetter) {
                DrawableLetter drawableLetter = (DrawableLetter) letterBean;
                if (mTouchState && mTouchIndex == i || (!mTouchState && mTouchIndex == i && mLetterShowChecked)) {
                    if (mLetterTouchDrawable != null) {
                        mTouchRect.set(mRect);
                        mLetterTouchDrawable.setBounds(mTouchRect);
                        mLetterTouchDrawable.draw(canvas);
                    }
                }
                Drawable drawable = drawableLetter.getDrawable();
                drawable.setBounds(mRect);
                drawable.draw(canvas);
            } else if (letterBean instanceof CharLetter) {
                CharLetter charLetter = (CharLetter) letterBean;
                if (mTouchState && mTouchIndex == i || (!mTouchState && mTouchIndex == i && mLetterShowChecked)) {
                    if (mLetterTouchDrawable != null) {
                        mTouchRect.set(mRect);
                        mLetterTouchDrawable.setBounds(mTouchRect);
                        mLetterTouchDrawable.draw(canvas);
                    }
                    mPaint.setColor(mLetterTouchColor);
                    canvas.drawText(String.valueOf(charLetter.getCharLetter()), 0, 1, mRect.centerX(), baseline, mPaint);
                } else {
                    canvas.drawText(String.valueOf(charLetter.getCharLetter()), 0, 1, mRect.centerX(), baseline, mPaint);
                }
            } else if (letterBean instanceof StringLetter) {
                StringLetter stringLetter = (StringLetter) letterBean;
                if (mTouchState && mTouchIndex == i || (!mTouchState && mTouchIndex == i && mLetterShowChecked)) {
                    if (mLetterTouchDrawable != null) {
                        mTouchRect.set(mRect);
                        mLetterTouchDrawable.setBounds(mTouchRect);
                        mLetterTouchDrawable.draw(canvas);
                    }
                    mPaint.setColor(mLetterTouchColor);
                    canvas.drawText(stringLetter.getLetter(), 0, stringLetter.getLetter().length(),
                            mRect.centerX(), baseline, mPaint);
                } else {
                    canvas.drawText(stringLetter.getLetter(), 0, stringLetter.getLetter().length(),
                            mRect.centerX(), baseline, mPaint);
                }
            }

            mRect.offset(0, mRect.height());
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float yPos = event.getY();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                check(yPos);
                mTouchState = true;
                if (mTouchIndex >= 0 && mTouchIndex < mLetterList.size()) {
                    if (mLetterScrollListener != null) {
                        mLetterScrollListener.onLetterTouch(mLetterList.get(mTouchIndex), mTouchIndex);
                    }
                    if (mLetterTouchListener != null) {
                        if (mLetterTouchListener instanceof PopupTouchListener) {
                            PopupTouchListener popupTouchListener = (PopupTouchListener) mLetterTouchListener;
                            popupTouchListener.show(mLetterList.get(mTouchIndex), (int) yPos);
                            mPopupHandler.removeMessages(0);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                check(yPos);
                mTouchState = true;
                if (mTouchIndex >= 0 && mTouchIndex < mLetterList.size()) {
                    if (mLetterScrollListener != null) {
                        mLetterScrollListener.onLetterTouch(mLetterList.get(mTouchIndex), mTouchIndex);
                    }
                    if (mLetterTouchListener instanceof PopupTouchListener) {
                        PopupTouchListener popupTouchListener = (PopupTouchListener) mLetterTouchListener;
                        popupTouchListener.update(mLetterList.get(mTouchIndex), -1, (int) yPos);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                mTouchState = false;
                if (mLetterTouchListener != null) {
                    if (mLetterTouchListener instanceof PopupTouchListener) {
                        PopupTouchListener popupTouchListener = (PopupTouchListener) mLetterTouchListener;
                        long duration = popupTouchListener.getDuration();

                        mPopupHandler.sendEmptyMessageDelayed(0, duration == 0 ?
                                PopupTouchListener.DEFAULT_DURATION : duration);
                    }
                }
                break;
        }
        invalidate();
        return true;
    }

    public int getTouchIndexCenterY() {
        return mTouchIndex == 0 ? itemHeight / 2 : mTouchIndex * itemHeight + itemHeight / 2;
    }

    private void check(float y) {
        int addIndex = (int) (y / itemHeight);
        if (addIndex >= 0 && addIndex < mLetterList.size()) {
            mTouchIndex = addIndex;
        }
    }

    private static class PopupHandler extends Handler {
        final WeakReference<LetterBar> mLetterBar;

        PopupHandler(LetterBar letterBar) {
            mLetterBar = new WeakReference<>(letterBar);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                LetterBar letterBar = mLetterBar.get();
                if (letterBar != null) {
                    if (letterBar.mLetterTouchListener instanceof PopupTouchListener) {
                        PopupTouchListener popupTouchListener = (PopupTouchListener) letterBar.mLetterTouchListener;
                        popupTouchListener.dismiss();
                    }
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
            if (mLetterList.size() > 0) {
                mHeight -= mHeight % mLetterList.size();
                itemHeight = mHeight / mLetterList.size();
            }
        } else if (heightMode == MeasureSpec.AT_MOST) {
            mHeight = mLetterList.size() * itemHeight;
        }
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
            itemWidth = mWidth;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            mWidth = itemWidth;
        }
        setMeasuredDimension(mWidth, mHeight);
    }
}
