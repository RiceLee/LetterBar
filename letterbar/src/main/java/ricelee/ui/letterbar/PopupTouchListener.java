package ricelee.ui.letterbar;

public interface PopupTouchListener extends LetterBar.LetterTouchListener {
    long DEFAULT_DURATION = 2000;

    long getDuration();

    void dismiss();

    void update(ILetter letter, int x, int y);

    void show(ILetter letter, int y);


}
