package ricelee.ui.letterbar;

public class StringLetter implements ILetter {

    private String letter;
    private boolean letterTouchShow;

    public StringLetter(String letter) {
        this.letter = letter;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public void setLetterTouchShow(boolean letterTouchShow) {
        this.letterTouchShow = letterTouchShow;
    }

    @Override
    public boolean letterTouchShow() {
        return letterTouchShow;
    }

    @Override
    public String toString() {
        return "StringLetter{" +
                "letter='" + letter + '\'' +
                '}';
    }

}
