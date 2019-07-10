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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import ricelee.ui.letterbar.utils.Utils;

public class LetterBar extends View {

    private static final String TAG = LetterBar.class.getSimpleName();
    private int mWidth, mHeight;
    private Rect mRect;
    private int itemHeight, itemWidth;

    private List<ILetter> letterList = new ArrayList<>();
    private int mCharNumber = 0;

    //  字母的模式 是否动态添加
    private boolean mLetterDynamicMode;
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
    //左侧扩展宽度
    private int mExpandLeftWidth;
    //右侧扩展宽度
    private int mExpandRightWidth;

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
        int expandLeftWidth = 0;
        int expandRightWidth = 0;
        Drawable barTouchBackground = null;
        int n = typedArray.getIndexCount();
        int letterTouchColor = letterColor;
        boolean letterShowChecked = false;
        Drawable letterTouchDrawable = null;
        boolean letterDynamicMode = false;
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
            } else if (attr == R.styleable.LetterBar_expandLeftWidth) {
                expandLeftWidth = typedArray.getDimensionPixelSize(attr, expandLeftWidth);
            } else if (attr == R.styleable.LetterBar_expandRightWidth) {
                expandRightWidth = typedArray.getDimensionPixelSize(attr, expandRightWidth);
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
            }
        }
        typedArray.recycle();

        mPaint.setTextSize(letterSize);
        mLetterColor = letterColor;
        mBarBackground = barBackground;
        mLetterVerticalPadding = letterVerticalPadding;
        mLetterHorizontalPadding = letterHorizontalPadding;
        mExpandLeftWidth = expandLeftWidth;
        mExpandRightWidth = expandRightWidth;
        mBarTouchBackground = barTouchBackground;
        mLetterTouchColor = letterTouchColor;
        mLetterShowChecked = letterShowChecked;
        mLetterTouchDrawable = letterTouchDrawable;
        mLetterDynamicMode = letterDynamicMode;
    }

    private void initTextPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(false);
