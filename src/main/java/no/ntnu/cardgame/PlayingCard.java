package no.ntnu.cardgame;

/**
 * Represents a single playing card with a suit and a face value.
 *
 * <p>Suits are represented as characters:
 * <ul>
 *   <li>'S' = Spades</li>
 *   <li>'H' = Hearts</li>
 *   <li>'D' = Diamonds</li>
 *   <li>'C' = Clubs</li>
 * </ul>
 * Face values range from 1 (Ace) to 13 (King).
 */
public class PlayingCard {

    private final char suit;   // 'S', 'H', 'D', 'C'
    private final int face;    // 1–13

    /**
     * Creates a playing card with the given suit and face value.
     *
     * @param suit the suit character ('S', 'H', 'D', or 'C')
     * @param face the face value (1–13)
     * @throws IllegalArgumentException if suit or face is invalid
     */
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

    /**
     * Returns the suit character of this card.
     *
     * @return 'S', 'H', 'D', or 'C'
     */
    public char getSuit() {
        return suit;
    }

    /**
     * Returns the face value of this card (1 = Ace, 13 = King).
     *
     * @return face value 1–13
     */
    public int getFace() {
        return face;
    }

    /**
     * Returns a string representation such as "H12" (Queen of Hearts) or "S1" (Ace of Spades).
     *
     * @return string representation of this card
     */
    @Override
    public String toString() {
        return "" + suit + face;
    }

    /**
     * Returns the full name of this card, e.g. "Queen of Hearts".
     *
     * @return full card name
     */
    public String getFullName() {
        String suitName = switch (suit) {
            case 'S' -> "Spades";
            case 'H' -> "Hearts";
            case 'D' -> "Diamonds";
            case 'C' -> "Clubs";
            default  -> "Unknown";
        };
        String faceName = switch (face) {
            case 1  -> "Ace";
            case 11 -> "Jack";
            case 12 -> "Queen";
            case 13 -> "King";
            default -> String.valueOf(face);
        };
        return faceName + " of " + suitName;
    }
}
