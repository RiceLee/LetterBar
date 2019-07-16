package ricelee.ui.letterbar.utils;

import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.widget.TextView;

public class TextViewBuilder {

    private int gravity = Gravity.CENTER;
    private int leftPadding;
    private int topPadding;
    private int rightPadding;
    private int bottomPadding;
    private float textSize;
    private int textColor;
    private Drawable backgroundDrawable;

    public TextViewBuilder() {
    }

    public TextViewBuilder setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public TextViewBuilder setPadding(int leftPadding, int topPadding, int rightPadding, int bottomPadding) {
        this.leftPadding = leftPadding;
        this.topPadding = topPadding;
        this.rightPadding = rightPadding;
        this.bottomPadding = bottomPadding;
        return this;
    }

    public TextViewBuilder setLeftPadding(int leftPadding) {
        this.leftPadding = leftPadding;
        return this;

    }

    public TextViewBuilder setTopPadding(int topPadding) {
        this.topPadding = topPadding;
        return this;
    }

    public TextViewBuilder setRightPadding(int rightPadding) {
        this.rightPadding = rightPadding;
        return this;
    }

    public TextViewBuilder setBottomPadding(int bottomPadding) {
        this.bottomPadding = bottomPadding;
        return this;
    }

    public TextViewBuilder setTextSize(float textSize) {
        this.textSize = textSize;
        return this;
    }

    public TextViewBuilder setTextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }

    public TextViewBuilder setBackgroundDrawable(Drawable backgroundDrawable) {
        this.backgroundDrawable = backgroundDrawable;
        return this;
    }

    public TextView buildTextView(TextView textView) {
        textView.setTextSize(textSize);
        textView.setTextColor(textColor);
        textView.setPadding(leftPadding, topPadding, rightPadding, bottomPadding);
        textView.setGravity(gravity);
        textView.setBackground(backgroundDrawable);
//        ViewBackgroundProvider.setViewBackground(textView, this);
        return textView;
    }

}