//        mPaint.setStrokeWidth(5);
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    private void init() {
        if (!mLetterDynamicMode) {
            for (char c = 'A'; c <= 'Z'; c++) {
                letterList.add(new CharLetter.ComparableCharLetter(c));
                mCharNumber++;
            }
        }
        mRect = new Rect();
        mPaint.getTextBounds("M", 0, 1, mRect);
        itemHeight = (int) (mRect.height() + Utils.dp2px(2f));
        itemWidth = mRect.width() + 2 * mLetterHorizontalPadding + getPaddingLeft() + getPaddingRight();
        mTouchRect = new Rect();
        mPopupHandler = new PopupHandler(this);
    }

    interface LetterTouchListener {
        void setLetterBar(LetterBar letterBar);
    }

    public <T extends CharLetter> void addCharLetter(List<T> charLetterList) {
        for (CharLetter charLetter : charLetterList) {
            CharLetter.ComparableCharLetter comparableCharLetter = new CharLetter.ComparableCharLetter(charLetter);
            addDefaultCharLetter(comparableCharLetter);
        }
        requestLayout();
    }

    private List<CharLetter.ComparableCharLetter> getCharList() {
        if (mCharNumber == 0) return null;
        List<CharLetter.ComparableCharLetter> charLetterList = new ArrayList<>();
        for (int i = 0; i < letterList.size(); i++) {
            ILetter iLetter = letterList.get(i);
            if (iLetter instanceof CharLetter.ComparableCharLetter) {
                CharLetter.ComparableCharLetter comparableCharLetter = (CharLetter.ComparableCharLetter) iLetter;
                charLetterList.add(comparableCharLetter);
            }
        }
        return charLetterList;
    }

    private void addDefaultCharLetter(CharLetter.ComparableCharLetter charLetter) {
        List<CharLetter.ComparableCharLetter> charLetterList = getCharList();
        if (charLetterList == null || charLetterList.size() == 0) {
            letterList.add(charLetter);
            mCharNumber++;
            Log.e("addCharLetter", "char:" + charLetter.character + "\tmCharNumber:" + mCharNumber);
        } else {
            if (!letterList.contains(charLetter)) {
                int addIndex = letterList.indexOf(charLetterList.get(0));
                if (charLetter.character == 'A') {
                    addIndex = letterList.indexOf(charLetterList.get(0));
                } else if (charLetter.character == 'Z') {
                    addIndex = letterList.indexOf(charLetterList.get(charLetterList.size() - 1)) + 1;
                } else {
                    for (int ch = charLetter.character - 1; ch >= 'A'; ch--) {
                        CharLetter.ComparableCharLetter instanceCharLetter = CharLetter.ComparableCharLetter.getInstance((char) ch);
                        if (charLetterList.contains(instanceCharLetter)) {
                            addIndex = letterList.indexOf(instanceCharLetter) + 1;
                            break;
                        }
                    }
                }
                letterList.add(addIndex, charLetter);
                mCharNumber++;
                Log.e("addCharLetter", "char:" + charLetter.character + "\taddIndex:" + addIndex + "\tmCharNumber:" + mCharNumber);
            }
        }
    }

    public <T extends CharLetter> void addCharLetter(T charLetter) {
        addDefaultCharLetter(new CharLetter.ComparableCharLetter(charLetter));
        requestLayout();
    }

    public void addLetter(ILetter... letters) {
        for (ILetter iLetter : letters) {
            addLetter(iLetter);
        }
        requestLayout();
    }

    private void addLetter(ILetter iLetter) {
        letterList.add(iLetter);
    }

    public void addStringLetter(String string) {
        addLetter(new StringLetter(string));
        requestLayout();
    }

    public void addFirstDrawable(@DrawableRes int drawable) {
        letterList.add(0, new DrawableLetter(getResources().getDrawable(drawable)));
        requestLayout();
    }

    public void addLastDrawable(@DrawableRes int drawable) {
        letterList.add(new DrawableLetter(getResources().getDrawable(drawable)));
        requestLayout();
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
            mBarBackground.setBounds(mExpandLeftWidth, 0, mWidth - mExpandRightWidth, mHeight);
            mBarBackground.draw(canvas);
        }
        if (mBarTouchBackground != null && mTouchState) {
            mBarTouchBackground.setBounds(mExpandLeftWidth, 0, mWidth - mExpandRightWidth, mHeight);
            mBarTouchBackground.draw(canvas);
        }

    }

    private void drawLetter(Canvas canvas) {
        mPaint.setColor(mLetterColor);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float baseline;
        mRect.set(getPaddingLeft() + mExpandLeftWidth,
                getPaddingTop(),
                mWidth - getPaddingRight() - mExpandRightWidth,
                mLetterVerticalPadding + itemHeight);
        int size = letterList.size();
        for (int i = 0; i < size; i++) {
            ILetter letterBean = letterList.get(i);
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
                if (mTouchIndex >= 0 && mTouchIndex < letterList.size()) {
                    if (mLetterScrollListener != null) {
                        mLetterScrollListener.onLetterTouch(letterList.get(mTouchIndex));
                    }
                    if (mLetterTouchListener != null) {
                        if (mLetterTouchListener instanceof PopupTouchListener) {
                            PopupTouchListener popupTouchListener = (PopupTouchListener) mLetterTouchListener;
                            popupTouchListener.show(letterList.get(mTouchIndex), (int) yPos);
                            Log.e(getClass().getSimpleName(), "ACTION_DOWN:" + "\ty:" + yPos + "\tmTouchIndex:" + mTouchIndex + "\tletter:" + letterList.get(mTouchIndex).getClass().getSimpleName());
                            mPopupHandler.removeMessages(0);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                check(yPos);
                mTouchState = true;
                if (mTouchIndex >= 0 && mTouchIndex < letterList.size()) {
                    if (mLetterScrollListener != null) {
                        mLetterScrollListener.onLetterTouch(letterList.get(mTouchIndex));
                    }
                    if (mLetterTouchListener instanceof PopupTouchListener) {
                        PopupTouchListener popupTouchListener = (PopupTouchListener) mLetterTouchListener;
                        popupTouchListener.update(letterList.get(mTouchIndex), -1, (int) yPos);
                        Log.e(getClass().getSimpleName(), "update:" + "\ty:" + yPos + "\tmTouchIndex:" + mTouchIndex + "\tletter:" + letterList.get(mTouchIndex).getClass().getSimpleName());
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                mTouchState = false;
                if (mLetterScrollListener != null) {

                }
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
        return mTouchIndex == 0 ? mLetterVerticalPadding + itemHeight / 2
                : mLetterVerticalPadding + mTouchIndex * (itemHeight + mLetterVerticalPadding) + itemHeight / 2;
    }

    private void check(float y) {
        int addIndex = (int) ((y - getPaddingTop() - getPaddingBottom()) / (itemHeight + mLetterVerticalPadding));
        if (addIndex >= 0 && addIndex < letterList.size()) {
            mTouchIndex = addIndex;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e(TAG, "onSizeChanged:" + "\tw:" + w + "\th:" + h + "\toldw:" + oldw + "\toldh:" + oldh);

    }

//    @Override
//    protected void onDetachedFromWindow() {
//        super.onDetachedFromWindow();
//        mPopupHandler.removeMessages(0);
//        if (mLetterTouchListener != null) {
//            if (mLetterTouchListener instanceof PopupTouchListener) {
//                PopupTouchListener popupTouchListener = (PopupTouchListener) mLetterTouchListener;
//                popupTouchListener.dismiss();
//            }
//        }
//    }


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
            if (letterList.size() > 0) {
                itemHeight = (mHeight - getPaddingTop() - getPaddingBottom() - letterList.size() * mLetterVerticalPadding) / letterList.size();
            }
        } else if (heightMode == MeasureSpec.AT_MOST) {
            mHeight = letterList.size() * itemHeight + mLetterVerticalPadding * letterList.size() + getPaddingTop() + getPaddingBottom();
            Log.e("mLetterDynamicMode", "mHeight:" + mHeight + "\titemHeight:" + itemHeight + "\tmWidth:" + mWidth + "\titemWidth:" + itemWidth);
        }
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
            itemWidth = mWidth - getPaddingLeft() - getPaddingRight() - 2 * mLetterHorizontalPadding - mExpandLeftWidth - mExpandRightWidth;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            mWidth = itemWidth + getPaddingLeft() + getPaddingRight() + mExpandLeftWidth + mExpandRightWidth;
        }
        Log.e("onMeasure", "heightMode:" + mHeight + "\titemHeight:" + itemHeight + "\tmWidth:" + mWidth + "\titemWidth:" + itemWidth);

        setMeasuredDimension(mWidth, mHeight);
    }
}
