package ricelee.ui.letterbar;

import android.util.Log;

public interface CharLetter extends ILetter {
    char getCharLetter();


    final class DefaultCharLetter implements CharLetter {
        char character;

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
    }


    final class ComparableCharLetter implements CharLetter, Comparable<ComparableCharLetter> {
        char character;

        public static ComparableCharLetter getInstance(char character) {
            return new ComparableCharLetter(character);
        }

        public ComparableCharLetter(char character) {
            if (!Character.isLetter(character))
                Log.e(ComparableCharLetter.class.getSimpleName(), "character is must letter");
            this.character = character;
        }

        public ComparableCharLetter(CharLetter charLetter) {
            this(charLetter.getCharLetter());
        }

        @Override
        public char getCharLetter() {
            return character;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj instanceof ComparableCharLetter) {
                ComparableCharLetter comparableCharLetter = (ComparableCharLetter) obj;
                return comparableCharLetter.character == character;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return character;
        }

        @Override
        public int compareTo(ComparableCharLetter other) {
            return Character.compare(this.character, other.character);
        }
    }
}
