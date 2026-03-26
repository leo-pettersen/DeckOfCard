package no.ntnu.cardgame;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a hand of playing cards dealt to a player.
 *
 * <p>Provides stream-based analysis methods for the hand:
 * <ul>
 *   <li>Sum of all face values</li>
 *   <li>Hearts cards as a formatted string</li>
 *   <li>Queen of Spades check</li>
 *   <li>5-card flush check</li>
 * </ul>
 */
public class HandOfCards {

    private final List<PlayingCard> cards;

    /**
     * Creates a hand from the given list of cards.
     *
     * @param cards the cards in this hand (must not be null)
     */
    public HandOfCards(List<PlayingCard> cards) {
        if (cards == null) {
            throw new IllegalArgumentException("Card list must not be null");
        }
        this.cards = Collections.unmodifiableList(cards);
    }

    /**
     * Returns an unmodifiable view of the cards in this hand.
     *
     * @return list of cards
     */
    public List<PlayingCard> getCards() {
        return cards;
    }

    /**
     * Returns the number of cards in this hand.
     *
     * @return hand size
     */
    public int size() {
        return cards.size();
    }

    // -------------------------------------------------------------------------
    // Oppgave 5 – Stream-based analysis
    // -------------------------------------------------------------------------

    /**
     * Calculates the sum of all face values in this hand (Ace = 1).
     *
     * @return sum of face values
     */
    public int sumOfFaces() {
        return cards.stream()
                .mapToInt(PlayingCard::getFace)
                .sum();
    }

    /**
     * Returns a formatted string of all Hearts cards in this hand.
     *
     * <p>The format is {@code "H12 H9 H1"} (suit-letter followed by face value,
     * space-separated). If there are no Hearts, returns {@code "No Hearts"}.
     *
     * @return formatted string of Hearts cards, or "No Hearts"
     */
    public String heartsAsString() {
        String result = cards.stream()
                .filter(c -> c.getSuit() == 'H')
                .map(PlayingCard::toString)
                .collect(Collectors.joining(" "));

        return result.isEmpty() ? "No Hearts" : result;
    }

    /**
     * Checks whether the Queen of Spades (S12) is present in this hand.
     *
     * @return {@code true} if the Queen of Spades is in the hand
     */
    public boolean hasQueenOfSpades() {
        return cards.stream()
                .anyMatch(c -> c.getSuit() == 'S' && c.getFace() == 12);
    }

    /**
     * Checks whether this hand is a 5-card flush (all 5 cards share the same suit).
     *
     * <p>A flush requires exactly 5 cards all of the same suit.
     *
     * @return {@code true} if the hand contains exactly 5 cards of the same suit
     */
    public boolean isFlush() {
        if (cards.size() != 5) {
            return false;
        }
        char firstSuit = cards.get(0).getSuit();
        return cards.stream()
                .allMatch(c -> c.getSuit() == firstSuit);
    }

    /**
     * Returns a space-separated string representation of all cards, e.g. "H4 H12 C3 D11 S1".
     *
     * @return string of all cards
     */
    @Override
    public String toString() {
        return cards.stream()
                .map(PlayingCard::toString)
                .collect(Collectors.joining(" "));
    }
}
