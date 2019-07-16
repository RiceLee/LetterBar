package ricelee.ui.letterbar;

import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;

public class DrawableLetter implements ILetter {

    private Drawable drawable;
    private SpannableString drawableSpan;
    private Drawable mutateDrawable;
    private boolean letterTouchShow;

    public DrawableLetter(Drawable drawable) {
        this(drawable, false);
    }

    public DrawableLetter(Drawable drawable, boolean letterTouchShow) {
        this.drawable = drawable;
        this.letterTouchShow = letterTouchShow;
        if (letterTouchShow) {
            initDrawableSpan();
        }
    }

    private void initDrawableSpan() {
        drawableSpan = new SpannableString("W");
        mutateDrawable = drawable.mutate().getConstantState().newDrawable();
        drawableSpan.setSpan(new ImageSpan(mutateDrawable, ImageSpan.ALIGN_BOTTOM), 0, 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
    }

    public void setLetterTouchShow(boolean letterTouchShow) {
        this.letterTouchShow = letterTouchShow;
        if (letterTouchShow && drawableSpan == null) {
            initDrawableSpan();
        }
    }

    @Override
    public boolean isLetterTouchShow() {
        return letterTouchShow;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public Drawable getMutateDrawable() {
        return mutateDrawable;
    }

    public SpannableString getDrawableSpan() {
        return drawableSpan;
    }
}
