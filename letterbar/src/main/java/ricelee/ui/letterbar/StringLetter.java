package ricelee.ui.letterbar;

public class StringLetter implements  ILetter {

    private String letter;

    public StringLetter(String letter) {
        this.letter = letter;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    @Override
    public String toString() {
        return "StringLetter{" +
                "letter='" + letter + '\'' +
                '}';
    }
}
