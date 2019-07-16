package ricelee.ui.letterbar;

import ricelee.ui.letterbar.utils.TextViewBuilder;

public class SingleLetterPopup {

    TextViewBuilder textViewBuilder;
//    @AnimatorRes
//    private int popInAnim;
//
//    @AnimatorRes
//    private int popOutAnim;

//    private View rootView;

    private PopupBehavior popupBehavior;

    private long duration;

    public TextViewBuilder getTextViewBuilder() {
        return textViewBuilder;
    }

    public SingleLetterPopup setTextViewBuilder(TextViewBuilder textViewBuilder) {
        this.textViewBuilder = textViewBuilder;
        return this;
    }

//    public int getPopInAnim() {
//        return popInAnim;
//    }
//
//    public SingleLetterPopup setPopInAnim(int popInAnim) {
//        this.popInAnim = popInAnim;
//        return this;
//    }
//
//    public int getPopOutAnim() {
//        return popOutAnim;
//    }
//
//    public SingleLetterPopup setPopOutAnim(int popOutAnim) {
//        this.popOutAnim = popOutAnim;
//        return this;
//    }

    public PopupBehavior getPopupBehavior() {
        return popupBehavior;
    }

    public SingleLetterPopup setPopupBehavior(PopupBehavior popupBehavior) {
        this.popupBehavior = popupBehavior;
        return this;
    }

    public long getDuration() {
        return duration;
    }

    public SingleLetterPopup setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    public SingeTextPopup createPopup() {
        return new SingeTextPopup(this);
    }
}
