package ricelee.ui.letterbar;

import android.view.View;

public class PopupBehavior {

    private int behavior;
    private int gravity;
    private int x;
    private int y;
    private View rootView;

    public static PopupBehavior createCenterBehavior() {
        PopupBehavior popupBehavior = new PopupBehavior();
        popupBehavior.behavior = LetterBar.POPUP_BEHAVIOR_CENTER;
        return popupBehavior;
    }

    public static PopupBehavior createStableBehavior(View rootView, int gravity, int x, int y) {
        PopupBehavior popupBehavior = new PopupBehavior();
        popupBehavior.behavior = LetterBar.POPUP_BEHAVIOR_STABLE;
        popupBehavior.gravity = gravity;
        popupBehavior.x = x;
        popupBehavior.y = y;
        popupBehavior.rootView = rootView;
        return popupBehavior;
    }

    public static PopupBehavior createFollowYBehavior(int x) {
        PopupBehavior popupBehavior = new PopupBehavior();
        popupBehavior.behavior = LetterBar.POPUP_BEHAVIOR_FOLLOW_Y;
        popupBehavior.x = x;
        return popupBehavior;
    }

    public static PopupBehavior createCenterItemBehavior(int x) {
        PopupBehavior popupBehavior = new PopupBehavior();
        popupBehavior.behavior = LetterBar.POPUP_BEHAVIOR_CENTER_ITEM;
        popupBehavior.x = x;
        return popupBehavior;
    }

    public int getBehavior() {
        return behavior;
    }

    public void setBehavior(int behavior) {
        this.behavior = behavior;
    }

    public int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public View getRootView() {
        return rootView;
    }

    public void setRootView(View rootView) {
        this.rootView = rootView;
    }
}
