package no.ntnu.cardgame;

public class PlayingCard {

    private final char suit;
    private final int face;

    public PlayingCard(char suit, int face) {
        if (suit != 'S' && suit != 'H' && suit != 'D' && suit != 'C') {
            throw new IllegalArgumentException("Invalid suit: " + suit);
        }
        if (face < 1 || face > 13) {
            throw new IllegalArgumentException("Invalid face value: " + face);
        }
        this.suit = suit;
        this.face = face;
    }

    public char getSuit() {
        return suit;
    }
    public int getFace() {
        return face;
    }

    @Override
    public String toString() {
        return "" + suit + face;
    }
}
