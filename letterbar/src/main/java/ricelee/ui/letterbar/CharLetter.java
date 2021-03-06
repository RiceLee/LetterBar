package ricelee.ui.letterbar;

public interface CharLetter extends ILetter {
    char getCharLetter();

    @Override
    default boolean isLetterTouchShow() {
        return false;
    }

    @Override
    default void setLetterTouchShow(boolean letterTouchShow) {
    }

    class DefaultCharLetter implements CharLetter {
        private char character;
        private boolean letterTouchShow = true;

        public DefaultCharLetter(char character) {
            this.character = character;
        }

        public DefaultCharLetter(CharLetter charLetter) {
            this(charLetter.getCharLetter());
        }

        @Override
        public char getCharLetter() {
            return character;
        }

        @Override
        public boolean isLetterTouchShow() {
            return letterTouchShow;
        }

        public void setLetterTouchShow(boolean letterTouchShow) {
            this.letterTouchShow = letterTouchShow;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj instanceof Character) {
                return character == (Character) obj;
            }
            if (obj instanceof DefaultCharLetter) {
                DefaultCharLetter defaultCharLetter = (DefaultCharLetter) obj;
                return defaultCharLetter.character == character;
            }
            return false;
        }
    }

    final class SpecialCharLetter extends DefaultCharLetter implements Comparable<CharLetter> {
        private int compareInt;

        public SpecialCharLetter(char character, int compareInt) {
            super(character);
            this.compareInt = compareInt;
        }

        public void setCompareInt(int compareInt) {
            this.compareInt = compareInt;
        }

        @Override
        public int hashCode() {
            return compareInt;
        }

        public int compare(char ch) {
            return compareInt - ch;
        }

        @Override
        public int compareTo(CharLetter o) {
            return compareInt - o.getCharLetter();
        }
    }

    final class ComparableCharLetter extends DefaultCharLetter implements Comparable<ComparableCharLetter> {
        public static ComparableCharLetter getInstance(char character) {
            return new ComparableCharLetter(character);
        }

        public ComparableCharLetter(char character) {
            super(character);
        }

        public ComparableCharLetter(CharLetter charLetter) {
            this(charLetter.getCharLetter());
        }

        @Override
        public int compareTo(ComparableCharLetter other) {
            return Character.compare(getCharLetter(), other.getCharLetter());
        }

        @Override
        public int hashCode() {
            return getCharLetter();
        }
    }
}
