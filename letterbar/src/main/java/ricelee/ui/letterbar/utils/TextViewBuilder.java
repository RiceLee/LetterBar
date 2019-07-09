package ricelee.ui.letterbar.utils;

import android.view.Gravity;
import android.widget.TextView;

public class TextViewBuilder extends ViewBackgroundBuilder {

    private int gravity = Gravity.CENTER;
    private int leftPadding;
    private int topPadding;
    private int rightPadding;
    private int bottomPadding;
    private float textSize;
    private int textColor;

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

    @Override
    public TextViewBuilder setTopLeftRadius(float topLeftRadius) {
        super.setTopLeftRadius(topLeftRadius);
        return this;
    }

    @Override
    public TextViewBuilder setTopRightRadius(float topRightRadius) {
        super.setTopRightRadius(topRightRadius);
        return this;
    }

    @Override
    public TextViewBuilder setBottomLeftRadius(float bottomLeftRadius) {
        super.setBottomLeftRadius(bottomLeftRadius);
        return this;
    }

    @Override
    public TextViewBuilder setBottomRightRadius(float bottomRightRadius) {
        super.setBottomRightRadius(bottomRightRadius);
        return this;
    }

    @Override
    public TextViewBuilder setRadius(float topLeftRadius, float topRightRadius, float bottomLeftRadius, float
            bottomRightRadius) {
        super.setRadius(topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius);
        return this;
    }

    @Override
    public TextViewBuilder setRadius(float radius) {
        super.setRadius(radius);
        return this;
    }

    @Override
    public TextViewBuilder setSolidColor(int solidColor) {
        super.setSolidColor(solidColor);
        return this;
    }

    @Override
    public TextViewBuilder setStrokeColor(int strokeColor) {
        super.setStrokeColor(strokeColor);
        return this;
    }

    @Override
    public TextViewBuilder setStrokeWidth(int strokeWidth) {
        super.setStrokeWidth(strokeWidth);
        return this;
    }

    @Override
    public TextViewBuilder setFillStyle(int fillStyle) {
        super.setFillStyle(fillStyle);
        return this;
    }

    @Override
    public TextViewBuilder setShape(int shape) {
        super.setShape(shape);
        return this;
    }

    public TextView buildTextView(TextView textView) {
        textView.setTextSize(textSize);
        textView.setTextColor(textColor);
        textView.setPadding(leftPadding, topPadding, rightPadding, bottomPadding);
        textView.setGravity(gravity);
//        textView.setBackground(buildDrawable());
        ViewBackgroundProvider.setViewBackground(textView, this);
        return textView;
    }

}
