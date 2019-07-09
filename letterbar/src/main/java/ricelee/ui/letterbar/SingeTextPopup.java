package ricelee.ui.letterbar;


import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import ricelee.ui.letterbar.utils.Utils;

import static ricelee.ui.letterbar.LetterBar.POPUP_BEHAVIOR_CENTER;
import static ricelee.ui.letterbar.LetterBar.POPUP_BEHAVIOR_CENTER_ITEM;
import static ricelee.ui.letterbar.LetterBar.POPUP_BEHAVIOR_FOLLOW_Y;
import static ricelee.ui.letterbar.LetterBar.POPUP_BEHAVIOR_STABLE;


public class SingeTextPopup implements PopupTouchListener {

    private int[] letterBarLocation;
    private LetterBar letterBar;
    private SingleLetterPopup letterPopup;
    private PopupWindow popupWindow;
    private TextView textView;
    private Rect mRect;

    public SingeTextPopup(SingleLetterPopup letterPopup) {
        this.letterPopup = letterPopup;
    }


    @Override
    public void update(ILetter letter, int x, int y) {
        setContent(letter, textView);
        PopupBehavior popupBehavior = letterPopup.getPopupBehavior();
        switch (popupBehavior.getBehavior()) {
            case POPUP_BEHAVIOR_FOLLOW_Y:
                popupWindow.update(popupBehavior.getX(), calculateFollowY(y), popupWindow.getWidth(), popupWindow.getHeight());
                break;
            case POPUP_BEHAVIOR_CENTER_ITEM:
                popupWindow.update(popupBehavior.getX(), calculateCenterItem(), popupWindow.getWidth(), popupWindow.getHeight());
                break;
        }
    }

    @Override
    public void show(ILetter letter, int y) {
        initPopupWindow();

        setContent(letter, textView);
        if (popupWindow != null && !popupWindow.isShowing()) {
            PopupBehavior popupBehavior = letterPopup.getPopupBehavior();
            switch (popupBehavior.getBehavior()) {
                case POPUP_BEHAVIOR_CENTER:
                    popupWindow.showAtLocation(letterBar.getRootView(), Gravity.CENTER, 0, 0);
                    break;
                case POPUP_BEHAVIOR_STABLE:
                    popupWindow.showAtLocation(popupBehavior.getRootView(), popupBehavior.getGravity(), popupBehavior.getX(), popupBehavior.getY());
                    break;
                case POPUP_BEHAVIOR_FOLLOW_Y:
                    popupWindow.showAtLocation(letterBar.getRootView(), Gravity.NO_GRAVITY, popupBehavior.getX(), calculateFollowY(y));
                    break;
                case POPUP_BEHAVIOR_CENTER_ITEM:
                    popupWindow.showAtLocation(letterBar.getRootView(), Gravity.NO_GRAVITY, popupBehavior.getX(), calculateCenterItem());
                    break;
            }
        }
    }

    private int calculateCenterItem() {
        if (letterBarLocation == null) {
            letterBarLocation = Utils.getViewLocation(letterBar);
        }
        return letterBarLocation[1] + letterBar.getTouchIndexCenterY() - textView.getHeight() / 2;
    }

    private int calculateFollowY(int y) {
        if (letterBarLocation == null) {
            letterBarLocation = Utils.getViewLocation(letterBar);
        }
        if (y <= 0) {
            y = letterBarLocation[1] - textView.getHeight() / 2;
        } else if (y >= letterBar.getHeight()) {
            y = letterBarLocation[1] + letterBar.getHeight() - textView.getHeight() / 2;
        } else {
            y = letterBarLocation[1] + y - textView.getHeight() / 2;
        }
        return y;
    }

    @Override
    public void setLetterBar(LetterBar letterBar) {
        this.letterBar = letterBar;
    }

    @Override
    public long getDuration() {
        return letterPopup.getDuration() <= 0 ? DEFAULT_DURATION : letterPopup.getDuration();
    }

    @Override
    public void dismiss() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }

    }

    protected void setContent(ILetter letterBean, TextView textView) {
        if (letterBean instanceof CharLetter) {
            CharLetter charLetter = (CharLetter) letterBean;
            textView.setText(String.valueOf(charLetter.getCharLetter()));
        } else if (letterBean instanceof StringLetter) {
            String letter = ((StringLetter) letterBean).getLetter();
            textView.setText(letter);
        } else if (letterBean instanceof DrawableLetter) {
            DrawableLetter drawableLetter = (DrawableLetter) letterBean;
            SpannableString drawableSpan = drawableLetter.getDrawableSpan();
            resize(mRect, drawableLetter.getMutateDrawable());
            textView.setText(drawableSpan);
        }
    }

    private void resize(Rect rect, Drawable drawable) {
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        int mWidth = rect.width() - textView.getPaddingLeft() - textView.getPaddingRight();
        int mHeight = rect.height() - textView.getPaddingTop() - textView.getPaddingBottom();
        int width = mWidth < intrinsicWidth ? mWidth : intrinsicWidth;
        int height = mHeight < intrinsicHeight ? mHeight : intrinsicHeight;
        drawable.setBounds(0, 0, width, height);
    }


    private void initPopupWindow() {
        if (popupWindow != null) return;
        popupWindow = new PopupWindow();
        textView = new TextView(letterBar.getContext());
        letterPopup.textViewBuilder.buildTextView(textView);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        popupWindow.setContentView(textView);
        mRect = new Rect();
        TextPaint paint = textView.getPaint();
        paint.getTextBounds("W", 0, 1, mRect);
        int width = mRect.width();
        int height = mRect.height();
        int[] measureView = Utils.measureView(textView);
        if (width > measureView[0] - textView.getPaddingLeft() - textView.getPaddingRight()) {
            width += textView.getPaddingLeft() + textView.getPaddingRight();
        } else {
            width = measureView[0];
        }
        if (height > measureView[1] - textView.getPaddingTop() - textView.getPaddingBottom()) {
            height += textView.getPaddingTop() + textView.getPaddingBottom();
        } else {
            height = measureView[1];
        }
        width = height = width >= height ? width : height;
        mRect.set(0, 0, width, height);
        popupWindow.setWidth(mRect.width());
        popupWindow.setHeight(mRect.height());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());

    }
}

