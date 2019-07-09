package ricelee.ui.letterbar;

import android.util.Log;

public interface CharLetter extends ILetter {
    char getCharLetter();

    final class DefaultCharLetter implements CharLetter, Comparable<DefaultCharLetter> {
        char character;

        public DefaultCharLetter(char character) {
            if (!Character.isLetter(character))
                Log.e(DefaultCharLetter.class.getSimpleName(), "character is must letter");
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

        @Override
        public int hashCode() {
            return character;
        }

        @Override
        public int compareTo(DefaultCharLetter other) {
            return Character.compare(this.character, other.character);
        }
    }
}
