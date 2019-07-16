package ricelee.ui.letterbar.utils;

import android.graphics.drawable.GradientDrawable;

public class ViewBackgroundBuilder {

    public static final int STYLE_FILL = 0;
    public static final int STYLE_STROKE = 1;
    public static final int STYLE_FILL_AND_STROKE = 2;

    private float topLeftRadius;
    private float topRightRadius;
    private float bottomLeftRadius;
    private float bottomRightRadius;
    private float radius;


    private int solidColor;
    private int strokeColor;
    private int strokeWidth;
    private int fillStyle = STYLE_FILL;
    private int shape = GradientDrawable.RECTANGLE;

    public ViewBackgroundBuilder() {
    }

    public ViewBackgroundBuilder setTopLeftRadius(float topLeftRadius) {
        this.topLeftRadius = topLeftRadius;
        return this;
    }

    public ViewBackgroundBuilder setTopRightRadius(float topRightRadius) {
        this.topRightRadius = topRightRadius;
        return this;
    }

    public ViewBackgroundBuilder setBottomLeftRadius(float bottomLeftRadius) {
        this.bottomLeftRadius = bottomLeftRadius;
        return this;
    }

    public ViewBackgroundBuilder setBottomRightRadius(float bottomRightRadius) {
        this.bottomRightRadius = bottomRightRadius;
        return this;
    }

    public ViewBackgroundBuilder setRadius(float topLeftRadius, float topRightRadius, float bottomLeftRadius, float
            bottomRightRadius) {
        this.topLeftRadius = topLeftRadius;
        this.topRightRadius = topRightRadius;
        this.bottomLeftRadius = bottomLeftRadius;
        this.bottomRightRadius = bottomRightRadius;
        return this;
    }

    public ViewBackgroundBuilder setRadius(float radius) {
        this.radius = radius;
        return this;
    }

    //@ColorInt
    public ViewBackgroundBuilder setSolidColor(int solidColor) {
        this.solidColor = solidColor;
        return this;
    }

    public ViewBackgroundBuilder setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        return this;
    }

    public ViewBackgroundBuilder setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        return this;
    }

    public ViewBackgroundBuilder setFillStyle(int fillStyle) {
        this.fillStyle = fillStyle;
        return this;
    }

    public ViewBackgroundBuilder setShape(int shape) {
        this.shape = shape;
        return this;
    }

    public GradientDrawable buildDrawable() {
        return createBackgroundDrawable(fillStyle, solidColor, strokeWidth, strokeColor,
                radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius, shape);
    }

    public static GradientDrawable createBackgroundDrawable(int mFillType, int mSolidColor,
                                                            int mStrokeWidth, int mStrokeColor,
                                                            float mRadius,
                                                            float mTopLeftRadius, float mTopRightRadius,
                                                            float mBottomLeftRadius, float mBottomRightRadius, int
                                                                    mShape) {
        GradientDrawable mGradientDrawable = new GradientDrawable();
        switch (mFillType) {
            case STYLE_FILL:
                mGradientDrawable.setColor(mSolidColor);
                break;

            case STYLE_STROKE:
                mGradientDrawable.setStroke(mStrokeWidth, mStrokeColor);
                break;

            case STYLE_FILL_AND_STROKE:
                mGradientDrawable.setStroke(mStrokeWidth, mStrokeColor);
                mGradientDrawable.setColor(mSolidColor);
                break;
        }

        mGradientDrawable.setShape(mShape);
        mGradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        float outerRadii[];
        if (0 != mRadius) {
            outerRadii = new float[]{mRadius, mRadius, mRadius, mRadius, mRadius, mRadius, mRadius, mRadius};
        } else {
            outerRadii = new float[]{mTopLeftRadius, mTopLeftRadius, mTopRightRadius, mTopRightRadius,
                    mBottomLeftRadius, mBottomLeftRadius, mBottomRightRadius, mBottomRightRadius};
        }
        mGradientDrawable.setCornerRadii(outerRadii);
        return mGradientDrawable;
    }
}
