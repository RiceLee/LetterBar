package ricelee.ui.letterbar;

public interface CharLetter extends ILetter {
    char getCharLetter();

    class DefaultCharLetter implements CharLetter {
        private char character;

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
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj instanceof DefaultCharLetter) {
                DefaultCharLetter defaultCharLetter = (DefaultCharLetter) obj;
                return defaultCharLetter.character == character;
            }
            return false;
        }
    }

    final class NoneCompareCharLetter extends DefaultCharLetter {

        private boolean isTop;

        public NoneCompareCharLetter(char character, boolean isTop) {
            super(character);
            this.isTop = isTop;
        }

        public boolean isTop() {
            return isTop;
        }

        public void setTop(boolean top) {
            isTop = top;
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
